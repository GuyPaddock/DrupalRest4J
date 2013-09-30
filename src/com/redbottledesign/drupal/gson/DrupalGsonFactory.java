package com.redbottledesign.drupal.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redbottledesign.drupal.Node;
import com.redbottledesign.drupal.User;
import com.redbottledesign.drupal.gson.typeadapter.BooleanTypeAdapterFactory;
import com.redbottledesign.drupal.gson.typeadapter.EntityTypeAdapterFactory;
import com.redbottledesign.gson.typeadapter.UnixDateAdapterFactory;

public class DrupalGsonFactory
{
  private static final DrupalGsonFactory INSTANCE = new DrupalGsonFactory();

  public static DrupalGsonFactory getInstance()
  {
    return INSTANCE;
  }

  public Gson createGson()
  {
    return this.createGsonBuilder().create();
  }

  public GsonBuilder createGsonBuilder()
  {
    GsonBuilder gsonBuilder =
        new GsonBuilder()
              .registerTypeAdapterFactory(new EntityTypeAdapterFactory<Node>(Node.class, Node.ID_FIELD_NAME))
              .registerTypeAdapterFactory(new EntityTypeAdapterFactory<User>(User.class, User.ID_FIELD_NAME))
              .registerTypeAdapterFactory(new UnixDateAdapterFactory())
              .registerTypeAdapterFactory(new BooleanTypeAdapterFactory());

    return gsonBuilder;
  }
}
