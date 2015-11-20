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
    public void testToStringCode() throws Exception {
        SellOfferFront sof = new SellOfferFront(
                1234567896543l,
                "098765456787654457685",
                1223346567l,
                7.63,
                0.004,
                99.94,
                "Call my phone&num; and press &num;2&num;3&num;4&num;&num;&num;&num; and I'll &num; you",
                "walnut",
                true);
        String correctStringCode =
                "7.630000#" +
                "walnut#" +
                "99.940000#" +
                "0.004000#" +
                "Call my phone&num; and press &num;2&num;3&num;4&num;&num;&num;&num; and I'll &num; you";
        System.out.println(correctStringCode);
        System.out.println(sof.toInsertString());

        assertTrue(correctStringCode.equals(sof.toInsertString()));


    }
    @Test
    public void testSellOfferFrontString() throws Exception {
        String[] testStringCodes = {
                // Acceptable input
                "7.63#" +
                        "walnut#" +
                        "99.94#" +
                        "0.004#" +
                        "Call my phone&num; and press &num;2&num;3&num;4&num;&num;&num;&num; and I'll &num; you",

                "7.63.7#" +   // don't accept crazy garbage like this
                        "walnut#" +
                        "99.94#" +
                        "0.004#" +
                        "Call my phone&num; and press &num;2&num;3&num;4&num;&num;&num;&num; and I'll &num; you",

                "7.63#" +
                        "walnut#" +
                        "99.94#" +
                        "0.004+19#" +   // no math expressions allowed
                        "Call my phone&num; and press &num;2&num;3&num;4&num;&num;&num;&num; and I'll &num; you",

                "7.63#" +
                        "walnut#" +
                        "e^99.94#" +  // no math expressions allowed
                        "0.004#" +
                        "Call my phone&num; and press &num;2&num;3&num;4&num;&num;&num;&num; and I'll &num; you",

                "7.63#" +
                        "pistachio#" +   // unsupported nut type
                        "99.94#" +
                        "0.004#" +
                        "Call my phone&num; and press &num;2&num;3&num;4&num;&num;&num;&num; and I'll &num; you",
        };
        SellOfferFront offer;
        try {
            offer = new SellOfferFront(testStringCodes[0]);
            assertTrue(offer.getPricePerUnit() == 7.63);
            assertTrue(offer.getMinWeight() == 0.004);
            assertTrue(offer.getMaxWeight() == 99.94);
            assertTrue(offer.getCommodity().equals("walnut"));
            assertTrue(offer.getTerms().equals(
                    "Call my phone# and press #2#3#4#### and I'll # you"
            ));
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            // The first entry should not throw an exception; if it does, this test fails
            System.out.println(e.getMessage());
            fail();
        }
        try {
            offer = new SellOfferFront(testStringCodes[1]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("pricePerUnit is not a double"));
        }
        try {
            offer = new SellOfferFront(testStringCodes[2]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("minWeight is not a double"));
        }
        try {
            offer = new SellOfferFront(testStringCodes[3]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("maxWeight is not a double"));
        }
        try {
            offer = new SellOfferFront(testStringCodes[4]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("Invalid commodity type"));
        }
    }
}