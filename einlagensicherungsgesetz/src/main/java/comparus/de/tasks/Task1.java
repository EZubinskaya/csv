package comparus.de.tasks;

import com.opencsv.CSVReader;
import comparus.de.domen.B;
import comparus.de.domen.CVSClient;
import comparus.de.domen.KeyFile1ToFile2;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static comparus.de.CSVReaderMain.*;
import static comparus.de.protocols.ProtocolForTask1.*;
import static comparus.de.util.Util.createNumValue;
import static comparus.de.util.Util.decimalToString;

/**
 * Created by ekaterina on 9/26/17.
 */
public class Task1 {
    static List<String[]> bTheSame = new ArrayList<>();

    public static Map<B,CVSClient> readCSVFileByString(String file, boolean addA2ToC2B) throws IOException {

        Map<B,CVSClient> clients = new TreeMap(new Comparator<B>() {
            @Override
            public int compare(B o1, B o2) {
                return o1.getBKey().compareTo(o2.getBKey());
            }
        });

        CSVReader reader = new CSVReader(new FileReader(file), '\n', '|');
        List<String[]> myEntries = reader.readAll();
        if(A == null) {
            A = myEntries.get(0)[0].split("\\*");
        } else {
            AFile2 = myEntries.get(0)[0].split("\\*");
        }

        if(myEntries.size() > 0) {
            for(int k = myEntries.size()-1; k > 0; k-- ) {
                if((myEntries.get(k)[0]).startsWith("E")) {
                    E = myEntries.get(k)[0].split("\\*");
                    break;
                }
            }
            int j = 1;
            for (int i = j ;i<myEntries.size(); i++) {
                String currentEllement = myEntries.get(i)[0];
                if(currentEllement.startsWith("B")) {
                    String[] bValue = currentEllement.split("\\*");

                    // UNIQUE KEY - B3 - Last Name, B4 - First Name and B12 - Birthday
                    String key = bValue[2] + bValue[3] + bValue[11];
                    B B = new B(key, bValue[1] + "-" + myEntries.get(0)[0].split("\\*")[1], bValue[1]);
                    List<String[]> CValues = new LinkedList<>();
                    String[] DValue = null;
                    j = i+1;
                    while (myEntries.get(j)[0].startsWith("C")) {
                        String[] cur = myEntries.get(j)[0].split("\\*");
                        if(addA2ToC2B) {
                            cur[2] = cur[2] + "-" + myEntries.get(0)[0].split("\\*")[1];
                        }
                        CValues.add(cur);
                        j++;
                    }
                    if(myEntries.get(j)[0].startsWith("D")) {
                        DValue = myEntries.get(j)[0].split("\\*");
                        j++;
                    }
                    clients.put(B, new CVSClient(bValue, CValues, DValue));
                }
            }
        }
        return clients;
    }

    public static Map<String,CVSClient> generateMapOfData(Map<B, CVSClient> readFile1, Map<B, CVSClient> readFile2) {
        Map<String,CVSClient> resultMap = new LinkedHashMap<>();
        List<KeyFile1ToFile2> keyForElementToRecalculateD = new ArrayList<>();
        protocol.setKeyFile1ToFile2(keyForElementToRecalculateD);

        Map<B, CVSClient> b2MapUtil = new TreeMap<>(new Comparator<B>() {
            @Override
            public int compare(B o1, B o2) {
                return o1.getB1RealInFile().compareTo(o2.getB1RealInFile());
            }
        });

        b2MapUtil.putAll(readFile2);

        for (Map.Entry<B, CVSClient> entry : readFile1.entrySet()){
            if (readFile2.get(entry.getKey()) != null) {
                KeyFile1ToFile2 keyFile1ToFile2 = new KeyFile1ToFile2();
                keyFile1ToFile2.setKeyFile1(StringUtils.join(readFile1.get(entry.getKey()).getClientB(), "*"));
                keyFile1ToFile2.setKeyFile2(StringUtils.join(readFile2.get(entry.getKey()).getClientB(), "*"));
                keyForElementToRecalculateD.add(keyFile1ToFile2);
                bTheSame.add(readFile2.get(entry.getKey()).getClientB());
                bTheSame.add(readFile1.get(entry.getKey()).getClientB());
                List<String[]> clients = new LinkedList<>();
                clients.addAll(entry.getValue().getClientsC());
                List<String[]> file2Crecords = readFile2.get(entry.getKey()).getClientsC();
                for (String[] curEl : file2Crecords) {
                    curEl[1] = readFile1.get(entry.getKey()).getClientB()[1];
                }
                clients.addAll(readFile2.get(entry.getKey()).getClientsC());
                resultMap.put(entry.getKey().getBKey(), new CVSClient(entry.getValue().getClientB(), clients, entry.getValue().getD()));
                readFile2.remove(entry.getKey());
            } else if(b2MapUtil.get(entry.getKey()) != null) {
                String[] B = entry.getValue().getClientB();
                B[1] = entry.getKey().getB1();
                List<String[]> C1 = entry.getValue().getClientsC();
                for(String[] curC : C1) {
                    curC[1] = curC[1] + "-" + A[1];
                }
                String[] D1 = entry.getValue().getD();
                D1[1] = D1[1] + "-" + A[1];
                CVSClient cvsClient = new CVSClient(B, C1, D1);
                resultMap.put(entry.getKey().getBKey(), cvsClient);
                String[] BFile2 = b2MapUtil.get(entry.getKey()).getClientB();
                BFile2[1] = BFile2[1] + "-" + AFile2[1];
                String B2InRealFile = BFile2[1];
                CVSClient cvsClientFile2 = b2MapUtil.get(entry.getKey());
                for(String[] curC : cvsClientFile2.getClientsC()) {
                    curC[1] = curC[1] + "-" + AFile2[1];
                }
                String[] D = cvsClientFile2.getD();
                D[1] = D[1] + "-" + AFile2[1];
                String key = BFile2[2] + BFile2[3] + BFile2[11];
                resultMap.put(key, cvsClientFile2);
                B bReadFile2 = new B(key, BFile2[1], B2InRealFile);
                readFile2.remove(bReadFile2);
            } else {
                resultMap.put(entry.getKey().getB1RealInFile(), entry.getValue());
            }
        }
        for (Map.Entry<B, CVSClient> entry : readFile2.entrySet()) {
            resultMap.put(entry.getKey().getBKey(), entry.getValue());
        }
        return resultMap;
    }

    public static String[] reCalculateE(Map<String, CVSClient> fullFile, String[] A) {
        String[] E = new String[15];
        E[0] = "E";
        E[1] = A[1];
        BigDecimal[] eVar = new BigDecimal[15];
        for (int i = 0; i<eVar.length; i++) {
            eVar[i] = BigDecimal.ZERO;
        }
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            CVSClient cvsClientVersion41 = entry.getValue();
            String[] D = cvsClientVersion41.getD();
            for(int i=2;i<eVar.length;i++){ // use eVar array length !!!
                eVar[i] = eVar[i].add(createNumValue(D[i]));
            }
        }
        for(int i = 2; i<eVar.length; i++) {
            E[i] = decimalToString(eVar[i]);
        }
        return E;
    }

    public static void reCalculateD(Map<String, CVSClient> fullFile, String[] A, boolean writeToProtocol) {
        List<String>  sameBList = new ArrayList<>();
        if(writeToProtocol) {
            sameBList = sameBList(protocol.getKeyFile1ToFile2());
        }
        List<String>  recalculatedDRecords = new ArrayList<>();
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            String[] clientB = entry.getValue().getClientB();
            CVSClient cvsClientVersion41 = entry.getValue();
            List<String[]> CList = cvsClientVersion41.getClientsC();
            String[] D = cvsClientVersion41.getD();
            if(writeToProtocol && sameBList.contains(StringUtils.join(clientB, "*"))) {
                D[2] = calculateD3(CList);
                D[3] = calculateD4(clientB, CList, createNumValue(D[2]));
                D[4] = calculateD5D7(createNumValue(D[2]), createNumValue(D[3]));
                D[5] = calculateD6(createNumValue(A[3]), createNumValue(D[4]), CList);
                D[6] = calculateD5D7(createNumValue(D[4]), createNumValue(D[5]));
                D[7] = "0,00";
                D[8] = "0,00";
                D[9] = calculateD1011(30, 49, 10, 49, CList, clientB);
                D[10] = calculateD1011(30, 49, 20, 49, CList, clientB);
                D[11] = calculateD12(D, CList, A[5]);
                D[12] = "0,00";
                D[13] = calculateD14(CList);
                D[14] = "0,00"; // why 101
                recalculatedDRecords.add(StringUtils.join(D, "*"));
            }
        }
        if(writeToProtocol) {
            protocol.setRecalculatedDRecords(recalculatedDRecords);
        }
    }


    static String calculateD3(List<String[]> CList) {
        BigDecimal sumD3 = BigDecimal.ZERO;
        for (String[] elStr : CList) {
            BigDecimal el = createNumValue(elStr[19]);
            if (el.signum() == 1)/*positive*/ {
                sumD3 = sumD3.add(el);
            }
        }
        return decimalToString(sumD3);
    }

    static String calculateD4(String[] clientB, List<String[]> CList, BigDecimal D3) {

        if(specificPositionSymbol(0,29,clientB[13])) {
            BigDecimal sumD4 = BigDecimal.ZERO;
            for (String[] elStr : CList) {
                BigDecimal el = createNumValue(elStr[19]);
                if (el.signum() == 1)/*positive*/ {
                    sumD4 = sumD4.add(el);
                }
            }
            return decimalToString(sumD4);
        }

        //COMPARING BIGDECIMALS - -1 less, 0 - eq, 1 - more
        if(compare(D3, 20) == -1 && C20AllStartWithY(CList)) {
            return decimalToString(D3);
        }

        if(C2C20ContainsYin1To20pos(CList)) {
            return calculateositiveC19ForAllContainsC2C20Y(CList);
        }
        return decimalToString(BigDecimal.valueOf(0, 2));
    }

    static int compare(BigDecimal a, int b) {
        return a.compareTo(new BigDecimal(b));
    }

    static boolean C20AllStartWithY(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(!elStr[20].startsWith("Y")) {
                return false;
            }
        }
        return true;
    }

    public static boolean C2C20ContainsYin1To20pos(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(elStr[20].substring(1,20).contains("Y")) {
                return true;
            }
        }
        return false;
    }

    static String calculateositiveC19ForAllContainsC2C20Y(List<String[]> CList) {
        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C18 = createNumValue(cStr[19]);
            if((cStr[20].substring(1,20).contains("Y")) && C18.signum() == 1) {
                sum  = sum.add(C18);
            }
        }
        return decimalToString(sum);
    }

    static String calculateD5D7(BigDecimal s, BigDecimal s1) {
        return decimalToString(s.subtract(s1));
    }

    //MAX AMOUNT IS A4 * C5 for D6 (account per person)
    //C5 MUST BE SAME FOR ALL C-RECORDS
    static String calculateD6(BigDecimal A4, BigDecimal D5, List<String[]> CList) {
        BigDecimal maxA4C5 = A4; // default - single account
        for(String[] cStr : CList) {
            maxA4C5 = A4.multiply(createNumValue(cStr[5]));
            if (D5.compareTo(maxA4C5) == 1) { //if more than max amount - cap to max amount
                return decimalToString(maxA4C5);
            }
        }
        return decimalToString(D5);
    }

    static boolean specificPositionSymbol(int from, int to, String value) {
        if(value.substring(from, to+1).contains("Y")) {
            return true;
        }
        return false;
    }

    private static boolean specificPositionSymbolByC (int from, int to, List<String[]> CList) {
        for(String[] el : CList) {
            if(el[20].substring(from, to+1).contains("Y")) {
                return true;
            }

        }
        return false;
    }

    static String calculateD1011(int ifFrom, int ifTo, int elseFrom, int elseTo, List<String[]> CList, String[] clientB) {
        BigDecimal sumD10 = BigDecimal.ZERO;

        if(specificPositionSymbol(ifFrom,ifTo, clientB[13])) {
            for (String[] cStr : CList) {
                BigDecimal el = createNumValue(cStr[19]);
                if(el.signum() == 1) {
                    sumD10 = sumD10.add(el);
                }
            }
            return decimalToString(sumD10);
        }

        if(specificPositionSymbolByC(elseFrom,elseTo, CList)) {
            for(String[] cStr : CList) {
                if(cStr[20].substring(elseFrom, elseTo).contains("Y")) {
                    sumD10.add(createNumValue(cStr[19]));
                }
            }

            return decimalToString(sumD10);
        }

        return decimalToString(BigDecimal.valueOf(0, 2));
    }

    //C5 MUST BE SAME FOR ALL C-RECORDS
    static String calculateD12(String[] D, List<String[]> CList, String A6) {
        BigDecimal max = BigDecimal.ZERO;
        for (String[] cStr: CList) {
            BigDecimal C5 = createNumValue(cStr[5]);
            BigDecimal cur = C5.multiply(createNumValue(A6));
            if(cur.compareTo(max) == 1) {
                max = cur;
            }
        }
        BigDecimal D3_D10_D14 = createNumValue(D[2]).subtract(createNumValue(D[9])).subtract(createNumValue(D[13]).abs());
        BigDecimal calc;
        if(D3_D10_D14.compareTo(max) == 1) {
            calc = max;
        } else {
            calc = D3_D10_D14;
        }
        BigDecimal result = calc.subtract(createNumValue(D[5])).subtract(createNumValue(D[8]));
        if(result.signum() == -1) {
            result = BigDecimal.ZERO;
        }
        return decimalToString(result);
    }

    static String calculateD14(List<String[]> CList) {
        BigDecimal sumD14 = BigDecimal.ZERO;
        for (String[] cStr : CList) {
            BigDecimal el = createNumValue(cStr[19]);
            if(el.signum() == -1) {
                sumD14 = sumD14.add(el);
            }
        }
        return decimalToString(sumD14);
    }
}
