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
package com.fanwe.lib.looper;

/**
 * 循环触发接口
 */
public interface FLooper
{
    /**
     * 是否已经开始循环
     *
     * @return
     */
    boolean isStarted();

    /**
     * 返回循环间隔
     *
     * @return
     */
    long getInterval();

    /**
     * 设置循环触发间隔，大于0才有效
     *
     * @param interval 循环触发间隔(毫秒)
     */
    void setInterval(long interval);

    /**
     * 开始循环
     *
     * @param runnable
     */
    void start(Runnable runnable);

    /**
     * 延迟多少毫秒后开始循环
     *
     * @param delayMillis
     * @param runnable
     */
    void startDelayed(long delayMillis, Runnable runnable);

    /**
     * 停止循环
     */
    void stop();
}