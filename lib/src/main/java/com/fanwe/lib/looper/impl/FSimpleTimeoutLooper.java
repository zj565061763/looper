package com.fanwe.lib.looper.impl;

import android.os.Looper;

import com.fanwe.lib.looper.Timeouter;

public class FSimpleTimeoutLooper extends FSimpleLooper implements Timeouter
{
    private final Timeouter mTimeouter = new FSimpleTimeouter();

    public FSimpleTimeoutLooper()
    {
        this(null);
    }

    public FSimpleTimeoutLooper(Looper looper)
    {
        super(looper);
        // 默认超时时间10秒
        setTimeout(10 * 1000);
        // 默认循环间隔300毫秒
        setInterval(300);
    }

    @Override
    protected void onStateChanged(boolean started)
    {
        super.onStateChanged(started);
        if (started)
            startTimeout();
    }

    @Override
    protected boolean onLoop()
    {
        if (isTimeout())
        {
            runTimeoutRunnable();
            return false;
        }
        return super.onLoop();
    }

    @Override
    public long getTimeout()
    {
        return mTimeouter.getTimeout();
    }

    @Override
    public synchronized boolean isTimeout()
    {
        return mTimeouter.isTimeout();
    }

    @Override
    public synchronized void setTimeoutRunnable(Runnable timeoutRunnable)
    {
        mTimeouter.setTimeoutRunnable(timeoutRunnable);
    }

    @Override
    public synchronized void runTimeoutRunnable()
    {
        mTimeouter.runTimeoutRunnable();
    }

    @Override
    public synchronized void setTimeout(long timeout)
    {
        mTimeouter.setTimeout(timeout);
    }

    @Override
    public synchronized void startTimeout()
    {
        mTimeouter.startTimeout();
    }
}
