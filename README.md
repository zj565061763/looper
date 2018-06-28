## About
应用场景：<br>
* 轮播图每隔一秒切换一页
* 定时间隔刷新，定时间隔请求接口
* 倒计时，可以实现倒计时每隔一秒更新一下剩余时长，但是会存在误差，如果需要准确的倒计时，建议用系统的android.os.CountDownTimer
* 直播间聊天列表有大量数据的时候，每隔几百毫秒取数据展示
* 直播间循环遍历礼物view的播放状态，从礼物队列中取数据展示
* ...

# Gradle
[![](https://jitpack.io/v/zj565061763/looper.svg)](https://jitpack.io/#zj565061763/looper)

# 简单demo

FSimpleLooper是库中已经实现FLooper接口的实现类，内部基于Handler实现，较Timer性能消耗更少，构造方法支持传入Looper对象来指定要循环的线程，默认在主线程循环<br>
```java
public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private final FSimpleLooper mLooper = new FSimpleLooper();
    private final FSimpleTimeoutLooper mTimeoutLooper = new FSimpleTimeoutLooper();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testSimpleLooper();
        testSimpleTimeoutLooper();
    }

    private void testSimpleLooper()
    {
        // 设置每隔1000毫秒触发一次
        mLooper.setInterval(1000);
        // 设置要循环触发的runnable
        mLooper.setLoopRunnable(new Runnable()
        {
            @Override
            public void run()
            {
                Log.i(TAG, "FSimpleLooper run");
            }
        });
        // 开始循环
        mLooper.start();
    }

    private void testSimpleTimeoutLooper()
    {
        // 设置超时时间，默认10秒
        mTimeoutLooper.setTimeout(5 * 1000);
        // 设置超时需要执行的Runnable
        mTimeoutLooper.setTimeoutRunnable(new Runnable()
        {
            @Override
            public void run()
            {
                Log.e(TAG, "timeout");
            }
        });
        // 设置每隔1000毫秒触发一次
        mTimeoutLooper.setInterval(1000);
        // 设置要循环触发的runnable
        mTimeoutLooper.setLoopRunnable(new Runnable()
        {
            @Override
            public void run()
            {
                Log.e(TAG, "FSimpleTimeoutLooper run");
            }
        });
        // 开始循环
        mTimeoutLooper.start();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // 停止循环
        mLooper.stop();
        // 停止循环
        mTimeoutLooper.stop();
    }
}
```
```java
public interface Looper
{
    /**
     * 设置状态变化回调
     *
     * @param callback
     */
    void setOnStateChangeCallback(OnStateChangeCallback callback);

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
     * 设置要循环触发的runnable
     *
     * @param runnable
     */
    void setLoopRunnable(Runnable runnable);

    /**
     * 开始循环
     *
     * @return
     */
    boolean start();

    /**
     * 延迟多少毫秒后开始循环
     *
     * @param delayMillis
     * @return
     */
    boolean startDelayed(long delayMillis);

    /**
     * 停止循环
     */
    void stop();

    @Deprecated
    void start(Runnable runnable);

    @Deprecated
    void startDelayed(long delayMillis, Runnable runnable);

    interface OnStateChangeCallback
    {
        /**
         * 循环是否开始状态变化回调
         *
         * @param started
         */
        void onStateChanged(boolean started);
    }
}
```

```java
public interface Timeouter
{
    /**
     * 获得设置的超时时间
     */
    long getTimeout();

    /**
     * 是否超时
     */
    boolean isTimeout();

    /**
     * 设置超时需要执行的Runnable
     */
    void setTimeoutRunnable(Runnable timeoutRunnable);

    /**
     * 执行超时需要执行的Runnable
     */
    void runTimeoutRunnable();

    /**
     * 设置超时时间(毫秒)
     *
     * @param timeout
     */
    void setTimeout(long timeout);

    /**
     * 开始超时计时
     */
    void startTimeout();
}
```
