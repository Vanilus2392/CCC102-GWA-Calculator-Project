import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.*;

public class GwaCalculator implements ActionListener {
    public final JFrame baseFrame;
    private final JPanel midPanel;
    private final JPanel midTopPanel;
    private final JPanel midInsidePanel;
    private final JLabel courseCountLabel, resultLabel, quoteLabel; //added quoteLabel: 08-05-25
    private final JButton addButton, removeButton, calculateButton;
    private int courseCount = 0;
    private final List<JPanel> courseEntryPanels = new ArrayList<>();
    private JPanel coursePanel;
    private double result;

    private final String[] quotes = { //added quotes: 08-05-25
        "“Success is the sum of small efforts, repeated day in and day out.”",
        "“Study hard. No shortcuts.”",
        "“Push yourself, because no one else is going to do it for you.”",
        "“Don't watch the clock; do what it does. Keep going.”",
        "“The future belongs to those who prepare for it today.”",
        "“Hard work beats talent when talent doesn't work hard.”",
        "“Discipline is the bridge between goals and accomplishment.”",
        "“Success doesn’t come to you. You go to it.”",
        "“Wake up with determination. Go to bed with satisfaction.”",
        "“There is no substitute for hard work.”"
    };

    public GwaCalculator() {
        // Title section
        Title title = new Title();

        // Course count label
        courseCountLabel = new JLabel("Course Count: " + courseCount + "      ");
        courseCountLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        courseCountLabel.setForeground(new Color(0xF5EEDD));
        courseCountLabel.setHorizontalAlignment(JLabel.CENTER);
        courseCountLabel.setVerticalAlignment(JLabel.CENTER);

        // Quote label added: 08-05-25
        quoteLabel = new JLabel();
        quoteLabel.setFont(new Font("Comic Sans MS", Font.ITALIC, 14));
        quoteLabel.setForeground(new Color(0xF5EEDD));
        quoteLabel.setHorizontalAlignment(JLabel.CENTER);
        quoteLabel.setVerticalAlignment(JLabel.CENTER);
        updateQuote(); // Show a random quote at startup

        // Result label
        resultLabel = new JLabel("Result: " + result);
        resultLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        resultLabel.setForeground(new Color(0xF5EEDD));

        // Buttons
        removeButton = new JButton("-");
        removeButton.setPreferredSize(new Dimension(50, 25));
        removeButton.setFont(new Font("Dialog", Font.PLAIN, 10));
        removeButton.setBackground(new Color(0xF5EEDD));
        removeButton.setForeground(new Color(0x06202B));
        removeButton.setFocusable(false);
        

        addButton = new JButton("+");
        addButton.setPreferredSize(new Dimension(50, 25));
        addButton.setFont(new Font("Dialog", Font.PLAIN, 10));
        addButton.setBackground(new Color(0xF5EEDD));
        addButton.setForeground(new Color(0x06202B));
        addButton.setFocusable(false);

        calculateButton = new JButton("Calculate");
        calculateButton.setPreferredSize(new Dimension(125, 25));
        calculateButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        calculateButton.setBackground(new Color(0xF5EEDD));
        calculateButton.setForeground(new Color(0x06202B));
        calculateButton.setFocusable(false);
        // 08-25-04 Update: Button listeners will be initialized after the constructor finishes

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0x077A7D));
        topPanel.add(title);
        topPanel.setPreferredSize(new Dimension(100, 50));

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0x06202B));
        bottomPanel.setPreferredSize(new Dimension(100, 70));
        bottomPanel.setLayout(new GridLayout(3, 1));
        bottomPanel.add(quoteLabel); // added quoteLabel to bottomPanel: 08-05-25
        bottomPanel.add(resultLabel);
        bottomPanel.add(calculateButton);

        // Mid Top Panel
        midTopPanel = new JPanel();
        midTopPanel.setBackground(new Color(0x06202B));
        midTopPanel.setPreferredSize(new Dimension(450, 50));
        midTopPanel.add(courseCountLabel);
        midTopPanel.add(removeButton);
        midTopPanel.add(addButton);

        // Mid Inside Panel
        midInsidePanel = new JPanel();
        midInsidePanel.setBackground(new Color(0x06202B));
        midInsidePanel.setPreferredSize(new Dimension(600, 3000));

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(midInsidePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(600, 600));

        // Mid Panel
        midPanel = new JPanel();
        midPanel.setBackground(new Color(0x06202B));
        midPanel.setPreferredSize(new Dimension(600, 3000));
        midPanel.add(midTopPanel);
        midPanel.add(scrollPane);

        // Segment Panel
        JPanel segment = new JPanel();
        segment.setBackground(new Color(0x06202B));
        segment.setPreferredSize(new Dimension(100, 70));
        segment.add(midPanel);

        // Frame setup
        baseFrame = new JFrame();
        baseFrame.setTitle("GWA Calculator by Casino and Gustilo v1.0");
        baseFrame.setSize(800, 600);
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseFrame.setLayout(new BorderLayout());
        baseFrame.add(topPanel, BorderLayout.NORTH);
        baseFrame.add(segment, BorderLayout.CENTER);
        baseFrame.add(bottomPanel, BorderLayout.SOUTH);
        baseFrame.setVisible(true);
    }

    private void initializeButtonListeners() {  // added 08-05-25 
        removeButton.addActionListener(this);
        addButton.addActionListener(this);
        calculateButton.addActionListener(this);
    }

    public void playSound(String soundFile) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            Logger.getLogger(GwaCalculator.class.getName()).log(Level.SEVERE, "Error playing sound", e);
        }
    }
    
    

    private void updateQuote() { // ADDED 08-05-25 Method to update the quote label with a random quote
        int index = (int)(Math.random() * quotes.length);
        quoteLabel.setText(quotes[index]);
    }

    public void addCourseEntryPanel() {
        // 08-05-25: Added limit for course count
        final int MAX_COURSE_COUNT = 60;
        if (courseCount >= MAX_COURSE_COUNT) {
            JOptionPane.showMessageDialog(baseFrame,
                "You can only add up to " + MAX_COURSE_COUNT + " courses.",
                "Limit Reached",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        courseCount++;
        courseCountLabel.setText("Course Count: " + courseCount + "      ");

        JLabel courseLabel = new JLabel("Course name: ");
        courseLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        courseLabel.setForeground(new Color(0xF5EEDD));
        JLabel units = new JLabel("     Units:" );
        units.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        units.setForeground(new Color(0xF5EEDD));
        JLabel grade = new JLabel("     Grade:" );
        grade.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        grade.setForeground(new Color(0xF5EEDD));

        coursePanel = new JPanel();
        coursePanel.setBackground(new Color(0x06202B));
        coursePanel.setPreferredSize(new Dimension(600, 30));
        coursePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        coursePanel.add(courseLabel);
        coursePanel.add(new JTextField(15));
        coursePanel.add(units);
        coursePanel.add(new JTextField(5));
        coursePanel.add(grade);
        coursePanel.add(new JTextField(5));

        courseEntryPanels.add(coursePanel);
        midInsidePanel.add(coursePanel);
        midInsidePanel.revalidate();
        midInsidePanel.repaint();
    }

    public void removeCourseEntryPanel() {
        if (courseCount > 0) {
            courseCount--;
            courseCountLabel.setText("Course Count: " + courseCount + "      ");
            midInsidePanel.remove(courseEntryPanels.get(courseCount));
            courseEntryPanels.remove(courseCount);
            midInsidePanel.revalidate();
            midInsidePanel.repaint();
        }
    }

    public void calculate() throws InvalidUnitCountException, InvalidGradeCountException {
        updateQuote(); // Refresh the quote

        double totalUnits = 0;
        double totalWeightedGrades = 0;

        for (JPanel panel : courseEntryPanels) {
            try {
                String unitsText = ((JTextField) panel.getComponent(3)).getText();
                String gradeText = ((JTextField) panel.getComponent(5)).getText();

                double units = Double.parseDouble(unitsText);
                double grade = Double.parseDouble(gradeText);
                // 08-05-25: Added validation for units and grade
                if (units > 10 || units < 1){ 
                    throw new InvalidUnitCountException();
                }
                if (grade > 5.0 || grade < 1.00){
                    throw new InvalidGradeCountException();
                }
                totalUnits += units;
                totalWeightedGrades += units * grade;
        
            } catch (NumberFormatException | ClassCastException e) {
                resultLabel.setText("Result: Invalid input");
                return;
            }
        }

        if (totalUnits <= 0) {
            resultLabel.setText("Result: 0");
            return;
        }

        result = totalWeightedGrades / totalUnits;

        resultLabel.setText("Result: " + String.format("%.2f", result));
        resultLabel.repaint(); // force refresh
        // 08-05-25: v Moved below result label in order to display result before showing image
        Icon[] icons = new Icon[3];
        JDialog newWindow = new JDialog(baseFrame, "RESULT", true); // true makes it modal
        newWindow.setSize(487, 314);
        newWindow.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        newWindow.setLocationRelativeTo(baseFrame);
        
        if ((result >= 1.0) && (result <= 1.25)) {
            icons[0] = new ImageIcon(getClass().getResource("/images/pic1.jpg"));
            newWindow.add(new JLabel(icons[0]));
            playSound("/sounds/excellent.wav"); // ADDED SFX 08-05-25

        } else if ((result > 1.25) && (result < 3.0)) {
            icons[1] = new ImageIcon(getClass().getResource("/images/pic2.jpg"));
            newWindow.add(new JLabel(icons[1]));
            playSound("/sounds/good.wav");

        } else if (result >= 3.0) {
            icons[2] = new ImageIcon(getClass().getResource("/images/pic3.jpg"));
            newWindow.add(new JLabel(icons[2]));
            playSound("/sounds/okay.wav");
        }
        newWindow.setVisible(true);
 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addCourseEntryPanel();
        }
        if (e.getSource() == removeButton) {
            removeCourseEntryPanel();
        }
        if (e.getSource() == calculateButton) {
            try {
                calculate();
            } catch (InvalidUnitCountException iuce) {
                resultLabel.setText("Result: Please input a valid unit count (1-10)");
            } catch (InvalidGradeCountException igce) {
                resultLabel.setText("Result: Please input a valid grade (1.00 - 5.00)");
            }
        }
    }

    public static void main(String[] args) {
        GwaCalculator calculator = new GwaCalculator();
        calculator.initializeButtonListeners(); // Initialize button listeners after construction
    }
}