package com.example.shopping.screenMain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.activity.BuyActivity;
import com.example.shopping.ScreenConsumer.activity.MainConsumer;
import com.example.shopping.screenMain.email.JavaMail;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RecoveryPassword extends AppCompatActivity {

    Button back, recovery;
    TextInputLayout email;
    String $email;
    String good;
    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    String rec, subject, textMessage;

    String urlEmail = "https://startbuying.000webhostapp.com/validandoEmail.php";
    String urlChange = "https://startbuying.000webhostapp.com/ChangePassword.php";


    //Dialog progress
    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);

        back = findViewById(R.id.btn_backRecovery);
        recovery = findViewById(R.id.btn_recovery);

        email = findViewById(R.id.tf_recoveryEmail);

        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                good = idOrdenGenerated();
                  if(ValidationEmail()){
                    foundEmail($email);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean validationEmaill() {
        $email = email.getEditText().getText().toString().trim();
        if ($email.isEmpty()) {
            email.setError("field empty!");
            return false;
        } else {
            if ($email.length() > 40) {
                email.setError("maximum characters!");
                return false;
            }
            email.setError(null);
            return true;
        }
    }

    private boolean ValidationEmail() {
        if(!validationEmaill()){
            return false;
        }
        return true;

    }

    private void foundEmail(final String _email) {
        StringRequest con = new StringRequest(StringRequest.Method.POST, urlEmail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null && response.length() > 0) {
                    //email.setError("Success!");
                    sendMail(_email);
                }else{
                    email.setError("Email not found!");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email_user", _email);
                return parametros;

            }
        };
        con.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(RecoveryPassword.this);
        requestQueue.add(con);
    }


    private void sendMail(String email) {
        //Send Mail
        JavaMail javaMailAPI = new JavaMail(this ,email,good );
        javaMailAPI.execute();
        changedContra(email,good);
        progressback();
    }

    private String idOrdenGenerated(){

        char [] chars = "0123456789$_ABCDEFGHIJKLMNOPQRSTUVXWZ".toCharArray();
        Random random = new Random();

        String clave = "";

        for (int i=0;i<8;i++){
         clave += chars[random.nextInt(chars.length)];
        }

        return clave;
    }

    private void changedContra(final String $email, final String clave){

        StringRequest con = new StringRequest(StringRequest.Method.POST, urlChange, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null && response.length() > 0) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email", $email);
                parametros.put("password", clave);
                return parametros;

            }
        };
        con.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(RecoveryPassword.this);
        requestQueue.add(con);



    }


    private void progressback() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Send Email");
        progressDialog.setMessage("Send Email To The Email: "+$email);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setMax(100);

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                e = e + 5;
                if (e <= 100) {
                    progressDialog.setProgress(e);
                } else {
                    timer.cancel();
                    progressDialog.dismiss();
                    e = 0;

                    onBackPressed();


                }

            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 1000, 200);

    }



}


