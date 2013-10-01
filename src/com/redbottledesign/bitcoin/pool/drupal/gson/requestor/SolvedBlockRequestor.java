package com.redbottledesign.bitcoin.pool.drupal.gson.requestor;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.redbottledesign.bitcoin.pool.drupal.SolvedBlock;
import com.redbottledesign.drupal.gson.JsonEntityResultList;
import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.requestor.NodeRequestor;

public class SolvedBlockRequestor
extends NodeRequestor<SolvedBlock>
{
  public SolvedBlockRequestor(SessionManager sessionManager)
  {
    super(sessionManager);
  }

  @Override
  protected Type getListResultType()
  {
    return new TypeToken<JsonEntityResultList<SolvedBlock>>(){}.getType();
  }

  @Override
  protected Class<SolvedBlock> getNodeType()
  {
    return SolvedBlock.class;
  }
}
