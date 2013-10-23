package com.redbottledesign.drupal.gson.requestor;

import java.io.IOException;

import org.apache.http.HttpRequest;

import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;

public abstract class SessionBasedHttpRequestor
extends HttpRequestor
{
  private SessionManager sessionManager;

  public SessionBasedHttpRequestor(SessionManager sessionManager)
  {
    super(sessionManager.getDrupalSiteUri());

    this.setSessionManager(sessionManager);
  }

  public SessionManager getSessionManager()
  {
    return this.sessionManager;
  }

  public void setSessionManager(SessionManager sessionManager)
  {
    this.sessionManager = sessionManager;
  }

  @Override
  protected void preprocessRequest(HttpRequest request)
  throws DrupalHttpException, IOException
  {
    super.preprocessRequest(request);

    // Add the session token to the request before it is sent.
    this.sessionManager.addSessionToRequest(request);
  }
}
