package com.vishnudevops.studentmanagment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
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

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText username;
    EditText studentid;
    TextView register;
    String line;
    String m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.loginid);
        username= findViewById(R.id.usernameid);
        studentid=findViewById(R.id.passwordid);
        register=findViewById(R.id.registertvid);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
//-------------------------------------------------------------------------------------------------------------------------

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register_intent=new Intent(MainActivity.this,Register.class);
                startActivity(register_intent);
            }
        });
        //----------------------------------------------------------------------------------------------------------------------
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.getText().toString().equals("") && !studentid.getText().toString().equals(""))
                {

                 asyncPOST();


                }
                if(username.getText().toString().equals("") && studentid.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this,"enter data",Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
    //----------------------------------------------------------------------------------------------------------------------
    private  void asyncPOST(){
        @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> asyncTask= new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute()
            {

            }

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                List<NameValuePair> list =new ArrayList<>();
                list.add(new BasicNameValuePair("studentname",username.getText().toString()));
                list.add(new BasicNameValuePair("studentid",studentid.getText().toString()));

                try {
                    HttpClient client=new DefaultHttpClient();
                    HttpPost post= new HttpPost("http://androidworks.000webhostapp.com/studentDB//login.php");
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
                    m = builder.toString();
                }catch (Exception e){
                    Log.d("msg", e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                Toast.makeText(MainActivity.this,m , Toast.LENGTH_SHORT).show();
            }
        };
        asyncTask.execute();
    }
}
