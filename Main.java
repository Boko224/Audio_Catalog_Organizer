import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static CatalogManager manager = new CatalogManager();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addNewItem();
                    break;
                case "2":
                    manager.showSortedCatalog();
                    break;
                case "3":
                    System.out.print("Search (Title/Author): ");
                    manager.search(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Enter genre to filter: ");
                    manager.filterByGenre(scanner.nextLine());
                    break;
                case "5":
                    System.out.print("Enter new playlist name: ");
                    manager.createPlaylist(scanner.nextLine());
                    break;
                case "6":
                    managePlaylist(); // Тук е промяната
                    break;
                case "7":
                    System.out.print("Enter playlist name to export: ");
                    String plName = scanner.nextLine();
                    manager.exportPlaylist(plName, plName + ".bin");
                    break;
                case "8":
                    manager.saveData();
                    break;
                case "0":
                    manager.saveData(); 
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== AUDIO ORGANIZER ===");
        System.out.println("1. Add New Item (Song/Podcast/Book)");
        System.out.println("2. Show All Items");
        System.out.println("3. Search");
        System.out.println("4. Filter by Genre");
        System.out.println("5. Create Playlist");
        System.out.println("6. Manage Playlist (Add/Remove/View)");
        System.out.println("7. Export Playlist to File");
        System.out.println("8. Save Changes");
        System.out.println("0. Exit");
        System.out.print("Choose option: ");
    }

    private static void addNewItem() {
        System.out.println("Type: 1.Song 2.Podcast 3.Audiobook");
        String type = scanner.nextLine();

        System.out.print("Title: "); String title = scanner.nextLine();
        System.out.print("Author/Artist: "); String author = scanner.nextLine();
        System.out.print("Genre: "); String genre = scanner.nextLine();
        
        int year = 0;
        double duration = 0.0;
        try {
            System.out.print("Year: "); year = Integer.parseInt(scanner.nextLine());
            System.out.print("Duration (min): "); duration = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(" Invalid number format! Operation cancelled.");
            return;
        }

        if (type.equals("1")) {
            manager.addItem(new Song(title, author, genre, year, duration));
        } else if (type.equals("2")) {
            System.out.print("Podcast Host: ");
            String host = scanner.nextLine();
            manager.addItem(new Podcast(title, author, genre, year, duration, host));
        } else if (type.equals("3")) {
            manager.addItem(new Audiobook(title, author, genre, year, duration));
        } else {
            System.out.println("Invalid type selected.");
        }
    }

    private static void managePlaylist() {
        manager.showAllPlaylists();
        System.out.print("Enter playlist name to manage: ");
        String plName = scanner.nextLine();
        Playlist pl = manager.getPlaylist(plName);

        if (pl == null) {
            System.out.println(" Playlist not found.");
            return;
        }

        System.out.println("\n--- Managing: " + pl.getName() + " ---");
        System.out.println("1. View Content");
        System.out.println("2. Add Item from Catalog");
        System.out.println("3. Remove Item from Playlist"); // Нова опция
        System.out.print("Choice: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            if (pl.getItems().isEmpty()) {
                System.out.println("Playlist is empty.");
            } else {
                pl.getItems().forEach(System.out::println);
            }
        } else if (choice.equals("2")) {
            System.out.print("Enter EXACT title of the song/item: ");
            String songTitle = scanner.nextLine();
            
            AudioItem itemFound = manager.getCatalog().stream()
                    .filter(i -> i.getTitle().equalsIgnoreCase(songTitle))
                    .findFirst()
                    .orElse(null);
            
            if (itemFound != null) {
                pl.addTrack(itemFound);
                System.out.println(" Added to playlist.");
            } else {
                System.out.println(" Item not found in main catalog. Add it there first.");
            }
        } else if (choice.equals("3")) {
            // ЛОГИКА ЗА ИЗТРИВАНЕ
            System.out.print("Enter title to remove: ");
            String titleToRemove = scanner.nextLine();
            boolean removed = pl.removeTrack(titleToRemove);
            
            if (removed) {
                System.out.println(" Item removed from playlist.");
            } else {
                System.out.println(" Item not found in this playlist.");
            }
        }
    }
}