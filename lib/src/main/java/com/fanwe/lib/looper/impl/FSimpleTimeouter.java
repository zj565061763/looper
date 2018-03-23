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

import com.fanwe.lib.looper.FTimeouter;

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
        {
            mTimeoutRunnable.run();
        }
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
