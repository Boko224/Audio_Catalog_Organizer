
public class Song extends AudioItem {
    public Song(String title, String author, String genre, int year, double duration) {
        super(title, author, genre, year, duration);
    }

    @Override
    public String toString() {
        return "[Song] " + super.toString();
    }
}