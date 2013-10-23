package com.redbottledesign.drupal.gson.exception;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

public class DrupalHttpException
extends Exception
{
  private URI resourceUri;
  private StatusLine statusLine;

  public DrupalHttpException(URI resourceUri, StatusLine statusLine, HttpResponse response)
  {
    this(
      String.format(
        "An HTTP error occurred while requesting the JSON end-point '%s': %s %s",
        resourceUri,
        statusLine,
        getAllResponseLines(response)),
      resourceUri,
      statusLine);
  }

  public DrupalHttpException(String message, URI resourceUri, StatusLine statusLine)
  {
    this(message, resourceUri, statusLine, null);
  }

  public DrupalHttpException(String message, URI resourceUri, StatusLine statusLine, Throwable cause)
  {
    super(message, cause);

    this.setResourceUri(resourceUri);
    this.setStatusLine(statusLine);
  }

  public URI getResourceUri()
  {
    return this.resourceUri;
  }

  public StatusLine getStatusLine()
  {
    return this.statusLine;
  }

  protected void setResourceUri(URI resourceUri)
  {
    this.resourceUri = resourceUri;
  }

  protected void setStatusLine(StatusLine statusLine)
  {
    this.statusLine = statusLine;
  }

  protected static String getAllResponseLines(HttpResponse response)
  {
    StringBuffer responseLines = new StringBuffer();

    try
    {
      InputStream     responseStream = response.getEntity().getContent();
      BufferedReader  responseReader = new BufferedReader(new InputStreamReader(responseStream));
      String          line;

      while ((line = responseReader.readLine()) != null)
      {
        responseLines.append(line + "\n");
      }
    }

    catch (Exception e)
    {
      // Suppressed, since we're in the middle of an exception handler.
    }

    return responseLines.toString();
  }
}
