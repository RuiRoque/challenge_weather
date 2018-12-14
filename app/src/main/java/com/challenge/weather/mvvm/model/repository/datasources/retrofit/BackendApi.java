package com.challenge.weather.mvvm.model.repository.datasources.retrofit;

import android.annotation.SuppressLint;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.challenge.weather.BuildConfig;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.CookieManager;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class BackendApi {

    //region Configuration
    private static final String BASE_URL = "https://api.openweathermap.org";
    private static final boolean IS_BASIC_AUTH_CONFIGURED = false;
    private static final String BASIC_AUTH_USERNAME = null;
    private static final String BASIC_AUTH_PASSWORD = null;
    private static final boolean IGNORE_SSL_HANDSHAKE = false;
    //endregion

    private OkHttpClient okHttpClient;

    public BackendApi(CookieManager cookieManager) {
        this.okHttpClient = createOkHttpClient(cookieManager);
    }

    private OkHttpClient createOkHttpClient(CookieManager cookieManager) {
        if (IGNORE_SSL_HANDSHAKE) {
            return createUnsafeOkHttpClient(cookieManager);
        } else {
            return createSafeOkHttpClient(cookieManager);
        }
    }

    private OkHttpClient createSafeOkHttpClient(CookieManager cookieManager) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .cookieJar(new JavaNetCookieJar(cookieManager))
//                .addInterceptor(createAuthenticationInterceptor(cookieManager))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(createHttpLoggingInterceptor());
        }

        addBasicAuthToOkHttpClient(okHttpClientBuilder);

        return okHttpClientBuilder.build();
    }

    private OkHttpClient createUnsafeOkHttpClient(CookieManager cookieManager) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            if (BuildConfig.DEBUG) {
                builder.addInterceptor(createHttpLoggingInterceptor());
            }

            return builder
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .cookieJar(new JavaNetCookieJar(cookieManager))
//                    .addInterceptor(createAuthenticationInterceptor(cookieManager))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private HttpLoggingInterceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(buildJacksonConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private JacksonConverterFactory buildJacksonConverter() {
        return JacksonConverterFactory.create(
                new ObjectMapper().enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
        );
    }

    <T> T createApiEndpoints(Class<T> service) {
        return createRetrofit().create(service);
    }

    private void addBasicAuthToOkHttpClient(OkHttpClient.Builder okHttpClient) {
        if (IS_BASIC_AUTH_CONFIGURED) {
            okHttpClient.authenticator((route, response) -> response
                    .request()
                    .newBuilder()
                    .header("Authorization", Credentials.basic(BASIC_AUTH_USERNAME, BASIC_AUTH_PASSWORD))
                    .build());
        }
    }

    String getFullUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
