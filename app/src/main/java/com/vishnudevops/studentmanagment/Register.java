package com.vishnudevops.studentmanagment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
     EditText studentname;
     EditText studentid;
     EditText stmobilenum;
     EditText stemail;
     EditText stclass;
     Button register;
    String line,m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        studentname=findViewById(R.id.nameid);
        studentid=findViewById(R.id.stdid);
        stmobilenum=findViewById(R.id.mnoid);
        stemail=findViewById(R.id.emailid);
        stclass=findViewById(R.id.classid);
        register=findViewById(R.id.regbtn);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
     register.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             if(!studentname.getText().toString().equals("") &&
                     !studentid.getText().toString().equals("") &&
                     !stmobilenum.getText().toString().equals("")&&
                     !stemail.getText().toString().equals("") &&
                     !stclass.getText().toString().equals(""))
             {
                 Toast.makeText(Register.this, "Registered_Successfully", Toast.LENGTH_SHORT).show();
                 asyncPOST();
                 Intent intent=new Intent(Register.this,MainActivity.class);
                 startActivity(intent);
         }
         }
     });

    }
    private final void asyncPOST()
    {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> asyncTask= new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {



            }

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                List<NameValuePair> list =new ArrayList<>();
                list.add(new BasicNameValuePair("studentname",studentname.getText().toString()));//same as the php name
                list.add(new BasicNameValuePair("studentid",studentid.getText().toString()));
                list.add(new BasicNameValuePair("mobilenum",stmobilenum.getText().toString()));
                list.add(new BasicNameValuePair("email",stemail.getText().toString()));
                list.add(new BasicNameValuePair("class",stclass.getText().toString()));


                try {
                    HttpClient client=new DefaultHttpClient();
                    HttpPost post= new HttpPost("http://androidworks.000webhostapp.com/studentDB/register.php");
                    post.setEntity(new UrlEncodedFormEntity(list));
                    HttpResponse response=client.execute(post);
                    HttpEntity entity=response.getEntity();
                    InputStream is=entity.getContent();
                    InputStreamReader isr=new InputStreamReader(is);
                    BufferedReader reader=new BufferedReader(isr);
                    StringBuilder builder=new StringBuilder();



                    while ((line=reader.readLine())!=null)
                    {
                        builder.append(line);
                    }
                    String m = builder.toString();
                }catch (Exception e){
                    Log.d("regmsg", e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

            }
        };
        asyncTask.execute();
    }
}
