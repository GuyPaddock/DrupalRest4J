package com.redbottledesign.drupal;
import java.net.URI;

import com.google.gson.annotations.SerializedName;

public abstract class Entity<T extends Entity<?>>
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

  public abstract Entity.Reference<T> asReference();

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

  public static abstract class Reference<T extends Entity<?>>
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
    public int hashCode()
    {
      final int prime = 31;
            int result = 1;

      result = prime * result + ((entityType == null) ? 0 : entityType.hashCode());
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      result = prime * result + ((uri == null) ? 0 : uri.hashCode());

      return result;
    }

    @Override
    public boolean equals(Object obj)
    {
      if (this == obj)
        return true;

      if (obj == null)
        return false;

      if (getClass() != obj.getClass())
        return false;

      Reference<?> other = (Reference<?>)obj;

      if (entityType == null)
      {
        if (other.entityType != null)
          return false;
      }

      else if (!entityType.equals(other.entityType))
        return false;

      if (id == null)
      {
        if (other.id != null)
          return false;
      }

      else if (!id.equals(other.id))
        return false;

      if (uri == null)
      {
        if (other.uri != null)
          return false;
      }

      else if (!uri.equals(other.uri))
        return false;

      return true;
    }

    @Override
    public String toString()
    {
      Class<?>  thisClass = this.getClass();
      String    enclosingClassName = thisClass.getEnclosingClass().getSimpleName(),
                thisClassName      = thisClass.getSimpleName();

      return enclosingClassName + "$" + thisClassName + " [" +
             "id="              + this.id             + ", " +
             "entityType="      + this.entityType     + ", " +
             "uri="             + this.uri            +
             "]";
    }
  }
}