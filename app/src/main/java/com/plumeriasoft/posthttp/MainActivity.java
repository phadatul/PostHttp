package com.plumeriasoft.posthttp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText txtID,txtName,txtSal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.button);
        txtID=(EditText)findViewById(R.id.textView);
        txtName=(EditText)findViewById(R.id.textView2);
        txtSal=(EditText)findViewById(R.id.textView3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask().execute("url");
            }
        });

    }


    public class MyTask extends AsyncTask<String,String,String>{
        String id,name,sal;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            id=txtID.getText().toString();
            name=txtName.getText().toString();
            sal=txtSal.getText().toString();
        }

        @Override
        protected String doInBackground(String... params) {

            String response = "";
            URL url = null;
            try {
                URL urlToRequest = new URL("http://xdesign.in/register.php");
                HttpURLConnection urlConnection = (HttpURLConnection)urlToRequest.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

// write out form parameters

                String postParamaters = "id="+id+"&name="+name+"&salary="+sal;

                urlConnection.setFixedLengthStreamingMode(postParamaters.getBytes().length);
                PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
                out.print(postParamaters);
                out.close();

// connect
                urlConnection.connect();
                int responseCode=urlConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    response = br.readLine();
                }
                else {
                    response="Error Registering";
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtID.setText(s);
        }
    }
}
