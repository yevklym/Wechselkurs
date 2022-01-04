package yk.wechselkurs;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CurrencyModel> currencyModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.currencyRates, R.id.converter).build();
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        currencyModelArrayList = new ArrayList<>();
/*        getRates("EUR", "USD");
        getRates("USD", "AUD");
        getRates("EUR", "GBP");
        getRates("EUR", "CNY");*/
        currencyModelArrayList.add(new CurrencyModel("EUR", "CZK", 24.88));

        RecyclerView currencyRV;

        currencyRV = findViewById(R.id.RVCurrency);

        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, currencyModelArrayList);

        //cardview two columns
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
