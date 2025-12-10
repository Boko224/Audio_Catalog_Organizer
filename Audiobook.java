
public class Audiobook extends AudioItem {
    public Audiobook(String title, String author, String genre, int year, double duration) {
        super(title, author, genre, year, duration);
    }

    @Override
    public String toString() {
        return "[Audiobook] " + super.toString();
    }
}