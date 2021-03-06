package comparus.de.domen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekaterina on 9/19/17.
 */
public class Protocol {
    private int resultAmountB = 0;
    private int resultAmountC = 0;
    private int resultAmountD = 0;
    private int resultAmountTotal = 0;

    private List<KeyFile1ToFile2> keyFile1ToFile2 = new ArrayList<>();
    private List<String> recalculatedDRecords = new ArrayList<>();
    private List<KeyFile1ToFile2ToFile3> keyFile1ToFile2ToFile3_B_Record = new ArrayList<>();
    private List<KeyFile1ToFile2ToFile3> keyFile1ToFile2ToFile3_C_Record = new ArrayList<>();

    public int getResultAmountB() {
        return resultAmountB;
    }

    public void setResultAmountB(int resultAmountB) {
        this.resultAmountB = resultAmountB;
    }

    public int getResultAmountC() {
        return resultAmountC;
    }

    public void setResultAmountC(int resultAmountC) {
        this.resultAmountC = resultAmountC;
    }

    public int getResultAmountD() {
        return resultAmountD;
    }

    public void setResultAmountD(int resultAmountD) {
        this.resultAmountD = resultAmountD;
    }

    public int getResultAmountTotal() {
        return resultAmountTotal;
    }

    public void setResultAmountTotal(int resultAmountTotal) {
        this.resultAmountTotal = resultAmountTotal;
    }

    public List<KeyFile1ToFile2> getKeyFile1ToFile2() {
        return keyFile1ToFile2;
    }

    public void setKeyFile1ToFile2(List<KeyFile1ToFile2> keyFile1ToFile2) {
        this.keyFile1ToFile2 = keyFile1ToFile2;
    }

    public List<String> getRecalculatedDRecords() {
        return recalculatedDRecords;
    }

    public void setRecalculatedDRecords(List<String> recalculatedDRecords) {
        this.recalculatedDRecords = recalculatedDRecords;
    }

    public List<KeyFile1ToFile2ToFile3> getKeyFile1ToFile2ToFile3_B_Record() {
        return keyFile1ToFile2ToFile3_B_Record;
    }

    public void setKeyFile1ToFile2ToFile3_B_Record(List<KeyFile1ToFile2ToFile3> keyFile1ToFile2ToFile3_B_Record) {
        this.keyFile1ToFile2ToFile3_B_Record = keyFile1ToFile2ToFile3_B_Record;
    }

    public List<KeyFile1ToFile2ToFile3> getKeyFile1ToFile2ToFile3_C_Record() {
        return keyFile1ToFile2ToFile3_C_Record;
    }

    public void setKeyFile1ToFile2ToFile3_C_Record(List<KeyFile1ToFile2ToFile3> keyFile1ToFile2ToFile3_C_Record) {
        this.keyFile1ToFile2ToFile3_C_Record = keyFile1ToFile2ToFile3_C_Record;
    }
}


