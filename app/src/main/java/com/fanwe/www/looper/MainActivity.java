package com.fanwe.www.looper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fanwe.lib.looper.impl.FSimpleLooper;
import com.fanwe.lib.looper.impl.FSimpleTimeoutLooper;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = "MainActivity";

    private FSimpleLooper mLooper = new FSimpleLooper();
    private FSimpleTimeoutLooper mTimeoutLooper = new FSimpleTimeoutLooper();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testSimpleTimeoutLooper();
    }

    private void testSimpleLooper()
    {
        //延迟500毫秒后，每秒触发一次设置的Runnable对象
        mLooper.start(500, 1000, new Runnable()
        {
            @Override
            public void run()
            {
                Log.i(TAG, "looper run");
            }
        });
    }

    private void testSimpleTimeoutLooper()
    {
        mTimeoutLooper.setTimeout(5 * 1000) //设置超时时间
                .setTimeoutRunnable(new Runnable() //设置超时后需要执行的Runnable
                {
                    @Override
                    public void run()
                    {
                        Log.e(TAG, "looper timeout");
                    }
                })
                //延迟500毫秒，每隔1000毫秒触发一次Runnable，触发的同时会进行是否超时的判断，如果超时，执行超时Runnable
                .start(500, 1000, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Log.i(TAG, "looper run");
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
