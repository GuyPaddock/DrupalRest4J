package com.redbottledesign.drupal;
import java.net.URI;

import com.google.gson.annotations.SerializedName;

public abstract class Entity
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

  public abstract Entity.Reference<? extends Entity> asReference();

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

  public static abstract class Reference<T extends Entity>
  {
    public static final String DRUPAL_ENTITY_TYPE_FIELD = "resource";
    public static final String JAVA_ENTITY_TYPE_FIELD = "entityType";

    private Integer id;

    @SerializedName(DRUPAL_ENTITY_TYPE_FIELD)
    private String entityType;

    private URI uri;

    public Reference(String entityType)
    {
      this(entityType, null);
    }

    public Reference(String entityType, Integer id)
    {
      this.setEntityType(entityType);
      this.setId(id);
    }

    public String getEntityType()
    {
      return this.entityType;
    }

    public Integer getId()
    {
      return this.id;
    }

    public void setId(Integer id)
    {
      this.id = id;
    }

    public URI getUri()
    {
      return this.uri;
    }

    public void setUri(URI uri)
    {
      this.uri = uri;
    }

    protected void setEntityType(String entityType)
    {
      this.entityType = entityType;
    }

    @Override
    public String toString()
    {
      return this.getClass().getSimpleName()  + " [" +
             "id="          + this.id         + ", " +
             "entityType="  + this.entityType + ", " +
             "uri="         + this.uri        +
             "]";
    }
  }
}