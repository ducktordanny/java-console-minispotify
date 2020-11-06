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

//Getters:
    public String getName() {
        return name;
    }
    
    public List<Song> getSongs() {
        return songs;
    }
    
    public int getSize() {
        return songs.size();
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
    
    public void addByConsole() throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("Szam cime: ");
        String name = console.readLine();
        System.out.print("Eloado neve: ");
        String artist = console.readLine();
        System.out.print("Stilus: ");
        String style = console.readLine();
        System.out.print("Szam hossza(masodp): ");
        int length = Integer.parseInt(console.readLine());
        System.out.print("Album neve ha van(egyebkent enter): ");
        String album = console.readLine();
        
        if (album.equals("")) {
            add(new Song(name, artist, style, length));
        } else {
            add(new Song(name, artist, style, length, album));
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
    
    public void removeSongFromLibrary() throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String exit;
        do {
            System.out.print("Adja meg a szam cimet: ");
            String name = console.readLine();
            System.out.print("Adja meg a szam eloadojat: ");
            String artist = console.readLine();
            
            if (isValidArtistInLib(artist) && isValidNameInLib(name)) {
                FileWriter file = new FileWriter(filename, false);
                
                try {
                    for (int i = 0; i < songs.size(); i++) {
                        if (songs.get(i).getArtist().toLowerCase().equals(artist.toLowerCase()) &&
                                songs.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
                            songs.remove(i);
                            i--;
                        } else {
                            file.write(songs.get(i).getName() + " &_next ");
                            file.write(songs.get(i).getArtist() + " &_next ");
                            file.write(songs.get(i).getStyle() + " &_next ");
                            file.write(String.valueOf(songs.get(i).getLength()));
                            
                            if (songs.get(i).getAlbumName() != null) {
                                file.write(" &_next " + songs.get(i).getAlbumName());
                            }
                            file.write("\n");
                        }
                    }
                    file.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Nincs ilyen zene a lejatszasi listaban!");
            }
            
            System.out.print("Kivan tovabbi szamokat torolni? (y/n) ");
            exit = console.readLine();
        } while (!exit.toLowerCase().equals("n"));
    }
    
    public void removeLib() {
        try {
            File file = new File(filename);           //file to be delete  
            if (file.delete()) //returns Boolean value  
            {
                System.out.println(name + " deleted");   //getting and printing the file name  
            } else {
                System.out.println("Failed to delete.");
            }
        } catch (Exception e) {
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
            if (song.getArtist().toLowerCase().equals(artist.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isValidNameInLib(String name) {
        for (Song song : songs) {
            if (song.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
