package edu.dccc.conversion.config;

import edu.dccc.conversion.model.Unit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConversionMap {


    Map<String, Double> unitLookup = new HashMap<>();
    ArrayList<Unit> conversionsArrayList;

    public void initializeMaps(String conversionFilePath) throws IOException {
        ReadCSVWithScanner csvScanner = new ReadCSVWithScanner();
        conversionsArrayList = csvScanner.GetUnitDataSet(conversionFilePath);
        for (Unit unit : conversionsArrayList) {
            unitLookup.put(unit.getUnit(), unit.getValue());
        }
    }


    public String makeLongUnit(String selectionA, String selectionB) {
        return selectionA.toLowerCase() + "-" + selectionB.toLowerCase();
    }

    public double convertTemperature(double tempToConvert, String shortUnit) {
        double temp = 0.0;
        if (shortUnit.equals("celsius-fahrenheit"))
            temp = (tempToConvert * 9 / 5) + 32;
        else if (shortUnit.equals("fahrenheit-celsius"))
            temp = (tempToConvert - 32) * 5 / 9;
        return temp;
    }


    public double convert(String selectionA, String selectionB, double numToConvert) throws ConversionLookupException {
        double unitLookupValue = 0.0;
        String longUnit = "";
        try {
            longUnit = makeLongUnit(selectionA, selectionB);

            if (longUnit.equals("celsius-fahrenheit") || longUnit.equals("fahrenheit-celsius"))
                return convertTemperature(numToConvert, longUnit);

            unitLookupValue = unitLookup.get(longUnit);
        } catch (Exception e) {
            String message = "Lookup Error longUnit=" + longUnit;
            throw new ConversionLookupException(message);
        }
        return unitLookupValue * numToConvert;
    }

    public void testLongLookups(String type) throws ConversionLookupException {
        ArrayList<Unit> typeArrayList = new ArrayList<>();
        for (Unit u : conversionsArrayList) {
            if (u.getType().equals(type))
                typeArrayList.add(u);
        }
        for (Unit u : typeArrayList) {
            double unitLookupValue = unitLookup.get(u.getUnit());
            System.out.println(String.format("%s:: %.4f" , u.getUnit() , unitLookupValue * 5));
        }
    }

    public class ConversionLookupException extends Exception {
        public ConversionLookupException(String message) {
            super(message);
        }
    }


    public static void main(String[] args) {
        ConversionMap cm = new ConversionMap();
        try {
            cm.initializeMaps("resources/ConversionsLong.csv");
        } catch (IOException io) {
            System.out.println("Problem reading config files: " + io.getMessage());
        }
        try {
            //  Random test code for testing conversion configurations
            cm.testLongLookups("Length");
            cm.testLongLookups("Area");
            cm.testLongLookups("Volume");
            cm.testLongLookups("Mass");
            cm.testLongLookups("Temperature");

            System.out.println(String.format("%.4f", cm.convert("Foot", "Inch", 10)));
            System.out.println(String.format("%.4f", cm.convert("Meter", "Foot", 10)));

            System.out.println(String.format("%.4f", cm.convert("Hectare", "SquareMeter", 1)));
            System.out.println(String.format("%.4f", cm.convert("SquareMeter", "Hectare", 10)));

            System.out.println(String.format("Fahrenheit-Celsius %.4f", cm.convert("Fahrenheit", "Celsius", 10)));
            System.out.println(String.format("Celsius-Fahrenheit %.4f", cm.convert("Celsius", "Fahrenheit", 10)));


        } catch (ConversionLookupException e) {
            System.out.println("ConversationLookupException " + e.getMessage());
        }
    }
}
