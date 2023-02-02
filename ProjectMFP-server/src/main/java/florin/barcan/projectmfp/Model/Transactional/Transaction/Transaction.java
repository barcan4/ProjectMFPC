package florin.barcan.projectmfp.Model.Transactional.Transaction;

import jakarta.persistence.*;

@Entity
@Table(schema = "Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String timestamp;
    private String status;

    public Transaction(String timestamp, String status) {
        this.timestamp = timestamp;
        this.status = status;
    }

    public Transaction() {}

    public long getId() { return this.id; }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
