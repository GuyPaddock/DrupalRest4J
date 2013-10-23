package com.redbottledesign.drupal.gson.requestor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redbottledesign.drupal.gson.DrupalConsumer;
import com.redbottledesign.drupal.gson.exception.DrupalAuthenticationRequiredException;
import com.redbottledesign.drupal.gson.exception.DrupalEndpointMissingException;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;

public abstract class HttpRequestor
extends DrupalConsumer
{
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestor.class);

  private static final String MIME_TYPE_JSON = "application/json";
  private static final String HTTP_HEADER_ACCEPT = "Accept";
  private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
  private static final String JSON_PARAM_SORT_DIRECTION = "direction";
  private static final String JSON_PARAM_SORT_NAME = "sort";

  public HttpRequestor(URI drupalSiteUri)
  {
    super(drupalSiteUri);
  }

  protected InputStream executeRequest(HttpUriRequest request)
  throws IOException, DrupalHttpException
  {
    InputStream fullResponseStream  = null;

    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build())
    {
      try (CloseableHttpResponse response           = this.executeRequest(request, httpClient, null);
           InputStream           responseStream     = response.getEntity().getContent();
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

    return fullResponseStream;
  }

  protected CloseableHttpResponse executeRequest(HttpUriRequest request, CloseableHttpClient httpClient,
                                                 HttpClientContext authContext)
  throws IOException, DrupalHttpException
  {
    CloseableHttpResponse response;
    StatusLine            statusLine;
    int                   statusCode;
    boolean               wasSuccessful = false;

    this.preprocessRequest(request);

    if (LOGGER.isDebugEnabled())
    {
      Header headers[] = request.getAllHeaders();

      LOGGER.debug("executeRequest() - request: -------------------");
      LOGGER.debug(request.getRequestLine().toString());

      for (Header header : headers){
        LOGGER.debug(header.getName() + ": " + header.getValue());
      }
    }

    /* NOTE: Not try-with-resources because the caller needs it open, except if
     * an exception is thrown.
     */
    response = httpClient.execute(request, authContext);

    try
    {
      statusLine  = response.getStatusLine();
      statusCode  = statusLine.getStatusCode();

      // 2XX is success
      if (!String.valueOf(statusCode).matches("2[0-9]{2}"))
      {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent())))
        {
          String line;

          while ((line = reader.readLine()) != null)
          {
            System.out.println(" >> " + line);
          }
        }

        switch (statusCode)
        {
          /* 403 Authorization Required */
          case 403:
            throw new DrupalAuthenticationRequiredException(request.getURI(), statusLine);

          /* 404 File Not Found */
          case 404:
            throw new DrupalEndpointMissingException(request.getURI(), statusLine);

          /* All other errors. */
          default:
            throw new DrupalHttpException(request.getURI(), statusLine, response);
        }
      }

      wasSuccessful = true;
    }

    finally
    {
      if (!wasSuccessful)
        response.close();
    }

    return response;
  }

  protected void preprocessRequest(HttpRequest request)
  throws DrupalHttpException, IOException
  {
    request.addHeader(HTTP_HEADER_CONTENT_TYPE, MIME_TYPE_JSON);
    request.addHeader(HTTP_HEADER_ACCEPT, MIME_TYPE_JSON);
  }

  protected URI createUriForCriterion(String endpoint, String criterion, Object value)
  {
    return this.createUriForCriteria(endpoint, Collections.singletonMap(criterion, value), null, null);
  }

  protected URI createUriForCriteria(String endpoint, Map<String, Object> criteria)
  {
    return this.createUriForCriteria(endpoint, criteria, null, null);
  }

  protected URI createUriForCriteria(String endpoint, Map<String, Object> criteria, String sortName,
                                     SortOrder sortOrder)
  {
    StringBuilder       queryStringBuilder = new StringBuilder("?");
    boolean             isFirst            = true;
    Map<String, Object> queryStringParams  = new HashMap<>(criteria);

    if ((sortName == null) && (sortOrder != null))
      throw new IllegalArgumentException("sortName must be specified if sortOrder is specified.");

    if (sortName != null)
    {
      String sortOrderString = (sortOrder != null) ? sortOrder.getJsonValue() : SortOrder.ASCENDING.toString();

      queryStringParams.put(JSON_PARAM_SORT_NAME, sortName);
      queryStringParams.put(JSON_PARAM_SORT_DIRECTION, sortOrderString);
    }

    for (Entry<String, Object> criterionEntry : queryStringParams.entrySet())
    {
      String  criterionName   = criterionEntry.getKey(),
              encodedValue;
      Object  criterionValue  = criterionEntry.getValue();

      if (criterionName == null)
        throw new IllegalArgumentException("all criteria must have non-null keys.");

      if (criterionValue == null)
        throw new IllegalArgumentException("all criteria must have non-null values.");

      if (!isFirst)
        queryStringBuilder.append('&');

      else
        isFirst = false;

      try
      {
        encodedValue = URLEncoder.encode(criterionValue.toString(), StandardCharsets.UTF_8.name());
      }

      catch (UnsupportedEncodingException ex)
      {
        throw new IllegalArgumentException(
          "Bad criterion; '" + criterionName + "'='" + criterionValue + "': " + ex.getMessage());
      }

      queryStringBuilder.append(criterionName);
      queryStringBuilder.append('=');
      queryStringBuilder.append(encodedValue);
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