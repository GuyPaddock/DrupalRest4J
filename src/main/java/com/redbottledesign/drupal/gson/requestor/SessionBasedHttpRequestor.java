package com.redbottledesign.drupal.gson.requestor;

import java.io.IOException;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.exception.DrupalAuthenticationRequiredException;
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

    protected static void removeAllHeaders(HttpUriRequest request)
    {
        for (HeaderIterator iterator = request.headerIterator(); iterator.hasNext(); iterator.next())
        {
            // Remove the header
            iterator.remove();
        }
    }

    @Override
    protected void preprocessRequest(HttpRequest request)
    throws DrupalHttpException, IOException
    {
        super.preprocessRequest(request);

        // Add the session token to the request before it is sent.
        this.sessionManager.addSessionToRequest(request);
    }

    @Override
    protected CloseableHttpResponse executeRequest(HttpUriRequest request, CloseableHttpClient httpClient,
                                                   HttpClientContext authContext)
    throws IOException, DrupalHttpException
    {
        CloseableHttpResponse   result      = null; // Should never be returned null.
        boolean                 shouldRetry = false,
                                hasRetried  = false;

        do
        {
            try
            {
                result = super.executeRequest(request, httpClient, authContext);
            }

            catch (DrupalAuthenticationRequiredException ex)
            {
                if (!hasRetried)
                {
                    shouldRetry = true;
                    hasRetried  = true;

                    // Refresh session token and try again.
                    this.sessionManager.refreshSessionToken();

                    // Clear out stale session headers
                    removeAllHeaders(request);
                }

                else
                {
                    throw new DrupalHttpException(
                        "Failed to authenticate with Drupal even after refreshing session token: " + ex.getMessage(),
                        ex);
                }
            }
        }
        while (shouldRetry);

      return result;
    }
}