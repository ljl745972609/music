package com.cwnu.ttpodmusic.utils;

import java.io.File;

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
