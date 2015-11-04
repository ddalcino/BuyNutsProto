package com.cs4310.epsilon.nutsinterface;

import java.util.HashMap;

/**
 * Types of units of weight.
 *
 * Usage:
 * UnitsWt.Type.METRIC_TON  // gets you the value that corresponds to metric tons;
 *                          // datatype is UnitsWt.Type
 * UnitsWt.toType("gross ton")  // gets you a UnitsWt.Type object equivalent
 *                              // to UnitsWt.Type.GROSS_TON
 * UnitsWt.toString(UnitsWt.Type.KG)    //returns the String "kg"
 *
 *
 * Created by dave on 11/2/15.
 */

public class UnitsWt {
    /**
     * An enum that lists all of the different weight units we're using.
     */
    public enum Type {
            LB, KG, GROSS_TON, NET_TON, METRIC_TON
    }

    /**
     * A hashtable that maps UnitsWt.Type objects to a Double that represents
     * the weight in pounds for one unit.
     */
    public static HashMap<Type, Double> numLbs = new HashMap<Type, Double>();
    static{
        numLbs.put(Type.LB, 1.0);
        numLbs.put(Type.KG, 2.204623);
        numLbs.put(Type.GROSS_TON, 2240.0);
        numLbs.put(Type.NET_TON, 2000.0);
        numLbs.put(Type.METRIC_TON, 2204.623);
    }

    /**
     * This is a bit sloppy right now, but the idea is that if you send it a
     * string, you get back a UnitWt.Type object equivalent to that string.
     * Not case sensitive.
     * @param in    Input string that starts with:
     *                  "lb" --> UnitWt.Type.LB
     *                  "kg" --> UnitWt.Type.KG
     *                  "gross ton" --> UnitWt.Type.GROSS_TON
     *                  "net ton" --> UnitWt.Type.NET_TON
     *                  "metric ton" --> UnitWt.Type.METRIC_TON
     * @return      UnitWt.Type equivalent to input string
     */
    public static Type toType(String in){

        if(in.toLowerCase().startsWith("lb")) return Type.LB;
        else if (in.toLowerCase().startsWith("kg")) return Type.KG;
        else if (in.toLowerCase().startsWith("gross ton")) return Type.GROSS_TON;
        else if (in.toLowerCase().startsWith("net ton")) return Type.NET_TON;
        else if (in.toLowerCase().startsWith("metric ton")) return Type.METRIC_TON;
        //else if (in.toLowerCase().equals("gross ton (2240lb)")) return Type.GROSS_TON;
        //else if (in.toLowerCase().equals("net ton (2000lb)")) return Type.NET_TON;
        //else if (in.toLowerCase().equals("metric tonne (1000kg)")) return Type.METRIC_TON;
        else return null;
    }

    /**
     * Returns a string version of the UnitWt.Type enum
     * @param t UnitWt.Type object
     * @return  String version of input UnitWt.Type object
     */
    public static String toString(Type t){
        if(t.equals(Type.LB)) return "lb";
        else if(t.equals(Type.KG)) return "kg";
        else if(t.equals(Type.GROSS_TON)) return "gross ton";
        else if(t.equals(Type.NET_TON)) return "net ton";
        else if(t.equals(Type.METRIC_TON)) return "metric ton";
        else return "";
    }

    /**
     * Returns a number you can use to convert one weight measurement to another.
     * <p>
     *     Usage:
     *     </p>
     * <p>
     *     double weightInGrossTons = weightInLbs * UnitsWt.unitConversion(UnitsWt.Type.LB, UnitsWt.Type.GROSS_TON);
     *     </p>
     *
     * @param in    the units you're starting with
     * @param out   the units you're converting to
     * @return      a number you can use to convert from 'in' weights to 'out' weights
     */
    public static double unitConversion(Type in, Type out){
        return numLbs.get(in)/numLbs.get(out);
    }
}
