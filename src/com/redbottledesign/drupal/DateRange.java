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
  public int hashCode()
  {
    final int prime = 31;
          int result = 1;

    result = prime * result + ((duration == null) ? 0 : duration.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());

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

    DateRange other = (DateRange) obj;

    if (duration == null)
    {
      if (other.duration != null)
        return false;
    }
    else if (!duration.equals(other.duration))
      return false;

    if (endDate == null)
    {
      if (other.endDate != null)
        return false;
    }

    else if (!endDate.equals(other.endDate))
      return false;

    if (startDate == null)
    {
      if (other.startDate != null)
        return false;
    }

    else if (!startDate.equals(other.startDate))
      return false;

    return true;
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
