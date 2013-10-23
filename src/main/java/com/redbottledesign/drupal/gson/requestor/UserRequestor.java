package com.redbottledesign.drupal.gson.requestor;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.redbottledesign.drupal.User;
import com.redbottledesign.drupal.gson.JsonEntityResultList;
import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;

public class UserRequestor
extends EntityRequestor<User>
{
  public UserRequestor(SessionManager sessionManager)
  {
    super(sessionManager);
  }

  public User requestUserByUid(int userId)
  throws IOException, DrupalHttpException
  {
    return this.requestEntityById(userId, User.ENTITY_TYPE, User.class);
  }

  public User requestUserByUsername(String userName)
  throws IOException, DrupalHttpException
  {
    return this.requestEntityByCriterion(User.ENTITY_TYPE, User.DRUPAL_FIELD_NAME, userName);
  }

  @Override
  protected Type getListResultType()
  {
    return new TypeToken<JsonEntityResultList<User>>(){}.getType();
  }
}
