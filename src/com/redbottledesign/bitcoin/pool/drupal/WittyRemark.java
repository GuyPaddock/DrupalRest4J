package com.redbottledesign.bitcoin.pool.drupal;

import com.redbottledesign.drupal.Node;

public class WittyRemark
extends Node
{
  @Override
  public String toString()
  {
    return super.toString().replace("Node [", "WittyRemark [");
  }
}