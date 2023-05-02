/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

/**
 *
 * @author  Mohamed Mostafa
 */
public class Fields {
    /*Data Fields*/
    private String name;
    private String i_o;
    private String allowedvalues;
    private String mandatory;
    private boolean field_child;
    
    //no-arg constructor

    public Fields() {
    }
    //constructor

    public Fields(String name, String i_o, String allowedvalues, String mandatory) {
        this.name = name;
        this.i_o = i_o;
        this.mandatory = mandatory;
        this.allowedvalues = allowedvalues;
    }

    
    
    /***************************Accessors************************************/
    
    
    public String getName() {
        return name;
    }

    public String getI_o() {
        return i_o;
    }
    
    public String getMandatory() {
        return mandatory;
    }

    public String getAllowedvalues() {
        return allowedvalues;
    }

    public boolean isField_child() {
        return field_child;
    }
    
    
    
     /*******************************Mutators***************************************/
    
    public void setField_child(boolean field_child) {
        this.field_child = field_child;
    }
    
    @Override
    public boolean equals(Object o)
    {
        return(this.name.equals(((Fields)o).getName()));
    }
    
     //return the information
    @Override
    public String toString()
    {
        return "Name : " + this.name + "\n" + "Mandatory : " + this.mandatory + "\n" + "Allowed Values : " + this.allowedvalues + "\n";
    }
    
}