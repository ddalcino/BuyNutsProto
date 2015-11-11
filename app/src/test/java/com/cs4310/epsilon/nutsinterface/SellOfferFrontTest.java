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
        String[][] testCSVs = {
                new String[]{
                        "1234567896543",
                        "098765456787654457685",
                        "1223346567",
                        "7.63",
                        "0.004",
                        "99.94",
                        "walnut",
                        "true",
                        "Hi I'm a list of terms,,,,,,,,,,,,and, also, some, conditions"
                },
                new String[]{
                        "1234567896543.1",  // should be a long, not double
                        "098765456787654457685",
                        "1223346567",
                        "7.63",
                        "0.004",
                        "99.94",
                        "walnut",
                        "false",
                        "Hi I'm a list of terms,,,,,,,,,,,,and, also, some, conditions"
                },
                new String[]{
                        "1234567896543",
                        "098765456787654457685x",   //shouldn't contain non-numeric characters
                        "1223346567",
                        "7.63",
                        "0.004",
                        "99.94",
                        "walnut",
                        "false",
                        "Hi I'm a list of terms,,,,,,,,,,,,and, also, some, conditions"
                },
                new String[]{
                        "1234567896543",
                        "098765456787654457685",
                        "1223346567.0",     //shouldn't be a double
                        "7.63",
                        "0.004",
                        "99.94",
                        "walnut",
                        "true",
                        "Hi I'm a list of terms,,,,,,,,,,,,and, also, some, conditions"
                },
                new String[]{
                        "1234567896543",
                        "098765456787654457685",
                        "1223346567",
                        "7.63.7",   // don't accept crazy garbage like this
                        "0.004",
                        "99.94",
                        "walnut",
                        "true",
                        "Hi I'm a list of terms,,,,,,,,,,,,and, also, some, conditions"
                },
                new String[]{
                        "1234567896543",
                        "098765456787654457685",
                        "1223346567",
                        "7.63",
                        "0.004+19",   // no math expressions allowed
                        "99.94",
                        "walnut",
                        "true",
                        "Hi I'm a list of terms,,,,,,,,,,,,and, also, some, conditions"
                },
                new String[]{
                        "1234567896543",
                        "098765456787654457685",
                        "1223346567",
                        "7.63",
                        "0.004",
                        "e^99.94",   // no math expressions allowed
                        "walnut",
                        "true",
                        "Hi I'm a list of terms,,,,,,,,,,,,and, also, some, conditions"
                },
                new String[]{
                        "1234567896543",
                        "098765456787654457685",
                        "1223346567",
                        "7.63",
                        "0.004",
                        "99.94",
                        "pistachio",    // unsupported nut type
                        "true",
                        "Hi I'm a list of terms,,,,,,,,,,,,and, also, some, conditions"
                },
                new String[]{
                        "1234567896543",
                        "098765456787654457685",
                        "1223346567",
                        "7.63",
                        "0.004",
                        "99.94",
                        "almond",
                        "fuhgeddaboudit",   // neither true not false
                        "Hi I'm a list of terms,,,,,,,,,,,,and, also, some, conditions"
                }


        };
        SellOfferFront offer;
        try {
            offer = new SellOfferFront(testCSVs[0]);
            assertTrue(offer.getId() == 1234567896543l);
            assertEquals(offer.getSellerId(), "098765456787654457685");
            assertTrue(offer.getOfferBirthday() == 1223346567l);
            assertTrue(offer.getPricePerUnit() == 7.63);
            assertTrue(offer.getMinWeight() == 0.004);
            assertTrue(offer.getMaxWeight() == 99.94);
            assertTrue(offer.getCommodity().equals("walnut"));
            assertTrue(offer.getExpired());
            assertTrue(offer.getTerms().equals(
                    "Hi I'm a list of terms,,,,,,,,,,,,and, also, some, conditions"
            ));
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            // The first entry should not throw an exception; if it does, this test fails
            fail();
        }
        try {
            offer = new SellOfferFront(testCSVs[1]);
            // this test should throw an exception; otherwise it fails
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("User ID is not a long"));
        }
        try {
            offer = new SellOfferFront(testCSVs[2]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("SellerId is not a number"));
        }
        try {
            offer = new SellOfferFront(testCSVs[3]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("OfferBirthday is not a long"));
        }
        try {
            offer = new SellOfferFront(testCSVs[4]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("pricePerUnit is not a double"));
        }
        try {
            offer = new SellOfferFront(testCSVs[5]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("minWeight is not a double"));
        }
        try {
            offer = new SellOfferFront(testCSVs[6]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("maxWeight is not a double"));
        }
        try {
            offer = new SellOfferFront(testCSVs[7]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("Invalid commodity type"));
        }
        try {
            offer = new SellOfferFront(testCSVs[8]);
            fail();
        } catch (SellOfferFront.SellOfferStringArrayException e) {
            assertTrue(e.getMessage().equals("Expired field improperly set"));
        }
    }
}