import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// This class handles all database operations for movies, reviews, and watchlist
public class MovieDB {

    // get all movies from database
    public static List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT m.id, m.title, m.year, m.director, g.name as genre, m.genre_id, " +
                     "m.poster_path, COALESCE(AVG(r.rating), 0) as avg_rating, w.status " +
                     "FROM movies m " +
                     "LEFT JOIN genres g ON m.genre_id = g.id " +
                     "LEFT JOIN reviews r ON m.id = r.movie_id " +
                     "LEFT JOIN watchlist w ON m.id = w.movie_id " +
                     "GROUP BY m.id, w.status";
        try {
            Statement st = DatabaseHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Movie movie = new Movie(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("year"),
                    rs.getString("director"),
                    rs.getString("genre"),
                    rs.getInt("genre_id"),
                    rs.getString("poster_path")
                );
                movie.setAvgRating(rs.getDouble("avg_rating"));
                movie.setStatus(rs.getString("status"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Error getting movies: " + e.getMessage());
        }
        return movies;
    }

    // search movies by title, genre, status
    public static List<Movie> searchMovies(String keyword, int genreId, String status) {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT m.id, m.title, m.year, m.director, g.name as genre, m.genre_id, " +
                     "m.poster_path, COALESCE(AVG(r.rating), 0) as avg_rating, w.status " +
                     "FROM movies m " +
                     "LEFT JOIN genres g ON m.genre_id = g.id " +
                     "LEFT JOIN reviews r ON m.id = r.movie_id " +
                     "LEFT JOIN watchlist w ON m.id = w.movie_id " +
                     "WHERE m.title LIKE ? " +
                     (genreId > 0 ? "AND m.genre_id = ? " : "") +
                     (status != null && !status.equals("All") ? "AND w.status = ? " : "") +
                     "GROUP BY m.id, w.status";
        try {
            PreparedStatement ps = DatabaseHelper.getConnection().prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            int i = 2;
            if (genreId > 0) ps.setInt(i++, genreId);
            if (status != null && !status.equals("All")) ps.setString(i, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("year"),
                    rs.getString("director"),
                    rs.getString("genre"),
                    rs.getInt("genre_id"),
                    rs.getString("poster_path")
                );
                movie.setAvgRating(rs.getDouble("avg_rating"));
                movie.setStatus(rs.getString("status"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Error searching: " + e.getMessage());
        }
        return movies;
    }

    // add a new movie
    public static int addMovie(String title, int year, String director, int genreId, String posterPath) {
        String sql = "INSERT INTO movies (title, year, director, genre_id, poster_path) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = DatabaseHelper.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setInt(2, year);
            ps.setString(3, director);
            ps.setInt(4, genreId);
            ps.setString(5, posterPath);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error adding movie: " + e.getMessage());
        }
        return -1;
    }

    // update existing movie
    public static void updateMovie(int id, String title, int year, String director, int genreId, String posterPath) {
        String sql = "UPDATE movies SET title=?, year=?, director=?, genre_id=?, poster_path=? WHERE id=?";
        try {
            PreparedStatement ps = DatabaseHelper.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setInt(2, year);
            ps.setString(3, director);
            ps.setInt(4, genreId);
            ps.setString(5, posterPath);
            ps.setInt(6, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating: " + e.getMessage());
        }
    }

    // delete a movie and its related data
    public static void deleteMovie(int id) {
        try {
            // delete reviews first
            PreparedStatement ps1 = DatabaseHelper.getConnection().prepareStatement("DELETE FROM reviews WHERE movie_id=?");
            ps1.setInt(1, id);
            ps1.executeUpdate();
            // delete watchlist entry
            PreparedStatement ps2 = DatabaseHelper.getConnection().prepareStatement("DELETE FROM watchlist WHERE movie_id=?");
            ps2.setInt(1, id);
            ps2.executeUpdate();
            // delete movie
            PreparedStatement ps3 = DatabaseHelper.getConnection().prepareStatement("DELETE FROM movies WHERE id=?");
            ps3.setInt(1, id);
            ps3.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting: " + e.getMessage());
        }
    }

    // add to watchlist
    public static void addToWatchlist(int movieId, String status) {
        String sql = "INSERT INTO watchlist (movie_id, status, added_date) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = DatabaseHelper.getConnection().prepareStatement(sql);
            ps.setInt(1, movieId);
            ps.setString(2, status);
            ps.setString(3, java.time.LocalDate.now().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding to watchlist: " + e.getMessage());
        }
    }

    // update watchlist status
    public static void updateWatchlistStatus(int movieId, String status) {
        String sql = "UPDATE watchlist SET status=? WHERE movie_id=?";
        try {
            PreparedStatement ps = DatabaseHelper.getConnection().prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, movieId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }

    // get watchlist status for a movie
    public static String getWatchlistStatus(int movieId) {
        String sql = "SELECT status FROM watchlist WHERE movie_id=?";
        try {
            PreparedStatement ps = DatabaseHelper.getConnection().prepareStatement(sql);
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("status");
        } catch (SQLException e) {
            System.out.println("Error getting status: " + e.getMessage());
        }
        return "Want to Watch";
    }

    // add a review
    public static void addReview(int movieId, int rating, String text) {
        String sql = "INSERT INTO reviews (movie_id, rating, review_text, watch_date) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = DatabaseHelper.getConnection().prepareStatement(sql);
            ps.setInt(1, movieId);
            ps.setInt(2, rating);
            ps.setString(3, text);
            ps.setString(4, java.time.LocalDate.now().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding review: " + e.getMessage());
        }
    }

    // get all reviews for a movie
    public static List<Review> getReviews(int movieId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE movie_id=?";
        try {
            PreparedStatement ps = DatabaseHelper.getConnection().prepareStatement(sql);
            ps.setInt(1, movieId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviews.add(new Review(
                    rs.getInt("id"),
                    rs.getInt("movie_id"),
                    rs.getInt("rating"),
                    rs.getString("review_text"),
                    rs.getString("watch_date")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting reviews: " + e.getMessage());
        }
        return reviews;
    }

    // get all genres
    public static List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        try {
            Statement st = DatabaseHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM genres");
            while (rs.next()) {
                genres.add(new Genre(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Error getting genres: " + e.getMessage());
        }
        return genres;
    }

    // get top 10 movies by rating
    public static List<Movie> getTop10() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT m.id, m.title, m.year, m.director, g.name as genre, m.genre_id, " +
                     "m.poster_path, AVG(r.rating) as avg_rating, w.status " +
                     "FROM movies m " +
                     "LEFT JOIN genres g ON m.genre_id = g.id " +
                     "JOIN reviews r ON m.id = r.movie_id " +
                     "LEFT JOIN watchlist w ON m.id = w.movie_id " +
                     "GROUP BY m.id ORDER BY avg_rating DESC LIMIT 10";
        try {
            Statement st = DatabaseHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Movie movie = new Movie(
                    rs.getInt("id"), rs.getString("title"), rs.getInt("year"),
                    rs.getString("director"), rs.getString("genre"),
                    rs.getInt("genre_id"), rs.getString("poster_path")
                );
                movie.setAvgRating(rs.getDouble("avg_rating"));
                movies.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Error getting top 10: " + e.getMessage());
        }
        return movies;
    }

    // count movies by genre for stats
    public static java.util.Map<String, Integer> getGenreStats() {
        java.util.LinkedHashMap<String, Integer> data = new java.util.LinkedHashMap<>();
        String sql = "SELECT g.name, COUNT(*) as cnt FROM movies m JOIN genres g ON m.genre_id=g.id GROUP BY g.name ORDER BY cnt DESC LIMIT 5";
        try {
            Statement st = DatabaseHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) data.put(rs.getString("name"), rs.getInt("cnt"));
        } catch (SQLException e) {
            System.out.println("Error getting genre stats: " + e.getMessage());
        }
        return data;
    }

    // count ratings distribution
    public static java.util.Map<Integer, Integer> getRatingStats() {
        java.util.LinkedHashMap<Integer, Integer> data = new java.util.LinkedHashMap<>();
        for (int i = 1; i <= 5; i++) data.put(i, 0);
        try {
            Statement st = DatabaseHelper.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT rating, COUNT(*) as cnt FROM reviews GROUP BY rating");
            while (rs.next()) data.put(rs.getInt("rating"), rs.getInt("cnt"));
        } catch (SQLException e) {
            System.out.println("Error getting rating stats: " + e.getMessage());
        }
        return data;
    }

    public static int getTotalMovies() {
        try {
            ResultSet rs = DatabaseHelper.getConnection().createStatement().executeQuery("SELECT COUNT(*) FROM movies");
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static int getTotalWatched() {
        try {
            ResultSet rs = DatabaseHelper.getConnection().createStatement().executeQuery("SELECT COUNT(*) FROM watchlist WHERE status='Watched'");
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static double getOverallAvgRating() {
        try {
            ResultSet rs = DatabaseHelper.getConnection().createStatement().executeQuery("SELECT AVG(rating) FROM reviews");
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public static String getFavouriteGenre() {
        String sql = "SELECT g.name FROM genres g JOIN movies m ON g.id=m.genre_id " +
                     "JOIN watchlist w ON m.id=w.movie_id WHERE w.status='Watched' " +
                     "GROUP BY g.name ORDER BY COUNT(*) DESC LIMIT 1";
        try {
            ResultSet rs = DatabaseHelper.getConnection().createStatement().executeQuery(sql);
            if (rs.next()) return rs.getString(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return "N/A";
    }
}
