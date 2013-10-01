package com.redbottledesign.drupal.gson.strategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.redbottledesign.drupal.Node;

public class NewNodeExclusionStrategy
implements ExclusionStrategy
{
  private static final Set<String> EXCLUDED_FIELDS =
      Collections.unmodifiableSet(
        new HashSet<>(
            Arrays.asList(
              Node.JAVA_PUBLISHED_FIELD_NAME,
              Node.JAVA_PROMOTED_FIELD_NAME,
              Node.JAVA_STICKY_FIELD_NAME)));

  @Override
  public boolean shouldSkipField(FieldAttributes attributes)
  {
    boolean result =
        (Node.class.isAssignableFrom(attributes.getDeclaringClass()) &&
         (EXCLUDED_FIELDS.contains(attributes.getName())));

    return result;
  }

  @Override
  public boolean shouldSkipClass(Class<?> clazz)
  {
    return false;
  }
}
