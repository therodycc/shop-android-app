package com.example.shopping.screenSuplier.fragments_second;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.example.shopping.screenSuplier.AddProductActivty;
import com.example.shopping.screenSuplier.CompleteOrderActivity;
import com.example.shopping.screenSuplier.MainSuplierActivity;
import com.example.shopping.screenSuplier.SecondSuplierActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentSetting extends Fragment {

    View v;
    SwitchMaterial switchMaterial;
    Button back;

    String urlEnabledCompay ="https://startbuying.000webhostapp.com/CountProductSuplier.php";
    String idCompany;
    int CountProduct = 0;

    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    FragmentProduct fragmentProduct = new FragmentProduct();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =inflater.inflate(R.layout.fragment_setting, container, false);
        switchMaterial = v.findViewById(R.id.switch_seconSuplier_enabledStore);
        back = v.findViewById(R.id.btn_setting_close_SecondSuplier);

        idCompany = String.valueOf(GlobalUsuario.idCompany);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sc = new Intent(getActivity(), MainSuplierActivity.class);
                sc.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(sc);
            }
        });

        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String c = String.valueOf(CountProduct);
                if(isChecked){
                    if(CountProduct == 0){
                        switchMaterial.setChecked(false);
                        Snackbar.make(buttonView,"The company must have at least one product",Snackbar.LENGTH_LONG)
                                .setAction("Created Product", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(), AddProductActivty.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }


               }else {
                    Toast.makeText(buttonView.getContext(),"V",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressCount();
    }

    private void progressCount() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Completed order...");
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

                    if (CountProduct >0){
                        switchMaterial.setChecked(true);
                    }else{
                        switchMaterial.setChecked(false);
                    }

                }

            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 10, 200);

    }



    @Override
    public void onStart() {
        super.onStart();
        CheckProductCompany();
    }



  /*  private void dialogWarning(){

        final MaterialAlertDialogBuilder build = new MaterialAlertDialogBuilder(getActivity())
                .setTitle("Warning!")
                .setIcon(R.drawable.ic_warning)
                .setBackground(getResources().getDrawable(R.drawable.background_alert_term_policy))
                .setMessage("")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
*/


    private void CheckProductCompany(){
        StringRequest con = new StringRequest(StringRequest.Method.POST, urlEnabledCompay, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    try {
                        JSONArray array = new JSONArray(response);
                        for(int i=0; i < array.length(); i++){

                            JSONObject row = array.getJSONObject(i);
                            CountProduct = row.getInt("product");

                        }

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_company", idCompany);
                return parametros;

            }
        };

        con.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(con);
    }


}