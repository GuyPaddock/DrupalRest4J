package com.redbottledesign.bitcoin.pool.drupal;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.redbottledesign.drupal.Node;
import com.redbottledesign.drupal.User;

public class SolvedBlock
extends Node
{
  private static final String CONTENT_TYPE = "block";

  @SerializedName("field_block_height")
  private long height;

  @SerializedName("field_block_status")
  private SolvedBlock.Status status;

  @SerializedName("field_block_creation_time")
  private Date creationTime;

  @SerializedName("field_block_difficulty")
  private long difficulty;

  @SerializedName("field_block_reward")
  private BigDecimal blockReward;

  @SerializedName("field_block_round")
  private Node.Reference round;

  @SerializedName("field_block_solver")
  private User.Reference solvingMember;

  public SolvedBlock()
  {
    super(CONTENT_TYPE);
  }

  public long getHeight()
  {
    return this.height;
  }

  public void setHeight(long height)
  {
    this.height = height;
  }

  public SolvedBlock.Status getStatus()
  {
    return this.status;
  }

  public void setStatus(SolvedBlock.Status status)
  {
    this.status = status;
  }

  public Date getCreationTime()
  {
    return this.creationTime;
  }

  public void setCreationTime(Date creationTime)
  {
    this.creationTime = creationTime;
  }

  public long getDifficulty()
  {
    return this.difficulty;
  }

  public void setDifficulty(long difficulty)
  {
    this.difficulty = difficulty;
  }

  public BigDecimal getBlockReward()
  {
    return this.blockReward;
  }

  public void setBlockReward(BigDecimal blockReward)
  {
    this.blockReward = blockReward;
  }

  public Node.Reference getRound()
  {
    return this.round;
  }

  public void setRound(Node.Reference round)
  {
    this.round = round;
  }

  public User.Reference getSolvingMember()
  {
    return this.solvingMember;
  }

  public void setSolvingMember(User.Reference solvingMember)
  {
    this.solvingMember = solvingMember;
  }

  @Override
  public String toString()
  {
    return this.getClass().getSimpleName()          + " [" +
           "id="            + this.getId()          + ", " +
           "url="           + this.getUrl()         + ", " +
           "hash="          + this.getTitle()       + ", " +
           "height="        + this.height           + ", " +
           "status="        + this.status           + ", " +
           "creationTime="  + this.creationTime     + ", " +
           "difficulty="    + this.difficulty       + ", " +
           "blockReward="   + this.blockReward      + ", " +
           "round="         + this.round            + ", " +
           "solvingMember=" + this.solvingMember    + ", " +
           "published="     + this.isPublished()    + ", " +
           "dateCreated="   + this.getDateCreated() + ", " +
           "dateChanged="   + this.getDateChanged() +
           "]";
  }

  public static enum Status
  {
    @SerializedName("0")
    UNCONFIRMED,

    @SerializedName("1")
    CONFIRMED
  }
}
