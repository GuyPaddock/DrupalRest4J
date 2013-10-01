package com.redbottledesign.bitcoin.pool.drupal.gson.requestor;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.redbottledesign.bitcoin.pool.drupal.WittyRemark;
import com.redbottledesign.drupal.gson.JsonEntityResultList;
import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.requestor.NodeRequestor;

public class WittyRemarkRequestor
extends NodeRequestor<WittyRemark>
{
  public WittyRemarkRequestor(SessionManager sessionManager)
  {
    super(sessionManager);
  }

  @Override
  protected Type getListResultType()
  {
    return new TypeToken<JsonEntityResultList<WittyRemark>>(){}.getType();
  }

  @Override
  protected Class<WittyRemark> getNodeType()
  {
    return WittyRemark.class;
  }
}
