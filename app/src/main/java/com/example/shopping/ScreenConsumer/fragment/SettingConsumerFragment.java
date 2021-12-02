package com.example.shopping.ScreenConsumer.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.ScreenConsumer.activity.MainConsumer;
import com.example.shopping.screenMain.Login;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class SettingConsumerFragment extends Fragment {

    View v;

    String url = "https://startbuying.000webhostapp.com/editPhoneSettingCosumer.php";

    String idUser;

    TextView fullname,username,account, phone;
    Button logout, edit;

    String $phone;
    TextInputLayout editphone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_setting_consumer, container, false);
        fullname = v.findViewById(R.id.setting_fullname);
        username = v.findViewById(R.id.setting_username);
        account = v.findViewById(R.id.setting_account);
        phone = v.findViewById(R.id.setting_phone);

        edit = v.findViewById(R.id.btn_setting_editphone);
        logout = v.findViewById(R.id.btn_setting_close);

        fullname.setText(GlobalUsuario.firstname+" "+GlobalUsuario.lastname);
        username.setText(GlobalUsuario.username);
        account.setText("Consumer");
        phone.setText(GlobalUsuario.phone);

        idUser = String.valueOf(GlobalUsuario.idusuario);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alerted();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogout();
            }
        });

        return v;
    }

    private void dialogLogout(){

        final MaterialAlertDialogBuilder build = new MaterialAlertDialogBuilder(getContext())
                .setTitle("Warning!")
                .setIcon(R.drawable.ic_warning)
                .setBackground(getResources().getDrawable(R.drawable.background_alert_term_policy))
                .setMessage("You sure want to log out");
        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(v.getContext(), Login.class);
                startActivity(i);
            }
        })
        .setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        build.show();

    }

    private void alerted(){

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder (getContext(),android.R.style.Theme_Material_Dialog_NoActionBar);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_edit_phone_consumer,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();


        editphone = view.findViewById(R.id.tf_setting_editphone);
        Button Confirm_edit = view.findViewById(R.id.btn_setting_editphone_consumer);

        Confirm_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validationPhone()){
                    editPhonedata($phone);
                    phone.setText($phone);
                    dialog.dismiss();
                }


            }
        });




    }

    private boolean validationPhone() {
        $phone = editphone.getEditText().getText().toString().trim();
        if ($phone.isEmpty()) {
            editphone.setError("field empty!");
            return false;
        } else {
            if ($phone.length() < 15) {
                editphone.setError("digits are missing!");
                return false;
            }

            phone.setError(null);
            return true;
        }
    }

    private void editPhonedata(final String _phone){

        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("phone", _phone);
                parametros.put("id", idUser);
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(con);
    }




}