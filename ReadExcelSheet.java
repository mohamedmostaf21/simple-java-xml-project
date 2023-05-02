/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Mohamed Mostafa
 */
public class ReadExcelSheet {
    private String sheet_name;
    private int sheet_number; //sheet number in workbook
    private XSSFSheet sheet;  //sheet in workbook
    
    /***************888arg constructor*******************/

    public ReadExcelSheet(XSSFWorkbook workbook,int sheet_number) throws FileNotFoundException, IOException {
        if ((sheet_number + 1) <= workbook.getNumberOfSheets())
        {
            this.sheet = workbook.getSheetAt(sheet_number);
            this.sheet_name = this.sheet.getSheetName();
        }
        else
        {
            this.sheet = workbook.getSheetAt(workbook.getNumberOfSheets() - 1);
            this.sheet_name = this.sheet.getSheetName();
        }
    }

     /***************************Accessors****************************/
    public String getSheet_name()
    {
        return sheet_name;
    }

    public int getSheet_number() {
        return sheet_number;
    }

   
    public XSSFSheet getSheet() {
        return sheet;
    }

   
    @Override
    public boolean equals(Object obj)
    {
        return(this.sheet_name.equals(((ReadExcelSheet)obj).getSheet_name()));
    }
    
    //return the information
    @Override
    public String toString() {
        return this.sheet_name;
    }
    
    
    
}