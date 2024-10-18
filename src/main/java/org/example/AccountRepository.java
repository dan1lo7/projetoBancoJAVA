package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private static final String caminhoDoArquivo = "src/main/java/resources/accounts.json";


    public static List<Account> getAccounts() {
        // Trazer os dados de um arquivo json
        String json = "";

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoDoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                json += linha;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Transformar texto obtido em objetos Account com Gson
        Gson gson = new Gson();
        Type accountListType = new TypeToken<List<Account>>(){}.getType();

        List<Account> accounts = gson.fromJson(json, accountListType);

        return accounts;
    }

    public static void saveAccounts(List<Account> accountList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Transformar objetos Account em texto com Gson
        String json = gson.toJson(accountList);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoDoArquivo))) {
            bw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
