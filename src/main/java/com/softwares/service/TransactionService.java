package com.softwares.service;



import com.softwares.models.Order;
import com.softwares.models.Seller;
import com.softwares.models.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionBySeller(Seller seller);
    List<Transaction>getAllTransactions();
}
