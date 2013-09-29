package com.redbottledesign.drupal;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Node
extends Entity
{
  public static final String ID_FIELD_NAME = "nid";
  public static final String TYPE_FIELD_NAME = "type";

  @SerializedName("vid")
  private int revisionId;

  private String type;

  private String title;

  private String language;

  @SerializedName("status")
  private boolean published;

  @SerializedName("promote")
  private boolean promoted;

  private boolean sticky;

  @SerializedName("created")
  private Date dateCreated;

  @SerializedName("changed")
  private Date dateChanged;

  private User author;

  public int getRevisionId()
  {
    return this.revisionId;
  }

  public void setRevisionId(int revisionId)
  {
    this.revisionId = revisionId;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
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

  public User getAuthor()
  {
    return this.author;
  }

  public void setAuthor(User author)
  {
    this.author = author;
  }

  @Override
  public String toString()
  {
    return "Node ["       +
           "id="          + this.getId()      + ", " +
           "url="         + this.getUrl()     + ", " +
           "revisionId="  + this.revisionId   + ", " +
           "type="        + this.type         + ", " +
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
}