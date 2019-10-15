package model;

import exception.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


public class LocalFile implements FileOrdinary {
    Path path;

    public LocalFile(Path path){
        this.path = path;
    }
    public LocalFile(){

    }

    @Override
    public void create(String path, String name) throws CreateFileException {
        Path filePath = null;
        if(path!= null && path.length()>0)
            filePath = Paths.get(path);
        if(name==null || name.equals(""))
            throw new CreateFileException("Name is empty");

        Path p = Paths.get(filePath+  java.io.File.separator+name);
        if(Files.exists(filePath) && Files.exists(p)==false){
            try{
                Files.createFile(p);
                System.out.println("Uspesno kreiran fajl");
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
        else
            throw new CreateFileException("File wasnt created");

    }

    @Override
    public void delete(String pathName) throws DeleteFileException {
        if(pathName==null || pathName.equals(""))
            throw new DeleteFileException("path name is empty");
        Path filePath = Paths.get(pathName);
        if(Files.exists(filePath)){
            try{
                Files.delete(filePath);
                System.out.println("Uspesno obrisan fajl");
            }
            catch(Exception e){
                IOException exception = (IOException)e;
                e.printStackTrace();

            }
        }
        else
            throw new DeleteFileException("File wasnt deleted");
    }

    @Override
    public void move(String srcPath, String dstPath) throws MoveFileException {
        if(srcPath==null || srcPath.equals(""))
            throw new MoveFileException("source path not found");
        if(dstPath==null || dstPath.equals(""))
            throw new MoveFileException("destination path not found");
        Path sPath =  Paths.get(srcPath);
        Path dPath = Paths.get(dstPath);
        if(Files.exists(sPath) && Files.exists(dPath)){
            try {
                Files.move(sPath, Paths.get(dPath + File.separator + srcPath.substring(srcPath.lastIndexOf(File.separator) + 1)), StandardCopyOption.REPLACE_EXISTING);
            }
            catch(Exception e){
                IOException exception = (IOException)e;
                exception.printStackTrace();
            }
        }
        else
            throw new MoveFileException("paths not found");
    }

    @Override
    public void rename(String path, String newName) throws RenameException {
        if(newName==null || newName.equals(""))
            throw new RenameException("name is empty");
        if(path==null || path.equals(""))
            throw new RenameException("path is empty");
        Path pathFile = Paths.get(path);
        if(Files.exists(pathFile)){
            try{
                File currFile = new File(path);
                File newFile = new File(currFile.getParent()+File.separator+newName);
                currFile.renameTo(newFile);
                System.out.println("Uspesno promenjeno ime fajla!");
            }
            catch(Exception e){
                IOException exception = (IOException)e;
                e.printStackTrace();
            }
        }
        else
            throw new RenameException("File doesnt exist");

    }

    @Override
    public void download(String s, String s1) throws DownloadFileException {

    }

    @Override
    public void copy(String srcPath, String dstPath) throws CopyFileException {
        if(srcPath==null || srcPath.equals(""))
            throw new CopyFileException("source path not found");
        if(dstPath==null || dstPath.equals(""))
            throw new CopyFileException("destination path not found");
        Path sPath = Paths.get(srcPath);
        Path dPath = Paths.get(dstPath);

        if(Files.exists(sPath) && Files.exists(dPath)){
            try{
                Files.copy(sPath,Paths.get(dPath+File.separator+srcPath.substring(srcPath.lastIndexOf(File.separator)+1)),StandardCopyOption.REPLACE_EXISTING);
            }
            catch(Exception e){
                IOException exception = (IOException)e;
                exception.printStackTrace();
            }
        }
        else
            throw new CopyFileException("files dont exist");


    }

    @Override
    public void uploadZip(String s, String s1, String s2) throws CopyFileException {

    }

    @Override
    public void uploadMultipleFile(List<File> list, String s) throws UploadMultipleFileException {

    }

    @Override
    public void uploadListToZip(List<File> list, String s, String s1) throws UploadMultipleFileException {

    }
}
