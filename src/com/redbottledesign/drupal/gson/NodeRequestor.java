package com.redbottledesign.drupal.gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.redbottledesign.drupal.Node;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;

public class NodeRequestor
extends SessionBasedHttpRequestor
{
  private static final String ENTITY_TYPE_NODE = "node";
  private static final String JSON_ENDPOINT = "/node.json";

  public NodeRequestor(SessionManager sessionManager)
  {
    super(sessionManager);
  }

  public Node requestNodeByNid(int nodeId)
  throws IOException, DrupalHttpException
  {
    Node  result      = null;
    URI   requestUri  = this.createUriForEntityId(ENTITY_TYPE_NODE, nodeId);

    try (InputStream  responseStream = this.executeRequest(new HttpGet(requestUri));
         Reader       responseReader = new InputStreamReader(responseStream))
    {
      Gson drupalGson = DrupalGsonFactory.getInstance().createGson();

      result = drupalGson.fromJson(responseReader, Node.class);
    }

    return result;
  }

  public List<Node> requestNodesByType(String nodeType)
  throws IOException, DrupalHttpException
  {
    List<Node>  results     = null;
    URI         requestUri  = this.createUriForCriterion(JSON_ENDPOINT, Node.TYPE_FIELD_NAME, nodeType);

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
    URI     requestUri;
    Gson    drupalGson;
    String  nodeJson;
    HttpPut request;

    if (node == null)
      throw new IllegalArgumentException("node cannot be null.");

    if (node.getId() == 0)
      throw new IllegalArgumentException("node must have an id in order to be updated.");

    requestUri = this.createUriForEntityId(ENTITY_TYPE_NODE, node.getId());
    request    = new HttpPut(requestUri);

    drupalGson = DrupalGsonFactory.getInstance().createGson();
    nodeJson   = drupalGson.toJson(node);

    request.setEntity(new StringEntity(nodeJson));

    try (InputStream  responseStream = this.executeRequest(request);
         Reader       responseReader = new InputStreamReader(responseStream);
         BufferedReader bufferedReader = new BufferedReader(responseReader))
    {
      String line;

      while ((line = bufferedReader.readLine()) != null)
      {
        System.out.println(" >> " + line);
      }
    }
  }

  protected Type getListResultType()
  {
    return new TypeToken<JsonEntityResultList<Node>>(){}.getType();
  }
}