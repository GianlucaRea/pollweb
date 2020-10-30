package com.company.pollweb.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GeneratoreCSV {

    private GeneratoreCSV(){ }

    public static void nuovoCSV(List<List<String>> risultati, String titoloSondaggio) throws IOException {
        File file = new File(titoloSondaggio+".csv");
        if(!file.exists()) {
            file.createNewFile();
        }
        FileWriter csvWriter = new FileWriter(file);
        csvWriter.append("Domanda");
        csvWriter.append(",");
        csvWriter.append("Risposta");
        csvWriter.append("\n");

        for (List<String> rowData : risultati) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public static String nuovaStringaCSV(List<List<String>> result) throws IOException {
        String csvWriter="Domanda,Risposta\n";
        for (List<String> rowData : result) {
            csvWriter += String.join(",", rowData)+"\n";
        }
        return csvWriter;
    }
}
