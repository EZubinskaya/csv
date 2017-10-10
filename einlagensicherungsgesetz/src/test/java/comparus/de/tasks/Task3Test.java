package comparus.de.tasks;

import com.opencsv.CSVReader;
import comparus.de.domen.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static comparus.de.tasks.Task3.*;
import static org.junit.Assert.*;

/**
 * Created by ekaterina on 10/4/17.
 */
public class Task3Test {
    static String file1 = "src/test/resources/example1.csv";
    static String file2 = "src/test/resources/example2.csv";
    static String file3 = "src/test/resources/example3.csv";
    static String[] AVersion5 = null;

    //Version 5.1
    private Map<String,CVSClient> getClientVersion5_1 () throws IOException {
        String A_Additional_5 = "src/test/resources/extraData/A_Additional_5.0.csv";
        String B_Additional_5 = "src/test/resources/extraData/B_Additional_5.0.csv";
        String C_Additional_5 = "src/test/resources/extraData/C_Additional_5.0.csv";

        String file = "src/test/resources/exampleExtraData.csv";
        List<String> inputDataFile = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file), '\n', '\'');
        List<String[]>  inputDataArrayFile = reader.readAll();
        for(String[] el : inputDataArrayFile) {
            inputDataFile.add(el[0]);
        }

        Map<B,CVSClient> readFile1 = Task1.readCSVFileByString(file, false);
        Map<B,CVSClient> readFile2 = new HashMap<>();
        Map<String,CVSClient> fullFile = Task1.generateMapOfData(readFile1, readFile2);
        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
        Task1.reCalculateD(fullFile, A, false);
        String[] E = Task1.reCalculateE(fullFile, A);

        Map<String, String> A_ExtraData = readExtraDataA(A_Additional_5);
        Map<String, B_ExtraData> B_ExtraData = readExtraDataB(B_Additional_5);
        Map<String, C_ExtraData> C_ExtraData = readExtraDataC(C_Additional_5);
        String[] AVersion5_1 = geeratedAVersion5_1 (A,  A_ExtraData);
        AVersion5 = AVersion5_1;

        Map<String,CVSClient> fullFileVersion5_1 = generateVersion5(fullFile,  B_ExtraData, C_ExtraData);
        reCalculateDVersion5_1(fullFileVersion5_1, AVersion5_1);
        return fullFileVersion5_1;
    }

    @Test
    public void D12Test() throws IOException {
        HW hw = new HW();
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        String[] clientB = fullFileVersion5_1.get("5615").getClientB();
        List<String[]> CList = fullFileVersion5_1.get("5615").getClientsC();
        String[] D = fullFileVersion5_1.get("5615").getD();
        List<String> curDList = new LinkedList<>();
        curDList.addAll(Arrays.asList(D));
        BigDecimal D12B = BigDecimalcalculateD12B(D, AVersion5, CList, hw);
        System.out.println(D12B);
        Assert.assertEquals(D12B,BigDecimal.ZERO);

    }

    @Test
    public void D12Test2() throws IOException {
        HW hw = new HW();
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        String[] clientB = fullFileVersion5_1.get("5615").getClientB();
        List<String[]> CList = fullFileVersion5_1.get("5615").getClientsC();
        String[] D = fullFileVersion5_1.get("5615").getD();
        List<String> curDList = new LinkedList<>();
        curDList.addAll(Arrays.asList(D));
        BigDecimal D12B = BigDecimalcalculateD12B(D, AVersion5, CList,hw);
        System.out.println(D12B);
        Assert.assertEquals(D12B,BigDecimal.ZERO);

    }

    @Test
    public void D12CTest1() throws IOException {
        HW hw = new HW();
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        String[] clientB = fullFileVersion5_1.get("5615").getClientB();
        List<String[]> CList = fullFileVersion5_1.get("5615").getClientsC();
        String[] D = fullFileVersion5_1.get("5615").getD();

        BigDecimal D12C =BigDecimalcalculateD12C(D, AVersion5, CList, hw);

        System.out.println(D12C);
        Assert.assertEquals(D12C,BigDecimal.ZERO);

    }

    @Test
    public void D12CTest2() throws IOException {
        HW hw = new HW();
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        String[] clientB = fullFileVersion5_1.get("5615").getClientB();
        List<String[]> CList = fullFileVersion5_1.get("5615").getClientsC();
        String[] D = fullFileVersion5_1.get("5615").getD();

        BigDecimal D12C =BigDecimalcalculateD12C(D, AVersion5, CList, hw);

        System.out.println(D12C);
        Assert.assertEquals(D12C,new BigDecimal("0"));

    }

    @Test
    public void reCalculateDTest() throws IOException {
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        String[] clientB = fullFileVersion5_1.get("5615").getClientB();
        List<String[]> CList = fullFileVersion5_1.get("5615").getClientsC();
        String[] D = fullFileVersion5_1.get("5615").getD();
        Assert.assertEquals(D[0], "D");
        Assert.assertEquals(D[1], "10534");
        Assert.assertEquals(D[2], "100001,62");
        Assert.assertEquals(D[3], "0,00");
        Assert.assertEquals(D[4], "100001,62");
        Assert.assertEquals(D[5], "100000");
        Assert.assertEquals(D[6], "1,62");
        Assert.assertEquals(D[7], "0");
        Assert.assertEquals(D[8], "0");
        Assert.assertEquals(D[9], "100001,62");
        Assert.assertEquals(D[10], "0,00");
        Assert.assertEquals(D[11], "0");
        Assert.assertEquals(D[12], "0");
        Assert.assertEquals(D[13], "0");
        Assert.assertEquals(D[14], "0");
        Assert.assertEquals(D[15], "0");
        Assert.assertEquals(D[16], "-100000.00");
        Assert.assertEquals(D[17], "0");
    }

    @Test
    public void reCalculateeTest() throws IOException {
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        String[] E = reCalculateEVersion5_1(fullFileVersion5_1);
        Assert.assertEquals(E[0], "E");
        Assert.assertEquals(E[2], "300004,86");
        Assert.assertEquals(E[3], "100001,62");
        Assert.assertEquals(E[4], "-100001,62");
        Assert.assertEquals(E[5], "300000");
        Assert.assertEquals(E[6], "4,86");
        Assert.assertEquals(E[7], "0,00");
        Assert.assertEquals(E[8], "0,00");
        Assert.assertEquals(E[9], "100001,62");
        Assert.assertEquals(E[10], "99992,62");
        Assert.assertEquals(E[11], "0,00");
        Assert.assertEquals(E[12], "0,00");
        Assert.assertEquals(E[13], "-10700000");
        Assert.assertEquals(E[14], "-10700000");
        Assert.assertEquals(E[15], "0,00");
        Assert.assertEquals(E[16], "-10800003,24");
        Assert.assertEquals(E[17], "0,00");
    }

    @Test
    public void calculateHW5Test1() throws IOException {
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        List<String[]> CList = fullFileVersion5_1.get("5615").getClientsC();
        BigDecimal sumHW5 = calculateHW5(CList);
        Assert.assertEquals(sumHW5, BigDecimal.ZERO);
    }


    @Test
    public void calculateHW5Test2() throws IOException {
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        List<String[]> CList = fullFileVersion5_1.get("5615").getClientsC();
        BigDecimal sumHW5 = calculateHW5(CList);
        Assert.assertEquals(sumHW5, BigDecimal.ZERO);
    }

    @Test
    public void calculateHW3Test1() throws IOException {
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        List<String[]> CList = fullFileVersion5_1.get("5615").getClientsC();
        BigDecimal sumHW3 = calculateHW3(CList);
        Assert.assertEquals(sumHW3, BigDecimal.ZERO);
    }

    @Test
    public void calculateHW3Test2() throws IOException {
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        List<String[]> CList = fullFileVersion5_1.get("5615").getClientsC();
        BigDecimal sumHW3 = calculateHW3(CList);
        Assert.assertEquals(sumHW3, BigDecimal.ZERO);
    }

    @Test
    public void calculateHW2Test1() throws IOException {
        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
        List<String[]> CList = fullFileVersion5_1.get("5615").getClientsC();
        BigDecimal sumHW2 = calculateHW2(CList);
        Assert.assertEquals(sumHW2, new BigDecimal("0"));
    }
}