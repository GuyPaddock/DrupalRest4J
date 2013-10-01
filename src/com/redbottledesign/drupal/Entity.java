package com.redbottledesign.drupal;
import java.net.URI;

import com.google.gson.annotations.SerializedName;

public abstract class Entity<T extends Entity<T>>
{
  public static final String JAVA_BUNDLE_TYPE_FIELD_NAME = "bundleType";
  public static final String DRUPAL_BUNDLE_TYPE_FIELD_NAME = "type";

  public static final String JAVA_ID_FIELD_NAME = "id";

  private transient final String entityType;

  private Integer id;

  @SerializedName(DRUPAL_BUNDLE_TYPE_FIELD_NAME)
  private String bundleType;

  private URI uri;

  public Entity(String entityType)
  {
    this.entityType = entityType;
  }

  public Integer getId()
  {
    return this.id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getEntityType()
  {
    return this.entityType;
  }

  public String getBundleType()
  {
    return this.bundleType;
  }

  public URI getUrl()
  {
    return this.uri;
  }

  public void setUrl(URI url)
  {
    this.uri = url;
  }

  public boolean isNew()
  {
    return (this.id == null);
  }

  protected void setBundleType(String bundleType)
  {
    this.bundleType = bundleType;
  }

  public abstract EntityReference<T> asReference();

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName()  + " [" +
           "id="          + this.id         + ", " +
           "entityType="  + this.entityType + ", " +
    		   "bundleType="  + this.bundleType + ", " +
  		   	 "uri="         + this.uri        +
  		   	 "]";
  }
}