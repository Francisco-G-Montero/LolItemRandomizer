package com.frommetoyou.lolitemrandomizer.common.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class LeagueOfLegendsApiModule {
    public final String BASE_URL="https://ddragon.leagueoflegends.com/cdn/11.1.1/data/es_MX/";
    //public final String API_KEY="RGAPI-838bff2c-9c6f-4217-b51a-31381e0ec68a";

    @Provides
    public OkHttpClient provideClient(){
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
 /*       HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", API_KEY).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                })
                .build();*/
    }
    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    private Gson getGson(){
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
    @Provides
    public LeagueOfLegendsApiService provideApiService(){
        return provideRetrofit(BASE_URL,provideClient()).create(LeagueOfLegendsApiService.class);
    }

}
