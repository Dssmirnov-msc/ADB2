/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adb2;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author test2020
 */
public class ADB2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException {
        DBBuilder builder = new DBBuilder("TheBestDB");
        DBProvider provider = new DBProvider(builder);
        provider.create();
        provider.connect();
        provider.getAllSirs();
        provider.close();
        
    }
    
}
