package com.example.githubuser.ui.main

import android.content.pm.ActivityInfo
import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.githubuser.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    private val userName = "firman-ali"

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        activityScenarioRule.scenario
    }

    @Test
    fun getUserData() {

        onView(withId(R.id.sv_search)).perform(click())
        onView(withId(R.id.sv_search))
            .perform(typeText(userName), pressKey(KeyEvent.KEYCODE_ENTER), closeSoftKeyboard())

        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        Thread.sleep(1000)

        onView(withId(R.id.sv_search)).perform(closeSoftKeyboard())

        onView(withId(R.id.menu2)).perform(click())

        activityScenarioRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        onView(withId(R.id.menu1)).perform(click())

        onView(withId(R.id.switch_theme)).perform(click())

        onView(withId(R.id.bt_back)).perform(click())
    }
}