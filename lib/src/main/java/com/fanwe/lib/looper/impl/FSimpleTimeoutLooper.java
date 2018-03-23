package com.fanwe.lib.looper.impl;

import android.os.Looper;

import com.fanwe.lib.looper.FTimeouter;

public class FSimpleTimeoutLooper extends FSimpleLooper implements FTimeouter
{
    private final FTimeouter mTimeouter = new FSimpleTimeouter();

    public FSimpleTimeoutLooper()
    {
        this(Looper.getMainLooper());
    }

    public FSimpleTimeoutLooper(Looper looper)
    {
        super(looper);
        setTimeout(10 * 1000); //默认超时时间10秒
        setInterval(300); //默认循环间隔300毫秒
    }

    @Override
    protected void onStartLoop()
    {
        super.onStartLoop();
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
    public boolean isTimeout()
    {
        return mTimeouter.isTimeout();
    }

    @Override
    public void setTimeoutRunnable(Runnable timeoutRunnable)
    {
        mTimeouter.setTimeoutRunnable(timeoutRunnable);
    }

    @Override
    public void runTimeoutRunnable()
    {
        mTimeouter.runTimeoutRunnable();
    }

    @Override
    public synchronized void setTimeout(long timeout)
    {
        mTimeouter.setTimeout(timeout);
    }

    @Override
    public void startTimeout()
    {
        mTimeouter.startTimeout();
    }
}
