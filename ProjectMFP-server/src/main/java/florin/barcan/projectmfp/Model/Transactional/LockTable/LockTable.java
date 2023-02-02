package florin.barcan.projectmfp.Model.Transactional.LockTable;

import jakarta.persistence.*;

@Entity
@Table(schema = "LockTable")
public class LockTable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String typeRW;
    private long objectId;
    private String tableName;
    private long transactionId;

    public LockTable(String type, long objectId, String table, long transactionId) {
        this.typeRW = type;
        this.objectId = objectId;
        this.tableName = table;
        this.transactionId = transactionId;
    }

    public LockTable() {}

    public long getId() {
        return id;
    }

    public String getType() {
        return typeRW;
    }

    public void setType(String type) {
        this.typeRW = type;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getTable() {
        return tableName;
    }

    public void setTable(String table) {
        this.tableName = table;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }
}
