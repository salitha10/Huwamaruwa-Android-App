package com.example.huwamaruwa.singleProduct;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {
private PremiumProduct premiumProduct;
    @Before
    public void setUp() throws Exception {
        premiumProduct = new PremiumProduct();
    }

    @Test
    public void calcAvgRating() {
        double ans = premiumProduct.calcAvgRating(90,20);
    assertEquals(0.9,ans ,0.01);
    }
}