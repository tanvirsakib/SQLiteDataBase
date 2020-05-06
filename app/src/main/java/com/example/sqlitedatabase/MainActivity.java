package com.example.sqlitedatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText, ageEditText, genderEditText , idEditText;
    private Button submitButton, displayButton, updateButton, deleteButton;

    MyDataBaseHelper myDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataBaseHelper = new MyDataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDataBaseHelper.getWritableDatabase();

        nameEditText = findViewById(R.id.nameEditTextID);
        ageEditText = findViewById(R.id.ageEditTextID);
        genderEditText = findViewById(R.id.genderEditTextID);
        submitButton = findViewById(R.id.submitButtonID);
        displayButton = findViewById(R.id.displayButtonID);
        idEditText = findViewById(R.id.idEditTextID);
        updateButton = findViewById(R.id.updateButtonID);
        deleteButton = findViewById(R.id.deleteButtonID);

        submitButton.setOnClickListener(this);
        displayButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String id = idEditText.getText().toString();


        if (v.getId()==R.id.submitButtonID){
            long rowId = myDataBaseHelper.insertData(name,age,gender);
            if (rowId == -1){
                Toast.makeText(this,"Unsuccessfull",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"Row "+rowId+" is inserted successfully",Toast.LENGTH_LONG).show();
            }
            nameEditText.setText("");
            ageEditText.setText("");
            genderEditText.setText("");
        }

        if (v.getId() == R.id.displayButtonID){
            Cursor cursor = myDataBaseHelper.displayAllData();
            if (cursor.getCount()==0){
                //there is no data
                showData("Error","No data found");
            }
            StringBuffer stringBuffer = new StringBuffer();
            while (cursor.moveToNext()){
                stringBuffer.append("ID: "+cursor.getString(0)+"\n");
                stringBuffer.append("Name: "+cursor.getString(1)+"\n");
                stringBuffer.append("Age: "+cursor.getString(2)+"\n");
                stringBuffer.append("Gender: "+cursor.getString(3)+"\n\n\n");
            }
            showData("Resultset", stringBuffer.toString());
        }

        if (v.getId() == R.id.updateButtonID){
           Boolean isUpdated = myDataBaseHelper.updateData(id,name,age,gender);

           if (isUpdated  == true){
               Toast.makeText(this,"Data is Updated",Toast.LENGTH_LONG).show();
           }else {
               Toast.makeText(this,"Data is not Updated",Toast.LENGTH_LONG).show();
           }
        }

        if (v.getId()==R.id.deleteButtonID){
            int value = myDataBaseHelper.deleteData(id);

            if (value>0){
                Toast.makeText(this,"Data is Deleted",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"Data is not deleted",Toast.LENGTH_LONG).show();
            }
        }

    }

    public void showData(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
}
