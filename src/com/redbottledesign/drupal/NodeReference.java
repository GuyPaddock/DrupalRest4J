package com.redbottledesign.drupal;

public class NodeReference
extends EntityReference<Node>
{
  public NodeReference()
  {
    this(null);
  }

  public NodeReference(Integer id)
  {
    super(Node.ENTITY_TYPE, id);
  }
}
