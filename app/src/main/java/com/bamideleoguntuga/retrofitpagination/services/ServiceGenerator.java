package com.bamideleoguntuga.retrofitpagination.services;

import android.content.Context;
import android.text.TextUtils;

import com.wealdtech.hawk.HawkCredentials;

import java.util.Iterator;

import com.bamideleoguntuga.retrofitpagination.R;
//import com.bamideleoguntuga.retrofitpagination.converter.PolymorphicCustomConverter;
//import com.bamideleoguntuga.retrofitpagination.services.models.AccessToken;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    //public static String apiBaseUrl = "http://10.0.2.2:3000/api/";
    public static String apiBaseUrl = "https://api.github.com/";

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    //.addConverterFactory(SimpleXmlConverterFactory.create())
                    //.addConverterFactory(new PolymorphicCustomConverter())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(apiBaseUrl);

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    // No need to instantiate this class.
    private ServiceGenerator(Context context) {
        if (TextUtils.isEmpty(apiBaseUrl)) {
            apiBaseUrl = context.getString(R.string.server_base_url);
        }
    }



    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, "");
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                // remove existing interceptors
                Iterator<Interceptor> interceptors = httpClient.interceptors().iterator();
                while (interceptors.hasNext()) {
                    if (interceptors.next() instanceof AuthenticationInterceptor) {
                        interceptors.remove();
                    }
                }

                // add new interceptor & update retrofit
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return create(serviceClass);
    }



    private static <S> S create(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);

            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }

    public static Retrofit retrofit() {
        return retrofit;
    }
}
