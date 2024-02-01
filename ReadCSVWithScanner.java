package edu.dccc.conversion.config;

import edu.dccc.conversion.model.Unit;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadCSVWithScanner {

    public ArrayList<Unit> GetUnitDataSet(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line = null;
        Scanner scanner = null;
        int index = 0;
        ArrayList<Unit> list = new ArrayList();
        int rowCount =  0;
        while ((line = reader.readLine()) != null) {
            if (rowCount == 0)
            {
                rowCount ++;
                continue;
            }

            Unit unit = new Unit();
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    unit.setType(data);
                else if (index == 1)
                    unit.setUnit(data);
                else if (index == 2)
                    unit.setValue(Double.valueOf(data));
                index++;
            }
            rowCount++;
            index = 0;
            list.add(unit);
        }


        reader.close();
        return list;
    }


    public static void main(String[] args) throws IOException {
        
        ReadCSVWithScanner readCSVWithScanner = new ReadCSVWithScanner();
        ArrayList<Unit> unitList = readCSVWithScanner.GetUnitDataSet("resources/ConversionsLong.csv");
        System.out.println(unitList);

    }

}