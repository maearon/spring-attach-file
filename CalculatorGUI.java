import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CalculatorGUI extends JFrame implements ActionListener {
    private JTextField display;
    private JTextArea historyArea;
    private String operator = "";
    private double firstNum = 0;
    private boolean isResultDisplayed = false;
    private ArrayList<String> history = new ArrayList<>();

    public CalculatorGUI() {
        setTitle("Máy tính cầm tay");
        setSize(350, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        add(display, BorderLayout.NORTH);

        String[] buttons = {
            "7", "8", "9", "/", 
            "4", "5", "6", "*", 
            "1", "2", "3", "-", 
            "0", ".", "=", "+",
            "C"
        };

        JPanel panel = new JPanel(new GridLayout(5, 4, 5, 5));
        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);

        // Lịch sử tính toán
        historyArea = new JTextArea(5, 20);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        add(scrollPane, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("0123456789.".contains(cmd)) {
            if (isResultDisplayed) {
                display.setText("");
                isResultDisplayed = false;
            }
            display.setText(display.getText() + cmd);
        } else if ("+-*/".contains(cmd)) {
            try {
                firstNum = Double.parseDouble(display.getText());
                operator = cmd;
                display.setText("");
            } catch (Exception ex) {
                display.setText("Lỗi");
            }
        } else if ("=".equals(cmd)) {
            try {
                double secondNum = Double.parseDouble(display.getText());
                double result = 0;
                switch (operator) {
                    case "+": result = firstNum + secondNum; break;
                    case "-": result = firstNum - secondNum; break;
                    case "*": result = firstNum * secondNum; break;
                    case "/": 
                        if (secondNum != 0) result = firstNum / secondNum;
                        else {
                            display.setText("Lỗi chia 0");
                            return;
                        }
                        break;
                }
                String expression = firstNum + " " + operator + " " + secondNum + " = " + result;
                history.add(expression);
                updateHistory();
                display.setText(String.valueOf(result));
                isResultDisplayed = true;
            } catch (Exception ex) {
                display.setText("Lỗi");
            }
        } else if ("C".equals(cmd)) {
            display.setText("");
        }
    }

    private void updateHistory() {
        StringBuilder sb = new StringBuilder();
        for (int i = history.size() - 1; i >= 0 && i >= history.size() - 10; i--) {
            sb.append(history.get(i)).append("\n");
        }
        historyArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorGUI().setVisible(true));
    }
}
