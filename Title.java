import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class Title extends JLabel{

    public Title(){
            this.setText("GWA Calculator");
            this.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
            this.setForeground(new Color(0xF5EEDD));
            this.setHorizontalAlignment(JLabel.CENTER);
    }
}
