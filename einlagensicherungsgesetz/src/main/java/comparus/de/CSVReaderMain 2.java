package comparus.de;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ekaterina on 8/31/17.
 */
public class CSVReaderMain1 {

    private static String[] A = null;
    public static void main(String[] args) throws IOException {

        String file1 = "/Users/ekaterina/Desktop/einlagensicherungsgesetz/src/main/resources/Pseudodatei aufbereitet B11.csv";
        String file2 = "/Users/ekaterina/Desktop/einlagensicherungsgesetz/src/main/resources/example.csv";
        Map<String,CVSClient> readFile1 = readCSVFileByString(file1);
        Map<String,CVSClient> readFile2 = readCSVFileByString(file2);

        Map<String,CVSClient> fullFile = generateMapOfData(readFile1, readFile2);
        reCalculateD(fullFile, A);

        reCalculateE(fullFile);
        System.out.println(fullFile);
    }

    private static Map<String,CVSClient> readCSVFileByString(String file) throws IOException {

        Map<String,CVSClient> clients = new HashMap<String, CVSClient>();
        CSVReader reader = new CSVReader(new FileReader(file), '\n', '\'');
        List<String[]> myEntries = reader.readAll();
        if(A == null) {
            A = myEntries.get(0);
        }
        int j = 1;
        for (int i = j ;i<myEntries.size(); i++) {
            String currentEllement = myEntries.get(i)[0];
            if(currentEllement.startsWith("B")) {
                String key = currentEllement;
                List<String[]> CValues = new ArrayList<String[]>();
                String[] DValue = null;
                j = i+1;
                while (myEntries.get(j)[0].startsWith("C")) {
                    CValues.add(myEntries.get(j)[0].split("\\*"));
                    j++;
                }
                if(myEntries.get(j)[0].startsWith("D")) {
                    DValue = myEntries.get(j)[0].split("\\*");
                    j++;
                }
                clients.put(key, new CVSClient(CValues, DValue));
            }
        }
        return clients;
    }

    private static Map<String,CVSClient> generateMapOfData(Map<String, CVSClient> readFile1, Map<String, CVSClient> readFile2) {
        Map<String,CVSClient> resultMap = new HashMap<String, CVSClient>();
        for (Map.Entry<String, CVSClient> entry : readFile1.entrySet()){
            if (readFile2.get(entry.getKey()) != null) {
                List<String[]> clients = new ArrayList<String[]>();
                clients.addAll(entry.getValue().getClientsB());
                clients.addAll(readFile2.get(entry.getKey()).getClientsB());
                resultMap.put(entry.getKey(), new CVSClient(clients, entry.getValue().getD()));
                readFile2.remove(entry.getKey());
            } else {
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        resultMap.putAll(readFile2);
        return resultMap;
    }

    private static void reCalculateE(Map<String, CVSClient> fullFile) {
    }

    private static void reCalculateD(Map<String, CVSClient> fullFile, String[] A) {
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            String[] clientB = entry.getKey().split("]\\*");
            CVSClient cvsClient = entry.getValue();
            List<String[]> CList = cvsClient.getClientsB();
            String[] D = cvsClient.getD();
            D[3] = calculateD3(CList);
            D[4] = calculateD4(clientB, CList, Double.valueOf(D[3]));
            D[5] = calculateD5D7(Double.parseDouble(D[3]), Double.parseDouble(D[4]));
            D[6] = calculateD6(Double.valueOf(A[4]), CList);
            D[7] = calculateD5D7(Double.parseDouble(D[5]), Double.parseDouble(D[6]));
            D[8] = "0";
            D[9] = "0";
            D[10] = String.valueOf(calculateD1011(31, 50, 11, 50, CList, clientB));
            D[11] = String.valueOf(calculateD1011(31, 50, 21, 50, CList, clientB));
            D[12] = calculateD12(D, CList, A[6]);
            D[13] = "0";
            D[14] = calculateD14(CList);
            D[15] = "0";

        }
    }

    private static String calculateD3(List<String[]> CList) {
        double sumD3 = 0;
        for (String[] elStr : CList) {
            double el = Double.parseDouble(elStr[19].replace(",", "."));
            if(el > 0) {
                sumD3 += el;
            }
        }
        return String.valueOf(sumD3);
    }

    private static String calculateD4(String[] clientB, List<String[]> CList, double D3) {
        if(clientB[14].contains("Y")) {
            double sumD4 = 0;
            for (String[] elStr : CList) {
                double el = Double.parseDouble(elStr[19]);
                if(el > 0) {
                    sumD4 += el;
                }
            }
            return String.valueOf(sumD4);
        }

        if(D3 < 2000 && C20AllStartWithY(CList)) {
            String.valueOf(D3);
        }

        if(C20AllContainsY(CList)) {
            return calculateositiveC19ForAllContainsC20Y(CList);
        }

        return "0";
    }

    private static boolean C20AllStartWithY(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(!elStr[0].startsWith("Y")) {
                return false;
            }
        }
        return true;
    }

    private static boolean C20AllContainsY(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(!elStr.toString().contains("Y")) {
                return true;
            }
        }
        return false;
    }

    private static String calculateositiveC19ForAllContainsC20Y(List<String[]> CList) {
        double sum = 0;
        for(int i = 0; i < CList.size(); i++ ) {
            String[] curr = CList.get(i);
            if(curr[20].contains("Y") && Double.parseDouble(curr[19]) > 0) {
                sum += Double.parseDouble(curr[19]);
            }
        }
        return String.valueOf(sum);
    }

    private static String calculateD5D7(double s, double s1) {
        return String.valueOf(s-s1);
    }

    private static String calculateD6(double A4, List<String[]> CList) {
        double maxA4C5 = 0;
        for (int i = 0; i< CList.size(); i++) {
            double cur = A4 * Double.valueOf(CList.get(i)[5]);
            if (cur > maxA4C5) {
                maxA4C5 = cur;
            }
        }
        return String.valueOf(maxA4C5);
    }

    private static boolean specificPositionSymbol (int from, int to, String[] B14BySymbol) {
        for(int i = from; i < to; i++) {
            if(B14BySymbol[i].equals("Y")) {
                return true;
            }
        }
        return false;
    }

    private static boolean specificPositionSymbolByC (int from, int to, List<String[]> CList) {
        for(String[] el : CList) {
            String[] C20 = el[20].split("");
            for(int i = from; i < to; i ++) {
                if(C20[i].equals("Y")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static double calculateD1011(int ifFrom, int ifTo, int elseFrom, int elseTo, List<String[]> CList, String[] clientB) {

        if(specificPositionSymbol(ifFrom,ifTo, clientB[14].split(""))) {
            double sumD10 = 0;
            for (String[] elStr : CList) {
                double el = Double.parseDouble(elStr[19]);
                if(el > 0) {
                    sumD10 += el;
                }
            }
            return sumD10;
        }

        if(specificPositionSymbolByC(elseFrom,elseTo, CList)) {
            double sumD10 = 0;
            for(String[] el : CList) {
                String[] С20 = el[20].split("");
                for(int i = elseFrom; i < elseTo; i ++) {
                    if(С20[i].equals("Y")) {
                        sumD10 += Double.valueOf(el[19]);
                    }
                }
            }
            return sumD10;
        }

        return 0;
    }

    private static String calculateD12(String[] D, List<String[]> CList, String A6) {
        double max = 0;
        for (String[] el: CList) {
            double C5 = Double.valueOf(el[5]);
            double cur = C5*Double.valueOf(A6);
            if(cur > max) {
                max = cur;
            }
        }
        double D3_D10_D14 = Double.valueOf(D[3]) - Double.valueOf(D[10]) - Double.valueOf(D[14]);
        double calc = 0;
        if(D3_D10_D14 > max) {
            calc = max;
        } else {
            calc = D3_D10_D14;
        }
        return String.valueOf(calc - Double.valueOf(D[6]) - Double.valueOf(D[9]));
    }

    private static String calculateD14(List<String[]> CList) {
        int sumD14 = 0;
        for (String[] elStr : CList) {
            int el = Integer.parseInt(elStr[19]);
            if(el < 0) {
                sumD14 += el;
            }
        }
        return String.valueOf(sumD14);
    }

}
