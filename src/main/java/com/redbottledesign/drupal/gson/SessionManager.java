package com.redbottledesign.drupal.gson;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpRequest;

import com.redbottledesign.drupal.gson.exception.DrupalHttpException;
import com.redbottledesign.drupal.gson.requestor.SessionRequestor;

public class SessionManager
extends DrupalConsumer
{
  private static final String HTTP_REQUEST_HEADER_CSRF_TOKEN = "X-CSRF-Token";
  private static final String HTTP_REQUEST_HEADER_SESSION_COOKIE = "Cookie";

  private SessionToken sessionToken;
  private SessionRequestor sessionRequestor;

  public SessionManager(URI drupalSiteUri, String userName, String password)
  {
    super(drupalSiteUri);

    this.setSessionToken(null);
    this.setSessionRequestor(new SessionRequestor(drupalSiteUri, userName, password));
  }

  public synchronized SessionToken getSessionToken()
  throws DrupalHttpException, IOException
  {
    if (this.sessionToken == null)
      this.refreshSessionToken();

    return this.sessionToken;
  }

  public synchronized void refreshSessionToken()
  throws DrupalHttpException, IOException
  {
    this.setSessionToken(this.sessionRequestor.requestSessionToken());
  }

  public void addSessionToRequest(HttpRequest request)
  throws DrupalHttpException, IOException
  {
    SessionToken sessionToken = this.getSessionToken();

    request.addHeader(HTTP_REQUEST_HEADER_SESSION_COOKIE, sessionToken.getSessionCookie());
    request.addHeader(HTTP_REQUEST_HEADER_CSRF_TOKEN,     sessionToken.getCsrfToken());
  }

  protected synchronized void setSessionToken(SessionToken sessionToken)
  {
    this.sessionToken = sessionToken;
  }

  protected synchronized DrupalConsumer getSessionRequestor()
  {
    return this.sessionRequestor;
  }

  protected synchronized void setSessionRequestor(SessionRequestor sessionRequestor)
  {
    this.sessionRequestor = sessionRequestor;
  }
}