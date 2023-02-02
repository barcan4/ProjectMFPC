package florin.barcan.projectmfp.Dao.Car;

import florin.barcan.projectmfp.Model.Car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepositoryInterface extends JpaRepository<Car, Long> {

    @Query(value = "select c FROM Car c where c.motorFuel=?1 order by c.id")
    List<Car> getCarsByMotorFuel(String motorFuel);

    @Query(value = "select c FROM Car c where c.model=?1 order by c.id")
    List<Car> getCarsByModel(String model);

    @Query(value = "select c from Car c where c.id=?1")
    Car getById(Long id);
}
