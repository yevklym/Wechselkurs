package yk.wechselkurs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView currencyRV;

    private ArrayList<CurrencyModel> currencyModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currencyRV = findViewById(R.id.idRVCurrency);

        currencyModelArrayList = new ArrayList<>();
        currencyModelArrayList.add(new CurrencyModel("EUR", "USD", 1.33));
        currencyModelArrayList.add(new CurrencyModel("EUR", "GBP", 1.33));
        currencyModelArrayList.add(new CurrencyModel("EUR", "CNY", 1.33));


        CurrencyAdapter currencyAdapter = new CurrencyAdapter(this, currencyModelArrayList);

/*        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        currencyRV.setLayoutManager(linearLayoutManager);

        currencyRV.setAdapter(currencyAdapter);*/

        //android cardview two columns
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        currencyRV.setLayoutManager(mLayoutManager);
        currencyRV.setAdapter(currencyAdapter);

    }
}