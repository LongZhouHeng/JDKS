package com.jdruanjian.jdks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.hs.nohttp.Logger;
import com.hs.nohttp.NoHttp;
import com.hs.nohttp.rest.RequestQueue;
import com.jdruanjian.jdks.utils.CrashHandler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class BaseApplication extends Application {
    // 缓存存放地址
    public static final String CHACHE_FILE = "JDLot";
    private final static int MEMORY_CACHE_SIZE = 10 * 1024 * 1024;
    private final static int MAX_IMG_WIDTH = 480;
    private final static int MAX_IMG_HEIGHT = 800;
    private final static int DISC_CACHE_SIZE = 50 * 1024 * 1024;
    private final static int DISC_CACHE_FILE_COUNT = 100;
    private final static int CONNECT_TIMEOUT = 5 * 1000;
    private final static int READ_TIMEOUT = 30 * 1000;
    private static BaseApplication instance;
//    public static com.android.volley.RequestQueue queue;
    private SharedPreferences mPrefs;

//    private IWXAPI api;
    // 默认图片属性
    private DisplayImageOptions mDisplayDefaultOptions;
    // 请求队列
    private RequestQueue mRequestQueue;
    // 版本名
    private String versionName;
    // imei
    private String imei;
    private Context mContext;
    public static BaseApplication getInst() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //JPushInterface.setDebugMode(true);
   //     JPushInterface.init(this);
 //       UMShareAPI.get(this);//初始化sdk
   //     Config.DEBUG = false;//可以弹出对话框告诉我们什么地方出错了，不写这句话的话，要费时间找bug的
 //       queue = Volley.newRequestQueue(getApplicationContext()); // 实例化RequestQueue对象
        instance = this;
        mPrefs = this.getSharedPreferences(CHACHE_FILE, Context.MODE_PRIVATE);
        try {
            versionName = this.getPackageManager().getPackageInfo(
                    this.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        NoHttp.initialize(instance);
        Logger.setTag(Constants.TAG);
        Logger.setDebug(Constants.DEVELOPER_MODE);// 开始NoHttp的调试模式,
        // 这样就能看到请求过程和日志
        // 捕捉程序崩溃
        CrashHandler.getInstance().init(getApplicationContext(), versionName);
        // 初始化ImageLoader
        initImageLoader();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        //OKGO配置
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        params.put("commonParamsKey2", "这里支持中文参数");
        //-----------------------------------------------------------------------------------//


        //必须调用初始化
        OkGo.init(this);

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()

                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.INFO, true)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)

                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
                  //  .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效

                    //可以设置https的证书,以下几种方案根据需要自己设置
                    .setCertificates()                                  //方法一：信任所有证书,不安全有风险
//              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
//              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//

                    //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//               .setHostnameVerifier(new SafeHostnameVerifier())

                    //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        return chain.proceed(chain.request());
//                    }
//                })

                    //这两行同上，不需要就不要加入
                    .addCommonHeaders(headers)  //设置全局公共头
                    .addCommonParams(params);   //设置全局公共参数

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    {
 //       PlatformConfig.setWeixin("wxcc915e0f91f89504 ","e1e88b0f1183e470f381bd77077f80c4 ");
  //      PlatformConfig.setQQZone("1106444980","2mF5UTpkJOVT51WD");
    }
  /*  public static com.android.volley.RequestQueue getHttpQueue() {
        return queue;
    }*/
    /**打开的activity**/
    private List<Activity> activities = new ArrayList<Activity>();
    /**
     * 应用退出，结束所有的activity
     */
    public void exit(){
        for (Activity activity : activities) {
            if (activity!=null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : activities) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    /**
     * 请求队列
     * */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = NoHttp.newRequestQueue();
        return mRequestQueue;
    }

    /**
     * 唯一标志
     * */
    @SuppressLint("MissingPermission")
    public String getDeviceId() {
        if (TextUtils.isEmpty(imei)) {
            TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            imei = TelephonyMgr.getDeviceId();
        }
        return imei;
    }

    public DisplayImageOptions getDisplayImageOptions() {
        if (mDisplayDefaultOptions == null) {
            mDisplayDefaultOptions = new DisplayImageOptions.Builder()
                    .displayer(new FadeInBitmapDisplayer(400))
                    //  .showStubImage(R.drawable.icon)
                    .showImageForEmptyUri(R.drawable.bg_default)
                    .cacheInMemory(true).cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.ARGB_8888).build();
        }
        return mDisplayDefaultOptions;
    }

    // 配置ImageLoader
    private void initImageLoader() {

        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), Constants.IMAGE_CACHE_SDCARD_PATH);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(MAX_IMG_WIDTH, MAX_IMG_HEIGHT)
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(MEMORY_CACHE_SIZE))
                .memoryCacheSize(MEMORY_CACHE_SIZE)
                .discCacheSize(DISC_CACHE_SIZE)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(DISC_CACHE_FILE_COUNT) // 缓存的文件数量
                .discCache(new UnlimitedDiscCache(cacheDir)) // 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(
                        new BaseImageDownloader(this, CONNECT_TIMEOUT,
                                READ_TIMEOUT)).build();
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }

    /**
     * 当前应用的文件缓存SharedPreferences引用
     * */
    public SharedPreferences getAppPreferences() {
        return mPrefs;
    }

    /**
     * 默认图片的加载配置
     * */
    public final DisplayImageOptions getDefaultDisplayImageOptions() {
        return mDisplayDefaultOptions;
    }

    /**
     * 新安装应用或者更新版本时，需要显示导航页面一次
     * */
    public boolean showGuide() {
        String version = mPrefs.getString(Constants.CURRENT_VERSION, "");
        return !versionName.equals(version);
    }

    /**
     * 导航页面启动过一次之后，不在显示，除非更新版本。
     * */
    public void finishGuide() {
        mPrefs.edit().putString(Constants.CURRENT_VERSION, versionName)
                .commit();
    }

    /**
     * 获取版本名
     * */
    public String getVersionName() {
        return versionName;
    }
}