package com.redbottledesign.bitcoin.pool.drupal;

import com.google.gson.annotations.SerializedName;
import com.redbottledesign.drupal.DateRange;
import com.redbottledesign.drupal.Node;

public class Round
extends Node
{
  private static final String CONTENT_TYPE = "round";

  public static final String DRUPAL_FIELD_ROUND_STATUS = "field_round_status";
  public static final String JAVA_FIELD_ROUND_STATUS = "roundStatus";

  public static final String DRUPAL_FIELD_ROUND_DURATION = "field_round_start_end";
  public static final String JAVA_FIELD_ROUND_DURATION = "roundDuration";

  @SerializedName(DRUPAL_FIELD_ROUND_STATUS)
  private Round.Status roundStatus;

  @SerializedName(DRUPAL_FIELD_ROUND_DURATION)
  private DateRange roundDuration;

  public Round()
  {
    super(CONTENT_TYPE);

    this.setRoundDuration(new DateRange());
  }

  public Round.Status getRoundStatus()
  {
    return this.roundStatus;
  }

  public void setRoundStatus(Round.Status roundStatus)
  {
    this.roundStatus = roundStatus;
  }

  public DateRange getRoundDuration()
  {
    return this.roundDuration;
  }

  protected void setRoundDuration(DateRange roundDuration)
  {
    this.roundDuration = roundDuration;
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName()          + " [" +
           "id="            + this.getId()          + ", " +
           "url="           + this.getUrl()         + ", " +
           "roundStatus="   + this.roundStatus      + ", " +
           "roundDuration=" + this.roundDuration    + ", " +
           "published="     + this.isPublished()    + ", " +
           "dateCreated="   + this.getDateCreated() + ", " +
           "dateChanged="   + this.getDateChanged() +
           "]";
  }

  public static enum Status
  {
    @SerializedName("0")
    OPEN,

    @SerializedName("1")
    CLOSED
  }
}
