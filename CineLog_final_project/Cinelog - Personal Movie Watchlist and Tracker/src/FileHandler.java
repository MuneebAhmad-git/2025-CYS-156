import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    
    private static final String FILE_NAME = "cinelog_backup.txt";


    public static void exportMovies(List<Movie> movies) {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Movie movie : movies) {
                
                String line = movie.getId() + "|" + 
                              movie.getTitle() + "|" + 
                              movie.getYear() + "|" + 
                              movie.getDirector();
                
                writer.write(line);
                writer.newLine(); 
            }
            System.out.println("Movies successfully exported to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    
    public static List<Movie> importMovies() {
        List<Movie> importedMovies = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                String[] data = line.split("\\|");
                
                if (data.length >= 4) {
                   
                    int id = Integer.parseInt(data[0]);
                    String title = data[1];
                    int year = Integer.parseInt(data[2]);
                    String director = data[3];
                    
                    
                    Movie movie = new Movie(id, title, year, director, "", 0, ""); 
                    importedMovies.add(movie);
                }
            }
            System.out.println("Movies successfully imported.");
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        
        return importedMovies;
    }
}