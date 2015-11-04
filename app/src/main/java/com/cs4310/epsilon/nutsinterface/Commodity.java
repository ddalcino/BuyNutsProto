package com.cs4310.epsilon.nutsinterface;

/**
 * Types of commodities.
 *
 * Usage:
 * Commodity.Type.WALNUT    // gets you the value that corresponds to Walnut;
 *                          // datatype is Commodity.Type
 * Commodity.toType("Pecan")    // gets you a Commodity.Type object equivalent
 *                              // to Commodity.Type.PECAN
 * Commodity.toString(Commodity.Type.ALMOND)    //returns the String "Almond"
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
        if(in.toLowerCase().equals("walnut")) return Type.WALNUT;
        else if (in.toLowerCase().equals("pecan")) return Type.PECAN;
        else if (in.toLowerCase().equals("almond")) return Type.ALMOND;
        else if (in.toLowerCase().equals("cashew")) return Type.CASHEW;
        else return null;
    }

    /**
     * Returns a string version of the commodity type.
     *
     * This method is actually unnecessary; if you just try to use Commodity.Type.WALNUT
     * as a string, you get the string "WALNUT" -- this is a property of Enums.
     * @param t the commodity type you would like to turn into a string
     * @return  the string version of the commodity type
     */
    public static String toString(Type t){
        if(t.equals(Type.WALNUT)) return "Walnut";
        else if(t.equals(Type.PECAN)) return "Pecan";
        else if(t.equals(Type.ALMOND)) return "Almond";
        else if(t.equals(Type.CASHEW)) return "Cashew";
        else return "";
    }
}
