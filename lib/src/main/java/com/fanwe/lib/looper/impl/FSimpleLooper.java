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
    private long mPeriod = DEFAULT_PERIOD;
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
            if (mRunnable != null)
            {
                mRunnable.run();
                sendMsgDelayed(mPeriod);
            } else
            {
                stop();
            }
        }
    }

    @Override
    public synchronized boolean isStarted()
    {
        return mIsStarted;
    }

    @Override
    public synchronized void setPeriod(long period)
    {
        if (period <= 0)
        {
            period = DEFAULT_PERIOD;
        }
        mPeriod = period;
    }

    @Override
    public synchronized long getPeriod()
    {
        return mPeriod;
    }

    @Override
    public void start(Runnable runnable)
    {
        start(0, mPeriod, runnable);
    }

    @Override
    public void start(long period, Runnable runnable)
    {
        start(0, period, runnable);
    }

    @Override
    public synchronized void start(long delay, long period, Runnable runnable)
    {
        stop();

        mIsStarted = true;
        mRunnable = runnable;

        setPeriod(period);
        sendMsgDelayed(delay);
    }

    private void sendMsgDelayed(long delay)
    {
        Message msg = mHandler.obtainMessage(MSG_WHAT);
        mHandler.sendMessageDelayed(msg, delay);
    }

    @Override
    public synchronized void stop()
    {
        mHandler.removeMessages(MSG_WHAT);
        mIsStarted = false;
    }
}
