package com.example.shopping.screenSuplier.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.shopping.screenSuplier.EditAndDeleteCategoryActivity;
import com.example.shopping.screenSuplier.Facade.FactoryMaker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class FragmentAccount extends Fragment {

    View v;
    TextView fulname,name,phone,address;
    Button edit;

    TextInputLayout username_c,phone_c;
    String username_s, phone_s;

    Button edit_bottom, close_bottom;

    String urlEdit = "https://startbuying.000webhostapp.com/EditUserSuplier.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_account, container, false);

        fulname = v.findViewById(R.id.account_suplier_fullname);
        name = v.findViewById(R.id.account_suplier_username);
        phone = v.findViewById(R.id.account_suplier_phone);
        address = v.findViewById(R.id.account_suplier_address);

        edit = v.findViewById(R.id.btn_editAccount_suplier);

        String $fullname = GlobalUsuario.firstname+" "+ GlobalUsuario.lastname;
        String $name = GlobalUsuario.username;
        String $phone = GlobalUsuario.phone;
        String $address = GlobalUsuario.address;

        fulname.setText($fullname);
        name.setText($name);
        phone.setText($phone);
        address.setText($address);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v,"mantenimiento", Snackbar.LENGTH_LONG).show();
                String IdUser = String.valueOf(GlobalUsuario.idusuario);
                bottomDialogEdit(IdUser, v.getContext(), v);

            }
        });


        return  v;
    }

    private void bottomDialogEdit(final String id, Context context, View v) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);
        View bottomsheetView = LayoutInflater.from(context)
                .inflate(R.layout.activity_bottom_edit_user_suplier, (LinearLayout) v.findViewById(R.id.bottomsheetContainer_account_user_suplier));

        edit_bottom = bottomsheetView.findViewById(R.id.btnEdit_editUser_suplier);
        close_bottom = bottomsheetView.findViewById(R.id.btnClose_editUser_suplier);

        username_c  = bottomsheetView.findViewById(R.id.tf_username_editUser_suplier);
        phone_c  = bottomsheetView.findViewById(R.id.tf_phone_editUser_suplier);

        username_c.getEditText().setText(name.getText().toString());
        phone_c.getEditText().setText(phone.getText().toString());

        username_c.getEditText().addTextChangedListener(validationTextWatcher);
        phone_c.getEditText().addTextChangedListener(validationTextWatcher);

        edit_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] date =  {id, username_s, phone_s};
                ChangedUser(date);
                name.setText(username_s);
                phone.setText(phone_s);
                bottomSheetDialog.dismiss();
            }
        });

        close_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setContentView(bottomsheetView);
        bottomSheetDialog.show();
    }


    private TextWatcher validationTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(validationUserSuplier()){
                edit_bottom.setVisibility(View.VISIBLE);
            }else{
                edit_bottom.setVisibility(View.GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean validationUsername(){
        username_s = username_c.getEditText().getText().toString().trim();

        if( username_s.isEmpty()){
            return false;
        }else{
            if( username_s.length() > 40){
                username_c.setError("Max Characters");
                return false;
            }
            username_c.setError(null);
            return true;
        }

    }
    private boolean validationPhone(){
        phone_s = phone_c.getEditText().getText().toString().trim();

        if(phone_s.isEmpty()){
            return false;
        }else{
            return true;
        }

    }

    private boolean validationUserSuplier(){
        if(!validationUsername() | !validationPhone() ){
            return false;
        }
        return true;
    }


    private void ChangedUser(final String[] datos) {
        StringRequest con = new StringRequest(StringRequest.Method.POST, urlEdit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Toast.makeText(getActivity(), "save", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("data",error.toString());
                Toast.makeText(getActivity(), "save", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", datos[0]);
                parametros.put("username", datos[1]);
                parametros.put("phone", datos[2]);
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(con);
    }


}