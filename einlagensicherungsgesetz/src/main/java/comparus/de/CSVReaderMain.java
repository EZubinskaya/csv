package comparus.de;


import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekaterina on 8/30/17.
 */
public class CSVReaderMain {

    public static void main(String[] args) throws IOException {

        String file1 = "/Users/ekaterina/Desktop/einlagensicherungsgesetz/src/main/resources/Pseudodatei aufbereitet B11.csv";
        String file2 = "/Users/ekaterina/Desktop/einlagensicherungsgesetz/src/main/resources/example.csv";

        List<String> resultCSV =  createNewCSV(file1, file2);
        List<String[]> res = createArrayFromStringCSVFile(resultCSV);

        for (int i = 0; i < res.size(); i++) {
            if(res.get(i)[0].equals("B")) {
                List<String[]> calculateD = new ArrayList<String[]>();
                i++;
                while (res.size() >= i && !res.get(i)[0].startsWith("D")) {
                    calculateD.add(res.get(i));
                    i++;
                }
                String[] d3 = new String[calculateD.size()];
                String[] d4 = new String[calculateD.size()];
                String[] d6 = new String[calculateD.size()];


                for(int j = 0; j < calculateD.size(); j++) {
                    d3[j] = calculateD.get(j)[19];
                }
                res.get(i)[3]= String.valueOf(calculateD3(d3));
                res.get(i)[4]= String.valueOf(calculateD4(d4));
                res.get(i)[5]= String.valueOf(calculateD5D7(Integer.parseInt(res.get(i)[3]),Integer.parseInt(res.get(i)[4])));
                res.get(i)[6]= String.valueOf(calculateD6(d6));
                res.get(i)[7]= String.valueOf(calculateD5D7(Integer.parseInt(res.get(i)[5]),Integer.parseInt(res.get(i)[6])));
                res.get(i)[8]= "0";
                res.get(i)[9]= "0";


            }
        }

        System.out.println(res);
    }

    public static List<String> createNewCSV (String file1, String file2) throws IOException {
        List<String> dataReadCSVFileByString1 = readCSVFileByString(file1);
        List<String> dataReadCSVFileByString2 = readCSVFileByString(file2);
        List<String> resultStr= new ArrayList<String>();

        for(String strFirstFile : dataReadCSVFileByString1) {
            resultStr.add(strFirstFile);
            if(strFirstFile.startsWith("B")) {
                for(int i = 0; i < dataReadCSVFileByString2.size(); i++ ) {
                    if(strFirstFile.equals(dataReadCSVFileByString2.get(i))) {
                        i++;
                        while (dataReadCSVFileByString2.size() >= i && !dataReadCSVFileByString2.get(i).startsWith("D")) {
                            resultStr.add(dataReadCSVFileByString2.get(i));
                            i++;
                        }
                    }
                }
            }
        }
        return resultStr;
    }

    public static List<String[]> createArrayFromStringCSVFile(List<String> data) throws IOException {
        List<String[]> result = new ArrayList<String[]>();

        for(String field : data) {
            field.split("\\*");
            result.add(field.split("\\*"));
        }
        return result;
    }

//    public static List<String[]> readCSVFile(String file) throws IOException {
//        List<String[]> result = new ArrayList<String[]>();
//        CSVReader reader = new CSVReader(new FileReader(file), '\t', '\'');
//        List<String[]> myEntries = reader.readAll();
//        for(String[] field : myEntries) {
//            field[0].split("\\*");
//            result.add(field[0].split("\\*"));
//        }
//        return result;
//    }

    public static List<String> readCSVFileByString(String file) throws IOException {
        List<String> result = new ArrayList<String>();
        CSVReader reader = new CSVReader(new FileReader(file), '\t', '\'');
        List<String[]> myEntries = reader.readAll();
        for(String[] field : myEntries) {
            result.add(field[0]);
        }
        return result;
    }

    private static int calculateD3(String[] arg) {
        int sumD3 = 0;
        for (String elStr : arg) {
            int el = Integer.parseInt(elStr);
            if(el > 0) {
                sumD3 += el;
            }
        }
        return sumD3;
    }

    private static int calculateD5D7(int s, int s1) {
        if(s1 == 0) {
            return s;
        }
        return s%s1;
    }

    private static void calculateD6() {

    }

    private static int calculateD8(int ... arg) {
        int sumD8 = 0;
        for (int el : arg) {
            sumD8 += el;
        }
        return sumD8;
    }

    private static int calculateE(int ... arg) {
        int sumE = 0;
        for (int el : arg) {
            sumE += el;
        }
        return sumE;
    }
}
