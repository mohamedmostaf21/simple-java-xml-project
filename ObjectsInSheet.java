/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;


import java.util.ArrayList;

/**
 *
 * @author Mohamed Mostafa
 */
public class ObjectsInSheet extends Fields{
    private String object_fieldname; //name of object in field_name tab
    private ArrayList<Fields> fields = new ArrayList<Fields>();
    private ArrayList<ObjectsInSheet> objects = new ArrayList<ObjectsInSheet>();
    
    //Empty Constructor
    public ObjectsInSheet()
    {}
    
    //argConstructor
    public ObjectsInSheet(String name,String i_o,String allowedvalues, String mandatory) {
        super(name, i_o, allowedvalues, mandatory);
    }
    
     /***************************Accessors************************************/

    public ArrayList<Fields> getFields() {
        return fields;
    }

    public ArrayList<ObjectsInSheet> getObjects() {
        return objects;
    }

    public String getObject_fieldname() {
        return object_fieldname;
    }
    
    
    /*******************************Mutators***************************************/

    public void setObject_fieldname(String object_fieldname) {
        this.object_fieldname = object_fieldname;
    }
    
    
    
    //for add new field in the array list of fields
    public void addFields(Fields field) {
        this.fields.add(field);
    }
    
    //for add new objects in the array list of objects
    public void addObjects(ObjectsInSheet object){
        this.objects.add(object);
    }
    
     //looping on the array list to print fields
    public void print_fields()
    {
        for (Fields field : fields)
        {
            System.out.println(field);
        }
    }
    
    //looping on the array list to print objects
    public void print_object()
    {
        for(ObjectsInSheet o : objects)
        {
            System.out.println(o);
        }
    }
    
  
  
  
    @Override
    public boolean equals(Object o)
    {
        return(this.getName().equals(((ObjectsInSheet)o).getName()));
    }
    //return the information
    @Override
    public String toString()
    {
        return "Object Name : " + this.getName() + "\n" + "I/o : " + this.getI_o() + "\n" + "Mandatory : " + this.getMandatory() + "\n" 
                +"Fields : " + "\n" +  this.fields + "\n" + "Objects : " + "\n" + this.objects;
    }
    
}