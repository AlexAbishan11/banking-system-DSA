/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
public class Transaction {
    String type;
    String accNo;
    String fromAcc;
    String toAcc;
    double amount;
    boolean isVIP;

    public Transaction(String type, String accNo, double amount) {
        this.type = type;
        this.accNo = accNo;
        this.amount = amount;
        this.isVIP = false;
    }

    public Transaction(String type, String fromAcc, String toAcc, double amount, boolean isVIP) {
        this.type = type;
        this.fromAcc = fromAcc;
        this.toAcc = toAcc;
        this.amount = amount;
        this.isVIP = isVIP;
    }
}