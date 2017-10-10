package comparus.de.tasks;

import com.opencsv.CSVReader;
import comparus.de.domen.B;
import comparus.de.domen.CVSClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static comparus.de.tasks.Task2.generateMetadata;
import static comparus.de.tasks.Task2.generateMetadataArray;
import static comparus.de.tasks.Task2.readCSVFileByStringSimple;
import static comparus.de.util.ReadWriteData.writeCSV;
import static org.junit.Assert.*;

/**
 * Created by ekaterina on 10/4/17.
 */
public class Task2Test {
    static String file1 = "src/test/resources/example1.csv";
    static String EStr = "E*121*175410,21*0,00*175410,21*169272,02*6138,19*0,00*0,00*0,00*0,00*6138,19*0,00*-43301,13*0,00";


    @Test
    public void MetaDataValueTest() throws IOException {
        Map<String,CVSClient> fullFile = readCSVFileByStringSimple(file1);
        String[] E = EStr.split("\\*");
        List<String> metadata = generateMetadata(E, fullFile);


        List<String> inputDataFileMetadata = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(file1), '\n', '\'');
        List<String[]> inputDataArrayFileMetadata = reader.readAll();
        String[] A1 = inputDataArrayFileMetadata.get(0)[0].split("\\*");
        for(String[] el : inputDataArrayFileMetadata) {
            inputDataFileMetadata.add(el[0]);
        }

        List<String[]> allMetadata = Task2.generateMetadataArray(metadata);
        String metada[] = allMetadata.get(0);

        Assert.assertEquals(metada[0],"M");
        Assert.assertEquals(metada[2],new SimpleDateFormat("yyMMdd").format(new Date()));
        for(int i=3; i<15;i++) {
            Assert.assertEquals(metada[i],E[i-1]);

        }
        for(int i=16; i<29;i++) {
            Assert.assertEquals(metada[i],"0,00");

        }
        Assert.assertEquals(metada[29],"0,00");
        for(int i=30; i<55;i++) {
            Assert.assertEquals(metada[i],"0,00");
        }
        Assert.assertEquals(metada[55],"0,00");
        for(int i=56; i<66;i++) {
            Assert.assertEquals(metada[i],"0,00");
        }
        Assert.assertEquals(metada[66],"0,00");
        for(int i=67; i<77;i++) {
            Assert.assertEquals(metada[i],"0,00");
        }
        Assert.assertEquals(metada[77],"0,00");
        Assert.assertEquals(metada[78],"0,00");
        Assert.assertEquals(metada[79],"0,00");
        Assert.assertEquals(metada[80],"0,00");
        for(int i=81; i<110;i++) {
            Assert.assertEquals(metada[i],"0,00");
        }
        for(int i=118; i<145;i++) {
            Assert.assertEquals(metada[i],"");
        }
        Assert.assertEquals(metada[145],"");
    }

}