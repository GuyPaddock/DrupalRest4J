package com.redbottledesign.drupal.gson.exception;

import java.net.URI;

import org.apache.http.StatusLine;

public class DrupalAuthenticationFailedException
extends DrupalAuthenticationException
{
  public DrupalAuthenticationFailedException(URI resourceUri, StatusLine statusLine)
  {
    super(
      String.format(
        "Authentication with Drupal failed when acquiring token from endpoint '%s': %s",
        resourceUri,
        statusLine),
      resourceUri,
      statusLine);
  }

  public DrupalAuthenticationFailedException(String message, URI resourceUri, StatusLine statusLine)
  {
    super(message, resourceUri, statusLine);
  }
}
