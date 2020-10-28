package com.company.pollweb.data.proxy;

import com.company.pollweb.data.implementation.CompilazioneImpl;
import com.company.pollweb.data.implementation.DomandaImpl;
import com.company.pollweb.framework.data.DataLayer;
import org.json.JSONObject;

public class CompilazioneProxy extends CompilazioneImpl {

    protected boolean dirty;

    protected DataLayer dataLayer;

    public CompilazioneProxy(DataLayer d) {
        super();
        this.dataLayer = d;
        this.dirty = false;
    }

    public void setSondaggio_id(int Sondaggio_id){
        super.setSondaggioId(Sondaggio_id);
    }

    public void setEmail(String email){super.setEmail(email);}

    public boolean isDirty() {
        return dirty;
    }
}
