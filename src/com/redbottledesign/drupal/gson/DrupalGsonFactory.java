package com.redbottledesign.drupal.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redbottledesign.drupal.Node;
import com.redbottledesign.drupal.User;
import com.redbottledesign.drupal.gson.typeadapter.BooleanAdapterFactory;
import com.redbottledesign.drupal.gson.typeadapter.EntityTypeAdapterFactory;
import com.redbottledesign.util.gson.UnixDateAdapterFactory;

public class DrupalGsonFactory
{
  protected static final DrupalGsonFactory INSTANCE = new DrupalGsonFactory();

  public static DrupalGsonFactory getInstance()
  {
    return INSTANCE;
  }

  public Gson createGson()
  {
    Gson gson =
        new GsonBuilder()
              .registerTypeAdapterFactory(new EntityTypeAdapterFactory<Node>(Node.class, Node.ID_FIELD_NAME))
              .registerTypeAdapterFactory(new EntityTypeAdapterFactory<User>(User.class, User.ID_FIELD_NAME))
              .registerTypeAdapterFactory(new UnixDateAdapterFactory())
              .registerTypeAdapterFactory(new BooleanAdapterFactory())
              .create();

    return gson;
  }
}
