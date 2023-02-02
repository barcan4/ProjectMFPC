package florin.barcan.projectmfp.Dao.Transactional.Transaction;

import florin.barcan.projectmfp.Model.Transactional.Transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepositoryInterface transactionRepository;

    @Autowired
    public TransactionService(TransactionRepositoryInterface transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionsByStatus(String status) {
        List<Transaction> transactionList = transactionRepository.getTransactionsByStatus(status);
        if (transactionList.isEmpty()) {
            return new ArrayList<>();
        }
        return transactionList;
    }

    public Transaction getTransactionById(Long id) throws Exception{
        Transaction transactionObject = transactionRepository.getById(id);
        if (transactionObject == null) {
            throw new Exception("Transaction not found!");
        }
        return transactionObject;
    }

    public Long createTransaction(Transaction transactionDTO) {
        return transactionRepository.save(transactionDTO).getId();
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactionRepository.findAll());
    }

    public Transaction updateTransaction(Long id, Transaction transactionDTO) throws Exception {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) {
            throw new Exception("Transaction not found with ID " + id);
        }

        Transaction transactionObj = transactionOptional.get();
        transactionObj.setStatus(transactionDTO.getStatus());
        transactionObj.setTimestamp(transactionDTO.getTimestamp());

        return transactionRepository.save(transactionObj);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
