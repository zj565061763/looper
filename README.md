
## Gradle
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
        compile 'com.github.zj565061763:looper:1.0.5'
}

```

## SDSimpleLooper
应用场景：<br>
* 轮播图每隔一秒切换一页
* 定时间隔刷新，定时间隔请求接口
* 倒计时，可以实现倒计时每隔一秒更新一下剩余时长，但是会存在误差，如果需要准确的倒计时，建议用系统的android.os.CountDownTimer
* 直播间聊天列表有大量数据的时候，每隔几百毫秒取数据展示
* 直播间循环遍历礼物view的播放状态，从礼物队列中取数据展示
* ...

该类是库中已经实现ISDLooper接口的实现类，内部基于Handler实现，较Timer性能消耗更少，构造方法支持传入Looper对象来指定要循环的线程，默认在主线程循环<br>
```java
private void testSDSimpleLooper()
{
    //延迟500毫秒后，每隔1000毫秒触发一次设置的Runnable对象
    ISDLooper looper = new SDSimpleLooper();
    looper.start(500, 1000, new Runnable()
    {
        @Override
        public void run()
        {
            Toast.makeText(getApplication(), "toast", 0).show();
        }
    });
    // looper.stop(); //停止循环，在需要停止的地方停止，比如ui销毁
}
```

## SDSimpleTimeoutLooper
```java
private void testSDSimpleTimeoutLooper()
{
    SDSimpleTimeoutLooper looper = new SDSimpleTimeoutLooper();
    looper.setTimeout(5 * 1000) //设置超时时间
            .setTimeoutRunnable(new Runnable() //设置超时后需要执行的Runnable
            {
                @Override
                public void run()
                {
                    Toast.makeText(getApplication(), "timeout", 0).show();
                }
            })
            //延迟500毫秒，每隔1000毫秒触发一次Runnable，触发的同时会进行是否超时的判断，如果超时，执行超时Runnable
            .start(500, 1000, new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(getApplication(), "run", 0).show();
                }
            });
    // looper.stop(); //停止循环，在需要停止的地方停止，比如ui销毁
}
```

## SDWaitRunner
```java
/**
 * 等待某个条件成立后需要执行的Runnable
 */
private void testSDWaitRunner()
{
    SDWaitRunner waitRunner = new SDWaitRunner()
            .run(new Runnable() //设置需要等待执行的Runnable
            {
                @Override
                public void run()
                {
                    Toast.makeText(getApplication(), "run", 0).show();
                }
            })
            .condition(new SDWaitRunner.Condition() //设置Runnable执行条件
            {
                @Override
                public boolean canRun()
                {
                    // 返回true则Runnable立即执行，返回false则继续等待，如果超时会执行超时Runnable
                    return false;
                }
            })
            .setTimeout(5 * 1000)//设置等待超时时间
            .setTimeoutRunnable(new Runnable() //设置超时需要执行的Runnable
            {
                @Override
                public void run()
                {
                    Toast.makeText(getApplication(), "timeout", 0).show();
                }
            })
            .startWait(100); //开始等待，每100毫秒检测一次Runnable执行条件是否成立

    // waitRunner.stopWait(); //停止等待，在需要停止的地方停止，比如ui销毁
}
```
## ISDLooper
```java
public interface ISDLooper
{

    /**
     * 默认循环触发间隔
     */
    public static final long DEFAULT_PERIOD = 300;

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
```
## ISDTimeouter
```java
public interface ISDTimeouter
{
    /**
     * 默认超时
     */
    long DEFAULT_TIMEOUT = 10 * 1000;

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
    ISDTimeouter setTimeoutRunnable(Runnable timeoutRunnable);

    /**
     * 执行超时需要执行的Runnable
     */
    ISDTimeouter runTimeoutRunnable();

    /**
     * 设置超时时间
     *
     * @param timeout 大于0超时才有效
     * @return
     */
    ISDTimeouter setTimeout(long timeout);

    /**
     * 开始超时计时
     */
    ISDTimeouter startTimeout();

    /**
     * 停止超时计时
     */
    ISDTimeouter stopTimeout();
}
```

