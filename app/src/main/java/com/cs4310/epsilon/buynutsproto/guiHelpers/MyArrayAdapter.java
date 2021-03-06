package com.cs4310.epsilon.buynutsproto.guiHelpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;
import com.cs4310.epsilon.nutsinterface.UnitsWt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This is an ArrayAdapter class for SellOfferFront objects, used to display
 * an ArrayList of SellOfferFront objects in a ListView object. To do so, it
 * uses the layout defined in list_item_news.xml
 *
 * Created by dave on 11/6/15.
 */
public class MyArrayAdapter extends ArrayAdapter<SellOfferFront> {

    /**
     * A reference to the ArrayList of objects you want to display in the ListView
     */
    private ArrayList<SellOfferFront> aList;

    private String mUnits;


    /**
     * Constructor
     * @param context           The activity that needs the ArrayAdapter
     * @param layoutResourceId  The layout xml file used to produce the output
     * @param objects           The ArrayList of SellOffer objects to display
     */
    public MyArrayAdapter(Context context, int layoutResourceId,
                          ArrayList<SellOfferFront> objects, String units){
        super(context, layoutResourceId, objects);
        mUnits = units;
        if(objects != null) {
            this.aList = objects;
            // Sort the list by lowest price:
            Collections.sort(aList, new Comparator<SellOfferFront>() {
                @Override
                public int compare(SellOfferFront so1, SellOfferFront so2) {

                    return so1.getPricePerUnit().compareTo(
                            so2.getPricePerUnit());
                }
            });
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View view = convertView;

        //inflate the xml file into objects
        if (view == null) {
            LayoutInflater inflater =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_news, null);
        }

        //get the SellOfferFront specified by the parameter "position" from the list
        SellOfferFront i = aList.get(position);

        if (i != null) {
            //Get textview references
            TextView tvCommod = (TextView) view.findViewById(R.id.tvShowCommodType);
            TextView tvPPU = (TextView) view.findViewById(R.id.tvShowPPU);
            TextView tvMaxWeight = (TextView) view.findViewById(R.id.tvShowMaxWeight);
            TextView tvMinWeight = (TextView) view.findViewById(R.id.tvShowMinWeight);

            TextView tvUnits1 = (TextView) view.findViewById(R.id.tvLblUnits);
            tvUnits1.setText("/"+mUnits);
            TextView tvUnits2 = (TextView) view.findViewById(R.id.tvShowUnitsWt2);
            tvUnits2.setText(" "+mUnits);
//            TextView tvWeightUnits = (TextView) view.findViewById(R.id.tvShowUnitsWt);

            // Default units conversion; we'll figure that out later
            double unitConversion = UnitsWt.unitConversion(UnitsWt.Type.LB, UnitsWt.toType(mUnits));

            // put column values in the textviews of the inflated file
            if (tvCommod != null){
                tvCommod.setText(i.getCommodityPretty());
            }
            if (tvPPU != null){
                tvPPU.setText(String.format("%.02f", i.getPricePerUnit()/unitConversion));
            }
            if (tvMaxWeight != null){
                tvMaxWeight.setText(String.format("%.4f", i.getMaxWeight()*unitConversion));
            }
            if (tvMinWeight != null){
                tvMinWeight.setText(String.format("%.4f", i.getMinWeight()*unitConversion));
            }
//            if (tvWeightUnits != null){
//                tvWeightUnits.setText(UnitsWt.toString(i.getUnits()));
//            }
        }

        //return the inflated view
        return view;
    }


}
