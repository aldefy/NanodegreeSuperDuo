package barqsoft.footballscores.api;

import com.cc.mieon.user.utils.Logger;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by aditlal on 24/11/15.
 */
public class ApiGenerator {

    public static String BASE_URL = "https://api.parse.com/1/";

    // No need to instantiate this class.
    private ApiGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
                request.addHeader("X-Parse-REST-API-Key", "LNOoEfmtrBkMbs88H7yxcpGsQOzht2hYf2tAzsMS");
                request.addHeader("X-Parse-Application-Id", "nfHBueoc5meotFcoeDoeQYODYEmPYF3NmX0xl4dA");
            }
        };
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Logger.i("Retro", msg);
                    }
                })
                .setRequestInterceptor(requestInterceptor)
                .setClient(new OkClient(okHttpClient));

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }

    public static <S> S createRevocableService(Class<S> serviceClass) {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept", "application/json");
                request.addHeader("X-Parse-REST-API-Key", "LNOoEfmtrBkMbs88H7yxcpGsQOzht2hYf2tAzsMS");
                request.addHeader("X-Parse-Application-Id", "nfHBueoc5meotFcoeDoeQYODYEmPYF3NmX0xl4dA");
                request.addHeader("X-Parse-Revocable-Session", "1");
            }
        };


        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Logger.i("Retro", msg);
                    }
                })
                .setRequestInterceptor(requestInterceptor)
                .setClient(new OkClient(new OkHttpClient()));

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }

}

