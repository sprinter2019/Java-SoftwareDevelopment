package edu.gatech.seclass.words6300;

import org.junit.Test;

import static org.junit.Assert.*;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class AdjustLetterDistActivityTest {

    @Rule
    public ActivityTestRule<AdjustLetterDistActivity> tActivityRule = new ActivityTestRule<>(
            AdjustLetterDistActivity.class);

    @Test
    public void ScreenshotError1() {
        onView(withId(R.id.editTextLetterPt)).perform(clearText(), replaceText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonChangeSettingLetterDist)).perform(click());
        onView(withId(R.id.editTextLetterPt)).check(matches(hasErrorText("Value must be greater than 0!")));
    }

    @Test
    public void ScreenshotError2() {
        onView(withId(R.id.editTextNumTiles)).perform(clearText(), replaceText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonChangeSettingLetterDist)).perform(click());
        onView(withId(R.id.editTextNumTiles)).check(matches(hasErrorText("Value must be greater than 0!")));
    }

    @Test
    public void ScreenshotError3() {
        onView(withId(R.id.editTextLetterPt)).perform(clearText(), replaceText("0"));
        onView(withId(R.id.editTextNumTiles)).perform(clearText(), replaceText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.buttonChangeSettingLetterDist)).perform(click());
        onView(withId(R.id.editTextLetterPt)).check(matches(hasErrorText("Value must be greater than 0!")));
        onView(withId(R.id.editTextNumTiles)).check(matches(hasErrorText("Value must be greater than 0!")));
    }
}