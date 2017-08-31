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
package com.fanwe.library.looper;

/**
 * 循环触发接口
 */
public interface ISDLooper
{
    /**
     * 默认循环触发间隔
     */
    long DEFAULT_PERIOD = 300;

    /**
     * 是否正在循环中
     *
     * @return
     */
    boolean isRunning();

    /**
     * 设置循环触发间隔
     *
     * @param period
     */
    void setPeriod(long period);

    /**
     * 开始循环
     *
     * @param runnable 循环触发对象
     * @return
     */
    ISDLooper start(Runnable runnable);

    /**
     * 开始循环
     *
     * @param period   循环触发间隔
     * @param runnable 循环触发对象
     * @return
     */
    ISDLooper start(long period, Runnable runnable);

    /**
     * 开始循环
     *
     * @param delay    延迟多久开始循环
     * @param period   循环触发间隔
     * @param runnable 循环触发对象
     * @return
     */
    ISDLooper start(long delay, long period, Runnable runnable);

    /**
     * 停止循环
     *
     * @return
     */
    ISDLooper stop();

}