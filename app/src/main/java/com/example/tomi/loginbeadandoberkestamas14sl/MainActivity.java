package com.example.tomi.loginbeadandoberkestamas14sl;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button belepes, bejelentkezes, registration;
    private CheckBox csekk;
    private EditText editTelefonszam, editTeljesNev, jelszo, jelszoUjra, editNev;
    private RelativeLayout tvbox2, etbox2;

    private Toast toast = null;

    private boolean whatever = false;

    private AdatbazisSegito db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();

        registration.setOnClickListener(this);
        belepes.setOnClickListener(this);
        bejelentkezes.setOnClickListener(this);

        editNev.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Ures(editNev);
                EnableReg();
                isNev();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        jelszo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Ures(jelszo);
                EnableReg();
                isJelszo();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        jelszoUjra.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Ures(jelszoUjra);
                EnableReg();
                isJelszoUjra();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        editTeljesNev.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Ures(editTeljesNev);
                EnableReg();
                isTeljesNev();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        editTelefonszam.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Ures(editTelefonszam);
                EnableReg();
                isTelefonszam();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        SharedPreferences sh = getSharedPreferences("shname", Context.MODE_PRIVATE);
        if (sh.getBoolean("csekk", false)){
            load();
        }
    }

    private void init(){
        belepes = (Button) findViewById(R.id.belepes);
        bejelentkezes = (Button) findViewById(R.id.bejelentkezes);
        registration = (Button) findViewById(R.id.registration);

        csekk = (CheckBox) findViewById(R.id.csekk);

        editTelefonszam = (EditText) findViewById(R.id.editTelefonszam);
        editTeljesNev = (EditText) findViewById(R.id.editTeljesNev);
        jelszoUjra = (EditText) findViewById(R.id.jelszoUjra);
        jelszo = (EditText) findViewById(R.id.jelszo);
        editNev = (EditText) findViewById(R.id.editNev);

        tvbox2 = (RelativeLayout) findViewById(R.id.tvbox2);
        etbox2 = (RelativeLayout) findViewById(R.id.etbox2);

        db = new AdatbazisSegito(this);

    }

    public void adatRogzites()
    {
        String nickname = editNev.getText().toString();
        String password = jelszo.getText().toString();
        String fullname = editTeljesNev.getText().toString();
        String phoneNumber = editTelefonszam.getText().toString();

        boolean eredmeny = db.adatRogzites(nickname, password, fullname, phoneNumber);

        if (eredmeny)
        {
            Toast("Sikeres adatrögzítés");
        }else
        {
            Toast("Sikertelen adatrögzítés");
        }
    }

    private boolean RegCheck(String nev){
        Cursor eredmeny = db.adatLekerdezes();
        StringBuffer stringBuffer = new StringBuffer();


        if (eredmeny!=null && eredmeny.getCount()>0)
        {
            while(eredmeny.moveToNext())
            {
                if (nev.equals(eredmeny.getString(1))){
                    return true;
                }
            }

        }else
        {
            Toast("Nincs adat, amit le lehet kérni");
            return false;
        }
        Toast("A felhasználó név már foglalt!");
        return false;
    }

    private boolean LogInCheck(String nev, String jelszo){
        Cursor eredmeny = db.adatLekerdezes();
        StringBuffer stringBuffer = new StringBuffer();


        if (eredmeny!=null && eredmeny.getCount()>0)
        {
            while(eredmeny.moveToNext())
            {
                if (
                nev.equals(eredmeny.getString(1)) &&
                jelszo.equals(eredmeny.getString(2))){
                    return true;
                }
            }

        }else
        {
            Toast("Nincs adat, amit le lehet kérni");
            return false;
        }
        Toast("Helytelen hitelesítési adatok!");
        return false;
    }

    private void isNev(){
        if (NevHelyes()) editNev.setError("A névnek legalább 3 karakternek kell lennie!");
    }
    private void isJelszo(){
        if (JelszoHelyes()) jelszo.setError("A jelszónak legalább 6 karakter hosszűnak kell lennie!");
    }
    private void isJelszoUjra(){
        if (JelszoUjraHelyes()) jelszoUjra.setError("A két jelszónak eggyeznie kell!");
    }
    private void isTeljesNev(){
        if (TeljesNevHelyes()) editTeljesNev.setError("A névnek legalább 3 karakternek kell lennie!");
    }
    private void isTelefonszam(){
        if (editTelefonszam.getText().toString().length() < 10) editTelefonszam.setError("Túl rövid telefonszám!");
        if (editTelefonszam.getText().toString().length() > 11) editTelefonszam.setError("Túl hosszú telefonszám!");
    }

    private boolean NevHelyes(){
        return editNev.getText().toString().length() < 3;
    }
    private boolean JelszoHelyes(){
        return jelszo.getText().toString().length() < 6;
    }
    private boolean JelszoUjraHelyes(){
        return !(jelszoUjra.getText().toString().equals(jelszo.getText().toString()));
    }
    private boolean TeljesNevHelyes(){
        return editTeljesNev.getText().toString().length() < 3;
    }
    private boolean TelefonszamHelyes(){
        return (editTelefonszam.getText().toString().length() < 10) || (editTelefonszam.getText().toString().length() > 11);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.registration:
                if (!whatever){
                    Whatever(true);
                }else {
                    if (!RegCheck(editNev.getText().toString())){
                        adatRogzites();
                    }else {
                        editNev.setError("A név már foglalt!");
                    }
                }

                break;
            case R.id.bejelentkezes:
                Whatever(false);
                break;
            case R.id.belepes:
                if (!(jelszo.getText().toString().length() < 6) && !(editNev.getText().toString().length() < 3)) {
                    if (LogInCheck(editNev.getText().toString(), jelszo.getText().toString())) {
                        save();

                        Intent intent = new Intent(this, Main2Activity.class);
                        startActivity(intent);

                    }
                }else {
                    isNev();
                    isJelszo();
                }
                break;
            default:
        }

    }

    protected void onPause() {
        save();
        super.onPause();
    }

    private void save(){
        SharedPreferences sh = getSharedPreferences("shname", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sh.edit();

        edit.putString("editNev", String.valueOf(editNev.getText()));
        edit.putString("password", String.valueOf(jelszo.getText()));
        edit.putBoolean("csekk", csekk.isChecked());
        edit.apply();
    }

    private void load(){
        SharedPreferences sh = getSharedPreferences("shname", Context.MODE_PRIVATE);

        editNev.setText(sh.getString("editNev", ""));
        jelszo.setText(sh.getString("password", ""));
        csekk.setChecked(sh.getBoolean("csekk", false));
    }

    private void Toast(String szoveg){
        toast = Toast.makeText(getApplicationContext(), szoveg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void Whatever(boolean reg){
        int dur = 500;
        if (reg){
            ValueAnimator anim = ValueAnimator.ofInt(belepes.getMeasuredHeight(), 0);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams par = belepes.getLayoutParams();
                    par.height = val;
                    belepes.setLayoutParams(par);
                }
            });
            anim.setDuration(dur);

            ValueAnimator anim1 = ValueAnimator.ofInt(bejelentkezes.getMeasuredHeight(), 100);
            anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams par = bejelentkezes.getLayoutParams();
                    par.height = val;
                    bejelentkezes.setLayoutParams(par);
                }
            });
            anim1.setDuration(dur);

            ValueAnimator anim2 = ValueAnimator.ofInt(tvbox2.getMeasuredHeight(), 300);
            anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams par = tvbox2.getLayoutParams();
                    ViewGroup.LayoutParams par2 = etbox2.getLayoutParams();
                    par.height = val;
                    par2.height = val;
                    tvbox2.setLayoutParams(par);
                    etbox2.setLayoutParams(par2);
                }
            });
            anim2.setDuration(dur);

            anim.start();
            anim1.start();
            anim2.start();
            registration.setEnabled(false);
        }else{
            ValueAnimator anim = ValueAnimator.ofInt(belepes.getMeasuredHeight(), 100);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams par = belepes.getLayoutParams();
                    par.height = val;
                    belepes.setLayoutParams(par);
                }
            });
            anim.setDuration(dur);

            ValueAnimator anim1 = ValueAnimator.ofInt(bejelentkezes.getMeasuredHeight(), 0);
            anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams par = bejelentkezes.getLayoutParams();
                    par.height = val;
                    bejelentkezes.setLayoutParams(par);
                }
            });
            anim1.setDuration(dur);

            ValueAnimator anim2 = ValueAnimator.ofInt(tvbox2.getMeasuredHeight(), 0);
            anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    ViewGroup.LayoutParams par = tvbox2.getLayoutParams();
                    ViewGroup.LayoutParams par2 = etbox2.getLayoutParams();
                    par.height = val;
                    par2.height = val;
                    tvbox2.setLayoutParams(par);
                    etbox2.setLayoutParams(par2);
                }
            });
            anim2.setDuration(dur);

            anim.start();
            anim1.start();
            anim2.start();
            registration.setEnabled(true);
        }


        whatever = reg;
    }

    private void Ures(EditText editText){

        if (editText.getText().toString().equals("")) {
            editText.setError("Ez a mező nem maradhat üres!");
        }

    }

    private boolean UresE(EditText editText){

        if (editText.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    private void EnableReg(){
        if(whatever &&
        !UresE(editNev) && !NevHelyes() &&
        !UresE(jelszo) && !JelszoHelyes() &&
        !UresE(jelszoUjra) && !JelszoUjraHelyes() &&
        !UresE(editTeljesNev) && !TeljesNevHelyes() &&
        !UresE(editTelefonszam) && !TelefonszamHelyes()){
            registration.setEnabled(true);
        }else if(whatever) registration.setEnabled(false);
    }

}
