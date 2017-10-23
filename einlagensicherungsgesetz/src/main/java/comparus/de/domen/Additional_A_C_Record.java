package comparus.de.domen;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by ekaterina on 9/28/17.
 */
public class Additional_A_C_Record {
    private String A2;
    private String C1;
    private String C2A;
    private String C2B;
    private String C3;
    private String C4;
    private String C5;
    private String C6;
    private String C7;
    private String C8;
    private String C9;
    private String C10;
    private String C11;
    private String C12;
    private String C13;
    private String C14;
    private String C15;
    private String C16;
    private String C17;
    private String C18;
    private String C19;
    private String C20;
    private String C21;
    private String C22;
    private String C23;

    public Additional_A_C_Record() {
    }

    public Additional_A_C_Record(String a2, String c1, String c2A, String c2B, String c3, String c4, String c5, String c6, String c7, String c8, String c9, String c10, String c11, String c12, String c13, String c14, String c15, String c16, String c17, String c18, String c19, String c20_01_50, String c21_01_04, String c21_05, String c21_06, String c21_07, String c21_08_50, String c22, String c23) {
        A2 = a2;
        C1 = c1;
        C2A = c2A;
        C2B = c2B;
        C3 = c3;
        C4 = c4;
        C5 = c5;
        C6 = c6;
        C7 = c7;
        C8 = c8;
        C9 = c9;
        C10 = c10;
        C11 = c11;
        C12 = c12;
        C13 = c13;
        C14 = c14;
        C15 = c15;
        C16 = c16;
        C17 = c17;
        C18 = c18;
        C19 = c19;
        C20 = c20_01_50;
        C21 = c21_01_04 + c21_05 + c21_06 + c21_07 + c21_08_50;
        C22 = c22;
        C23 = c23;
    }

    public String getA2() {
        return A2;
    }

    public void setA2(String a2) {
        A2 = a2;
    }

    public String getC1() {
        return C1;
    }

    public void setC1(String c1) {
        C1 = c1;
    }

    public String getC2A() {
        return C2A;
    }

    public void setC2A(String c2A) {
        C2A = c2A;
    }

    public String getC2B() {
        return C2B;
    }

    public void setC2B(String c2B) {
        C2B = c2B;
    }

    public String getC3() {
        return C3;
    }

    public void setC3(String c3) {
        C3 = c3;
    }

    public String getC4() {
        return C4;
    }

    public void setC4(String c4) {
        C4 = c4;
    }

    public String getC5() {
        return C5;
    }

    public void setC5(String c5) {
        C5 = c5;
    }

    public String getC6() {
        return C6;
    }

    public void setC6(String c6) {
        C6 = c6;
    }

    public String getC7() {
        return C7;
    }

    public void setC7(String c7) {
        C7 = c7;
    }

    public String getC8() {
        return C8;
    }

    public void setC8(String c8) {
        C8 = c8;
    }

    public String getC9() {
        return C9;
    }

    public void setC9(String c9) {
        C9 = c9;
    }

    public String getC10() {
        return C10;
    }

    public void setC10(String c10) {
        C10 = c10;
    }

    public String getC11() {
        return C11;
    }

    public void setC11(String c11) {
        C11 = c11;
    }

    public String getC12() {
        return C12;
    }

    public void setC12(String c12) {
        C12 = c12;
    }

    public String getC13() {
        return C13;
    }

    public void setC13(String c13) {
        C13 = c13;
    }

    public String getC14() {
        return C14;
    }

    public void setC14(String c14) {
        C14 = c14;
    }

    public String getC15() {
        return C15;
    }

    public void setC15(String c15) {
        C15 = c15;
    }

    public String getC16() {
        return C16;
    }

    public void setC16(String c16) {
        C16 = c16;
    }

    public String getC17() {
        return C17;
    }

    public void setC17(String c17) {
        C17 = c17;
    }

    public String getC18() {
        return C18;
    }

    public void setC18(String c18) {
        C18 = c18;
    }

    public String getC19() {
        return C19;
    }

    public void setC19(String c19) {
        C19 = c19;
    }

    public String getC20() {
        return C20;
    }

    public void setC20(String c20) {
        C20 = c20;
    }

    public String getC21() {
        return C21;
    }

    public void setC21(String c21) {
        C21 = c21;
    }

    public String getC22() {
        return C22;
    }

    public void setC22(String c22) {
        C22 = c22;
    }

    public String getC23() {
        return C23;
    }

    public void setC23(String c23) {
        C23 = c23;
    }

    public String[] toArray() {
        String[] arr = {C1, C2A, C2B, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15, C16, C17, C18, C19, C20, C21, C22, C23};
        return arr;
    }

    @Override
    public String toString() {
        return StringUtils.join(toArray(), "*");
    }
}
