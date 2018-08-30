package com.sd.lib.looper.impl;

import android.os.Handler;
import android.os.Message;

import com.sd.lib.looper.Looper;

public class FSimpleLooper implements Looper
{
    private static final int MSG_WHAT = 1990;

    private final Handler mHandler;
    private Runnable mLoopRunnable;
    /**
     * 循环触发间隔，默认300毫秒
     */
    private long mInterval = 300;
    private boolean mIsStarted = false;

    private OnStateChangeCallback mOnStateChangeCallback;

    public FSimpleLooper()
    {
        this(null);
    }

    public FSimpleLooper(android.os.Looper looper)
    {
        if (looper == null)
            looper = android.os.Looper.getMainLooper();

        mHandler = new Handler(looper)
        {
            @Override
            public void handleMessage(Message msg)
            {
                loopIfNeed();
            }
        };
    }

    private synchronized void loopIfNeed()
    {
        if (mIsStarted)
        {
            if (onLoop())
            {
                sendMessageDelayed(getInterval());
            } else
            {
                stop();
            }
        }
    }

    /**
     * 循环回调
     *
     * @return true-继续循环，false-停止循环
     */
    protected boolean onLoop()
    {
        if (mLoopRunnable == null)
            return false;

        mLoopRunnable.run();
        return true;
    }

    private void sendMessageDelayed(long delay)
    {
        final Message msg = mHandler.obtainMessage(MSG_WHAT);
        mHandler.sendMessageDelayed(msg, delay);
    }

    @Override
    public void setOnStateChangeCallback(OnStateChangeCallback callback)
    {
        mOnStateChangeCallback = callback;
    }

    @Override
    public final boolean isStarted()
    {
        return mIsStarted;
    }

    @Override
    public long getInterval()
    {
        return mInterval;
    }

    @Override
    public synchronized void setInterval(long interval)
    {
        if (interval <= 0)
            throw new IllegalArgumentException("interval is out of range (interval > 0).");

        mInterval = interval;
    }

    @Override
    public synchronized void setLoopRunnable(Runnable runnable)
    {
        mLoopRunnable = runnable;
    }

    @Override
    public final boolean start()
    {
        return startDelayed(0);
    }

    @Override
    public synchronized final boolean startDelayed(long delayMillis)
    {
        if (mLoopRunnable == null)
            return false;

        stop();

        sendMessageDelayed(delayMillis);
        setStarted(true);
        return true;
    }

    @Override
    public synchronized final void stop()
    {
        if (mIsStarted)
        {
            mHandler.removeMessages(MSG_WHAT);
            setStarted(false);
        }
    }

    private void setStarted(boolean started)
    {
        if (mIsStarted != started)
        {
            mIsStarted = started;
            onStateChanged(started);
            if (mOnStateChangeCallback != null)
                mOnStateChangeCallback.onStateChanged(started);
        }
    }

    /**
     * 循环是否开始状态变化回调
     *
     * @param started
     */
    protected void onStateChanged(boolean started)
    {
    }
}
