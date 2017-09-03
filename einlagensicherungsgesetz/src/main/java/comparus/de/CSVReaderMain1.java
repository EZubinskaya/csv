package comparus.de;

import com.opencsv.CSVReader;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ekaterina on 8/31/17.
 */
public class CSVReaderMain1 {

    private static String[] A = null;
    public static void main(String[] args) throws IOException {

        String file1 = "einlagensicherungsgesetz/src/main/resources/Pseudodatei aufbereitet B11.csv";
        String file2 = "einlagensicherungsgesetz/src/main/resources/example.csv";
        String file3 = "einlagensicherungsgesetz/src/main/resources/example.csv";

        Map<String,CVSClient> readFile1 = readCSVFileByString(file1);
        Map<String,CVSClient> readFile2 = readCSVFileByString(file2);

        Map<String,CVSClient> fullFile = generateMapOfData(readFile1, readFile2);
        reCalculateD(fullFile, A);//TODO
        String[] E = reCalculateE(fullFile);
        List<String> all = generateListOfData(A, fullFile, E);
        System.out.println(all.stream().collect(Collectors.joining("\n")));
        List<String> metadata = generateMetadata(file3, E, fullFile);
        System.out.println(metadata);
    }

    private static List<String> generateMetadata(String file, String[] E, Map<String,CVSClient> fullFile) throws IOException {
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

    private static List<String> generateListOfData(String[] a, Map<String, CVSClient> fullFile, String[] e) {
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

    private static Map<String,CVSClient> readCSVFileByString(String file) throws IOException {

        Map<String,CVSClient> clients = new HashMap<String, CVSClient>();
        CSVReader reader = new CSVReader(new FileReader(file), '\n', '\'');
        List<String[]> myEntries = reader.readAll();
        if(A == null) {
            A = myEntries.get(0)[0].split("\\*");
        }
        int j = 1;
        for (int i = j ;i<myEntries.size(); i++) {
            String currentEllement = myEntries.get(i)[0];
            if(currentEllement.startsWith("B")) {
                String[] bValue = currentEllement.split("\\*");

                // UNIQUE KEY - B3 - Last Name, B4 - First Name and B12 - Birthday
                String key = bValue[2] + bValue[3] + bValue[11];

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
                clients.put(key, new CVSClient(bValue, CValues, DValue));
            }
        }
        return clients;
    }

    private static Map<String,CVSClient> generateMapOfData(Map<String, CVSClient> readFile1, Map<String, CVSClient> readFile2) {
        Map<String,CVSClient> resultMap = new HashMap<String, CVSClient>();
        for (Map.Entry<String, CVSClient> entry : readFile1.entrySet()){
            if (readFile2.get(entry.getKey()) != null) {
                List<String[]> clients = new ArrayList<String[]>();
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

    private static String[] reCalculateE(Map<String, CVSClient> fullFile) {
        String[] E = new String[15];
        E[0] = "E";
        E[1] = A[1];
        BigDecimal[] eVar = new BigDecimal[15];
        for (int i = 0; i<eVar.length; i++) {
			eVar[i] = BigDecimal.ZERO;
        }
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            CVSClient cvsClient = entry.getValue();
            String[] D = cvsClient.getD();
            for(int i=2;i<eVar.length;i++){ // use eVar array length !!!
				eVar[i] = eVar[i].add(createNumValue(D[i]));
            }
        }
        for(int i = 2; i<eVar.length; i++) {
            E[i] = decimalToString(eVar[i]);
        }
        return E;
    }

    private static Map<String, CVSClient> reCalculateD(Map<String, CVSClient> fullFile, String[] A) {
        for (Map.Entry<String, CVSClient> entry : fullFile.entrySet()){
            String[] clientB = entry.getValue().getClientB();
            CVSClient cvsClient = entry.getValue();
            List<String[]> CList = cvsClient.getClientsC();
            String[] D = cvsClient.getD();
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

    //INDEXES IN ARRAY START FROM 0!  19 -> arr[18]
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
        if(clientB[13].contains("Y")) {
            BigDecimal sumD4 = BigDecimal.ZERO;
            for (String[] elStr : CList) {
                BigDecimal el = createNumValue(elStr[19]);
                if (el.signum() == 1)/*positive*/ {
                    sumD4 = sumD4.add(el);
                }
            }
            return decimalToString(sumD4);
        }

        //not 2000 !!
        //COMPARING BIGDECIMALS - -1 less, 0 - eq, 1 - more
        if(compare(D3, 20) == -1 && C20AllStartWithY(CList)) {
            decimalToString(D3);
        }

        if(C20ContainsYin1To20pos(CList)) {
            return calculateositiveC19ForAllContainsC20Y(CList);
        }

        return decimalToString(BigDecimal.valueOf(0, 2));
    }


    //field C20 not C1!
    private static boolean C20AllStartWithY(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(!elStr[20].startsWith("Y")) {
                return false;
            }
        }
        return true;
    }


    //ONLY FIRST TWENTY POSITIONS!
    private static boolean C20ContainsYin1To20pos(List<String[]> CList) {
        for(String[] elStr : CList) {
            if(elStr[20].substring(0,20).contains("Y")) {
                return true;
            }
        }
        return false;
    }

    private static String calculateositiveC19ForAllContainsC20Y(List<String[]> CList) {
        BigDecimal sum = BigDecimal.ZERO;
        for(String[] cStr : CList) {
            BigDecimal C18 = createNumValue(cStr[19]);
            if((cStr[20].substring(0,20).contains("Y")) && C18.signum() == 1) {
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
        if(value.substring(from, to+1).equals("Y")) {
            return true;
        }
        return false;
    }

    //el[19] - not 20!
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
        BigDecimal D3_D10_D14 = createNumValue(D[2]).subtract(createNumValue(D[9])).subtract(createNumValue(D[13]));
        BigDecimal calc;
        if(D3_D10_D14.compareTo(max) == 1) {
            calc = max;
        } else {
            calc = D3_D10_D14;
        }
        return decimalToString(calc.subtract(createNumValue(D[5])).subtract(createNumValue(D[8])));
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

}
