import javax.swing.JLabel;

public class InvalidGradeCountException extends Exception {
    public InvalidGradeCountException() {
        JLabel resultLabel = new JLabel();

            resultLabel.setText("Please input a valid grade (1 - 5)");
    }
}
