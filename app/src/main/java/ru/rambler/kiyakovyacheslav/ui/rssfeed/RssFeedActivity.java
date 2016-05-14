package ru.rambler.kiyakovyacheslav.ui.rssfeed;

import android.os.Bundle;
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
        RssFeedFragment fragment = RssFeedFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, null)
                .commit();
    }
}
