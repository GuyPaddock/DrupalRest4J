package com.redbottledesign.drupal;

public class UserReference
extends EntityReference<User>
{
  public UserReference()
  {
    this(null);
  }

  public UserReference(Integer id)
  {
    super(User.ENTITY_TYPE, id);
  }
}
