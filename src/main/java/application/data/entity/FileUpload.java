package application.data.entity;

import java.util.List;

public class FileUpload {
    private String excelName;
    private String imgName;

    public FileUpload() {
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
