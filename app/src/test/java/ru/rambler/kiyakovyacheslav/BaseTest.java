package ru.rambler.kiyakovyacheslav;

import android.app.Application;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricGradleTestRunner.class)
@Config(application = Application.class,
        constants = BuildConfig.class,
        sdk = 21)
@Ignore
public abstract class BaseTest {
    protected <T> List<T> nonEmptyList() {
        return AdditionalMatchers.not(listOfNElements(0));
    }

    protected <T> List<T> listOfNElements(int n) {
        return Mockito.argThat(new IsListOfNElements(n));
    }

    public static class IsListOfNElements extends ArgumentMatcher<List> {
        private int n;

        public IsListOfNElements(int n) {
            this.n = n;
        }

        public boolean matches(Object list) {
            return ((List) list).size() == n;
        }
    }
}
