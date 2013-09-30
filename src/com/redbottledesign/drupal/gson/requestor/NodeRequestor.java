package com.redbottledesign.drupal.gson.requestor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

import org.apache.http.client.methods.HttpGet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.redbottledesign.drupal.Node;
import com.redbottledesign.drupal.gson.DrupalGsonFactory;
import com.redbottledesign.drupal.gson.JsonEntityResultList;
import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;

public class NodeRequestor
extends EntityRequestor
{
  public NodeRequestor(SessionManager sessionManager)
  {
    super(sessionManager);
  }

  public Node requestNodeByNid(int nodeId)
  throws IOException, DrupalHttpException
  {
    return this.requestEntityById(nodeId, Node.ENTITY_TYPE, Node.class);
  }

  public List<Node> requestNodesByType(String nodeType)
  throws IOException, DrupalHttpException
  {
    List<Node>  results     = null;
    URI         requestUri  = this.createUriForEntityCriterion(Node.ENTITY_TYPE, Node.TYPE_FIELD_NAME, nodeType);

    try (InputStream  responseStream = this.executeRequest(new HttpGet(requestUri));
         Reader       responseReader = new InputStreamReader(responseStream))
    {
      Type                        listType    = this.getListResultType();
      Gson                        drupalGson  = DrupalGsonFactory.getInstance().createGson();
      JsonEntityResultList<Node>  jsonResults = drupalGson.fromJson(responseReader, listType);

      if (jsonResults != null)
        results = jsonResults.getResults();
    }

    return results;
  }

  public void updateNode(Node node)
  throws IOException, DrupalHttpException
  {
    this.updateEntity(node);
  }

  protected Type getListResultType()
  {
    return new TypeToken<JsonEntityResultList<Node>>(){}.getType();
  }
}