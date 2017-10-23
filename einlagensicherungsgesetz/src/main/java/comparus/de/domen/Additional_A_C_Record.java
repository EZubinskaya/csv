package comparus.de.domen;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by ekaterina on 9/28/17.
 */
public class Additional_A_C_Record extends Additional_CRecord{
    private String A2;

    public Additional_A_C_Record() {
    }

    public Additional_A_C_Record(String a2, String c1, String c2A, String c2B, String c3, String c4, String c5, String c6, String c7, String c8, String c9, String c10, String c11, String c12, String c13, String c14, String c15, String c16, String c17, String c18, String c19, String c20_01_50, String c21_01_04, String c21_05, String c21_06, String c21_07, String c21_08_50, String c22, String c23) {
        super(c1, c2A,c2B,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20_01_50,c21_01_04,c21_05,c21_06,c21_07,c21_08_50,c22,c23);
        A2 = a2;
    }

    public String getA2() {
        return A2;
    }

    public void setA2(String a2) {
        A2 = a2;
    }

}
