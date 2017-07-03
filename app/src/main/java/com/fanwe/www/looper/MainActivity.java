package com.fanwe.www.looper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.iml.SDSimpleLooper;
import com.fanwe.library.looper.iml.SDWaitRunner;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testSDWaitRunner();
    }

    int count = 0;

    /**
     * 等待某个条件成立后需要执行的Runnable
     */
    private void testSDWaitRunner()
    {
        SDWaitRunner waitRunner = new SDWaitRunner()
                .run(new Runnable() //设置需要等待执行的Runnable
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(getApplication(), "run", 0).show();
                    }
                })
                .condition(new SDWaitRunner.Condition() //设置Runnable执行条件
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

//        waitRunner.stopWait(); //停止等待，ui销毁的地方要记得停止，否则会内存泄漏
    }

    private void testSDSimpleLooper()
    {
        //延迟500毫秒后，每秒触发一次设置的Runnable对象
        ISDLooper looper = new SDSimpleLooper();
        looper.start(500, 1000, new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(getApplication(), "toast", 0).show();
            }
        });
//        looper.stop(); //停止循环，ui销毁的地方要停止，否则会内存泄漏
    }
}
