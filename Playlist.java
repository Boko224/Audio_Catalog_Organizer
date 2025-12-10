import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Playlist implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private List<AudioItem> items;

    public Playlist(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    // Добавяне на песен
    public void addTrack(AudioItem item) {
        items.add(item);
    }

    // НОВО: Премахване на песен по заглавие
    public boolean removeTrack(String title) {
        // removeIf връща true, ако е намерено и изтрито нещо
        return items.removeIf(i -> i.getTitle().equalsIgnoreCase(title));
    }

    public void sortPlaylistByTitle() {
        items.sort(Comparator.comparing(AudioItem::getTitle));
    }

    public String getName() { return name; }
    public List<AudioItem> getItems() { return items; }

    @Override
    public String toString() {
        return "Playlist: " + name + " (" + items.size() + " tracks)";
    }
}