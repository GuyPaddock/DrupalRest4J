package com.redbottledesign.drupal.gson.requestor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Date;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.redbottledesign.drupal.Entity;
import com.redbottledesign.drupal.Node;
import com.redbottledesign.drupal.gson.DrupalGsonFactory;
import com.redbottledesign.drupal.gson.JsonEntityResultList;
import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;
import com.redbottledesign.drupal.gson.strategy.NewNodeExclusionStrategy;

public class NodeRequestor<N extends Node>
extends EntityRequestor<N>
{
    public NodeRequestor(SessionManager sessionManager)
    {
        super(sessionManager);
    }

    public N requestNodeByNid(int nodeId)
    throws IOException, DrupalHttpException
    {
        return this.requestEntityById(nodeId, Node.ENTITY_TYPE, this.getNodeType());
    }

    public List<N> requestNodesByType(String nodeType)
    throws IOException, DrupalHttpException
    {
        List<N> results     = null;
        URI     requestUri  = this.createUriForEntityCriterion(Node.ENTITY_TYPE, Entity.DRUPAL_BUNDLE_TYPE_FIELD_NAME, nodeType);

        try (InputStream responseStream = this.executeRequest(new HttpGet(requestUri));
             Reader      responseReader = new InputStreamReader(responseStream))
        {
            Type                    listType    = this.getListResultType();
            Gson                    drupalGson  = DrupalGsonFactory.getInstance().createGson();
            JsonEntityResultList<N> jsonResults = drupalGson.fromJson(responseReader, listType);

            if (jsonResults != null)
                results = jsonResults.getResults();
        }

        return results;
    }

    @Override
    public void saveNew(N node)
    throws IOException, DrupalHttpException
    {
        this.saveNew(node, new NewNodeExclusionStrategy());
    }

    @Override
    protected String computeDifferenceOnlyJson(N existingEntity, N updatedEntity)
    {
        final Date  existingDate        = existingEntity.getDateChanged();
        final Date  updatedDate         = updatedEntity.getDateChanged();
        final int   existingRevision    = existingEntity.getRevisionId();
        final int   updatedRevision     = updatedEntity.getRevisionId();

        if (existingDate.after(updatedDate))
        {
            throw new IllegalArgumentException(
                String.format(
                    "Entity being updated (entity type '%s') was modified remotely (date of existing entity is " +
                    "'%s'; date of updated (local) entity is '%s'.\n" +
                    "Existing entity contents: '%s'.\n" +
                    "Updated entity contents: '%s'",
                    existingEntity.getEntityType(),
                    existingDate,
                    updatedDate,
                    existingEntity,
                    updatedEntity));
        }

        if (existingRevision > updatedRevision)
        {
            throw new IllegalArgumentException(
                String.format(
                    "Entity being updated (entity type '%s') was modified remotely (revision ID of existing entity " +
                    "is %d; revision ID of updated (local) entity is %d.\n" +
                    "Existing entity contents: '%s'.\n" +
                    "Updated entity contents: '%s'",
                    existingEntity.getEntityType(),
                    existingRevision,
                    updatedRevision,
                    existingEntity,
                    updatedEntity));
        }

        return super.computeDifferenceOnlyJson(existingEntity, updatedEntity);
    }

    @Override
    protected Type getListResultType()
    {
        return new TypeToken<JsonEntityResultList<Node>>(){}.getType();
    }

    @SuppressWarnings("unchecked")
    protected Class<N> getNodeType()
    {
        return (Class<N>)Node.class;
    }
}