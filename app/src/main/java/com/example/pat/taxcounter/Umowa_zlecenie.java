package com.example.pat.taxcounter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Umowa_zlecenie extends AppCompatActivity {
    public static Double podatek = 0.0;
    private TextView textView;
    private String podatek_wynosi = "";
    String wyswietl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umowa_praca);
        textView = (TextView) findViewById(R.id.podatek);
        podatek_wynosi = "Podatek wynosi: ";
        textView.setText(podatek_wynosi);
    }
    public void onOblicz(View view) {
        EditText editText = (EditText) findViewById(R.id.podstawa);

        if (String.valueOf(editText.getText()).isEmpty())
            Toast.makeText(getApplicationContext(), "Wprowadz wynagrodzenie", Toast.LENGTH_SHORT).show();
        else {
            Double podstawa = Double.parseDouble(editText.getText().toString());

            CheckBox zdrowotnaCheck = (CheckBox) findViewById(R.id.zdrowotna_radio);
            CheckBox zus_Check = (CheckBox) findViewById(R.id.zus_radio);

            boolean zdrowoBool = zdrowotnaCheck.isChecked();
            boolean zusBool = zus_Check.isChecked();
            Double skladka_zus = 0.0;
            Double skladka_zdrowotna = 0.0;
            Double do_zaplaty = 0.0;

            final double procent_zus = 0.1371;
            final double procent_zdrowotna = 0.075;
            final double koszt_uzyskania_przych = 133.5;
            final double koszt_uz_przych_poza = 111.25;
            final double procent_podatek = 0.18;
            final double kwota_wolna_od_pod = 46.33;

            if (zusBool) {
                skladka_zus = podstawa * procent_zus;
            }
            if (zdrowoBool) {
                skladka_zdrowotna = podstawa * procent_zdrowotna;
            }

            do_zaplaty = podatek - (skladka_zdrowotna);
            wyswietl = (podatek_wynosi + (String.valueOf(Math.round(do_zaplaty))));
            textView.setText(wyswietl);
        }
    }
    public void onWyslij(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, wyswietl);
        startActivity(intent);
    }
    String mCurrentPath;
    private File createTextFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String fileName = "Podatek_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = File.createTempFile(
                fileName,
                ".txt",
                storageDir
        );
        mCurrentPath = file.getAbsolutePath();
        return file;
    }

    public void onZapisz(View view) throws IOException {
        File textFile = null;
        try {
            textFile = createTextFile();
            FileWriter writer = new FileWriter(textFile, true);
            writer.append(wyswietl + "\n\n");
            writer.flush();
            writer.close();
            Toast.makeText(getApplicationContext(), "Plik utworzono", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }
}
