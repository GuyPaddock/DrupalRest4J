package com.redbottledesign.drupal.gson.exception;

import java.net.URI;

import org.apache.http.StatusLine;

@SuppressWarnings("serial")
public class DrupalAuthenticationException
extends DrupalHttpException
{
    public DrupalAuthenticationException(String message, URI resourceUri, StatusLine statusLine)
    {
        super(message, resourceUri, statusLine);
    }

    public DrupalAuthenticationException(String message, URI resourceUri, StatusLine statusLine, Throwable cause)
    {
        super(message, resourceUri, statusLine, cause);
    }
}
