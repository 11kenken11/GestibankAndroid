package com.sip.gestibank;

import android.app.Application;
import android.content.Context;

import com.sip.gestibank.Helper.LocaleHelper;

public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "fr"));
    }
}
