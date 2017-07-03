package com.fanwe.library.looper.iml;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.fanwe.library.looper.ISDLooper;

public class SDSimpleLooper implements ISDLooper
{
    private static final int MSG_WHAT = 1990;

    private Runnable mRunnable;
    private long mPeriod;
    private boolean mIsRunning = false;
    private boolean mIsCancelled = false;
    private Handler mHandler;

    public SDSimpleLooper()
    {
        this(Looper.getMainLooper());
    }

    public SDSimpleLooper(Looper looper)
    {
        mHandler = new Handler(looper)
        {
            public void handleMessage(Message msg)
            {
                synchronized (SDSimpleLooper.this)
                {
                    if (mIsCancelled)
                    {
                        return;
                    }

                    if (mRunnable != null)
                    {
                        mRunnable.run();
                        SDSimpleLooper.this.sendMessageDelayed(mPeriod);
                    } else
                    {
                        stop();
                    }
                }
            }
        };
    }

    @Override
    public synchronized boolean isRunning()
    {
        return mIsRunning;
    }

    @Override
    public synchronized void setPeriod(long period)
    {
        if (period <= 0)
        {
            period = DEFAULT_PERIOD;
        }
        this.mPeriod = period;
    }

    @Override
    public ISDLooper start(Runnable runnable)
    {
        start(0, DEFAULT_PERIOD, runnable);
        return this;
    }

    @Override
    public ISDLooper start(long period, Runnable runnable)
    {
        start(0, period, runnable);
        return this;
    }

    @Override
    public synchronized ISDLooper start(long delay, long period, Runnable runnable)
    {
        stop();

        this.mIsRunning = true;
        this.mIsCancelled = false;
        this.mRunnable = runnable;

        setPeriod(period);

        sendMessageDelayed(delay);
        return this;
    }

    private void sendMessageDelayed(long delay)
    {
        Message msg = mHandler.obtainMessage(MSG_WHAT);
        mHandler.sendMessageDelayed(msg, delay);
    }

    @Override
    public synchronized ISDLooper stop()
    {
        mHandler.removeMessages(MSG_WHAT);
        mIsRunning = false;
        mIsCancelled = true;
        return this;
    }
}
