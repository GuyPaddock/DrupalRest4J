package com.redbottledesign.drupal.gson.exception;

import java.net.URI;

import org.apache.http.StatusLine;

public class DrupalHttpException
extends Exception
{
  private URI resourceUri;
  private StatusLine statusLine;

  public DrupalHttpException(URI resourceUri, StatusLine statusLine)
  {
    this(
      String.format("An HTTP error occurred while requesting the JSON end-point '%s': %s", resourceUri, statusLine),
      resourceUri,
      statusLine);
  }

  public DrupalHttpException(String message, URI resourceUri, StatusLine statusLine)
  {
    super(message);

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
}
