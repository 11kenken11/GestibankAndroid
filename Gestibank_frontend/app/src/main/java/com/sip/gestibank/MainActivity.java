package com.sip.gestibank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sip.gestibank.Helper.LocaleHelper;

import java.util.Locale;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    TextView alreadyRegisterdTV2;


    /*@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "fr"));
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpannableString ss = new SpannableString("Si vous avez déjà un compte, cliquer ici.");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 37, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        alreadyRegisterdTV2 = (TextView) findViewById(R.id.alreadyRegisterdTV2);
        alreadyRegisterdTV2.setText(ss);
        alreadyRegisterdTV2.setMovementMethod(LinkMovementMethod.getInstance());
        alreadyRegisterdTV2.setHighlightColor(Color.TRANSPARENT);

        /*Paper.init(this);

        String language = Paper.book().read("language");
        if(language == null) {
            Paper.book().write("language", "fr");
        }

        updateView((String) Paper.book().read("language"));*/
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.language_fr) {
            Paper.book().write("language", "fr");
            //updateView((String) Paper.book().read("language"));
            setLocale((String) Paper.book().read("language"));
        } else if(item.getItemId() == R.id.language_en) {
            Paper.book().write("language", "en");
            //updateView((String) Paper.book().read("language"));
            setLocale((String) Paper.book().read("language"));
        } else if (item.getItemId() == R.id.language_jpn) {
            Paper.book().write("language", "jpn");
            //updateView((String) Paper.book().read("language"));
            setLocale((String) Paper.book().read("language"));
        }
        return true;
    }

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this, lang);
        Resources resources = context.getResources();


    }*/

    public void callCreateAccountActivity(View view){
        Intent i = new Intent(getApplicationContext(), CreateAccountActivity.class);

        startActivity(i);
    }

    public void callConvertCurrencyActivity(View view){
        Intent i = new Intent(getApplicationContext(), ConvertCurrencyActivity.class);

        startActivity(i);
    }

    /*public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        invalidateOptionsMenu();
        recreate();
        Intent intent = getIntent();
        //finish();
        startActivity(intent);
    }*/
}