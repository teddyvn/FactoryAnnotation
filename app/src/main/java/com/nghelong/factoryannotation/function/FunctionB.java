package com.nghelong.factoryannotation.function;

import android.util.Log;

import com.nghelong.factoryannotation.FunctionType;
import com.nghelong.factoryannotation.data.FunctionBData;
import com.nghelong.factoryannotationlib.FunctionRegister;

@FunctionRegister(functionType = FunctionType.TYPE_B)
public class FunctionB extends FunctionBase {
    FunctionB(FunctionBData data) {
        super(data);
    }

    @Override
   public void execute() {
        Log.d(TAG,"FunctionB");
    }
}
