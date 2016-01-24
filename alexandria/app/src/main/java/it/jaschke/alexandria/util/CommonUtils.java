package it.jaschke.alexandria.util;

import android.content.Context;

import it.jaschke.alexandria.R;

/**
 * Created by aditlal on 24/01/16.
 */
public class CommonUtils {

    public static boolean isTablet(Context c) {
        return c.getResources().getBoolean(R.bool.isTablet);
    }

}
