package net.testaholic.brewery.rest;

import net.testaholic.brewery.app.App;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by williamrussell on 6/12/16.
 */
public class RestClient {
    private static Retrofit retrofit;
    private static BreweryService service;

    private static Retrofit getRetrofit() {
        if (retrofit == null) {

            retrofit =  new Retrofit.Builder()
                    .client(createClient().build())
                    .baseUrl(App.getInstance().getDomainUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static BreweryService getBreweryService(){
        if(service == null){
            service = getRetrofit().create(BreweryService.class);
        }
        return service;
    }

    public static OkHttpClient.Builder createClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder client = new OkHttpClient().newBuilder();

                client.addInterceptor(loggingInterceptor);
                client.connectTimeout(15, TimeUnit.SECONDS);
                client.readTimeout(15, TimeUnit.SECONDS);
                client.writeTimeout(15, TimeUnit.SECONDS);
                return client;
    }
}

