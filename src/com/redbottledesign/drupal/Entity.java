package com.redbottledesign.drupal;
import java.net.URL;

import com.google.gson.annotations.SerializedName;

public class Entity
{
  public static final String JAVA_BUNDLE_TYPE_FIELD_NAME = "bundleType";
  public static final String DRUPAL_BUNDLE_TYPE_FIELD_NAME = "type";

  public static final String JAVA_ID_FIELD_NAME = "id";

  private transient final String entityType;

  @SerializedName(DRUPAL_BUNDLE_TYPE_FIELD_NAME)
  private String bundleType;

  private int id;

  private URL url;

  public Entity(String entityType)
  {
    this.entityType = entityType;
  }

  public String getEntityType()
  {
    return this.entityType;
  }

  public String getBundleType()
  {
    return this.bundleType;
  }

  public int getId()
  {
    return this.id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public URL getUrl()
  {
    return this.url;
  }

  public void setUrl(URL url)
  {
    this.url = url;
  }

  protected void setBundleType(String bundleType)
  {
    this.bundleType = bundleType;
  }

  @Override
  public String toString()
  {
    return "Entity [" +
           "entityType="  + this.entityType + ", " +
    		   "bundleType="  + this.bundleType + ", " +
           "id="          + this.id         + ", " +
  		   	 "url="         + this.url        +
  		   	 "]";
  }
}