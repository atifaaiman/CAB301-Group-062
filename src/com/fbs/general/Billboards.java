package com.fbs.general;


import java.io.Serializable;

/**
 * @author Fernando Barbosa Silva
 * Create an Billboard object with the attributes required by the application.
 * Constructor (String billboard_name, String xml, String user_name)
 */
public class Billboards implements Serializable {
    private String billboard_name;
    private String xml;
    private String created_by;

    public Billboards (String billboard_name, String xml, String user_name){
        this.billboard_name = billboard_name;
        this.xml = xml;
        this.created_by = user_name;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return billboard_name string
     */
    public String getBillboard_name() {
        return billboard_name;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return xml string
     */
    public String getXml() {
        return xml;
    }

    /**
     * @author Fernando Barbosa Silva
     * @return user_name of the person who has created the billboard.
     */
    public String getCreated_by() {
        return created_by;
    }

}
