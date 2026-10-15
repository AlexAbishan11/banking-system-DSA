/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
public class Account {
    String accNo;
    String name;
    String nic;
    String phone;
    double balance;
    boolean active;

    public Account(String accNo, String name, String nic, String phone, double balance) {
        this.accNo = accNo;
        this.name = name;
        this.nic = nic;
        this.phone = phone;
        this.balance = balance;
        this.active = true;
    }

    public Account(String accNo, String name, String nic, String phone, double balance, boolean active) {
        this.accNo = accNo;
        this.name = name;
        this.nic = nic;
        this.phone = phone;
        this.balance = balance;
        this.active = active;
    }
}