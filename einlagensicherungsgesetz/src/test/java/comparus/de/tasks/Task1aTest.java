package comparus.de.tasks;

import comparus.de.domen.Additional_CRecord;
import comparus.de.domen.CVSClient;
import comparus.de.domen.FileInfo;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static comparus.de.CSVReaderMain.A;
import static comparus.de.protocols.ProtocolForTask1A.readCSVFileByStringRetCountTask1a;
import static comparus.de.tasks.Task1.reCalculateE;
import static comparus.de.tasks.Task1a.*;
import static comparus.de.tasks.Task2.readCSVFileByStringSimple;
import static comparus.de.util.ReadWriteData.writeCSV;
import static org.junit.Assert.*;

/**
 * Created by ekaterina on 10/4/17.
 */
public class Task1aTest {
    String inputFileAfterMerge =  "src/test/resources/task1a/EinreicherdateiB11_Gesamt_4.1.csv";
    String Additional_CRecord_Data_Task1a = "src/test/resources/task1a/a.csv";
    String Additional_CRecord_Data_Task1aGEMKD = "src/test/resources/task1a/additional_c_records.csv";

    @Test
    public void test1() throws IOException {
        Map<String, CVSClient> readFile1 = readCSVFileByStringSimple(inputFileAfterMerge);
        FileInfo fileInfo2 = readCSVFileByStringRetCountTask1a(readFile1, inputFileAfterMerge);
        List<String[]> allData = null;
        String errorMessage = null;
        Set<Additional_CRecord> mergingCRecords = new HashSet<>();

        Map<String, Additional_CRecord> additional_cRecord = readExtraAdditionalDataCTask1a(Additional_CRecord_Data_Task1a);

        fileInfo2 = new FileInfo();
        fileInfo2.setCCount(additional_cRecord.size());
        mergingCRecords.addAll(additional_cRecord.values());
        Map<String,CVSClient> fullFileVersion4_1_Additional_C = generateVersion4AdditionalC(readFile1, additional_cRecord);
        List<String> B = new ArrayList<>();
        for(Additional_CRecord a : mergingCRecords) {
            B.add(a.getC2A());
        }
        reCalculateD_task1A(fullFileVersion4_1_Additional_C, A, B);
        String[] E = reCalculateE(fullFileVersion4_1_Additional_C, A);
        allData = generateListOfDataArray(A, fullFileVersion4_1_Additional_C, E);
        List<String> a = new ArrayList<>();
        for (String[] cur: allData) {
            a.add(StringUtils.join(cur, "*"));
        }
        assertEquals(a.get(1), "B*0000255015**Sudfper Gm0000255015bH**3*Erfurter Stras232951431se 33**99867*Gera*DE*19121990*450*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(2), "C*0000255015*0000033703-121**N*001*10082015*Kontokorrent*EUR*4731,72*1,00000*4731,72*99999,99999*31082017*31082017*Y*04*0,00*0,00*4731,72*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(3), "D*0000255015*4731,72*0,00*4731,72*4731,72*0,00*0,00*0,00*0,00*0,00*0,00*0,00*-56202,06*0,00");
        assertEquals(a.get(4), "B*0001038230*Wagner*Ilse**2*Hamburger Stra�e 33**25482*Otterfing*DE*11061953*97A*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(5), "C*0001038230*5010001654**N*001*11092013*Kontokorrent*EUR*0,09*1,00000*0,09*99999,99999*31122016*01092017*Y*04*0,00*0,00*0,09*YNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN*NNNNNNNNNYNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(6), "C*0001038230*5002852112-883**N*1*15062016*Kontokorrent*EUR*-3240,70*1*-3240,70*0*0*16062016*N*04*0*0*-3240,70*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN**");
        assertEquals(a.get(7), "D*0001038230*0,09*0,00*0,09*0,09*0,00*0,00*0,00*0,00*0,00*0,00*0,00*-3240,70*0,00");
        assertEquals(a.get(8), "B*0001216104**Supevr GmabH**3*Erfurter Strasse 33**99867*Gera*DE*19121990*450*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(9), "C*0001216104*0000033703-121**N*001*10082015*Kontokorrent*EUR*4731,72*1,00000*4731,72*99999,99999*31082017*31082017*Y*04*0,00*0,00*4731,72*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(10), "D*0001216104*4731,72*0,00*4731,72*4731,72*0,00*0,00*0,00*0,00*0,00*0,00*0,00*-56202,06*0,00");
        assertEquals(a.get(11), "B*0003268284**Sudfper GmbH**3*Erfurter Stras232951431se 33**99867*Gera*DE*19121990*450*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(12), "C*0003268284*0000033703-121**N*001*10082015*Kontokorrent*EUR*4731,72*1,00000*4731,72*99999,99999*31082017*31082017*Y*04*0,00*0,00*4731,72*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(13), "D*0003268284*4731,72*0,00*4731,72*4731,72*0,00*0,00*0,00*0,00*0,00*0,00*0,00*-56202,06*0,00");
    }

    @Test
    public void test2() throws IOException {
        Map<String, CVSClient> readFile1 = readCSVFileByStringSimple(inputFileAfterMerge);
        FileInfo fileInfo2 = readCSVFileByStringRetCountTask1a(readFile1, inputFileAfterMerge);
        List<String[]> allData = null;
        String errorMessage = null;
        Set<Additional_CRecord> mergingCRecords = new HashSet<>();

        Map<String, Additional_CRecord> additional_cRecord = readExtraAdditionalDataCTask1a(Additional_CRecord_Data_Task1aGEMKD);

        fileInfo2 = new FileInfo();
        fileInfo2.setCCount(additional_cRecord.size());
        mergingCRecords.addAll(additional_cRecord.values());
        Map<String,CVSClient> fullFileVersion4_1_Additional_C = generateVersion4AdditionalC(readFile1, additional_cRecord);
        List<String> B = new ArrayList<>();
        for(Additional_CRecord a : mergingCRecords) {
            B.add(a.getC2A());
        }
        reCalculateD_task1A(fullFileVersion4_1_Additional_C, A, B);
        String[] E = reCalculateE(fullFileVersion4_1_Additional_C, A);
        allData = generateListOfDataArray(A, fullFileVersion4_1_Additional_C, E);
        List<String> a = new ArrayList<>();
        for (String[] cur: allData) {
            a.add(StringUtils.join(cur, "*"));
        }
        assertEquals(a.get(1), "B*0000255015**Sudfper Gm0000255015bH**3*Erfurter Stras232951431se 33**99867*Gera*DE*19121990*450*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(2), "C*0000255015*0000033703-121**N*001*10082015*Kontokorrent*EUR*4731,72*1,00000*4731,72*99999,99999*31082017*31082017*Y*04*0,00*0,00*4731,72*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(3), "D*0000255015*4731,72*0,00*4731,72*4731,72*0,00*0,00*0,00*0,00*0,00*0,00*0,00*-56202,06*0,00");
        assertEquals(a.get(4), "B*0001038230*Wagner*Ilse**2*Hamburger Stra�e 33**25482*Otterfing*DE*11061953*97A*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(5), "C*0001038230*5010001654**N*001*11092013*Kontokorrent*EUR*0,09*1,00000*0,09*99999,99999*31122016*01092017*Y*04*0,00*0,00*0,09*YNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN*NNNNNNNNNYNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(6), "D*0001038230*0,09*0,09*0,00*0,00*0,00*0,00*0,00*0,00*0,00*0,09*0,00*0,00*0,00");
        assertEquals(a.get(7), "B*0001216104**Supevr GmabH**3*Erfurter Strasse 33**99867*Gera*DE*19121990*450*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(8), "C*0001216104*0000033703-121**N*001*10082015*Kontokorrent*EUR*4731,72*1,00000*4731,72*99999,99999*31082017*31082017*Y*04*0,00*0,00*4731,72*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(9), "D*0001216104*4731,72*0,00*4731,72*4731,72*0,00*0,00*0,00*0,00*0,00*0,00*0,00*-56202,06*0,00");
        assertEquals(a.get(10), "B*0003268284**Sudfper GmbH**3*Erfurter Stras232951431se 33**99867*Gera*DE*19121990*450*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(11), "C*0003268284*0000033703-121**N*001*10082015*Kontokorrent*EUR*4731,72*1,00000*4731,72*99999,99999*31082017*31082017*Y*04*0,00*0,00*4731,72*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN*NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        assertEquals(a.get(12), "D*0003268284*4731,72*0,00*4731,72*4731,72*0,00*0,00*0,00*0,00*0,00*0,00*0,00*-56202,06*0,00");
    }
}