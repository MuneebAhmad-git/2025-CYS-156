// Stores a single review for a movie
public class Review {
    private int id;
    private int movieId;
    private int rating;
    private String reviewText;
    private String watchDate;

    public Review(int id, int movieId, int rating, String reviewText, String watchDate) {
        this.id         = id;
        this.movieId    = movieId;
        this.rating     = rating;
        this.reviewText = reviewText;
        this.watchDate  = watchDate;
    }

    public int    getId()           { return id; }
    public int    getMovieId()      { return movieId; }
    public int    getRating()       { return rating; }
    public String getReviewText()   { return reviewText; }
    public String getWatchDate()    { return watchDate; }
}
