package com.fanwe.library.looper.impl;

import com.fanwe.library.looper.ISDTimeouter;

public class SDSimpleTimeouter implements ISDTimeouter
{
    private Runnable mTimeoutRunnable;

    private long mTimeout = DEFAULT_TIMEOUT;
    private long mStartTime;

    @Override
    public synchronized long getTimeout()
    {
        return mTimeout;
    }

    @Override
    public synchronized boolean isTimeout()
    {
        boolean result = false;
        if (mTimeout > 0 && mStartTime > 0)
        {
            if (System.currentTimeMillis() - mStartTime >= mTimeout)
            {
                // 超时
                result = true;
            }
        }
        return result;
    }

    @Override
    public synchronized ISDTimeouter setTimeoutRunnable(Runnable timeoutRunnable)
    {
        this.mTimeoutRunnable = timeoutRunnable;
        return this;
    }

    @Override
    public synchronized ISDTimeouter runTimeoutRunnable()
    {
        if (mTimeoutRunnable != null)
        {
            mTimeoutRunnable.run();
        }
        return this;
    }

    @Override
    public synchronized ISDTimeouter setTimeout(long timeout)
    {
        mTimeout = timeout;
        return this;
    }

    @Override
    public synchronized ISDTimeouter startTimeout()
    {
        mStartTime = System.currentTimeMillis();
        return this;
    }

    @Override
    public synchronized ISDTimeouter stopTimeout()
    {
        mStartTime = 0;
        return this;
    }
}
