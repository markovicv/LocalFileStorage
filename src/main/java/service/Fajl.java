package service;

import exception.*;
import model.FileOrdinary;
import model.Zipper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


public class Fajl implements FileOrdinary {
    Path path;

    public Fajl(Path path){
        this.path = path;
    }
    public Fajl(){

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
    public void upload(String srcPath, String dstPath) throws UploadFileException {

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
    public void uploadZip(File file, String dstPath) throws UploadFileException {
        if(dstPath==null || dstPath.equals(""))
            throw new UploadFileException("destination path is empty");
        if(!file.exists())
            throw new UploadFileException("file does not exist");
        Path dPath = Paths.get(dstPath);
        Zipper zipper = new Zipper();
        if(Files.exists(dPath)){
            zipper.zipFile(file,dstPath,file.getName().substring(0, file.getName().lastIndexOf('.')));
        }
        else
            throw new UploadFileException("unable to zip a file");


    }

    @Override
    public void uploadMultipleFile(List<File> files, String dstPath) throws UploadMultipleFileException {
        if(files==null || files.size()==0)
            throw new UploadMultipleFileException("list of files are empty");
        if(dstPath==null || dstPath.equals(""))
            throw new UploadMultipleFileException("path is empty");
        Path dPath = Paths.get(dstPath);
        if(Files.exists(dPath)){
            for(File f:files){
                try {
                    Path p = Paths.get(dstPath + File.separator + f.getName());
                    Files.copy(Paths.get(f.getAbsolutePath()), p, StandardCopyOption.REPLACE_EXISTING);
                }
                catch(Exception e){
                    IOException exception = (IOException)e;
                    exception.printStackTrace();
                }
            }
        }
        else
            throw new UploadMultipleFileException("could upload files");
    }

    @Override
    public void uploadMultipleFilesToZipFiles(List<File> files, String dstPath) throws UploadMultipleFileException {
        if(files==null || files.size()==0)
            throw new UploadMultipleFileException("list of files are empty");
        if(dstPath==null || dstPath.equals(""))
            throw new UploadMultipleFileException("path is emmty");
        Path dPath = Paths.get(dstPath);
        Zipper zipper = new Zipper();
        if(Files.exists(dPath)){
            for(File f:files){
                System.out.println("AAAAAAAAA");
                zipper.zipFile(f,dstPath,f.getName().substring(0, f.getName().lastIndexOf('.')));
            }
        }
        else
            throw new UploadMultipleFileException("Couldnt zip files");
    }
}
