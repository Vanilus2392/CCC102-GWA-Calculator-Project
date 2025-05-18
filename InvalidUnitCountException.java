
import javax.swing.JLabel;

public class InvalidUnitCountException extends Exception {
    public InvalidUnitCountException() {
        JLabel resultLabel = new JLabel();

            resultLabel.setText("Please input a valid unit (1 - 10)");
    }
}
