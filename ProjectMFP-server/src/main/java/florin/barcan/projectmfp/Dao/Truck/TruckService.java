package florin.barcan.projectmfp.Dao.Truck;

import florin.barcan.projectmfp.Model.Truck.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TruckService {

    private final TruckRepositoryInterface truckRepository;

    @Autowired
    public TruckService(TruckRepositoryInterface truckRepository) {
        this.truckRepository = truckRepository;
    }

    public List<Truck> getTruckByModel(String truckModel) throws Exception {
        List<Truck> truckList = truckRepository.getTrucksByModel(truckModel);
        if (truckList.isEmpty()) {
            return new ArrayList<>();
        }
        return truckList;
    }

    public Truck getTruckById(Long id) throws Exception {
        Truck truckObj = truckRepository.getById(id);
        if (truckObj == null) {
            throw new Exception("Truck not found by ID");
        }
        return truckObj;
    }

    public Long createTruck(Truck truckDTO) {
        return truckRepository.save(truckDTO).getId();
    }

    public List<Truck> getAllTrucks() {
        return new ArrayList<>(truckRepository.findAll());
    }

    public Truck updateTruck(Long id, Truck truckDTO) throws Exception {
        Optional<Truck> truckOptional = truckRepository.findById(id);
        if (truckOptional.isEmpty()) {
            throw new Exception("Truck not found with ID " + id);
        }

        Truck truckObj = truckOptional.get();
        truckObj.setModel(truckDTO.getModel());
        truckObj.setTonnage(truckDTO.getTonnage());

        return truckRepository.save(truckObj);
    }

    public void deleteTruck(Long id) {
        truckRepository.deleteById(id);
    }
}
