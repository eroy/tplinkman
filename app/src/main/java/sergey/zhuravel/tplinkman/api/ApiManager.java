package sergey.zhuravel.tplinkman.api;

import android.util.Log;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import sergey.zhuravel.tplinkman.constant.ApiConstant;


public class ApiManager {
    private Retrofit mRetrofit;
    private InfoService infoService;


    public ApiManager(String baseUrl) {
        initRetrofit(baseUrl);
        initServices();
    }

    private void initRetrofit(String baseUrl) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor((message -> Log.e("LOGGING",message)));
//        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(logging)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }


    public InfoService getInfoService() {
        return infoService;
    }

    private void initServices() {
        if (mRetrofit != null) {
        infoService = mRetrofit.create(InfoService.class);


        }

    }

    private GsonConverterFactory createGsonConverter() {
        GsonBuilder builder = new GsonBuilder()
                .setLenient()
                .serializeNulls();
        return GsonConverterFactory.create(builder.create());
    }


}