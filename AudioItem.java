import java.io.Serializable;

// Serializable е задължителен за запис във файл
public abstract class AudioItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String author; // Изпълнител или Автор
    private String genre;
    private int year;
    private double duration; // В минути

    public AudioItem(String title, String author, String genre, int year, double duration) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.duration = duration;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }
    public double getDuration() { return duration; }

    @Override
    public String toString() {
        return String.format("'%s' by %s [%s, %d, %.2f min]", title, author, genre, year, duration);
    }
}