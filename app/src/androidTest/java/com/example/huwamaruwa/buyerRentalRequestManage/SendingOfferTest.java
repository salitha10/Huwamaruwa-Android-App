package com.example.huwamaruwa.buyerRentalRequestManage;

import android.app.Activity;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class SendingOfferTest {

    @Rule
    public ActivityScenarioRule<SendingOffer> activityRule = new ActivityScenarioRule<>(SendingOffer.class);


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
}