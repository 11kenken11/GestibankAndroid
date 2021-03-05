package com.sip.gestibank;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BaseActivity  extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    protected void attachBaseContext(Context newBase) {
        //super.attachBaseContext(LocaleHelper.wrap(newBase, AppClass.getLanguage(newBase)));

/**
 *save value in sharedPrefrences
 *
 *AppClass.getLanguage(newBase)
 *
 *
 **/

    }


    /**
     * A locale helper class for updating app specific locale
     */
    private static class LocaleHelper extends ContextWrapper {

        public LocaleHelper(Context base) {
            super(base);
        }

        public static ContextWrapper wrap(Context context, String language) {
            if (TextUtils.isEmpty(language.trim())) {
                return new LocaleHelper(context);
            }
            Configuration config = context.getResources().getConfiguration();
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale);
            } else {
                //noinspection deprecation
                config.locale = locale;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLayoutDirection(locale);
                context = context.createConfigurationContext(config);
            } else {
                //noinspection deprecation
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            }
            return new LocaleHelper(context);
        }

    } // LocaleHelper


}
