package com.redbottledesign.drupal;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Node
extends Entity<Node>
{
  public static final String ENTITY_TYPE = "node";

  public static final String DRUPAL_ID_FIELD_NAME = "nid";

  public static final String DRUPAL_REVISION_ID_FIELD_NAME = "vid";
  public static final String JAVA_REVISION_ID_FIELD_NAME = "revisionId";

  public static final String DRUPAL_PUBLISHED_FIELD_NAME = "status";
  public static final String JAVA_PUBLISHED_FIELD_NAME = "published";

  public static final String DRUPAL_PROMOTED_FIELD_NAME = "promote";
  public static final String JAVA_PROMOTED_FIELD_NAME = "promoted";

  public static final String DRUPAL_STICKY_FIELD_NAME = "sticky";
  public static final String JAVA_STICKY_FIELD_NAME = "sticky";

  public static final String DRUPAL_DATE_CREATED_FIELD_NAME = "created";
  public static final String JAVA_DATE_CREATED_FIELD_NAME = "dateCreated";

  public static final String DRUPAL_DATE_CHANGED_FIELD_NAME = "changed";
  public static final String JAVA_DATE_CHANGED_FIELD_NAME = "dateChanged";

  @SerializedName(DRUPAL_REVISION_ID_FIELD_NAME)
  private Integer revisionId;

  private String title;

  private String language;

  @SerializedName(DRUPAL_PUBLISHED_FIELD_NAME)
  private boolean published;

  @SerializedName(DRUPAL_PROMOTED_FIELD_NAME)
  private boolean promoted;

  @SerializedName(DRUPAL_STICKY_FIELD_NAME)
  private boolean sticky;

  @SerializedName(DRUPAL_DATE_CREATED_FIELD_NAME)
  private Date dateCreated;

  @SerializedName(DRUPAL_DATE_CHANGED_FIELD_NAME)
  private Date dateChanged;

  private User.Reference author;

  public Node(String bundleType)
  {
    super(ENTITY_TYPE);

    this.setBundleType(bundleType);
    this.setPublished(true);
  }

  public Integer getRevisionId()
  {
    return this.revisionId;
  }

  public void setRevisionId(Integer revisionId)
  {
    this.revisionId = revisionId;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getLanguage()
  {
    return this.language;
  }

  public void setLanguage(String language)
  {
    this.language = language;
  }

  public boolean isPublished()
  {
    return this.published;
  }

  public void setPublished(boolean published)
  {
    this.published = published;
  }

  public boolean isPromoted()
  {
    return this.promoted;
  }

  public void setPromoted(boolean promoted)
  {
    this.promoted = promoted;
  }

  public boolean isSticky()
  {
    return this.sticky;
  }

  public void setSticky(boolean sticky)
  {
    this.sticky = sticky;
  }

  public Date getDateCreated()
  {
    return this.dateCreated;
  }

  public void setDateCreated(Date dateCreated)
  {
    this.dateCreated = dateCreated;
  }

  public Date getDateChanged()
  {
    return this.dateChanged;
  }

  public void setDateChanged(Date dateChanged)
  {
    this.dateChanged = dateChanged;
  }

  public User.Reference getAuthor()
  {
    return this.author;
  }

  public void setAuthor(User.Reference author)
  {
    this.author = author;
  }

  @Override
  public Node.Reference asReference()
  {
    return new Node.Reference(this.getId());
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName()    + " [" +
           "id="          + this.getId()      + ", " +
           "url="         + this.getUrl()     + ", " +
           "revisionId="  + this.revisionId   + ", " +
           "title="       + this.title        + ", " +
           "language="    + this.language     + ", " +
           "published="   + this.published    + ", " +
           "promoted="    + this.promoted     + ", " +
           "sticky="      + this.sticky       + ", " +
           "dateCreated=" + this.dateCreated  + ", " +
           "dateChanged=" + this.dateChanged  + ", " +
           "author="      + this.author       +
           "]";
  }

  public static class Reference
  extends Entity.Reference<Node>
  {
    public Reference()
    {
      this(null);
    }

    public Reference(Integer id)
    {
      super(Node.ENTITY_TYPE, id);
    }
  }
}