/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
/*
 * VIP Priority Transaction Request
 * Stores transaction details for queue processing
 */

public class TransactionRequest {
    String type;      // "deposit", "withdraw", "transfer"
    String fromAcc;   // Sender account number
    String toAcc;     // Receiver account number (for transfer)
    double amount;    // Transaction amount
    boolean isVIP;    // VIP priority flag
    long timestamp;   // Request arrival time
    
    // Constructor for Deposit & Withdraw
    public TransactionRequest(String type, String accNo, double amount, boolean isVIP) {
        this.type = type;
        this.fromAcc = accNo;
        this.amount = amount;
        this.isVIP = isVIP;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Constructor for Transfer
    public TransactionRequest(String type, String fromAcc, String toAcc, double amount, boolean isVIP) {
        this.type = type;
        this.fromAcc = fromAcc;
        this.toAcc = toAcc;
        this.amount = amount;
        this.isVIP = isVIP;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getType() { return type; }
    public String getFromAcc() { return fromAcc; }
    public String getToAcc() { return toAcc; }
    public double getAmount() { return amount; }
    public boolean isVIP() { return isVIP; }
    public long getTimestamp() { return timestamp; }
}
