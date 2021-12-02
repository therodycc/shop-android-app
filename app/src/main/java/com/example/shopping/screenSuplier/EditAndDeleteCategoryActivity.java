package com.example.shopping.screenSuplier;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.screenSuplier.Facade.FactoryMaker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Timer;
import java.util.TimerTask;

public class EditAndDeleteCategoryActivity extends AppCompatActivity {

    Button back, delete, edit;
    TextInputLayout nameCategory, descripcionCategory;
    String $nameCategory, $descripcionCategory;

    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    FactoryMaker Facade;

    String idCategory = "";

    String urlDelete ="https://startbuying.000webhostapp.com/deleteCategory.php";
    String urlEdit ="https://startbuying.000webhostapp.com/updateCategory.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete_category);

        back = findViewById(R.id.btn_backEditorDeleteCategory);
        delete = findViewById(R.id.btn_Delete_EditorDeletecategory);
        edit = findViewById(R.id.btn_edit_EditorDeleteCategory);

        Bundle reviewCategory = getIntent().getExtras();

        nameCategory = findViewById(R.id.et_nameEditorDeleteCategory);
        descripcionCategory = findViewById(R.id.et_descripcionEditorDeleteCategory);

        nameCategory.getEditText().setText(reviewCategory.getString("name_category"));
        descripcionCategory.getEditText().setText(reviewCategory.getString("descripcion_category"));
        idCategory =reviewCategory.getString("id_category");

        nameCategory.getEditText().addTextChangedListener(validationTextWatcher);
        descripcionCategory.getEditText().addTextChangedListener(validationTextWatcher);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogDelete(idCategory);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        String $name, $description;
                        $name = nameCategory.getEditText().getText().toString();
                        $description = descripcionCategory.getEditText().getText().toString();
                        String[] date = {idCategory,$name,$description};

                Facade = new FactoryMaker(EditAndDeleteCategoryActivity.this,urlEdit);
                Facade.FactoryCategoryMethodUpdate(date);

                dialog();
            }
        });


    }

    private void bottomDialogDelete(final String id) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EditAndDeleteCategoryActivity.this, R.style.BottomSheetDialogTheme);
        View bottomsheetView = LayoutInflater.from(EditAndDeleteCategoryActivity.this)
                .inflate(R.layout.layout_alert_delete, (LinearLayout) findViewById(R.id.bottom_sheet_alert_delete));

        Button yes= bottomsheetView.findViewById(R.id.bottom_delete_btnYes);
        Button no= bottomsheetView.findViewById(R.id.bottom_delete_btnNo);

        TextView info = bottomsheetView.findViewById(R.id.bottom_delete_info);

        info.setText("You want to permanently remove this category.");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Facade = new FactoryMaker(EditAndDeleteCategoryActivity.this,urlDelete);
                Facade.FactoryCategoryMethodDelete(id);
                dialog();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
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
            if(validationAddCategory()){
                edit.setVisibility(View.VISIBLE);
            }else{
                edit.setVisibility(View.GONE);
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

    private void dialog(){

        progressDialog = new ProgressDialog(EditAndDeleteCategoryActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
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

}