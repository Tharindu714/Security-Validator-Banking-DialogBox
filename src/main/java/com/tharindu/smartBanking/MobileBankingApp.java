package com.tharindu.smartBanking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

class Transaction {
    String account;
    double amount;
    String amountRaw;
    Transaction(String acct, String amtRaw) {
        this.account = acct;
        this.amountRaw = amtRaw;
    }
}

// --- Chain of Responsibility ---
interface Handler {
    Handler setNext(Handler next);
    String handle(Transaction tx);  // returns error message or null if OK
}

abstract class AbstractHandler implements Handler {
    private Handler next;
    public Handler setNext(Handler next) {
        this.next = next;
        return next;
    }
    public String handle(Transaction tx) {
        String err = check(tx);
        if (err != null) return err;
        if (next != null) return next.handle(tx);
        return null;
    }
    protected abstract String check(Transaction tx);
}

class FormatValidator extends AbstractHandler {
    protected String check(Transaction tx) {
        if (tx.account.isEmpty()) return "Account number is required.";
        try {
            tx.amount = Double.parseDouble(tx.amountRaw);
        } catch (NumberFormatException e) {
            return "Invalid amount format.";
        }
        if (tx.amount <= 0) return "Amount must be greater than zero.";
        return null;
    }
}

class LimitValidator extends AbstractHandler {
    private final double limit;
    LimitValidator(double limit) { this.limit = limit; }
    protected String check(Transaction tx) {
        if (tx.amount > limit) return "Exceeds daily limit of Rs. " + limit;
        return null;
    }
}

class FraudValidator extends AbstractHandler {
    private final double threshold;
    FraudValidator(double threshold) { this.threshold = threshold; }
    protected String check(Transaction tx) {
        if (tx.amount > threshold) return "Possible fraud: unknown large transaction.";
        return null;
    }
}

public class MobileBankingApp extends JFrame {
    private JTextField accountField;
    private JTextField amountField;
    private JTextArea resultArea;

    public MobileBankingApp() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}

        setTitle("🏦 Mobile Banking - Transfer Validation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // Top banner
        JPanel banner = new JPanel();
        banner.setBackground(new Color(10, 81, 156));
        JLabel title = new JLabel("🛡️ Secure Transfer Validation");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        banner.add(title);

        // Input panel
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(20, 20, 20, 20));
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Account number
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Account Number:"), gbc);
        accountField = new JTextField(15);
        accountField = createStyledTextField();
        gbc.gridx = 1; form.add(accountField, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Amount (Rs):"), gbc);
        amountField  = new JTextField(15);
        amountField = createStyledTextField();
        gbc.gridx = 1; form.add(amountField, gbc);

        // Submit button
        JButton submitBtn = new JButton("Submit Transfer");
        submitBtn.setBackground(new Color(10, 81, 156));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.addActionListener(this::onSubmit);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        form.add(submitBtn, gbc);

        // Result area
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Result"));

        // Layout
        setLayout(new BorderLayout());
        add(banner, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(220, 30));
        field.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(10, 81, 156)));
        return field;
    }

    private void onSubmit(ActionEvent e) {
        String acct = accountField.getText().trim();
        String amtText = amountField.getText().trim();
        Transaction tx = new Transaction(acct, amtText);

        // Build the chain: Format -> Limit -> Fraud
        Handler format = new FormatValidator();
        Handler limit = new LimitValidator(100_000);
        Handler fraud  = new FraudValidator(50_000);
        format.setNext(limit).setNext(fraud);

        String error = format.handle(tx);
        if (error != null) {
            resultArea.setText("❌ Transfer Rejected: " + error);
        } else {
            resultArea.setText("✅ Transfer Approved! Account: " + acct + ", Amount: Rs. " + tx.amount);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MobileBankingApp().setVisible(true));
    }
}
