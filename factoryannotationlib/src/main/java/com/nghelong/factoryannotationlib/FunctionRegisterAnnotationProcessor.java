package com.nghelong.factoryannotationlib;

import com.nghelong.factoryannotation.FunctionType;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes(
        "com.nghelong.factoryannotationlib.FunctionRegister")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class FunctionRegisterAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Map<FunctionType,String> functionTypeMap = new HashMap<>();

        Collection<? extends Element> annotatedElements =
                roundEnvironment.getElementsAnnotatedWith(FunctionRegister.class);
        List<TypeElement> types = ElementFilter.typesIn(annotatedElements);
        for (TypeElement type : types) {
            String className = type.getQualifiedName().toString();
            FunctionRegister functionRegister = type.getAnnotation(FunctionRegister.class);
            FunctionType functionType = functionRegister.functionType();
            functionTypeMap.put(functionType,className);
        }
        this.writeSourceFile("com.nghelong.factoryannotation.function.FunctionFactory",functionTypeMap);
        return true;
    }
    private void writeSourceFile(String className, final Map<FunctionType,String> screenMap) {
        try {
            JavaFileObject sourceFile =
                    processingEnv.getFiler().createSourceFile(className);

            try (Writer writer = sourceFile.openWriter()) {
                writer.write("package com.nghelong.factoryannotation.function;\n" +
                        "import android.util.Log;\n" +
                        "import com.nghelong.factoryannotation.FunctionType;\n" +
                        "import com.nghelong.factoryannotation.data.FunctionBaseData;\n" +
                        "import java.lang.reflect.Constructor;\n" +
                        "import java.lang.reflect.InvocationTargetException;\n"+
                        "import java.util.HashMap;\n"+
                        "import java.util.Map;\n"+

                        "/**\n" +
                        "* This is automatically generated code. Don't modify it.\n" +
                        "*/\n" +
                        "public class FunctionFactory {\n" +
                        "private static final String TAG = FunctionFactoryTest.class.getSimpleName();\n"+
                        "private static final Map<FunctionType,String> functionMap = new HashMap<FunctionType,String>(){{\n");
                for (FunctionType key : screenMap.keySet()) {
                    writer.write("    put(FunctionType.valueOf(\"" + key + "\"),\"" + screenMap.get(key) + "\");\n");
                }
                writer.write("    }};\n"+
                "public static FunctionBase getFunction(FunctionType functionType, FunctionBaseData data){\n"+
                "    FunctionBase function = null;\n"+
                "    String className = functionMap.get(functionType);\n"+
                "    if (className != null){\n"+
                "        try {\n"+
                "            Class<?> clazz = Class.forName(className);\n"+
                        "        Constructor<?>[] constructors =  clazz.getDeclaredConstructors();\n"+
                        "                        if (constructors.length == 1){\n"+
                        "                            Constructor<?> constructor = constructors[0];\n"+
                        "                            Class<?>[] types = constructor.getParameterTypes();\n"+
                        "                            Class<?> declaringClass = constructor.getDeclaringClass();\n"+
                        "                            Class<?> declaringSuperClass = (Class<?>) declaringClass.getGenericSuperclass();\n"+
                        "                            if (types.length == 1){\n"+
                        "                                Class<?> type = types[0];\n"+
                        "                                Class<?> superClass = (Class<?>) type.getGenericSuperclass();\n"+
                        "                                if ((superClass!=null) && (declaringSuperClass!= null) &&\n"+
                        "                                        (FunctionBaseData.class.isAssignableFrom(superClass)) &&\n"+
                        "                                        (FunctionBase.class.isAssignableFrom(declaringSuperClass))){\n"+
                        "                                   function = (FunctionBase) constructor.newInstance(data);\n"+
                        "                                }\n"+
                        "                            }\n"+
                        "                        }\n"+
                        "                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {\n"+
                        "                        Log.d(TAG,e.toString());\n"+
                        "                    }\n"+
                        "                }\n"+
                        "                return function;\n"+
                        "            }\n"+
                        "   }");
            }
        } catch ( IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                    "Could not write generated class " + className + ": " + e);
        }
    }
}
