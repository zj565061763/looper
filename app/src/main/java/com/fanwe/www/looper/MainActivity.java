package com.fanwe.www.looper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fanwe.lib.looper.impl.FSimpleLooper;
import com.fanwe.lib.looper.impl.FSimpleTimeoutLooper;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private final FSimpleLooper mLooper = new FSimpleLooper();
    private final FSimpleTimeoutLooper mTimeoutLooper = new FSimpleTimeoutLooper();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testSimpleLooper();
        testSimpleTimeoutLooper();
    }

    private void testSimpleLooper()
    {
        mLooper.setInterval(1000);//设置每隔1000毫秒触发一次
        mLooper.start(new Runnable()
        {
            @Override
            public void run()
            {
                Log.i(TAG, "FSimpleLooper run");
            }
        });
    }

    private void testSimpleTimeoutLooper()
    {
        mTimeoutLooper.setTimeout(5 * 1000); //设置超时时间，默认10秒
        mTimeoutLooper.setTimeoutRunnable(new Runnable() //设置超时需要执行的Runnable
        {
            @Override
            public void run()
            {
                Log.e(TAG, "timeout");
            }
        });
        mTimeoutLooper.setInterval(1000); //设置每隔1000毫秒触发一次
        mTimeoutLooper.start(new Runnable()
        {
            @Override
            public void run()
            {
                Log.e(TAG, "FSimpleTimeoutLooper run");
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mLooper.stop(); // 停止循环
        mTimeoutLooper.stop(); // 停止循环
    }
}
