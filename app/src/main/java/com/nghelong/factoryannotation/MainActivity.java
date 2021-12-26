package com.nghelong.factoryannotation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nghelong.factoryannotation.data.FunctionAData;
import com.nghelong.factoryannotation.function.FunctionBase;
import com.nghelong.factoryannotation.function.FunctionFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FunctionAData data = new FunctionAData();
        FunctionBase function = FunctionFactory.getFunction(FunctionType.TYPE_A,data);
        function.execute();
    }
}