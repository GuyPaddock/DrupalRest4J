package com.redbottledesign.drupal.gson;

import java.net.URI;

public class DrupalConsumer
{
  protected URI drupalSiteUri;

  public DrupalConsumer(URI drupalSiteUri)
  {
    this.setDrupalSiteUri(drupalSiteUri);
  }

  public URI getDrupalSiteUri()
  {
    return this.drupalSiteUri;
  }

  protected void setDrupalSiteUri(URI drupalSiteUri)
  {
    this.drupalSiteUri = drupalSiteUri;
  }
}