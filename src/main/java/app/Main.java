package app;

import exception.*;
import model.LocalDir;
import model.LocalFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        LocalDir localDir = new LocalDir();
        LocalFile localFile = new LocalFile();


        try{

         //   List<File> dirs = localDir.getAllDirs("/home/vukasin/Desktop/AA",false);
         //   System.out.println(dirs.get(0).getName());
          //  localDir.zipDir(dirs.get(0),"/home/vukasin/Desktop","PRRRR");
          //  localFile.move("/home/vukasin/Desktop/code.txt","/home/vukasin/Prevodioci");
            localFile.copy("/home/vukasin/Desktop/code.txt","/home/vukasin/Prevodioci");
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
}
