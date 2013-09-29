package com.redbottledesign.drupal.gson.type;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class BooleanAdapterFactory
implements TypeAdapterFactory
{
  @Override
  @SuppressWarnings("unchecked")
  public final <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type)
  {
    TypeAdapter<T>    result = null;
    Class<? super T>  rawType = type.getRawType();

    if (Boolean.class.isAssignableFrom(rawType) || boolean.class.isAssignableFrom(rawType))
    {
      result = (TypeAdapter<T>)new BooleanTypeAdapter(gson.getAdapter(JsonElement.class));
    }

    return result;
  }

  protected static class BooleanTypeAdapter
  extends TypeAdapter<Boolean>
  {
    private static final String BOOLEAN_FALSE = "0";
    private static final String BOOLEAN_TRUE = "1";

    TypeAdapter<JsonElement>  elementAdapter;

    public BooleanTypeAdapter(TypeAdapter<JsonElement> elementAdapter)
    {
      this.elementAdapter = elementAdapter;
    }

    @Override
    public void write(JsonWriter out, Boolean value)
    throws IOException
    {
      if (out == null)
        throw new IllegalArgumentException("out cannot be null.");

      if (value != null)
      {
        JsonElement jsonValue = new JsonPrimitive(value.equals(Boolean.TRUE) ? BOOLEAN_TRUE : BOOLEAN_FALSE);

        this.elementAdapter.write(out, jsonValue);
      }

      else
      {
        this.elementAdapter.write(out, JsonNull.INSTANCE);
      }
    }

    @Override
    public Boolean read(JsonReader in)
    throws IOException
    {
      JsonElement   readValue;
      JsonPrimitive primitiveValue;

      if (in == null)
        throw new IllegalArgumentException("in cannot be null.");

      readValue       = this.elementAdapter.read(in);
      primitiveValue  = readValue.getAsJsonPrimitive();

      return (BOOLEAN_TRUE.equals(primitiveValue.getAsString()) ? Boolean.TRUE : Boolean.FALSE);
    }
  }
}