package doglandia.com.server;

import doglandia.com.server.model.Place;
import doglandia.com.server.model.PlaceLocateResult;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Thomas on 6/7/2015.
 */
public interface ServerInterface {

    @POST("/locationGuess/{user_id}")// send placeLocate result and get new Place
    void locationGuess(@Path("user_id")Integer userId, @Body PlaceLocateResult placeLocateResult, Callback<Place> callback);

    @POST("/getNewLocation/{user_id}") // get new place based on userId
    void getNewLocation(@Path("user_id")Integer userId,Callback<Place> callback);
}
