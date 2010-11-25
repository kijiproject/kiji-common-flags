// (c) Copyright 2010 Odiago, Inc.

package com.odiago.common.flags;

import java.lang.reflect.Field;

/**
 * Everything there is to know about a flag declaration.
 */
class FlagSpec {
  private Field mField;
  private Flag mFlag;
  private Object mObj;

  public FlagSpec(Field field, Flag flag, Object obj) {
    mField = field;
    mFlag = flag;
    mObj = obj;
  }

  public String getName() {
    return mFlag.name().isEmpty() ? mField.getName() : mFlag.name();
  }

  public Class<?> getType() {
    return mField.getType();
  }

  public String getTypeName() {
    Class<?> type = getType();
    if (type == String.class) {
      return "String";
    }
    return type.toString();
  }

  public String getUsage() {
    return mFlag.usage();
  }

  public void setValue(String value) throws IllegalAccessException {
    if (!mField.isAccessible()) {
      mField.setAccessible(true);
    }
    if (mField.getType() == boolean.class) {
      if (value.equals("true") || value.isEmpty()) {
        mField.setBoolean(mObj, true);
      } else if (value.equals("false")) {
        mField.setBoolean(mObj, false);
      } else {
        throw new IllegalFlagValueException(getName(), value);
      }
    } else if (mField.getType() == double.class) {
      try {
        mField.setDouble(mObj, Double.parseDouble(value));
      } catch (NumberFormatException e) {
        throw new IllegalFlagValueException(getName(), value);
      }
    } else if (mField.getType() == float.class) {
      try {
        mField.setFloat(mObj, Float.parseFloat(value));
      } catch (NumberFormatException e) {
        throw new IllegalFlagValueException(getName(), value);
      }
    } else if (mField.getType() == int.class) {
      try {
        mField.setInt(mObj, Integer.parseInt(value));
      } catch (NumberFormatException e) {
        throw new IllegalFlagValueException(getName(), value);
      }
    } else if (mField.getType() == long.class) {
      try {
        mField.setLong(mObj, Long.parseLong(value));
      } catch (NumberFormatException e) {
        throw new IllegalFlagValueException(getName(), value);
      }
    } else if (mField.getType() == short.class) {
      try {
        mField.setShort(mObj, Short.parseShort(value));
      } catch (NumberFormatException e) {
        throw new IllegalFlagValueException(getName(), value);
      }
    } else if (mField.getType() == String.class) {
      mField.set(mObj, value);
    } else {
      throw new UnsupportedFlagTypeException(mField.getName());
    }
  }
}