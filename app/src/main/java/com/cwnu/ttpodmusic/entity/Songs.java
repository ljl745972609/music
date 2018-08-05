package com.cwnu.ttpodmusic.entity;

/**
 * Created by dell on 2018/8/5.
 * 歌曲的实体类
 */

public class Songs {

    private int id;             // 歌曲ID
    private String name;        // 歌曲名称
    private String artist;      // 歌手姓名
    private String priUrl;      // 歌曲的图片
    private int artistId;       // 歌手ID

    public Songs() {
    }

    public Songs(int id, String name, String artist, String priUrl, int artistId) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.priUrl = priUrl;
        this.artistId = artistId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPriUrl() {
        return priUrl;
    }

    public void setPriUrl(String priUrl) {
        this.priUrl = priUrl;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "Songs{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", priUrl='" + priUrl + '\'' +
                ", artistId=" + artistId +
                '}';
    }
}
