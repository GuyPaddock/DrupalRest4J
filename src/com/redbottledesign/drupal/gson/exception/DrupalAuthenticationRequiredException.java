package com.redbottledesign.drupal.gson.exception;

import java.net.URI;

import org.apache.http.StatusLine;

public class DrupalAuthenticationRequiredException
extends DrupalAuthenticationException
{
  public DrupalAuthenticationRequiredException(URI resourceUri, StatusLine statusLine)
  {
    super(
      String.format("Authentication with Drupal is required to access resource '%s': %s", resourceUri, statusLine),
      resourceUri,
      statusLine);
  }
}
