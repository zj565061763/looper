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

import com.fanwe.lib.looper.ISDLooper;
import com.fanwe.lib.looper.ISDTimeouter;

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
