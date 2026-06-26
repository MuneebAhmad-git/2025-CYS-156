import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

// Shows stats: total movies, avg rating, genre breakdown, rating distribution, top 10
public class StatsDialog extends JDialog {

    public StatsDialog(JFrame parent) {
        super(parent, "Stats", true);
        setSize(500, 560);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(new Color(22, 22, 32));
        main.setBorder(new EmptyBorder(14, 14, 14, 14));

        main.add(makeStatCards());
        main.add(Box.createVerticalStrut(14));
        main.add(makeGenreSection());
        main.add(Box.createVerticalStrut(14));
        main.add(makeRatingSection());
        main.add(Box.createVerticalStrut(14));
        main.add(makeTop10Section());

        JScrollPane scroll = new JScrollPane(main);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(22, 22, 32));
        add(scroll);

        setVisible(true);
    }

    private JPanel makeStatCards() {
        JPanel row = new JPanel(new GridLayout(1, 4, 8, 0));
        row.setBackground(new Color(22, 22, 32));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        row.add(card("Total",    String.valueOf(MovieDB.getTotalMovies()),                         new Color(50, 80, 160)));
        row.add(card("Watched",  String.valueOf(MovieDB.getTotalWatched()),                        new Color(40, 120, 80)));
        row.add(card("Avg",      String.format("%.1f", MovieDB.getOverallAvgRating()),             new Color(160, 120, 30)));
        row.add(card("Top Genre", MovieDB.getFavouriteGenre(),                                     new Color(120, 50, 150)));
        return row;
    }

    private JPanel card(String label, String value, Color color) {
        JPanel c = new JPanel(new BorderLayout());
        c.setBackground(color);
        c.setBorder(new EmptyBorder(8, 10, 8, 10));
        JLabel v = new JLabel(value, SwingConstants.CENTER);
        v.setForeground(Color.WHITE);
        v.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel l = new JLabel(label, SwingConstants.CENTER);
        l.setForeground(new Color(220, 220, 230));
        l.setFont(new Font("Arial", Font.PLAIN, 11));
        c.add(v, BorderLayout.CENTER);
        c.add(l, BorderLayout.SOUTH);
        return c;
    }

    private JPanel makeGenreSection() {
        JPanel panel = makeSection("Movies by Genre");
        Map<String, Integer> data = MovieDB.getGenreStats();
        int max = data.values().stream().mapToInt(v -> v).max().orElse(1);
        Color[] colors = {new Color(80,130,220), new Color(80,180,120), new Color(220,160,60), new Color(180,80,180), new Color(220,90,90)};
        int i = 0;
        for (Map.Entry<String, Integer> e : data.entrySet()) {
            panel.add(barRow(e.getKey(), e.getValue(), max, colors[i % colors.length]));
            i++;
        }
        if (data.isEmpty()) panel.add(emptyLabel("No movies added yet."));
        return panel;
    }

    private JPanel makeRatingSection() {
        JPanel panel = makeSection("Rating Distribution");
        Map<Integer, Integer> data = MovieDB.getRatingStats();
        int max = data.values().stream().mapToInt(v -> v).max().orElse(1);
        for (int star = 5; star >= 1; star--) {
            int count = data.getOrDefault(star, 0);
            panel.add(barRow(star + " / 5", count, max, new Color(255, 200, 50)));
        }
        return panel;
    }

    private JPanel makeTop10Section() {
        JPanel panel = makeSection("Top 10 by Rating");
        List<Movie> top = MovieDB.getTop10();
        for (int i = 0; i < top.size(); i++) {
            Movie m = top.get(i);
            JLabel row = new JLabel((i + 1) + ".  " + m.getTitle() + "   " + String.format("%.1f/5", m.getAvgRating()));
            row.setForeground(i == 0 ? new Color(255, 200, 50) : new Color(180, 180, 200));
            row.setFont(new Font("Arial", i == 0 ? Font.BOLD : Font.PLAIN, 12));
            row.setBorder(new EmptyBorder(3, 8, 3, 8));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
            panel.add(row);
        }
        if (top.isEmpty()) panel.add(emptyLabel("Rate some movies first."));
        return panel;
    }

    private JPanel makeSection(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(30, 30, 45));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 80)), title,
            0, 0, new Font("Arial", Font.BOLD, 12), new Color(180, 180, 200)));
        return panel;
    }

    private JPanel barRow(String label, int value, int max, Color color) {
        JPanel row = new JPanel(new BorderLayout(6, 0));
        row.setBackground(new Color(30, 30, 45));
        row.setBorder(new EmptyBorder(3, 8, 3, 8));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));

        JLabel nameLbl = new JLabel(label);
        nameLbl.setForeground(new Color(180, 180, 200));
        nameLbl.setFont(new Font("Arial", Font.PLAIN, 11));
        nameLbl.setPreferredSize(new Dimension(70, 20));

        int barWidth = max == 0 ? 0 : (int)((double) value / max * 250);
        JPanel barBg = new JPanel(new BorderLayout());
        barBg.setBackground(new Color(50, 50, 70));
        JPanel bar = new JPanel();
        bar.setBackground(color);
        bar.setPreferredSize(new Dimension(barWidth, 16));
        barBg.add(bar, BorderLayout.WEST);

        JLabel cnt = new JLabel(" " + value);
        cnt.setForeground(Color.WHITE);
        cnt.setFont(new Font("Arial", Font.PLAIN, 11));

        row.add(nameLbl, BorderLayout.WEST);
        row.add(barBg,   BorderLayout.CENTER);
        row.add(cnt,     BorderLayout.EAST);
        return row;
    }

    private JLabel emptyLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(120, 120, 150));
        l.setBorder(new EmptyBorder(6, 10, 6, 10));
        return l;
    }
}
