package com.example.shopping.screenMain;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopping.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScreenSignup extends AppCompatActivity {

    TextInputLayout username, firstname, lastname, phone, address, password, postalcode, email;

    String $username, $firstname, $lastname, $phone, $address, $password, $postalcode, $email;
    String[] $adress_detalle = new String[5];

    Button next, back;

    CheckBox termPolicy;

    ConstraintLayout snackview;

    String url="https://startbuying.000webhostapp.com/validandoEmail.php";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_signup);

        username = findViewById(R.id.tf_signupUser);
        email = findViewById(R.id.tf_signupEmail);
        firstname = findViewById(R.id.tf_signupFirstName);
        lastname = findViewById(R.id.tf_signupLastName);
        password = findViewById(R.id.tf_SignupPassword);
        snackview = findViewById(R.id.snabackviewSignup);

        phone = findViewById(R.id.tf_signupPhone);
        address = findViewById(R.id.tf_signupAddress);
        termPolicy = findViewById(R.id.cb_terminePolicy);
        next = findViewById(R.id.btn_nextSingup);
        back = findViewById(R.id.btn_backSingup);

        String Contrant = "You accept our Terms, Data Policy and Cookies Policy more...";
        SpannableString css = new SpannableString(Contrant);

        ClickableSpan clickableSpanMore = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                ///Dialog.
                final MaterialAlertDialogBuilder build = new MaterialAlertDialogBuilder(ScreenSignup.this)
                        .setTitle("TERMS AND CONDITIONS")
                        .setIcon(R.drawable.ic_policy)
                        .setBackground(getResources().getDrawable(R.drawable.background_alert_term_policy))
                        .setMessage(getResources().getString(R.string.terminosPoliticy));
                build.setPositiveButton("Great!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                build.show();

            }
        };

        css.setSpan(clickableSpanMore,53, 60, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        termPolicy.setText(css);
        termPolicy.setMovementMethod(LinkMovementMethod.getInstance());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        address.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                     BottomDialog();
                     return true;
                }
                return false;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validationSignup()) {

                    onlyEmail_validacion($email);

                    /*

                    *///

                }

            }
        });

    }

    TextInputLayout country, city, sector, street,numberhouse;

    private void BottomDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ScreenSignup.this, R.style.BottomSheetDialogTheme);
        View bottomsheetView = LayoutInflater.from(ScreenSignup.this)
                .inflate(R.layout.activity_bottom_sheet_signup, (LinearLayout) findViewById(R.id.bottomsheetContainer));


        Button saveAdress = bottomsheetView.findViewById(R.id.btn_signupaAddress_saved);
        Button closeAdress = bottomsheetView.findViewById(R.id.btn_closeBottomsheet);
        country = bottomsheetView.findViewById(R.id.tf_signupCountry);
        city = bottomsheetView.findViewById(R.id.tf_signupCity);
        sector = bottomsheetView.findViewById(R.id.tf_signupSector);
        street = bottomsheetView.findViewById(R.id.tf_signupStreet);

        numberhouse = bottomsheetView.findViewById(R.id.tf_signupNumberAptHouse);

        postalcode = bottomsheetView.findViewById(R.id.tf_signupCodigo_postal);

        if(!address.getEditText().getText().toString().trim().isEmpty()){
            country.getEditText().setText($adress_detalle[0]);
            city.getEditText().setText($adress_detalle[1]);
            sector.getEditText().setText($adress_detalle[2]);
            street.getEditText().setText($adress_detalle[3]);
            numberhouse.getEditText().setText($adress_detalle[4]);
            postalcode.getEditText().setText($postalcode);
        }

        saveAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validationAdress_detalle()){
                    String _adress = $adress_detalle[0]+", "+$adress_detalle[1]+"; "+$adress_detalle[2]+
                    ", st. "+$adress_detalle[3]+" /#"+$adress_detalle[4];
                    address.getEditText().setText(_adress);
                    bottomSheetDialog.dismiss();
                }
            }
        });

    closeAdress.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bottomSheetDialog.cancel();
        }
    });

        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.setContentView(bottomsheetView);
        bottomSheetDialog.show();

    }

    private boolean validationCountry() {
        $adress_detalle[0] = country.getEditText().getText().toString().trim();
        if ($adress_detalle[0].isEmpty()) {
            country.setError("field empty!");
            return false;
        } else {
            country.setError(null);
            return true;
        }
    }

    private boolean validationCity() {
        $adress_detalle[1] = city.getEditText().getText().toString().trim();
        if ($adress_detalle[1].isEmpty()) {
            city.setError("field empty!");
            return false;
        } else {
            city.setError(null);
            return true;
        }
    }

    private boolean validationSector() {
        $adress_detalle[2] = sector.getEditText().getText().toString().trim();
        if ($adress_detalle[2].isEmpty()) {
            sector.setError("field empty!");
            return false;
        } else {
            sector.setError(null);
            return true;
        }
    }

    private boolean validationStreet() {
        $adress_detalle[3] = street.getEditText().getText().toString().trim();
        if ($adress_detalle[3].isEmpty()) {
           street.setError("field empty!");
            return false;
        } else {
            street.setError(null);
            return true;
        }
    }

    private boolean validationNumerhouse() {
        String num = numberhouse.getEditText().getText().toString().trim();
        $adress_detalle[4] = num;

        if ($adress_detalle[4].isEmpty()) {
            numberhouse.setError("field empty!");
            return false;
        } else {
            numberhouse.setError(null);
            return true;
        }
    }

    private boolean validationPostalcode() {
        $postalcode = postalcode.getEditText().getText().toString().trim();
        if ($postalcode.isEmpty()) {
            postalcode.setError("field empty!");
            return false;
        } else {
            postalcode.setError(null);
            return true;
        }
    }

    private boolean validationAdress_detalle(){

        if (!validationCountry() | !validationCity()
                | !validationStreet() | !validationSector()| !validationNumerhouse() | !validationPostalcode()) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_top, R.anim.out_top);
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


    private boolean validationUsername() {
        $username = username.getEditText().getText().toString().trim();
        if ($username.isEmpty()) {
            username.setError("field empty!");
            return false;
        } else {
            if ($username.length() > 30) {
                username.setError("maximum characters!");
                return false;
            }
            username.setError(null);
            return true;
        }
    }

    private boolean validationFirstname() {
        $firstname = firstname.getEditText().getText().toString().trim();
        if ($firstname.isEmpty()) {
            firstname.setError("field empty!");
            return false;
        } else {
            if ($firstname.length() > 30) {
                firstname.setError("maximum characters!");
                return false;
            }
            firstname.setError(null);
            return true;
        }
    }

    private boolean validationLastname() {
        $lastname = lastname.getEditText().getText().toString().trim();
        if ($lastname.isEmpty()) {
            lastname.setError("field empty!");
            return false;
        } else {
            if ($lastname.length() > 30) {
                lastname.setError("maximum characters!");
                return false;
            }
            lastname.setError(null);
            return true;
        }
    }

    private boolean validationPhone() {
        $phone = phone.getEditText().getText().toString().trim();
        if ($phone.isEmpty()) {
            phone.setError("field empty!");
            return false;
        } else {
            if ($phone.length() < 15) {
                phone.setError("digits are missing!");
                return false;
            }

            phone.setError(null);
            return true;
        }
    }

    private boolean validationPassword() {
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

    private boolean validationAdress() {
        $address = address.getEditText().getText().toString().trim();
        if ($address.isEmpty()) {
            address.setError("field empty!");
            return false;
        } else {
            if ($address.length() > 80) {
                address.setError("maximum characters!");
                return false;
            }
            lastname.setError(null);
            return true;
        }
    }

    private boolean validationTermin(){

        if(termPolicy.isChecked()){
            return true;
        }
        return false;
    }


    private boolean validationSignup() {
        if (!validationUsername() | !validationFirstname() | !validationLastname()
                | !validationPhone() | !validationPassword() | !validationAdress()
        | !validationEmail()) {
            return false;
        }
        return true;
    }

    private void onlyEmail_validacion(final String _email) {
        StringRequest con = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null && response.length() > 0) {
                   email.setError("Email Already!");
                }else{
                    Enter();
                }

             }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(screenSignup1.this, "Saved", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email_user", _email);
                return parametros;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ScreenSignup.this);
        requestQueue.add(con);
    }

    private void Enter(){

        if(validationTermin()){
            Bundle var = new Bundle();
            Intent start = new Intent(ScreenSignup.this, screenSignup1.class);
            var.putString("username", $username);
            var.putString("firstname", $firstname);
            var.putString("lastname", $lastname);
            var.putString("address", $address);
            var.putString("password", $password);
            var.putString("phone", $phone);
            var.putString("statususer", "true");
            var.putString("postal", $postalcode);
            var.putString("email", $email);
            start.putExtras(var);
            startActivity(start);
            overridePendingTransition(R.anim.to_right, R.anim.out_left);
        }else{
            Snackbar.make(snackview, "You must accept the terms of the app", Snackbar.LENGTH_SHORT)
                    .setAction("Accept", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            termPolicy.setChecked(true);
                        }

                    }).show();
        }
    }



}