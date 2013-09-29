package com.redbottledesign.drupal.gson.exception;

import java.net.URI;

import org.apache.http.StatusLine;

public class DrupalAuthenticationException
extends DrupalHttpException
{
  public DrupalAuthenticationException(String message, URI resourceUri, StatusLine statusLine)
  {
    super(message, resourceUri, statusLine);
  }
}
