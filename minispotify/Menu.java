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
    public Menu() {
        File directoryPath = new File("./src/minispotify/Libraries");
        File fileList[] = directoryPath.listFiles();
        
        for (File file : fileList) {
            String libName = String.valueOf(file.getName()).split(".dat")[0];
            allLibrary.add(new Library(libName));
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
                System.out.printf("\nNyomjon Entert a folytatashoz...");
                console.readLine();
            }
        } while(!command.equals("q"));
    }
    
    private void menuSelector(String command) throws IOException {
        switch (command.toLowerCase()) {
            case "q": exitOutput(); break;
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
    private void exitOutput() {
        System.out.println("On kilepett!");
    }
    
    private void playAllSongs() throws IOException {
        int index = chooseLibrary(false);
        if (index != -1) {
            for (Song song : allLibrary.get(index).getSongs()) {
                System.out.print("Lejatszva: ");
                song.writeAllData();
            }
        } else {
            exitOutput();
        }
    }
    
    private void shuffleAllSongs() throws IOException {
        int index = chooseLibrary(false);
        if (index != -1) {
            List<Song> copy = allLibrary.get(index).getSongs();
            Collections.shuffle(copy);
            for (Song song : copy) {
                System.out.print("Lejatszva: ");
                song.writeAllData();
            }
        } else {
            exitOutput();
        }
    }
    
    private void playByArtist() throws IOException {
        int index = chooseLibrary(false);
        if (index != -1) {
            String chosenArtist = chooseArtist(index);
            
            for (Song song : allLibrary.get(index).getSongs()) {
                if (song.getArtist().toLowerCase().equals(chosenArtist.toLowerCase())) {
                    System.out.print("Lejatszva: ");
                    song.writeAllData();
                }
            }
        } else {
            exitOutput();
        }
    }
    
    //I need a func without parameter where I call chooseLibrary this is used at 3rd menupoint
    private void listAllArtistOrderedByName() throws IOException {
        int index = chooseLibrary(false);
        if (index != -1) {
            sortAndList(index);
        } else {
            exitOutput();
        }
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
        int index = chooseLibrary(false);
        if (index != -1) {
            int i = 0;
            List<Song> copy = allLibrary.get(index).getSongs();
            Collections.sort(copy, (Song a, Song b) -> a.getStyle().compareTo(b.getStyle()));
            for (Song song : copy) {
                if (i == 0 || (i != 0 && !song.getStyle().equals(copy.get(i-1).getStyle()))) {
                    System.out.println(song.getStyle());
                }
                i++;
            }
        } else {
            exitOutput();
        }
        
    }
    
    private void makeNewPlaylist() throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        boolean isValidFileName = true;
        
        do {
            System.out.print("Adja meg az uj lejatszasi lista nevet: ");
            String fileName = console.readLine();
            for (Library lib : allLibrary) {
                if (lib.getName().equals(fileName)) {
                    isValidFileName = false;
                }
            }
            if (isValidFileName) {
                Library newLib = new Library(fileName);
                allLibrary.add(newLib);
            } else {
                System.out.println("Foglalt fajlnev!");
            }
        } while (!isValidFileName);
        
    }
    
    private void editPlaylists() throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        int index = chooseLibrary(true);
        
        if (index != -1) {
            System.out.printf("(1) Szam hozzaadasa\n(2) Osszes szam kilistazasa\n(3) Szam torlese\n(4) Lejatszasi lista torlese\n(q) Kilepes\n");
            String command = console.readLine();
            switch (command) {
                case "1": addSongToALibrary(index); break;
                case "2": listSongsFromLib(index); break;
                case "3": deleteSongFromThisLibrary(index); break;
                case "4": deleteLibrary(index); break;
                case "q": exitOutput(); break;
                default: System.out.println("Ervenytelen menukod!"); break;
            }
        } else {
            exitOutput();
        }
    }
    private void addSongToALibrary(int index) throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        
        allLibrary.get(index).addByConsole();
    }
    private void listSongsFromLib(int index) {
        for (Song song : allLibrary.get(index).getSongs()) {
            song.writeAllData();
        }
    }
    private void deleteSongFromThisLibrary(int index) throws IOException {
        allLibrary.get(index).removeSongFromLibrary();
    }
    private void deleteLibrary(int index) throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Biztosan torolni szeretne ezt a lejatszasi listat? (y/n) ");
        String answer = console.readLine();
        
        if (answer.toLowerCase().equals("y")) {
            allLibrary.get(index).removeLib();
            allLibrary.remove(allLibrary.get(index));
        } else {
            System.out.println("On kilepett!");
        }
    }
    
    //Var Funcs:
    
    private int chooseLibrary(boolean allowEmptyPlaylist) throws IOException {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String chosenLib;
        do {
            for (Library lib : allLibrary) {
                System.out.println("(" + (allLibrary.indexOf(lib) + 1) + ") " + lib.getName());
            }
            System.out.print("Lejatszasi lista kivalasztasa: ");
            chosenLib = console.readLine();
            System.out.println();
            if (!chosenLib.toLowerCase().equals("q") && !chosenLib.equals("")) {
                if (allLibrary.get(Integer.parseInt(chosenLib) - 1).getSize() == 0 && !allowEmptyPlaylist) {
                    System.out.println("Ures lejatszasi lista");
                    return -1;
                } else if (Integer.parseInt(chosenLib) - 1 >= 0 && Integer.parseInt(chosenLib) - 1 < allLibrary.size()) {
                    return Integer.parseInt(chosenLib) - 1;
                } else {
                    System.out.println("Ervenytelen menukod!");
                }
            } else if (chosenLib.equals("")) {
                System.out.println("Ervenytelen menukod!");
            }
        } while (!chosenLib.toLowerCase().equals("q"));
        return -1;
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
