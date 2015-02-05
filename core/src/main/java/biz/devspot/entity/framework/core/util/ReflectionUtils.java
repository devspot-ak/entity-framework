package biz.devspot.entity.framework.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class ReflectionUtils {

    public static Type getFieldType(Class<?> clazz, Field field){
        return getTypeP(clazz, field).type;
    }
    
    protected static class TypeInfo {

        Type type;
        Type name;

        public TypeInfo(Type type, Type name) {
            this.type = type;
            this.name = name;
        }

    }

    private static TypeInfo getTypeP(Class<?> clazz, Field field) {
        TypeInfo type = new TypeInfo(null, null);
        if (field.getGenericType() instanceof TypeVariable<?>) {
            TypeVariable<?> genericTyp = (TypeVariable<?>) field.getGenericType();
            Class<?> superClazz = clazz.getSuperclass();

            if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) clazz.getGenericSuperclass();
                TypeVariable<?>[] superTypeParameters = superClazz.getTypeParameters();
                if (!Object.class.equals(paramType)) {
                    if (field.getDeclaringClass().equals(superClazz)) {
                        // this is the root class an starting point for this search
                        type.name = genericTyp;
                        type.type = null;
                    } else {
                        type = getTypeP(superClazz, field);
                    }
                }
                if (type.type == null || type.type instanceof TypeVariable<?>) {
                    // lookup if type is not found or type needs a lookup in current concrete class
                    for (int j = 0; j < superClazz.getTypeParameters().length; ++j) {
                        TypeVariable<?> superTypeParam = superTypeParameters[j];
                        if (type.name.equals(superTypeParam)) {
                            type.type = paramType.getActualTypeArguments()[j];
                            Type[] typeParameters = clazz.getTypeParameters();
                            if (typeParameters.length > 0) {
                                for (Type typeParam : typeParameters) {
                                    TypeVariable<?> objectOfComparison = superTypeParam;
                                    if (type.type instanceof TypeVariable<?>) {
                                        objectOfComparison = (TypeVariable<?>) type.type;
                                    }
                                    if (objectOfComparison.getName().equals(((TypeVariable<?>) typeParam).getName())) {
                                        type.name = typeParam;
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }else{
                type = getTypeP(superClazz, field);
            }
        } else {
            type.type = field.getGenericType();
        }

        return type;
    }
    
}
