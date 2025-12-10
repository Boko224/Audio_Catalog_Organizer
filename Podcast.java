
public class Podcast extends AudioItem {
    private String host; 

    public Podcast(String title, String author, String genre, int year, double duration, String host) {
        super(title, author, genre, year, duration);
        this.host = host;
    }
    
    public String getHost() { return host; }

    @Override
    public String toString() {
        return "[Podcast] " + super.toString() + " (Host: " + host + ")";
    }
}