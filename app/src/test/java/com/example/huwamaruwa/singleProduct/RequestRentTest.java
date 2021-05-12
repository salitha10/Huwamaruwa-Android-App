package com.example.huwamaruwa.singleProduct;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestRentTest {
private RequestRent requestRent;
    @Before
    public void setUp() throws Exception {
        requestRent = new RequestRent();
    }

    @Test
    public void calcDeposit() {
        double deposit = requestRent.calcDeposit(1000.00,10.00);
        assertEquals((long)100,(long) deposit);
    }
}