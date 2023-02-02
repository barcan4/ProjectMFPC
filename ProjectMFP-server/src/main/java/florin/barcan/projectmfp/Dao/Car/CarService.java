package florin.barcan.projectmfp.Dao.Car;

import florin.barcan.projectmfp.Model.Car.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepositoryInterface carRepository;

    @Autowired
    public CarService(CarRepositoryInterface carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCarsByMotorFuel(String motorFuel) throws Exception {
        List<Car> carList = carRepository.getCarsByMotorFuel(motorFuel);
        if (carList.isEmpty()) {
            return new ArrayList<>();
        }
        return carList;
    }

    public List<Car> getCarsByModel(String model) throws Exception {
        List<Car> carList = carRepository.getCarsByModel(model);
        if (carList.isEmpty()) {
            return new ArrayList<>();
        }
        return carList;
    }

    public Car getCarById(Long id) throws Exception {
        Car carObject = carRepository.getById(id);
        if (carObject == null) {
            throw new Exception("Car not found!");
        }
        return carObject;
    }

    public Long createCar(Car carDTO) {
        return carRepository.save(carDTO).getId();
    }

    public List<Car> getAllCars() {
        return new ArrayList<>(carRepository.findAll());
    }

    public Car updateCar(Long id, Car carDTO) throws Exception {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isEmpty()) {
            throw new Exception("Car not found with ID " + id);
        }

        Car carObj = carOptional.get();
        carObj.setModel(carDTO.getModel());
        carObj.setMotorFuel(carDTO.getMotorFuel());

        return carRepository.save(carObj);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
