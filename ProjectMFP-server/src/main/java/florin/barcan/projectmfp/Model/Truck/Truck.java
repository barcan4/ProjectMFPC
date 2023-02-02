package florin.barcan.projectmfp.Model.Truck;

import jakarta.persistence.*;

@Entity
@Table(schema = "Truck")
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int tonnage;
    private String model;

    public Truck(int tonnage, String model) {
        this.tonnage = tonnage;
        this.model = model;
    }

    public Truck() {}

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTonnage() {
        return tonnage;
    }

    public void setTonnage(int tonnage) {
        this.tonnage = tonnage;
    }

    public long getId() {
        return id;
    }
}
