package com.redbottledesign.gson.typeadapter;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class UnixDateAdapterFactory
implements TypeAdapterFactory
{
  @Override
  @SuppressWarnings("unchecked")
  public final <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type)
  {
    TypeAdapter<T> result = null;

    if (Date.class.isAssignableFrom(type.getRawType()))
    {
      result = (TypeAdapter<T>)new UnixDateAdapter(gson.getAdapter(JsonElement.class));
    }

    return result;
  }

  protected static class UnixDateAdapter
  extends TypeAdapter<Date>
  {
    TypeAdapter<JsonElement>  elementAdapter;

    public UnixDateAdapter(TypeAdapter<JsonElement> elementAdapter)
    {
      this.elementAdapter = elementAdapter;
    }

    @Override
    public void write(JsonWriter out, Date value)
    throws IOException
    {
      if (out == null)
        throw new IllegalArgumentException("out cannot be null.");

      if (value != null)
      {
        JsonElement jsonValue = new JsonPrimitive(TimeUnit.SECONDS.convert(value.getTime(), TimeUnit.MILLISECONDS));

        this.elementAdapter.write(out, jsonValue);
      }

      else
      {
        this.elementAdapter.write(out, JsonNull.INSTANCE);
      }
    }

    @Override
    public Date read(JsonReader in)
    throws IOException
    {
      JsonElement readTime;
      Long        unixTime;
      Date        dateTime = null;

      if (in == null)
        throw new IllegalArgumentException("in cannot be null.");

      readTime = this.elementAdapter.read(in);

      if (readTime.isJsonPrimitive())
      {
        unixTime = readTime.getAsJsonPrimitive().getAsLong();
        dateTime = new Date(TimeUnit.MILLISECONDS.convert(unixTime, TimeUnit.SECONDS));
      }

      return dateTime;
    }
  }
}