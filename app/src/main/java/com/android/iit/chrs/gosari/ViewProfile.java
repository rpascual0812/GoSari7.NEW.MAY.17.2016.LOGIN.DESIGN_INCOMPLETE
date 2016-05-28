package com.android.iit.chrs.gosari;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewProfile extends Activity {

    TextView tv_name,tv_mobile,tv_emailAd;

    String json;

    JSONObject jsonObject;

    ArrayList<Account>accountArrayList;

    ArrayList<Account>GetData;

    JSONArray jsonArray;

    Button btn_logout,btn_changePass;

    DbHelper db;

    String mobile;

    String pass;

    String urlConnection;

    String acc_name,acc_email,acc_num;

    EditText in_oldPass,in_newPass,in_confirmNewPass;

    String oldpass,newpass,confirmpass;


    //www.gosari.ph/api/users/change_password.php?mobile_number=09056075270&password=a

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        db=new DbHelper(this);

        accountArrayList=db.getLoginAccount();

        for (Account account:accountArrayList){
            mobile=account.getLogin_pass();
            pass=account.getLogin_mobile();

            Log.e("OUTPUT:","MOBILE:"+mobile+" PASS: "+pass);
        }

        HttpGetProfile(mobile,pass);

        tv_name=(TextView)findViewById(R.id.Profile_name);
        tv_mobile=(TextView)findViewById(R.id.Profile_number);
        tv_emailAd=(TextView)findViewById(R.id.Profile_emailAd);
        btn_logout=(Button)findViewById(R.id.btn_logout);
        btn_changePass=(Button)findViewById(R.id.btn_ChagePass);
        in_oldPass=(EditText)findViewById(R.id.in_oldpassword);
        in_newPass=(EditText)findViewById(R.id.in_newPassWord);
        in_confirmNewPass=(EditText)findViewById(R.id.in_confirmnewPassWord);




        tv_name.setText("Name : "+acc_name);
        tv_mobile.setText("Mobile : "+acc_num);
        tv_emailAd.setText("Email : "+acc_email);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.LogOut();
                db.clearchkouthistory();
                db.removeAllItem();
                Log.e("MESSAGE: ","ACCOUNT DELETED");
                Intent viewLogin = new Intent(getApplicationContext(),ViewLogIn.class);
                finish();
                startActivity(viewLogin);


            }
        });

        btn_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  oldpass =in_oldPass.getText().toString();
                  newpass=in_newPass.getText().toString();
                  confirmpass=in_confirmNewPass.getText().toString();
                if(ConfirmPassword()){
                    Log.e("MESSAGE:","ACCOUNT CHANGE NEW PASS");
                    HttpNewPassword(mobile,newpass);
                    ClearFields();
                    db.LogOut();
                    db.clearchkouthistory();
                    db.removeAllItem();
                    Intent viewLogin = new Intent(getApplicationContext(),ViewLogIn.class);
                    startActivity(viewLogin);
                    finish();

                }
                else{
                    Log.e("MESSAGE:","INVALID DETAILS");
                    ClearFields();
                }


            }
        });

    }


    public void HttpGetProfile(String mobile,String pass){



        try {
            urlConnection="http://www.gosari.ph/api/users/login.php?mobile_number="+ mobile+ "&password="+ pass +"";
            URL url = new URL(urlConnection);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // Check the connection status
            if (urlConnection.getResponseCode() == 200) {
                // if response code = 200 ok
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                // Read the BufferedInputStream
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
                json=sb.toString();

                jsonObject=new JSONObject(json);

                jsonArray=jsonObject.getJSONArray("result");
                GetData=new ArrayList<>();
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject j=jsonArray.getJSONObject(i);
                    Account account=new Account();
                    account.setName(j.getString("name"));
                    account.setEmail_add(j.getString("email_address"));
                    account.setMobile_num(j.getString("mobile_number"));
                    GetData.add(account);
                }

                for(Account account:GetData){
                    acc_name=account.getName();
                    acc_email=account.getEmail_add();
                    acc_num=account.getMobile_num();
                    Log.e("OUTPUT JSON:",acc_name+acc_email+acc_num);

                }
              //  GetLogInDetails();

            }
        }
        catch (JSONException e){
        e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    public boolean ConfirmPassword(){

        boolean result=false;

        if(oldpass!=null){

            if(oldpass.equals(pass) && confirmpass.equals(newpass) && confirmpass != null){
               result=true;
           }
        }

        return result;


    }


    public void HttpNewPassword(String mobile,String newpass){
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet("http://www.gosari.ph/api/users/change_password.php?mobile_number=" + mobile + "&password=" + newpass + "");

            Log.e("MESSAGE", "http://www.gosari.ph/api/users/change_password.php?mobile_number=" + mobile + "&password=" + newpass + "");

            HttpResponse response = httpclient.execute(httpGet);

           if (response != null) {
                InputStream is = response.getEntity().getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String message=sb.toString();
                JSONObject jsonobj=new JSONObject(message);
                String status=jsonobj.getString("status");
                Log.e("STATUS: ",status);


            }


        }catch (IOException e) {
            e.printStackTrace();
        }
      catch (JSONException e){
            e.printStackTrace();
        }


    }


    public void ClearFields(){
        in_oldPass.setText("");
        in_newPass.setText("");
        in_confirmNewPass.setText("");
    }







}
