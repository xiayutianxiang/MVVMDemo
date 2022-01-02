package com.example.mvvmdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewConfiguration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

public class LiveDataActivity extends AppCompatActivity {

    private static final String TAG = "LiveDataActivity";
    MutableLiveData<String> mutableLiveData1;
    MutableLiveData<String> mutableLiveData2;
    MutableLiveData<Boolean> liveDataSwitch;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);

        MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

        mutableLiveData1 = new MutableLiveData<>();
        mutableLiveData2 = new MutableLiveData<>();
        liveDataSwitch = new MutableLiveData<>();//1

        mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG,"onChanged ... "+ s);
            }
        });
        //scaledTouchSlop
        Log.i(TAG, "TouchSlop=" + ViewConfiguration.get(this).getScaledTouchSlop());

        //如果想要在LiveData对象分发给观察者之前对其中存储的值进行更改，
        // 可以使用Transformations.map()。
        /*LiveData transformData = Transformations.map(mutableLiveData, new Function<String, Object>() {
            @Override
            public Object apply(String input) {
                return input + "+Android进阶解密";
            }
        });

        transformData.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Log.d(TAG, "onChanged2:"+o.toString());
            }
        });*/

        /**2.Transformations.switchMap(
         * 如果想要手动控制监听其中一个的数据变化，并能根据需要随时切换监听，
         *  这时可以使用Transformations.switchMap()，
         *  它和Transformations.map()使用方式类似，只不过switchMap()必须返回一个LiveData对象。
         */
        LiveData transformedLiveData = Transformations.switchMap(liveDataSwitch,
                new Function<Boolean, LiveData<String>>() {
            @Override
            public LiveData<String> apply(Boolean input) {
                if(input){
                    return mutableLiveData1;
                }else {
                    return mutableLiveData2;
                }
            }
        });

        transformedLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "onChanged:" + s);
            }
        });

        liveDataSwitch.postValue(false);//2
        mutableLiveData1.postValue("Android进阶之光");
        mutableLiveData2.postValue("Android进阶解密");

        /**
         * 3 合并多个LiveData数据源
         * MediatorLiveData继承自mutableLiveData，
         * 它可以将多个LiveData数据源集合起来，可以达到一个组件监听多个LiveData数据变化的目的。
         */

        MediatorLiveData liveDataMerger = new MediatorLiveData<String>();
        liveDataMerger.addSource(mutableLiveData1, new Observer() {
            @Override
            public void onChanged(Object o) {
                Log.d(TAG, "onChanged1:"+o.toString());
            }
        });

        liveDataMerger.addSource(mutableLiveData2, new Observer() {
            @Override
            public void onChanged(Object o) {
                Log.d(TAG, "onChanged1:"+o.toString());
            }
        });

        liveDataMerger.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Log.d(TAG, "onChanged:"+o.toString());
            }
        });

        mutableLiveData1.postValue("Android进阶之光");
        mutableLiveData.postValue("postValue liveData 中数据改变了");
    }
}
