/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;

/**
 *
 * @author Mohamed Mostafa
 */
public class FinalProject extends Application {
    private Stage stage2, stage3;
    private Scene scene1, scene2, scene3;
    private GridPane gridPane1, gridPane2;
    private FileChooser fileChooser;
    private static Label apiNames = new Label();
    private File file;
    private FileChooser.ExtensionFilter extensionFilter;
    private Alert alert;
    private Button ok1 = new Button("    Ok   "),ok2 = new Button("    Ok   "),browse = new Button("Browse"),previous = new Button("Previous");
    private static TextField path, apiName; 
    private String displayKeyword = "API_NAME"; //for display all apis


    
    public void start(Stage stage) throws Exception{
        //--------------------------Scene1-----------------------//
        gridPane1 = new GridPane();
        path = new TextField();
        browse.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
        ok1.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
        path.setPrefColumnCount(20);
        path.setFont(Font.font("body", FontWeight.NORMAL, FontPosture.REGULAR, 15));

        gridPane1.setAlignment(Pos.CENTER);
        gridPane1.setHgap(20);
        gridPane1.setVgap(20);
        gridPane1.add(path, 0, 0);
        gridPane1.add(browse, 1, 0);
        gridPane1.add(ok1, 1, 1);
        scene1 = new Scene(gridPane1, 500, 300);
        stage.setResizable(false);
        stage.setTitle("Read Excel Sheet");
        stage.setScene(scene1);
        stage.show();


        //--------------------------Events-----------------------//

        ok1.setOnAction(e -> {
            if(file != null)
            {
                path.getText();
                stage2 = new Stage();
                gridPane2 = new GridPane();
                apiName = new TextField();
                previous.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
                ok2.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
                apiName.setPrefColumnCount(20);
                apiName.setFont(Font.font("body", FontWeight.NORMAL, FontPosture.REGULAR, 15));
                apiNames.setFont(Font.font("body", FontWeight.NORMAL, FontPosture.REGULAR, 15));
                gridPane2.setAlignment(Pos.CENTER);
                gridPane2.setHgap(20);
                gridPane2.setVgap(20);
                gridPane2.add(apiNames, 0, 0);
                gridPane2.add(apiName, 0, 6);
                gridPane2.add(ok2, 1, 6);
                gridPane2.add(previous, 1, 7);
                scene2 = new Scene(gridPane2, 500, 300);
                stage2.setTitle("Available APIs");
                stage2.setResizable(false);
                stage2.setScene(scene2);
                stage.close();
                stage2.show();
            }
            else
            {
                alertMessage(Alert.AlertType.WARNING, "Warning", "You didn't choose a file");
            }
        });

        browse.setOnAction(e -> {

            fileChooser = new FileChooser();
            extensionFilter = new FileChooser.ExtensionFilter("EXCEL files (*.xlsx)", "*.xlsx");
            fileChooser.getExtensionFilters().add(extensionFilter);
            file = fileChooser.showOpenDialog(stage);
            if(file != null)
            path.setText(file.toString());
        });

        previous.setOnAction(e -> {
            stage2.close();
            stage.setScene(scene1);
            stage.show();
        });
        
        ok2.setOnAction(e -> {
           if(apiName.getText().matches(this.displayKeyword)){
                try {
                    readExcelSheet();     
                } catch (IOException ex) {
                    ex.printStackTrace();
                }       
           }else{
                alertMessage(Alert.AlertType.WARNING, "Warning", "Api Not Found!!");
           }
        });    
 }
    
  
  
    private void readExcelSheet() throws IOException {
        FileInputStream inputstream = new FileInputStream(path.getText());
        XSSFWorkbook workbook = new XSSFWorkbook(inputstream);
        int no_sheets = workbook.getNumberOfSheets();
   
        //loop through all sheets in excel file
        for (int i = 0; i < no_sheets; i++)
        {
            ReadExcelSheet sheet = new ReadExcelSheet(workbook,i);
            Services service = new Services(sheet.getSheet().getSheetName());
         
            //get all operation in a sheet and determine no. of rows in this operation
            getOperations(sheet.getSheet(), service);
            
            //get all objects and fields in all operations
            for(int j = 0; j < service.getOperations().size(); j++)
            {
                int rows_start = service.getOperations().get(j).getRows_start(); // get number of start row
                int rows = service.getOperations().get(j).getRows(); //get number of end row in operation
                int cols = getNumOfCols(rows, sheet.getSheet()); //get number of colums in excel sheet
                get_objects_fields(rows_start,rows, cols, service.getOperations().get(j).getObjects(), service.getOperations().get(j).getFields(), sheet.getSheet());
                
            }
            //loop through all operations
            for(Operations op : service.getOperations())
            {
              
                for (ObjectsInSheet o : op.getObjects())
                {
                    VBox vbox = new VBox(10); //cteate new Vbox pane
                    //loop through all objects

                    for(Fields field : o.getFields())
                    {
                        
                        vbox.getChildren().add(new Label(field.getI_o()+ "\t\t\t" +o.getName()+"/"+field.getName() + "\t\t\t" + getType(field)  + "\t\t\t" + field.getAllowedvalues() + "\t\t\t" + field.getMandatory() +"\n"));

                    }
                    //loop through all objects in main object
                   
                    for(ObjectsInSheet o2 : o.getObjects())
                    {
                        vbox.getChildren().add(new Label(o2.getI_o()+ "\t\t\t" + o.getName()+"/" +o2.getName() + "\t\t\t" + o2.getName() + "\t\t\t"+ o2.getAllowedvalues() + "\t\t\t" + o2.getMandatory() +"\n"));
                    }
                    Scene scene = new Scene(vbox,600,500);
                    Stage stage = new Stage();
                    stage.setTitle(o.getName());
                    stage.setScene(scene);
                    stage.show();
                 }
               
                for(Fields field : op.getFields())
                {
                    if(!field.isField_child())
                    {
                        VBox vbox2 = new VBox(10); //cteate new hbox pane
                      
                        vbox2.getChildren().add(new Label(field.getI_o()+ "\t\t\t" + field.getName() + "\t\t\t" + getType(field) + "\t\t\t" + field.getAllowedvalues() + "\t\t\t" + field.getMandatory() +"\n"));
                        
                        Scene scene = new Scene(vbox2,600,500);
                        Stage stage = new Stage();
                        stage.setTitle(field.getName());
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }

        }
    }
            
    private void alertMessage(Alert.AlertType alertType, String title, String content)
    {
        alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
    }      

    
    /**
     *
     * @param args
     */
    public static void main(String[] args)  throws FileNotFoundException, IOException{
        System.out.println("Running..");
        // TODO code application logic here
          Application.launch(args);
    }
    public static String getType(Object obj){
        String res = "";
        if(obj instanceof Fields){
            res =  "String";
        }
       return  res;
    }
     
    
    //function to get all operation in a sheet and determine no. of rows in this operation
    public static void getOperations(XSSFSheet sheet, Services service)
    {
        int rows_start = 0;
        int rows = sheet.getLastRowNum();
        int cols = sheet.getRow(0).getLastCellNum();
        for (int r = 0; r <= rows; r++)
        {
            XSSFRow row = sheet.getRow(r);
            for (int c = 0; c < cols; c++)
            {
                //check if row = null
                if (row == null)
                {
                    break;
                }
                //check if cell = null
                XSSFCell cell = row.getCell(c);
                if (cell == null)
                {
                    break;
                }
                if (cell.getStringCellValue().contains("REST Operation Mapping"))
                {
                    if (!service.getOperations().isEmpty())
                    {
                        service.getOperations().get(service.getOperations().size() - 1).setRows(rows_start,r);
                        rows_start = r + 1;
                    }
                    XSSFRow row2 = sheet.getRow(r + 2);
                    XSSFCell http_operation_cell = row2.getCell(c);
                    XSSFCell rest_url_cell = row2.getCell(c + 1);
                    service.addOperation(new Operations(cell.getStringCellValue(),http_operation_cell.getStringCellValue(),rest_url_cell.getStringCellValue()));
                }
            }
        }
        if (!service.getOperations().isEmpty())
        {
            service.getOperations().get(service.getOperations().size() - 1).setRows(rows_start,rows);
        }
    }
    
    
    
    
    //function to get object and fields and put them in array list
    public static void get_objects_fields(int rows_start,int rows, int cols, ArrayList<ObjectsInSheet> objects, ArrayList<Fields> fields,XSSFSheet sheet)
    {
        //headlines of cells in excel sheet
        int i_o = 0; //variable to get no. of cell for i_o
        int field_name = 0; //variable to get no. of cell for field_name
        int type = 0; //variable to get no. of cell fot type
        int allowed_values = 0; //variable to get no. of cell fot allowed values

        int mandatory = 0; //variable to get no. of cell for mandatory
         
        for (int r = rows_start; r <= rows; r++)
        {
            XSSFRow row = sheet.getRow(r);
            for (int c = 0; c < cols; c++)
            {
                //check if row = null
                if (row == null)
                {
                    break;
                }
                //check if cell = null
                XSSFCell cell = row.getCell(c);
                if (cell == null)
                {
                    break;
                }
                //numbers of cell headers
                switch (cell.getStringCellValue()) {
                    case "I/o":
                        i_o = c; // get no. of cell for i_o
                        break;
                    case "Field Name":
                        field_name = c; //get no. of cell for field_name
                        break;
                    case "Type":
                        type = c;   //get no. of cell fot type
                        break;
                    case "Allowed Values":
                        allowed_values = c; //get no. of cell fot allowed values
                        break;
                    case "Mandatory":
                        mandatory = c; //get no. of cell for mandatory
                        break;
                    default:
                        break;
                }
            }
        }
        
        for (int r = rows_start; r <= rows; r++)
        {
            XSSFRow row = sheet.getRow(r);
            for (int c = 0; c < cols; c++)
            {
                //check if row = null
                if (row == null)
                {
                    break;
                }
                XSSFCell cell = row.getCell(c);
                //check if cell = null
                if (cell == null)
                {
                    break;
                }
                //determine objects
                if (cell.getStringCellValue().matches("object."))
                {
                    XSSFCell i_o_cell = row.getCell(i_o); //determine i/o of object
                    XSSFCell field_name_cell = row.getCell(field_name); //determine Field Name
                    XSSFCell allowed_values_cell = row.getCell(allowed_values); //determine allowed values of field
                    String allowed_values_string = allowed_values(allowed_values_cell.getStringCellValue()); //determine if allowed values is all or there is a limited allowed values
                    XSSFCell mandatory_cell = row.getCell(mandatory); //determine mandatory of object
                    ObjectsInSheet object = new ObjectsInSheet(cell.getStringCellValue(),i_o_cell.getStringCellValue(),allowed_values_string,mandatory_cell.getStringCellValue()); //create new object
                    object.setObject_fieldname(field_name_cell.getStringCellValue());
                    if(field_child(field_name_cell.getStringCellValue())) // check if object is a subtype of object or not
                    {
                        int index = index_main_object(field_name_cell.getStringCellValue(), objects); //determine index of main object
                        objects.get(index).addObjects(object); //add subobject to arraylist of main object
                    }
                    objects.add(object); //add new object to array list of objects
                }
                //determine fields
                if (cell.getStringCellValue().equals("string"))
                {
                    XSSFCell i_o_cell = row.getCell(i_o); //determine i/o of field
                    XSSFCell field_name_cell = row.getCell(field_name); //determine Field Name
                    String field_name_string = field_name(field_name_cell.getStringCellValue()); //call a function to split field name and determine field name

                    XSSFCell allowed_values_cell = row.getCell(allowed_values); //determine allowed values of field
                    String allowed_values_string = allowed_values(allowed_values_cell.getStringCellValue()); //determine if allowed values is all or there is a limited allowed values
                    XSSFCell mandatory_cell = row.getCell(mandatory); //determine mandatory of field
                    Fields field  = new Fields(field_name_string,i_o_cell.getStringCellValue(),allowed_values_string,mandatory_cell.getStringCellValue()); //create new field
                    fields.add(field); //add field to array list of fields
                    if(field_child(field_name_cell.getStringCellValue())) // check if field is a child of an object or not
                    {
                        field.setField_child(true);
                        int index = object_fieldname_index(field_name_cell.getStringCellValue(),field_name_string, objects);
                        objects.get(index).addFields(field); //add field to the object
                    }
                    else
                    {
                        field.setField_child(false);
                    }
                }
            }
        }
    }
    
    
    //return field name in Field Name Cell
    public static String field_name(String field_name)
    {
        String[] s = field_name.substring(1).split("/");
        return s[s.length - 1]; //return name of the field
    }
    
    
    //return index of parent class of field
    public static int object_fieldname_index(String field_name,String field_name_string, ArrayList<ObjectsInSheet> objects)
    {
        String s = field_name.replace("/" + field_name_string, ""); //determine parent object of field
        for (ObjectsInSheet object : objects)
        {
            if(object.getObject_fieldname().equals(s))
            {
                return objects.indexOf(object); //return index of the parent object
            }
        }
        return 0;
    }
    
    //return allowed values in Allowed Values Cell to determine if allowed values is all or there is a limited allowed values
    public static String allowed_values(String allowed_values)
    {
        if (allowed_values.equals(""))
        {
            return "all";
        }
        else
        {
            return allowed_values;
        }
    }
    
    //determine if field is a child
    public static boolean field_child(String field_name){
        String[] s = field_name.substring(1).split("/");
        if (s.length > 1)
        {
           return true;
        }
        return false;
    }
    
    
    //return index of main object of sub object
    public static int index_main_object(String field_name, ArrayList<ObjectsInSheet> objects){
        String[] s = field_name.substring(1).split("/");
        for (ObjectsInSheet object : objects)
        {
            if (object.getName().equals(s[s.length-2]))
            {
                return objects.indexOf(object); //return index of the main object
            }
        }
        return 0;
    }
    
    //get total no. of columns in excel sheet
    public static int getNumOfCols(int rows, XSSFSheet sheet)
    {
        for (int r = 0; r <= rows; r++)
        {
            XSSFRow row = sheet.getRow(r);
            for (int c = 0; c < 1; c++){
                //check if row = null
                if (row == null)
                {
                    break;
                }
                //check if cell = null
                XSSFCell cell = row.getCell(c);
                if (cell == null)
                {
                    break;
                }
                if(cell.getStringCellValue().equals("I/o")){
                    return sheet.getRow(r).getPhysicalNumberOfCells();
                }
            }
        }
        return 0;
    }
}