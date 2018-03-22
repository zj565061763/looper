package com.fanwe.www.looper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fanwe.lib.looper.impl.FConditionRunner;
import com.fanwe.lib.looper.impl.FSimpleLooper;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = "MainActivity";

    private FSimpleLooper mLooper = new FSimpleLooper();
    private FConditionRunner mConditionRunner = new FConditionRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testSimpleLooper();
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

    private void testConditionRunner()
    {
        mConditionRunner.run(new Runnable() //设置条件成立后要执行的Runnable对象
        {
            @Override
            public void run()
            {
                Toast.makeText(getApplication(), "run", Toast.LENGTH_SHORT).show();
            }
        }).condition(new FConditionRunner.Condition() //设置Runnable的执行条件
        {
            @Override
            public boolean canRun()
            {
                //返回true则Runnable立即执行，返回false则继续检测，如果超时会执行超时Runnable
                return false;
            }
        }).setTimeout(5 * 1000)//设置超时时间，默认10秒
                .setTimeoutRunnable(new Runnable() //设置超时需要执行的Runnable
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplication(), "timeout", Toast.LENGTH_SHORT).show();
                    }
                })
                .startCheck(); //开始检测条件是否成立，默认每300毫秒检测一次
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mLooper.stop(); // 停止循环
        mConditionRunner.stopCheck(); // 停止检测
    }
}
