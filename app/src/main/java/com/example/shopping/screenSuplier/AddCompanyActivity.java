package com.example.shopping.screenSuplier;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class AddCompanyActivity extends AppCompatActivity {

    TextInputLayout ft_country_company, ft_address_company, ft_phone_company, ft_socialNetwork_company, ft_nameCompany_company;
    AutoCompleteTextView dropDownText;

    Bitmap bitmap;
    String encodedImage;
    Uri imageUri;

    Handler handler;
    Timer timer;
    Runnable runnable;
    int e;

    String $nameCompany, $socialCompany, $phoneCompany, $addressCompany, $countryCompany;
    String[] $adress_detalle = new String[5];
    Button create,back;
    Drawable lastImage;
    RoundedImageView roundedImageView;

    String url = "https://startbuying.000webhostapp.com/createCompany.php";

    ProgressDialog progressDialog;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_company);

        ft_nameCompany_company = findViewById(R.id.et_nameCompany);
        ft_phone_company = findViewById(R.id.etphoneCompany);
        ft_socialNetwork_company = findViewById(R.id.et_SocialNetworkCompany);
        ft_country_company = findViewById(R.id.et_CountryCompany);
        ft_address_company = findViewById(R.id.tf_addressCompany);
        roundedImageView = findViewById(R.id.riv_photoCompany);

        create = findViewById(R.id.btn_CreateCompany);
        back = findViewById(R.id.back_addCompany);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        roundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(AddCompanyActivity.this)
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

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation_company()) {
                    String idUsuario = String.valueOf(GlobalUsuario.idusuario);
                    if (roundedImageView.getDrawable() == lastImage) {
                        Toast.makeText(AddCompanyActivity.this, "you have not selected any image!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        String[] dates = {encodedImage, $socialCompany, $phoneCompany, $countryCompany, $addressCompany, idUsuario,$nameCompany};

                        FactoryMaker factoryMaker = new FactoryMaker(AddCompanyActivity.this, url);
                        factoryMaker.FactoryCompanyMethodCreated(dates);
                        dialog();

                    }


                }

            }
        });

        dropDownText = findViewById(R.id.dropdown_menu_country);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AddCompanyActivity.this, R.layout.dropdown_item_adapter, allcountre()
        );

        dropDownText.setAdapter(adapter);

        ft_address_company.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    BottomDialog();
                    return true;
                }
                return false;
            }
        });

    }

    private void dialog() {

        progressDialog = new ProgressDialog(AddCompanyActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loadin...");
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

    private boolean validation_company() {
        if (!validationName_company() | !validationPhone_company()
                | !validationSocialNetwork_company() | !validationCountry_company()
                | !validationAddress_company()) {
            return false;
        }
        return true;
    }

    private boolean validationAddress_company() {
        $addressCompany = ft_address_company.getEditText().getText().toString().trim();
        if ($addressCompany.isEmpty()) {
            ft_address_company.setError("field empty!");
            return false;
        } else {
            ft_address_company.setError(null);
            return true;
        }
    }

    private boolean validationCountry_company() {
        $countryCompany = ft_country_company.getEditText().getText().toString().trim();
        if ($countryCompany.isEmpty()) {
            ft_country_company.setError("field empty!");
            return false;
        } else {
            if ($countryCompany.length() > 50) {
                ft_country_company.setError("Max Characters");
                return false;
            }

            ft_country_company.setError(null);
            return true;
        }

    }

    private boolean validationSocialNetwork_company() {
        $socialCompany = ft_socialNetwork_company.getEditText().getText().toString().trim();
        if ($phoneCompany.isEmpty()) {
            ft_socialNetwork_company.setError("field empty!");
            return false;
        } else {
            ft_socialNetwork_company.setError(null);
            return true;
        }
    }

    private boolean validationPhone_company() {
        $phoneCompany = ft_phone_company.getEditText().getText().toString().trim();
        if ($phoneCompany.isEmpty()) {
            ft_phone_company.setError("field empty!");
            return false;
        } else {
            if ($phoneCompany.length() < 14) {
                ft_phone_company.setError("Missing characters!");
            }
            ft_phone_company.setError(null);
            return true;
        }
    }

    private boolean validationName_company() {
        $nameCompany = ft_nameCompany_company.getEditText().getText().toString().trim();
        if ($nameCompany.isEmpty()) {
            ft_nameCompany_company.setError("field empty!");
            return false;
        } else {
            if ($nameCompany.length() > 50) {
                ft_nameCompany_company.setError("maximum characters!");
                return false;
            }
            ft_nameCompany_company.setError(null);
            return true;
        }
    }




    TextInputLayout city, sector, street, numerohouse;


    private void BottomDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AddCompanyActivity.this, R.style.BottomSheetDialogTheme);
        View bottomsheetView = LayoutInflater.from(AddCompanyActivity.this)
                .inflate(R.layout.activity_bottom_sheet_signup_company_address, (LinearLayout) findViewById(R.id.bottomsheetContainer_company));

        Button saveAdress = bottomsheetView.findViewById(R.id.btn_saveaddressCompany);
        Button closeAdress = bottomsheetView.findViewById(R.id.btn_closeBottomsheetCompany);

        city = bottomsheetView.findViewById(R.id.et_cityCompany);
        sector = bottomsheetView.findViewById(R.id.et_sectorCompany);
        street = bottomsheetView.findViewById(R.id.et_streetCompany);
        numerohouse = bottomsheetView.findViewById(R.id.et_noapthouseCompany);

        if(!ft_address_company.getEditText().getText().toString().trim().isEmpty()){
            city.getEditText().setText($adress_detalle[0]);
            sector.getEditText().setText($adress_detalle[1]);
            street.getEditText().setText($adress_detalle[2]);
            numerohouse.getEditText().setText($adress_detalle[3]);
        }


        saveAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationAdress_detalle()) {
                    String Att = "ct." + $adress_detalle[0] + ", sector: " +
                            $adress_detalle[1] + ", st." + $adress_detalle[2] + "/#" +
                            $adress_detalle[3];

                    ft_address_company.getEditText().setText(Att);
                    bottomSheetDialog.dismiss();
                }

            }
        });

        closeAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setContentView(bottomsheetView);
        bottomSheetDialog.show();
    }

    private boolean validationCity() {
        $adress_detalle[0] = city.getEditText().getText().toString().trim();
        if ($adress_detalle[0].isEmpty()) {
            city.setError("field empty!");
            return false;
        } else {
            city.setError(null);
            return true;
        }
    }

    private boolean validationSector() {
        $adress_detalle[1] = sector.getEditText().getText().toString().trim();
        if ($adress_detalle[1].isEmpty()) {
            sector.setError("field empty!");
            return false;
        } else {
            sector.setError(null);
            return true;
        }
    }

    private boolean validationStreet() {
        $adress_detalle[2] = street.getEditText().getText().toString().trim();
        if ($adress_detalle[2].isEmpty()) {
            street.setError("field empty!");
            return false;
        } else {
            street.setError(null);
            return true;
        }
    }

    private boolean validationNumerhouse() {
        String num = numerohouse.getEditText().getText().toString().trim();
        $adress_detalle[3] = num;

        if ($adress_detalle[3].isEmpty()) {
            numerohouse.setError("field empty!");
            return false;
        } else {
            numerohouse.setError(null);
            return true;
        }
    }


    private boolean validationAdress_detalle() {

        if (!validationCity() | !validationStreet() | !validationSector() | !validationNumerhouse()) {
            return false;
        }
        return true;
    }


    private String[] allcountre() {
        String[] country = new String[]{"Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda", "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bangladés", "Barbados", "Baréin", "Bélgica", "Belice", "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina", "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi", "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Catar", "Chad", "Chile", "China", "Chipre", "Ciudad del Vaticano", "Colombia", "Comoras", "Corea del Norte", "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba", "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guyana", "Guinea", "Guinea ecuatorial", "Guinea-Bisáu", "Haití", "Honduras", "Hungría", "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall", "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos", "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein", "Lituania", "Luxemburgo", "Madagascar", "Malasia", "Malaui", "Maldivas", "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania", "México", "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique", "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria", "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán", "Palaos", "Panamá", "Papúa Nueva Guinea", "Paraguay", "Perú", "Polonia", "Portugal", "Reino Unido", "República Centroafricana", "República Checa", "República de Macedonia", "República del Congo", "República Democrática del Congo", "República Dominicana", "República Sudafricana", "Ruanda", "Rumanía", "Rusia", "Samoa", "San Cristóbal y Nieves", "San Marino", "San Vicente y las Granadinas", "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia", "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia", "Sri Lanka", "Suazilandia", "Sudán", "Sudán del Sur", "Suecia", "Suiza", "Surinam", "Tailandia", "Tanzania", "Tayikistán", "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez", "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda", "Uruguay", "Uzbekistán", "Vanuatu", "Venezuela", "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue"};
        return country;
    }

}