package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.expensetracker.databinding.ActivityAddExpenseBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {

    ActivityAddExpenseBinding binding;
    private String type;
    private ExpenseManager expenseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        type = getIntent().getStringExtra("type");
        expenseManager = (ExpenseManager) getIntent().getSerializableExtra("model");

        if(type == null){
            type=expenseManager.getType();
            binding.amount.setText(String.valueOf(expenseManager.getAmount()));
            binding.category.setText(expenseManager.getCategory());
            binding.note.setText(expenseManager.getNote());

        }



        if(type.equals("Income")){
            binding.incomeRadio.setChecked(true);
        }else {
            binding.expenseRadio.setChecked(true);
        }

        binding.incomeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "Income";
            }
        });
        binding.expenseRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "Expense";
            }
        });

    }

    //To Show Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(expenseManager==null){
            menuInflater.inflate(R.menu.add_menu,menu);
        }else {
            menuInflater.inflate(R.menu.update_menu,menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.saveExpense){
            if(type!=null){
                createExpense();
            }else {
                updateExpense();
            }

            return true;
        }
        if(id==R.id.deleteExpense){
            deleteExpense();

        }
        return false;
    }

    private void deleteExpense() {
        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseManager.getExpenseId())
                .delete();
        finish();
    }

    private void createExpense() {

        String expenceId = UUID.randomUUID().toString();
        String amount = binding.amount.getText().toString();
        String note = binding.note.getText().toString();
        String category = binding.category.getText().toString();

        boolean incomeChecked = binding.incomeRadio.isChecked();

        if(incomeChecked){
            type="Icome";
        }else {
            type="Expense";
        }

        if(amount.trim().length() == 0 ){
            binding.amount.setError("Empty");
            return;
        }

        ExpenseManager expenseManager = new ExpenseManager(expenceId,note,category,type,Long.parseLong(amount), Calendar.getInstance().getTimeInMillis(), FirebaseAuth.getInstance().getUid());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenceId)
                .set(expenceId);

        finish();




        
    }
    private void updateExpense() {

        String expenceId = expenseManager.getExpenseId();
        String amount = binding.amount.getText().toString();
        String note = binding.note.getText().toString();
        String category = binding.category.getText().toString();

        boolean incomeChecked = binding.incomeRadio.isChecked();

        if(incomeChecked){
            type="Icome";
        }else {
            type="Expense";
        }

        if(amount.trim().length() == 0 ){
            binding.amount.setError("Empty");
            return;
        }

        ExpenseManager manager = new ExpenseManager(expenceId,note,category,type,Long.parseLong(amount), expenseManager.getTime(), FirebaseAuth.getInstance().getUid());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenceId)
                .set(manager);

        finish();





    }
}