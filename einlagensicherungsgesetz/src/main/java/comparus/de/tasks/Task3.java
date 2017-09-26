package comparus.de.tasks;

import com.opencsv.CSVReader;
import comparus.de.domen.B_ExtraData;
import comparus.de.domen.CVSClient;
import comparus.de.domen.C_ExtraData;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static comparus.de.CSVReaderMain.*;
import static comparus.de.util.Util.createNumValue;
import static comparus.de.util.Util.decimalToString;

/**
 * Created by ekaterina on 9/26/17.
 */
public class Task3 {
    private static BigDecimal calculateHW1(String[] D) {
        BigDecimal sumHW1 = BigDecimal.ZERO;
        BigDecimal maxD6D9 = createNumValue(D[5]).max(createNumValue(D[8]));
        BigDecimal maxD6D9D11 = maxD6D9.max(createNumValue(D[10]));
        sumHW1= createNumValue(D[2]).subtract(createNumValue(D[9])).subtract(createNumValue(D[15])).subtract(maxD6D9D11);
        return sumHW1;
    }

    private static BigDecimal calculateHW2(List<String[]> CList) {
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

    private static BigDecimal calculateHW3(List<String[]> CList) {
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

    private static BigDecimal calculateHW5(List<String[]> CList) {
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

    static BigDecimal BigDecimalcalculateD14B(String[] D) {
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

    public static Map<String,C_ExtraData> readExtraDataC(String C_Additional_5) throws IOException {
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

    public static String[] geeratedAVersion5_1(String[] A, Map<String, String> A_Additional) {
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

    public static Map<String,CVSClient> generateVersion5 (Map<String,CVSClient> fullFile, Map<String,B_ExtraData> B_Additional, Map<String,C_ExtraData> C_Additional) {

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

        result.append(C20.substring(16,20));
        String C20pos21_24 = "NNNN";
        result.append(C20pos21_24);
        result.append(C20.substring(24,30));
        if(isYSymbolSet(22, C20) ||  isYSymbolSet(23, C20)) {
            result.append('Y');
        } else {
            result.append('N');
        }
        String C20pos32_39 = "NNNNNNNN";
        result.append(C20pos32_39);
        result.append(C20.substring(39));
        return result.toString();
    }

    private static boolean isYSymbolSet(int pos, String value) {
        if(value.charAt(pos) == 'Y') {
            return true;
        }
        return false;
    }

    public static String[] reCalculateEVersion5_1(Map<String, CVSClient> fullFileVersion5_1) {
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

    public static void reCalculateDVersion5_1(Map<String, CVSClient> fullFileVersion5_1, String[] AVersion5_1) {
        for (Map.Entry<String, CVSClient> entry : fullFileVersion5_1.entrySet()){
            String[] clientB = entry.getValue().getClientB();
            CVSClient cvsClientVersion= entry.getValue();
            List<String[]> CList = cvsClientVersion.getClientsC();
            String[] D = cvsClientVersion.getD();
            String[] tempD = new String[18];
            System.arraycopy(D, 0, tempD, 0, D.length);

            D[3] = calculateD4Version5_1(clientB, CList, createNumValue(D[2]));
            tempD[3] = D[3];

            D[9] = calculateD10Version5_1(CList);
            tempD[9] = D[9];

            D[10] = calculateD11Version5_1(CList);
            tempD[10] = D[10];

//            List<String> curDList = new LinkedList<>();
            String D15 = D[14];
            tempD[17] = D15;

//            curDList.addAll(Arrays.asList(Arrays.copyOfRange(D, 0,12)));

            // recalculate  D[9] and D[10] ???? (B14 and C20 changed)
            BigDecimal D14A = calculateD14AVersion5_1(CList);
            tempD[15] = String.valueOf(D14A);

            BigDecimal D12A = calculateD12AVersion5_1(AVersion5_1[5], tempD, CList);
            tempD[11] = String.valueOf(D12A);

            BigDecimal D12B = BigDecimalcalculateD12B(tempD, AVersion5_1, CList);
            tempD[12] = String.valueOf(D12B);

            BigDecimal D12C = BigDecimalcalculateD12C(tempD, AVersion5_1, CList);
            tempD[13] = String.valueOf(D12C);

            BigDecimal D13 = calculateD13Version5_1(D12A, D12B, D12C);
            tempD[14] = String.valueOf(D13);

            BigDecimal D14B = BigDecimalcalculateD14B(tempD);
            tempD[16] = String.valueOf(D14B);

//            curDList.add(String.valueOf(D12A));
//            curDList.add(String.valueOf(D12B));
//            curDList.add(String.valueOf(D12C));
//            curDList.add(String.valueOf(D13));
//            curDList.add(String.valueOf(D14A));
//            curDList.add(String.valueOf(D14B));
//            curDList.add(String.valueOf(D15));

//            String[] DVersion5_1 = curDList.toArray(new String[]{});

            cvsClientVersion.setD(tempD);
        }

    }

    static String calculateD4Version5_1(String[] clientB, List<String[]> CList, BigDecimal D3) {

        if(Task1.specificPositionSymbol(0,19,clientB[13])) {
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
        if(Task1.compare(D3, 20) == -1 && Task1.C20AllStartWithY(CList)) {
            return decimalToString(D3);
        }

        if(Task1.C2C20ContainsYin1To20pos(CList)) {
            return Task1.calculateositiveC19ForAllContainsC2C20Y(CList);
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

    static String calculateD11Version5_1(List<String[]> CList) {

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

    static BigDecimal calculateD12AVersion5_1(String A6, String[] D, List<String[]> CList) {
        BigDecimal HW1 = calculateHW1(D);
        BigDecimal result = BigDecimal.ZERO;

        for (String[] cStr: CList) {
            BigDecimal C5 = createNumValue(cStr[5]);
            BigDecimal A6C5 = C5.multiply(createNumValue(A6));
            result = A6C5;
            if(HW1.compareTo(A6C5) == -1) {
                result = HW1;
            }
        }
        result = result.subtract(createNumValue(D[5])).add(createNumValue(D[8]));

        if(result.signum() == -1) {
            result = BigDecimal.ZERO;
        }
        return (result);
    }

    static BigDecimal calculateD13Version5_1(BigDecimal D12A, BigDecimal D12B, BigDecimal D12C) {
        return (D12A.add(D12B).add(D12C));
    }

    static BigDecimal calculateD14AVersion5_1(List<String[]> CList) {
        BigDecimal result = BigDecimal.ZERO;
        for (String[] cur: CList) {
            if(createNumValue(cur[19]).signum() == -1) {
                result.add(createNumValue(cur[19]));
            }
        }
        return result;
    }

}
