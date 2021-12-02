package com.example.shopping.screenSuplier;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.shopping.GlobalUsuario;
import com.example.shopping.R;
import com.example.shopping.screenSuplier.Facade.FactoryMaker;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class EditAndDeleteProductActivity extends AppCompatActivity {

    Button edit, back, delete, addCategory;
    Spinner nameCategory;
    RoundedImageView roundedImageView;

    String[] categorysnames = null;
    int[] idcategorys = null;

    ProgressDialog progressDialog;
    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    FactoryMaker Facade;

    //url
    String idCompany;
    String url_listCategory = "https://startbuying.000webhostapp.com/listCategory_product.php";
    String url_editProduct ="https://startbuying.000webhostapp.com/editProduct.php";
    String url_deleteProduct ="https://startbuying.000webhostapp.com/deleteProduct.php";
    String Addurl="https://startbuying.000webhostapp.com/InsertCategory.php";


    //principal
    TextInputLayout name, description, price;

    //Bottom category
    TextInputLayout namebottom,descriptionbottom;
    String $namec, $descriptionc;
    Button save, close;

    //image
    Bitmap bitmap;
    String encodedImage;
    Uri imageUri;
    Drawable lastImage;

    //Edit product
    String name_product, description_product, price_product;
    String image_product = null;
    String idCategory;
    String idProduct = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_delete_product);

        edit = findViewById(R.id.btn_editProduct_edit);
        back = findViewById(R.id.btn_backEditorDeleteProduct);
        delete = findViewById(R.id.btn_Deleteproduct);
        addCategory = findViewById(R.id.btn_newCategoryAddproduct_edit);

        Bundle var = getIntent().getExtras();

        name = findViewById(R.id.et_nameProduct_edit);
        description = findViewById(R.id.et_descripcionProduct_edit);
        price = findViewById(R.id.et_priceProduct_edit);
        roundedImageView = findViewById(R.id.riv_photoProduct_edit);
        nameCategory = findViewById(R.id.sp_categoryProduct_edit);

        name_product = var.getString("name");
        description_product = var.getString("description");
        price_product = var.getString("price");
        idProduct = var.getString("id");

        name.getEditText().setText(var.getString("name"));
        description.getEditText().setText(var.getString("description"));
        price.getEditText().setText(var.getString("price"));


        Glide.with(EditAndDeleteProductActivity.this).load(var.getString("image")).into(roundedImageView);

        name.getEditText().addTextChangedListener(validationTextWatcherProduct);
        description.getEditText().addTextChangedListener(validationTextWatcherProduct);
        price.getEditText().addTextChangedListener(validationTextWatcherProduct);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogDelete(idProduct);
            }
        });

        roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(EditAndDeleteProductActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent gallery = new Intent(Intent.ACTION_PICK);
                                gallery.setType("image/*");
                                startActivityForResult(Intent.createChooser(gallery, "Select Image"), 1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken token) {

                                token.continuePermissionRequest();

                            }
                        }).check();


            }
        });

        lastImage = roundedImageView.getDrawable();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validationProduct()) {

                    String[] dates = new String[6];

                    image_product = encodedImage;

                    if(image_product == null){
                     image_product = "nada";
                    }

                    dates[0] = idProduct;
                    dates[1] = name_product;
                    dates[2] = description_product;
                    dates[3] = price_product;
                    dates[4] = idCategory;
                     dates[5] = image_product;


                    /*Toast.makeText(EditAndDeleteProductActivity.this,
                            dates[0]+"\n"+dates[1]+"\n"+dates[2]+"\n"+dates[3]+"\n"+
                                    dates[4]+"\n"+dates[5],Toast.LENGTH_SHORT).show();*/

                    Facade = new FactoryMaker(EditAndDeleteProductActivity.this, url_editProduct);
                    Facade.FactoryProductMethodUpdate(dates);
                    dialog();


                }

            }
        });

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialog();
            }
        });

        nameCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cat = parent.getItemAtPosition(position).toString();
                if(cat.equalsIgnoreCase("No Category")){
                    idCategory = null;
                }else{
                    idCategory = String.valueOf(idcategorys[position]);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void dialog() {

        progressDialog = new ProgressDialog(EditAndDeleteProductActivity.this);
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
                e = e + 10;
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
        }, 2000, 500);



    }
    private TextWatcher validationTextWatcherProduct = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(validationProduct()){
                edit.setVisibility(View.VISIBLE);
            }else{
                edit.setVisibility(View.GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private boolean validationProduct(){

        if(!validationNameProduct() | !validationDecriptionProduct() |
                !validationPriceProduct() | !validationIdProduct()){
            return false;
        }

        return true;
    }
    private boolean validationIdProduct() {

        if (idCategory.isEmpty()){
            Toast.makeText(EditAndDeleteProductActivity.this,"Select category",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }
    private boolean validationPriceProduct() {
        price_product = price.getEditText().getText().toString().trim();
        if (price_product.isEmpty()) {
            price.setError("field empty!");
            return false;
        } else {
            price.setError(null);
            return true;
        }
    }
    private boolean validationDecriptionProduct() {
        description_product = description.getEditText().getText().toString().trim();
        if (description_product.isEmpty()) {
            description.setError("field empty!");
            return false;
        } else {
            description.setError(null);
            return true;
        }

    }
    private boolean validationNameProduct() {
        name_product = name.getEditText().getText().toString().trim();
        if (name_product.isEmpty()) {
            name.setError("field empty!");
            return false;
        } else {
            if (name_product.length()>50){
                name.setError("Max Characters!");
                return false;
            }
            name.setError(null);
            return true;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                roundedImageView.setImageBitmap(bitmap);
                imageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] imageByte = stream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageByte, Base64.DEFAULT);
    }
    @Override
    protected void onStart() {
        super.onStart();
        idCompany = String.valueOf(GlobalUsuario.idCompany);
        listCategory();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Progress();
    }
    private void listCategory(){

        StringRequest con = new StringRequest(StringRequest.Method.POST, url_listCategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    try {

                        JSONArray array = new JSONArray(response);
                        categorysnames = new String[array.length()];;
                        idcategorys = new int[array.length()];
                        int i;

                        for(i=0; i < array.length(); i++){
                            JSONObject row = array.getJSONObject(i);
                            categorysnames[i] = row.getString("name_category");
                            idcategorys[i] = row.getInt("id_category");
                        }

                    } catch (JSONException ex) {
                        Log.i("Error data",ex.getMessage().toString());
                        categorysnames = new String[]{"No Category"};
                        idcategorys = new int[]{0};
                        //ex.printStackTrace();
                    }
                }
                //Toast.makeText(context, "La consulta no retorna nada.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error data",error.getMessage().toString());
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

        RequestQueue requestQueue = Volley.newRequestQueue(EditAndDeleteProductActivity.this);
        requestQueue.add(con);

    }
    private void Progress() {

        progressDialog = new ProgressDialog(EditAndDeleteProductActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Loading data...");
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditAndDeleteProductActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,categorysnames);
                    nameCategory.setAdapter(adapter);

                }

            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 2000, 100);

    }
    private void bottomDialogDelete(final String idProducts) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EditAndDeleteProductActivity.this, R.style.BottomSheetDialogTheme);
        View bottomsheetView = LayoutInflater.from(EditAndDeleteProductActivity.this)
                .inflate(R.layout.layout_alert_delete, (LinearLayout) findViewById(R.id.bottom_sheet_alert_delete));

        Button yes = bottomsheetView.findViewById(R.id.bottom_delete_btnYes);
        Button no = bottomsheetView.findViewById(R.id.bottom_delete_btnNo);

        TextView info = bottomsheetView.findViewById(R.id.bottom_delete_info);

        info.setText("You want to permanently remove this product.");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Facade = new FactoryMaker(EditAndDeleteProductActivity.this, url_deleteProduct);
                Facade.FactoryProductMethodDelete(idProducts);
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
    private void BottomDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(EditAndDeleteProductActivity.this, R.style.BottomSheetDialogTheme);
        View bottomsheetView = LayoutInflater.from(EditAndDeleteProductActivity.this)
                .inflate(R.layout.activity_bottom_sheet_addcategory_product, (LinearLayout) findViewById(R.id.bottom_sheetaddcategoryproduct));

        save = bottomsheetView.findViewById(R.id.btn_addCategoryproduct);
        close = bottomsheetView.findViewById(R.id.btn_cancelAddproductcc);

        namebottom = bottomsheetView.findViewById(R.id.et_nameCategoryproduct);
        descriptionbottom = bottomsheetView.findViewById(R.id.et_descripcionCategoryproduct);

        namebottom.getEditText().addTextChangedListener(validationTextWatcher);
        descriptionbottom.getEditText().addTextChangedListener(validationTextWatcher);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validationAddCategory()){

                    String id_company = String.valueOf(GlobalUsuario.idCompany);
                    String[] categorys = {$namec,$descriptionc, id_company};
                    Facade = new FactoryMaker(EditAndDeleteProductActivity.this, Addurl);
                    Facade.FactoryCategoryMethodCreate(categorys);
                    setAddCategoryProgress();
                    bottomSheetDialog.dismiss();
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        //bottomSheetDialog.setCanceledOnTouchOutside(false);
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
                save.setVisibility(View.VISIBLE);
            }else{
                save.setVisibility(View.GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void setAddCategoryProgress(){

        progressDialog = new ProgressDialog(EditAndDeleteProductActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Edit the category: "+$namec);
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

                    recreate();
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
    private boolean validationNamecategory(){
        $namec = namebottom.getEditText().getText().toString().trim();

        if($namec.isEmpty()){
            namebottom.setError("this field is empty");
            return false;
        }else{
            if($namec.length() > 40){
                namebottom.setError("Max Characters");
                return false;
            }
            namebottom.setError(null);
            return true;


        }

    }
    private boolean validationDescripcioncategory(){
        $descriptionc = descriptionbottom.getEditText().getText().toString().trim();

        if($descriptionc.isEmpty()){
            descriptionbottom.setError("This fields is Empty");
            return false;
        }else{
            descriptionbottom.setError(null);
            return true;
        }

    }
    private boolean validationAddCategory(){
        if(!validationNamecategory() | !validationDescripcioncategory()){
            return false;
        }
        return true;
    }


}