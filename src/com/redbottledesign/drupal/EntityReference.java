package com.redbottledesign.drupal;

import java.net.URI;

import com.google.gson.annotations.SerializedName;

public abstract class EntityReference<T extends Entity<T>>
{
  public static final String DRUPAL_ENTITY_TYPE_FIELD = "resource";
  public static final String JAVA_ENTITY_TYPE_FIELD = "entityType";

  private Integer id;

  @SerializedName(DRUPAL_ENTITY_TYPE_FIELD)
  private String entityType;

  private URI uri;

  public EntityReference(String entityType)
  {
    this(entityType, null);
  }

  public EntityReference(String entityType, Integer id)
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
