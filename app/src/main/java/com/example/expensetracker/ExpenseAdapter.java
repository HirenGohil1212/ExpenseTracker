package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {
    private Context context;
    private OnItemsClick onItemsClick;
    private List<ExpenseManager> expenseManagerList;

    public ExpenseAdapter(Context context,OnItemsClick onItemsClick){
        this.context = context;
        expenseManagerList=new ArrayList<>();
        this.onItemsClick=onItemsClick;
    }

    public void add(ExpenseManager expenseManager){
        expenseManagerList.add(expenseManager);
        notifyDataSetChanged();
    }

    public void clear(){
        expenseManagerList.clear();
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExpenseManager expenseManager = expenseManagerList.get(position);
        holder.note.setText(expenseManager.getNote());
        holder.category.setText(expenseManager.getCategory());
        holder.amount.setText(String.valueOf(expenseManager.getAmount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemsClick.onClick(expenseManager);

            }
        });

    }

    @Override
    public int getItemCount() {
        return expenseManagerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView note,category,amount,date;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            note=itemView.findViewById(R.id.note);
            category=itemView.findViewById(R.id.category);
            amount=itemView.findViewById(R.id.amount);
            date=itemView.findViewById(R.id.date);


        }
    }

}
