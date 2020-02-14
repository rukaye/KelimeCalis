package com.rukayye.kelimecalis.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Collection;
import java.util.Iterator;

@IgnoreExtraProperties
public class KelimeModel {

    private long id;
    private String kelime;
    private String anlam;
    private Boolean okundu;
    public KelimeModel() {
    }

    public KelimeModel(long id, String kelime, String anlam, Boolean okundu) {
        this.id = id;
        this.kelime = kelime;
        this.anlam = anlam;
        this.okundu = okundu;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKelime() {
        return kelime;
    }

    public void setKelime(String kelime) {
        this.kelime = kelime;
    }

    public String getAnlam() {
        return anlam;
    }

    public void setAnlam(String anlam) {
        this.anlam = anlam;
    }

    public Boolean getOkundu() {
        return okundu;
    }

    public void setOkundu(Boolean okundu) {
        this.okundu = okundu;
    }
}
