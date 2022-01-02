package com.example.mvvmdemo.lifedata;

import androidx.lifecycle.LiveData;

import java.math.BigDecimal;

/**
 * 扩展使用LiveData
 *  1.自定义LiveData，本身回调方法的覆写：onActive()、onInactive()。
 *  2.实现LiveData为单例模式，便于在多个Activity、Fragment之间共享数据。
 */
public class StockLiveData extends LiveData<BigDecimal> {

    @Override
    protected void onActive() {
        super.onActive();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
    }

    interface SimplePriceListener{
        void onPriceChanged(BigDecimal price);
    }
}
