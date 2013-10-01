package com.redbottledesign.drupal;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.gson.annotations.SerializedName;

public class DateRange
{
  @SerializedName("value")
  private Date startDate;

  @SerializedName("value2")
  private Date endDate;

  private Long duration;

  public Date getStartDate()
  {
    return this.startDate;
  }

  public void setStartDate(Date startDate)
  {
    this.startDate = startDate;

    this.calculateDuration();
  }

  public Date getEndDate()
  {
    return this.endDate;
  }

  public void setEndDate(Date endDate)
  {
    this.endDate = endDate;

    this.calculateDuration();
  }

  public Long getDuration()
  {
    return this.duration;
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName()  + " [" +
           "startDate=" + this.startDate    + ", " +
           "endDate="   + this.endDate      + ", " +
           "duration="  + this.duration     +
           "]";
  }

  protected void calculateDuration()
  {
    if ((this.startDate == null) || (this.endDate == null))
    {
      this.duration = null;
    }

    else
    {
      long resultInMilliseconds = this.endDate.getTime() - this.startDate.getTime();

      this.duration = TimeUnit.SECONDS.convert(resultInMilliseconds, TimeUnit.MILLISECONDS);
    }
  }
}
