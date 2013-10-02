package com.redbottledesign.bitcoin.pool.drupal;

import com.google.gson.annotations.SerializedName;
import com.redbottledesign.drupal.Node;

public class WittyRemark
extends Node
{
  private static final String CONTENT_TYPE = "witty_remark";

  public static final String DRUPAL_FIELD_WAS_REMARK_USED = "field_witty_remark_used";
  public static final String JAVA_FIELD_WAS_REMARK_USED = "wasUsed";

  @SerializedName(DRUPAL_FIELD_WAS_REMARK_USED)
  private Boolean wasUsed;

  public WittyRemark()
  {
    super(CONTENT_TYPE);
  }

  public boolean wasUsed()
  {
    return this.wasUsed;
  }

  public void setWasUsed(boolean wasUsed)
  {
    this.wasUsed = wasUsed;
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName()        + " [" +
           "id="          + this.getId()          + ", " +
           "url="         + this.getUrl()         + ", " +
           "revisionId="  + this.getRevisionId()  + ", " +
           "title="       + this.getTitle()       + ", " +
           "wasUsed="     + this.wasUsed          + ", " +
           "published="   + this.isPublished()    + ", " +
           "dateCreated=" + this.getDateCreated() + ", " +
           "dateChanged=" + this.getDateChanged() + ", " +
           "author="      + this.getAuthor()      +
           "]";
  }
}