package com.redbottledesign.drupal.gson;

import java.net.URL;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.redbottledesign.drupal.Entity;

public class JsonEntityResultList<T extends Entity>
{
  @SerializedName("self")
  private URL jsonUrl;

  @SerializedName("first")
  private URL pagedJsonFirstUrl;

  @SerializedName("last")
  private URL pagedJsonLastUrl;

  @SerializedName("list")
  private List<T> results;

  public URL getJsonUrl()
  {
    return this.jsonUrl;
  }

  public URL getPagedJsonFirstUrl()
  {
    return this.pagedJsonFirstUrl;
  }

  public URL getPagedJsonLastUrl()
  {
    return this.pagedJsonLastUrl;
  }

  public List<T> getResults()
  {
    return this.results;
  }

  protected void setJsonUrl(URL jsonUrl)
  {
    this.jsonUrl = jsonUrl;
  }

  protected void setPagedJsonFirstUrl(URL pagedJsonFirstUrl)
  {
    this.pagedJsonFirstUrl = pagedJsonFirstUrl;
  }

  protected void setPagedJsonLastUrl(URL pagedJsonLastUrl)
  {
    this.pagedJsonLastUrl = pagedJsonLastUrl;
  }

  protected void setResults(List<T> results)
  {
    this.results = results;
  }
}
