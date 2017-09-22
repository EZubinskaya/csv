package comparus.de;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.lang3.StringUtils;
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
    private static String[] AFile2 = null;

    private static String[] E = null;
    private static Protocol protocol = new Protocol();

    static List<String[]> bTheSame = new ArrayList<>();

    //Protocol
    static FileInfo readCSVFileByStringRetCount(Map<B, CVSClient> fullFile, String fileName) throws IOException {
        FileInfo fileInfo = new FileInfo();
        int b = 0;
        int c = 0;
        int d = 0;
        for (Map.Entry<B, CVSClient> entry : fullFile.entrySet()){
            CVSClient cvsClient = entry.getValue();
            b += 1;
            c += cvsClient.getClientsC().size();
            d += 1;
        }
        fileInfo.setFileName(fileName);
        fileInfo.setBCount(b);
        fileInfo.setCCount(c);
        fileInfo.setDCount(d);
        return fileInfo;
    }

    static void calculateAmount(List<String[]> allData) {
        int countB = 0;
        int countC = 0;
        int countD = 0;

        for(String[] el : allData) {
            if(el[0].equalsIgnoreCase("B")) {
                countB ++;
            } else if (el[0].equalsIgnoreCase("C")) {
                countC ++;
            } else if (el[0].equalsIgnoreCase("D")) {
                countD ++;
            }
        }
        protocol.setResultAmountB(countB);
        protocol.setResultAmountC(countC);
        protocol.setResultAmountD(countD);
    }

    static List<String>  sameBList(List<KeyFile1ToFile2> keys) {
        List<String> contistTheSameB = new ArrayList<>();
        for (KeyFile1ToFile2 cur : keys) {
            contistTheSameB.add(cur.getKeyFile1());
            contistTheSameB.add(cur.getKeyFile2());
        }
        return contistTheSameB;
    }

    static void  writeProtocolToFile(String protocolName, String taskNumber, String inputFileForMerge1, String inputFileForMerge2,
            String task1ResultFileName, FileInfo fileInfo1, FileInfo fileInfo2, String errorMessage) throws FileNotFoundException {

        Writer writer = null;
        try {
            if(taskNumber.equalsIgnoreCase("Task1") && inputFileForMerge1 != null && inputFileForMerge2 != null) {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(protocolName), "utf-8"));
                writer.write("\r\n=============================================================================\r\n");
                writer.write("GENERAL INFORMATIONS\r\n\r\n");

                writer.write("Program name: einlagensicherungsgesetz\r\n");
                writer.write("Date of run: " + new SimpleDateFormat("dd-MM-YYYY").format(new Date()) + "\r\n");
                writer.write("Task: " + taskNumber + "\r\n");
                writer.write("Input file 1: " + inputFileForMerge1 + "\r\n");
                writer.write("Input file 2: " + inputFileForMerge2 + "\r\n");
                writer.write("Filename result file: " + task1ResultFileName + "\r\n");

                writer.write("\r\n=============================================================================\r\n");
                writer.write("ERRORS\r\n");
                if(errorMessage != null) {
                    writer.write(errorMessage + "\r\n");
                }

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nAMOUNTS OF DATASETS\r\n\r\n");
                writer.write("Input file name 1: " + fileInfo1.getFileName() + "\r\n");
                writer.write("B : " + fileInfo1.getBCount() + "\r\n");
                writer.write("C : " + fileInfo1.getCCount()  + "\r\n");
                writer.write("D : " + fileInfo1.getDCount()  + "\r\n");
                writer.write("Input file name: " + fileInfo2.getFileName() + "\r\n");
                writer.write("B : " + fileInfo2.getBCount() + "\r\n");
                writer.write("C : " + fileInfo2.getCCount()  + "\r\n");
                writer.write("D : " + fileInfo2.getDCount()  + "\r\n");
                writer.write("Resultfile\r\n");
                writer.write("B : " + protocol.getResultAmountB() + "\r\n");
                writer.write("C : " + protocol.getResultAmountC() + "\r\n");
                writer.write("D : " + protocol.getResultAmountD() + "\r\n");

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nMERGING\r\n\r\n");
                writer.write("Amount of merged datasets: " + protocol.getResultAmountTotal() + "\r\n");
                writer.write("List of identified double customers:" + "\r\n");
                for(KeyFile1ToFile2 b : protocol.getKeyFile1ToFile2()) {
                    writer.write("B2 in file1 : " + b.getKeyFile1().split("\\*")[1] + "\r\n");
                    writer.write("B2 in file2 : " + b.getKeyFile2().split("\\*")[1] + "\r\n");
                    writer.write("B2 in result file : " + b.getKeyFile1().split("\\*")[1] + "\r\n");
                }

                writer.write("\r\n=============================================================================\r\n");
                writer.write("\r\nRECALCULATING\r\n\r\n");
                writer.write("List of recalculated D-Records in result file:\r\n");
                for(String d : protocol.getRecalculatedDRecords()) {
                    writer.write(d + "\r\n");
                }
            }
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }

    public static void main(String[] args) throws IOException {

//        String inputFileForMerge1 = System.getProperty("file1");
//        String inputFileForMerge2 = System.getProperty("file2");
//        String inputFileAfterMerge = System.getProperty("EinreicherdateiGesamt41");
//        String A_Additional_5 = System.getProperty("A_Additional5");
//
//        String Additional_CRecord_Data_Task1a = System.getProperty("Additional_CRecord_Data_Task1a");
//        String B_Additional_5 = System.getProperty("B_Additional5");
//        String C_Additional_5 = System.getProperty("C_Additional5");
//        String taskNumber = System.getProperty("taskNumber");

        String task1ResultFileName = "EinreicherdateiB11_Gesamt_4.1.csv";
        String task1aResultFileName = "EinreicherdateiB11_Gesamt_4.1_add_additional_C.csv";
        String task2ResultFileName = "Meldedatei_Gesamt_4.1.csv";
        String task3ResultFileName = "EinreicherdateiB11_Gesamt_5.1.csv";

        // TEST DATA
        String inputFileForMerge1 =  "src/main/resources/bug1/1.csv";
        String inputFileForMerge2 = "src/main/resources/bug1/2.csv";
        String inputFileAfterMerge =  "src/main/resources/bug1/3.csv";
        String Additional_CRecord_Data_Task1a = "src/main/resources/bug1/Additional_CRecord_Data_Task1a.csv";

        String taskNumber = "Task1a";
        String A_Additional_5 = "src/main/resources/extraData/A_Additional_5.0.csv";
        String B_Additional_5 = "src/main/resources/extraData/B_Additional_5.0.csv";
        String C_Additional_5 = "src/main/resources/extraData/C_Additional_5.0.csv";

        if(taskNumber.equalsIgnoreCase("Task1") && inputFileForMerge1!= null & inputFileForMerge2 != null) {
            Map<B, CVSClient> readFile1 = null;
            Map<B, CVSClient> readFile2 = null;
            FileInfo fileInfo1 = null;
            FileInfo fileInfo2 = null;
            List<String[]> allData = null;
            String errorMessage = null;
            try {
                readFile1 = readCSVFileByString(inputFileForMerge1);
                readFile2 = readCSVFileByString(inputFileForMerge2);

                fileInfo1 = readCSVFileByStringRetCount(readFile1, inputFileForMerge1);
                fileInfo2 = readCSVFileByStringRetCount(readFile2, inputFileForMerge2);

                Map<String, CVSClient> fullFile = generateMapOfData(readFile1, readFile2);
                reCalculateD(fullFile, A);
                String[] E = reCalculateE(fullFile, A);
                allData = generateListOfDataArray(A, fullFile, E);
                writeCSV(allData, task1ResultFileName);
            } catch (Exception ex) {
                System.out.println(" exception " + ex.getStackTrace());
                throw ex;
            }

            //Protocol
            calculateAmount(allData);
            protocol.setResultAmountTotal(allData.size());
            protocol.setResultAmountTotal(protocol.getKeyFile1ToFile2().size());
                        writeProtocolToFile("Protocol Task1.txt", taskNumber, inputFileForMerge1, inputFileForMerge2,
                    task1ResultFileName, fileInfo1, fileInfo2, errorMessage);
        } else if(taskNumber.equalsIgnoreCase("Task2") && inputFileAfterMerge != null) {
            Map<String,CVSClient> fullFile = readCSVFileByStringSimple(inputFileAfterMerge);
            List<String> metadata = generateMetadata(E, fullFile);
            List<String[]> allMetadata = generateMetadataArray(metadata);
            writeCSV(allMetadata, task2ResultFileName);
        }else if(taskNumber.equalsIgnoreCase("Task1a") && inputFileAfterMerge != null && Additional_CRecord_Data_Task1a != null) {
            Map<String,CVSClient> fullFile = readCSVFileByStringSimple(inputFileAfterMerge);
            Map<String, Additional_CRecord_Data_Task1a> additional_cRecord_data_task1aMap = readExtraAdditionalDataCTask1a(Additional_CRecord_Data_Task1a);
            Map<String,CVSClient> fullFileVersion4_1_Additional_C = generateVersion4AdditionalC(fullFile, additional_cRecord_data_task1aMap);
            reCalculateD2(fullFileVersion4_1_Additional_C, A);
            String[] E = reCalculateE(fullFileVersion4_1_Additional_C, A);
            List<String[]> allData = generateListOfDataArray(A, fullFileVersion4_1_Additional_C, E);
            writeCSV(allData, task1aResultFileName);
        }else if(taskNumber.equalsIgnoreCase("Task3") && inputFileAfterMerge != null && A_Additional_5 != null
                && B_Additional_5 != null && C_Additional_5 != null) {
            Map<String,CVSClient> fullFile = readCSVFileByStringSimple(inputFileAfterMerge);
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

    private static Map<String,CVSClient> generateVersion4AdditionalC(Map<String, CVSClient> fullFile, Map<String, Additional_CRecord_Data_Task1a> C_Additional) {

        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            String key = entry.getKey();
            CVSClient value = entry.getValue();
            String[] B = value.getClientB();
            String clientB = B[1];
            if(C_Additional.get(clientB) != null) {
                List<String[]> clientC = value.getClientsC();
                clientC.add(C_Additional.get(clientB).toArray());
            }
        }
        return fullFile;
    }

    private static Map<String,Additional_CRecord_Data_Task1a> readExtraAdditionalDataCTask1a(String additional_cRecord_data_task1a) throws IOException {
        Map<String,Additional_CRecord_Data_Task1a> C = new LinkedHashMap<>();
        CSVReader reader = new CSVReader(new FileReader(additional_cRecord_data_task1a), '\n', '\'');
        List<String[]> myEntries = reader.readAll();
        for(int i = 1; i < myEntries.size(); i++) {
            String[] curEl = myEntries.get(i)[0].split(";", -1);
            String key = curEl[1];
            Additional_CRecord_Data_Task1a value = new Additional_CRecord_Data_Task1a(curEl[0], curEl[1], curEl[3], curEl[4], curEl[5], curEl[6], curEl[7],
                    curEl[8], curEl[9], curEl[10], curEl[11], curEl[12], curEl[13], curEl[14], curEl[15], curEl[16], curEl[17], curEl[18], curEl[19], curEl[20], curEl[21],
                    curEl[22], curEl[23], curEl[24], curEl[25], curEl[26], curEl[27], curEl[28], curEl[29], curEl[30]);
            C.put(key, value);
        }
        return C;
    }

    static List<String[]> generateListOfDataArray(String[] a, Map<String, CVSClient> fullFile, String[] e) {
        // fullFile.
        List<String[]> fullData = new ArrayList<String[]>();
        fullData.add(a);
        for (CVSClient entry : getSortedListByB1C2A(fullFile)){
            fullData.add(entry.getClientB());
            fullData.addAll(entry.getClientsC());
            fullData.add(entry.getD());
        }
        fullData.add(e);
        return fullData;
    }

    static List<CVSClient> getSortedListByB1C2A (Map<String, CVSClient> data) {
        List<CVSClient> sortedData = new ArrayList<>();
        for (Map.Entry<String, CVSClient> entry : data.entrySet()){
            Collections.sort(entry.getValue().getClientsC(),
                    (new Comparator<String[]>() {
                        @Override
                        public int compare(String[] c1, String[] c2) {
                            return c1[1].compareTo(c2[1]);
                        }
                    }));
            sortedData.add(entry.getValue());
        }
        Collections.sort(sortedData, (new Comparator<CVSClient>() {
            @Override
            public int compare(CVSClient b1, CVSClient b2) {
                return b1.getClientB()[1].compareTo(b2.getClientB()[1]);
            }
        }));
        return  sortedData;
    }

    static void writeCSV(List<String[]> all, String outputFile ) throws IOException {
        File file = new File(outputFile);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        CSVWriter csvWriter = new CSVWriter(writer, '*', CSVWriter.NO_QUOTE_CHARACTER, '\'', "\n");
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
        System.out.println("generateMetadata E " + StringUtils.join(E, " ") );

        for (int i = 4; i < 17; i++) {
            System.out.println("metadata i " + i);
            System.out.println("E[i - 2] " + E[i - 2]);

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
        List<String[]> result = new ArrayList<>();
        result.add(metadataList.toArray(new String[]{}));
        return result;
    }

    static List<String> generateListOfData(String[] A, Map<String, CVSClient> fullFile, String[] e) {
        List<String> fullData = new ArrayList<String>();
        fullData.add(StringUtils.join(A, "*"));
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            fullData.add(StringUtils.join(entry.getValue().getClientB(), "*"));
            fullData.addAll(entry.getValue().listStringClientsC());
            fullData.add(StringUtils.join(entry.getValue().getD(), "*"));
        }
        fullData.add(StringUtils.join(e, "*"));
        return fullData;
    }

    static Map<B,CVSClient> readCSVFileByString(String file) throws IOException {

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
        System.out.println("before E" );
        System.out.println("myEntities size " +myEntries.size());

        if(myEntries.size() > 0) {
            for(int k = myEntries.size()-1; k > 0; k-- ) {
                System.out.println("k " + k + " & myEntries.get(k)[0] " + StringUtils.join(myEntries.get(k), " "));
                if((myEntries.get(k)[0]).startsWith("E")) {
                    System.out.println("found E");
                    E = myEntries.get(k)[0].split("\\*");
                    System.out.println( myEntries.get(k)[0]);
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
                        CValues.add(myEntries.get(j)[0].split("\\*"));
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

    static Map<String,CVSClient> readCSVFileByStringSimple(String file) throws IOException {

        Map<String,CVSClient> clients = new LinkedHashMap<>();

        CSVReader reader = new CSVReader(new FileReader(file), '\n', '\'');
        List<String[]> myEntries = reader.readAll();
        if(A == null) {
            A = myEntries.get(0)[0].split("\\*");
        } else {
            AFile2 = myEntries.get(0)[0].split("\\*");
        }

        if(myEntries.size() > 0) {
            for(int k = myEntries.size()-1; k > myEntries.size() - 10; k-- ) {
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
        }
        return clients;
    }

    static Map<String,CVSClient> generateMapOfData(Map<B, CVSClient> readFile1, Map<B, CVSClient> readFile2) {
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
                clients.addAll(readFile2.get(entry.getKey()).getClientsC());
                resultMap.put(entry.getKey().getBKey(), new CVSClient(entry.getValue().getClientB(), clients, entry.getValue().getD()));
                readFile2.remove(entry.getKey());
            } else if(b2MapUtil.get(entry.getKey()) != null) {
                String[] B = entry.getValue().getClientB();
                B[1] = entry.getKey().getB1();
                CVSClient cvsClient = new CVSClient(B, entry.getValue().getClientsC(), entry.getValue().getD());
                resultMap.put(entry.getKey().getBKey(), cvsClient);
                String[] BFile2 = b2MapUtil.get(entry.getKey()).getClientB();
                BFile2[1] = BFile2[1] + "-" + AFile2[1];
                String B2InRealFile = BFile2[1];
                CVSClient cvsClientFile2 = b2MapUtil.get(entry.getKey());
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
        List<String>  sameBList = sameBList(protocol.getKeyFile1ToFile2());
        List<String>  recalculatedDRecords = new ArrayList<>();
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            String[] clientB = entry.getValue().getClientB();
            CVSClient cvsClientVersion41 = entry.getValue();
            List<String[]> CList = cvsClientVersion41.getClientsC();
            String[] D = cvsClientVersion41.getD();
            if(sameBList.contains(StringUtils.join(clientB, "*"))) {
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
                recalculatedDRecords.add(StringUtils.join(D, "*"));
            }
        }
        protocol.setRecalculatedDRecords(recalculatedDRecords);
        return fullFile;
    }

    static Map<String, CVSClient> reCalculateD2(Map<String, CVSClient> fullFile, String[] A) {
        List<String>  recalculatedDRecords = new ArrayList<>();
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
                recalculatedDRecords.add(StringUtils.join(D, "*"));

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

    private static boolean isYSymbolSet(int pos, String value) {
        if(value.charAt(pos) == 'Y') {
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

                currentC[21] = currentC[21].substring(0,14) +
                        c_extraData.getC21_Pos15() +
                        c_extraData.getC21_Pos16() +
                        c_extraData.getC21_Pos17() +
                        c_extraData.getC21_Pos18() +
                        c_extraData.getC21_Pos19() +
                        currentC[21].substring(19);

                currentC[20] = C20_Version5_1(currentC[20], currentC[21], BVersion5_1[13], BVersion5_1[15]);

                List<String> curClientCList = new LinkedList<>();
                curClientCList.addAll(Arrays.asList(currentC));
                curClientCList.add("");
                curClientCList.add("");
                curClientCList.add(c_extraData.getC24());
                curClientCList.add(c_extraData.getC25());
                curClientCList.add(c_extraData.getC26());
                curClientCList.add(c_extraData.getC27());

                String[] curCVersion5_1 = curClientCList.toArray(new String[]{});
                clientC.set(i,curCVersion5_1);
            }
            fullFileVersion5_1.put(key,value);

        }
        return fullFileVersion5_1;
    }

    private static String C20_Version5_1(String C20, String C21, String B14, String B16) {
        StringBuilder result = new StringBuilder();
        result.append(C20.substring(0,11));

        if(isYSymbolSet(1, C20) && isYSymbolSet(9, C21)) {
            result.append('Y');
        } else {
            result.append('N');
        }

        if(isYSymbolSet(1, B14) && !isYSymbolSet(17, C21) && !B16.equals("20")) {
            result.append('Y');
        } else {
            result.append('N');
        }

        if(isYSymbolSet(4, B14) &&  !isYSymbolSet(17, C21)) {
            result.append('Y');
        } else {
            result.append('N');
        }

        if(isYSymbolSet(5, B14) &&  !isYSymbolSet(17, C21)) {
            result.append('Y');
        } else {
            result.append('N');
        }

        if(isYSymbolSet(9, B14) &&  !isYSymbolSet(17, C21)) {
            result.append('Y');
        } else {
            result.append('N');
        }

        result.append(C20.substring(16));
        return result.toString();
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
            CVSClient cvsClientVersion5_1 = entry.getValue();
            String[] D = cvsClientVersion5_1.getD();
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


            D[3] = calculateD4Version5_1(clientB, CList, createNumValue(D[2]));
            D[9] = calculateD10Version5_1(CList);
            D[10] = calculateD11Version5_1(CList);

            //TODO ?
            // recalculate  D[9] and D[10] ???? (B14 and C20 changed)

            List<String> curDList = new LinkedList<>();

            String D13 = D[12];
            String D14A= D[13];
            String D15 = D[14];

            curDList.addAll(Arrays.asList(Arrays.copyOfRange(D, 0,12)));

            BigDecimal D12B = BigDecimalcalculateD12B(D, AVersion5_1, CList);
            BigDecimal D12C = BigDecimalcalculateD12C(D, AVersion5_1, CList);
            BigDecimal D14B = BigDecimalcalculateD14B(D, CList);

            curDList.add(String.valueOf(D12B));
            curDList.add(String.valueOf(D12C));
            curDList.add(D13);
            curDList.add(D14A);
            curDList.add(String.valueOf(D14B));
            curDList.add(String.valueOf(D15));

            String[] DVersion5_1 = curDList.toArray(new String[]{});

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

    static String calculateD10Version5_1(List<String[]> CList) {

        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C19 = createNumValue(cStr[19]);
            if(cStr[27].equals("10") && C19.signum() == 1) {
                sum  = sum.add(C19);
            }
        }
        return decimalToString(sum);
    }

    private static String calculateD11Version5_1(List<String[]> CList) {

        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C19 = createNumValue(cStr[19]);
            BigDecimal C26 = createNumValue(cStr[26]);

            if(cStr[27].equals("20") && C19.signum() == 1) {
                sum  = sum.add(C19).subtract(C26);
            }
        }
        return decimalToString(sum);
    }
}