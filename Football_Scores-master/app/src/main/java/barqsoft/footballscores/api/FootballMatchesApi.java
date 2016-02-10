package barqsoft.footballscores.api;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by aditlal on 01/02/16.
 */
public interface FootballMatchesApi {

    @GET("/fixtures")
    void getMatchesForTimeFrame(@Query("timeFrame") String timeFrame, Callback<Response> callback);
}
