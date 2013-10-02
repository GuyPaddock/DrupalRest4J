package com.redbottledesign.bitcoin.pool.drupal;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.redbottledesign.drupal.Node;
import com.redbottledesign.drupal.User;

public class SolvedBlock
extends Node
{
  public static final String CONTENT_TYPE = "block";

  public static final String DRUPAL_FIELD_HEIGHT = "field_block_height";
  public static final String JAVA_FIELD_HEIGHT = "height";

  public static final String DRUPAL_FIELD_STATUS = "field_block_status";
  public static final String JAVA_FIELD_STATUS = "status";

  public static final String DRUPAL_FIELD_CREATION_TIME = "field_block_creation_time";
  public static final String JAVA_FIELD_CREATION_TIME = "creationTime";

  public static final String DRUPAL_FIELD_BLOCK_DIFFICULTY = "field_block_difficulty";
  public static final String JAVA_FIELD_BLOCK_DIFFICULTY = "difficulty";

  public static final String DRUPAL_FIELD_BLOCK_REWARD = "field_block_reward";
  public static final String JAVA_FIELD_BLOCK_REWARD = "blockReward";

  public static final String DRUPAL_FIELD_ROUND = "field_block_round";
  public static final String JAVA_FIELD_ROUND = "round";

  public static final String DRUPAL_FIELD_SOLVING_MEMBER = "field_block_solver";
  public static final String JAVA_FIELD_SOLVING_MEMBER = "solvingMember";

  @SerializedName(DRUPAL_FIELD_HEIGHT)
  private long height;

  @SerializedName(DRUPAL_FIELD_STATUS)
  private SolvedBlock.Status status;

  @SerializedName(DRUPAL_FIELD_CREATION_TIME)
  private Date creationTime;

  @SerializedName(DRUPAL_FIELD_BLOCK_DIFFICULTY)
  private long difficulty;

  @SerializedName(DRUPAL_FIELD_BLOCK_REWARD)
  private BigDecimal blockReward;

  @SerializedName(DRUPAL_FIELD_ROUND)
  private Node.Reference round;

  @SerializedName(DRUPAL_FIELD_SOLVING_MEMBER)
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