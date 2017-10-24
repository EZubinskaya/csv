package comparus.de.tasks;

import com.opencsv.CSVReader;
import comparus.de.domen.*;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.map.DefaultedMap;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static comparus.de.tasks.Task1.*;
import static comparus.de.tasks.Task3.*;
import static comparus.de.util.Util.createNumValue;
import static comparus.de.util.Util.decimalToString;

/**
 * Created by ekaterina on 10/12/17.
 */
public class Task4A {

    static Set<String> B_List = new HashSet<>();
    public static List<String> additional_cRecord_Withought_C_Extra_Data = new ArrayList<>();
    public static Map<String, CVSClient> clients_not_have_additional_data = new LinkedHashMap<>();
    public static List<String> mergingCRecords = new ArrayList<>();
    public static Map<String, List<String[]>> full_C_Record = new HashMap<>();

    public static Map<String, List<Additional_A_C_Record>> readExtraAdditionalDataCTask4a(String additional_cRecord_data_task1a) throws IOException {
        Map<String, List<Additional_A_C_Record>> C =  DefaultedMap.decorate(new TreeMap<>(
                new Comparator<String>(){
            @Override
            public int compare(String  o1, String o2) {
                return getFirstPart(o1).compareTo(getFirstPart(o2));
            }
        }),new Transformer() {
            @Override
            public List<Additional_A_C_Record> transform(Object o) {
                return new ArrayList<>();
            }
        });
        CSVReader reader = new CSVReader(new FileReader(additional_cRecord_data_task1a), '\n', '|');
        List<String[]> myEntries = reader.readAll();
        for(int i = 1; i < myEntries.size(); i++) {
            //starts with A2
            String[] curEl = myEntries.get(i)[0].split(";", -1);
            String key = curEl[2];
            if(curEl[3].startsWith("GEMKD") && curEl[29].equals("J")) {
                specialCRecords(C, curEl);
            } else {
                Additional_A_C_Record valueRecord = new Additional_A_C_Record(curEl[0], curEl[1], curEl[2], curEl[3], curEl[4], curEl[5], curEl[6], curEl[7],
                        curEl[8], curEl[9], curEl[10], curEl[11], curEl[12], curEl[13], curEl[14], curEl[15], curEl[16], curEl[17], curEl[18], curEl[19], curEl[20], curEl[21],
                        curEl[22], curEl[23], curEl[24], curEl[25], curEl[26], curEl[27], curEl[28]);
                valueRecord.setC2B(curEl[3] + "-" + curEl[0]);
                List<Additional_A_C_Record> records = C.get(key);
                records.add(valueRecord);
                C.put(key, records);
            }
        }
        return C;
    }

    static void specialCRecords(Map<String,List<Additional_A_C_Record>> C, String[] curEl) {
        Additional_A_C_Record valueZusatz002 = new Additional_A_C_Record(curEl[0], curEl[1], curEl[2], curEl[3], curEl[4], curEl[5], curEl[6], curEl[7],
                curEl[8], curEl[9], curEl[10], curEl[11], curEl[12], curEl[13], curEl[14], curEl[15], curEl[16], curEl[17], curEl[18], curEl[19], curEl[20], curEl[21],
                curEl[22], curEl[23], curEl[24], curEl[25], curEl[26], curEl[27], curEl[28]);
        Additional_A_C_Record valueZusatz003 = new Additional_A_C_Record(curEl[0], curEl[1], curEl[2], curEl[3], curEl[4], curEl[5], curEl[6], curEl[7],
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

        //TODO GEMKD1000009068-GEMKD2329514-1002453340 ???
        String newC2B_Zusatz002 = "GEMKD" + curEl[30] + "-" + curEl[3];
        String newC2B_Zusatz003 = "GEMKD" + curEl[31] + "-" + curEl[3];

        valueZusatz002.setC2B(newC2B_Zusatz002 + "-" + curEl[0]);
        valueZusatz003.setC2B(newC2B_Zusatz003 + "-" + curEl[0]);

        List<Additional_A_C_Record> records = C.get(valueZusatz002.getC2A());
        records.add(valueZusatz002);
        C.put(valueZusatz002.getC2A(), records);
        records = C.get(valueZusatz003.getC2A());
        records.add(valueZusatz003);
        C.put(valueZusatz003.getC2A(), records);
    }

    public static void sortDataForAddingNewCRecord (Map<String, List<Additional_A_C_Record>> additional_cRecord , Map<String, C_ExtraData> C_ExtraData_1,
                                                                                  Map<String, C_ExtraData> C_ExtraData_2,
                                                                                  Map<String,CVSClient> fullFile) {
        for(Iterator<Map.Entry<String, CVSClient>> iterator = fullFile.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, CVSClient> element = iterator.next();
            String[] B = element.getValue().getClientB();
            //List<String[]> C = element.getValue().getClientsC();
            String B2 = B[1];
            //optimisation to avoid nested loop
            List<Additional_A_C_Record> additional_a_c_records = additional_cRecord.get(B2);

            if (additional_a_c_records.size() > 0) {
                for (Additional_A_C_Record additional_a_c_record : additional_a_c_records) {
                    String A2 = additional_a_c_record.getA2();
                    String C2A = additional_a_c_record.getC2A();
                    String C2B = getFirstPart(additional_a_c_record.getC2B());

                    if(C_ExtraData_1.get(C2B) != null && C_ExtraData_1.get(C2B).getA2().compareTo(createNumValue(A2)) == 0){
                        fillAndAddAdditionalCRecord(C_ExtraData_1.get(C2B), C2A , additional_a_c_record, B);
                    } else if (C_ExtraData_2.get(C2B) != null && C_ExtraData_2.get(C2B).getA2().compareTo(createNumValue(A2)) == 0) {
                        fillAndAddAdditionalCRecord(C_ExtraData_2.get(C2B), C2A , additional_a_c_record, B);
                    } else {
                        additional_cRecord_Withought_C_Extra_Data.add(additional_a_c_record.toString());
                    }
                }
            }

        }
    }

    public static void fillAndAddAdditionalCRecord(C_ExtraData c_extraData, String C2A, Additional_A_C_Record entry_c, String[] clientB) {
        String[] entry_cArr = entry_c.toArray();
        entry_cArr[21] = entry_cArr[21].substring(0,14) +
            c_extraData.getC21_Pos15() +
            c_extraData.getC21_Pos16() +
            c_extraData.getC21_Pos17() +
            c_extraData.getC21_Pos18() +
            c_extraData.getC21_Pos19() +
            entry_cArr[21].substring(19);
        entry_cArr[20] = C20_Version5_1(entry_cArr[20], entry_cArr[21], clientB[13], clientB[15]);
        List<String> curClientCList = new LinkedList<>();
        curClientCList.addAll(Arrays.asList(entry_cArr));
        curClientCList.add(c_extraData.getC24());
        curClientCList.add(c_extraData.getC25());
        curClientCList.add(c_extraData.getC26());
        curClientCList.add(calculateC27(entry_cArr[20], clientB[13]));

        if(full_C_Record.get(C2A) != null) {
            List<String[]> C_record = full_C_Record.get(C2A);
            C_record.add(curClientCList.toArray(new String[]{}));
        } else {
            String[] C_record = curClientCList.toArray(new String[]{});
            List<String []> C_record_list = new ArrayList<>();
            C_record_list.add(C_record);
            full_C_Record.put(C2A, C_record_list);
        }
    }


    public static Map<String,CVSClient> generateVersion5_Task4A(Map<String, CVSClient> fullFile, Map<String, List<String[]>> full_C_Record) {
        Map<String,CVSClient>  fullFileVersion5_1 = new LinkedHashMap<>();
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            String[] B = entry.getValue().getClientB();
            String key = entry.getKey();
            CVSClient value = entry.getValue();

            String  bKey = getFirstPart(B[1]);

            //TODO дублируются хначения??
            if(full_C_Record.get(bKey) != null) {
                B_List.add(key);
                value.getClientsC().addAll(full_C_Record.get(bKey));
                for (String [] el : full_C_Record.get(bKey)) {
                    mergingCRecords.add(StringUtils.join(el,"*"));
                }
            }
            fullFileVersion5_1.put(key,value);
        }

        return fullFileVersion5_1;
    }

    public static void reCalculateDVersion5_1(Map<String, CVSClient> fullFileVersion5_1, String[] AVersion5_1) {
        for (Map.Entry<String, CVSClient> entry : fullFileVersion5_1.entrySet()) {

            if (B_List.contains(entry.getKey())) {
                HW hw = new HW();

                String[] clientB = entry.getValue().getClientB();
                CVSClient cvsClientVersion = entry.getValue();
                List<String[]> CList = cvsClientVersion.getClientsC();
                String[] D = cvsClientVersion.getD();
                String[] tempD = new String[18];
                D[2] = calculateD3(CList);
                D[3] = calculateD4Version5_1(clientB, CList, createNumValue(D[2]));
                D[4] = calculateD5D7(createNumValue(D[2]), createNumValue(D[3]));
                D[5] = calculateD6(createNumValue(AVersion5_1[3]), createNumValue(D[4]), CList);
                D[6] = calculateD5D7(createNumValue(D[4]), createNumValue(D[5]));
                D[7] = "0,00";
                D[8] = "0,00";

                System.arraycopy(D, 0, tempD, 0, D.length);

                D[9] = calculateD10Version5_1(CList);
                tempD[9] = D[9];

                D[10] = calculateD11Version5_1(CList);
                tempD[10] = D[10];

                String D15 = D[14];
                tempD[17] = D15;

                BigDecimal D14A = calculateD14AVersion5_1(CList);
                tempD[15] = decimalToString(D14A);

                BigDecimal D12A = calculateD12AVersion5_1(AVersion5_1[5], tempD, CList);
                tempD[11] = decimalToString(D12A);

                BigDecimal D12B = BigDecimalcalculateD12B(tempD, AVersion5_1, CList, hw);
                tempD[12] = decimalToString(D12B);

                BigDecimal D12C = BigDecimalcalculateD12C(tempD, AVersion5_1, CList, hw);
                tempD[13] = decimalToString(D12C);

                BigDecimal D13 = calculateD13Version5_1(D12A, D12B, D12C);
                tempD[14] = decimalToString(D13);

                BigDecimal D14B = BigDecimalcalculateD14B(tempD);
                tempD[16] = decimalToString(D14B);
                D_HW.put(StringUtils.join(tempD, "*"), hw);
                cvsClientVersion.setD(tempD);
            }
        }
    }

    private static  String getFirstPart(String key) {
        if(key.contains("-")) {
            String fixedKey = key.split("-")[0];
            return fixedKey;
        }
        return key;
    }

}
