/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minispotify;

/**
 *
 * @author dnyyy
 */
public class Song {
//Varialbes:
    private String name;
    private String artist;
    private String style;
    private int length;
    private String albumName;

//Constructors:
    public Song(String name, String artist, String style, int length) {
        this.name = name;
        this.artist = artist;
        this.style = style;
        this.length = length;
    }
    
    public Song(String name, String artist, String style, int length, String albumName) {
        this.name = name;
        this.artist = artist;
        this.style = style;
        this.length = length;
        this.albumName = albumName;
    }
    
//Functions:
    
    //Getters:
    public String getName() {
        return name;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public String getStyle() {
        return style;
    }
    
    public int getLength() {
        return length;
    }
    
    public String getAlbumName() {
        return albumName;
    }
    
    //Voids:
    public void writeAllData() {
        System.out.printf("* %s - %s - %s - %ds", name, artist, style, length);
        if (albumName == null) {
            System.out.printf("\n");
        } else {
            System.out.printf(" - %s\n", albumName);
        }
    }
    
    //Var Funcs:
    public boolean equals(Song song) {
        
        if (song.getName().equals(name) && song.getArtist().equals(artist) && song.getStyle().equals(style) && song.getLength() == length) {
            if (song.getAlbumName() == null || song.getAlbumName().equals(albumName)) {
                return true;
            }
        }
        return false;
    }
}
