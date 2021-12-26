package com.nghelong.factoryannotation.function;

import android.util.Log;

import com.nghelong.factoryannotation.FunctionType;
import com.nghelong.factoryannotation.data.FunctionAData;
import com.nghelong.factoryannotationlib.FunctionRegister;

@FunctionRegister(functionType = FunctionType.TYPE_A)
public class FunctionA extends FunctionBase{
    FunctionA(FunctionAData data) {
        super(data);
    }

    @Override
    public void execute() {
    Log.d(TAG,"FunctionA");
    }
}
