/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.util.ArrayList;


/**
 *
 * @author  Mohamed Mostafa
 */

public class Operations {
    private String name;
    private int rows;
    private int rows_start;
    private String http_operation;
    private String rest_url;
    private ArrayList<ObjectsInSheet> objects = new ArrayList<>();
    private ArrayList<Fields> fields = new ArrayList<>();
    
    /******************arg constructor*******************/

    public Operations(String name, String http_operation, String rest_url) {
        this.name = name;
        this.http_operation = http_operation;
        this.rest_url = rest_url;
    }
    
    /***************************Accessors************************************/

    public String getName() {
        return name;
    }

    public int getRows_start() {
        return rows_start;
    }
    
    public int getRows() {
        return rows;
    }

    public String getHttp_operation() {
        return http_operation;
    }

    public String getRest_url() {
        return rest_url;
    }

    public ArrayList<ObjectsInSheet> getObjects() {
        return objects;
    }

    public ArrayList<Fields> getFields() {
        return fields;
    }
    
    
    
    /*******************************Mutators***************************************/

    public void setRows(int rows_start, int rows) {
        this.rows_start = rows_start;
        this.rows = rows;
    }
    
   
    public void addObjects(ObjectsInSheet object)
    {
        this.objects.add(object);
    }
    
    
    public void addFields(Fields field)
    {
        this.fields.add(field);
    }
    
    
 
    
    
   
    @Override
    public boolean equals(Object obj)
    {
        return(this.name.equals(((Operations)obj).getName()));
    }
   //return the information
    @Override
    public String toString()
    {
        return "API_Name : " + this.name + this.rows + "\n" + "HTTP Operation : " + this.http_operation + "\n" + "REST URL : " + this.rest_url;
    }
}