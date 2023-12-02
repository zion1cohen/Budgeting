package com.example.easyrecipes;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private Context context;
    private List<Transaction> transactions;
    private TransactionClickListener clickListener;

    public interface TransactionClickListener {
        void onTransactionClick(Transaction transaction);
    }
    public TransactionAdapter(@NonNull Context context, @NonNull List<Transaction> transactions, TransactionClickListener clickListener) {
        super(context, 0, transactions);
        this.context = context;
        this.transactions = transactions;
        this.clickListener = clickListener;
    }
    public TransactionAdapter(@NonNull Context context, @NonNull List<Transaction> transactions) {
        super(context, 0, transactions);
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the item layout if it's not recycled
            convertView = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);

            // Create a ViewHolder to hold references to the views
            viewHolder = new ViewHolder();
            viewHolder.textViewAmount = convertView.findViewById(R.id.textViewAmount);
            viewHolder.textViewDescription = convertView.findViewById(R.id.textViewDescription);
            viewHolder.textViewType = convertView.findViewById(R.id.textViewType);

            // Store the ViewHolder as a tag in the view
            convertView.setTag(viewHolder);
        } else {
            // If the view is recycled, retrieve the ViewHolder from the tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data for the current position
        Transaction transaction = transactions.get(position);

        // Bind the data to the views
        viewHolder.textViewAmount.setText(String.format("$%.2f", transaction.getAmount()));
        viewHolder.textViewDescription.setText(transaction.getDescription());
        viewHolder.textViewType.setText(transaction.isIncome() ? "Income" : "Expense");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onTransactionClick(transactions.get(position));
                }
            }
        });

        return convertView;
    }


    // ViewHolder pattern to improve performance
    private static class ViewHolder {
        TextView textViewAmount;
        TextView textViewDescription;
        TextView textViewType;
    }
}
