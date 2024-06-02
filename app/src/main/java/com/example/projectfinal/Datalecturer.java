package com.example.projectfinal;

public class Datalecturer {

    private String lecturerName;
    private String telephone;
    private String email;
    private String dataImage;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getDataImage() {
        return dataImage;
    }

    // Corrected the typo here
    public Datalecturer(String lecturerName, String telephone, String email, String dataImage) {
        this.lecturerName = lecturerName;
        this.telephone = telephone;
        this.email = email;
        this.dataImage = dataImage;
    }

    public Datalecturer() {

    }
}
