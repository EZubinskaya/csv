package comparus.de.tasks;

import com.opencsv.CSVReader;
import comparus.de.domen.Additional_CRecord;
import comparus.de.domen.CVSClient;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static comparus.de.CSVReaderMain.A;
import static comparus.de.CSVReaderMain.protocol;
import static comparus.de.tasks.Task1.*;
import static comparus.de.util.Util.createNumValue;
import static comparus.de.util.Util.decimalToString;


/**
 * Created by ekaterina on 9/26/17.
 */
public class Task1a {
    public static Set<Additional_CRecord> C_Additional_Not_Import = new HashSet<>();
    public static Set<Additional_CRecord> C_Additional_Import = new HashSet<>();

    public static Map<String,Additional_CRecord> readExtraAdditionalDataCTask1a(String additional_cRecord_data_task1a) throws IOException {
        Map<String,Additional_CRecord> C = new LinkedHashMap<>();
        CSVReader reader = new CSVReader(new FileReader(additional_cRecord_data_task1a), '\n', '|');
        List<String[]> myEntries = reader.readAll();
        for(int i = 1; i < myEntries.size(); i++) {
            //starts with A2
            String[] curEl = myEntries.get(i)[0].split(";", -1);
            String key = curEl[2] + curEl[3];
            if(curEl[3].startsWith("GEMKD") && curEl[29].equals("J")) {
                specialCRecords(C, curEl);
            } else {
                Additional_CRecord valueRecord = new Additional_CRecord(curEl[1], curEl[2], curEl[3], curEl[4], curEl[5], curEl[6], curEl[7],
                        curEl[8], curEl[9], curEl[10], curEl[11], curEl[12], curEl[13], curEl[14], curEl[15], curEl[16], curEl[17], curEl[18], curEl[19], curEl[20], curEl[21],
                        curEl[22], curEl[23], curEl[24], curEl[25], curEl[26], curEl[27], curEl[28]);
                valueRecord.setC2B(curEl[3] + "-" + curEl[0]);
                C.put(key, valueRecord);
            }
        }
        return C;
    }

    public static Map<String,CVSClient> generateVersion4AdditionalC(Map<String, CVSClient> fullFile, Map<String, Additional_CRecord> C_Additional) {

        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            CVSClient value = entry.getValue();
            String[] B = value.getClientB();
            String clientB = B[1];
            for(Iterator<Map.Entry<String, Additional_CRecord>> it = C_Additional.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, Additional_CRecord> entry_c = it.next();
                String C2A = entry_c.getValue().getC2A();
                if(C2A.equals(clientB)) {
                    List<String[]> clientC = value.getClientsC();
                    clientC.add(entry_c.getValue().toArray());
                    C_Additional_Import.add(entry_c.getValue());
                    it.remove();
                }
            }
        }
        C_Additional_Not_Import.addAll(C_Additional.values());
        return fullFile;
    }

    public static List<String[]> generateListOfDataArray(String[] a, Map<String, CVSClient> fullFile, String[] e) {
        // fullFile.
        if(fullFile.size() > 0) {
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
        return new ArrayList<String[]>();
    }

    public static List<String[]> generateListOfDataArrayNotSorted(String[] a, Map<String, CVSClient> fullFile, String[] e) {
        // fullFile.
        List<String[]> fullData = new ArrayList<String[]>();
        fullData.add(a);
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            CVSClient cvsClient = entry.getValue();
            fullData.add(cvsClient.getClientB());
            fullData.addAll(cvsClient.getClientsC());
            fullData.add(cvsClient.getD());
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

    static void specialCRecords(Map<String,Additional_CRecord> C, String[] curEl) {
        Additional_CRecord valueZusatz002 = new Additional_CRecord(curEl[1], curEl[2], curEl[3], curEl[4], curEl[5], curEl[6], curEl[7],
                curEl[8], curEl[9], curEl[10], curEl[11], curEl[12], curEl[13], curEl[14], curEl[15], curEl[16], curEl[17], curEl[18], curEl[19], curEl[20], curEl[21],
                curEl[22], curEl[23], curEl[24], curEl[25], curEl[26], curEl[27], curEl[28]);
        Additional_CRecord valueZusatz003 = new Additional_CRecord(curEl[1], curEl[2], curEl[3], curEl[4], curEl[5], curEl[6], curEl[7],
                curEl[8], curEl[9], curEl[10], curEl[11], curEl[12], curEl[13], curEl[14], curEl[15], curEl[16], curEl[17], curEl[18], curEl[19], curEl[20], curEl[21],
                curEl[22], curEl[23], curEl[24], curEl[25], curEl[26], curEl[27], curEl[28]);

        valueZusatz002.setC2A(curEl[30]);
        valueZusatz003.setC2A(curEl[31]);

        BigDecimal two = new BigDecimal("2");
        valueZusatz002.setC9(decimalToString(createNumValue(curEl[10]).divide(two)));
        valueZusatz003.setC9(decimalToString(createNumValue(curEl[10]).divide(two)));

        valueZusatz002.setC11(decimalToString(createNumValue(curEl[12]).divide(two)));
        valueZusatz003.setC11(decimalToString(createNumValue(curEl[12]).divide(two)));

        valueZusatz002.setC17(decimalToString(createNumValue(curEl[18]).divide(two)));
        valueZusatz003.setC17(decimalToString(createNumValue(curEl[18]).divide(two)));

        valueZusatz002.setC18(decimalToString(createNumValue(curEl[19]).divide(two)));
        valueZusatz003.setC18(decimalToString(createNumValue(curEl[19]).divide(two)));

        valueZusatz002.setC19(decimalToString(createNumValue(curEl[20]).divide(two)));
        valueZusatz003.setC19(decimalToString(createNumValue(curEl[20]).divide(two)));

        String newC2B_Zusatz002 = "GEMKD" + curEl[30] + "-" + curEl[3];
        String newC2B_Zusatz003 = "GEMKD" + curEl[31] + "-" + curEl[3];

        valueZusatz002.setC2B(newC2B_Zusatz002 + "-" + curEl[0]);
        valueZusatz003.setC2B(newC2B_Zusatz003 + "-" + curEl[0]);

        C.put(valueZusatz002.getC2A() + valueZusatz002.getC2B(), valueZusatz002);
        C.put(valueZusatz003.getC2A() + valueZusatz003.getC2B(), valueZusatz003);
    }

    public static void reCalculateD_task1A(Map<String, CVSClient> fullFile, String[] a, List<String> b) {
        List<String>  recalculatedDRecords = new ArrayList<>();
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            String[] clientB = entry.getValue().getClientB();
            CVSClient cvsClientVersion41 = entry.getValue();
            List<String[]> CList = cvsClientVersion41.getClientsC();
            String[] D = cvsClientVersion41.getD();
            if(b.contains(clientB[1])) {
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
        protocol.setRecalculatedDRecords(recalculatedDRecords);
    }

}