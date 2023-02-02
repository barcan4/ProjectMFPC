package florin.barcan.projectmfp.Dao.Truck;

import florin.barcan.projectmfp.Model.Truck.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TruckRepositoryInterface extends JpaRepository<Truck, Long> {

    @Query(value = "select t from Truck t where t.model=?1 ORDER BY t.id")
    List<Truck> getTrucksByModel(String model);

    @Query(value = "select t from Truck t where t.id=?1")
    Truck getById(Long id);
}
