package com.redbottledesign.drupal.gson.requestor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redbottledesign.drupal.Entity;
import com.redbottledesign.drupal.gson.DrupalGsonFactory;
import com.redbottledesign.drupal.gson.JsonEntityResultList;
import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;
import com.redbottledesign.gson.strategy.BeforeAndAfterExclusionStrategy;

public abstract class EntityRequestor
extends SessionBasedHttpRequestor
{
  private static final String JSON_URI_SUFFIX = ".json";

  public EntityRequestor(SessionManager sessionManager)
  {
    super(sessionManager);
  }

  protected abstract Type getListResultType();

  protected <T extends Entity<?>> T requestEntityById(int entityId, String entityType, Class<T> entityClass)
  throws IOException, DrupalHttpException
  {
    T     result      = null;
    URI   requestUri  = this.createUriForEntityId(entityType, entityId, false);

    try (InputStream  responseStream = this.executeRequest(new HttpGet(requestUri));
         Reader       responseReader = new InputStreamReader(responseStream))
    {
      Gson drupalGson = DrupalGsonFactory.getInstance().createGson();

      result = drupalGson.fromJson(responseReader, entityClass);
    }

    return result;
  }

  public <T extends Entity<?>> T requestEntityByCriterion(String entityType, String criterionName, Object criterionValue)
  throws IOException, DrupalHttpException
  {
    return this.requestEntityByCriteria(entityType, Collections.singletonMap(criterionName, criterionValue));
  }

  public <T extends Entity<?>> T requestEntityByCriteria(String entityType, Map<String, Object> criteria)
  throws IOException, DrupalHttpException
  {
    T   result      = null;
    URI requestUri  = this.createUriForEntityCriteria(entityType, criteria);

    try (InputStream  responseStream = this.executeRequest(new HttpGet(requestUri));
         Reader       responseReader = new InputStreamReader(responseStream))
    {
      Type                    listType    = this.getListResultType();
      Gson                    drupalGson  = DrupalGsonFactory.getInstance().createGson();
      JsonEntityResultList<T> jsonResults = drupalGson.fromJson(responseReader, listType);

      if (jsonResults != null)
      {
        List<T> results = jsonResults.getResults();

        if (!results.isEmpty())
          result = results.get(0);
      }
    }

    return result;
  }

  protected String computeDifferenceOnlyJson(final Entity<?> updatedEntity)
  {
    GsonBuilder     drupalGsonBuilder;
    Gson            drupalGson;
    final Entity<?> existingEntity;

    try
    {
      existingEntity =
        this.requestEntityById(
          updatedEntity.getId(),
          updatedEntity.getEntityType(),
          updatedEntity.getClass());
    }

    catch (IOException | DrupalHttpException ex)
    {
      throw new RuntimeException(
        String.format(
          "Failed to retrieve existing entity (type: %s, id: %s) for comparison\": %s",
          updatedEntity.getEntityType(),
          updatedEntity.getId(),
          ex.getMessage()
        ),
        ex);
    }

    drupalGsonBuilder = DrupalGsonFactory.getInstance().createGsonBuilder();

    drupalGsonBuilder.addSerializationExclusionStrategy(
      new BeforeAndAfterExclusionStrategy(existingEntity, updatedEntity, Entity.JAVA_BUNDLE_TYPE_FIELD_NAME));

    drupalGson = drupalGsonBuilder.create();

    return drupalGson.toJson(updatedEntity);
  }

  protected void updateEntity(Entity<?> entity)
  throws IOException, DrupalHttpException
  {
    URI     requestUri;
    String  entityJson;
    HttpPut request;
    Integer entityId;

    if (entity == null)
      throw new IllegalArgumentException("entity cannot be null.");

    entityId = entity.getId();

    if (entity.isNew())
      throw new IllegalArgumentException("entity cannot be new.");

    if (entityId == 0)
      throw new IllegalArgumentException("entity must have a non-zero id in order to be updated.");

    requestUri  = this.createUriForEntityId(entity.getEntityType(), entityId, true);
    request     = new HttpPut(requestUri);

    entityJson  = this.computeDifferenceOnlyJson(entity);

    request.setEntity(new StringEntity(entityJson));

    this.executeRequest(request).close();
  }

  protected void createEntity(Entity<?> entity, ExclusionStrategy... exclusionStrategies)
  throws IOException, DrupalHttpException
  {
    URI         requestUri;
    GsonBuilder drupalGsonBuilder = DrupalGsonFactory.getInstance().createGsonBuilder();
    Gson        drupalGson;
    String      entityJson;
    HttpPost    request;

    if (entity == null)
      throw new IllegalArgumentException("entity cannot be null.");

    if (!entity.isNew())
      throw new IllegalArgumentException("entity must be new.");

    requestUri  = this.createUriForEntity(entity.getEntityType(), true);
    request     = new HttpPost(requestUri);

    drupalGsonBuilder.setExclusionStrategies(exclusionStrategies);

    drupalGson  = drupalGsonBuilder.create();
    entityJson  = drupalGson.toJson(entity);

    System.out.println(entityJson);

    request.setEntity(new StringEntity(entityJson));

    try (InputStream  responseStream        = this.executeRequest(request);
         Reader       responseStreamReader  = new InputStreamReader(responseStream))
    {
      Entity<?> updatedEntity = drupalGson.fromJson(responseStreamReader, entity.getClass());

      entity.setId(updatedEntity.getId());
    }
  }

  protected URI createUriForEntityCriterion(String entityType, String criterion, Object value)
  {
    return this.createUriForEntityCriteria(entityType, Collections.singletonMap(criterion, value));
  }

  protected URI createUriForEntityCriteria(String entityType, Map<String, Object> criteria)
  {
    return this.createUriForCriteria(
      this.getRelativeEndpointUriFragment(entityType, null, false),
      criteria);
  }

  protected URI createUriForEntity(String entityType, boolean forSave)
  {
    return this.createUriForEntityId(entityType, null, forSave);
  }

  protected URI createUriForEntityId(String entityType, Integer entityId, boolean forSave)
  {
    return this.createEndpointUri(
      this.getRelativeEndpointUriFragment(entityType, entityId, forSave));
  }

  protected String getRelativeEndpointUriFragment(String entityType, Integer entityId, boolean forSave)
  {
    StringBuilder uriFragment = new StringBuilder("/");

    uriFragment.append(entityType.toLowerCase());

    if (entityId != null)
    {
      uriFragment.append('/');
      uriFragment.append(entityId);
    }

    // TODO: Find out why this seemingly hack-ish workaround is needed.
    if (!forSave)
      uriFragment.append(JSON_URI_SUFFIX);

    return uriFragment.toString();
  }
}