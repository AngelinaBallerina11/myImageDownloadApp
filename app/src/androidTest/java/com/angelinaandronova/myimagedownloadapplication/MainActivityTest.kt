package com.angelinaandronova.myimagedownloadapplication

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.angelinaandronova.myimagedownloadapplication.EspressoTestMatchers.Companion.hasNoTextInputEditTextError
import com.angelinaandronova.myimagedownloadapplication.EspressoTestMatchers.Companion.hasTextInputEditTextError
import com.angelinaandronova.myimagedownloadapplication.EspressoTestMatchers.Companion.withDrawable
import com.angelinaandronova.myimagedownloadapplication.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun emptyUsernameFieldShowsError() {
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.username))
            .check(matches(hasTextInputEditTextError(activityTestRule.activity.getString(R.string.please_fill_in_username))))
    }

    @Test
    fun emptyPasswordFieldShowsError() {
        onView(withId(R.id.username)).perform(typeText("hello"))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.password))
            .check(matches(hasTextInputEditTextError(activityTestRule.activity.getString(R.string.please_fill_in_password))))
    }

    @Test
    fun correctCredentialsShowNoError() {
        onView(withId(R.id.username)).perform(typeText("andronova"))
        onView(withId(R.id.password)).perform(typeText("angelina"))
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.username)).check(matches(hasNoTextInputEditTextError()))
        onView(withId(R.id.password)).check(matches(hasNoTextInputEditTextError()))
    }

    @Test
    fun initiallyThereIsAndroidImage() {
        onView(withId(R.id.imageView)).check(matches(withDrawable(R.drawable.ic_launcher_foreground)))
    }
}

