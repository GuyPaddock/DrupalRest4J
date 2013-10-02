package com.redbottledesign.drupal.gson.requestor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.redbottledesign.drupal.gson.SessionToken;
import com.redbottledesign.drupal.gson.exception.DrupalAuthenticationFailedException;
import com.redbottledesign.drupal.gson.exception.DrupalAuthenticationRequiredException;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;

public class SessionRequestor
extends HttpRequestor
{
  private static final String JSON_SESSION_ENDPOINT = "/restws/session/token";
  private static final String HTTP_RESPONSE_HEADER_SESSION_COOKIE = "Set-Cookie";

  private String userName;
  private String password;

  public SessionRequestor(URI drupalSiteUri, String userName, String password)
  {
    super(drupalSiteUri);

    this.setUserName(userName);
    this.setPassword(password);
  }

  public String getUserName()
  {
    return this.userName;
  }

  public String getPassword()
  {
    return this.password;
  }

  public SessionToken requestSessionToken()
  throws IOException, DrupalHttpException
  {
    SessionToken        sessionToken        = null;
    URI                 requestUri          = this.createEndpointUri(JSON_SESSION_ENDPOINT);
    CredentialsProvider credentialProvider  = new BasicCredentialsProvider();
    HttpClientContext   authContext         = this.setupAuthenticationHttpContext(requestUri, credentialProvider);
    HttpGet             request             = new HttpGet(requestUri);
    HttpClientBuilder   httpClientBuilder;

    httpClientBuilder = HttpClients.custom().setDefaultCredentialsProvider(credentialProvider);

    try (CloseableHttpClient    httpClient  = httpClientBuilder.build();
         CloseableHttpResponse  response    = this.executeRequest(request, httpClient, authContext))
    {
      StatusLine statusLine = response.getStatusLine();

      sessionToken = this.createSessionToken(requestUri, response, statusLine);
    }

    catch (DrupalAuthenticationRequiredException ex)
    {
      // Make exception more specific, since we tried to authenticate.
      throw new DrupalAuthenticationFailedException(requestUri, ex.getStatusLine(), ex);
    }

    return sessionToken;
  }

  protected void setUserName(String userName)
  {
    this.userName = userName;
  }

  protected void setPassword(String password)
  {
    this.password = password;
  }

  protected HttpClientContext setupAuthenticationHttpContext(URI requestUri, CredentialsProvider credentialProvider)
  {
    HttpHost            targetHost  = new HttpHost(requestUri.getHost(), requestUri.getPort(), requestUri.getScheme());
    AuthScope           authScope   = new AuthScope(targetHost);
    AuthCache           authCache   = new BasicAuthCache();
    BasicScheme         basicAuth   = new BasicScheme();
    HttpClientContext   context     = HttpClientContext.create();

    credentialProvider.setCredentials(authScope, new UsernamePasswordCredentials(this.userName, this.password));

    authCache.put(targetHost, basicAuth);
    context.setAuthCache(authCache);

    return context;
  }

  protected SessionToken createSessionToken(URI requestUri, CloseableHttpResponse response, StatusLine statusLine)
  throws DrupalAuthenticationFailedException, IOException
  {
    Header      cookieHeader    = response.getFirstHeader(HTTP_RESPONSE_HEADER_SESSION_COOKIE);
    HttpEntity  responseEntity  = response.getEntity();
    String      cookies,
                csrfToken;

    if ((cookieHeader == null) || cookieHeader.getValue().isEmpty())
    {
      throw new DrupalAuthenticationFailedException(
        String.format("No %s session header was returned.", HTTP_RESPONSE_HEADER_SESSION_COOKIE),
        requestUri,
        statusLine);
    }

    cookies = cookieHeader.getValue();

    try (InputStream        responseStream  = responseEntity.getContent();
         InputStreamReader  reader          = new InputStreamReader(responseStream);
         BufferedReader     bufferedReader  = new BufferedReader(reader))
    {
      csrfToken = bufferedReader.readLine();
    }

    if ((csrfToken == null) || (csrfToken.isEmpty()))
    {
      throw new DrupalAuthenticationFailedException(
        "No CSRF token was returned in the response.",
        requestUri,
        statusLine);
    }

    return new SessionToken(cookies, csrfToken);
  }

  @Override
  protected void preprocessRequest(HttpRequest request)
  throws DrupalHttpException, IOException
  {
    // Do nothing -- we want to control headers ourselves.
  }
}
