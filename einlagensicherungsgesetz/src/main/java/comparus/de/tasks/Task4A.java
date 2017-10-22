package comparus.de.tasks;

import comparus.de.domen.*;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

import static comparus.de.CSVReaderMain.protocol;
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


    public static Map<String, Additional_CRecord> sortDataForAddingNewCRecord (Map<String, Additional_CRecord> additional_cRecord , Map<String, C_ExtraData> C_ExtraData ) {
        for(Iterator<Map.Entry<String, Additional_CRecord>> it = additional_cRecord.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Additional_CRecord> entry_c = it.next();
            String C2B = entry_c.getValue().getC2B();
            if(C2B.split("-").length > 0) {
                C2B = C2B.split("-")[0];
            }
            if(C_ExtraData.get(C2B) == null) {
                additional_cRecord_Withought_C_Extra_Data.add(StringUtils.join(entry_c.getValue().toArray(), "*"));
                it.remove();
            }
        }

        return additional_cRecord;
    }

    public static Map<String,CVSClient> generateVersion5 (Map<String,CVSClient> fullFile, Map<String,C_ExtraData> C_Additional) {

        List<KeyFile1ToFile2ToFile3> keyFile1ToFile2ToFile_B_Records = new ArrayList<>();
        protocol.setKeyFile1ToFile2ToFile3_B_Record(keyFile1ToFile2ToFile_B_Records);
        List<KeyFile1ToFile2ToFile3> keyFile1ToFile2ToFile_C_Records = new ArrayList<>();
        protocol.setKeyFile1ToFile2ToFile3_C_Record(keyFile1ToFile2ToFile_C_Records);

        Map<String,CVSClient>  fullFileVersion5_1 = new LinkedHashMap<>();
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()) {
            String key = entry.getKey();
            CVSClient value = entry.getValue();

            //C
            String[] clientB = value.getClientB();
            List<String[]> clientC = value.getClientsC();
            boolean addElement = false;

            for(int i = 0; i < clientC.size(); i++) {
                String[] currentC = clientC.get(i);
                String accountPK =getСprimaryKey(currentC[2]);
                C_ExtraData c_extraData = C_Additional.get(accountPK);

                //TODO
                if(c_extraData != null && clientB.length >= 15) {
                    B_List.add(key);
                    String clientCinitial = StringUtils.join(currentC, "*");
                    if (!currentC[2].endsWith("-" + c_extraData.getA2())) {
                        currentC[2] = currentC[2] + "-" + c_extraData.getA2();
                    }
                    currentC[21] = currentC[21].substring(0,14) +
                            c_extraData.getC21_Pos15() +
                            c_extraData.getC21_Pos16() +
                            c_extraData.getC21_Pos17() +
                            c_extraData.getC21_Pos18() +
                            c_extraData.getC21_Pos19() +
                            currentC[21].substring(19);

                    currentC[20] = C20_Version5_1(currentC[20], currentC[21], clientB[13], clientB[15]);

                    List<String> curClientCList = new LinkedList<>();
                    curClientCList.addAll(Arrays.asList(currentC));
                    curClientCList.add("");
                    curClientCList.add("");
                    curClientCList.add(c_extraData.getC24());
                    curClientCList.add(c_extraData.getC25());
                    curClientCList.add(c_extraData.getC26());
                    curClientCList.add(calculateC27(currentC[20], clientB[13]));

                    String[] curCVersion5_1 = curClientCList.toArray(new String[]{});
                    clientC.set(i,curCVersion5_1);

                    keyFile1ToFile2ToFile_C_Records.add(new KeyFile1ToFile2ToFile3(clientCinitial,
                            c_extraData.getA2() + ";" + c_extraData.getSatz_ID() + ";" + accountPK + ";" + c_extraData.getC21_Pos15() +
                                    ";" + c_extraData.getC21_Pos16() + ";" + c_extraData.getC21_Pos17() + ";" + c_extraData.getC21_Pos18() +
                                    ";" + c_extraData.getC21_Pos19() + ";" + c_extraData.getC24() + ";" + c_extraData.getC25() + ";" + c_extraData.getC26(),
                            StringUtils.join(curCVersion5_1, "*")));

                    //should not remove in case of multiple GEMKD-type C-records
                    addElement = true;
                    mergingCRecords.add(StringUtils.join(curCVersion5_1, "*"));

                }
            }
            if(addElement) {
                fullFileVersion5_1.put(key,value);
            } else {
                clients_not_have_additional_data.put(key, value);
            }
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

    private static String getСprimaryKey(String candidate) {
        if((candidate.startsWith("LST") || candidate.startsWith("GEMKD")) && candidate.contains("-")) {
            String key = candidate.split("-")[1];
            return key;
        }
        if(candidate.contains("-")) {
            String key = candidate.split("-")[0];
            return key;
        }
        return candidate;
    }
}
