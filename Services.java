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

import java.util.ArrayList;


public class Services{
    private String name_sheet;
    private ArrayList<Operations> operations = new ArrayList<>();
    
    /*no arg constructor */

    public Services(String name_sheet) {
        this.name_sheet = name_sheet;
    }

   
    
    /*********************Accessors*********************************/

    public String getName_sheet() {
        return name_sheet;
    }

    public ArrayList<Operations> getOperations() {
        return operations;
    }
    
    
    public void addOperation(Operations op)
    {
        this.operations.add(op);
    }
    
      //looping on the array list to print operations
    public void print()
    {
        for (Operations op : operations)
        {
            System.out.println(op);
        }
    }
    
    
    
    @Override
    public boolean equals(Object obj)
    {
        return(this.name_sheet.equals(((Services)obj).getName_sheet()));
    }
    
    
    //return information
    @Override
    public String toString()
    {
        return this.name_sheet + "\n" +  this.operations;
    }
}