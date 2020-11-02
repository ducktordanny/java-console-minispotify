/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minispotify;
import java.io.*;
import java.util.*;

/**
 *
 * @author dnyyy
 */
public class MiniSpotify {

    /**
     * @param args the command line arguments
     */
    
    //this list is gonna contain the main Library and all of the Playlists
    public static List<Library> allLibs = new ArrayList<Library>();
    
    public static void newLine() {
        System.out.println();
    }
    
    public static void getAllLibraries() throws IOException {
        
        File directoryPath = new File("./src/minispotify/Libraries");
        File fileList[] = directoryPath.listFiles();
        
        for (File file : fileList) {
            String libName = String.valueOf(file.getName()).split(".dat")[0];
            allLibs.add(new Library(libName));
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        
        getAllLibraries();
        
        Menu m1 = new Menu(allLibs);
        m1.expectUserChoice();
//        m1.showMenuPoints();
        


//        Library l1 = new Library("main");
//        l1.write();
        
//        Library l2 = new Library("TestPlaylist");
//        l2.add(new Song("Run!", "Scarlxrd", "HipHop/Rap", 165, "Lxrdzsn"));
//        l2.write();
        //adding songs to Library test
//        l2.add(new Song("FFS Freestyle", "Scarlxrd", "HipHop/Rap", 180, "Fantasy Vxid"));
//        l2.write();
        
        
        
        // *TODO code application logic here
        
        // [x] * Make Song class what's gonna store a song
        // [x] * Make Library class what's gonna store Songs and it is also gonna work as playlist
        // [x] * Need a folder where the program store the files and need a function or a class what handles those
        //          for separating files and systematize datas I found this: https://www.tutorialspoint.com/how-to-get-list-of-all-files-folders-from-a-folder-in-java
        // [ ] * Make Menu class what's gonna show us it and it is gonna control almost everything (e.g. playing, shuffle, etc.)
        // [ ] * Final optimizations etc.
    }
    
}
