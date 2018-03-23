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
import android.os.Looper;
import android.os.Message;

import com.fanwe.lib.looper.FLooper;

public class FSimpleLooper implements FLooper
{
    private static final int MSG_WHAT = 1990;

    private final Handler mHandler;
    private Runnable mRunnable;
    /**
     * 循环触发间隔，默认300毫秒
     */
    private long mInterval = 300;
    private boolean mIsStarted = false;

    public FSimpleLooper()
    {
        this(Looper.getMainLooper());
    }

    public FSimpleLooper(Looper looper)
    {
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
                loopDelayed(mInterval);
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
        if (mRunnable == null)
        {
            return false;
        } else
        {
            mRunnable.run();
            return true;
        }
    }

    private void loopDelayed(long delay)
    {
        final Message msg = mHandler.obtainMessage(MSG_WHAT);
        mHandler.sendMessageDelayed(msg, delay);
    }

    @Override
    public final boolean isStarted()
    {
        return mIsStarted;
    }

    @Override
    public final long getInterval()
    {
        return mInterval;
    }

    @Override
    public final synchronized void setInterval(long interval)
    {
        if (interval > 0)
        {
            mInterval = interval;
        }
    }

    @Override
    public final void start(Runnable runnable)
    {
        startDelayed(0, runnable);
    }

    @Override
    public synchronized final void startDelayed(long delayMillis, Runnable runnable)
    {
        stop();

        mRunnable = runnable;
        mIsStarted = true;
        onStartLoop();
        loopDelayed(delayMillis);
    }

    @Override
    public synchronized final void stop()
    {
        if (mIsStarted)
        {
            mIsStarted = false;
            mHandler.removeMessages(MSG_WHAT);
            onStopLoop();
        }
    }

    /**
     * 循环开始回调
     */
    protected void onStartLoop()
    {
    }

    /**
     * 循环结束回调
     */
    protected void onStopLoop()
    {
    }
}
