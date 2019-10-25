package db;

import exception.DataBaseException;
import model.Storage;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LocalBase implements Storage {

    private String storagePath;
    private String storageName;
    private boolean isInitialize;
    private User admin;

    public LocalBase(String parh,String name){
        this.storagePath = parh;
        this.storageName = name;
    }


    @Override
    public void iniStorage() throws DataBaseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("username");
        String username = scanner.nextLine();
        System.out.println("password");
        String password = scanner.nextLine();
        if(auth(username,password)){
            System.out.println("uspesno logovanje");
        }
        else{
            throw new DataBaseException("Ne postoji korisink");
        }

        if(storagePath==null || storagePath.equals("")){
            throw new DataBaseException("storage path is empty");

        }
        if(storageName==null || storageName.equals("")){
            throw new DataBaseException("Name of the base doesnt exist");

        }
        Path dirPath = Paths.get(storagePath);
        Path b = Paths.get(storagePath+ File.separator+storageName);
        if(Files.exists(dirPath)){
            if(Files.exists(b)==false) {
                try {
                    Files.createDirectory(b);
                    System.out.println("Base created!");

                } catch (Exception e) {

                }
            }
        }
        else
            throw new DataBaseException("Path doesnt exist");
        if(Files.exists(b))
            System.out.println("Base already exists");

    }

    @Override
    public String getStoragePath() {
        return storagePath;
    }

    @Override
    public String getStorageName() {
        return storageName;
    }

    public boolean isInitialize() {
        return isInitialize;
    }

    public void setInitialize(boolean initialize) {
        isInitialize = initialize;
    }

    @Override
    public boolean auth(String username, String password) {
        if(username.equals("123") && password.equals("123")) {
            admin = new User();
            admin.setUserName(username);
            admin.setPassword(password);
            return true;
        }
        if(checkFile(username,password))
            return true;

        return false;
    }

    @Override
    public User getUser() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
    private boolean checkFile(String username,String password){
        try{
            FileReader fileReader = new FileReader(new File(System.getProperty("user.dir")+File.separator+"users.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String [] parsovano = bufferedReader.readLine().split(",");
            if(username.equals(parsovano[0]) && password.equals(parsovano[1])){
                admin = new User();
                admin.setUserName(username);
                admin.setPassword(password);
                return true;
            }
            bufferedReader.close();
            fileReader.close();

            return false;
        }
        catch(Exception e){

        }
        return false;
    }
}
