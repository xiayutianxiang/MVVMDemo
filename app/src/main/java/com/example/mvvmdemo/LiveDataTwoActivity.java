package com.example.mvvmdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

public class LiveDataTwoActivity extends AppCompatActivity {

    private static final String TAG = "LiveDataTwoActivity";
    private MutableLiveData<String> mMutableLiveData;
    private MutableLiveData<Integer> mMutableLiveData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);

        mMutableLiveData = new MutableLiveData<>();
        mMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "onChanged " + s);
            }
        });
        Log.d(TAG, "onCreate: ");
        mMutableLiveData.setValue("onCreate");

        /**
         *如果希望在将 LiveData 对象分派给观察者之前对存储在其中的值进行更改，
         * 或者需要根据另一个实例的值返回不同的 LiveData 实例，
         * 可以使用LiveData中提供的Transformations类。
         */
        LiveData<String> liveData = Transformations.map(mMutableLiveData2, new Function<Integer, String>() {
            @Override
            public String apply(Integer input) {
                String s = input + " Transformations.map ";
                Log.d(TAG," apply "+s);
                return s;
            }
        });

        liveData.observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
        liveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i(TAG, "onChanged: "+s);
            }
        });
        mMutableLiveData2.setValue(666);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        mMutableLiveData.setValue("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        mMutableLiveData.setValue("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        mMutableLiveData.setValue("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        mMutableLiveData.setValue("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        mMutableLiveData.setValue("onDestroy");
    }
}
