package comparus.de.domen;

/**
 * Created by ekaterina on 9/19/17.
 */
public class KeyFile1ToFile2ToFile3 {
    private String keyFile1;
    private String keyFile2;
    private String keyFile3;

    public KeyFile1ToFile2ToFile3() {
    }

    public KeyFile1ToFile2ToFile3(String keyFile1, String keyFile2, String keyFile3) {
        this.keyFile1 = keyFile1;
        this.keyFile2 = keyFile2;
        this.keyFile3 = keyFile3;
    }

    public String getKeyFile1() {
        return keyFile1;
    }

    public void setKeyFile1(String keyFile1) {
        this.keyFile1 = keyFile1;
    }

    public String getKeyFile2() {
        return keyFile2;
    }

    public void setKeyFile2(String keyFile2) {
        this.keyFile2 = keyFile2;
    }

    public String getKeyFile3() {
        return keyFile3;
    }

    public void setKeyFile3(String keyFile3) {
        this.keyFile3 = keyFile3;
    }
}
