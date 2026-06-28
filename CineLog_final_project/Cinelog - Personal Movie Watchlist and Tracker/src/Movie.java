// This class stores all the info about a single movie
public class Movie {

    private int id;
    private String title;
    private int year;
    private String director;
    private String genre;
    private int genreId;
    private String posterPath;
    private double avgRating;
    private String status;

    public Movie(int id, String title, int year, String director, String genre, int genreId, String posterPath) {
        this.id         = id;
        this.title      = title;
        this.year       = year;
        this.director   = director;
        this.genre      = genre;
        this.genreId    = genreId;
        this.posterPath = posterPath;
        this.avgRating  = 0;
        this.status     = "Want to Watch";
    }

    // getters
    public int    getId()          { return id; }
    public String getTitle()       { return title; }
    public int    getYear()        { return year; }
    public String getDirector()    { return director; }
    public String getGenre()       { return genre; }
    public int    getGenreId()     { return genreId; }
    public String getPosterPath()  { return posterPath; }
    public double getAvgRating()   { return avgRating; }
    public String getStatus()      { return status; }

    // setters
    public void setTitle(String t)      { this.title = t; }
    public void setYear(int y)          { this.year = y; }
    public void setDirector(String d)   { this.director = d; }
    public void setGenre(String g)      { this.genre = g; }
    public void setGenreId(int g)       { this.genreId = g; }
    public void setPosterPath(String p) { this.posterPath = p; }
    public void setAvgRating(double r)  { this.avgRating = r; }
    public void setStatus(String s)     { this.status = s; }

    // useful for displaying in lists
    @Override
    public String toString() {
        return title + " (" + year + ")";
    }
}
