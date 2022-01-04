package yk.wechselkurs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Converter#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Converter extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Spinner fromDropdown;
    Spinner toDropdown;
    Button convertButton;
    TextView convertResult;
    EditText convertAmount;


    public Converter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Converter.
     */
    // TODO: Rename and change types and number of parameters
    public static Converter newInstance(String param1, String param2) {
        Converter fragment = new Converter();
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
        //return inflater.inflate(R.layout.fragment_converter, container, false);

        View view = inflater.inflate(R.layout.fragment_converter, container, false);

        fromDropdown = (Spinner) view.findViewById(R.id.SPFrom);
        toDropdown = (Spinner) view.findViewById(R.id.SPTo);
        convertButton = (Button) view.findViewById(R.id.ButtonConvert);
        convertResult = (TextView) view.findViewById(R.id.TVResult);
        convertAmount = (EditText) view.findViewById(R.id.ETAmount);

        String[] dropDownList = {"USD", "EUR", "GBP", "PLN", "AUD", "CAD"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, dropDownList);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toDropdown.setAdapter(arrayAdapter);
        fromDropdown.setAdapter(arrayAdapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RetrofitInterface retrofitInterface = RetrofitClient.getRetrofitInstance().create(RetrofitInterface.class);
                Call<JsonObject> call = retrofitInterface.getData(fromDropdown.getSelectedItem().toString(), toDropdown.getSelectedItem().toString());

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        JsonPrimitive rate = res.getAsJsonObject().getAsJsonPrimitive("conversion_rate");
                        double amount = Double.parseDouble(convertAmount.getText().toString());
                        double rateDouble = Double.parseDouble(rate.toString());
                        double result = amount * rateDouble;
                        convertResult.setText(String.valueOf(result));
//                String rateString = rate.toString();
//                Log.d("RATE", rateString);
                    }

                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        //Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
                    }

                });
            }


        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    public void convert() {

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}