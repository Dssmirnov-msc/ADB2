/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adb2;

import com.healthmarketscience.jackcess.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.ucanaccess.jdbc.UcanaccessDriver;

/**
 *
 * @author test2020
 */
public class DBProvider {
    
    Database CDB;
    DBBuilder builder;
    Connection conn;
    Statement stat;
    
    public DBProvider(DBBuilder builder) {
        this.builder = builder;
    }
    
    public void create() throws IOException{
        CDB = DatabaseBuilder.create(Database.FileFormat.V2010, new File(builder.getDBName()+".accdb"));
        createTables();
        CDB.close();
    }

    private void createTables() {
        HashMap<String, HashMap> DataStruct = builder.getDataStruct();
        
        
        DataStruct.forEach((String Name, HashMap TableStruct) -> {
            try {
                createTable(Name,TableStruct);
            } catch (IOException ex) {
                Logger.getLogger(DBProvider.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void createTable(String Name, HashMap<String, String> TableStruct) throws IOException {
        TableBuilder TB = new TableBuilder(Name);
        TableStruct.forEach((String ColName, String ColType) -> 
                TB.addColumn(new ColumnBuilder(ColName, DataType.valueOf(ColType))));
        Table newTab = TB.toTable(CDB);
        fillTable(newTab);
    }

    private void fillTable(Table newTab) throws IOException {
        newTab.addRowsFromMaps(builder.getData(newTab.getName()));
    }
    
    public void connect() throws SQLException{
        String FullPath = "C:/Java/ADB2/" + builder.getDBName()+".accdb";
        conn = DriverManager.getConnection("jdbc:ucanaccess://" + FullPath,"","");
        System.err.println(conn.getClientInfo().toString());
        stat = conn.createStatement();
    }
    
    public void getAllSirs() throws SQLException{
        String SQLQuery = getQuery(1); 
        boolean A = stat.execute(SQLQuery);
        System.out.println(A);
        ResultSet res = stat.getResultSet();
        builder.handle(res, "Sir");
    }
    
    public void close() throws SQLException{
        conn.close();
    }

    private String getQuery(int qId) {
        String SQLQuery;
        switch(qId){
            case (1): 
                 SQLQuery = builder.getAllSirQuery();
                 break;
            default: 
                System.err.println("Не верный идентификатор запроса");
                SQLQuery = null;
                throw new Error("Не верный идентификатор запроса");
        }
        return SQLQuery;
    }
    
}
