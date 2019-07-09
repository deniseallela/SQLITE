package com.example.sqlitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
Button search,selectall,save,update,delete;
SQLiteDatabase sqLiteDatabase;
String name,mail,age,searchme;
EditText editName,editEmail,editSearch,editAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteDatabase=openOrCreateDatabase("MyDb",
                Context.MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS students(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name VARCHAR(255),Email VARCHAR(255),Age VARCHAR(255))");
        search=findViewById(R.id.search);
        selectall=findViewById(R.id.selectall);
        save=findViewById(R.id.save);
        update=findViewById(R.id.update);
        delete=findViewById(R.id.delete);
        editName=findViewById(R.id.name);
        editEmail=findViewById(R.id.email);
        editSearch=findViewById(R.id.searched);
        editAge=findViewById(R.id.age);
        save.setOnClickListener(this);
        selectall.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    if (v.getId()==R.id.save){
        name=editName.getText().toString().trim();
        mail=editEmail.getText().toString().trim();
        age=editAge.getText().toString().trim();
        if (name.equals("")||mail.equals("")||
                age.equals("")){
            Toast.makeText(this, "Field can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }else {
            sqLiteDatabase.execSQL("Insert into " +
                    "students(Name,Email,Age) VALUES('"+
                    name + " ',' "+mail + " ',' " + age + " ');");
            Toast.makeText(this, "SAVED", Toast.LENGTH_SHORT).show();
        }
    }
    else if (v.getId()==R.id.selectall){
        Cursor c=sqLiteDatabase.rawQuery("Select * from students",null);
        if (c.getCount()==0){
            Toast.makeText(this, "Empty db", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer=new StringBuffer();
        while (c.moveToNext()){
            buffer.append("Student name: "+c.getString(1)+"\n");
            buffer.append("Student email: "+c.getString(2)+"\n");
            buffer.append("Student age: "+c.getString(3)+"\n");
        }
        Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();
    }
    else if (v.getId()==R.id.search){
        searchme=editSearch.getText().toString().trim();
        if (searchme.equals("")){
            Toast.makeText(this, "Enter Student name first", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor c=sqLiteDatabase.rawQuery("Select * from students Where Name='" +
                searchme + " '",null);
        if (c.moveToFirst()){
            editName.setText(c.getString(1));
            editEmail.setText(c.getString(2));
            editAge.setText(c.getString(3));
        }
        else{
            Toast.makeText(this, "Student not found ", Toast.LENGTH_SHORT).show();
        }

    }
    else if(v.getId()==R.id.update){
        searchme=editSearch.getText().toString().trim();
        name=editName.getText().toString().trim();
        mail=editEmail.getText().toString().trim();
        age=editAge.getText().toString().trim();
        if (searchme.equals("")){
            Toast.makeText(this, "Enter student name to update", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cusorupdate=sqLiteDatabase.rawQuery
                ("Select * from students Where Name='" +
                searchme + " '",null);
        if (cusorupdate.moveToFirst()){
            if (name.equals("")||
                    mail.equals("")|| age.equals("")){
                Toast.makeText(this, "Fields cant be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                sqLiteDatabase.execSQL("Update students Set Name=' "+
                        name+" ',Email='" +mail + " ',Age='" +age +" '");
                Toast.makeText(this, "Student data updated", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Student does not exist", Toast.LENGTH_SHORT).show();
        }
    }
    else if (v.getId()==R.id.delete){
        searchme=editSearch.getText().toString().trim();
        if (searchme.equals("")){
            Toast.makeText(this, "Enter Student name first", Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cdel=sqLiteDatabase.rawQuery("Select * from students Where Name='" +
                searchme + " '",null);
        if (cdel.moveToFirst()){
            sqLiteDatabase.execSQL("Delete from students where Name='"+searchme + " '");
            Toast.makeText(this, "Record deleted", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "Student not found ", Toast.LENGTH_SHORT).show();
        }

    }
    }
}
