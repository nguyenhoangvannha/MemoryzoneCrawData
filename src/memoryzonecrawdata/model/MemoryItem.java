/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memoryzonecrawdata.model;

/**
 *
 * @author nguye
 */
public class MemoryItem {
    String TEN;
    int GIA;
    String HINHANH;
    String MOTA;
    String NHASANXUAT;
    String MODEL;
    String CHUANGIAOTIEP;
    String DUNGLUONG;
    String BAOHANH;
    public MemoryItem() {
    }

    public MemoryItem(String TEN, int GIA, String HINHANH) {
        this.TEN = TEN;
        this.GIA = GIA;
        this.HINHANH = HINHANH;
    }
    
    public MemoryItem(String TEN, int GIA, String HINHANH, String MOTA, String NHASANXUAT, String MODEL
            , String CHUANGIAOTIEP, String DUNGLUONG, String BAOHANH) {
        this.TEN = TEN;
        this.GIA = GIA;
        this.HINHANH = HINHANH;
        this.MOTA = MOTA;
        this.NHASANXUAT = NHASANXUAT;
        this.MODEL = MODEL;
        this.CHUANGIAOTIEP = CHUANGIAOTIEP;
        this.DUNGLUONG = DUNGLUONG;
        this.BAOHANH = BAOHANH;
    }

    public String getTEN() {
        return TEN;
    }

    public void setTEN(String TEN) {
        this.TEN = TEN;
    }

    public int getGIA() {
        return GIA;
    }

    public void setGIA(int GIA) {
        this.GIA = GIA;
    }

    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }

    public String getMOTA() {
        return MOTA;
    }

    public void setMOTA(String MOTA) {
        this.MOTA = MOTA;
    }

    public String getNHASANXUAT() {
        return NHASANXUAT;
    }

    public void setNHASANXUAT(String NHASANXUAT) {
        this.NHASANXUAT = NHASANXUAT;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getCHUANGIAOTIEP() {
        return CHUANGIAOTIEP;
    }

    public void setCHUANGIAOTIEP(String CHUANGIAOTIEP) {
        this.CHUANGIAOTIEP = CHUANGIAOTIEP;
    }

    public String getDUNGLUONG() {
        return DUNGLUONG;
    }

    public void setDUNGLUONG(String DUNGLUONG) {
        this.DUNGLUONG = DUNGLUONG;
    }

    public String getBAOHANH() {
        return BAOHANH;
    }

    public void setBAOHANH(String BAOHANH) {
        this.BAOHANH = BAOHANH;
    }
    
}
