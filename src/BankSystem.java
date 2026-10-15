import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

public class BankSystem {

    private ArrayList<Account> accList = new ArrayList<>();

    private Stack<List<Account>> history = new Stack<>();
    private Queue<String> historyQueue = new LinkedList<>();
    private Stack<Transaction> undoStack = new Stack<>();
    
    // ⭐ NEW: Priority Queue for VIP Priority System
    private PriorityQueue<TransactionRequest> transactionQueue = new PriorityQueue<>(
        new Comparator<TransactionRequest>() {
            @Override
            public int compare(TransactionRequest r1, TransactionRequest r2) {
                // VIP always comes FIRST (higher priority)
                if (r1.isVIP() && !r2.isVIP()) {
                    return -1;  // r1 is VIP → comes before r2
                }
                if (!r1.isVIP() && r2.isVIP()) {
                    return 1;   // r2 is VIP → comes before r1
                }
                // Both same priority - Last Come First Serve (LCFS)
                // Newer request (higher timestamp) comes first
                return Long.compare(r2.getTimestamp(), r1.getTimestamp());
            }
        }
    );

    private static BankSystem instance;

    public static BankSystem getInstance() {
        if (instance == null) {
            instance = new BankSystem();
        }
        return instance;
    }

    
    public String generateNextAccountNo() {
        return String.format("%03d", accList.size() + 1);
    }

    
    private List<Account> copyAccounts() {
        List<Account> copy = new ArrayList<>();
        for (Account a : accList) {
            copy.add(new Account(
                a.accNo,
                a.name,
                a.nic,
                a.phone,
                a.balance,
                a.active
            ));
        }
        return copy;
    }

    private void saveState() {
        history.push(copyAccounts());
    }

    
    public String createAccount(String acc, String name, String nic, String phone, double balance) {
        if (acc.isEmpty() || name.isEmpty() || nic.isEmpty() || phone.isEmpty())
            return "Fill all fields";
        if (balance < 0)
            return "Invalid Initial Balance";
        for (Account a : accList) {
            if (a.accNo.equals(acc))
                return "Account Already Exists";
        }
        saveState();
        accList.add(new Account(acc, name, nic, phone, balance));
        historyQueue.add("Account Created | " + acc);
        return "Account Created Successfully";
    }

    
    public Account search(String accNo) {
        for (Account a : accList) {
            if (a.accNo.equals(accNo))
                return a;
        }
        return null;
    }

    
    public String updateAccount(String accNo, String name, String nic, String phone) {
        Account acc = search(accNo);
        if (acc == null)
            return "Account Not Found";
        if (isNICExists(nic, accNo))
            return "NIC Already Exists";
        saveState();
        acc.name = name;
        acc.nic = nic;
        acc.phone = phone;
        historyQueue.add("Account Updated | " + accNo);
        return "Account Updated Successfully";
    }

   
    public String deposit(String accNo, double amount, boolean isVIP) {
        if (amount <= 0) return "Invalid Amount";
        
        Account a = search(accNo);
        if (a == null) return "Account Not Found";
        if (!a.active) return "Account is DEACTIVATED";
        
        // ⭐ Add to priority queue instead of immediate processing
        TransactionRequest request = new TransactionRequest("deposit", accNo, amount, isVIP);
        transactionQueue.add(request);
        
        String priority = isVIP ? "VIP PRIORITY" : "NORMAL";
        return "Deposit request added to QUEUE (" + priority + "). Queue size: " + transactionQueue.size();
    }

    
    public String withdraw(String accNo, double amount, boolean isVIP) {
        if (amount <= 0) return "Invalid Amount";
        
        Account a = search(accNo);
        if (a == null) return "Account Not Found";
        if (!a.active) return "Account is DEACTIVATED";
        if (a.balance < amount) return "Insufficient Balance";
        
        // ⭐ Add to priority queue instead of immediate processing
        TransactionRequest request = new TransactionRequest("withdraw", accNo, amount, isVIP);
        transactionQueue.add(request);
        
        String priority = isVIP ? "VIP PRIORITY" : "NORMAL";
        return "Withdraw request added to QUEUE (" + priority + "). Queue size: " + transactionQueue.size();
    }

   
   public String transfer(String fromAcc, String toAcc, double amount, boolean isVIP) {
    // ... existing validation code ...
    
    // Add to queue
    TransactionRequest request = new TransactionRequest("transfer", fromAcc, toAcc, amount, isVIP);
    transactionQueue.add(request);
    
    // ⭐ ADD TO DISPLAY HISTORY
    String historyRecord = String.format("[%s] %s TRANSFER: Rs.%.2f from %s to %s (Queue Size: %d)",
        new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()),
        isVIP ? "VIP" : "NORMAL",
        amount, fromAcc, toAcc,
        transactionQueue.size()
    );
    addToTransactionHistory(historyRecord);
    
    return "Transfer request added to QUEUE (" + (isVIP ? "VIP" : "NORMAL") + ")";
}

   
    public String processNextTransaction() {
        if (transactionQueue.isEmpty()) {
            return "No pending transactions in queue";
        }
        
        TransactionRequest req = transactionQueue.poll();
        
        if (req.getType().equals("deposit")) {
            return executeDeposit(req.getFromAcc(), req.getAmount());
        } else if (req.getType().equals("withdraw")) {
            return executeWithdraw(req.getFromAcc(), req.getAmount());
        } else if (req.getType().equals("transfer")) {
            return executeTransfer(req.getFromAcc(), req.getToAcc(), req.getAmount());
        }
        
        return "Unknown transaction type";
    }
    
    
    private String executeDeposit(String accNo, double amount) {
        Account a = search(accNo);
        if (a == null) return "Account Not Found";
        
        saveState();
        undoStack.push(new Transaction("withdraw", accNo, amount));
        a.balance += amount;
        historyQueue.add("Deposit | " + accNo + " | " + amount);
        return "DEPOSIT SUCCESSFUL: Rs." + amount + " to Account " + accNo;
    }
    
    
    private String executeWithdraw(String accNo, double amount) {
        Account a = search(accNo);
        if (a == null) return "Account Not Found";
        if (a.balance < amount) return "Insufficient Balance";
        
        saveState();
        undoStack.push(new Transaction("deposit", accNo, amount));
        a.balance -= amount;
        historyQueue.add("Withdraw | " + accNo + " | " + amount);
        return "WITHDRAW SUCCESSFUL: Rs." + amount + " from Account " + accNo;
    }
    
   
    private String executeTransfer(String fromAcc, String toAcc, double amount) {
        Account from = search(fromAcc);
        Account to = search(toAcc);
        
        if (from == null) return "From Account Not Found";
        if (to == null) return "To Account Not Found";
        if (from.balance < amount) return "Insufficient Balance";
        
        saveState();
        undoStack.push(new Transaction("transfer", fromAcc, toAcc, amount, false));
        
        from.balance -= amount;
        to.balance += amount;
        
        historyQueue.add("Transfer | " + amount + " from " + fromAcc + " to " + toAcc);
        return "TRANSFER SUCCESSFUL: Rs." + amount + " from " + fromAcc + " to " + toAcc;
    }

    // ===================== VIEW CURRENT QUEUE =====================
    public String viewQueue() {
        if (transactionQueue.isEmpty()) {
            return "QUEUE IS EMPTY";
        }
        
        StringBuilder sb = new StringBuilder();
                sb.append("PENDING TRANSACTIONS QUEUE\n");
        
        
        int position = 1;
        for (TransactionRequest req : transactionQueue) {
            sb.append(position++).append(". ");
            
            if (req.isVIP()) {
                sb.append("⭐ VIP ");
            } else {
                sb.append("   NORMAL ");
            }
            
            sb.append(req.getType().toUpperCase()).append(": ");
            sb.append(req.getFromAcc());
            
            if (req.getType().equals("transfer")) {
                sb.append(" → ").append(req.getToAcc());
            }
            
            sb.append(" | Rs.").append(req.getAmount());
            sb.append("\n");
        }
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("Total: ").append(transactionQueue.size()).append(" transaction(s)\n");
        
        return sb.toString();
    }
    
    
    public int getQueueSize() {
        return transactionQueue.size();
    }
    
    
    public String processAllTransactions() {
        if (transactionQueue.isEmpty()) {
            return "No pending transactions";
        }
        
        StringBuilder results = new StringBuilder();
        int count = 0;
        
        while (!transactionQueue.isEmpty()) {
            String result = processNextTransaction();
            results.append(result).append("\n");
            count++;
        }
        
        return "Processed " + count + " transaction(s):\n" + results.toString();
    }

    
    public String toggleAccountStatus(String accNo) {
        Account a = search(accNo);
        if (a == null) return "Account Not Found";
        saveState();
        a.active = !a.active;
        String status = a.active ? "ACTIVATED" : "DEACTIVATED";
        historyQueue.add("Account " + accNo + " " + status);
        return status;
    }

    
    public ArrayList<Account> getAllAccounts() {
        return accList;
    }

    
    public Queue<String> getHistory() {
        return historyQueue;
    }

   
    public boolean isNICExists(String nic, String currentAccNo) {
        for (Account a : accList) {
            if (a.nic.equals(nic) && !a.accNo.equals(currentAccNo)) {
                return true;
            }
        }
        return false;
    }

   
    public String undo() {
        if (history.isEmpty())
            return "Nothing to Undo";
        accList = new ArrayList<>(history.pop());
        return "Undo Successful";
    }
    
    
    public String undoLastTransaction() {
        if (undoStack.isEmpty()) return "No Transaction to Undo";
        
        Transaction t = undoStack.pop();
        
        if (t.type.equals("deposit")) {
            Account acc = search(t.accNo);
            if (acc != null) {
                acc.balance -= t.amount;
                return "Deposit Undo Successful";
            }
        } else if (t.type.equals("withdraw")) {
            Account acc = search(t.accNo);
            if (acc != null) {
                acc.balance += t.amount;
                return "Withdraw Undo Successful";
            }
        } else if (t.type.equals("transfer")) {
            Account from = search(t.fromAcc);
            Account to = search(t.toAcc);
            if (from != null && to != null) {
                from.balance += t.amount;
                to.balance -= t.amount;
                return "Transfer Undo Successful";
            }
        }
        return "Undo Failed";
    }
    // Add to BankSystem class - NEW Queue for transaction history display
private Queue<String> transactionDisplayHistory = new LinkedList<>();

// Add this method to add to history
public void addToTransactionHistory(String record) {
    transactionDisplayHistory.add(record);
    // Optional: Limit size to last 100 transactions
    if(transactionDisplayHistory.size() > 100) {
        transactionDisplayHistory.poll();
    }
}

// Get history as array for display
public String[] getTransactionHistory() {
    return transactionDisplayHistory.toArray(new String[0]);
}

// Clear history (if needed)
public void clearTransactionHistory() {
    transactionDisplayHistory.clear();
}
}