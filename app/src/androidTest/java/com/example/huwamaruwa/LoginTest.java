package com.example.huwamaruwa;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.huwamaruwa.buyerRentalRequestManage.EditSingleSellerRequest;
import com.example.huwamaruwa.buyerRentalRequestManage.SentRentalRequestBySeller;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class LoginTest {

    @Rule
    public IntentsTestRule<Login> intentsTestRule = new IntentsTestRule<>(Login.class);
    //    public ActivityScenarioRule<EditSingleSellerRequest> activityRule = new ActivityScenarioRule<>(EditSingleSellerRequest.class);
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testIntent(){
        onView(withId(R.id.signUp)).perform(click());
        intended(hasComponent(SignUp.class.getName()));
    }
}