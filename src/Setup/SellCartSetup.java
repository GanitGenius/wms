/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Setup;

import Data.SellCart;
import Gateway.SellCartGateway;
import dataBase.DBConnection;
import dataBase.DBProperties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GanitGenius
 */
public class SellCartSetup {
    
    SellCartGateway sellCartGateway = new SellCartGateway();
    
    DBConnection dbCon = new DBConnection();
    Connection con = dbCon.geConnection();
    PreparedStatement pst;
    ResultSet rs;
    
    DBProperties dBProperties = new DBProperties();
    String db = dBProperties.loadPropertiesFile();
    
    public void sell(SellCart sellCart) {
        
        updateCurrentQuentity(sellCart);
        sellCartGateway.save(sellCart);
        
    }
    
    public void updateCurrentQuentity(SellCart sellCart) {
        
        System.out.println("In Update");
        int oQ = Integer.parseInt(sellCart.oldQuentity);
        int nQ = Integer.parseInt(sellCart.quantity);
        int uQ = (oQ - nQ);
        System.out.println("NOW QUANTITY IS: " + uQ);
        String updatedQuentity = String.valueOf(uQ);
        
        try {
            System.out.println("In Processing Update");
            
            pst = con.prepareStatement("select * from " + db + ".BinStatus where ProductId=?");
            pst.setString(1, sellCart.productID);
            rs = pst.executeQuery();
            while (rs.next()) {
                int tmp = Integer.parseInt(rs.getString(3));
                System.out.println("ProductNumber = " + tmp);
                String type = rs.getString(1);
                if(nQ == 0)
                    break;
                
                if (tmp > nQ) {
                    
                    tmp = tmp - nQ;
                    PreparedStatement pst2 = con.prepareStatement("update " + db + ".BinStatus set ProductNumber=? where Type=?");
                    pst2.setString(1, String.valueOf(tmp));
                    pst2.setString(2, type);
                    pst2.executeUpdate();
                    break;
                    
                }
                else{
                    PreparedStatement pst2 = con.prepareStatement("update " + db + ".BinStatus set ProductNumber=? , ProductId=? where Type=?");
                    nQ = nQ-tmp;
                    pst2.setString(1, "");
                    pst2.setString(2, "");
                    pst2.setString(3, type);
                    pst2.executeUpdate();
                }

            }
            
            pst = con.prepareStatement("update " + db + ".Products set Quantity=? where Id=?");
            pst.setString(1, updatedQuentity);
            pst.setString(2, sellCart.Id);
            pst.executeUpdate();
            
            pst = con.prepareStatement("select * from " + db + ".Customer where Id=?");
            pst.setString(1, sellCart.customerID);
            rs = pst.executeQuery();
            double t = 0;
            String s = "";
            while (rs.next()) {
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
                System.out.println(rs.getString(5));
                s = rs.getString(5);
            }
            if (s != null && !s.isEmpty()) {
                t = Double.parseDouble(s);
                t += Double.parseDouble(sellCart.totalPrice);
            } else {
                t = Double.parseDouble(sellCart.totalPrice);
            }
            
            pst = con.prepareStatement("update " + db + ".Customer set TotalBuy=? where Id=?");
            pst.setString(1, String.valueOf(t));
            pst.setString(2, sellCart.customerID);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(SellCartSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Update Complete");
        
    }
    
}
