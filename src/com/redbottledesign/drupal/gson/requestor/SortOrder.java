package com.redbottledesign.drupal.gson.requestor;

public enum SortOrder
{
  ASCENDING ("ASC" ),
  DESCENDING("DESC");

  private final String jsonValue;

  public String getJsonValue()
  {
    return this.jsonValue;
  }

  private SortOrder(String jsonValue)
  {
    this.jsonValue = jsonValue;
  }
}