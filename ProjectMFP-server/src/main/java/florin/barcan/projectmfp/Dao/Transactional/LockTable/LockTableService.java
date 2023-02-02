package florin.barcan.projectmfp.Dao.Transactional.LockTable;

import florin.barcan.projectmfp.Model.Transactional.LockTable.LockTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LockTableService {

    private final LockTableRepositoryInterface lockRepository;

    @Autowired
    public LockTableService(LockTableRepositoryInterface lockRepository) {
        this.lockRepository = lockRepository;
    }

    public List<LockTable> getLocksByType(String type) throws Exception{
        List<LockTable> lockList = lockRepository.getLocksByType(type);
        if (lockList.isEmpty()) {
            return new ArrayList<>();
        }
        return lockList;
    }

    public LockTable getLockById(Long id) throws Exception {
        LockTable lockObject = lockRepository.getById(id);
        if (lockObject == null) {
            throw new Exception("Lock not found!");
        }
        return lockObject;
    }

    public Long createLock(LockTable lockDTO) {
        return lockRepository.save(lockDTO).getId();
    }

    public List<LockTable> getAllLocks() {
        return new ArrayList<>(lockRepository.findAll());
    }

    public LockTable updateLock(Long id, LockTable lockDTO) throws Exception {
        Optional<LockTable> lockOptional = lockRepository.findById(id);
        if (lockOptional.isEmpty()) {
            throw new Exception("Lock not found with ID " + id);
        }

        LockTable lockObj = lockOptional.get();
        lockObj.setObjectId(lockDTO.getObjectId());
        lockObj.setTable(lockDTO.getTable());
        lockObj.setTransactionId(lockDTO.getTransactionId());
        lockObj.setType(lockDTO.getType());

        return lockRepository.save(lockObj);
    }

    public void deleteLock(Long id) {
        lockRepository.deleteById(id);
    }
}
