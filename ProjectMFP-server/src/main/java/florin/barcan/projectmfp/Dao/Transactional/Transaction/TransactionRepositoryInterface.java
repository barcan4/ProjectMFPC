package florin.barcan.projectmfp.Dao.Transactional.Transaction;

import florin.barcan.projectmfp.Model.Transactional.Transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepositoryInterface extends JpaRepository<Transaction, Long> {

    @Query(value = "select t from Transaction t where t.status=?1 order by t.id")
    List<Transaction> getTransactionsByStatus(String status);

    @Query(value = "select t from Transaction t where t.id=?1")
    Transaction getById(Long id);
}
