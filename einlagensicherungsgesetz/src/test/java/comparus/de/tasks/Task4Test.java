package comparus.de.tasks;

import com.opencsv.CSVReader;
import comparus.de.domen.B;
import comparus.de.domen.CVSClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by ekaterina on 10/4/17.
 */
public class Task4Test {
    static String file1 = "src/test/resources/task4/1.csv";
    static String file0 = "src/test/resources/task4/example0.csv";

    @Test
    public void reCalculateDTest() throws IOException {
        List<String> inputDataFile = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file1), '\n', '\'');
        List<String[]>  inputDataArrayFile = reader.readAll();
        for(String[] el : inputDataArrayFile) {
            inputDataFile.add(el[0]);
        }

        Map<B,CVSClient> readFile1 = Task1.readCSVFileByString(file1, false);
        Map<B,CVSClient> readFile2 = new HashMap<>();
        Map<String,CVSClient> fullFile = Task1.generateMapOfData(readFile1, readFile2);
        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
        Task4.calculateDversion5(fullFile, A, false);

        String[] clientB = fullFile.get("5615").getClientB();
        List<String[]> CList = fullFile.get("5615").getClientsC();
        String[] D = fullFile.get("5615").getD();
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
    public void reCalculateDTest2() throws IOException {
        List<String> inputDataFile = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file0), '\n', '\'');
        List<String[]>  inputDataArrayFile = reader.readAll();
        for(String[] el : inputDataArrayFile) {
            inputDataFile.add(el[0]);
        }

        Map<B,CVSClient> readFile1 = Task1.readCSVFileByString(file0, true);
        Map<B,CVSClient> readFile2 = Task1.readCSVFileByString(file0, true);
        Map<String,CVSClient> fullFile = Task1.generateMapOfData(readFile1, readFile2);
        String[] A = inputDataArrayFile.get(0)[0].split("\\*");
        Task4.calculateDversion5(fullFile, A, true);

        String[] clientB = fullFile.get("AEF99037A1757D821E80D46E83E71B17 XX X X X7011936").getClientB();
        List<String[]> CList = fullFile.get("AEF99037A1757D821E80D46E83E71B17 XX X X X7011936").getClientsC();
        String[] D = fullFile.get("AEF99037A1757D821E80D46E83E71B17 XX X X X7011936").getD();
        Assert.assertEquals(D[0], "D");
        Assert.assertEquals(D[1], "14");
        Assert.assertEquals(D[2], "200000");
        Assert.assertEquals(D[3], "200000");
        Assert.assertEquals(D[4], "0,00");
        Assert.assertEquals(D[5], "0,00");
        Assert.assertEquals(D[6], "0,00");
        Assert.assertEquals(D[7], "0,00");
        Assert.assertEquals(D[8], "0,00");
        Assert.assertEquals(D[9], "200000");
        Assert.assertEquals(D[10], "0,00");
        Assert.assertEquals(D[11], "0,00");
        Assert.assertEquals(D[12], "0,00");
        Assert.assertEquals(D[13], "0,00");
        Assert.assertEquals(D[14], "0,00");


    }
}