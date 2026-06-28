


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class MainWindow extends JFrame {
    JFrame frame = new JFrame();
    private JPanel gridPanel;
    private JTextField searchField;
    private JComboBox<Genre> genreBox;
    private JComboBox<String> statusBox;
    private JComboBox<String> sortBox;
    private String currentStatus = "All";
    Cursor cursor;
    
    MainWindow(){

        frame.setTitle("Cinelog - Movie Tracker");
        frame.setBounds(200,50,1000,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(new Color(22,22,32));
        frame.setLayout(null);
        frame.getContentPane();
        
        cursor = new Cursor(Cursor.HAND_CURSOR);

        Container container = frame.getContentPane();

        container.add(sidebar());
        container.add(moviegrid());
        container.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private JPanel sidebar(){
        JPanel p1 = new JPanel();
        int xAxis = 0;
        int yAxis = 7;
        p1.setBounds(0,0,190,600);
        p1.setLayout(null);
        p1.setBackground(new Color(22,22,40));

        JLabel label1 = new JLabel(" Cinelog");
        label1.setBounds(20, 0, 90, 35);
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Arial", Font.BOLD, 13));
        p1.add(label1);

        List<JButton> buttons = new ArrayList<>();
        String[] labels = {"All Movies", "Want to Watch", "Watching", "Watched", "Stats"};
        for(String label : labels){
            xAxis =  20;
            yAxis = yAxis + 40;
            JButton btn = addButton(label,xAxis, yAxis);
            p1.add(btn);
            btn.addActionListener(e -> {
                // highlight active button
                buttons.forEach(b -> b.setBackground(new Color(28, 28, 40)));
                btn.setBackground(new Color(45, 45, 65));

                if (label.equals("Stats")) {
                    new StatsDialog(this);
                } else {
                    if (label.equals("All Movies")){
                        currentStatus = "All";
                    }else{
                        currentStatus = label;
                    }
                    if (statusBox != null) {
                        statusBox.setSelectedItem(currentStatus);
                    }
                    loadMovies();
                }

                 
            });
            buttons.add(btn);
        }
        buttons.get(0).setBackground(new Color(45, 45, 65));

        JButton addBtn = addButton("+ Add Movie", xAxis, yAxis = 510);
        addBtn.setForeground(new Color(100, 200, 150));
        addBtn.addActionListener(e -> {
            AddEditMovieDialog dialog = new AddEditMovieDialog(this, null);
            if (dialog.isSaved()) {
                loadMovies();
            }
        });
        p1.add(addBtn);

        
        JButton btnExport = addButton("Export to File",xAxis, yAxis-80);
        JButton btnImport = addButton("Import from File",xAxis, yAxis-120);

        
        btnExport.setFocusPainted(false);
        btnImport.setFocusPainted(false);

        
        btnExport.addActionListener(e -> {
            
            List<Movie> currentMovies = MovieDB.getAllMovies(); 
            
            FileHandler.exportMovies(currentMovies);
            JOptionPane.showMessageDialog(this, "Library exported to cinelog_backup.txt!");
        });

        
        btnImport.addActionListener(e -> {
            
            List<Movie> importedMovies = FileHandler.importMovies();
            
            
            for (Movie m : importedMovies) {
                
                MovieDB.addMovie(m.getTitle(), m.getYear(), m.getDirector(), m.getGenreId(),m.getPosterPath()); 
            }
            
            
            loadMovies(); 
            JOptionPane.showMessageDialog(this, "Library restored from file!");
        });

        
        p1.add(btnExport);
        p1.add(btnImport);
            

        return p1;
    }

    private JPanel moviegrid() {
    
        JPanel center = new JPanel(new BorderLayout());
        center.setBounds(185,0,810,570);
        center.setBackground(new Color(22, 22, 37));

        // top bar
        JPanel bar = new JPanel();
        bar.setBackground(new Color(28, 28, 40));

        searchField = new JTextField(16);
        searchField.setBackground(new Color(45, 45, 60));
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        searchField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(80, 80, 100)),
        BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        searchField.addActionListener(e -> loadMovies());

        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(45, 45, 80));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        searchBtn.addActionListener(e -> loadMovies());

        genreBox = new JComboBox<>();
        genreBox.addItem(new Genre(0, "All Genres"));
        for(Genre genre : MovieDB.getAllGenres()){
            genreBox.addItem(genre);
        }
        styleCombo(genreBox);
        genreBox.addActionListener(e -> loadMovies());

        statusBox = new JComboBox<>(new String[]{"All", "Want to Watch", "Watching", "Watched"});
        styleCombo(statusBox);
        statusBox.addActionListener(e -> loadMovies());

        sortBox = new JComboBox<>(new String[]{"Default", "Title", "Year", "Rating"});
        styleCombo(sortBox);
        sortBox.addActionListener(e -> loadMovies());

        bar.add(new JLabel("Search:") {{ setForeground(Color.WHITE); }});
        bar.add(searchField);
        bar.add(searchBtn);
        bar.add(new JLabel("Genre:") {{ setForeground(Color.WHITE); }});
        bar.add(genreBox);
        bar.add(new JLabel("Status:") {{ setForeground(Color.WHITE); }});
        bar.add(statusBox);
        bar.add(new JLabel("Sort:") {{ setForeground(Color.WHITE); }});
        bar.add(sortBox);

        // grid
        gridPanel = new JPanel(new GridLayout(0, 4, 12, 12));
        gridPanel.setBackground(new Color(22, 22, 40));
        gridPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane scroll = new JScrollPane(gridPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(22, 22, 32));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        center.add(bar,    BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);

        loadMovies(); 
        return center;
    }

    public void loadMovies() {
        gridPanel.removeAll();

        String keyword = searchField != null ? searchField.getText().trim() : "";
        Genre  genre   = genreBox != null ? (Genre) genreBox.getSelectedItem() : null;
        String status  = statusBox != null ? (String) statusBox.getSelectedItem() : "All";
        String sort    = sortBox != null ? (String) sortBox.getSelectedItem() : "Default";
        int genreId    = (genre != null) ? genre.getId() : 0;

        List<Movie> movies = MovieDB.searchMovies(keyword, genreId, status);

        // apply sort
        if (sort.equals("Title")) {
            movies.sort((a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
        } 
        else if (sort.equals("Year")) {
            movies.sort((a, b) -> b.getYear() - a.getYear());
        } 
        

        if (movies.isEmpty()) {

            gridPanel.setLayout(new FlowLayout());
            JLabel empty = new JLabel("No movies found. Click '+ Add Movie' to get started.");
            empty.setForeground(new Color(120, 120, 150));
            empty.setFont(new Font("Arial", Font.PLAIN, 14));
            gridPanel.add(empty);
        } else {

            gridPanel.setLayout(new GridLayout(0, 4, 12, 12));
            for (Movie m : movies) {
                
                gridPanel.add(new MovieCard(m, () -> {
                    DetailDialog d = new DetailDialog(this, m);
                    
                }));
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JButton addButton(String text, int xAxis, int yAxis){
        JButton btn = new JButton(text);
        btn.setForeground(Color.white);
        btn.setCursor(cursor);
        
        btn.setBounds(xAxis, yAxis, 150, 25);
        btn.setBackground(new Color(45, 45, 65));
        btn.setFocusPainted(false);
        return btn;
    }

    
    private void styleCombo(JComboBox<?> box) {
        box.setBackground(new Color(45, 45, 60));
        box.setForeground(Color.WHITE);
    }

    


   public static void main(String[] args) {
    
    new MainWindow();
   }
}
 
