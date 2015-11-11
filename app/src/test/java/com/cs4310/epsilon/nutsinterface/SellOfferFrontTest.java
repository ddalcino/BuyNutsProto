package com.cs4310.epsilon.nutsinterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dave on 11/10/15.
 */
public class SellOfferFrontTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testToStringCSV() throws Exception {

    }
    @Test
    public void testSellOfferFrontString() throws Exception {
        String[] testCSVs = {
                "1234567896543,098765456787654457685,1223346567,7.63,0.004," +
                        "99.94,walnut,1,Hi I'm a list of terms,,,,,,,,,,,," +
                        "and, also, some, conditions",
                ""
        };

        SellOfferFront offer = new SellOfferFront(testCSVs[0]);
        assertEquals(offer.getId(), "1234567896543");
        assertEquals(offer.getSellerId(), "098765456787654457685");
        assertTrue(offer.getOfferBirthday() == 1223346567l);
        assertTrue(offer.getPricePerUnit() == 7.63);
        assertTrue(offer.getMinWeight() == 0.004);
        assertTrue(offer.getMaxWeight() == 99.94);
        assertTrue(offer.getCommodity().equals("walnut"));
        assertTrue(offer.getExpired() == true);
        assertTrue(offer.getTerms().equals("Hi I'm a list of terms,,,,,,,,,,,," +
                                       "and, also, some, conditions"));
    }
}