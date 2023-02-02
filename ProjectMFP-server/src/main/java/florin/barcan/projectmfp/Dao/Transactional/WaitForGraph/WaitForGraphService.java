package florin.barcan.projectmfp.Dao.Transactional.WaitForGraph;

import florin.barcan.projectmfp.Model.Transactional.WaitForGraph.WaitForGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WaitForGraphService {

    private final WaitForGraphRepositoryInterface waitForGraphRepository;

    @Autowired
    public WaitForGraphService(WaitForGraphRepositoryInterface waitForGraphRepository) {
        this.waitForGraphRepository = waitForGraphRepository;
    }

    public List<WaitForGraph> getWaitForGraphsByLockTable(String lockTable) {
        List<WaitForGraph> waitForGraphList = waitForGraphRepository.getWaitForGraphsByLockTable(lockTable);
        if (waitForGraphList.isEmpty()) {
            return new ArrayList<>();
        }
        return waitForGraphList;
    }

    public WaitForGraph getWaitForGraphById(Long id) throws Exception {
        WaitForGraph waitForGraphObj = waitForGraphRepository.getById(id);
        if (waitForGraphObj == null) {
            throw new Exception("WaitForGraph not found!");
        }
        return waitForGraphObj;
    }

    public Long createWaitForGraph(WaitForGraph waitForGraphDTO) {
        return waitForGraphRepository.save(waitForGraphDTO).getId();
    }

    public List<WaitForGraph> getAllWaitForGraphs() {
        return new ArrayList<>(waitForGraphRepository.findAll());
    }

    public WaitForGraph updateWaitForGraph(Long id, WaitForGraph waitForGraphDTO) throws Exception {
        Optional<WaitForGraph> waitForGraphOptional = waitForGraphRepository.findById(id);
        if (waitForGraphOptional.isEmpty()) {
            throw new Exception("WaitForGraph not found with ID " + id);
        }

        WaitForGraph waitForGraphObj = waitForGraphOptional.get();
        waitForGraphObj.setLockObjectId(waitForGraphDTO.getLockObjectId());
        waitForGraphObj.setLockTable(waitForGraphDTO.getLockTable());
        waitForGraphObj.setLockType(waitForGraphDTO.getLockType());
        waitForGraphObj.setTransHasLock(waitForGraphDTO.isTransHasLock());
        waitForGraphDTO.setTransWaitsLock(waitForGraphDTO.isTransWaitsLock());

        return waitForGraphRepository.save(waitForGraphObj);
    }

    public void deleteWaitForGraph(Long id) {
        waitForGraphRepository.deleteById(id);
    }
}
