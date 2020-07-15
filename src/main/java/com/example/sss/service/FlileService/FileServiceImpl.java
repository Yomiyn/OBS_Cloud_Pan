package com.example.sss.service.FlileService;

import com.example.sss.dao.FileMapper;
import com.example.sss.model.domin.ObsFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title:
 * Description:对数据库的文件进行操作
 *
 * @author： pop
 * @date： 2020/7/14 11:11
 * @vertion： V1.0.1
 */
@Service
public class FileServiceImpl implements FileService{
    @Autowired
    private FileMapper fileMapper;

    //---------------------private--------------
    /**
    * description:判断是不是文件夹
    * @param path 路径
    * @return 是则返回true
    */
    private boolean isDir(String path){
        return path.endsWith("/");
    }


    //---------------------public---------------

    /**
    * description:根据路径获取下面的文件列表
    * @param userId 用户id
    * @param path 文件夹路径
    * @return 该文件夹下的所有文件（不包括子文件夹里的文件）
    */
    @Override
    public List<ObsFile> selectFileListByPath(Integer userId, String path){
        if (isDir(path)) {
            List<ObsFile> list = fileMapper.selectFileListByPath(userId, path);
            for(ObsFile item:list){
                if (item.getPath().equals(path)){
                    list.remove(item);
                    break;
                }
            }
            return list;
        }
        else
            return null;
    }

    /**
    * description:向数据库添加文件信息
    * @param file 文件信息
    */
    @Override
    public void addFile(ObsFile file){
        fileMapper.addFile(file);
    }

    /**
    * description:向数据库删除文件的信息
    * @param userId 用户id
    * @param path 文件的的路径
    */
    @Override
    public void deleteFile(Integer userId,String path){
        fileMapper.deleteFile(userId, path);
    }

    /**
    * description:更改文件命
    * @param userId 用户id
    * @param path 文件路径
    * @param newName 新的文件名
    */
    @Override
    public void updateFileName(Integer userId,String path,String newName){
        String[] result =path.split("/");
        String newPath = "";
        if (path.startsWith("https")){
            newPath = "https:/";
        }else{
            newPath = "http:/";
        }
        result[result.length-1]=newName;
        for (int i=2;i<result.length;i++){
            newPath +='/'+result[i];
        }
        if (isDir(path)){
            newPath+='/';
        }
        fileMapper.updateFileName(userId, path,newPath, newName);
    }

    /**
    * description:更改文件路径，即移动文件
    * @param userId 用户id
    * @param oldPath 原来的路径
    * @param newPath 新的路径
    */
    @Override
    public void updateFilePath(Integer userId,String oldPath,String newPath){
        fileMapper.updateFilePath(userId, oldPath, newPath);
    }
}