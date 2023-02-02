package florin.barcan.projectmfp.Model.Car;

import jakarta.persistence.*;

@Entity
@Table(schema = "Car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String model;
    private String motorFuel;


    public Car(String model, String motorFuel) {
        this.model = model;
        this.motorFuel = motorFuel;
    }

    public Car() {}

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMotorFuel() {
        return motorFuel;
    }

    public void setMotorFuel(String motorFuel) {
        this.motorFuel = motorFuel;
    }

    public long getId() {
        return id;
    }
}
