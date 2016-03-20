package com.nobrain.samples.adapterrolesample.main.view;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.nobrain.samples.adapterrolesample.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.jayway.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = rule.getActivity();
        await().until(() -> activity.lvMain.getAdapter().getItemCount() > 0);
    }

    @Test
    public void testOnCreate() throws Exception {
        assertThat(activity.lvMain.getAdapter().getItemCount(), is(greaterThan(0)));
    }

    @Test
    public void testRefresh() throws Throwable {
        final boolean[] onChanged = {false};
        activity.lvMain.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                onChanged[0] = true;
            }

        });
        rule.runOnUiThread(activity::refresh);
        ViewMatchers.assertThat(onChanged[0], is(true));
    }

    @Test
    public void testShowFlickerItemActionDialog() throws Throwable {
        rule.runOnUiThread(() -> activity.showFlickerItemActionDialog(0, "test"));

        onView(withText("Copy"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testShowFlickerImageDialog() throws Throwable {
        rule.runOnUiThread(() -> activity.showFlickerImageDialog("test"));

        onView(withId(R.id.iv_photo_detail))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }
}