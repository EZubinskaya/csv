package comparus.de;

/**
 * Created by ekaterina on 9/20/17.
 */
public class FileInfo {
    private String fileName;
    private int BCount;
    private int CCount;
    private int DCount;

    public FileInfo() {
    }

    public FileInfo(String fileName, int DCount, int BCount, int CCount) {
        this.fileName = fileName;
        this.DCount = DCount;
        this.BCount = BCount;
        this.CCount = CCount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getDCount() {
        return DCount;
    }

    public void setDCount(int DCount) {
        this.DCount = DCount;
    }

    public int getBCount() {
        return BCount;
    }

    public void setBCount(int BCount) {
        this.BCount = BCount;
    }

    public int getCCount() {
        return CCount;
    }

    public void setCCount(int CCount) {
        this.CCount = CCount;
    }
}
