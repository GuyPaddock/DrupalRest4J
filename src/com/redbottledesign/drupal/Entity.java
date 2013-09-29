package com.redbottledesign.drupal;
import java.net.URL;

public class Entity
{
  public static final String ID_FIELD_NAME = "id";

  private int id;
  private URL url;

  public int getId()
  {
    return this.id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public URL getUrl()
  {
    return this.url;
  }

  public void setUrl(URL url)
  {
    this.url = url;
  }

  @Override
  public String toString()
  {
    return "Entity [" +
    		   "id="  + this.id  + ", " +
  		   	 "url=" + this.url +
  		   	 "]";
  }
}