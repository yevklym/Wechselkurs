package yk.wechselkurs;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {

    String BASE_URL = "https://v6.exchangerate-api.com/";

    @GET("v6/API_KEY/pair/{baseCurrency}/{exchangeCurrency}")
    Call<JsonObject> getData(@Path("baseCurrency") String baseCurrency,
                                @Path("exchangeCurrency") String exchangeCurrency);
}
