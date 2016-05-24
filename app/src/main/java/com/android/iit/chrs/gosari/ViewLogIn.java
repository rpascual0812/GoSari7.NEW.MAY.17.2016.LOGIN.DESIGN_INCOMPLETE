package com.android.iit.chrs.gosari;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class ViewLogIn extends Activity {


    Button btnlogin;

    EditText inMobile, inPassword;

    String mobile, pass;

    JSONObject jsonObject;

    String msg;



    DbHelper db;

    JSONArray jsonArray=null;

    ArrayList<Account>accountArrayList;

    ArrayList<Account>testlist;

    String message;

    Button btnSignUp;

    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        setContentView(R.layout.activity_view_log_in);
        db=new DbHelper(this);

        cd=new ConnectionDetector(getApplicationContext());

        btnSignUp=(Button) findViewById(R.id.btnSignUp);
        inMobile = (EditText) findViewById(R.id.inMobile);
        inPassword = (EditText) findViewById(R.id.inPassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);

       if(db.checkfirst()==true){

           ViewMainActivity();
       }


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile = inMobile.getText().toString();
                pass = inPassword.getText().toString();

                if (mobile.equals("")|pass.equals("")){
                    inMobile.setText("");
                    inPassword.setText("");
                    Log.e("MESSAGE: ","INVALID DETAILS");
                    Toast.makeText(getApplicationContext(),"Please enter a valid detail.",Toast.LENGTH_SHORT).show();

            }
                else {


                    Log.e("MESSAGE: ","LOGGING IN");

                     if (HttpGetLogin(mobile, pass) == true) {
                        inMobile.setText("");
                        inPassword.setText("");
                        db.addAccount(new Account(mobile, pass));
                        testlist = db.getLoginAccount();
                        for (Account account : testlist) {
                            message = account.getLogin_mobile() + account.getLogin_pass();
                            Log.e("OUTPUT DATABASE:", message);
                        }

                         if(cd.isConnectingToInternet()) {
                             ViewMainActivity();
                         }
                         else{
                             ShowAlertNoInternet();
                         }
                    }


                     else {
                        inMobile.setText("");
                        inPassword.setText("");
                        Toast.makeText(getApplicationContext(), "No such account exists!", Toast.LENGTH_SHORT).show();


                    }
                }

            }
        });

       btnSignUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if(cd.isConnectingToInternet()) {

                   ViewSignUp();
               }
               else {
                   ShowAlertNoInternet();
               }
           }
       });
    }


    public void ViewMainActivity() {

        Intent viewMainActivity = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(viewMainActivity);

    }


    public boolean HttpGetLogin(String num, String pass) {

        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet("http://www.gosari.ph/api/users/login.php?mobile_number=" + num + "&password=" + pass + "");

            Log.e("MESSAGE", "http://www.gosari.ph/api/users/login.php?mobile_number=" + num + "&password=" + pass + "");

            HttpResponse response = httpclient.execute(httpGet);

            // for JSON:
            if (response != null) {
                InputStream is = response.getEntity().getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String responsemsg = sb.toString();
                jsonObject = new JSONObject(responsemsg);

                msg = jsonObject.getString("status");
                Log.e("MESSAGE", msg);
                jsonArray =jsonObject.getJSONArray("result");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject c=jsonArray.getJSONObject(i);
                    Account account=new Account();
                    account.setMobile_num(c.getString("mobile_num"));
                    account.setName(c.getString("name"));
                    account.setEmail_add(c.getString("email_address"));
                    accountArrayList.add(account);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (msg == "false") {
            return false;
        }
            else
                return true;

    }


    public void ViewSignUp(){

     Intent viewSignUp = new Intent(getApplicationContext(),ViewSignUp.class);

        startActivity(viewSignUp);

    }


    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


    public void ShowAlertNoInternet(){
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
        alertdialogbuilder.setTitle("Alert!");
        alertdialogbuilder.setMessage("Please check if your connected to the internet?");
        alertdialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                minimizeApp();

            }
        });
        AlertDialog alertDialog=alertdialogbuilder.create();
        alertDialog.show();

    }



}
