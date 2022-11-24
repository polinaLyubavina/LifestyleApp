package com.example.lifestyleapp

import com.lifestyleapp.R

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule;
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed

import androidx.test.espresso.Espresso

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.lifestyleapp.MasterDetail
import org.hamcrest.Matchers.not
import org.hamcrest.core.StringContains.containsString


@RunWith(AndroidJUnit4::class)

/**
 * Tests the UI in right order
 */
class CorrectOrderTest {
    @Rule @JvmField
    public var activityActivityTestRule: ActivityTestRule<MasterDetail> =
        ActivityTestRule<MasterDetail>(MasterDetail::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.lifestyleapp", appContext.packageName)
    }

    @Test
    fun updateProfile() {
        onView(withId(R.id.my_prof_btn_frag))
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())

        onView(withHint("Name:"))
            .perform(scrollTo())
            .perform(replaceText("TestName"))
            .check(matches(withText("TestName")))

        onView(withHint("Age:"))
            .perform(scrollTo())
            .perform(replaceText("20"))
            .check(matches(withText("20")))

        onView(withHint("City:"))
            .perform(scrollTo())
            .perform(replaceText("Salt Lake City"))
            .check(matches(withText("Salt Lake City")))

        onView(withHint("Country:"))
            .perform(scrollTo())
            .perform(replaceText("USA"))
            .check(matches(withText("USA")))

        onView(withId(R.id.profileMaleFrag))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(click())
            .check(matches(isEnabled()))

        onView(withId(R.id.saveProfileFrag))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.lifeBtnMyProfFrag))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())
    }

    @Test
    fun checkWeightMgt() {
        //Enter Weight Management
        onView(withId(R.id.weight_man_btn_frag))
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist()) //check that the next View is displayed (onView with Id

        //Sedentary Btn
        onView(withId(R.id.calculatorSedentaryFrag))
            .check(matches(isDisplayed()))
            .perform(click())
            .check(matches(isEnabled()))

        //Back Btn
        onView(withId(R.id.lifestyle_btn_weightman_frag))
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())
    }

    @Test
    fun checkNearbyHikes() {
        //Enter Hikes
        onView(withId(R.id.hike_btn_frag))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    @Test
    fun checkLocalWeather() {
        //Enter Weather
        onView(withId(R.id.weather_btn_frag))
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())

        //Press "Go" Btn
        onView(withText("GO"))
            .perform(scrollTo())
            .perform(click())
            .check(matches(isDisplayed()))

        //Press "Back" Btn
        onView(withText("BACK"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())
    }
}


/**
 * Tests the UI in incorrect order of use
 */
class IncorrectOrderTest {
    @Rule @JvmField
    public var activityActivityTestRule: ActivityTestRule<MasterDetail> =
        ActivityTestRule<MasterDetail>(MasterDetail::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.lifestyleapp", appContext.packageName)
    }

    @Test
    fun checkLocalWeather() {
        //Enter Weather
        onView(withId(R.id.weather_btn_frag))
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())

        //Press "Go" Btn
        onView(withText("GO"))
            .perform(click())
            .check(matches(isDisplayed()))

        //Press "Back" Btn
        onView(withText("BACK"))
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())
    }

    @Test
    fun checkProfileNoInputs() {
        //Enter Profile
        onView(withId(R.id.my_prof_btn_frag))
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())

        //Press "Save" Btn
        onView(withText("Save"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(click())
//            .check(matches(withText("Please fill out all fields!")))

        //Press "Back" Btn
        onView(withText("Back"))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())
    }

    @Test
    fun checkProfilePartialInputs() {
        //Enter Profile
        onView(withId(R.id.my_prof_btn_frag))
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())

        //Enter Name
        onView(withId(R.id.profileNameFrag))
            .perform(scrollTo())
            .perform(replaceText("NameTest"))
            .check(matches(withText("NameTest")))

        //Enter Age
        onView(withId(R.id.profileAgeFrag))
            .perform(scrollTo())
            .perform(replaceText("20"))
            .check(matches(withText("20")))

        //Enter City
        onView(withId(R.id.profileCityFrag))
            .perform(scrollTo())
            .perform(replaceText("Salt Lake City"))
            .check(matches(withText("Salt Lake City")))

        //Enter Country
        onView(withId(R.id.profileCountryFrag))
            .perform(scrollTo())
            .perform(replaceText("USA"))
            .check(matches(withText("USA")))

        //Press "Save" Btn
        onView(withId(R.id.saveProfileFrag))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(click())
//            .check(matches(withText("Please select a gender!")))
//            .check(matches(withText(containsString("Please select a gender!"))))

        //Press "Back" Btn
        onView(withId(R.id.lifeBtnMyProfFrag))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .perform(click())
            .check(doesNotExist())
    }
}
