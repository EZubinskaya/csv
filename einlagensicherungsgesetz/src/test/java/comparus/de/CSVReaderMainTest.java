//package comparus.de;
//
//import com.opencsv.CSVReader;
//import org.junit.Assert;
//import org.junit.Test;
//import java.io.FileReader;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import static comparus.de.CSVReaderMain.*;
//
///**
// * Created by ekaterina on 9/5/17.
// */
//public class CSVReaderMainTest {
//
//    static String file1 = "src/test/resources/example1.csv";
//    static String file2 = "src/test/resources/example2.csv";
//    static String file3 = "src/test/resources/example3.csv";
//    static String[] AVersion5 = null;
//
//    @Test
//    public void fileTheSameTest() throws IOException {
//        List<String> inputDataFile = new ArrayList<>();
//        CSVReader reader = new CSVReader(new FileReader(file1), '\n', '\'');
//        List<String[]>  inputDataArrayFile = reader.readAll();
//        for(String[] el : inputDataArrayFile) {
//            inputDataFile.add(el[0]);
//        }
//
//        Map<String,CVSClient> readFile1 = CSVReaderMain.readCSVFileByString(file1);
//        Map<String,CVSClient> readFile2 = new HashMap<>();
//        Map<String,CVSClient> fullFile = CSVReaderMain.generateMapOfData(readFile1, readFile2);
//        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
//        CSVReaderMain.reCalculateD(fullFile, A);
//        String[] ESame = CSVReaderMain.reCalculateE(fullFile, A);
//        List<String> allt = CSVReaderMain.generateListOfData(A, fullFile, ESame);
//
//        Assert.assertArrayEquals( allt.toArray(), inputDataFile.toArray() );
//    }
//
//    @Test
//    public void DvalueTest() throws IOException {
//        List<String> inputDataFile = new ArrayList<>();
//        CSVReader reader = new CSVReader(new FileReader(file3), '\n', '\'');
//        List<String[]>  inputDataArrayFile = reader.readAll();
//        for(String[] el : inputDataArrayFile) {
//            inputDataFile.add(el[0]);
//        }
//
//        Map<String,CVSClient> readFile3 = CSVReaderMain.readCSVFileByString(file3);
//        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
//        Map<String,CVSClient> readFile2 = new HashMap<>();
//        Map<String,CVSClient> fullFile = CSVReaderMain.generateMapOfData(readFile3, readFile2);
//        CSVReaderMain.reCalculateD(fullFile, A);
//        String[] E = CSVReaderMain.reCalculateE(fullFile, A);
//        List<String[]> allData = CSVReaderMain.generateListOfDataArray(A, fullFile, E);
//        Assert.assertEquals(allData.get(4)[2],"16317,05");
//        Assert.assertEquals(allData.get(4)[3],"6317,05");
//        Assert.assertEquals(allData.get(8)[3],"17,05");
//        Assert.assertEquals(allData.get(12)[3],"156,05");
//        Assert.assertEquals(allData.get(16)[3],"0");
//        Assert.assertEquals(allData.get(4)[4],"16317,05");
//        Assert.assertEquals(allData.get(4)[5],"0");
//        Assert.assertEquals(allData.get(4)[6],"0");
//        Assert.assertEquals(allData.get(4)[7],"0");
//        Assert.assertEquals(allData.get(4)[8],"0");
//        Assert.assertEquals(allData.get(4)[9],"0");
//        Assert.assertEquals(allData.get(8)[9],"0");
//        Assert.assertEquals(allData.get(4)[10],"0");
//        Assert.assertEquals(allData.get(8)[10],"0");
//        Assert.assertEquals(allData.get(4)[11],"39430,35");
//        Assert.assertEquals(allData.get(4)[12],"0");
//        Assert.assertEquals(allData.get(4)[13],"-39430,35");
//        Assert.assertEquals(allData.get(4)[14],"0");
//    }
//
//    @Test
//    public void EvalueTest() throws IOException {
//        List<String> inputDataFile = new ArrayList<>();
//        CSVReader reader = new CSVReader(new FileReader(file2), '\n', '\'');
//        List<String[]>  inputDataArrayFile = reader.readAll();
//        for(String[] el : inputDataArrayFile) {
//            inputDataFile.add(el[0]);
//        }
//        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
//        Map<String,CVSClient> readFile1 = CSVReaderMain.readCSVFileByString(file2);
//        Map<String,CVSClient> readFile2 = new HashMap<>();
//        Map<String,CVSClient> fullFile = CSVReaderMain.generateMapOfData(readFile1, readFile2);
//        CSVReaderMain.reCalculateD(fullFile, A);
//        String[] E = CSVReaderMain.reCalculateE(fullFile, A);
//        Assert.assertEquals(E[0],"E");
//        Assert.assertEquals(E[1],"181");
//        Assert.assertEquals(E[2],"122455,24");
//        Assert.assertEquals(E[3],"0");
//        Assert.assertEquals(E[4],"122455,24");
//        Assert.assertEquals(E[5],"116317,05");
//        Assert.assertEquals(E[6],"6138,19");
//        Assert.assertEquals(E[7],"0");
//        Assert.assertEquals(E[8],"0");
//        Assert.assertEquals(E[9],"0");
//        Assert.assertEquals(E[10],"0");
//        Assert.assertEquals(E[11],"45568,54");
//        Assert.assertEquals(E[12],"0");
//        Assert.assertEquals(E[13],"-39430,35");
//        Assert.assertEquals(E[14],"0");
//
//    }
//
//    @Test
//    public void MetaDataValueTest() throws IOException {
//        List<String> inputDataFileMetadata = new ArrayList<>();
//        CSVReader reader = new CSVReader(new FileReader(file1), '\n', '\'');
//        List<String[]> inputDataArrayFileMetadata = reader.readAll();
//        String[] A1 = inputDataArrayFileMetadata.get(0)[0].split("\\*");
//        for(String[] el : inputDataArrayFileMetadata) {
//            inputDataFileMetadata.add(el[0]);
//        }
//
//        Map<String,CVSClient> readFile3 = CSVReaderMain.readCSVFileByString(file3);
//        Map<String,CVSClient> readFile2 = new HashMap<>();
//        Map<String,CVSClient> fullFile = CSVReaderMain.generateMapOfData(readFile3, readFile2);
//        CSVReaderMain.reCalculateD(fullFile, A1);
//        String[] E = CSVReaderMain.reCalculateE(fullFile, A1);
//
//        List<String> metadata = CSVReaderMain.generateMetadata( E, fullFile);
//
//        List<String[]> allMetadata = CSVReaderMain.generateMetadataArray(metadata);
//        String metada[] = allMetadata.get(0);
//
//        Assert.assertEquals(metada[0],"M");
//        Assert.assertEquals(metada[1],"181");
//        Assert.assertEquals(metada[2],new SimpleDateFormat("yyMMdd").format(new Date()));
//        for(int i=3; i<15;i++) {
//            Assert.assertEquals(metada[i],E[i-1]);
//
//        }
//        for(int i=16; i<29;i++) {
//            Assert.assertEquals(metada[i],"0");
//
//        }
//        Assert.assertEquals(metada[29],"16317,05");
//        for(int i=30; i<55;i++) {
//            Assert.assertEquals(metada[i],"0");
//        }
//        Assert.assertEquals(metada[55],"16317,05");
//        for(int i=56; i<66;i++) {
//            Assert.assertEquals(metada[i],"0");
//        }
//        Assert.assertEquals(metada[66],"-51052,13");
//        for(int i=67; i<77;i++) {
//            Assert.assertEquals(metada[i],"0");
//        }
//        Assert.assertEquals(metada[77],"-51069,18");
//        Assert.assertEquals(metada[78],"0");
//        Assert.assertEquals(metada[79],"156,05");
//        Assert.assertEquals(metada[80],"-51069,18");
//        for(int i=81; i<145;i++) {
//            Assert.assertEquals(metada[i],"0");
//        }
//        Assert.assertEquals(metada[145],"-51069,18");
//    }
//
//    @Test
//    public void mergeTest() throws IOException {
//
//        Map<String,CVSClient> readFile1 = CSVReaderMain.readCSVFileByString(file1);
//        Map<String,CVSClient> readFile2 = CSVReaderMain.readCSVFileByString(file2);
//        Map<String,CVSClient> fullFile = CSVReaderMain.generateMapOfData(readFile1, readFile2);
//        Assert.assertEquals(fullFile.size(),4);
//        Assert.assertEquals(fullFile.get("0E3DBD67E762CCB494BF13A7526F1B20 X X gmbh19121990").getClientsC().size(),4);
//        Assert.assertEquals(fullFile.get("12620667BECAAC7DC708CE2C6AB300D2 XX2021973").getClientsC().size(),2);
//        Assert.assertEquals(fullFile.get("361C15BFAF64225DE5AD11166D3BE2CB XX23041980").getClientsC().size(),2);
//        Assert.assertEquals(fullFile.get("62C30CED3DFEAE112B6DBC131FEBF7A5 XX X10051950").getClientsC().size(),1);
//    }
//
//    //Version 5.1
//    private Map<String,CVSClient> getClientVersion5_1 () throws IOException {
//        String A_Additional_5 = "src/test/resources/extraData/A_Additional_5.0.csv";
//        String B_Additional_5 = "src/test/resources/extraData/B_Additional_5.0.csv";
//        String C_Additional_5 = "src/test/resources/extraData/C_Additional_5.0.csv";
//
//        String file = "src/test/resources/exampleExtraData.csv";
//        List<String> inputDataFile = new ArrayList<>();
//        CSVReader reader = new CSVReader(new FileReader(file), '\n', '\'');
//        List<String[]>  inputDataArrayFile = reader.readAll();
//        for(String[] el : inputDataArrayFile) {
//            inputDataFile.add(el[0]);
//        }
//
//        Map<String,CVSClient> readFile1 = CSVReaderMain.readCSVFileByString(file);
//        Map<String,CVSClient> readFile2 = new HashMap<>();
//        Map<String,CVSClient> fullFile = CSVReaderMain.generateMapOfData(readFile1, readFile2);
//        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
//        CSVReaderMain.reCalculateD(fullFile, A);
//        String[] E = CSVReaderMain.reCalculateE(fullFile, A);
//
//        Map<String, String> A_ExtraData = CSVReaderMain.readExtraDataA(A_Additional_5);
//        Map<String, B_ExtraData> B_ExtraData = readExtraDataB(B_Additional_5);
//        Map<String, C_ExtraData> C_ExtraData = readExtraDataC(C_Additional_5);
//        String[] AVersion5_1 = geeratedAVersion5_1 (A,  A_ExtraData);
//        AVersion5 = AVersion5_1;
//
//        Map<String,CVSClient> fullFileVersion5_1 = generateVersion5(fullFile, E, AVersion5_1, B_ExtraData, C_ExtraData);
//        reCalculateDVersion5_1(fullFileVersion5_1, AVersion5_1);
//        return fullFileVersion5_1;
//    }
//
//    @Test
//    public void D12Test() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        String[] clientB = fullFileVersion5_1.get("1BC055D5B1275F553C0D719137B9E9F XX X4031936").getClientB();
//        List<String[]> CList = fullFileVersion5_1.get("1BC055D5B1275F553C0D719137B9E9F XX X4031936").getClientsC();
//        String[] D = fullFileVersion5_1.get("1BC055D5B1275F553C0D719137B9E9F XX X4031936").getD();
//        List<String> curDList = new LinkedList<>();
//        curDList.addAll(Arrays.asList(D));
//        BigDecimal D12B = BigDecimalcalculateD12B(D, AVersion5, CList);
//        System.out.println(D12B);
//        Assert.assertEquals(D12B,BigDecimal.ZERO);
//
//    }
//
//    @Test
//    public void D12Test2() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        String[] clientB = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getClientB();
//        List<String[]> CList = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getClientsC();
//        String[] D = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getD();
//        List<String> curDList = new LinkedList<>();
//        curDList.addAll(Arrays.asList(D));
//        BigDecimal D12B = BigDecimalcalculateD12B(D, AVersion5, CList);
//        System.out.println(D12B);
//        Assert.assertEquals(D12B,BigDecimal.ZERO);
//
//    }
//
//    @Test
//    public void D12CTest1() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        String[] clientB = fullFileVersion5_1.get("1BC055D5B1275F553C0D719137B9E9F XX X4031936").getClientB();
//        List<String[]> CList = fullFileVersion5_1.get("1BC055D5B1275F553C0D719137B9E9F XX X4031936").getClientsC();
//        String[] D = fullFileVersion5_1.get("1BC055D5B1275F553C0D719137B9E9F XX X4031936").getD();
//
//        BigDecimal D12C =BigDecimalcalculateD12C(D, AVersion5, CList);
//
//        System.out.println(D12C);
//        Assert.assertEquals(D12C,BigDecimal.ZERO);
//
//    }
//
//    @Test
//    public void D12CTest2() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        String[] clientB = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getClientB();
//        List<String[]> CList = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getClientsC();
//        String[] D = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getD();
//
//        BigDecimal D12C =BigDecimalcalculateD12C(D, AVersion5, CList);
//
//        System.out.println(D12C);
//        Assert.assertEquals(D12C,new BigDecimal("-10600001.62"));
//
//    }
//
//    @Test
//    public void reCalculateDTest() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        String[] clientB = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getClientB();
//        List<String[]> CList = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getClientsC();
//        String[] D = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getD();
//        Assert.assertEquals(D[0], "D");
//        Assert.assertEquals(D[1], "7230");
//        Assert.assertEquals(D[2], "100001,62");
//        Assert.assertEquals(D[3], "0");
//        Assert.assertEquals(D[4], "-100001,62");
//        Assert.assertEquals(D[5], "100000");
//        Assert.assertEquals(D[6], "1,62");
//        Assert.assertEquals(D[7], "0");
//        Assert.assertEquals(D[8], "0");
//        Assert.assertEquals(D[9], "0");
//        Assert.assertEquals(D[10], "0");
//        Assert.assertEquals(D[11], "1,62");
//        Assert.assertEquals(D[12], "0");
//        Assert.assertEquals(D[13], "-10600001.62");
//        Assert.assertEquals(D[14], "0");
//        Assert.assertEquals(D[15], "0");
//        Assert.assertEquals(D[16], "-1.62");
//        Assert.assertEquals(D[17], "0");
//    }
//
//    @Test
//    public void reCalculateeTest() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        String[] E = reCalculateEVersion5_1(fullFileVersion5_1);
//        Assert.assertEquals(E[0], "E");
//        Assert.assertEquals(E[1], "181");
//        Assert.assertEquals(E[2], "300004,86");
//        Assert.assertEquals(E[3], "100001,62");
//        Assert.assertEquals(E[4], "-100001,62");
//        Assert.assertEquals(E[5], "300000");
//        Assert.assertEquals(E[6], "4,86");
//        Assert.assertEquals(E[7], "0");
//        Assert.assertEquals(E[8], "0");
//        Assert.assertEquals(E[9], "100001,62");
//        Assert.assertEquals(E[10], "99992,62");
//        Assert.assertEquals(E[11], "4,86");
//        Assert.assertEquals(E[12], "0");
//        Assert.assertEquals(E[13], "-10700001,62");
//        Assert.assertEquals(E[14], "0");
//        Assert.assertEquals(E[15], "0");
//        Assert.assertEquals(E[16], "-100003,24");
//        Assert.assertEquals(E[17], "0");
//    }
//
//    @Test
//    public void calculateHW5Test1() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        List<String[]> CList = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getClientsC();
//        BigDecimal sumHW5 = calculateHW5(CList);
//        Assert.assertEquals(sumHW5, BigDecimal.ZERO);
//    }
//
//
//    @Test
//    public void calculateHW5Test2() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        List<String[]> CList = fullFileVersion5_1.get("1BC055D5B1275F553C0D971913B9E9F XX X4031936").getClientsC();
//        BigDecimal sumHW5 = calculateHW5(CList);
//        Assert.assertEquals(sumHW5, BigDecimal.ZERO);
//    }
//
//    @Test
//    public void calculateHW3Test1() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        List<String[]> CList = fullFileVersion5_1.get("1BC055D5B1275F553C0D971913B9E9F XX X4031936").getClientsC();
//        BigDecimal sumHW3 = calculateHW3(CList);
//        Assert.assertEquals(sumHW3, BigDecimal.ZERO);
//    }
//
//    @Test
//    public void calculateHW3Test2() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        List<String[]> CList = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getClientsC();
//        BigDecimal sumHW3 = calculateHW3(CList);
//        Assert.assertEquals(sumHW3, BigDecimal.ZERO);
//    }
//
//    @Test
//    public void calculateHW2Test1() throws IOException {
//        Map<String,CVSClient> fullFileVersion5_1 = getClientVersion5_1();
//        List<String[]> CList = fullFileVersion5_1.get("1BC055D5B1275F55C0D9719137B9E9F XX X4031936").getClientsC();
//        BigDecimal sumHW2 = calculateHW2(CList);
//        Assert.assertEquals(sumHW2, new BigDecimal("62"));
//    }
//}
