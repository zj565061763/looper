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

import com.fanwe.lib.looper.ISDLooper;

public class SDSimpleLooper implements ISDLooper
{
    private static final int MSG_WHAT = 1990;

    private Runnable mRunnable;
    private long mPeriod;
    private boolean mIsStarted = false;
    private boolean mIsCancelled = false;
    private Handler mHandler;

    public SDSimpleLooper()
    {
        this(Looper.getMainLooper());
    }

    public SDSimpleLooper(Looper looper)
    {
        mHandler = new Handler(looper)
        {
            public void handleMessage(Message msg)
            {
                synchronized (SDSimpleLooper.this)
                {
                    if (mIsCancelled)
                    {
                        return;
                    }

                    if (mRunnable != null)
                    {
                        mRunnable.run();
                        SDSimpleLooper.this.sendMessageDelayed(mPeriod);
                    } else
                    {
                        stop();
                    }
                }
            }
        };
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
        this.mPeriod = period;
    }

    @Override
    public ISDLooper start(Runnable runnable)
    {
        start(0, DEFAULT_PERIOD, runnable);
        return this;
    }

    @Override
    public ISDLooper start(long period, Runnable runnable)
    {
        start(0, period, runnable);
        return this;
    }

    @Override
    public synchronized ISDLooper start(long delay, long period, Runnable runnable)
    {
        stop();

        this.mIsStarted = true;
        this.mIsCancelled = false;
        this.mRunnable = runnable;

        setPeriod(period);

        sendMessageDelayed(delay);
        return this;
    }

    private void sendMessageDelayed(long delay)
    {
        Message msg = mHandler.obtainMessage(MSG_WHAT);
        mHandler.sendMessageDelayed(msg, delay);
    }

    @Override
    public synchronized ISDLooper stop()
    {
        mHandler.removeMessages(MSG_WHAT);
        mIsStarted = false;
        mIsCancelled = true;
        return this;
    }
}
