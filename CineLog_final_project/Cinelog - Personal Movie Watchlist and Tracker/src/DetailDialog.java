import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class DetailDialog extends JDialog {

    private Movie movie;
    private JPanel reviewsPanel;
    private boolean movieChanged = false;

    public DetailDialog(JFrame parent, Movie movie) {
        super(parent, movie.getTitle(), true);
        this.movie = movie;

        setSize(680, 520);
        setLocationRelativeTo(parent);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(22, 22, 32));
        main.add(makeTopBar(parent), BorderLayout.NORTH);
        main.add(makeBody(),         BorderLayout.CENTER);
        add(main);

        setVisible(true);
    }

    private JPanel makeTopBar(JFrame parent) {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        bar.setBackground(new Color(28, 28, 40));

        JButton editBtn = makeBtn("Edit", new Color(50, 100, 180));
        editBtn.addActionListener(e -> {
            AddEditMovieDialog dialog = new AddEditMovieDialog(parent, movie);
            if (dialog.isSaved()) {
                movieChanged = true;
                dispose();
            }
        });

        JButton deleteBtn = makeBtn("Delete", new Color(180, 50, 50));
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Delete \"" + movie.getTitle() + "\"?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                MovieDB.deleteMovie(movie.getId());
                movieChanged = true;
                dispose();
            }
        });

        bar.add(editBtn);
        bar.add(deleteBtn);
        return bar;
    }

    private JPanel makeBody() {
        JPanel body = new JPanel(new BorderLayout(14, 0));
        body.setBackground(new Color(22, 22, 32));
        body.setBorder(new EmptyBorder(14, 14, 14, 14));
        body.add(makeLeft(),  BorderLayout.WEST);
        body.add(makeRight(), BorderLayout.CENTER);
        return body;
    }

    private JPanel makeLeft() {
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(new Color(22, 22, 32));
        left.setPreferredSize(new Dimension(190, 0));

        // poster image
        JLabel poster = new JLabel("No Image", SwingConstants.CENTER);
        poster.setPreferredSize(new Dimension(180, 255));
        poster.setMaximumSize(new Dimension(180, 255));
        poster.setMinimumSize(new Dimension(180, 255));
        poster.setOpaque(true);
        poster.setBackground(new Color(50, 50, 70));
        poster.setForeground(new Color(120, 120, 150));

        String path = movie.getPosterPath();
        if (path != null && !path.isEmpty()) {
            try {
                BufferedImage img = ImageIO.read(new File(path));
                if (img != null) {
                    BufferedImage scaled = new BufferedImage(180, 255, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2 = scaled.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.drawImage(img, 0, 0, 180, 255, null);
                    g2.dispose();
                    poster.setIcon(new ImageIcon(scaled));
                    poster.setText("");
                }
            } catch (Exception ex) {
                System.out.println("Image load error: " + ex.getMessage());
            }
        }

        // watchlist status dropdown
        String currentStatus = MovieDB.getWatchlistStatus(movie.getId());
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Want to Watch", "Watching", "Watched"});
        statusBox.setSelectedItem(currentStatus);
        statusBox.setBackground(new Color(45, 45, 60));
        statusBox.setForeground(Color.WHITE);
        statusBox.setMaximumSize(new Dimension(180, 30));
        statusBox.addActionListener(e ->
            MovieDB.updateWatchlistStatus(movie.getId(), (String) statusBox.getSelectedItem()));

        left.add(poster);
        left.add(Box.createVerticalStrut(8));
        left.add(statusBox);
        return left;
    }

    private JPanel makeRight() {
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(new Color(22, 22, 32));

        JLabel titleLbl = new JLabel(movie.getTitle());
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setFont(new Font("Arial", Font.BOLD, 20));

        String ratingTxt = movie.getAvgRating() > 0
            ? String.format("%.1f / 5.0", movie.getAvgRating()) : "Not rated yet";
        JLabel metaLbl = new JLabel(movie.getYear() + "   " + movie.getDirector() + "   " + ratingTxt);
        metaLbl.setForeground(new Color(160, 160, 190));
        metaLbl.setFont(new Font("Arial", Font.PLAIN, 13));

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(60, 60, 80));

        JLabel reviewsTitle = new JLabel("Reviews");
        reviewsTitle.setForeground(Color.WHITE);
        reviewsTitle.setFont(new Font("Arial", Font.BOLD, 14));

        reviewsPanel = new JPanel();
        reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
        reviewsPanel.setBackground(new Color(22, 22, 32));
        loadReviews();

        JScrollPane scroll = new JScrollPane(reviewsPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(22, 22, 32));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        scroll.setPreferredSize(new Dimension(0, 150));

        right.add(titleLbl);
        right.add(Box.createVerticalStrut(5));
        right.add(metaLbl);
        right.add(Box.createVerticalStrut(10));
        right.add(sep);
        right.add(Box.createVerticalStrut(10));
        right.add(reviewsTitle);
        right.add(Box.createVerticalStrut(5));
        right.add(scroll);
        right.add(Box.createVerticalStrut(8));
        right.add(makeReviewForm());
        return right;
    }

    private void loadReviews() {
        reviewsPanel.removeAll();
        List<Review> reviews = MovieDB.getReviews(movie.getId());

        if (reviews.isEmpty()) {
            JLabel none = new JLabel("No reviews yet.");
            none.setForeground(new Color(120, 120, 150));
            reviewsPanel.add(none);
        } else {
            for (Review r : reviews) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(35, 35, 50));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 60, 80)),
                    new EmptyBorder(5, 8, 5, 8)));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

                JLabel stars = new JLabel(r.getRating() + "/5  " + r.getWatchDate());
                stars.setForeground(new Color(255, 200, 50));
                stars.setFont(new Font("Arial", Font.PLAIN, 11));

                JLabel text = new JLabel("<html><body style='width:240px'>" + r.getReviewText() + "</body></html>");
                text.setForeground(new Color(180, 180, 200));
                text.setFont(new Font("Arial", Font.PLAIN, 12));

                card.add(stars, BorderLayout.NORTH);
                card.add(text,  BorderLayout.CENTER);
                reviewsPanel.add(card);
                reviewsPanel.add(Box.createVerticalStrut(4));
            }
        }
        reviewsPanel.revalidate();
        reviewsPanel.repaint();
    }

    private JPanel makeReviewForm() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(new Color(28, 28, 40));
        form.setBorder(new EmptyBorder(8, 8, 8, 8));
        form.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        JLabel lbl = new JLabel("Write a Review");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));

        JTextArea area = new JTextArea(3, 20);
        area.setBackground(new Color(45, 45, 60));
        area.setForeground(Color.WHITE);
        area.setCaretColor(Color.WHITE);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(4, 6, 4, 6));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row.setBackground(new Color(28, 28, 40));
        JLabel rl = new JLabel("Rating:");
        rl.setForeground(new Color(180, 180, 200));
        JComboBox<Integer> ratingBox = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        ratingBox.setBackground(new Color(45, 45, 60));
        ratingBox.setForeground(Color.WHITE);
        row.add(rl);
        row.add(ratingBox);

        JButton submitBtn = makeBtn("Submit Review", new Color(40, 120, 80));
        submitBtn.addActionListener(e -> {
            String text = area.getText().trim();
            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please write something!");
                return;
            }
            MovieDB.addReview(movie.getId(), (int) ratingBox.getSelectedItem(), text);
            area.setText("");
            loadReviews();
        });

        form.add(lbl);
        form.add(Box.createVerticalStrut(4));
        form.add(new JScrollPane(area));
        form.add(Box.createVerticalStrut(4));
        form.add(row);
        form.add(Box.createVerticalStrut(4));
        form.add(submitBtn);
        return form;
    }

    public boolean isMovieChanged() { return movieChanged; }

    private JButton makeBtn(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
