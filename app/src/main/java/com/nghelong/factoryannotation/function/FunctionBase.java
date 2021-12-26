package com.nghelong.factoryannotation.function;

import com.nghelong.factoryannotation.data.FunctionBaseData;

public abstract class FunctionBase {
    protected final String TAG;
    private final FunctionBaseData data;
    FunctionBase(FunctionBaseData data){
        this.data = data;
        TAG = getClass().getSimpleName();
    }
    public abstract void execute();
}
