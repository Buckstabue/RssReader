package ru.rambler.kiyakovyacheslav.ui.rssfeed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ru.rambler.kiyakovyacheslav.R;

public class RssFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_host_activity);
        startRssFeedFragment();
    }

    private void startRssFeedFragment() {
        final String TAG = "rss_feed";
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = RssFeedFragment.newInstance();
        } else {
            return;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, TAG)
                .commit();

    }
}
