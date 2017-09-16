package comparus.de;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ekaterina on 8/31/17.
 */
public class CSVReaderMain {

    private static String[] A = null;
    private static String[] E = null;

    static List<String[]> bTheSame = new ArrayList<>();
    public static void main(String[] args) throws IOException {

        String inputFileForMerge1 = System.getProperty("file1");
        String inputFileForMerge2 = System.getProperty("file2");
        String inputFileAfterMerge = System.getProperty("EinreicherdateiGesamt41");
        String A_Additional_5 = System.getProperty("A_Additional5");
        String B_Additional_5 = System.getProperty("B_Additional5");
        String C_Additional_5 = System.getProperty("C_Additional5");
        String taskNumber = System.getProperty("EinreicherdateiB11");

        String task1ResultFileName = "EinreicherdateiB11_Gesamt_4.1.csv";
        String task2ResultFileName = "Meldedatei_Gesamt_4.1.csv";
        String task3ResultFileName = "EinreicherdateiB11_Gesamt_5.1.csv";

        // TEST DATA
//        String inputFileForMerge1 =  "src/main/resources/example1.csv";
//        String inputFileForMerge2 = "src/main/resources/as/Pcs_0000000883170901.v";
//        String inputFileAfterMerge =  "src/main/resources/example1.csv";
//        String taskNumber = "Task3";
//        String A_Additional_5 = "src/main/resources/extraData/A_Additional_5.0.csv";
//        String B_Additional_5 = "src/main/resources/extraData/B_Additional_5.0.csv";
//        String C_Additional_5 = "src/main/resources/extraData/C_Additional_5.0.csv";

        if(taskNumber.equalsIgnoreCase("Task1") && inputFileForMerge1!= null & inputFileForMerge2 != null) {
            Map<String,CVSClient> readFile1 = readCSVFileByString(inputFileForMerge1);
            Map<String,CVSClient> readFile2 = readCSVFileByString(inputFileForMerge2);
            Map<String,CVSClient> fullFile = generateMapOfData(readFile1, readFile2);
            reCalculateD(fullFile, A);
            String[] E = reCalculateE(fullFile, A);
            List<String[]> allData = generateListOfDataArray(A, fullFile, E);
            writeCSV(allData, task1ResultFileName);
        } else if(taskNumber.equalsIgnoreCase("Task2") && inputFileAfterMerge != null) {
            Map<String,CVSClient> fullFile = readCSVFileByString(inputFileAfterMerge);
            List<String> metadata = generateMetadata(E, fullFile);
            List<String[]> allMetadata = generateMetadataArray(metadata);
            writeCSV(allMetadata, task2ResultFileName);
        } else if(taskNumber.equalsIgnoreCase("Task3") && inputFileAfterMerge != null) {
            Map<String,CVSClient> fullFile = readCSVFileByString(inputFileAfterMerge);
            Map<String, String> A_ExtraData = readExtraDataA(A_Additional_5);
            Map<String, B_ExtraData> B_ExtraData = readExtraDataB(B_Additional_5);
            Map<String, C_ExtraData> C_ExtraData = readExtraDataC(C_Additional_5);
            String[] AVersion5_1 = geeratedAVersion5_1 (A,  A_ExtraData);
            Map<String,CVSClient> fullFileVersion5_1 = generateVersion5(fullFile, E, AVersion5_1, B_ExtraData, C_ExtraData);
            reCalculateDVersion5_1(fullFileVersion5_1, AVersion5_1);
            String[] EVersion5_1 = reCalculateEVersion5_1(fullFileVersion5_1);
            List<String[]> allDataVersion5_1 = generateListOfDataArray(AVersion5_1, fullFileVersion5_1, EVersion5_1);
            writeCSV(allDataVersion5_1, task3ResultFileName);
        } else if (taskNumber.equalsIgnoreCase("Task4")) {

        } else if (taskNumber.equalsIgnoreCase("Task5")) {

        } else {

        }

    }

    static void writeCSV(List<String[]> all, String outputFile ) throws IOException {
        File file = new File(outputFile);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        CSVWriter csvWriter = new CSVWriter(writer, '*', CSVWriter.NO_QUOTE_CHARACTER, '\'', System.lineSeparator());
        csvWriter.writeAll(all);
        csvWriter.close();
    }

    static List<String> generateMetadata(String[] E, Map<String, CVSClient> fullFile) throws IOException {
        List<String> metadata = new ArrayList<>(147);
        metadata.add("M");
        metadata.add(A[1]);
        Date date = new Date();
        String currentDate = new SimpleDateFormat("yyMMdd").format(date);
        metadata.add(currentDate);

        for (int i = 4; i < 17; i++) {
            metadata.add(E[i - 2]);
        }

        for (int i = 17; i < 67; i++) {

            BigDecimal sumD = BigDecimal.ZERO;
            for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
                String[] B = entry.getValue().getClientB();
                if (B[13].substring(i-17, i-16).equals("Y")) {
                    sumD =  sumD.add(createNumValue(entry.getValue().getD()[2]));
                }
            }
            metadata.add(decimalToString(sumD));
        }
        for (int i = 67; i < 117; i++) {

            BigDecimal sumC = BigDecimal.ZERO;
            for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
                List<String[]> C = entry.getValue().getClientsC();
                for (String[] el : C) {
                    if (el[20].substring(i-67, i-66).equals("Y")) {
                        sumC = sumC.add( createNumValue(el[19]));
                    }
                }
            }
            metadata.add(decimalToString(sumC));
        }

        BigDecimal sum118 = BigDecimal.ZERO;
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            List<String[]> C = entry.getValue().getClientsC();
            for (String[] el : C) {
                if (el[21].contains("BE")) {
                    sum118 = sum118.add(createNumValue(el[19]));
                }
            }
        }
        metadata.add(decimalToString(sum118));

        for (int i = 118; i < 146; i++) {
            metadata.add("0");
        }

        BigDecimal sum146 = BigDecimal.ZERO;
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            List<String[]> C = entry.getValue().getClientsC();
            for (String[] el : C) {
                if (el[21].contains("CY")) {
                    sum146 = sum146.add(createNumValue(el[19]));
                }
            }
        }
        metadata.add(decimalToString(sum146));

        return metadata;
    }

    static List<String[]> generateMetadataArray(List<String> metadataList) throws IOException {
        return new ArrayList<String[]>() { {add(metadataList.stream().toArray(String[]::new));}};
    }

    static List<String> generateListOfData(String[] A, Map<String, CVSClient> fullFile, String[] e) {
        List<String> fullData = new ArrayList<String>();
        fullData.add(String.join("*", A));
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            fullData.add(String.join("*", entry.getValue().getClientB()));
            fullData.addAll(entry.getValue().listStringClientsC());
            fullData.add(String.join("*", entry.getValue().getD()));
        }
        fullData.add(String.join("*", e));
        return fullData;
    }

    static List<String[]> generateListOfDataArray(String[] a, Map<String, CVSClient> fullFile, String[] e) {
        List<String[]> fullData = new ArrayList<String[]>();
        fullData.add(a);
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            fullData.add(entry.getValue().getClientB());
            fullData.addAll(entry.getValue().getClientsC());
            fullData.add(entry.getValue().getD());
        }
        fullData.add(e);
        return fullData;
    }

    static Map<String,CVSClient> readCSVFileByString(String file) throws IOException {

        Map<String,CVSClient> clients = new LinkedHashMap<>();
        CSVReader reader = new CSVReader(new FileReader(file), '\n', '\'');
        List<String[]> myEntries = reader.readAll();
        if(A == null) {
            A = myEntries.get(0)[0].split("\\*");
        }
        if((myEntries.get(myEntries.size()-1)[0]).startsWith("E")) {
            E = myEntries.get(myEntries.size()-1)[0].split("\\*");
        }
        int j = 1;
        for (int i = j ;i<myEntries.size(); i++) {
            String currentEllement = myEntries.get(i)[0];
            if(currentEllement.startsWith("B")) {
                String[] bValue = currentEllement.split("\\*");

                // UNIQUE KEY - B3 - Last Name, B4 - First Name and B12 - Birthday
                String key = bValue[2] + bValue[3] + bValue[11];

                List<String[]> CValues = new LinkedList<>();
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
                clients.put(key, new CVSClient(bValue, CValues, DValue));
            }
        }
        return clients;
    }

    static Map<String,CVSClient> generateMapOfData(Map<String, CVSClient> readFile1, Map<String, CVSClient> readFile2) {
        Map<String,CVSClient> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, CVSClient> entry : readFile1.entrySet()){
            if (readFile2.get(entry.getKey()) != null) {
                bTheSame.add(readFile2.get(entry.getKey()).getClientB());
                bTheSame.add(readFile1.get(entry.getKey()).getClientB());

                List<String[]> clients = new LinkedList<>();
                clients.addAll(entry.getValue().getClientsC());
                clients.addAll(readFile2.get(entry.getKey()).getClientsC());
                resultMap.put(entry.getKey(), new CVSClient(entry.getValue().getClientB(), clients, entry.getValue().getD()));
                readFile2.remove(entry.getKey());
            } else {
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        resultMap.putAll(readFile2);
        return resultMap;
    }

    static String[] reCalculateE(Map<String, CVSClient> fullFile, String[] A) {
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

    static Map<String, CVSClient> reCalculateD(Map<String, CVSClient> fullFile, String[] A) {
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            String[] clientB = entry.getValue().getClientB();
            CVSClient cvsClientVersion41 = entry.getValue();
            List<String[]> CList = cvsClientVersion41.getClientsC();
            String[] D = cvsClientVersion41.getD();
            D[2] = calculateD3(CList);
            D[3] = calculateD4(clientB, CList, createNumValue(D[2]));
            D[4] = calculateD5D7(createNumValue(D[2]), createNumValue(D[3]));
            D[5] = calculateD6(createNumValue(A[3]), createNumValue(D[4]), CList);
            D[6] = calculateD5D7(createNumValue(D[4]), createNumValue(D[5]));
            D[7] = "0";
            D[8] = "0";
            D[9] = calculateD1011(30, 49, 10, 49, CList, clientB);
            D[10] = calculateD1011(30, 49, 20, 49, CList, clientB);
            D[11] = calculateD12(D, CList, A[5]);
            D[12] = "0";
            D[13] = calculateD14(CList);
            D[14] = "0"; // why 101
        }
        return fullFile;
    }

    private static String calculateD3(List<String[]> CList) {
        BigDecimal sumD3 = BigDecimal.ZERO;
        for (String[] elStr : CList) {
            BigDecimal el = createNumValue(elStr[19]);
            if (el.signum() == 1)/*positive*/ {
                sumD3 = sumD3.add(el);
            }
        }
        return decimalToString(sumD3);
    }

    private static String calculateD4(String[] clientB, List<String[]> CList, BigDecimal D3) {

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

    private static boolean C20AllStartWithY(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(!elStr[20].startsWith("Y")) {
                return false;
            }
        }
        return true;
    }

    private static boolean C2C20ContainsYin1To20pos(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(elStr[20].substring(1,20).contains("Y")) {
                return true;
            }
        }
        return false;
    }

    private static String calculateositiveC19ForAllContainsC2C20Y(List<String[]> CList) {
        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C18 = createNumValue(cStr[19]);
            if((cStr[20].substring(1,20).contains("Y")) && C18.signum() == 1) {
                sum  = sum.add(C18);
            }
        }
        return decimalToString(sum);
    }

    private static String calculateD5D7(BigDecimal s, BigDecimal s1) {
        return decimalToString(s.subtract(s1));
    }

    //MAX AMOUNT IS A4 * C5 for D6 (account per person)
    //C5 MUST BE SAME FOR ALL C-RECORDS
    private static String calculateD6(BigDecimal A4, BigDecimal D5, List<String[]> CList) {
        BigDecimal maxA4C5 = A4; // default - single account
        for(String[] cStr : CList) {
            maxA4C5 = A4.multiply(createNumValue(cStr[5]));
            if (D5.compareTo(maxA4C5) == 1) { //if more than max amount - cap to max amount
                return decimalToString(maxA4C5);
            }
        }
        return decimalToString(D5);
    }

    private static boolean specificPositionSymbol (int from, int to, String value) {
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

    private static String calculateD1011(int ifFrom, int ifTo, int elseFrom, int elseTo, List<String[]> CList, String[] clientB) {
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
    private static String calculateD12(String[] D, List<String[]> CList, String A6) {
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

    private static String calculateD14(List<String[]> CList) {
        BigDecimal sumD14 = BigDecimal.ZERO;
        for (String[] cStr : CList) {
            BigDecimal el = createNumValue(cStr[19]);
            if(el.signum() == -1) {
                sumD14 = sumD14.add(el);
            }
        }
        return decimalToString(sumD14);
    }

    private static BigDecimal createNumValue(String value) {
        BigDecimal result = BigDecimal.ZERO;
        String sanitized = value.replace(",", ".");
        if (NumberUtils.isCreatable(sanitized))     {
            result = NumberUtils.createBigDecimal(sanitized);
        }
        return result;
    }

    private static int compare(BigDecimal a, int b) {
        return a.compareTo(new BigDecimal(b));
    }

    private static String decimalToString(BigDecimal decimal) {
        if (decimal.signum() == 0) {
            return "0";
        }
        return String.valueOf(decimal).replace(".", ",");
    }

    //    Version 5
    private static BigDecimal calculateHW1(String[] D) {
        BigDecimal sumHW1 = BigDecimal.ZERO;
        BigDecimal maxD6D9 = createNumValue(D[5]).max(createNumValue(D[8]));
        BigDecimal maxD6D9D11 = maxD6D9.max(createNumValue(D[10]));
        sumHW1= createNumValue(D[2]).subtract(createNumValue(D[9])).subtract(createNumValue(D[13])).subtract(maxD6D9D11);
        return sumHW1;
    }

    static BigDecimal calculateHW2(List<String[]> CList) {
        if(C21ContainsYin19posAndC2701Or90(CList)) {
            return calculateositiveC19ForAllContainsC20YPosition19(CList);
        }
        return new BigDecimal("0");
    }

    private static boolean C21ContainsYin19posAndC2701Or90(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(elStr[21].substring(18,19).equals("Y") && (elStr[27].equals("01") || elStr[27].equals("90"))) {
                return true;
            }
        }
        return false;
    }

    private static BigDecimal calculateositiveC19ForAllContainsC20YPosition19(List<String[]> CList) {
        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C18 = createNumValue(cStr[19]);
            //TODO
            if((cStr[21].substring(18,19).contains("Y")) && C18.signum() == 1 && (cStr[27].equals("01") || cStr[27].equals("90"))) {
                sum  = sum.add(C18);
            }
        }
        return sum;
    }

    static BigDecimal calculateHW3(List<String[]> CList) {
        BigDecimal sumHW3 = BigDecimal.ZERO;
        if(HW3C19ForAllC2790C21In11And19IsN(CList)) {
            sumHW3.add(calculateositiveC19ForHW3C19ForAllC2790C21In11And19IsN(CList));
        }
        if(HW3C27Is20(CList)) {
            sumHW3.add(calculateositiveC19ForHW3C27Is20(CList));
        }
        return sumHW3;
    }


    private static boolean HW3C19ForAllC2790C21In11And19IsN(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(elStr[21].substring(10,11).equals("N") && elStr[21].substring(18,19).equals("N") && (elStr[27].equals("01") || elStr[27].equals("90"))) {
                return true;
            }
        }
        return false;
    }

    private static BigDecimal calculateositiveC19ForHW3C19ForAllC2790C21In11And19IsN(List<String[]> CList) {
        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C18 = createNumValue(cStr[19]);
            //TODO
            if(C18.signum() == 1 && cStr[21].substring(10,11).equals("N") && cStr[21].substring(18,19).equals("N") && (cStr[27].equals("01") || cStr[27].equals("90"))) {
                sum  = sum.add(C18);
            }
        }
        return sum;
    }

    private static boolean HW3C27Is20(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(elStr[27].equals("20")) {
                return true;
            }
        }
        return false;
    }

    private static BigDecimal calculateositiveC19ForHW3C27Is20(List<String[]> CList) {
        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C18 = createNumValue(cStr[19]);
            if(C18.signum() == 1 && cStr[27].equals("20")) {
                sum  = sum.add(C18);
            }
        }
        return sum;
    }

    private static BigDecimal calculateHW4(List<String[]> CList) {
        BigDecimal sumHW4 = BigDecimal.ZERO;
        if(HW4C21InPosition11YAnd2701Or90(CList)) {
            return calculateHW4C21InPosition11YAnd2701Or90(CList);
        }
        return sumHW4;
    }

    private static boolean HW4C21InPosition11YAnd2701Or90(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(elStr[21].substring(10,11).equals("Y") && (elStr[27].equals("01") || elStr[27].equals("90"))) {
                return true;
            }
        }
        return false;
    }

    private static BigDecimal calculateHW4C21InPosition11YAnd2701Or90(List<String[]> CList) {
        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C18 = createNumValue(cStr[19]);
            if(C18.signum() == 1 && cStr[21].substring(10,11).equals("Y") && (cStr[27].equals("01") || cStr[27].equals("90"))) {
                sum  = sum.add(C18);
            }
        }
        return sum;
    }

    static BigDecimal calculateHW5(List<String[]> CList) {
        BigDecimal sumHW5 = BigDecimal.ZERO;
        if(C19WhereC27Is90AndC21IsNInPosition11(CList)) {
            sumHW5.add(calculateC19WhereC27Is90AndC21IsNInPosition11(CList));
        }
        if(C27Is20(CList)) {
            sumHW5.add(calculateC27Is20(CList));
        }
        return sumHW5;
    }

    private static boolean C19WhereC27Is90AndC21IsNInPosition11(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(elStr[27].equals("90") && elStr[21].substring(10,11).equals("N")) {
                return true;
            }
        }
        return false;
    }

    private static BigDecimal calculateC19WhereC27Is90AndC21IsNInPosition11(List<String[]> CList) {
        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C18 = createNumValue(cStr[19]);
            if(C18.signum() == 1 && cStr[27].equals("90") && cStr[21].substring(10,11).equals("N")) {
                sum  = sum.add(C18);
            }
        }
        return sum;
    }

    private static boolean C27Is20(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(elStr[27].equals("20")) {
                return true;
            }
        }
        return false;
    }

    private static BigDecimal calculateC27Is20(List<String[]> CList) {
        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C18 = createNumValue(cStr[19]);
            if(C18.signum() == 1 && cStr[27].equals("20")) {
                sum  = sum.add(C18);
            }
        }
        return sum;
    }

    static BigDecimal BigDecimalcalculateD12B(String[] D, String[] A, List<String[]> CList) {
        BigDecimal D12B = BigDecimal.ZERO;

        BigDecimal D12B_HW1_D12A = calculateHW1(D).subtract(createNumValue(D[11]));
        BigDecimal D12B_SecondPart = BigDecimal.ZERO;

        BigDecimal HW2 = calculateHW2(CList);
        BigDecimal HW3 = calculateHW3(CList);

        BigDecimal D6PlusD9MinusHW3 = createNumValue(D[5]).add(createNumValue(D[8])).subtract(HW3);


        if(D6PlusD9MinusHW3.signum() == 1) {
            D12B_SecondPart = HW2.subtract(createNumValue(D[5]).add(createNumValue(D[8])).subtract(HW3));
        } else {
            D12B_SecondPart = HW2;
        }

        BigDecimal C5 = createNumValue(CList.get(0)[5]);
        BigDecimal D12B_ThirdPart = createNumValue(A[8]).multiply(C5).subtract(createNumValue(D[5]).add(createNumValue(D[8]).add(createNumValue(D[11]))));

        D12B = D12B_HW1_D12A.min(D12B_SecondPart).min(D12B_ThirdPart);

        if(D12B.signum() == -1) {
            D12B = BigDecimal.ZERO;
        }
        return D12B;
    }

    static BigDecimal BigDecimalcalculateD12C(String[] D, String[] A, List<String[]> CList) {
        BigDecimal D12C = BigDecimal.ZERO;
        BigDecimal HW1 = calculateHW1(D);
        BigDecimal С5 = createNumValue(CList.get(0)[5]);
        BigDecimal HW4 = calculateHW4(CList);
        BigDecimal HW5 = calculateHW5(CList);

        if(ifD12C(HW1, A, CList)) {

            BigDecimal D12C_First = HW1.subtract(createNumValue(D[11]).subtract(createNumValue(D[12])));

            BigDecimal D12C_Second = BigDecimal.ZERO;
            if(createNumValue(D[5]).add(createNumValue(D[8])).subtract(HW5).compareTo(BigDecimal.ZERO) == 1) {
                D12C_Second = HW4.subtract(createNumValue(D[5]).add(createNumValue(D[8]).subtract(HW5)));
            } else {
                D12C_Second = HW4;
            }
            BigDecimal D12C_Third = createNumValue(A[6]).multiply(С5).subtract((createNumValue(D[5]).add(createNumValue(D[8])).add(createNumValue(D[11])).add(createNumValue(D[12]))));


            D12C = D12C_First.min(D12C_Second).min(D12C_Third);
            return D12C;
        }

        return D12C;
    }

    private static BigDecimal BigDecimalcalculateD14B(String[] D, List<String[]> CList) {
        BigDecimal D14B = createNumValue(D[2]).subtract(createNumValue(D[14])).subtract(createNumValue(D[9])).subtract((createNumValue(D[5]).max(createNumValue(D[8]))).max(createNumValue(D[10])));
        if(D14B.signum() == 1) {
            D14B = D14B.negate();
        }
        return D14B;
    }

    private static boolean ifD12C(BigDecimal HW1, String[] A, List<String[]> CList) {
        for(String[] cStr : CList) {
            if(HW1.compareTo(createNumValue(A[5]).multiply(createNumValue(cStr[5]))) == 1 && cStr[21].substring(10,11).equals("Y")) {
                return true;
            }
        }
        return false;
    }

    public static Map<String,String> readExtraDataA (String A_Additional_5) throws IOException {
        Map<String,String> A = new LinkedHashMap<>();
        CSVReader reader = new CSVReader(new FileReader(A_Additional_5), '\n', '\'');
        List<String[]> myEntries = reader.readAll();
        String[] keys = myEntries.get(0)[0].split("\\*", -1);
        String[] values = myEntries.get(1)[0].split("\\*", -1);
        for(int i = 0; i<keys.length; i++) {
            A.put(keys[i], values[i]);
        }
        return A;
    }

    public static Map<String,B_ExtraData> readExtraDataB (String B_Additional_5) throws IOException {
        Map<String,B_ExtraData> B = new LinkedHashMap<>();
        CSVReader reader = new CSVReader(new FileReader(B_Additional_5), '\n', '\'');
        List<String[]> myEntries = reader.readAll();
        for(int i = 1; i < myEntries.size(); i++) {
            String[] curEl = myEntries.get(i)[0].split("\\*", -1);
            String key = curEl[2];
            B_ExtraData value = new B_ExtraData(createNumValue(curEl[0]), curEl[1], createNumValue(curEl[3]));
            B.put(key, value);
        }
        return B;
    }

     static Map<String,C_ExtraData> readExtraDataC(String C_Additional_5) throws IOException {
        Map<String,C_ExtraData> C = new LinkedHashMap<>();
        CSVReader reader = new CSVReader(new FileReader(C_Additional_5), '\n', '\'');
        List<String[]> myEntries = reader.readAll();
        for(int i = 1; i < myEntries.size(); i++) {
            String[] curEl = myEntries.get(i)[0].split("\\*", -1);
            String key = curEl[2];
            C_ExtraData value = new C_ExtraData(createNumValue(curEl[0]), curEl[1], curEl[3], curEl[4], curEl[5], curEl[6], curEl[7], (curEl[8]), (curEl[9]), (curEl[10]), (curEl[11]));
            C.put(key, value);
        }
        return C;
    }

    static String[] geeratedAVersion5_1 (String[] A,  Map<String,String> A_Additional) {
         //A
         List<String> AVersion5_1List = new LinkedList<>();
         AVersion5_1List.addAll(Arrays.asList(A));
         AVersion5_1List.add(A_Additional.get("A8"));
         AVersion5_1List.add(A_Additional.get("A9"));
         AVersion5_1List.add(A_Additional.get("A10"));
         String[] AVersion5_1 = new String[AVersion5_1List.size()];
         AVersion5_1 = AVersion5_1List.toArray(AVersion5_1);
         return AVersion5_1;
    }

    public static Map<String,CVSClient> generateVersion5 (Map<String,CVSClient> fullFile, String[] E, String[] AVersion5_1, Map<String,B_ExtraData> B_Additional, Map<String,C_ExtraData> C_Additional) {

        Map<String,CVSClient>  fullFileVersion5_1 = new LinkedHashMap<>();
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            String key = entry.getKey();
            CVSClient value = entry.getValue();

            //B
            String[] clientB = value.getClientB();
            List<String> clientBList = new LinkedList<>();
            String BPrimaryKey = clientB[1];
            B_ExtraData b_extraData = B_Additional.get(BPrimaryKey);
            String B14 = clientB[13];
            String B14Version5_1 = B14.substring(0,32) + "NN" + B14.substring(34);
            clientB[13] = B14Version5_1;
            clientBList.addAll(Arrays.asList(clientB));
            clientBList.add("");
            clientBList.add(b_extraData.getB16().toString());
            String[] BVersion5_1 = new String[clientBList.size()];
            BVersion5_1 = clientBList.toArray(BVersion5_1);
            value.setClientB(BVersion5_1);

            //C
            List<String[]> clientC = value.getClientsC();
            for(int i = 0; i < clientC.size(); i++) {
                String[] currentC = clientC.get(i);
                C_ExtraData c_extraData = C_Additional.get(currentC[2]);
                //TODO
                currentC[21] = currentC[21].substring(0,14) + c_extraData.getC21_Pos15() + c_extraData.getC21_Pos16() + c_extraData.getC21_Pos17()
                        + c_extraData.getC21_Pos18() + c_extraData.getC21_Pos19() + currentC[21].substring(19);
                currentC[20] = C20(currentC[20], currentC[21], BVersion5_1[13], BVersion5_1[15]);

                List<String> curClientCList = new LinkedList<>();
                curClientCList.addAll(Arrays.asList(currentC));
                curClientCList.add("");
                curClientCList.add("");
                curClientCList.add(c_extraData.getC24());
                curClientCList.add(c_extraData.getC25());
                curClientCList.add(c_extraData.getC26());
                curClientCList.add(c_extraData.getC27());

                String[] curCVersion5_1 = new String[currentC.length];
                curCVersion5_1 = curClientCList.toArray(curCVersion5_1);
                clientC.set(i,curCVersion5_1);
             }
            fullFileVersion5_1.put(key,value);

        }
        return fullFileVersion5_1;
    }

    private static String C20(String C20, String C21, String B14, String B16) {
        String position12 = "";
        if(C20.substring(1,2).equals("Y") && C21.substring(9,10).equals("Y")) {
            position12="Y";
        } else {
            position12="N";
        }

        String position13 = "";
        if(Character.toString(B14.charAt(1)).equals("Y") && Character.toString(C21.charAt(17)).equals("N") && !B16.equals("20")) {
            position13="Y";
        } else {
            position13="N";
        }

        String position14 = "";
        if(Character.toString(B14.charAt(4)).equals("Y") && Character.toString(C21.charAt(17)).equals("N")) {
            position14="Y";
        } else {
            position14="N";
        }

        String position15 = "";
        if(Character.toString(B14.charAt(5)).equals("Y") && Character.toString(C21.charAt(17)).equals("N")) {
            position15="Y";
        } else {
            position15="N";
        }

        String position16 = "";
        if(Character.toString(B14.charAt(9)).equals("Y") && Character.toString(C21.charAt(17)).equals("N")) {
            position16="Y";
        } else {
            position16="N";
        }
        return C20;
    }

    static String[] reCalculateEVersion5_1(Map<String, CVSClient> fullFileVersion5_1) {
        String[] E = new String[18];
        E[0] = "E";
        E[1] = A[1];
        BigDecimal[] eVar = new BigDecimal[18];
        for (int i = 0; i<eVar.length; i++) {
            eVar[i] = BigDecimal.ZERO;
        }
        for (Map.Entry<String, CVSClient> entry : fullFileVersion5_1.entrySet()){
            CVSClient cvsClientVersion41 = entry.getValue();
            String[] D = cvsClientVersion41.getD();
            for(int i=2;i<eVar.length;i++){ // use eVar array length !!!
                if(i == 15) {
                    continue;
                }
                eVar[i] = eVar[i].add(createNumValue(D[i]));
            }
        }
        for(int i = 2; i<eVar.length; i++) {
            if(i ==15) {
                E[i] = "";
            }
            E[i] = decimalToString(eVar[i]);
        }
        return E;
    }

    static Map<String, CVSClient> reCalculateDVersion5_1(Map<String, CVSClient> fullFileVersion5_1, String[] AVersion5_1) {
        for (Map.Entry<String, CVSClient> entry : fullFileVersion5_1.entrySet()){
            String[] clientB = entry.getValue().getClientB();
            CVSClient cvsClientVersion= entry.getValue();
            List<String[]> CList = cvsClientVersion.getClientsC();
            String[] D = cvsClientVersion.getD();
            D[4] = calculateD4Version5_1(clientB, CList, createNumValue(D[2]));
            List<String> curDList = new LinkedList<>();
            curDList.addAll(Arrays.asList(D));
            BigDecimal D12B = BigDecimalcalculateD12B(D, AVersion5_1, CList);
            BigDecimal D12C = BigDecimalcalculateD12C(D, AVersion5_1, CList);
            BigDecimal D14B = BigDecimalcalculateD14B(D, CList);

            curDList.add(12, String.valueOf(D12B));
            curDList.add(13, String.valueOf(D12C));
            curDList.add(16, String.valueOf(D14B));

            String[] DVersion5_1 = new String[D.length];
            DVersion5_1 = curDList.toArray(DVersion5_1);

            cvsClientVersion.setD(DVersion5_1);
        }
        return fullFileVersion5_1;

    }

    private static String calculateD4Version5_1(String[] clientB, List<String[]> CList, BigDecimal D3) {

        if(specificPositionSymbol(0,19,clientB[13])) {
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

}