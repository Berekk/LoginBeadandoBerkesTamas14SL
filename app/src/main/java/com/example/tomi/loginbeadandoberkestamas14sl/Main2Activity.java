package com.example.tomi.loginbeadandoberkestamas14sl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private Button kijelentkezes;
    private TextView neved, adatb;
    private AdatbazisSegito db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();

        SharedPreferences sh = getSharedPreferences("shname", Context.MODE_PRIVATE);

        neved.setText("Üdv " + sh.getString("editNev", "") + "!");
    }

    private void init(){
        kijelentkezes = (Button) findViewById(R.id.kijelentkezes);
        neved = (TextView) findViewById(R.id.neved);
        adatb = (TextView) findViewById(R.id.adatb);
        kijelentkezes.setOnClickListener(this);

        db = new AdatbazisSegito(this);
        adatb.setMovementMethod(new ScrollingMovementMethod());
        adatLekeres();
    }

    public void adatLekeres()
    {
        Cursor eredmeny = db.adatLekerdezes();

        StringBuffer stringBuffer = new StringBuffer();

        if (eredmeny!=null && eredmeny.getCount()>0)
        {
            while(eredmeny.moveToNext())
            {
                stringBuffer.append("ID:" + eredmeny.getString(0) + "\n");
                stringBuffer.append("Név:" + eredmeny.getString(1) + "\n");
                stringBuffer.append("Jelszó:" + eredmeny.getString(2) + "\n");
                stringBuffer.append("Teljes név:" + eredmeny.getString(3) + "\n");
                stringBuffer.append("Telefonszám:" + eredmeny.getString(4) + "\n");
            }
            adatb.setText(stringBuffer.toString());
            Toast.makeText(this, "Adat sikeresen lekérve", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(this, "Nincs adat, amit le lehet kérni", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.kijelentkezes:
                //Intent intent = new Intent(this, MainActivity.class);
                //startActivity(intent);
                finish();
                break;
            default:
        }
    }
}
