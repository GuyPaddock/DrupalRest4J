package com.redbottledesign.gson.strategy;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class BeforeAndAfterExclusionStrategy
implements ExclusionStrategy
{
    private final Object      originalObject;
    private final Object      updatedObject;
    private final Set<String> fieldsToPreserve;

    public BeforeAndAfterExclusionStrategy(Object originalObject, Object updatedObject, String... fieldsToPreserve)
    {
        this(originalObject, updatedObject, Arrays.asList(fieldsToPreserve));
    }

    public BeforeAndAfterExclusionStrategy(Object originalObject, Object updatedObject,
                                           Collection<String> fieldsToPreserve)
    {
        if (originalObject == null)
            throw new IllegalArgumentException("originalObject cannot be null.");

        if (updatedObject == null)
            throw new IllegalArgumentException("updatedObject cannot be null.");

        this.originalObject     = originalObject;
        this.updatedObject      = updatedObject;
        this.fieldsToPreserve   = new HashSet<>(fieldsToPreserve);
    }

    public Object getOriginalObject()
    {
        return this.originalObject;
    }

    public Object getUpdatedObject()
    {
        return this.updatedObject;
    }

    public Set<String> getFieldsToPreserve()
    {
        return this.fieldsToPreserve;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes attributes)
    {
        boolean     skip;
        String      fieldName       = attributes.getName();
        Class<?>    containerClass  = attributes.getDeclaringClass();

        /* Don't skip fields we've been asked to preserve, as well as fields
         * that don't pertain to the type of object we're working with.
         */
        if (this.fieldsToPreserve.contains(fieldName) ||
            !containerClass.isAssignableFrom(this.updatedObject.getClass()))
        {
            skip = false;
        }

        else
        {
            Object	originalValue,
            		newValue;

            try
            {
                Field field = containerClass.getDeclaredField(fieldName);

                field.setAccessible(true);

                originalValue   = field.get(this.originalObject);
                newValue        = field.get(this.updatedObject);
            }

            catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex)
            {
                throw new RuntimeException(String.format(
                    "Failed to retrieve value of existing entity field \"%s.%s\": %s", containerClass.getName(),
                    fieldName, ex.getMessage()), ex);
            }

            if ((originalValue == null) && (newValue == null))
                skip = true;

            else if ((originalValue != null) && originalValue.equals(newValue))
                skip = true;

            else
                skip = false;
        }

        return skip;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz)
    {
        return false;
    }
}