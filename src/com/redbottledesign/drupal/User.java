package com.redbottledesign.drupal;
import java.net.URI;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class User
extends Entity
{
  public static final String ENTITY_TYPE = "user";
  public static final String ID_FIELD_NAME = "uid";

  private String name;

  @SerializedName("mail")
  private URI emailAddress;

  @SerializedName("created")
  private Date dateCreated;

  @SerializedName("last_login")
  private Date dateLastLogin;

  @SerializedName("status")
  private boolean isActive;

  public User()
  {
    super(ENTITY_TYPE);
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public URI getEmailAddress()
  {
    return this.emailAddress;
  }

  public void setEmailAddress(URI emailAddress)
  {
    this.emailAddress = emailAddress;
  }

  public Date getDateCreated()
  {
    return this.dateCreated;
  }

  public void setDateCreated(Date dateCreated)
  {
    this.dateCreated = dateCreated;
  }

  public Date getDateLastLogin()
  {
    return this.dateLastLogin;
  }

  public void setDateLastLogin(Date dateLastLogin)
  {
    this.dateLastLogin = dateLastLogin;
  }

  public boolean isActive()
  {
    return this.isActive;
  }

  public void setActive(boolean isActive)
  {
    this.isActive = isActive;
  }

  @Override
  public String toString()
  {
    return "User ["         +
    		   "id="            + getId()       + ", " +
  		   	 "url="           + getUrl()      + ", " +
           "name="          + name          + ", " +
           "emailAddress="  + emailAddress  + ", " +
           "dateCreated="   + dateCreated   + ", " +
           "dateLastLogin=" + dateLastLogin + ", " +
           "isActive="      + isActive      +
           "]";
  }


}