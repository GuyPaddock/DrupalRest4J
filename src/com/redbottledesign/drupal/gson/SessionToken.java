package com.redbottledesign.drupal.gson;


class SessionToken
{
  private String sessionCookie;
  private String csrfToken;

  public SessionToken(String sessionCookie, String csrfToken)
  {
    this.setSessionCookie(sessionCookie);
    this.setCsrfToken(csrfToken);
  }

  public String getSessionCookie()
  {
    return this.sessionCookie;
  }

  public String getCsrfToken()
  {
    return this.csrfToken;
  }

  @Override
  public String toString()
  {
    return "SessionToken [sessionCookie=" + sessionCookie + ", csrfToken=" + csrfToken + "]";
  }

  protected void setSessionCookie(String sessionCookie)
  {
    this.sessionCookie = sessionCookie;
  }

  protected void setCsrfToken(String csrfToken)
  {
    this.csrfToken = csrfToken;
  }
}