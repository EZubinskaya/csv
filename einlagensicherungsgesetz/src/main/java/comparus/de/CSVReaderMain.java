//package comparus.de;
//
//
//import com.opencsv.CSVReader;
//
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by ekaterina on 8/30/17.
// */
//public class CSVReaderMain {
//
//    public static void main(String[] args) throws IOException {
//
//        String file1 = "/Users/ekaterina/Desktop/einlagensicherungsgesetz/src/main/resources/Pseudodatei aufbereitet B11.csv";
//        String file2 = "/Users/ekaterina/Desktop/einlagensicherungsgesetz/src/main/resources/example.csv";
//
//        List<String> resultCSV =  createNewCSV(file1, file2);
//        List<String[]> res = createArrayFromStringCSVFile(resultCSV);
//
//
//        for (int i = 0; i < res.size(); i++) {
//            if(res.get(i)[0].equals("B")) {
//                List<String[]> calculateD = new ArrayList<String[]>();
//                i++;
//                while (res.size() >= i && !res.get(i)[0].startsWith("D")) {
//                    calculateD.add(res.get(i));
//                    i++;
//                }
//                String[] d3 = new String[calculateD.size()];
//                for(int j = 0; j < calculateD.size(); j++) {
//                    d3[j] = calculateD.get(j)[19];
//                }
//                res.get(i)[3]= String.valueOf(calculateD3(d3));
//
//
//                List<String[]> calculateC19C20 = new ArrayList<String[]>();
//                i++;
//                while (res.size() >= i && !res.get(i)[0].startsWith("C")) {
//                    calculateC19C20.add(res.get(i));
//                    i++;
//                }
//                String[] C19 = new String[calculateC19C20.size()];
//                String[] C20 = new String[calculateC19C20.size()];
//                Double[] C5 = new Double[calculateC19C20.size()];
//                for(int j = 0; j < calculateC19C20.size(); j++) {
//                    C19[j] = calculateC19C20.get(j)[19];
//                    C20[j] = calculateC19C20.get(j)[20];
//                    C5[j] = Double.parseDouble(calculateC19C20.get(j)[20]);
//                }
//                String B14 = res.get(i)[14];
//                String D3 = res.get(i)[3];
//
//                res.get(i)[4]= String.valueOf(calculateD4(B14, C19, D3, C20));
//
//                res.get(i)[5]= String.valueOf(calculateD5D7(Double.parseDouble(res.get(i)[3]),Double.parseDouble(res.get(i)[4])));
//
//                double A4 = 0;
//                if(res.get(0)[0].startsWith("A")) {
//                    A4 = Double.parseDouble(res.get(0)[4]);
//                }
//
//                res.get(i)[6]= String.valueOf(calculateD6(A4, C5));
//                res.get(i)[7]= String.valueOf(calculateD5D7(Double.parseDouble(res.get(i)[5]),Double.parseDouble(res.get(i)[6])));
//                res.get(i)[8]= String.valueOf(calculateD8());
//                res.get(i)[9]= String.valueOf(calculateD9());
//
//                res.get(i)[10]= String.valueOf(calculateD10(String B14, String[] C19));
//                res.get(i)[11]= String.valueOf(calculateD11());
//                res.get(i)[12]= String.valueOf(calculateD12());
//
//                res.get(i)[13]= String.valueOf(calculateD13());
//                res.get(i)[14]= String.valueOf(calculateD14(d3));
//                res.get(i)[15]= String.valueOf(calculateD15());
//
//            }
//        }
//
//        System.out.println(res);
//    }
//
//    public static List<String> createNewCSV (String file1, String file2) throws IOException {
//        List<String> dataReadCSVFileByString1 = readCSVFileByString(file1);
//        List<String> dataReadCSVFileByString2 = readCSVFileByString(file2);
//        List<String> resultStr= new ArrayList<String>();
//
//        for(String strFirstFile : dataReadCSVFileByString1) {
//            resultStr.add(strFirstFile);
//            if(strFirstFile.startsWith("B")) {
//                for(int i = 0; i < dataReadCSVFileByString2.size(); i++ ) {
//                    if(strFirstFile.equals(dataReadCSVFileByString2.get(i))) {
//                        i++;
//                        while (dataReadCSVFileByString2.size() >= i && !dataReadCSVFileByString2.get(i).startsWith("D") && dataReadCSVFileByString2.get(i).startsWith("C") ) {
//                            resultStr.add(dataReadCSVFileByString2.get(i));
//                            i++;
//                        }
//                    }
//                }
//            }
//        }
//        return resultStr;
//    }
//
//    public static List<String[]> createArrayFromStringCSVFile(List<String> data) throws IOException {
//        List<String[]> result = new ArrayList<String[]>();
//
//        for(String field : data) {
//            field.split("\\*");
//            result.add(field.split("\\*"));
//        }
//        return result;
//    }
//
//    public static List<String> readCSVFileByString(String file) throws IOException {
//        List<String> result = new ArrayList<String>();
//        CSVReader reader = new CSVReader(new FileReader(file), '\t', '\'');
//        List<String[]> myEntries = reader.readAll();
//        for(String[] field : myEntries) {
//            result.add(field[0]);
//        }
//        return result;
//    }
//
//    private static double calculateD3(String[] arg) {
//        double sumD3 = 0;
//        for (String elStr : arg) {
//            double el = Double.parseDouble(elStr.replace(",", "."));
//            if(el > 0) {
//                sumD3 += el;
//            }
//        }
//        return sumD3;
//    }
//
//    private static boolean C20AllStartWithY(String[] C20) {
//        for(String elStr : C20) {
//            if(!elStr.startsWith("Y")) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private static boolean C20AllContainsY(String[] C20) {
//        for(String elStr : C20) {
//            if(!elStr.contains("Y")) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private static double calculateD4(String B14, String[] C19, String D3, String[] C20) {
//        if(B14.contains("Y")) {
//            double sumD4 = 0;
//            for (String elStr : C19) {
//                double el = Double.parseDouble(elStr);
//                if(el > 0) {
//                    sumD4 += el;
//                }
//            }
//            return sumD4;
//        }
//
//        if(Double.parseDouble(D3) < 2000 && C20AllStartWithY(C20)) {
//            Double.parseDouble(D3);
//        }
//
//        if(C20AllContainsY(C20)) {
//            return calculateositiveC19ForAllContainsC20Y(C19,C20);
//        }
//
//        return 0;
//    }
//
//    private static double calculateositiveC19ForAllContainsC20Y(String[] C19, String[] C20) {
//        double sum = 0;
//        for(int i = 0; i < C20.length; i++ ) {
//            if(C20[i].contains("Y") && Double.parseDouble(C19[i]) > 0) {
//                sum += Double.parseDouble(C19[i]);
//            }
//        }
//        return sum;
//    }
//
//    private static double calculateD5D7(double s, double s1) {
//        if(s1 == 0) {
//            return s;
//        }
//        return s%s1;
//    }
//
//    private static double calculateD6(double A4, Double[] C5) {
//        double maxA4C5 = 0;
//        for (int i = 0; i< C5.length; i++) {
//            double cur = A4 * C5[i];
//            if (cur > maxA4C5) {
//                maxA4C5 = cur;
//            }
//        }
//        return maxA4C5;
//    }
//
//    private static int calculateD8() {
//        return 0;
//    }
//
//    private static int calculateD9() {
//        return 0;
//    }
//
//    private static boolean specificPositionSymbol (int from, int to, String[] B14BySymbol) {
//        for(int i = from; i < to; i++) {
//            if(B14BySymbol[i].equals("Y")) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private static int specificPositionSymbolForRevalantCRecords (int from, int to, String[] C19BySymbol) {
//        int sum = 0;
//        for(int i = from; i < to; i++) {
//            if(C19BySymbol[i].equals("Y")) {
//                sum +=
//            }
//        }
//        return false;
//    }
//
//    private static double calculateD10(String B14, String[] C19) {
//        if(specificPositionSymbol(31,50, B14.split(""))) {
//            double sumD10 = 0;
//            for (String elStr : C19) {
//                double el = Double.parseDouble(elStr);
//                if(el > 0) {
//                    sumD10 += el;
//                }
//            }
//            return sumD10;
//        }
//
//        if(specificPositionSymbol(21,50, C19)) {
//            double sumD10 = 0;
//            for (String elStr : C19) {
//                double el = Double.parseDouble(elStr);
//                if(el > 0) {
//                    sumD10 += el;
//                }
//            }
//            return sumD10;
//        }
//
//        return 0;
//    }
//
//    private static int calculateD11() {
//        return 0;
//    }
//
//    private static int calculateD12() {
//        return 0;
//    }
//
//    private static int calculateD13() {
//        return 0;
//    }
//
//    private static int calculateD14(String[] arg) {
//        int sumD14 = 0;
//        for (String elStr : arg) {
//            int el = Integer.parseInt(elStr);
//            if(el < 0) {
//                sumD14 += el;
//            }
//        }
//        return sumD14;
//    }
//
//    private static int calculateD15() {
//        return 0;
//    }
//
//    private static int calculateE(int ... arg) {
//        int sumE = 0;
//        for (int el : arg) {
//            sumE += el;
//        }
//        return sumE;
//    }
//}
