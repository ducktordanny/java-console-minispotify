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
public class Menu {
//Variables:
//    private List<String> menuPoints = new ArrayList<String>();
    private String menuPoints[] = {
        "(1) Szamok lejatszasa",
        "(2) Szamok keverese",
        "(3) Lejatszas eloado szerint",
        "(4) Eloadok listazasa nevsorrendben",
        "(5) Stilusok listazasa",
        "(6) Lejatszasi lista keszitese",
        "(7) Lejatszasi lista szerkesztese",
        "(q) Kilepes"
    };
    
    private List<Library> allLibrary = new ArrayList<Library>();
    
//Constructor:
    public Menu(List<Library> allLibrary) {
        for (Library lib : allLibrary) {
            this.allLibrary.add(lib);
        }
    }
    
//Getters:
    public String[] getMenuPoints() {
        return menuPoints;
    }
    
    public List<Library> getLibraryList() {
        return allLibrary;
    }
    
//Functions:
    
    //Voids:
    
    public void showMenuPoints() {
        System.out.println();
        for (String points : menuPoints) {
            System.out.printf("%s\n", points);
        }
    }
    
    public void expectUserChoice() throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String command;
        
        do {
            showMenuPoints();
            command = console.readLine();
            
            menuSelector(command);
            if (!command.equals("q")) {
                System.out.print("Nyomjon Entert a folytatashoz...");
                console.readLine();
            }
        } while(!command.equals("q"));
    }
    
    private void menuSelector(String command) throws IOException {
        switch (command) {
            case "q": System.out.println("On kilepett!"); break;
            case "1": playAllSongs(); break;
            case "2": shuffleAllSongs(); break;
            case "3": playByArtist(); break;
            case "4": listAllArtistOrderedByName(); break;
            case "5": listAllStyle(); break;
            case "6": makeNewPlaylist(); break;
            case "7": editPlaylists(); break;
            default: System.out.println("Ervenytelen menukod!"); break;
        }
    }
    
    //Menu Functions:
    
    private void playAllSongs() throws IOException {
        int index = chooseLibrary();
        for (Song song : allLibrary.get(index).getSongs()) {
            System.out.print("Lejatszva: ");
            song.writeAllData();
        }
    }
    
    private void shuffleAllSongs() throws IOException {
        int index = chooseLibrary();
        List<Song> copy = allLibrary.get(index).getSongs();
        Collections.shuffle(copy);
        for (Song song : copy) {
            System.out.print("Lejatszva: ");
            song.writeAllData();
        }
    }
    
    private void playByArtist() throws IOException {
        int index = chooseLibrary();
        String chosenArtist = chooseArtist(index);
        
        for (Song song : allLibrary.get(index).getSongs()) {
            if (song.getArtist().equals(chosenArtist)) {
                System.out.print("Lejatszva: ");
                song.writeAllData();
            }
        }
    }
    
    //I need a func without parameter where I call chooseLibrary this is used at 3rd menupoint
    private void listAllArtistOrderedByName() throws IOException {
        sortAndList(chooseLibrary());
    }
    //This is used at chooseArtist where I just want to list the opportunities and I already has a library index
    private void listAllArtistOrderedByName(int index) throws IOException {
        sortAndList(index);
    }
    private void sortAndList(int index) throws IOException {
        List<Song> copy = allLibrary.get(index).getSongs();
        Collections.sort(copy, (Song a, Song b) -> a.getArtist().compareTo(b.getArtist()));
        
        int i = 0;
        for (Song song : copy) {
            if (i == 0 || (i != 0 && !song.getArtist().equals(copy.get(i-1).getArtist()))) {
                System.out.println(song.getArtist());
            }
            i++;
        }
    }
    
    private void listAllStyle() throws IOException {
        int index = chooseLibrary();
        int i = 0;
        List<Song> copy = allLibrary.get(index).getSongs();
        Collections.sort(copy, (Song a, Song b) -> a.getStyle().compareTo(b.getStyle()));
        for (Song song : copy) {
            if (i == 0 || (i != 0 && !song.getStyle().equals(copy.get(i-1).getStyle()))) {
                System.out.println(song.getStyle());
            }
            i++;
        }
    }
    
    private void makeNewPlaylist() throws IOException {
        
    }
    
    private void editPlaylists() throws IOException {
        
    }
    
    //Var Funcs:
    
    private int chooseLibrary() throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        
        do {
            for (Library lib : allLibrary) {
                System.out.println("(" + (allLibrary.indexOf(lib) + 1) + ") " + lib.getName());
            }
            
            int chosenLib = Integer.parseInt(console.readLine()) - 1;
            if (chosenLib >= 0 && chosenLib < allLibrary.size()) {
                return chosenLib;
            }
            System.out.println("Ervenytelen menukod!");
        } while (true);
    }
    
    private String chooseArtist(int index) throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        listAllArtistOrderedByName(index);
        do {
            System.out.print("Adja meg az eloado nevet: ");
            String chosenArtist = console.readLine();
            if (allLibrary.get(index).isValidArtistInLib(chosenArtist)) {
                return chosenArtist;
            }
            System.out.println("Ervenytelen menukod!");
        } while (true);
    }
}
