package com.example.shopping.screenMain;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class screenSignup1 extends AppCompatActivity {

    Button cancel, save, help;
    RadioGroup radioGroup;
    RadioButton radioButton;

    ProgressDialog progressDialog;

    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    String[] datos = new String[11];

    String url = "https://startbuying.000webhostapp.com/insertUser.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_signup1);

        cancel = findViewById(R.id.btn_cancelAccount);
        save = findViewById(R.id.btn_saveAccount);
        help = findViewById(R.id.btn_helpAccount);

        radioGroup = findViewById(R.id.rg_users);
        Bundle user_cp = getIntent().getExtras();

        datos[0] = "";
        datos[1] = user_cp.getString("username");
        datos[2] = user_cp.getString("password");
        datos[3] = user_cp.getString("firstname");
        datos[4] = user_cp.getString("lastname");
        datos[5] = user_cp.getString("address");
        if(user_cp.getString("postal") == null){
            datos[6] = "21102";
        }else{
            datos[6] = user_cp.getString("postal");
        }
        datos[8] = user_cp.getString("statususer");
        datos[9] = user_cp.getString("phone");
        datos[10] = user_cp.getString("email");

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialAlertDialogBuilder build = new MaterialAlertDialogBuilder(screenSignup1.this)
                        .setTitle("Info App:")
                        .setIcon(R.drawable.ic_help)
                        .setBackground(getResources().getDrawable(R.drawable.background_alert_term_policy))
                        .setMessage(getResources().getString(R.string.string_infoApp));
                build.setPositiveButton("Great!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                build.show();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int usertype = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(usertype);
                String $user_type = radioButton.getText().toString();
                if ($user_type.equalsIgnoreCase("Supplier")) {
                  datos[7] = "1";
                } else {
                    datos[7] = "2";
                }
                insertar();
                progressLogin();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(screenSignup1.this, Login.class);
                overridePendingTransition(R.anim.in_top, R.anim.out_top);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void progressLogin(){
        progressDialog = new ProgressDialog(screenSignup1.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Wait...");
        progressDialog.setMessage("By saving data to the database, keep your cool as the action ends");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setMax(100);

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {

                e=e+2;
                if(e<=100){

                    progressDialog.setProgress(e);

                }else{

                    timer.cancel();
                    progressDialog.dismiss();
                    e=0;

                    Intent start = new Intent(screenSignup1.this, Login.class);
                    overridePendingTransition(R.anim.in_top,R.anim.out_top);
                    start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(start);

                }
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },2000,200);

    }

    private void insertar() {
        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("data",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("name_user", datos[1]);
                parametros.put("password_user", datos[2]);
                parametros.put("firtName_user", datos[3]);
                parametros.put("lastName_user", datos[4]);
                parametros.put("address_user", datos[5]);
                parametros.put("postalCode_user", datos[6]);
                parametros.put("type_user", datos[7]);
                parametros.put("status_user", datos[8]);
                parametros.put("phone_user", datos[9]);
                parametros.put("email_user", datos[10]);
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(screenSignup1.this);
        requestQueue.add(con);
    }

}