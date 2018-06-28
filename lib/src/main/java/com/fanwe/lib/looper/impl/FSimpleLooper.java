/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.looper.impl;

import android.os.Handler;
import android.os.Message;

import com.fanwe.lib.looper.Looper;

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
                loopDelayed(getInterval());
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

    private void loopDelayed(long delay)
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
        if (interval > 0)
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

        loopDelayed(delayMillis);
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

    @Deprecated
    @Override
    public final void start(Runnable runnable)
    {
        startDelayed(0, runnable);
    }

    @Deprecated
    @Override
    public synchronized final void startDelayed(long delayMillis, Runnable runnable)
    {
        setLoopRunnable(runnable);
        startDelayed(delayMillis);
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
