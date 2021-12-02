package com.example.shopping.screenMain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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
import com.example.shopping.ScreenConsumer.activity.MainConsumer;
import com.example.shopping.screenSuplier.MainSuplierActivity;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {

    Button back, signup, recovery, login;
    String url = "https://startbuying.000webhostapp.com/validationLogin.php";
    TextInputLayout email,password;

    String $email, $password;

    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    int typeUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back = findViewById(R.id.btn_backlogin);
        signup = findViewById(R.id.btn_signupLogin);
        recovery = findViewById(R.id.btn_recoveryLogin);
        login = findViewById(R.id.btn_accessLogin);


        email = findViewById(R.id.tf_loginEmail);
        password = findViewById(R.id.tf_loginPassword);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(Login.this, ScreenSignup.class);
                startActivity(start);
                overridePendingTransition(R.anim.in_bottom,R.anim.out_bottom);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validationInputlogin()){
                    login_validacion();
                }


            }
        });

        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(Login.this, RecoveryPassword.class);
                startActivity(start);
                overridePendingTransition(R.anim.in_bottom,R.anim.out_bottom);
            }
        });

    }

    private boolean validationEmail() {
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
    private boolean validationPassword(){
        $password = password.getEditText().getText().toString().trim();
        if ($password.isEmpty()) {
            password.setError("field empty!");
            return false;
        } else {
            if ($password.length() > 30) {
                password.setError("maximum characters!");
                return false;
            }
            password.setError(null);
            return true;
        }
    }


    private boolean validationInputlogin(){

        if(!validationEmail() | !validationPassword()){
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void login_validacion() {
        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null && response.length() > 0) {
                    try {
                    JSONArray array = new JSONArray(response);

                    for(int i=0; i < array.length(); i++){

                             JSONObject row = array.getJSONObject(i);

                             int id = row.getInt("id_user");
                             int type = row.getInt("type_user");
                             String name = row.getString("name_user");
                             String firstName = row.getString("firtName_user");
                             String lastName = row.getString("lastName_user");
                             String password = row.getString("password_user");
                             String phone = row.getString("phone_user");
                             String address = row.getString("address_user");

                             new GlobalUsuario(id,name,password,type,lastName,firstName,phone,address);
                        }
                        Progress();
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }else{
                    email.setError("The credentials do not match.");
                    password.setError(null);
                    password.getEditText().setText("");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email_user", $email);
                parametros.put("password_user", $password);
                return parametros;

            }
        };
        con.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
        requestQueue.add(con);
    }

    private void Enter(){

        if(GlobalUsuario.typeUSER == 1){
            Intent start = new Intent(Login.this, MainSuplierActivity.class);
            startActivity(start);
            overridePendingTransition(R.anim.in_fade,R.anim.out_fade);
        }else{
            Intent start = new Intent(Login.this, MainConsumer.class);
            startActivity(start);
            overridePendingTransition(R.anim.in_fade,R.anim.out_fade);
        }

    }

    private void Progress(){

        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Success...");
        progressDialog.setMessage("Wait while your data loads");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setMax(100);

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                e=e+5;
                if(e<=100){
                    progressDialog.setProgress(e);
                }else{
                    timer.cancel();
                    progressDialog.dismiss();
                    e=0;

                    Enter();

                }

            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },1000,200);



    }



}