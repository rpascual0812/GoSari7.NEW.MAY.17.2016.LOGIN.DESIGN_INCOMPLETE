package com.android.iit.chrs.gosari;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import android.content.Context;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

public class ViewSignUp extends Activity {

    //http://www.gosari.ph/api/users/register.php?mobile_number=09950899978&email_address=arpascual0812@gmail.com&name=aRafael&location=aPasig&password=2

   Button btnRegister;

    boolean checkifRegistered;
    JSONObject jsonObject;
    String status;

   String in_mobile,in_name,in_pass,in_location,in_email,in_repeatPass;

    EditText et_mobile,et_name,et_pass,et_location,et_email,et_repeatPass;


    TelephonyManager telemamanger;

    String getSimNumber,mobilenumber;

    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sign_up);
        telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

        btnRegister=(Button)findViewById(R.id.btnRegister);
        et_email=(EditText)findViewById(R.id.inSignUp_Email);
        et_mobile=(EditText)findViewById(R.id.inSignUp_Mobile);
        et_name=(EditText)findViewById(R.id.inSignUp_Name);
        et_pass=(EditText)findViewById(R.id.inSignUp_Password);
        et_location=(EditText)findViewById(R.id.inSignUp_Location);
        et_repeatPass=(EditText)findViewById(R.id.inSignUp_RepeatPassword);
        getMobileNum();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                in_mobile=et_mobile.getText().toString();
                in_name= et_name.getText().toString();
                in_pass= et_pass.getText().toString();
                in_location=et_location.getText().toString();
                in_repeatPass=et_repeatPass.getText().toString();

                in_email=et_email.getText().toString();

                Log.e("PASS:",in_pass);

                Log.e("PASS CONFIRM:",in_repeatPass);

                int length= et_mobile.getText().length();

                Log.e("LENGTH:",String.valueOf(length));


                if(!checkPassWordAndConfirmPassword(in_pass,in_repeatPass,in_name,in_mobile,in_email,in_location)|length<11)
                {
                    //ClearFields();
                    Toast.makeText(getApplicationContext(),"Invalid Details!",Toast.LENGTH_SHORT).show();
                    //AlerDialogInvalidDetails();
                    Log.e("MESSAGE:","Invalid Details");
                }
                else {


                    Log.e("MESSAGE:","REGISTER SUCCESS");
                    ClearFields();

                    HttpGetLogin(in_mobile, in_name, in_pass, in_location, in_email);
                    ClearFields();
                    if(checkifRegistered==false){

                            finish();
                        }

                    else {
                        AlertDialogAlreadyRegistered();
                        Log.e("MESSAGE: ","ALREADY REGISTERED");
                    }
                }
            }
        });
    }



    public void HttpGetLogin(String mobile, String name,String pass,String location,String email) {

        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet("http://www.gosari.ph/api/users/register.php?mobile_number=" + mobile + "&email_address=" + email + "&name=" + name + "&location=" + location + "&password=" + pass + "");

            Log.e("MESSAGE","http://www.gosari.ph/api/users/register.php?mobile_number=" + mobile + "8&email_address=" + email + "&name=" + name + "&location=" + location + "&password=" + pass + "");

            HttpResponse response = httpclient.execute(httpGet);

            if(response != null) {
                InputStream is = response.getEntity().getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                String msgStatus=sb.toString();
                jsonObject=new JSONObject(msgStatus);
                status=jsonObject.getString("status");
                Log.e("STATUS:",status);
                if(status=="false"){
                    checkifRegistered=true;
                }

            }
            else {
                checkifRegistered=false;
            }


        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }


    public void ClearFields(){

        et_mobile.setText("");
        et_location.setText("");
        et_name.setText("");
        et_pass.setText("");
        et_repeatPass.setText("");
        et_email.setText("");
    }


    public boolean checkPassWordAndConfirmPassword(String password,String confirmPassword,String name,String mobile,String email,String location)
    {
        boolean pstatus = false;
        if (confirmPassword != null && password != null&&name!=null&&mobile!=null&&email!=null&&location!=null)
        {
            if (password.equals(confirmPassword))
            {
                pstatus = true;
            }
        }

        return pstatus;
    }


   /* public boolean checkifnull(String name,String password,String location,String mobile){

        boolean notnull=false;
        if(name==null|password==null|location==null|mobile==null){
            notnull=true;
        }
        return notnull;
    }*/


    public void AlerDialogInvalidDetails(){
      AlertDialog.Builder alertdialogbuilder =new AlertDialog.Builder(this);

        alertdialogbuilder.setTitle("Warning!");

        alertdialogbuilder.setMessage("Invalid Log in details.");

        alertdialogbuilder.setCancelable(true);
        AlertDialog alertDialog=alertdialogbuilder.create();
        alertDialog.show();


    }


    public void AlertDialogAlreadyRegistered(){
        final AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Alert!");
        alertDialogBuilder.setMessage("Mobile number is already registered.");

        final AlertDialog alertDialog= alertDialogBuilder.create();
        alertDialog.show();

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                  alertDialog.dismiss();
            }
        });

    }


    public void getMobileNum(){

        getSimNumber = telemamanger.getLine1Number();
        Log.e("MESSAGE: ","SIM NUMBER"+getSimNumber);
        et_mobile.setText(getSimNumber);

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
