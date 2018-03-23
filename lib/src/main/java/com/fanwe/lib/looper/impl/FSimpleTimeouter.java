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
    private long mTimeout;
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
    public FTimeouter setTimeoutRunnable(Runnable timeoutRunnable)
    {
        mTimeoutRunnable = timeoutRunnable;
        return this;
    }

    @Override
    public FTimeouter runTimeoutRunnable()
    {
        if (mTimeoutRunnable != null)
        {
            mTimeoutRunnable.run();
        }
        return this;
    }

    @Override
    public FTimeouter setTimeout(long timeout)
    {
        mTimeout = timeout;
        return this;
    }

    @Override
    public FTimeouter startTimeout()
    {
        mStartTime = System.currentTimeMillis();
        return this;
    }
}
