package florin.barcan.projectmfp.Controller;

import florin.barcan.projectmfp.Dao.Car.CarService;
import florin.barcan.projectmfp.Model.Car.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "car")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/all")
    public List<Car> getAll() {
        return carService.getAllCars();
    }

    @GetMapping("/all/motorFuel")
    public List<Car> getCarsByMotorFuel(@RequestParam(required = true)String motorFuel) throws Exception {
        return carService.getCarsByMotorFuel(motorFuel);
    }

    @GetMapping("/all/model")
    public List<Car> getCarsByModel(@RequestParam(required = true)String model) throws Exception {
        return carService.getCarsByModel(model);
    }

    @GetMapping("/all/id")
    public Car getCarById(@RequestParam(required = true)Long id) throws Exception {
        return carService.getCarById(id);
    }

    @PostMapping("/save_car")
    public Long createCar(Car car) {
        return carService.createCar(car);
    }

    @PutMapping("/update_car")
    public Car updateCar(@RequestParam(required = true)Long id, Car car) throws Exception {
        return carService.updateCar(id, car);
    }

    @DeleteMapping("/delete_car")
    public void deleteCar(@RequestParam(required = true)Long id) {
        carService.deleteCar(id);
    }
}
