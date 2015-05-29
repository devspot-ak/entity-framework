package biz.devspot.entity.framework.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ReflectionUtils {

    public static Type getFieldType(Class<?> targetClass, Field field) {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof TypeVariable)) {
            return field.getType();
        }
        Class declaringClass = field.getDeclaringClass();
        List<Class> classes = new LinkedList<Class>();
        classes.add(targetClass);
        if (!targetClass.equals(declaringClass)) {
            Class superClass = targetClass.getSuperclass();
            while (superClass != null && !superClass.equals(declaringClass)) {
                classes.add(superClass);
                superClass = superClass.getSuperclass();
            }
            classes.add(declaringClass);
        }
        Collections.reverse(classes);
        Type type = null;
        int index = -1;
        for (Class clazz : classes) {
            if (index > -1) {
                if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
                    type = pt.getActualTypeArguments()[index];
                    if(type instanceof TypeVariable){
                        genericType = type;
                    }
                }
            }
            int i = 0;
            for (TypeVariable typeParam : clazz.getTypeParameters()) {
                if (genericType.toString().equals(typeParam.getName())) {
                    index = i;
                    type = typeParam.getBounds()[0];
                }
                i = i + 1;
            }
        }
        return type;
    }

}
