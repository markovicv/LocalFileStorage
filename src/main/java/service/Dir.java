package service;

import exception.*;
import model.Directory;
import model.Zipper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.io.File;

public class Dir implements Directory {

    private String dirPath;

    public Dir(String path){
        this.dirPath = path;

    }
    public Dir(){

    }



    @Override
    public void create(String path, String name) throws CreateDirException {
        Path directoryPath;
        if(path!=null && path.length()!=0)
            directoryPath = Paths.get(path);
        else
            directoryPath = Paths.get(name);
        if(name==null || name.length()==0)
            throw new CreateDirException("name of directory is empty");

        Path p = Paths.get(directoryPath+ java.io.File.separator+name);
        if(Files.exists(directoryPath) && Files.exists(p)==false){
            try {
                Files.createDirectory(p);
                System.out.println("dir created!!!");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else
            throw new CreateDirException("Directory wasnt created");
    }
    @Override
    public void uploadDirs(List<File> dirs,String path) throws UploadMultipleFileException{
        if(path==null || path.equals(""))
            throw new UploadMultipleFileException("there is no path");
        Path dirPath = Paths.get(path);
        if(Files.exists(dirPath) && dirs.size()>0){
            for(File d:dirs){
                try {
                    FileUtils.copyDirectory(new File(dirPath.toString()), new File(path + File.separator + d.getName()));

                }
                catch(Exception e){
                    IOException exception = (IOException)e;
                    exception.printStackTrace();
                }
                System.out.println("Succes");
            }
        }
        else
            throw new UploadMultipleFileException("Direcories were not created!");
    }
    @Override
    public void uploadZipDirs(List<File>dirs,String dest,String zipName) throws ZipException{
        if(dirs==null || dirs.equals(""))
            throw new ZipException("local storage not initialized");
        if(dirs==null || dirs.size()==0)
            throw new ZipException("empty list directories");
        Path dirPath = Paths.get(dest);
        Zipper zipper = new Zipper();

        if(Files.exists(dirPath)){
            for(File d:dirs){
                zipper.zipDir(d,dirPath.toString(),zipName);

            }
            System.out.println("Succes");
        }
        else
            throw new ZipException("Zip wasnt successful");

    }
    @Override
    public void zipDir(File dir,String path,String zipName) throws ZipException{
        if(dir==null || dir.toString().equals(""))
            throw new ZipException("Directory is empty");
        if(path==null || path.equals(""))
            throw new ZipException("Path is empty");
        if(!(new File(dir.getAbsolutePath()).isDirectory()))
            throw new ZipException("Not a dir!");
        Path dirPath = Paths.get(path);
        if(!Files.exists(dirPath))
            throw new ZipException("Not a dir!");
        else{
            Zipper zipper = new Zipper();
            zipper.zipDir(dir,dirPath.toString(),zipName);
            System.out.println("dir zipped!");
        }
    }

    @Override
    public void delete(String pathName) throws DeleteDirException {

        if(pathName==null || pathName.length()==0)
            throw new DeleteDirException("path name is empty");
        Path dirPath = Paths.get(pathName);
        if(!(new File(pathName).isDirectory())){
            throw new DeleteDirException("Not a dir!!");
        }

        if(Files.exists(dirPath)){
            try{
                File file = new File(pathName);
                FileUtils.deleteDirectory(file);
                System.out.println("fajl upesno izbrisan!");
            }
            catch(Exception e){
                IOException exception = (IOException)e;
                exception.printStackTrace();
            }
        }
        else
            throw new DeleteDirException("directory does not exist");

    }

    @Override
    public void move(String srcPath, String dstPath) throws MoveDirException {
        if(srcPath==null || srcPath.equals(""))
            throw new MoveDirException("source path invalid");
        if(dstPath==null || dstPath.equals(""))
            throw  new MoveDirException("destination path invalid");
        if(!(new File(srcPath).isDirectory()))
            throw new MoveDirException("Not a dir");
        Path sourceP = Paths.get(srcPath);
        Path destinationP = Paths.get(dstPath);
        if(Files.exists(sourceP) && Files.exists(destinationP)){
            try{
                FileUtils.moveDirectory(new File(srcPath),new File(dstPath+File.separator+srcPath.substring(srcPath.lastIndexOf(File.separator)+1)));
                System.out.println("uspesno pomeren fajl");

            }
            catch(Exception e){
                IOException exception = (IOException) e;
                exception.printStackTrace();

            }
        }
        else
            throw new MoveDirException("Directory wasnt moved");

    }

    @Override
    public void rename(String path, String newName) throws RenameException {
        Path dirPath=null;
        if(path!=null && path.length()>0)
            dirPath = Paths.get(path);
        if(newName==null || newName.equals(""))
            throw new RenameException("new name is not present");

        if(Files.exists(dirPath)) {
            try {
                File currDir = new File(path);
                File newDir = new File(currDir.getParent() + File.separator + newName);
                currDir.renameTo(newDir);
                System.out.println("Fajlu uspesno promenjeno ime");
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
        else
            throw new RenameException("Directory does not exist");

    }

    @Override
    public void download(String s, String s1) throws DownloadDirException {

    }

    @Override
    public void upload(String s, String s1) throws UploadDirException {

    }

    @Override
    public List<File> getByExtension(String dirPath, String[] extensions,boolean sort) throws FileListException {
        if(dirPath==null || dirPath.equals(""))
            throw new FileListException("Path wasnt selected");
        if(extensions==null || extensions.length==0)
            throw new FileListException("extensions were not listed");
        Path directoryPath = Paths.get(dirPath);
        if(Files.exists(directoryPath)){
            List<File> fileList = new ArrayList<>(FileUtils.listFiles(new File(dirPath),extensions,true));
            System.out.println("uspesno izlistano po ekstenzijama");
            if(sort)
                Collections.sort(fileList);
            return fileList;
        }
        else
            throw new FileListException("couldnt get files by extension");
    }

    @Override
    public List<File> getFileNamesInDir(String path,boolean sort) throws FileListException {
        if(path==null || path.equals(""))
            throw new FileListException("path was empty");
        Path dirPath = Paths.get(path);
        if(Files.exists(dirPath)){
            List<File> fileList = new ArrayList<>(FileUtils.listFiles(new File(path),null,true));
            System.out.println("uspesno izlistano po imenima");
            if(sort)
                Collections.sort(fileList);
            return fileList;
        }
        else
            throw new FileListException("Couldnt list all the files");
    }

    @Override
    public List<File> getAllDirs(String path,boolean sort) throws DirecrotyListException {
        if(path==null || path.equals(""))
            throw new DirecrotyListException("path is empty");
        List<File> dirs;
        Path dirPath = Paths.get(path);

        if(Files.exists(dirPath)){
            dirs = new ArrayList<>(FileUtils.listFilesAndDirs(new File(path),new NotFileFilter(TrueFileFilter.INSTANCE), DirectoryFileFilter.DIRECTORY));
            if(sort)
                Collections.sort(dirs);
        }
        else
            throw new DirecrotyListException("Directorys could be listed");

        return dirs;


    }


    public void setPath(String path){
        this.dirPath = path;

    }
    public String getPath(){
        return dirPath;
    }
}
