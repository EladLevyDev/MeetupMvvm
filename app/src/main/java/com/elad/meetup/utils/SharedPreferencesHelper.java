package com.elad.meetup.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.elad.meetup.model.CryptoCurrency;

import java.util.Calendar;

/**
 * Helper class to manage access to {@link SharedPreferences}.
 */
public class SharedPreferencesHelper {

    // Keys for saving values in SharedPreferences.
    static final String KEY_NAME = "key_name";
    static final String KEY_ID = "key_id";
    static final String KEY_PRICE = "key_price";

    // The injected SharedPreferences implementation to use for persistence.
    private final SharedPreferences mSharedPreferences;

    /**
     * Constructor with dependency injection.
     *
     * @param sharedPreferences The {@link SharedPreferences} that will be used in this DAO.
     */
    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    /**
     * {@link SharedPreferences}.
     *
     * @return {@code true} if writing to {@link SharedPreferences} succeeded. {@code false}
     * otherwise.
     */
    public boolean savePersonalInfo(CryptoCurrency cryptoCurrency) {
        // Start a SharedPreferences transaction.
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_NAME, cryptoCurrency.getName());
        editor.putString(KEY_ID, cryptoCurrency.getId());
        editor.putString(KEY_PRICE, cryptoCurrency.getPriceBtc());

        // Commit changes to SharedPreferences.
        return editor.commit();
    }

    /**
     * {@link SharedPreferences}.
     */
    public boolean isCryptoCurrencyValid() {
        // Get data from the SharedPreferences.
        return !TextUtils.isEmpty(mSharedPreferences.getString(KEY_NAME, ""))
                && !TextUtils.isEmpty(mSharedPreferences.getString(KEY_ID, "")) &&
                !TextUtils.isEmpty(mSharedPreferences.getString(KEY_PRICE, ""));

    }
}