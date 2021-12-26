package com.nghelong.factoryannotationlib;

import com.nghelong.factoryannotation.FunctionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface FunctionRegister {
FunctionType functionType();
}