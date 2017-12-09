package com.example.pat.taxcounter;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Handler;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Umowa_dzielo extends AppCompatActivity {
    public static Double podatek=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umowa_dzielo);
        Button imgB = (Button) findViewById(R.id.gmail);
        imgB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message ="Podatek wynosi: "+ podatek.toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(intent);
            }
        });
    }
    public void onOblicz(View view) {
        EditText editText = (EditText) findViewById(R.id.podstawa) ;
        TextView textView =(TextView) findViewById(R.id.podatek);
        if(String.valueOf(editText.getText()).isEmpty()) Toast.makeText(getApplicationContext(), "Wprowadz wynagrodzenie", Toast.LENGTH_SHORT).show();
        else {  Double podstawa = Double.parseDouble(editText.getText().toString());
            RadioButton poza = (RadioButton) findViewById(R.id.poza_miejscem);
            CheckBox zdrowotnaCheck = (CheckBox) findViewById(R.id.zdrowotna_radio);
            CheckBox zus_Check = (CheckBox) findViewById(R.id.zus_radio);

            boolean zdrowoBool = zdrowotnaCheck.isChecked();
            boolean zusBool = zus_Check.isChecked();
            Double skladka_zus = 0.0;
            Double skladka_zdrowotna = 0.0;

            boolean pozaBool = poza.isChecked();

            if (zusBool) {
                skladka_zus = podstawa * 0.1371;
            }
            if (zdrowoBool) {
                skladka_zdrowotna = podstawa * 0.075;
            }


            if (pozaBool) {
                podatek = (podstawa - skladka_zus - 133.5) * 0.18 - 46.33;
            } else {
                podatek = (podstawa - skladka_zus - 111.25) * 0.18 - 46.33;
            }

            Double do_zaplaty = podatek - (skladka_zdrowotna);
            textView.setText("Podatek wynosi: " + String.valueOf(do_zaplaty));
        }
    }
}
