package com.redbottledesign.drupal.gson.typeadapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.redbottledesign.drupal.Entity;
import com.redbottledesign.util.gson.CustomizingTypeAdapterFactory;

public class EntityTypeAdapterFactory<E extends Entity>
extends CustomizingTypeAdapterFactory<E>
{
  private String idFieldName;

  public EntityTypeAdapterFactory(Class<E> type, String idFieldName)
  {
    super(type);

    this.setIdFieldName(idFieldName);
  }

  public String getIdFieldName()
  {
    return this.idFieldName;
  }

  protected void setIdFieldName(String idFieldName)
  {
    this.idFieldName = idFieldName;
  }

  @Override
  protected JsonElement beforeWrite(Entity sourceElement, JsonElement targetElement)
  {
    if (targetElement.isJsonObject())
    {
      JsonObject targetJsonObj = targetElement.getAsJsonObject();

      // Swap "id" to be the appropriate field name for the type of entity (nid, uid, etc).
      targetJsonObj.remove(Entity.ID_FIELD_NAME);
      targetJsonObj.addProperty(this.idFieldName, sourceElement.getId());
    }

    return targetElement;
  }

  @Override
  protected JsonElement afterRead(JsonElement parsedElement)
  {
    JsonObject  parsedJsonObj = parsedElement.getAsJsonObject();
    JsonElement parsedId      = parsedJsonObj.get(this.idFieldName);

    if (parsedId != null)
    {
      // Swap the type-specific field name (nid, uid, etc) to being just "id" in Java.
      parsedJsonObj.add(Entity.ID_FIELD_NAME, parsedId);
      parsedJsonObj.remove(this.idFieldName);
    }

    return parsedJsonObj;
  }
}