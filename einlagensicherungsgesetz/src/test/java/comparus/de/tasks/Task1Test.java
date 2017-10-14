package comparus.de.tasks;

import com.opencsv.CSVReader;
import comparus.de.domen.B;
import comparus.de.domen.CVSClient;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static comparus.de.tasks.Task1.generateMapOfData;
import static comparus.de.tasks.Task1a.generateListOfDataArray;
import static org.junit.Assert.*;

/**
 * Created by ekaterina on 10/4/17.
 */
public class Task1Test {
    static String file0 = "src/test/resources/example0.csv";

    static String file1 = "src/test/resources/example1.csv";
    static String file2 = "src/test/resources/example2.csv";
    static String file3 = "src/test/resources/example3.csv";

    @Test
    public void fileTheSameTest() throws IOException {
        List<String> inputDataFile = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file1), '\n', '\'');
        List<String[]>  inputDataArrayFile = reader.readAll();
        for(String[] el : inputDataArrayFile) {
            inputDataFile.add(el[0]);
        }

        Map<B,CVSClient> readFile1 = Task1.readCSVFileByString(file1,false);
        Map<B,CVSClient> readFile2 = new HashMap<>();
        Map<String, CVSClient> fullFile = generateMapOfData(readFile1, readFile2);

        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
        Task1.reCalculateD(fullFile, A, false);
        String[] ESame = Task1.reCalculateE(fullFile, A);
        List<String[]> allt = generateListOfDataArray(A, fullFile, ESame);
        List<String> result = new ArrayList<>();
        for (String[] el : allt) {
            result.add(StringUtils.join(el,"*"));
        }

        Assert.assertArrayEquals(result.toArray(), inputDataFile.toArray() );
    }

    @Test
    public void fileTheSameTest2() throws IOException {
        List<String> inputDataFile = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file0), '\n', '\'');
        List<String[]>  inputDataArrayFile = reader.readAll();
        for(String[] el : inputDataArrayFile) {
            inputDataFile.add(el[0]);
        }

        Map<B,CVSClient> readFile1 = Task1.readCSVFileByString(file0,true);
        Map<B,CVSClient> readFile2 = Task1.readCSVFileByString(file0,true);
        Map<String, CVSClient> fullFile = generateMapOfData(readFile1, readFile2);

        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
        Task1.reCalculateD(fullFile, A, true);
        String[] ESame = Task1.reCalculateE(fullFile, A);
        List<String[]> allt = generateListOfDataArray(A, fullFile, ESame);
        List<String> result = new ArrayList<>();
        for (String[] el : allt) {
            result.add(StringUtils.join(el,"*"));
        }

        List<String> inputDataFileExp = new ArrayList<>();
        for (String el : inputDataFile) {
            if(el.startsWith("C")) {
                String[] C = el.split("\\*");
                C[2] = C[2] + "-121";
                inputDataFileExp.add(StringUtils.join(C, "*"));
                inputDataFileExp.add(StringUtils.join(C, "*"));
            } else {
                inputDataFileExp.add(el);
            }
        }

        Assert.assertArrayEquals(result.toArray(), inputDataFileExp.toArray() );
    }

    @Test
    public void DvalueTest() throws IOException {
        List<String> inputDataFile = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file3), '\n', '\'');
        List<String[]>  inputDataArrayFile = reader.readAll();
        for(String[] el : inputDataArrayFile) {
            inputDataFile.add(el[0]);
        }

        Map<B,CVSClient> readFile3 = Task1.readCSVFileByString(file3, true);
        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
        Map<B,CVSClient> readFile2 = new HashMap<>();
        Map<String,CVSClient> fullFile = Task1.generateMapOfData(readFile3, readFile2);
        Task1.reCalculateD(fullFile, A, false);
        String[] E = Task1.reCalculateE(fullFile, A);
        List<String[]> allData = generateListOfDataArray(A, fullFile, E);
        Assert.assertEquals(allData.get(4)[2],"16317,05");
        Assert.assertEquals(allData.get(4)[3],"6317,05");
        Assert.assertEquals(allData.get(8)[3],"17,05");
        Assert.assertEquals(allData.get(4)[4],"16317,05");
        Assert.assertEquals(allData.get(4)[5],"0");
        Assert.assertEquals(allData.get(4)[6],"0");
        Assert.assertEquals(allData.get(4)[7],"0");
        Assert.assertEquals(allData.get(4)[8],"0");
        Assert.assertEquals(allData.get(4)[9],"0");
        Assert.assertEquals(allData.get(8)[9],"0");
        Assert.assertEquals(allData.get(4)[10],"0");
        Assert.assertEquals(allData.get(8)[10],"0");
        Assert.assertEquals(allData.get(4)[11],"39430,35");
        Assert.assertEquals(allData.get(4)[12],"0");
        Assert.assertEquals(allData.get(4)[13],"-39430,35");
        Assert.assertEquals(allData.get(4)[14],"0");
    }

    @Test
    public void EvalueTest() throws IOException {
        List<String> inputDataFile = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file2), '\n', '\'');
        List<String[]>  inputDataArrayFile = reader.readAll();
        for(String[] el : inputDataArrayFile) {
            inputDataFile.add(el[0]);
        }
        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
        Map<B,CVSClient> readFile1 = Task1.readCSVFileByString(file2, true);
        Map<B,CVSClient> readFile2 = new HashMap<>();
        Map<String,CVSClient> fullFile = Task1.generateMapOfData(readFile1, readFile2);
        Task1.reCalculateD(fullFile, A, false);
        String[] E = Task1.reCalculateE(fullFile, A);
        Assert.assertEquals(E[0],"E");
        Assert.assertEquals(E[1],"181");
        Assert.assertEquals(E[2],"122455,24");
        Assert.assertEquals(E[3],"0,00");
        Assert.assertEquals(E[4],"122455,24");
        Assert.assertEquals(E[5],"116317,05");
        Assert.assertEquals(E[6],"6138,19");
        Assert.assertEquals(E[7],"0,00");
        Assert.assertEquals(E[8],"0,00");
        Assert.assertEquals(E[9],"0,00");
        Assert.assertEquals(E[10],"0,00");
        Assert.assertEquals(E[11],"45568,54");
        Assert.assertEquals(E[12],"0,00");
        Assert.assertEquals(E[13],"-39430,35");
        Assert.assertEquals(E[14],"0,00");

    }

    @Test
    public void mergeTest() throws IOException {

        Map<B,CVSClient> readFile1 = Task1.readCSVFileByString(file1, false);
        Map<B,CVSClient> readFile2 = Task1.readCSVFileByString(file2, false);
        Map<String,CVSClient> fullFile = Task1.generateMapOfData(readFile1, readFile2);
        Assert.assertEquals(fullFile.size(),4);
        Assert.assertEquals(fullFile.get("0E3DBD67E762CCB494BF13A7526F1B20 X X gmbh19121990").getClientsC().size(),4);
        Assert.assertEquals(fullFile.get("12620667BECAAC7DC708CE2C6AB300D2 XX2021973").getClientsC().size(),2);
    }
}