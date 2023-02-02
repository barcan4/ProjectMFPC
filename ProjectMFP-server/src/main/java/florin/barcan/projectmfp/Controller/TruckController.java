package florin.barcan.projectmfp.Controller;

import florin.barcan.projectmfp.Dao.Truck.TruckService;
import florin.barcan.projectmfp.Model.Truck.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "truck")
public class TruckController {

    private final TruckService truckService;

    @Autowired
    public TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @GetMapping("/all")
    public List<Truck> getAll() {
        return truckService.getAllTrucks();
    }

    @GetMapping("/all/model")
    public List<Truck> getTruckByModel(@RequestParam(required = true)String model) throws Exception {
        return truckService.getTruckByModel(model);
    }

    @GetMapping("/all/id")
    public Truck getTruckById(@RequestParam(required = true)Long id) throws Exception {
        return truckService.getTruckById(id);
    }

    @PostMapping("/save_truck")
    public Long createTruck(Truck truck) {
        return truckService.createTruck(truck);
    }

    @PutMapping("/update_truck")
    public Truck updateTruck(@RequestParam(required = true)Long id, Truck truck) throws Exception {
        return truckService.updateTruck(id, truck);
    }

    @DeleteMapping("/delete_truck")
    public void deleteTruck(@RequestParam(required = true)Long id) {
        truckService.deleteTruck(id);
    }
}
