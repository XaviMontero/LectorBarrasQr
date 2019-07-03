package com.example.lectorbarrasqr.model;

public class Productos {


    private String id;


    private String proNombre;

     private float proSto;

     private String proUbi;

     private float proPreVen;

     private float proPreMay;

     private String proObs;

    public Productos() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public float getProSto() {
        return proSto;
    }

    public void setProSto(float proSto) {
        this.proSto = proSto;
    }

    public String getProUbi() {
        return proUbi;
    }

    public void setProUbi(String proUbi) {
        this.proUbi = proUbi;
    }

    public float getProPreVen() {
        return proPreVen;
    }

    public void setProPreVen(float proPreVen) {
        this.proPreVen = proPreVen;
    }

    public float getProPreMay() {
        return proPreMay;
    }

    public void setProPreMay(float proPreMay) {
        this.proPreMay = proPreMay;
    }

    public String getProObs() {
        return proObs;
    }

    public void setProObs(String proObs) {
        this.proObs = proObs;
    }
}
