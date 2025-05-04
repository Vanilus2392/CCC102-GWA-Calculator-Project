import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Copy implements ActionListener {
    private final JFrame baseFrame;
    private final JPanel topPanel, bottomPanel, midTopPanel, segment;
    private final JLabel courseCountLabel, resultLabel;
    private JButton addButton, removeButton, calculateButton;
    private int courseCount = 0;
    private List<JPanel> courseEntryPanels = new ArrayList<>();
    private JPanel midPanel; // Changed back to JPanel, scrollPane holds it
    private JScrollPane scrollPane;

    public Copy() {
        // Title section
        Title title = new Title();
        courseCountLabel = new JLabel();
        courseCountLabel.setText("Course Count: " + courseCount + "      ");
        courseCountLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        courseCountLabel.setForeground(new Color(0xF5EEDD));
        courseCountLabel.setHorizontalAlignment(JLabel.CENTER);
        courseCountLabel.setVerticalAlignment(JLabel.CENTER);

        resultLabel = new JLabel();
        resultLabel.setText("Result: 0.00"); // Initial value
        resultLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
        resultLabel.setForeground(new Color(0xF5EEDD));

        // removeButton section
        removeButton = new JButton("-");
        removeButton.setPreferredSize(new Dimension(50, 25));
        removeButton.setFont(new Font("Dialog", Font.PLAIN, 10));
        removeButton.setBackground(new Color(0xF5EEDD));
        removeButton.setForeground(new Color(0x06202B));
        removeButton.setFocusable(false);
        removeButton.addActionListener(this);

        // addButton section
        addButton = new JButton("+");
        addButton.setPreferredSize(new Dimension(50, 25));
        addButton.setFont(new Font("Dialog", Font.PLAIN, 10));
        addButton.setBackground(new Color(0xF5EEDD));
        addButton.setForeground(new Color(0x06202B));
        addButton.setFocusable(false);
        addButton.addActionListener(this);

        // calculateButton section
        calculateButton = new JButton("Calculate");
        calculateButton.setPreferredSize(new Dimension(125, 25));
        calculateButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        calculateButton.setBackground(new Color(0xF5EEDD));
        calculateButton.setForeground(new Color(0x06202B));
        calculateButton.setFocusable(false);
        calculateButton.addActionListener(this);

        // topPanel section
        topPanel = new JPanel();
        topPanel.setBackground(new Color(0x077A7D));
        topPanel.add(title);
        topPanel.setPreferredSize(new Dimension(100, 50));

        // bottomPanel section
        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0x06202B));
        bottomPanel.setPreferredSize(new Dimension(100, 50));
        bottomPanel.add(calculateButton);
        bottomPanel.add(resultLabel);

        // midTopPanel section
        midTopPanel = new JPanel();
        midTopPanel.setBackground(new Color(0x06202B));
        midTopPanel.setPreferredSize(new Dimension(450, 50));
        midTopPanel.add(courseCountLabel);
        midTopPanel.add(removeButton);
        midTopPanel.add(addButton);

        // midPanel section - for course entry panels, put into the scroll pane
        midPanel = new JPanel();
        midPanel.setBackground(new Color(0x06202B));
        midPanel.setPreferredSize(new Dimension(600, 700)); // Increased for potential course entries
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS)); // Use BoxLayout
        midPanel.add(midTopPanel);

        // scrollPane section
        scrollPane = new JScrollPane(midPanel); // midPanel is added here
        scrollPane.setPreferredSize(new Dimension(600, 400)); // Adjusted size as needed
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(new Color(0x06202B));

        // segment section
        segment = new JPanel();
        segment.setBackground(new Color(0x06202B));
        segment.setPreferredSize(new Dimension(800, 450)); // Adjusted height
        segment.setLayout(new BorderLayout()); // Use BorderLayout
        segment.add(scrollPane, BorderLayout.CENTER); // scrollPane in center

        // frame section... configurable
        baseFrame = new JFrame();
        baseFrame.setTitle("Test");
        baseFrame.setSize(800, 550);
        baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baseFrame.setLayout(new BorderLayout());
        baseFrame.add(topPanel, BorderLayout.NORTH);
        baseFrame.add(segment, BorderLayout.CENTER); // segment (containing scrollPane)
        baseFrame.add(bottomPanel, BorderLayout.SOUTH);
        baseFrame.setVisible(true);
    }

    public void addCourseEntryPanel() {
        courseCount++;
        courseCountLabel.setText("Course Count: " + courseCount + "    ");

        JLabel courseLabel = new JLabel("Course name: ");
        courseLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        courseLabel.setForeground(new Color(0xF5EEDD));
        JLabel unitsLabel = new JLabel("     Units:");
        unitsLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        unitsLabel.setForeground(new Color(0xF5EEDD));
        JLabel gradeLabel = new JLabel("     Grade:");
        gradeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
        gradeLabel.setForeground(new Color(0xF5EEDD));

        JPanel coursePanel = new JPanel();
        coursePanel.setBackground(new Color(0x06202B));
        coursePanel.setPreferredSize(new Dimension(600, 30));
        coursePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Left alignment

        JTextField courseNameField = new JTextField(15);
        JTextField unitsField = new JTextField(5);
        JTextField gradeField = new JTextField(5);

        coursePanel.add(courseLabel);
        coursePanel.add(courseNameField);
        coursePanel.add(unitsLabel);
        coursePanel.add(unitsField);
        coursePanel.add(gradeLabel);
        coursePanel.add(gradeField);

        courseEntryPanels.add(coursePanel);
        midPanel.add(coursePanel); // Add to midPanel, which is in the scrollPane
        midPanel.revalidate();
        midPanel.repaint();
        scrollPane.revalidate(); // Make sure the scroll pane knows about the new content
    }

    public void removeCourseEntryPanel() {
        if (courseCount > 0) {
            courseCount--;
            courseCountLabel.setText("Course Count: " + courseCount + "    ");
            JPanel panelToRemove = courseEntryPanels.remove(courseCount);
            midPanel.remove(panelToRemove);
            midPanel.revalidate();
            midPanel.repaint();
            scrollPane.revalidate(); // Update scrollPane
        }
    }

    public void calculate() {
        double totalUnits = 0;
        double totalWeightedGrades = 0;

        for (JPanel panel : courseEntryPanels) {
            try {
                JTextField unitsTextField = (JTextField) panel.getComponent(3);
                JTextField gradeTextField = (JTextField) panel.getComponent(5);

                double units = Double.parseDouble(unitsTextField.getText());
                double grade = Double.parseDouble(gradeTextField.getText());

                totalUnits += units;
                totalWeightedGrades += units * grade;
            } catch (NumberFormatException | ClassCastException e) {
                JOptionPane.showMessageDialog(baseFrame, "Please enter valid numbers for units and grades.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop calculation on error
            }
        }

        if (totalUnits > 0) {
            double result = totalWeightedGrades / totalUnits;
            resultLabel.setText("Result: " + String.format("%.2f", result));
        } else {
            resultLabel.setText("Result: 0.00");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Test::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addCourseEntryPanel();
        } else if (e.getSource() == removeButton) {
            removeCourseEntryPanel();
        } else if (e.getSource() == calculateButton) {
            calculate();
        }
    }
}
