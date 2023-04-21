package com.alibaba.fastjson;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class TypeReference<T> {
    protected final Type type;

    protected TypeReference() {
        this.type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected TypeReference(Type... actualTypeArguments) {
        ParameterizedType argType = (ParameterizedType) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Type rawType = argType.getRawType();
        Type[] argTypes = argType.getActualTypeArguments();
        int actualIndex = 0;
        int i = 0;
        while (true) {
            if (i >= argTypes.length) {
                break;
            }
            if (argTypes[i] instanceof TypeVariable) {
                int actualIndex2 = actualIndex + 1;
                argTypes[i] = actualTypeArguments[actualIndex];
                if (actualIndex2 >= actualTypeArguments.length) {
                    int i2 = actualIndex2;
                    break;
                }
                actualIndex = actualIndex2;
            }
            i++;
        }
        this.type = new ParameterizedTypeImpl(argTypes, getClass(), rawType);
    }

    public Type getType() {
        return this.type;
    }
}
