/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.data.dao;

import com.company.pollweb.utility.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author alessandrodorazio
 */
public class LoginDao {
    public static boolean validate(String email,String password){  
        boolean status=false;  
        try{  
            
            Connection con = Database.getConnection();
            PreparedStatement ps=con.prepareStatement("select * from pollweb.Utente where email=? and password=?");  
            ps.setString(1,email);  
            ps.setString(2,password);  

            ResultSet rs=ps.executeQuery();  
            status=rs.next();  
        } catch (Exception e) {System.out.println(e);}  
        return status;  
    }  
}
