package com.example.projectfinal;


public class Data {

    private String dataName;
    private String dataId;
    private String dataBatch;
    private String dataImage;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataName() {
        return dataName;
    }

    public String getDataId() {
        return dataId;
    }

    public String getDataBatch() {
        return dataBatch;
    }

    public String getDataImage() {
        return dataImage;
    }

    public Data(String dataName, String dataBatch, String dataId, String dataImage) {
        this.dataName = dataName;
        this.dataBatch = dataBatch;
        this.dataId = dataId;
        this.dataImage = dataImage;
    }
    public Data(){

    }
}