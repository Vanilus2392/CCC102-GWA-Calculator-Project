import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class GWA extends JFrame implements ActionListener{
    private final JLabel title, courseNumText, courseNumDisplay;
    private JButton addCourse, rmCourse, calculate;
    private JPanel topPanel, coursesPanel;
    private List<JPanel> courseEntryPanels = new ArrayList<>();
    private int MAX_ENTRY = 10;
    private int courseCounter = 0;
    JScrollPane coursesScrollPane;
    private Font defaultFont = new Font("Dialog", Font.BOLD, 16);

    public GWA(){
        title = new JLabel();
        title.setText("GWA Calculator");
        title.setFont(new Font("Dialog", Font.BOLD, 20));
        title.setBounds(165, 20 , 300, 50);

        courseNumText = new JLabel("Number of Course/s: ");
        courseNumText.setFocusable(false);
        courseNumText.setFont(new Font("Dialog", Font.BOLD, 15));
        courseNumText.setBounds(20, 40, 200, 100);

        rmCourse = new JButton("-");
        rmCourse.setFocusable(false);
        rmCourse.setBounds(370,80,40,20);
        rmCourse.setFont(new Font("Dialog", Font.BOLD, 10));
        rmCourse.addActionListener(this);

        addCourse = new JButton("+");
        addCourse.setFocusable(false);
        addCourse.setBounds(400,80,40,20);
        addCourse.setFont(new Font("Dialog", Font.BOLD, 10));
        addCourse.addActionListener(this);

        calculate = new JButton("Calculate");
        calculate.setFocusable(false);
        calculate.setFont(new Font("Dialog", Font.BOLD, 10));
        calculate.addActionListener(this);
        
        coursesPanel = new JPanel();
        coursesPanel.setLayout(new FlowLayout(FlowLayout.LEADING)); 
        coursesPanel.setBounds(20, 100, 460, 500); 

        courseNumDisplay = new JLabel(String.valueOf(courseCounter));
        courseNumDisplay.setFont(defaultFont);
        courseNumDisplay.setBounds(175, 40, 200, 100);

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
        topPanel.setBounds(0, 70, 500, 100); 

        this.setTitle("GWA-Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,650);
        this.setLayout(null);
        this.setResizable(false);

        topPanel.add(courseNumText);
        topPanel.add(courseNumDisplay);
        topPanel.add(rmCourse);
        topPanel.add(addCourse);

        this.add(topPanel);
        this.add(title);
        this.add(coursesPanel);
        this.setVisible(true);
    }
    public void addCourseEntryPanel(){
        if(courseCounter < MAX_ENTRY){
        JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField courseField = new JTextField(8);
        JTextField unitsField = new JTextField(4);
        JTextField gradeField = new JTextField(4);

        coursePanel.add(new JLabel("Course name (Optional):"));
        coursePanel.add(courseField);
        coursePanel.add(new JLabel("Units:"));
        coursePanel.add(unitsField);
        coursePanel.add(new JLabel("Grade:"));
        coursePanel.add(gradeField);

        courseEntryPanels.add(coursePanel);
        coursesPanel.add(coursePanel);
        coursesPanel.revalidate(); 
        coursesPanel.repaint();
        }
    }
    public void removeCourseEntryPanel(){
        if (courseCounter > 0) {
            coursesPanel.remove(courseEntryPanels.get(courseCounter - 1)); 
            courseEntryPanels.remove(courseCounter - 1); 
            courseCounter--;
            courseNumDisplay.setText(String.valueOf(courseCounter));
            coursesPanel.revalidate(); 
            coursesPanel.repaint(); 
        }
    }
    public static void main(String[] args){
        new GWA();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addCourse){
            if(MAX_ENTRY > courseCounter){
                addCourseEntryPanel();
                courseCounter++;
                courseNumDisplay.setText(String.valueOf(courseCounter));
            }
        }
        if(e.getSource() == rmCourse){
            removeCourseEntryPanel();
        }
        if(e.getSource() == calculate){

        }
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
