package com.fanwe.library.looper.impl;

import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.ISDTimeouter;

public class SDSimpleTimeoutLooper implements ISDLooper, ISDTimeouter
{
    private ISDLooper mLooper = new SDSimpleLooper();
    private ISDTimeouter mTimeouter = new SDSimpleTimeouter();
    private Runnable mRunnable;

    @Override
    public boolean isRunning()
    {
        return mLooper.isRunning();
    }

    @Override
    public void setPeriod(long period)
    {
        mLooper.setPeriod(period);
    }

    @Override
    public SDSimpleTimeoutLooper start(Runnable runnable)
    {
        start(0, DEFAULT_PERIOD, runnable);
        return this;
    }

    @Override
    public SDSimpleTimeoutLooper start(long period, Runnable runnable)
    {
        start(0, DEFAULT_PERIOD, runnable);
        return this;
    }

    @Override
    public SDSimpleTimeoutLooper start(long delay, long period, Runnable runnable)
    {
        this.mRunnable = runnable;
        mLooper.start(delay, period, mRealRunnable);
        mTimeouter.startTimeout();
        return this;
    }

    private Runnable mRealRunnable = new Runnable()
    {

        @Override
        public void run()
        {
            if (mRunnable != null)
            {
                if (isTimeout())
                {
                    runTimeoutRunnable();
                    stop();
                } else
                {
                    mRunnable.run();
                }
            } else
            {
                stop();
            }
        }
    };

    @Override
    public SDSimpleTimeoutLooper stop()
    {
        mLooper.stop();
        return this;
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
    public SDSimpleTimeoutLooper setTimeoutRunnable(Runnable timeoutRunnable)
    {
        mTimeouter.setTimeoutRunnable(timeoutRunnable);
        return this;
    }

    @Override
    public SDSimpleTimeoutLooper runTimeoutRunnable()
    {
        mTimeouter.runTimeoutRunnable();
        return this;
    }

    @Override
    public SDSimpleTimeoutLooper setTimeout(long timeout)
    {
        mTimeouter.setTimeout(timeout);
        return this;
    }

    @Override
    public SDSimpleTimeoutLooper startTimeout()
    {
        mTimeouter.startTimeout();
        return this;
    }

    @Override
    public SDSimpleTimeoutLooper stopTimeout()
    {
        mTimeouter.stopTimeout();
        return this;
    }

}
