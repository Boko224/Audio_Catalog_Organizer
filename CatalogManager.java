import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CatalogManager {
    private List<AudioItem> mainCatalog;
    private List<Playlist> allPlaylists;
    private final String DATA_FILE = "audiocatalog.bin"; 

    public CatalogManager() {
        this.mainCatalog = new ArrayList<>();
        this.allPlaylists = new ArrayList<>();
        loadData(); 
    }

    // --- ADD / REMOVE ---
    public void addItem(AudioItem item) {
        mainCatalog.add(item);
        System.out.println(" Successfully added: " + item.getTitle());
    }

    public void removeItem(String title) {
        boolean removed = mainCatalog.removeIf(i -> i.getTitle().equalsIgnoreCase(title));
        if (removed) System.out.println(" Item removed.");
        else System.out.println(" Item not found.");
    }

    // --- SEARCH / FILTER ---
    public void search(String query) {
        System.out.println("--- Search Results: " + query + " ---");
        mainCatalog.stream()
                .filter(i -> i.getTitle().toLowerCase().contains(query.toLowerCase()) || 
                             i.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .forEach(System.out::println);
    }

    public void filterByGenre(String genre) {
        System.out.println("--- Genre: " + genre + " ---");
        mainCatalog.stream()
                .filter(i -> i.getGenre().equalsIgnoreCase(genre))
                .forEach(System.out::println);
    }

    public void showSortedCatalog() {
        System.out.println("--- All Items (Sorted by Title) ---");
        mainCatalog.stream()
                .sorted(Comparator.comparing(AudioItem::getTitle))
                .forEach(System.out::println);
    }

    // --- PLAYLISTS ---
    public void createPlaylist(String name) {
        allPlaylists.add(new Playlist(name));
        System.out.println(" Playlist '" + name + "' created.");
    }

    public Playlist getPlaylist(String name) {
        return allPlaylists.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void showAllPlaylists() {
        if (allPlaylists.isEmpty()) System.out.println("No playlists found.");
        else allPlaylists.forEach(System.out::println);
    }

    // --- FILE I/O ---
    
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(mainCatalog);
            oos.writeObject(allPlaylists);
            System.out.println(" Data saved successfully to: " + DATA_FILE);
        } catch (IOException e) {
            System.out.println(" Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            mainCatalog = (List<AudioItem>) ois.readObject();
            allPlaylists = (List<Playlist>) ois.readObject();
            System.out.println(" Data loaded successfully.");
        } catch (Exception e) {
            System.out.println(" Error loading data or empty file.");
        }
    }

    public void exportPlaylist(String playlistName, String filename) {
        Playlist p = getPlaylist(playlistName);
        if (p == null) {
            System.out.println(" Playlist not found.");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(p);
            System.out.println(" Playlist exported to: " + filename);
        } catch (IOException e) {
            System.out.println(" Error exporting playlist: " + e.getMessage());
        }
    }
    
    public List<AudioItem> getCatalog() { return mainCatalog; }
}