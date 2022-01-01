package yk.wechselkurs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView currencyRV;

    private ArrayList<CurrencyModel> currencyModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyRV = findViewById(R.id.idRVCurrency);

        currencyModelArrayList = new ArrayList<>();
        getRates("EUR", "USD");
        getRates("USD", "AUD");
        getRates("EUR", "GBP");
        getRates("EUR", "CNY");
        currencyModelArrayList.add(new CurrencyModel("EUR", "CZK", 24.88));


        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, currencyModelArrayList);

        //android cardview two columns
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        currencyRV.setLayoutManager(mLayoutManager);
        currencyRV.setAdapter(currencyAdapter);
    }

    public void getRates(String baseCurrency, String exchangeCurrency) {

        RetrofitInterface retrofitInterface = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);
        Call<JsonObject> call = retrofitInterface.getData(baseCurrency, exchangeCurrency);

        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject res = response.body();
                JsonPrimitive rate = res.getAsJsonObject().getAsJsonPrimitive("conversion_rate");
//                String rateString = rate.toString();
//                Log.d("RATE", rateString);
                currencyModelArrayList.add(new CurrencyModel(baseCurrency, exchangeCurrency, Double.parseDouble(rate.toString())));
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
            }

        });
    }
}
