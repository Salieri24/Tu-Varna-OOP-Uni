import javax.swing.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

public class Calculator extends JDialog {
    private JPanel contentPane;
    private JButton plus;
    private JButton minus;
    private JButton mul;
    private JButton div;
    private JTextField number1;
    private JTextField number2;
    private JTextField answer;

    private final DecimalFormat decimalFormat = new DecimalFormat("0.0000");

    public Calculator() {
        setContentPane(contentPane);
        setModal(true);
        plus.addActionListener(this::calculatePlus);
        minus.addActionListener(this::calculateMinus);
        mul.addActionListener(this::calculateMul);
        div.addActionListener(this::calculateDiv);
    }

    private void calculatePlus(ActionEvent actionEvent) {
        try {
            double num1 = toDouble(number1.getText());
            double num2 = toDouble(number2.getText());
            answer.setText("" + decimalFormat.format(num1 + num2));
        } catch (Exception exception) {
            answer.setText("error");
            exception.printStackTrace();
        }
    }

    private void calculateMinus(ActionEvent actionEvent) {
        try {
            double num1 = toDouble(number1.getText());
            double num2 = toDouble(number2.getText());
            answer.setText("" + decimalFormat.format(num1 - num2));
        } catch (Exception exception) {
            answer.setText("error");
            exception.printStackTrace();
        }
    }

    private void calculateMul(ActionEvent actionEvent) {
        try {
            double num1 = toDouble(number1.getText());
            double num2 = toDouble(number2.getText());
            answer.setText("" + decimalFormat.format(num1 * num2));
        } catch (Exception exception) {
            answer.setText("error");
            exception.printStackTrace();
        }
    }

    private void calculateDiv(ActionEvent actionEvent) {
        try {
            double num1 = toDouble(number1.getText());
            double num2 = toDouble(number2.getText());
            if (num2 == 0D) throw new Exception("Cannot divide by 0");
            answer.setText("" + decimalFormat.format(num1 / num2));
        } catch (Exception exception) {
            answer.setText("error");
            exception.printStackTrace();
        }
    }

    public double toDouble(String s) {
        return Double.parseDouble(s);
    }

    public static void main(String[] args) {
        Calculator dialog = new Calculator();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
