package ru.rambler.kiyakovyacheslav.ui.rssfeed;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ru.rambler.kiyakovyacheslav.R;

public class RssFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        showRssFeedFragment();
    }

    private void showRssFeedFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(RssFeedFragment.TAG) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, RssFeedFragment.newInstance(), RssFeedFragment.TAG)
                    .commit();

        }
    }
}
