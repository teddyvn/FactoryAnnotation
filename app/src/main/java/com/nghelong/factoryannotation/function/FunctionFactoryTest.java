package com.nghelong.factoryannotation.function;

import android.util.Log;

import com.nghelong.factoryannotation.FunctionType;
import com.nghelong.factoryannotation.data.FunctionBaseData;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class FunctionFactoryTest {
    private static final String TAG = FunctionFactoryTest.class.getSimpleName();
    private static final Map<FunctionType,String> functionMap = new HashMap<FunctionType,String>(){{
        put(FunctionType.TYPE_A,"com.nghelong.factoryannotation.function.FunctionA");
    }};
    public static FunctionBase getFunction(FunctionType functionType, FunctionBaseData data){
        FunctionBase function = null;
        String className = functionMap.get(functionType);
        if (className != null){
            try {
                Class<?> clazz = Class.forName(className);
             Constructor<?>[] constructors =  clazz.getDeclaredConstructors();
             if (constructors.length == 1){
                 Constructor<?> constructor = constructors[0];
                 Class<?>[] types = constructor.getParameterTypes();
                 Class<?> declaringClass = constructor.getDeclaringClass();
                 Class<?> declaringSuperClass = (Class<?>) declaringClass.getGenericSuperclass();
                 if (types.length == 1){
                     Class<?> type = types[0];
                     Class<?> superClass = (Class<?>) type.getGenericSuperclass();
                     if ((superClass!=null) && (declaringSuperClass!= null) &&
                             (FunctionBaseData.class.isAssignableFrom(superClass)) &&
                                     (FunctionBase.class.isAssignableFrom(declaringSuperClass))){
                         function = (FunctionBase) constructor.newInstance(data);
                     }
                 }
             }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                Log.d(TAG,e.toString());
            }

        }
        return function;
    }
}
