package florin.barcan.projectmfp.Model.Transactional.WaitForGraph;

import jakarta.persistence.*;

@Entity
@Table(schema = "WaitForGraph")
public class WaitForGraph {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String lockType;
    private String lockTable;
    private long lockObjectId;
    private boolean transHasLock;
    private boolean transWaitsLock;

    public WaitForGraph(String lockType, String lockTable, long lockObjectId, boolean transHasLock, boolean transWaitsLock) {
        this.lockType = lockType;
        this.lockTable = lockTable;
        this.lockObjectId = lockObjectId;
        this.transHasLock = transHasLock;
        this.transWaitsLock = transWaitsLock;
    }

    public WaitForGraph() {}

    public String getLockType() {
        return lockType;
    }

    public void setLockType(String lockType) {
        this.lockType = lockType;
    }

    public String getLockTable() {
        return lockTable;
    }

    public void setLockTable(String lockTable) {
        this.lockTable = lockTable;
    }

    public long getLockObjectId() {
        return lockObjectId;
    }

    public void setLockObjectId(long lockObjectId) {
        this.lockObjectId = lockObjectId;
    }

    public boolean isTransHasLock() {
        return transHasLock;
    }

    public void setTransHasLock(boolean transHasLock) {
        this.transHasLock = transHasLock;
    }

    public boolean isTransWaitsLock() {
        return transWaitsLock;
    }

    public void setTransWaitsLock(boolean transWaitsLock) {
        this.transWaitsLock = transWaitsLock;
    }

    public long getId() {
        return id;
    }
}
