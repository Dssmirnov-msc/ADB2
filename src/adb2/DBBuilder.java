/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adb2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author test2020
 */
public class DBBuilder {
    private String DBName;
    private HashMap<String,HashMap> DataStruct;
    
    public DBBuilder(String DBName) {
        this.DBName = DBName;
        formDataStruct();
    }
    
    
    
    
    public String getDBName() {
        return DBName;
    }

    public void setDBName(String DBName) {
        this.DBName = DBName;
    }
    
    public HashMap<String,HashMap> getDataStruct(){
        return DataStruct;
    }
    
    public ArrayList<HashMap> getData(String TabName){
        /*
        HashMap<String,String> TableStruct = DataStruct.get(TabName);
        
        int V = 10; 
        ArrayList<HashMap> Data = new ArrayList<HashMap>();
        
        for (int i = 0; i < V; i++) {
            
        }
        */
        ArrayList<HashMap> Data;
        if (TabName.equals("Sir")) {
            Data = createSir();
        } else {
            Data = createChild();
        }
        
        
        return Data;
    }

    private void formDataStruct() {
        DataStruct = new HashMap();
        
        HashMap<String,String> Tab1 = new HashMap();
        HashMap<String,String> Tab2 = new HashMap();
        
        Tab1.put("ID", "INT");
        Tab1.put("Name", "TEXT");
        Tab1.put("Generation", "DOUBLE");
        
        Tab2.put("ID", "INT");
        Tab2.put("SirID", "INT");
        Tab2.put("Name", "TEXT");
        Tab2.put("Generation", "DOUBLE");
        
        DataStruct.put("Sir", Tab1);
        DataStruct.put("Child", Tab2);
    }

    private ArrayList<HashMap> createSir() {
        ArrayList<HashMap> Data = new ArrayList<HashMap>();
        HashMap Sir1 = new HashMap();
        Sir1.put("ID", 1);
        Sir1.put("Name","AA");
        Sir1.put("Generation", 5.0);
        Data.add(Sir1);
        
        return Data;
    }
    
    private ArrayList<HashMap> createChild() {
        ArrayList<HashMap> Data = new ArrayList<HashMap>();
        HashMap Child1 = new HashMap();
        Child1.put("ID", 1);
        Child1.put("SirID", 1);
        Child1.put("Name","AA");
        Child1.put("Generation", 5.0);
        Data.add(Child1);
        
        return Data;
    }

    String getAllSirQuery() {
        String SQLQuery = "SELECT * FROM Sir";
        return SQLQuery;
    }

    public void handle(ResultSet res, String TabName) throws SQLException {
        while (res.next()) {
            switch (TabName){
                case ("Sir"): rowSirHandle(res); break;
                case ("Child") : rowChildHandle(res); break;
                default: throw new Error(" Нет такой таблицы");
            }
           
        }
        
    }
    
    private void rowSirHandle(ResultSet res) throws SQLException{
        System.out.println(res.getInt("Sir.ID"));
        System.out.println(res.getString("Name"));
        System.out.println(res.getString("Generation"));
    }

    private void rowChildHandle(ResultSet res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
