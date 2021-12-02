package com.example.shopping.screenSuplier;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.screenMain.Login;
import com.example.shopping.screenMain.screenSignup1;
import com.example.shopping.screenSuplier.Facade.FactoryMaker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Timer;
import java.util.TimerTask;

public class AddCategoryActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Button back, add;
    String url ="https://startbuying.000webhostapp.com/InsertCategory.php";
    FactoryMaker Facade;

    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    TextInputLayout nameCategory, descripcionCategory;
    String $nameCategory, $descripcionCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        back =findViewById(R.id.btn_cancelAddcategory);
        add = findViewById(R.id.btn_addCategory);

        nameCategory = findViewById(R.id.et_nameCategory);
        descripcionCategory = findViewById(R.id.et_descripcionCategory);

        nameCategory.getEditText().addTextChangedListener(validationTextWatcher);
        descripcionCategory.getEditText().addTextChangedListener(validationTextWatcher);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_company = String.valueOf(GlobalUsuario.idCompany);
                String[] categorys = {$nameCategory,$descripcionCategory, id_company};

                Facade = new FactoryMaker(AddCategoryActivity.this, url);
                Facade.FactoryCategoryMethodCreate(categorys);

                progressDialog = new ProgressDialog(AddCategoryActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("Add the category: "+$nameCategory);
                progressDialog.setIndeterminate(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                progressDialog.setMax(100);

                handler = new Handler();

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        e=e+3;
                        if(e<=100){
                            progressDialog.setProgress(e);
                        }else{
                            timer.cancel();
                            progressDialog.dismiss();
                            e=0;

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
                },2000,100);

            }
        });


    }

    private TextWatcher validationTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           if(validationAddCategory()){
               add.setVisibility(View.VISIBLE);
           }else{
               add.setVisibility(View.GONE);
           }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private boolean validationNamecategory(){
        $nameCategory = nameCategory.getEditText().getText().toString().trim();

        if($nameCategory.isEmpty()){
            //nameCategory.setError("This fields is Empty");
            return false;
        }else{
            if($nameCategory.length() > 40){
                nameCategory.setError("Max Characters");
                return false;
            }
            nameCategory.setError(null);
            return true;


        }

    }
    private boolean validationDescripcioncategory(){
        $descripcionCategory = descripcionCategory.getEditText().getText().toString().trim();

        if($descripcionCategory.isEmpty()){
            //nameCategory.setError("This fields is Empty");
            return false;
        }else{
           /* if($descripcion.length() > 500){
                descripcionCategory.setError("Max Characters");
                return false;
            }*/
            descripcionCategory.setError(null);
            return true;
        }

    }

    private boolean validationAddCategory(){
        if(!validationNamecategory() | !validationDescripcioncategory()){
            return false;
        }
        return true;
    }



/*
    private void BottomDialogAddCategory(){


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(),R.style.BottomSheetDialogTheme);
        View bottomsheetView = LayoutInflater.from(getActivity())
                .inflate(R.layout.activity_bottom_sheet_add_category, (LinearLayout) v.findViewById(R.id.bottomsheetContainer2),false);

        Button BottomaddCategory = bottomsheetView.findViewById(R.id.btn_addCategory);
        Button closeAdd = bottomsheetView.findViewById(R.id.btn_closeBottomsheetCategory);

        nameCategory = bottomsheetView.findViewById(R.id.et_nameCategory);
        descripcionCategory = bottomsheetView.findViewById(R.id.et_descripcionCategory);

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validationAddCategory()){


                    Toast.makeText(getContext(),"Enviar",Toast.LENGTH_SHORT).show();
                }

            }
        });

        closeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        //bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setContentView(bottomsheetView);
        bottomSheetDialog.show();

    }
*/


}