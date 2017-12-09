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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Umowa_dzielo extends AppCompatActivity {
    public static Double podatek = 0.0;
    private TextView textView;
    private String podatek_wynosi = "";
    String wyswietl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umowa_dzielo);
        textView = (TextView) findViewById(R.id.podatek);
        podatek_wynosi = "Podatek wynosi: ";
        textView.setText(podatek_wynosi);
    }
    public void onOblicz(View view) {
        EditText editText = (EditText) findViewById(R.id.podstawa);
        double procentKoszty=0;
        if (String.valueOf(editText.getText()).isEmpty())
            Toast.makeText(getApplicationContext(), "Wprowadz wynagrodzenie", Toast.LENGTH_SHORT).show();
        else {
            Double podstawa = Double.parseDouble(editText.getText().toString());
            RadioGroup radioGroup =(RadioGroup) findViewById(R.id.radioGroup);
            int id = radioGroup.getCheckedRadioButtonId();
            switch (id){
                case R.id.radio_z: procentKoszty =0.5; break;
                case R.id.radio_bez: procentKoszty=0.2; break;
            }
            Double do_zaplaty = 0.0;
           double koszty= procentKoszty*podstawa;
            final double procent_podatek = 0.18;
            final double kwota_wolna_od_pod = 46.33;
            do_zaplaty = (podstawa-koszty)*procent_podatek-kwota_wolna_od_pod;
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
