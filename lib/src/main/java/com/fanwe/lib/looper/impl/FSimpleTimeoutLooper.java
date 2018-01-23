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

import com.fanwe.lib.looper.FLooper;
import com.fanwe.lib.looper.FTimeouter;

public class FSimpleTimeoutLooper implements FLooper, FTimeouter
{
    private FLooper mLooper = new FSimpleLooper();
    private FTimeouter mTimeouter = new FSimpleTimeouter();
    private Runnable mRunnable;

    @Override
    public boolean isStarted()
    {
        return mLooper.isStarted();
    }

    @Override
    public void setPeriod(long period)
    {
        mLooper.setPeriod(period);
    }

    @Override
    public long getPeriod()
    {
        return mLooper.getPeriod();
    }

    @Override
    public FSimpleTimeoutLooper start(Runnable runnable)
    {
        start(getPeriod(), runnable);
        return this;
    }

    @Override
    public FSimpleTimeoutLooper start(long period, Runnable runnable)
    {
        start(0, period, runnable);
        return this;
    }

    @Override
    public FSimpleTimeoutLooper start(long delay, long period, Runnable runnable)
    {
        mRunnable = runnable;
        mLooper.start(delay, period, mInternalRunnable);
        mTimeouter.startTimeout();
        return this;
    }

    private Runnable mInternalRunnable = new Runnable()
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
    public FSimpleTimeoutLooper stop()
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
    public FSimpleTimeoutLooper setTimeoutRunnable(Runnable timeoutRunnable)
    {
        mTimeouter.setTimeoutRunnable(timeoutRunnable);
        return this;
    }

    @Override
    public FSimpleTimeoutLooper runTimeoutRunnable()
    {
        mTimeouter.runTimeoutRunnable();
        return this;
    }

    @Override
    public FSimpleTimeoutLooper setTimeout(long timeout)
    {
        mTimeouter.setTimeout(timeout);
        return this;
    }

    @Override
    public FSimpleTimeoutLooper startTimeout()
    {
        mTimeouter.startTimeout();
        return this;
    }

    @Override
    public FSimpleTimeoutLooper stopTimeout()
    {
        mTimeouter.stopTimeout();
        return this;
    }

}
