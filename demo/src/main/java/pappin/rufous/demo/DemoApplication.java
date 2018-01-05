package pappin.rufous.demo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import okhttp3.OkHttpClient;
import pappin.rufous.util.LogUtil;

/**
 * Created by bpappin on 2017-12-09.
 */

public class DemoApplication extends Application {
    private static final String TAG = LogUtil.tag("DemoApplication");

    @Override
    public void onCreate() {
        super.onCreate();


        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        //        OkHttpClient clientNormal = null;
        //        OkHttpClient clientAuthenticated = null;


//        HttpLoggingInterceptor logIntercepter = new HttpLoggingInterceptor();
//        logIntercepter.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        Log.i(TAG, "Logging level set to: " + logIntercepter
//                .getLevel()
//        builder
//                .interceptors()
//                .add(logIntercepter);

        OkHttpClient okHttpClient = builder.build();

//        DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig
//                .newBuilder(this)
//                .setMaxCacheSizeOnVeryLowDiskSpace(1024)
//                .setVersion(0)
//                .build();
        //        DiskCacheConfig smallImageDiskCacheConfig = DiskCacheConfig.newBuilder(this).setMaxCacheSizeOnVeryLowDiskSpace(1024).build();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, okHttpClient)
                //                                                        .setBitmapMemoryCacheParamsSupplier(bitmapCacheParamsSupplier)
                //                                                        .setCacheKeyFactory(cacheKeyFactory)
                //                                                        .setDownsampleEnabled(true)
                //                                                        .setWebpSupportEnabled(true)
                //                                                        .setEncodedMemoryCacheParamsSupplier(encodedCacheParamsSupplier)
                //                                                        .setExecutorSupplier(executorSupplier)
                //                                                        .setImageCacheStatsTracker(imageCacheStatsTracker)
                //                                                        .setMainDiskCacheConfig(mainDiskCacheConfig)
                //                                                        .setMemoryTrimmableRegistry(memoryTrimmableRegistry)
                //                                                        .setNetworkFetchProducer(networkFetchProducer)
                //                                                        .setPoolFactory(poolFactory)
                //                                                        .setProgressiveJpegConfig(progressiveJpegConfig)
                //                                                        .setRequestListeners(requestListeners)
                //                                                        .setSmallImageDiskCacheConfig(smallImageDiskCacheConfig)
                .build();
        Fresco.initialize(this, config);
    }
}
