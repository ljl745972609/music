package com.cwnu.ttpodmusic.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dell on 2018/8/4.
 */

public class FileUtil {
    public static String ROOT = Constat.ROOTPATH;
    public static String MUSIC = ROOT+"/music";    // 音乐文件夹
    public static String LRC = ROOT+"/lrc";        // 歌词文件夹
    public static String IMAGE = ROOT+"/image";    // 图片文件夹

    /**
     * 初始化文件将
     */
    public static void createDrec(){
       createDrec(ROOT);
       createDrec(MUSIC);
       createDrec(LRC);
       createDrec(IMAGE);
    }

    public static String[] findMusic(){
        File musics = new File(MUSIC);
        if(musics.exists()){
            String[] list = musics.list();
            return list;
        }
        return null;
    }


    public static int writerFile(String path,String fileName,byte[] bytes){
        int result = 0;
        File file = new File(path,fileName);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
            result = 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 创建文件夹
     * @param drec 文件夹名称
     */
    private static void createDrec(String drec){
        File f = new File(drec);
        if(!f.exists()){
            f.mkdirs();
        }
    }
}
