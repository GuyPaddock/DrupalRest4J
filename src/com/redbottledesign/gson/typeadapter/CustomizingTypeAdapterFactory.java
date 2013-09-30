package com.redbottledesign.gson.typeadapter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Based on:
 * http://stackoverflow.com/questions/11271375/gson-custom-seralizer-for-one-variable-of-many-in-an-object-using-typeadapter
 *
 * @author gpaddock
 *
 * @param <C>
 */
public abstract class CustomizingTypeAdapterFactory<C>
implements TypeAdapterFactory
{
  private final Class<?> customizedClass;

  public CustomizingTypeAdapterFactory(Class<C> customizedClass)
  {
    this.customizedClass = customizedClass;
  }

  @Override
  @SuppressWarnings("unchecked")
  public final <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type)
  {
    TypeAdapter<T> result = null;

    if (customizedClass.isAssignableFrom(type.getRawType()))
    {
      TypeAdapter<T>            defaultDelegateAdapter  = gson.getDelegateAdapter(this, type);
      TypeAdapter<JsonElement>  elementAdapter          = gson.getAdapter(JsonElement.class);

      result =
        new CustomizingClassAdapter<T>((CustomizingTypeAdapterFactory<T>)this, defaultDelegateAdapter, elementAdapter);
    }

    return result;
  }

  protected JsonElement beforeWrite(C sourceElement, JsonElement targetElement)
  {
    return targetElement;
  }

  protected JsonElement afterRead(JsonElement parsedElement)
  {
    return parsedElement;
  }

  protected class CustomizingClassAdapter<T>
  extends TypeAdapter<T>
  {
    private final CustomizingTypeAdapterFactory<T> factory;
    private final TypeAdapter<T> defaultDelegateAdapter;
    private final TypeAdapter<JsonElement> elementAdapter;

    public CustomizingClassAdapter(CustomizingTypeAdapterFactory<T> factory, TypeAdapter<T> defaultDelegateAdapter,
                                   TypeAdapter<JsonElement> elementAdapter)
    {
      this.factory                = factory;
      this.defaultDelegateAdapter = defaultDelegateAdapter;
      this.elementAdapter         = elementAdapter;
    }

    @Override
    public void write(JsonWriter out, T value)
    throws IOException
    {
      JsonElement tree = this.defaultDelegateAdapter.toJsonTree(value);

      tree = this.factory.beforeWrite(value, tree);

      this.elementAdapter.write(out, tree);
    }

    @Override
    public T read(JsonReader in)
    throws IOException
    {
      JsonElement tree = this.elementAdapter.read(in);

      tree = this.factory.afterRead(tree);

      return this.defaultDelegateAdapter.fromJsonTree(tree);
    }
  }
}