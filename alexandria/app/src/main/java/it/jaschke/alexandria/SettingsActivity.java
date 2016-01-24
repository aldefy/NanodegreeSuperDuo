package it.jaschke.alexandria;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by aditlal on 20/01/16.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


    }
}
