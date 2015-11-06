package com.nutsinterface.Mike.myapplication.backend;

/**
 * Created by Mike on 11/3/2015.
 */

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {

   /* @Inject
    public static void setObjectifyFactory(ObjectifyFactory factory) {
        ObjectifyService.setFactory(factory);
    }
    */
    private OfyService() { }
    static {
       factory().register(SellOffer.class);
        //ObjectifyService.register(SellOffer.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}