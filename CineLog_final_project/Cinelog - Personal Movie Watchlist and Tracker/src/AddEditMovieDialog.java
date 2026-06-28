import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

// Dialog for adding a new movie or editing an existing one
public class AddEditMovieDialog extends JDialog {

    private JTextField titleField;
    private JTextField yearField;
    private JTextField directorField;
    private JComboBox<Genre> genreBox;
    private JLabel posterLabel;
    private String posterPath = "";
    private boolean saved = false;

    public AddEditMovieDialog(JFrame parent, Movie existingMovie) {
        super(parent, existingMovie == null ? "Add Movie" : "Edit Movie", true);
        setSize(400, 370);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(28, 28, 40));
        panel.setBorder(new EmptyBorder(16, 20, 16, 20));

        titleField    = makeField();
        yearField     = makeField();
        directorField = makeField();

        genreBox = new JComboBox<>();
        List<Genre> genres = MovieDB.getAllGenres();
        for (Genre g : genres) genreBox.addItem(g);
        genreBox.setBackground(new Color(45, 45, 60));
        genreBox.setForeground(Color.WHITE);
        genreBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        posterLabel = new JLabel("No image selected");
        posterLabel.setForeground(new Color(140, 140, 170));
        posterLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JButton choosePosterBtn = makeButton("Choose Poster", new Color(60, 80, 140));
        choosePosterBtn.addActionListener(e -> choosePoster());

        JButton saveBtn = makeButton(existingMovie == null ? "Add Movie" : "Save Changes", new Color(40, 120, 80));
        saveBtn.addActionListener(e -> save(parent, existingMovie));

        // if editing, fill in existing values
        if (existingMovie != null) {
            titleField.setText(existingMovie.getTitle());
            yearField.setText(String.valueOf(existingMovie.getYear()));
            directorField.setText(existingMovie.getDirector());
            posterPath = existingMovie.getPosterPath() != null ? existingMovie.getPosterPath() : "";
            if (!posterPath.isEmpty()) posterLabel.setText(new File(posterPath).getName());
            for (int i = 0; i < genreBox.getItemCount(); i++) {
                if (genreBox.getItemAt(i).getId() == existingMovie.getGenreId()) {
                    genreBox.setSelectedIndex(i);
                    break;
                }
            }
        }

        panel.add(labelRow("Title",    titleField));
        panel.add(Box.createVerticalStrut(8));
        panel.add(labelRow("Year",     yearField));
        panel.add(Box.createVerticalStrut(8));
        panel.add(labelRow("Director", directorField));
        panel.add(Box.createVerticalStrut(8));
        panel.add(makeLabel("Genre"));
        panel.add(genreBox);
        panel.add(Box.createVerticalStrut(8));
        panel.add(makeLabel("Poster"));
        panel.add(posterLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(choosePosterBtn);
        panel.add(Box.createVerticalStrut(12));
        panel.add(saveBtn);

        add(panel);
        setVisible(true);
    }

    private void choosePoster() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "bmp"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            posterPath = fc.getSelectedFile().getAbsolutePath();
            posterLabel.setText(fc.getSelectedFile().getName());
        }
    }

    private void save(JFrame parent, Movie existingMovie) {
        String title    = titleField.getText().trim();
        String yearStr  = yearField.getText().trim();
        String director = directorField.getText().trim();
        Genre  genre    = (Genre) genreBox.getSelectedItem();

        if (title.isEmpty() || yearStr.isEmpty() || director.isEmpty() || genre == null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Year must be a number!");
            return;
        }

        if (existingMovie == null) {
            // add new movie
            int newId = MovieDB.addMovie(title, year, director, genre.getId(), posterPath);
            if (newId != -1) {
                MovieDB.addToWatchlist(newId, "Want to Watch");
            }
        } else {
            // update existing
            MovieDB.updateMovie(existingMovie.getId(), title, year, director, genre.getId(), posterPath);
        }

        saved = true;
        dispose();
    }

    public boolean isSaved() { return saved; }

    private JPanel labelRow(String label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout(0, 3));
        p.setBackground(new Color(28, 28, 40));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 54));
        p.add(makeLabel(label), BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(160, 160, 190));
        l.setFont(new Font("Arial", Font.PLAIN, 12));
        return l;
    }

    private JTextField makeField() {
        JTextField f = new JTextField();
        f.setBackground(new Color(45, 45, 60));
        f.setForeground(Color.WHITE);
        f.setCaretColor(Color.WHITE);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 100)),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        return f;
    }

    private JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }
}
