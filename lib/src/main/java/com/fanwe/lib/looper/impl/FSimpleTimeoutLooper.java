package com.fanwe.lib.looper.impl;

import com.fanwe.lib.looper.FTimeouter;

public class FSimpleTimeoutLooper extends FSimpleLooper implements FTimeouter
{
    private final FTimeouter mTimeouter = new FSimpleTimeouter();

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
    public FTimeouter setTimeoutRunnable(Runnable timeoutRunnable)
    {
        mTimeouter.setTimeoutRunnable(timeoutRunnable);
        return this;
    }

    @Override
    public FTimeouter runTimeoutRunnable()
    {
        mTimeouter.runTimeoutRunnable();
        return this;
    }

    @Override
    public FTimeouter setTimeout(long timeout)
    {
        mTimeouter.setTimeout(timeout);
        return this;
    }

    @Override
    public FTimeouter startTimeout()
    {
        mTimeouter.startTimeout();
        return this;
    }
}
