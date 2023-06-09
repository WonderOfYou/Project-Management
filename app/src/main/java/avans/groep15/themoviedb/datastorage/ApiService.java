package avans.groep15.themoviedb.datastorage;

import avans.groep15.themoviedb.domain.Account;
import avans.groep15.themoviedb.domain.AddMovie;
import avans.groep15.themoviedb.domain.LoginData;
import avans.groep15.themoviedb.domain.WatchList;
import avans.groep15.themoviedb.domain.responses.ListResult;
import avans.groep15.themoviedb.domain.responses.LoginResult;
import avans.groep15.themoviedb.domain.responses.MovieResult;
import avans.groep15.themoviedb.domain.responses.SessionResult;
import avans.groep15.themoviedb.domain.responses.StatusResult;
import avans.groep15.themoviedb.domain.responses.UserListsResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/popular")
    Call<MovieResult> getMovies(@Query("api_key")String apiKey);

    @GET("authentication/token/new")
    Call<LoginResult> getToken(@Query("api_key")String apiKey);

    @POST("authentication/token/validate_with_login")
    Call<LoginResult> login(@Query("api_key")String apiKey, @Body() LoginData loginData);

    @POST("authentication/session/new")
    Call<SessionResult> getSessionId(@Query("api_key")String apiKey, @Body() LoginResult requestToken);

    @GET("account")
    Call<Account> getUserId(@Query("api_key")String apiKey, @Query("session_id")String session_id);

    @POST("list")
    Call<ListResult> createList(@Query("api_key")String apiKey, @Query("session_id")String session_id, @Body() WatchList watchList);
    
    @POST("list/{listId}/add_item")
    Call<StatusResult> addToList(@Path("listId") int listId, @Query("api_key")String apiKey, @Query("session_id")String session_id, @Body AddMovie addMovie);

    @GET("account/{accountId}/lists")
    Call<UserListsResult> getLists(@Path("accountId") int accountId, @Query("api_key")String apiKey, @Query("session_id")String session_id);
}
