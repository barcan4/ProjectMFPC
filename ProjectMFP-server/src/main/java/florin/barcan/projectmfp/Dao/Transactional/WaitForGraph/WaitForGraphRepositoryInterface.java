package florin.barcan.projectmfp.Dao.Transactional.WaitForGraph;

import florin.barcan.projectmfp.Model.Transactional.WaitForGraph.WaitForGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaitForGraphRepositoryInterface extends JpaRepository<WaitForGraph, Long> {

    @Query(value = "select w from WaitForGraph w where w.lockTable=?1 order by w.id")
    List<WaitForGraph> getWaitForGraphsByLockTable(String lockTable);

    @Query(value = "select w from WaitForGraph w where w.id=?1")
    WaitForGraph getById(Long id);
}
