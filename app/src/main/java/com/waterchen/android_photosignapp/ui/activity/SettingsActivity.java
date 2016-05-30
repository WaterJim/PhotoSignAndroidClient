package com.waterchen.android_photosignapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.waterchen.android_photosignapp.R;
import com.waterchen.android_photosignapp.ui.common.BaseActivity;

/**
 * Created by 橘子哥 on 2016/5/23.
 */
public class SettingsActivity extends BaseActivity {

    private FrameLayout mFragmentContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mFragmentContent = findView(R.id.settings_fragment_content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFragmentManager().beginTransaction()
                .add(R.id.settings_fragment_content, new BasePreferenceFragment())
                .commit();
    }

    public static class BasePreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_screen);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
