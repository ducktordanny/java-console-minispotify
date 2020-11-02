/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minispotify;
import java.util.*;
import java.io.*;
//import java.nio.file.Files;
/**
 *
 * @author dnyyy
 */
public class Library {
    private String name;
    private List<Song> songs = new ArrayList<Song>();
    private String filename = "./src/minispotify/Libraries/";
    
//Constructors:
    public Library(String name) {
        this.name = name;
        this.filename += name + ".dat";
        createFile();
    }
    
    public Library(String name, String filename) {
        this.name = name;
        this.filename += filename;
        readFile();
    }

//Getters:
    public String getName() {
        return name;
    }
    
    public List<Song> getSongs() {
        return songs;
    }
    
//Functions:
    
    //Voids:
    public void write() {
        System.out.printf("%s:\n", name);
        for (Song song : songs) {
            song.writeAllData();
        }
    }
    
    public void writeSong(int index) {
        songs.get(index).writeAllData();
    }
    
    public void add(Song song) {
        
        if (!isSongAdded(song)) {
            songs.add(song);
            appendToFile(song);
        } else {
            System.out.println("Song is already added!");
        }
        
    }
    
    private void createFile() {
        try {
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
                System.out.println("New playlist created: " + myObj.getName());
            } else {
                readFile();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    private void readFile()  {
        //read file's datas and separate them

        try {    
            File myObj = new File(filename);
            Scanner reader = new Scanner(myObj);
            while (reader.hasNextLine()) {
                String datas[] = reader.nextLine().split(" &_next ");
                
                if (datas.length == 4) {
                    songs.add(new Song(datas[0], datas[1], datas[2], Integer.parseInt(datas[3])));
                } else if (datas.length == 5) {
                    songs.add(new Song(datas[0], datas[1], datas[2], Integer.parseInt(datas[3]), datas[4]));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    private void appendToFile(Song song) {
        try {
            FileWriter file = new FileWriter(filename, true);
            
            file.write(song.getName() + " &_next ");
            file.write(song.getArtist() + " &_next ");
            file.write(song.getStyle() + " &_next ");
            file.write(String.valueOf(song.getLength()));
            if (song.getAlbumName() != null) {
                file.write(" &_next " + song.getAlbumName());
            }
            file.write("\n");
            file.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    //Var Funcs:
    public boolean isSongAdded(Song song) {
        //it gives back if a song is already in library
       
        for (Song s : songs) {
            if (song.equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isValidArtistInLib(String artist) {
        for (Song song : songs) {
            if (song.getArtist().equals(artist)) {
                return true;
            }
        }
        return false;
    }
}
