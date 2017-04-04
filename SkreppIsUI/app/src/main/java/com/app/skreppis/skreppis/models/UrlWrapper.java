package com.app.skreppis.skreppis.models;

/**
 * Created by Einar_2 on 4/4/2017.
 */


/**
 * Heimskur lítill klasi til að geyma urlið sem þið tengið serverinn við
 */
public class UrlWrapper {
    private String url;

    /**
     * Url gildi:
     *  192.168.1.2     Tölvan hans Einars
     *  192.168.42.49   Síminn hans Einars
     *  192.168.1.106   Nökkvi
     *
     *  Bætið við :8000 fyrir portið!
     */
    public UrlWrapper(){
        //Breytið þessu gildi í urlið sem þið notið!
        url = "http://192.168.1.106:8000";
    }

    public String getUrl() {
        return this.url;
    }
}
