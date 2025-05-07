import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Test implements ActionListener{
    public final JFrame baseFrame;
    private final JPanel topPanel, bottomPanel, segment, midPanel, midTopPanel, midInsidePanel;
    private final JLabel courseCountLabel, resultLabel;
    private final JButton addButton, removeButton, calculateButton;
    private int courseCount = 0;
    private List<JPanel> courseEntryPanels = new ArrayList<>();
    private JPanel coursePanel;
    private double result;
    private final JScrollPane scrollPane;

    public Test(){
        //title section
            Title title = new Title();
        //courseCountLabel section;
        courseCountLabel = new JLabel();
            courseCountLabel.setText("Course Count: " + courseCount + "      ");
            courseCountLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
            courseCountLabel.setForeground(new Color(0xF5EEDD));
            courseCountLabel.setHorizontalAlignment(JLabel.CENTER);
            courseCountLabel.setVerticalAlignment(JLabel.CENTER);
        
        //resultLabel section;
        resultLabel = new JLabel();
            resultLabel.setText("Result: " + result);
            resultLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
            resultLabel.setForeground(new Color(0xF5EEDD));

        //removeButton section
        removeButton = new JButton("-");
            removeButton.setPreferredSize(new Dimension(50, 25));
            removeButton.setFont(new Font("Dialog", Font.PLAIN, 10));
            removeButton.setBackground(new Color(0xF5EEDD));
            removeButton.setForeground(new Color(0x06202B));
            removeButton.setFocusable(false);
            removeButton.addActionListener(this);
            
        //addButton section
        addButton = new JButton("+");
            addButton.setPreferredSize(new Dimension(50, 25));
            addButton.setFont(new Font("Dialog", Font.PLAIN, 10));
            addButton.setBackground(new Color(0xF5EEDD));
            addButton.setForeground(new Color(0x06202B));
            addButton.setFocusable(false);
            addButton.addActionListener(this);

        //calculateButton section
        calculateButton = new JButton("Calculate");
            calculateButton.setPreferredSize(new Dimension(125, 25));
            calculateButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
            calculateButton.setBackground(new Color(0xF5EEDD));
            calculateButton.setForeground(new Color(0x06202B));
            calculateButton.setFocusable(false);
            calculateButton.addActionListener(this);

        //topPanel section
        topPanel = new JPanel();
            topPanel.setBackground(new Color(0x077A7D));
            topPanel.add(title);
            topPanel.setPreferredSize(new Dimension(100, 50));

        //bottomPanel section
        bottomPanel = new JPanel();
            bottomPanel.setBackground(new Color(0x06202B));
            bottomPanel.setPreferredSize(new Dimension(100, 50));
            bottomPanel.add(calculateButton);
            bottomPanel.add(resultLabel);

        //midTopPanel section
        midTopPanel = new JPanel();
            midTopPanel.setBackground(new Color(0x06202B));
            midTopPanel.setPreferredSize(new Dimension(450, 50));
            midTopPanel.add(courseCountLabel);
            midTopPanel.add(removeButton);
            midTopPanel.add(addButton);

        //midInsidePanel section
        midInsidePanel = new JPanel();
            midInsidePanel.setBackground(new Color(0x06202B));
            midInsidePanel.setPreferredSize(new Dimension(600, 3000));
        
        //scrollPane section
        scrollPane = new JScrollPane(midInsidePanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove default border
            scrollPane.setPreferredSize(new Dimension(600, 600)); // Adjusted size as needed
       
        //midPanel section
        midPanel = new JPanel();
            midPanel.setBackground(new Color(0x06202B));
            midPanel.setPreferredSize(new Dimension(600, 3000));
            midPanel.add(midTopPanel);
            midPanel.add(scrollPane);

        //segment section
        segment = new JPanel();
            segment.setBackground(new Color(0x06202B));
            segment.setPreferredSize(new Dimension(100, 70));
            segment.add(midPanel);
        
        //frame section... configurable
        baseFrame = new JFrame();
            baseFrame.setTitle("Test");
            baseFrame.setSize(800,600);
            baseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            baseFrame.setLayout(new BorderLayout());
            baseFrame.setVisible(true);
        
        //to add in baseFrame section
        baseFrame.add(topPanel, BorderLayout.NORTH);
        baseFrame.add(segment, BorderLayout.CENTER);
        baseFrame.add(bottomPanel, BorderLayout.SOUTH);
        
    }
    public void addCourseEntryPanel(){
        courseCount++;
        courseCountLabel.setText("Course Count: " + courseCount + "      ");

        JLabel courseLabel = new JLabel("Course name: " );
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
            coursePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Left alignment and no gap
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
    public void removeCourseEntryPanel(){
        if(courseCount > 0){
            courseCount--;
            courseCountLabel.setText("Course Count: " + courseCount + "      ");
            midInsidePanel.remove(courseEntryPanels.get(courseCount));
            courseEntryPanels.remove(courseCount);
            midInsidePanel.revalidate();
            midInsidePanel.repaint();
        }
    }
    public static void main(String[] args) {
        Test test = new Test();
    }
    public void calculate() {
        double totalUnits = 0;
        double totalWeightedGrades = 0;
    
        for (JPanel panel : courseEntryPanels) {
            try {
                String unitsText = ((JTextField) panel.getComponent(3)).getText();
                String gradeText = ((JTextField) panel.getComponent(5)).getText();
    
                double units = Double.parseDouble(unitsText);
                double grade = Double.parseDouble(gradeText);
    
                totalUnits += units;
                totalWeightedGrades += units * grade;
            } catch (NumberFormatException e) {
                resultLabel.setText("Result: Invalid input");
                return; // Exit if there's an error in input
            }
        }
        if(totalUnits < 0) {
            resultLabel.setText("Result: 0");
        }
        result = totalWeightedGrades / totalUnits;

        Icon[] icons = new Icon[3];
        JFrame newWindow = new JFrame("Result");

            if((result >= 1.0) && (result <= 1.25)) {
                icons[0] = new ImageIcon("pic1.jpg");
                newWindow.add(new JLabel(icons[0]));
            } else if((result > 1.26) && (result <= 3.0)) {
                icons[1] = new ImageIcon("pic2.jpg");
                newWindow.add(new JLabel(icons[1]));
            } else {
               
            }

            newWindow.setSize(487, 314);
            newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newWindow.setLocationRelativeTo(baseFrame); // Center the window
            newWindow.setVisible(true);
        
        resultLabel.setText("Result: " + String.format("%.2f", result));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addButton){
            addCourseEntryPanel();
        }
        if(e.getSource() == removeButton){
            removeCourseEntryPanel();
        }
        if(e.getSource() == calculateButton){
            calculate();
        }
    }
}