package com.cs4310.epsilon.nutsinterface;

/**
 * Contains an enum for types of commodities, and provides helper functions for using them.
 *
 * <p>Usage:</p>
 *
 * Commodity.Type.WALNUT &emsp;&emsp;// gets you the value that corresponds to Walnut;
 *                                     datatype is Commodity.Type <br/>
 * Commodity.toType("Pecan") &emsp;&emsp;// gets you a Commodity.Type object equivalent
 *                                          to Commodity.Type.PECAN <br/>
 * Commodity.toString(Commodity.Type.ALMOND) &emsp;//returns the String "Almond"
 *
 *
 * Created by dave on 11/3/15.
 */
public class Commodity {

    /**
     * An enum that contains valid values for commodity types.
     */
    public enum Type {
        WALNUT, PECAN, ALMOND, CASHEW
    }

    /**
     * Returns the Commodity.Type version of the input string.
     * @param in    The input string; should be either 'walnut', 'pecan',
     *              'almond', 'cashew'. Not case sensitive. Returns null
     *              if it's not one of these.
     * @return      The Commodity.Type object equivalent to the input string,
     *              or null if it's not on the list.
     */
    public static Type toType(String in){
        in = in.toLowerCase();
        if(in.startsWith("walnut")) return Type.WALNUT;
        else if (in.startsWith("pecan")) return Type.PECAN;
        else if (in.startsWith("almond")) return Type.ALMOND;
        else if (in.startsWith("cashew")) return Type.CASHEW;
        else return null;
    }

    /**
     * Returns a string version of the Commodity.Type enum passed as a parameter.<br/>
     * <br/>
     * This method is actually unnecessary; if you just try to use Commodity.Type.WALNUT
     * as a string, you get the string "WALNUT" -- this is a property of Enums.
     * @param t The Commodity.Type object you would like to turn into a string
     * @return  The string version of the Commodity.Type object
     */
    public static String toString(Type t){
        if(t != null) {
            if (t.equals(Type.WALNUT)) return "Walnut";
            else if (t.equals(Type.PECAN)) return "Pecan";
            else if (t.equals(Type.ALMOND)) return "Almond";
            else if (t.equals(Type.CASHEW)) return "Cashew";
        }
        return "";
    }
}
