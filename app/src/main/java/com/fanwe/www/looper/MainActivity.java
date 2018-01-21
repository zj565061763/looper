package com.fanwe.www.looper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fanwe.lib.looper.FLooper;
import com.fanwe.lib.looper.impl.FSimpleLooper;
import com.fanwe.lib.looper.impl.FSimpleTimeoutLooper;
import com.fanwe.lib.looper.impl.FWaitRunner;

public class MainActivity extends AppCompatActivity
{

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testSimpleLooper();
    }

    private void testSimpleTimeoutLooper()
    {
        FSimpleTimeoutLooper looper = new FSimpleTimeoutLooper();
        looper.setTimeout(5 * 1000) //设置超时时间
                .setTimeoutRunnable(new Runnable() //设置超时后需要执行的Runnable
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplication(), "timeout", 0).show();
                    }
                })
                //延迟500毫秒，每隔1000毫秒触发一次Runnable，触发的同时会进行是否超时的判断，如果超时，执行超时Runnable
                .start(500, 1000, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplication(), "run", 0).show();
                    }
                });
    }

    int count = 0;

    /**
     * 等待某个条件成立后需要执行的Runnable
     */
    private void testWaitRunner()
    {
        FWaitRunner waitRunner = new FWaitRunner()
                .run(new Runnable() //设置需要等待执行的Runnable
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplication(), "run", 0).show();
                    }
                })
                .condition(new FWaitRunner.Condition() //设置Runnable执行条件
                {
                    @Override
                    public boolean canRun()
                    {
                        // 返回true则Runnable立即执行，返回false则继续等待，如果超时会执行超时Runnable
                        return false;
                    }
                })
                .setTimeout(5 * 1000)//设置等待超时时间
                .setTimeoutRunnable(new Runnable() //设置超时需要执行的Runnable
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplication(), "timeout", 0).show();
                    }
                })
                .startWait(100); //开始等待，每100毫秒检测一次Runnable执行条件是否成立

        // waitRunner.stopWait(); //停止等待，在需要停止的地方停止，比如ui销毁
    }

    private void testSimpleLooper()
    {
        //延迟500毫秒后，每秒触发一次设置的Runnable对象
        FLooper looper = new FSimpleLooper();
        looper.start(500, 1000, new Runnable()
        {
            @Override
            public void run()
            {
                Log.i(TAG, "looper run");
            }
        });
        // looper.stop(); //停止循环，在需要停止的地方停止，比如ui销毁
    }
}
