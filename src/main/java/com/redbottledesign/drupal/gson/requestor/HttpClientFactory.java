package com.redbottledesign.drupal.gson.requestor;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientFactory
{
    private static HttpClientFactory INSTANCE = new HttpClientFactory();

    private final PoolingHttpClientConnectionManager connectionManager;
    private CloseableHttpClient client;

    public static HttpClientFactory getInstance()
    {
        return INSTANCE;
    }

    public HttpClientFactory()
    {
        this.connectionManager  = new PoolingHttpClientConnectionManager();
        this.client             = HttpClients.custom().setConnectionManager(this.connectionManager).build();
    }

    public PoolingHttpClientConnectionManager getConnectionManager()
    {
        return this.connectionManager;
    }

    public CloseableHttpClient getClient()
    {
        return this.client;
    }

    protected void setClient(CloseableHttpClient client)
    {
        this.client = client;
    }
}