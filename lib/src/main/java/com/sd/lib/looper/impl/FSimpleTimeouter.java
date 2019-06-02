package com.sd.lib.looper.impl;

import com.sd.lib.looper.FTimeouter;

public class FSimpleTimeouter implements FTimeouter
{
    /**
     * 超时时间，默认10秒
     */
    private long mTimeout = 10 * 1000;
    private long mStartTime;
    private Runnable mTimeoutRunnable;

    @Override
    public long getTimeout()
    {
        return mTimeout;
    }

    @Override
    public boolean isTimeout()
    {
        if (mTimeout > 0 && mStartTime > 0)
        {
            if (System.currentTimeMillis() - mStartTime >= mTimeout)
            {
                // 超时
                return true;
            }
        }
        return false;
    }

    @Override
    public void setTimeoutRunnable(Runnable timeoutRunnable)
    {
        mTimeoutRunnable = timeoutRunnable;
    }

    @Override
    public void runTimeoutRunnable()
    {
        if (mTimeoutRunnable != null)
            mTimeoutRunnable.run();
    }

    @Override
    public void setTimeout(long timeout)
    {
        mTimeout = timeout;
    }

    @Override
    public void startTimeout()
    {
        mStartTime = System.currentTimeMillis();
    }
}
