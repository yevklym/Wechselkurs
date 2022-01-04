package yk.wechselkurs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrencyRates#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrencyRates extends Fragment {
    ArrayList<CurrencyModel> currencyModelArrayList;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CurrencyRates() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrencyRates.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrencyRates newInstance(String param1, String param2) {
        CurrencyRates fragment = new CurrencyRates();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency_rates, container, false);
        currencyModelArrayList = new ArrayList<>();
/*        getRates("EUR", "USD");
        getRates("USD", "AUD");
        getRates("EUR", "GBP");
        getRates("EUR", "CNY");*/
        currencyModelArrayList.add(new CurrencyModel("EUR", "CZK", 24.88));
        RecyclerView currencyRV;
        currencyRV = view.findViewById(R.id.RVCurrency);

        CurrencyAdapter currencyAdapter = new CurrencyAdapter(getActivity().getBaseContext(), currencyModelArrayList);

        //cardview two columns
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getBaseContext(), 2);
        currencyRV.setLayoutManager(mLayoutManager);

        currencyRV.setAdapter(currencyAdapter);

        return view;
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
                //Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
            }

        });
    }
}