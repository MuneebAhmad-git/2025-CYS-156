import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class MovieCard extends JPanel {

    private Movie movie;
    private Runnable onClick;

    public MovieCard(Movie movie, Runnable onClick) {
        this.movie   = movie;
        this.onClick = onClick;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(160, 150));
        setBackground(new Color(38, 38, 52));
        setBorder(BorderFactory.createLineBorder(new Color(60, 60, 80), 1));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        add(makePoster(), BorderLayout.CENTER);
        add(makeInfo(),   BorderLayout.SOUTH);

        // clicking the card opens movie detail
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { onClick.run(); }
            public void mouseEntered(MouseEvent e) { setBackground(new Color(55, 55, 75)); repaint(); }
            public void mouseExited(MouseEvent e)  { setBackground(new Color(38, 38, 52)); repaint(); }
        });
    }

    private JLabel makePoster() {
        JLabel poster = new JLabel("No Image", SwingConstants.CENTER);
        poster.setPreferredSize(new Dimension(160, 190));
        poster.setOpaque(true);
        poster.setBackground(new Color(50, 50, 70));
        poster.setForeground(new Color(120, 120, 150));
        poster.setFont(new Font("Arial", Font.PLAIN, 12));

        String path = movie.getPosterPath();
        if (path != null && !path.isEmpty()) {
            try {
                BufferedImage img = ImageIO.read(new File(path));
                if (img != null) {
                    
                    BufferedImage scaled = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2 = scaled.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.drawImage(img, 0, 0, 200, 200, null);
                    g2.dispose();
                    poster.setIcon(new ImageIcon(scaled));
                    poster.setText("");
                }
            } catch (Exception ex) {
                // if image fails just show "No Image"
                System.out.println("Could not load image: " + ex.getMessage());
            }
        }
        return poster;
    }

    private JPanel makeInfo() {
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(new Color(38, 38, 52));
        info.setBorder(new EmptyBorder(5, 8, 5, 8));

        JLabel title = new JLabel(movie.getTitle());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 12));

        String rating = movie.getAvgRating() > 0 ? String.format("%.1f/5", movie.getAvgRating()) : "No rating";
        JLabel meta = new JLabel(movie.getYear() + "  " + rating);
        meta.setForeground(new Color(180, 180, 200));
        meta.setFont(new Font("Arial", Font.PLAIN, 11));

        String status = movie.getStatus() != null ? movie.getStatus() : "Want to Watch";
        JLabel statusLbl = new JLabel(status);
        statusLbl.setFont(new Font("Arial", Font.PLAIN, 10));
        statusLbl.setForeground(new Color(100, 200, 150));

        info.add(title);
        info.add(Box.createVerticalStrut(2));
        info.add(meta);
        info.add(statusLbl);
        return info;
    }
}
