package com.company.pollweb.data.proxy;

import com.company.pollweb.data.implementation.DomandaImpl;
import com.company.pollweb.framework.data.DataLayer;
import org.json.JSONObject;

public class CompilazioneProxy extends DomandaImpl {

    protected boolean dirty;

    protected DataLayer dataLayer;

    public CompilazioneProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.dirty = false;
    }

    public void setSondaggio_id(int Sondaggio_id){
        super.setSondaggio_id(Sondaggio_id);
    }

    public void setEmail(String Tipologia){super.setTipologia(Tipologia);}

    public boolean isDirty() {
        return dirty;
    }
}
