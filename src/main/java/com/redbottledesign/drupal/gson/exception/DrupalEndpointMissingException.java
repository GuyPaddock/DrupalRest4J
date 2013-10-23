package com.redbottledesign.drupal.gson.exception;

import java.net.URI;

import org.apache.http.StatusLine;

public class DrupalEndpointMissingException
extends DrupalHttpException
{
  public DrupalEndpointMissingException(URI resourceUri, StatusLine statusLine)
  {
    super(
      String.format("The JSON end-point '%s' was not found on the Drupal site: %s", resourceUri, statusLine),
      resourceUri,
      statusLine);
  }
}
