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
    public static HashMap<Type, Double> numLbs = new HashMap< /*Type, Double*/ >();
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
     * <p>
     *     Note: This code SHOULD just check if the input string EQUALS "lb" or
     *     "kg" etc., but right now it's checking if the input string STARTS WITH
     *     "lb" or "kg". This is bad programming and should eventually be changed.
     *     The reason I did this is because of the way I'm using this function
     *     when I read user input from the Units of Weight Spinner objects
     *     (R.id.spinnerWeightUnits_MO and R.id.spinnerWeightUnits_SF). These
     *     objects are filled with strings that don't just say things like
     *     "Gross Ton"; they say things like "gross ton (2240lb)", and I want to
     *     be able to pass that string into a function that recognizes what
     *     UnitsWt.Type enum corresponds to it. This is bad programming, and
     *     should be changed, but for the moment I don't know how to fix it.
     * </p>
     * @param in    Input string that starts with:<br/>
     *                  &emsp;&emsp;"lb" --> UnitWt.Type.LB<br/>
     *                  &emsp;&emsp;"kg" --> UnitWt.Type.KG<br/>
     *                  &emsp;&emsp;"gross ton" --> UnitWt.Type.GROSS_TON<br/>
     *                  &emsp;&emsp;"net ton" --> UnitWt.Type.NET_TON<br/>
     *                  &emsp;&emsp;"metric ton" --> UnitWt.Type.METRIC_TON<br/>
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
     * Returns a string version of the UnitWt.Type enum passed as a parameter
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
     * <p>&emsp;
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
