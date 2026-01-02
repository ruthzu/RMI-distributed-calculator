import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class CalculatorClient extends JFrame {
    private JTextField num1Field = new JTextField();
    private JTextField num2Field = new JTextField();
    private JTextField resultField = new JTextField();
    private Calculator calculator;

    public CalculatorClient() {
        try {
            // Lookup the remote object
            calculator = (Calculator) Naming.lookup("rmi://localhost/Calculator");
            System.out.println("Connected to remote Calculator server");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Cannot connect to server:\n" + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }

        // GUI Setup
        setTitle("RMI Distributed Calculator");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));
        setLocationRelativeTo(null);

        add(new JLabel("  Number 1:"));
        add(num1Field);

        add(new JLabel("  Number 2:"));
        add(num2Field);

        add(createButton("Add", "+"));
        add(createButton("Subtract", "-"));
        add(createButton("Multiply", "*"));
        add(createButton("Divide", "/"));

        add(new JLabel("  Result:"));
        resultField.setEditable(false);
        resultField.setFont(new Font("Arial", Font.BOLD, 16));
        add(resultField);

        setVisible(true);
    }

    private JButton createButton(String label, String op) {
        JButton btn = new JButton(label);
        btn.addActionListener(e -> performOperation(op));
        return btn;
    }

    private void performOperation(String op) {
        try {
            double n1 = Double.parseDouble(num1Field.getText());
            double n2 = Double.parseDouble(num2Field.getText());
            double result = 0;

            switch (op) {
                case "+" -> result = calculator.add(n1, n2);
                case "-" -> result = calculator.subtract(n1, n2);
                case "*" -> result = calculator.multiply(n1, n2);
                case "/" -> result = calculator.divide(n1, n2);
            }

            resultField.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers");
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Remote error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorClient());
    }
}