package com.redbottledesign.gson;

import com.google.gson.annotations.SerializedName;

public final class GsonUtils
{
    private GsonUtils()
    {
    }

    public static <T extends Enum<T>> String getSerializedName(Class<T> type, T enumValue)
    {
        String  result,
                enumValueName = enumValue.name();

        try
        {
            result = GsonUtils.getSerializedName(type, enumValueName);
        }

        catch (NoSuchFieldException | SecurityException ex)
        {
            throw new RuntimeException(
                String.format(
                    "Unexpectedly failed to determine serialized name for '%s' value of '%s' enum: %s",
                    enumValueName,
                    ex.getMessage()),
                    ex);
        }

        return result;
    }

    public static String getSerializedName(Class<?> type, String fieldName)
    throws NoSuchFieldException, SecurityException
    {
        String          result      = null;
        SerializedName  annotation  = type.getField(fieldName).getAnnotation(SerializedName.class);

        if (annotation != null)
            result = annotation.value();

        return result;
    }
}
