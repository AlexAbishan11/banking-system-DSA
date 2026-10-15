import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionUI extends javax.swing.JFrame {

    BankSystem bank;
    private Timer refreshTimer;

    public TransactionUI(BankSystem system) {
        this.bank = system;
        initComponents();
        
        updateQueueDisplay();
        loadTransactionHistory();
        
        // Auto-refresh every 2 seconds
        refreshTimer = new Timer(2000, e -> {
            updateQueueDisplay();
            loadTransactionHistory();
        });
        refreshTimer.start();
    }
    
    private void loadTransactionHistory() {
        txtAreaLog.setText("");
        
        String[] history = bank.getTransactionHistory();
        
        if(history.length == 0) {
            
            txtAreaLog.append("NO TRANSACTIONS YET\n");
            
            
            
        } else {
           
            txtAreaLog.append("TRANSACTION HISTORY (Current Session)\n");
            
            
            for(String record : history) {
                txtAreaLog.append(record + "\n");
            }
            
            
            txtAreaLog.append("Total: " + history.length + " transaction(s)\n");
           
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtFromAcc = new javax.swing.JTextField();
        txtToAcc = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaLog = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        btnTransfer = new javax.swing.JButton();
        btnUndoTransfer = new javax.swing.JButton();
        chkVIP = new javax.swing.JCheckBox();
        btnProcessQueue = new javax.swing.JButton();
        btnProcessAll = new javax.swing.JButton();
        btnRefreshQueue = new javax.swing.JButton();
        txtQueueDisplay = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bank Transaction System - Transfer Money");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("From Account");
        jLabel2.setText("Amount");
        jLabel3.setText("To Account");
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel4.setText("Money Transfer");

        txtAreaLog.setEditable(false);
        txtAreaLog.setColumns(20);
        txtAreaLog.setRows(5);
        jScrollPane1.setViewportView(txtAreaLog);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel5.setText("Transfer History");

        btnTransfer.setText("Add to Queue");
        btnTransfer.addActionListener(evt -> btnTransferActionPerformed(evt));

        btnUndoTransfer.setText("Undo");
        btnUndoTransfer.addActionListener(evt -> btnUndoTransferActionPerformed(evt));

        chkVIP.setText("VIP Priority");

        btnProcessQueue.setText("Process Next");
        btnProcessQueue.addActionListener(evt -> btnProcessQueueActionPerformed(evt));

        btnProcessAll.setText("Process All");
        btnProcessAll.addActionListener(evt -> btnProcessAllActionPerformed(evt));

        btnRefreshQueue.setText("Refresh Queue");
        btnRefreshQueue.addActionListener(evt -> updateQueueDisplay());

        btnBack.setText("Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        
        txtQueueDisplay.setEditable(false);
        txtQueueDisplay.setRows(8);
        txtQueueDisplay.setColumns(30);
        txtQueueDisplay.setBackground(new java.awt.Color(255, 255, 200));

        // Layout
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFromAcc, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(txtToAcc)
                            .addComponent(txtAmount))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnTransfer, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(btnUndoTransfer, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(chkVIP, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnProcessQueue, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(btnProcessAll, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(btnRefreshQueue, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(btnBack, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(txtQueueDisplay, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFromAcc, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTransfer))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtToAcc, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUndoTransfer))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkVIP))
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnProcessQueue)
                        .addGap(10, 10, 10)
                        .addComponent(btnProcessAll)
                        .addGap(10, 10, 10)
                        .addComponent(btnRefreshQueue)
                        .addGap(30, 30, 30)
                        .addComponent(btnBack)))
                .addGap(10, 10, 10)
                .addComponent(txtQueueDisplay, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void btnTransferActionPerformed(java.awt.event.ActionEvent evt) {
        String from = txtFromAcc.getText().trim();
        String to = txtToAcc.getText().trim();

        if (from.isEmpty() || to.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (from.equals(to)) {
            JOptionPane.showMessageDialog(this, "Sender and Receiver cannot be same", "Transfer Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(txtAmount.getText().trim());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than 0", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean vip = chkVIP.isSelected();
        String res = bank.transfer(from, to, amount, vip);
        
        if (res.contains("QUEUE") || res.contains("Success")) {
            JOptionPane.showMessageDialog(this, res, "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, res, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        updateQueueDisplay();
        loadTransactionHistory();
        
        txtFromAcc.setText("");
        txtToAcc.setText("");
        txtAmount.setText("");
        chkVIP.setSelected(false);
    }

    private void btnProcessQueueActionPerformed(java.awt.event.ActionEvent evt) {
        if(bank.getQueueSize() == 0) {
            JOptionPane.showMessageDialog(this, "Queue is empty! No transactions to process.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String result = bank.processNextTransaction();
        JOptionPane.showMessageDialog(this, result, "Processed", JOptionPane.INFORMATION_MESSAGE);
        updateQueueDisplay();
        loadTransactionHistory();
    }

    private void btnProcessAllActionPerformed(java.awt.event.ActionEvent evt) {
        if(bank.getQueueSize() == 0) {
            JOptionPane.showMessageDialog(this, "Queue is empty! No transactions to process.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String result = bank.processAllTransactions();
        JOptionPane.showMessageDialog(this, result, "Processed All", JOptionPane.INFORMATION_MESSAGE);
        updateQueueDisplay();
        loadTransactionHistory();
    }

    private void btnUndoTransferActionPerformed(java.awt.event.ActionEvent evt) {
        String res = bank.undoLastTransaction();
        if(res.contains("No Transaction")) {
            JOptionPane.showMessageDialog(this, res, "Info", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, res, "Undo Successful", JOptionPane.INFORMATION_MESSAGE);
        }
        updateQueueDisplay();
        loadTransactionHistory();
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        if(refreshTimer != null) {
            refreshTimer.stop();
        }
        new MainMenuUI().setVisible(true);
        this.dispose();
    }
    
    private void updateQueueDisplay() {
        txtQueueDisplay.setText(bank.viewQueue());
    }
    
    @Override
    public void dispose() {
        if(refreshTimer != null) {
            refreshTimer.stop();
        }
        super.dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TransactionUI(BankSystem.getInstance()).setVisible(true);
        });
    }

    // Variables declaration
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnTransfer;
    private javax.swing.JButton btnUndoTransfer;
    private javax.swing.JButton btnProcessQueue;
    private javax.swing.JButton btnProcessAll;
    private javax.swing.JButton btnRefreshQueue;
    private javax.swing.JCheckBox chkVIP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextArea txtAreaLog;
    private javax.swing.JTextField txtFromAcc;
    private javax.swing.JTextField txtToAcc;
    private javax.swing.JTextArea txtQueueDisplay;
}