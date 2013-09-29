package com.redbottledesign.drupal.gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.redbottledesign.drupal.gson.exception.DrupalAuthenticationRequiredException;
import com.redbottledesign.drupal.gson.exception.DrupalEndpointMissingException;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;

public abstract class HttpRequestor
{
  private URI drupalSiteUri;

  public HttpRequestor(URI drupalSiteUri)
  {
    this.setDrupalSiteUri(drupalSiteUri);
  }

  public URI getDrupalSiteUri()
  {
    return this.drupalSiteUri;
  }

  protected void setDrupalSiteUri(URI drupalSiteUri)
  {
    this.drupalSiteUri = drupalSiteUri;
  }

  protected InputStream executeRequest(HttpUriRequest request)
  throws IOException, DrupalHttpException
  {
    InputStream fullResponseStream  = null;

    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build())
    {
      StatusLine  statusLine;
      int         statusCode;

      this.preprocessRequest(request);

      try (CloseableHttpResponse response = httpClient.execute(request))
      {
        statusLine  = response.getStatusLine();
        statusCode  = statusLine.getStatusCode();

        if (statusCode != 200)
        {
          switch (statusCode)
          {
            /* Success */
            case 200:
              break;

            /* 403 Authorization Required */
            case 403:
              throw new DrupalAuthenticationRequiredException(request.getURI(), statusLine);

            /* 404 File Not Found */
            case 404:
              throw new DrupalEndpointMissingException(request.getURI(), statusLine);

            /* All other errors. */
            default:
              throw new DrupalHttpException(request.getURI(), statusLine);
          }
        }

        try (InputStream           responseStream     = response.getEntity().getContent();
             ByteArrayOutputStream outputStream       = new ByteArrayOutputStream();
             OutputStreamWriter    outputStreamWriter = new OutputStreamWriter(outputStream))
        {
          int readByte;

          // FIXME: This seems inefficient...
          while ((readByte = responseStream.read()) != -1)
          {
            outputStream.write(readByte);
          }

          fullResponseStream = new ByteArrayInputStream(outputStream.toByteArray());
        }
      }
    }

    return fullResponseStream;
  }

  protected void preprocessRequest(HttpRequest request)
  throws DrupalHttpException, IOException
  {
  }

  protected URI createUriForEntityId(String entityType, int entityId)
  {
    return this.createEndpointUri(String.format("/%s/%d.json", entityType.toLowerCase(), entityId));
  }

  protected URI createUriForCriterion(String endpoint, String criterion, Object value)
  {
    return this.createUriForCriteria(endpoint, Collections.singletonMap(criterion, value));
  }

  protected URI createUriForCriteria(String endpoint, Map<String, Object> criteria)
  {
    StringBuilder queryStringBuilder = new StringBuilder("?");
    boolean       isFirst            = true;

    for (Entry<String, Object> criterionEntry : criteria.entrySet())
    {
      if (!isFirst)
        queryStringBuilder.append('&');

      else
        isFirst = false;

      queryStringBuilder.append(criterionEntry.getKey());
      queryStringBuilder.append('=');
      queryStringBuilder.append(criterionEntry.getValue());
    }

    return this.createEndpointUri(endpoint, queryStringBuilder.toString());
  }

  protected URI createEndpointUri(String endpoint)
  {
    return this.createEndpointUri(endpoint, null);
  }

  protected URI createEndpointUri(String endpoint, String queryString)
  {
    String  relativeUrl = (queryString != null) ? (endpoint + queryString) : endpoint;

    return this.drupalSiteUri.resolve(relativeUrl);
  }
}
