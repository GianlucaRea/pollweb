/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alessandrodorazio
 */
public class ValidazioneCampi {
    public static boolean emailPattern(String email) {
        //pattern e matcher per verificare se l'email è in un formato corretto
        Pattern emailPattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
