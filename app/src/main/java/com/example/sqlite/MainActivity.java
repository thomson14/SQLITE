package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txt_name,txt_surname,txt_marks,txt_id;
    Button btn_addDATA,btn_VIEW_ALL,btn_update,btn_delete;

    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_name = findViewById(R.id.SQLName);
        txt_surname = findViewById(R.id.SQLSurname);
        txt_marks = findViewById(R.id.SQLmarks);
        txt_id = findViewById(R.id.SQLid);

        btn_addDATA = findViewById(R.id.SQLADD_DATA);
        btn_VIEW_ALL = findViewById(R.id.SQLiewAll);
        btn_update = findViewById(R.id.SQLUpdate);
        btn_delete = findViewById(R.id.SQLDelete);


        AddData();
        viewALL();
        UpdateData();
        deleteData();

        mydb = new DatabaseHelper(this);
    }

    public void AddData(){
        btn_addDATA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = mydb.insertData(txt_name.getText().toString(),txt_surname.getText().toString(),
                                    txt_marks.getText().toString());
                
                if(isInserted == true)
                    Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Data is Not Inserted", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void viewALL(){
        btn_VIEW_ALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getALLDATA();
                if(res.getCount() == 0) {
                    //show Result
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer  = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Id :"+ res.getString(0)+"\n");
                    buffer.append("Name :"+ res.getString(1)+"\n");
                    buffer.append("Surname :"+ res.getString(2)+"\n");
                    buffer.append("Marks :"+ res.getString(3)+"\n\n");

                }

                //show all data
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void UpdateData(){

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = mydb.updateData(txt_id.getText().toString(),
                        txt_name.getText().toString(),txt_surname.getText().toString(),txt_marks.getText().toString());
                if (isUpdated == true) {
                    Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Data is Not Updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteData(){
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = mydb.deleteData(txt_id.getText().toString());
                if(deleteRows > 0)
                {
                    Toast.makeText(getApplicationContext(), "Data Deleted", Toast.LENGTH_LONG).show();
                }
                else
                    {
                    Toast.makeText(getApplicationContext(), "Data is Not Deleted", Toast.LENGTH_LONG).show();
                }
                }

        });
    }

}