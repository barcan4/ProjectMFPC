package florin.barcan.projectmfp.Dao.Transactional.LockTable;

import florin.barcan.projectmfp.Model.Transactional.LockTable.LockTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LockTableRepositoryInterface extends JpaRepository<LockTable, Long> {

    @Query(value = "select l from LockTable l where l.typeRW=?1 order by l.id")
    List<LockTable> getLocksByType(String type);

    @Query(value = "select l from LockTable l where l.id=?1")
    LockTable getById(Long id);
}
