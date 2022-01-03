package yk.wechselkurs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CurrencyModel> currencyModelArrayList;

    public CurrencyAdapter(Context context, ArrayList<CurrencyModel> currencyModelArrayList) {
        this.context = context;
        this.currencyModelArrayList = currencyModelArrayList;
    }

    @NonNull
    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.ViewHolder holder, int position) {
        CurrencyModel model = currencyModelArrayList.get(position);
        holder.baseCurrencyTV.setText(model.getBaseCurrency());
        holder.exchangeCurrencyTV.setText("/ " + model.getExchangeCurrency());
        holder.rateTV.setText(String.valueOf(model.getRate()));
    }

    @Override
    public int getItemCount() {
        return currencyModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView baseCurrencyTV, exchangeCurrencyTV, rateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            baseCurrencyTV = itemView.findViewById(R.id.TVBaseCurrency);
            exchangeCurrencyTV = itemView.findViewById(R.id.TVExchangeCurrency);
            rateTV = itemView.findViewById(R.id.TVRate);
        }
    }
}

