package com.redbottledesign.bitcoin.pool.drupal.gson.requestor;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.redbottledesign.bitcoin.pool.drupal.Round;
import com.redbottledesign.drupal.gson.JsonEntityResultList;
import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.requestor.NodeRequestor;

public class RoundRequestor
extends NodeRequestor<Round>
{
  public RoundRequestor(SessionManager sessionManager)
  {
    super(sessionManager);
  }

  @Override
  protected Type getListResultType()
  {
    return new TypeToken<JsonEntityResultList<Round>>(){}.getType();
  }

  @Override
  protected Class<Round> getNodeType()
  {
    return Round.class;
  }
}
