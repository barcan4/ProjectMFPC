package florin.barcan.projectmfp.Controller;

import florin.barcan.projectmfp.Dao.Car.CarService;
import florin.barcan.projectmfp.Dao.Transactional.LockTable.LockTableService;
import florin.barcan.projectmfp.Dao.Transactional.Transaction.TransactionService;
import florin.barcan.projectmfp.Dao.Transactional.WaitForGraph.WaitForGraphService;
import florin.barcan.projectmfp.Dao.Truck.TruckService;
import florin.barcan.projectmfp.Model.Car.Car;
import florin.barcan.projectmfp.Model.Transactional.LockTable.LockTable;
import florin.barcan.projectmfp.Model.Transactional.Transaction.Transaction;
import florin.barcan.projectmfp.Model.Transactional.WaitForGraph.WaitForGraph;
import florin.barcan.projectmfp.Model.Truck.Truck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "transact")
public class TransactController {

    private final CarService carService;
    private final TruckService truckService;

    private final LockTableService lockTableService;
    private final TransactionService transactionService;
    private final WaitForGraphService waitForGraphService;

    @Autowired
    public TransactController(CarService carService, TruckService truckService, LockTableService lockTableService, TransactionService transactionService, WaitForGraphService waitForGraphService) {
        this.carService = carService;
        this.truckService = truckService;
        this.lockTableService = lockTableService;
        this.transactionService = transactionService;
        this.waitForGraphService = waitForGraphService;
    }

    @GetMapping("/get_car")
    public List<Car> getCars() throws Exception {
        // create transaction
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Transaction transaction = new Transaction(format.format(date), "active");
        Long transId = transactionService.createTransaction(transaction);

        // lock resource
        synchronized (this) {
            LockTable lockTable = new LockTable("read", -1, "car", transId);
            Long ltId = lockTableService.createLock(lockTable);

            // create entry in graph
            WaitForGraph waitForGraph = new WaitForGraph(lockTable.getType(), lockTable.getTable(), lockTable.getObjectId(), true, false);
            Long wfgId = waitForGraphService.createWaitForGraph(waitForGraph);

            // check if there is already a lock
            List<WaitForGraph> waitForGraphList = waitForGraphService.getAllWaitForGraphs();
            for (WaitForGraph wfg : waitForGraphList) {
                if (wfg.getLockTable().equals(waitForGraph.getLockTable()) && wfg.isTransHasLock() && wfg.getId() != waitForGraph.getId()) {
                    waitForGraph.setTransHasLock(false);
                    waitForGraph.setTransWaitsLock(true);
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                    wait();
                    waitForGraph.setTransWaitsLock(false);
                    waitForGraph.setTransHasLock(true);
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                }
            }

            // execute read
            List<Car> carList = carService.getAllCars();

            // release lock
            waitForGraph.setTransHasLock(false);
            waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
            transaction.setStatus("committed");
            transactionService.updateTransaction(transId, transaction);

            return carList;
        }
    }

    @GetMapping("/get_truck")
    public List<Truck> getTrucks() throws Exception {
        // create transaction
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Transaction transaction = new Transaction(format.format(date), "active");
        Long transId = transactionService.createTransaction(transaction);

        // lock resource
        synchronized (this) {
            LockTable lockTable = new LockTable("read", -1, "truck", transId);
            Long ltId = lockTableService.createLock(lockTable);

            // create entry in graph
            WaitForGraph waitForGraph = new WaitForGraph(lockTable.getType(), lockTable.getTable(), lockTable.getObjectId(), true, false);
            Long wfgId = waitForGraphService.createWaitForGraph(waitForGraph);

            // check if there is already a lock
            List<WaitForGraph> waitForGraphList = waitForGraphService.getAllWaitForGraphs();
            for (WaitForGraph wfg : waitForGraphList) {
                if (wfg.getLockTable().equals(waitForGraph.getLockTable()) && wfg.isTransHasLock() && wfg.getId() != waitForGraph.getId()) {
                    waitForGraph.setTransHasLock(false);
                    waitForGraph.setTransWaitsLock(true);
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                    wait();
                    waitForGraph.setTransWaitsLock(false);
                    waitForGraph.setTransHasLock(true);
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                }
            }

            // execute read
            List<Truck> truckList = truckService.getAllTrucks();

            // release lock
            waitForGraph.setTransHasLock(false);
            waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
            transaction.setStatus("committed");
            transactionService.updateTransaction(transId, transaction);
            notify();

            return truckList;
        }

    }

    @DeleteMapping("/delete_obj")
    public Long deleteObj(@RequestParam(required = true, name = "idStr") String idStr, @RequestParam(required = true, name = "className") String className) throws Exception {
        if (className.isEmpty()) {
            throw new Exception("Table not found!");
        }

        String resourceClass = "";
        switch (className.toLowerCase()) {
            case "car":
                resourceClass = "car";
                break;
            case "truck":
                resourceClass = "truck";
                break;
            default:
                break;
        }
        if (resourceClass.isEmpty()) {
            throw new Exception("Table not found!");
        }

        // create transaction
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Transaction transaction = new Transaction(format.format(date), "active");
        Long transId = transactionService.createTransaction(transaction);

        synchronized (this) {
            LockTable lockTable = new LockTable("read", Long.parseLong(idStr), resourceClass, transId);
            Long ltId = lockTableService.createLock(lockTable);

            // create entry in graph
            WaitForGraph waitForGraph = new WaitForGraph(lockTable.getType(), lockTable.getTable(), lockTable.getObjectId(), true, false);
            Long wfgId = waitForGraphService.createWaitForGraph(waitForGraph);

            wait(10000);
            // check if there is already a lock
            List<WaitForGraph> waitForGraphList = waitForGraphService.getAllWaitForGraphs();
            for (WaitForGraph wfg : waitForGraphList) {
                if (wfg.getLockTable().equals(waitForGraph.getLockTable()) && wfg.isTransHasLock() && wfg.getId() != waitForGraph.getId()) {
                    System.out.println("Detectat ca este lock");
                    waitForGraph.setTransHasLock(false);
                    waitForGraph.setTransWaitsLock(true);
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                    System.out.println("S-a intrat in wait");
                    wait();
                    waitForGraph.setTransWaitsLock(false);
                    waitForGraph.setTransHasLock(true);
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                }
            }


            if (resourceClass.equals("car")) {
                // execute read
                List<Car> carList = carService.getAllCars();
                if (carList.isEmpty() || carList.stream().noneMatch(car -> car.getId() == Long.parseLong(idStr))) {
                    transaction.setStatus("aborted");
                    transactionService.updateTransaction(transId, transaction);
                    waitForGraph.setTransHasLock(false);
                    waitForGraph.setTransWaitsLock(false);
                    notify();
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                    return -1L;
                }

                //unlock Read
                waitForGraph.setTransHasLock(false);
                waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);

                // lock for write
                LockTable lockTableW = new LockTable("write", Long.parseLong(idStr), resourceClass, transId);
                Long ltWId = lockTableService.createLock(lockTableW);

                // create entry in graph
                WaitForGraph waitForGraphW = new WaitForGraph(lockTableW.getType(), lockTableW.getTable(), lockTableW.getObjectId(), true, false);
                Long wfgWId = waitForGraphService.createWaitForGraph(waitForGraphW);

                wait(5000);
                // check if there is already a lock
                waitForGraphList = waitForGraphService.getAllWaitForGraphs();
                for (WaitForGraph wfg : waitForGraphList) {
                    if (wfg.getLockTable().equals(waitForGraphW.getLockTable()) && wfg.isTransHasLock() && wfg.getId() != waitForGraphW.getId()) {
                        System.out.println("Detectat ca este lock");
                        waitForGraphW.setTransHasLock(false);
                        waitForGraphW.setTransWaitsLock(true);
                        waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
                        System.out.println("S-a intrat in wait");
                        wait();
                        waitForGraphW.setTransWaitsLock(false);
                        waitForGraphW.setTransHasLock(true);
                        waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
                    }
                }

                // execute write
                carService.deleteCar(Long.parseLong(idStr));

                // release lock
                waitForGraphW.setTransHasLock(false);
                waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
            }
            if (resourceClass.equals("truck")){
                // execute read
                List<Truck> truckList = truckService.getAllTrucks();
                if (truckList.isEmpty() || truckList.stream().noneMatch(truck -> truck.getId() == Long.parseLong(idStr))) {
                    transaction.setStatus("aborted");
                    transactionService.updateTransaction(transId, transaction);
                    waitForGraph.setTransHasLock(false);
                    waitForGraph.setTransWaitsLock(false);
                    notify();
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                    return -1L;
                }


                //unlock Read
                waitForGraph.setTransHasLock(false);
                waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);

                // lock for write
                LockTable lockTableW = new LockTable("write", Long.parseLong(idStr), resourceClass, transId);
                Long ltWId = lockTableService.createLock(lockTableW);

                // create entry in graph
                WaitForGraph waitForGraphW = new WaitForGraph(lockTableW.getType(), lockTableW.getTable(), lockTableW.getObjectId(), true, false);
                Long wfgWId = waitForGraphService.createWaitForGraph(waitForGraphW);

                // check if there is already a lock
                waitForGraphList = waitForGraphService.getAllWaitForGraphs();
                for (WaitForGraph wfg : waitForGraphList) {
                    if (wfg.getLockTable().equals(waitForGraphW.getLockTable()) && wfg.isTransHasLock() && wfg.getId() != waitForGraphW.getId()) {
                        System.out.println("Detectat ca este lock");
                        waitForGraphW.setTransHasLock(false);
                        waitForGraphW.setTransWaitsLock(true);
                        waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
                        System.out.println("S-a intrat in wait");
                        wait();
                        waitForGraphW.setTransWaitsLock(false);
                        waitForGraphW.setTransHasLock(true);
                        waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
                    }
                }
                // execute write
                truckService.deleteTruck(Long.parseLong(idStr));

                // release lock
                waitForGraphW.setTransHasLock(false);
                waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
            }

            transaction.setStatus("committed");
            transactionService.updateTransaction(transId, transaction);
            notify();

            return Long.parseLong(idStr);
        }
    }


    @PostMapping("/save_obj")
    public Long createObj(@RequestParam(required = true, name = "className") String className, @RequestParam(required = true, name = "params") List<String> params) throws Exception {
        Long idReturned = -1L;
        String resourceClass = "";
        switch (className.toLowerCase()) {
            case "car":
                resourceClass = "car";
                break;
            case "truck":
                resourceClass = "truck";
                break;
            default:
                break;
        }
        if (resourceClass.isEmpty()) {
            throw new Exception("Table not found!");
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        Transaction transaction = new Transaction(format.format(date), "active");
        Long transId = transactionService.createTransaction(transaction);

        synchronized (this) {
            LockTable lockTable = new LockTable("read", -2, resourceClass, transId);
            Long ltId = lockTableService.createLock(lockTable);

            // create entry in graph
            WaitForGraph waitForGraph = new WaitForGraph(lockTable.getType(), lockTable.getTable(), lockTable.getObjectId(), true, false);
            Long wfgId = waitForGraphService.createWaitForGraph(waitForGraph);

            // check if there is already a lock
            List<WaitForGraph> waitForGraphList = waitForGraphService.getAllWaitForGraphs();
            for (WaitForGraph wfg : waitForGraphList) {
                if (wfg.getLockTable().equals(waitForGraph.getLockTable()) && wfg.isTransHasLock() && wfg.getId() != waitForGraph.getId()) {
                    waitForGraph.setTransHasLock(false);
                    waitForGraph.setTransWaitsLock(true);
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                    wait();
                    waitForGraph.setTransWaitsLock(false);
                    waitForGraph.setTransHasLock(true);
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                }
            }

            if (resourceClass.equals("car")) {
                List<Car> carList = carService.getAllCars();
                if (!carList.isEmpty() && carList.stream().anyMatch(car -> car.getModel().equals(params.get(0)))) {
                    transaction.setStatus("aborted");
                    transactionService.updateTransaction(transId, transaction);
                    waitForGraph.setTransHasLock(false);
                    waitForGraph.setTransWaitsLock(false);
                    notify();
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                    return -1L;
                }

                //unlock Read
                waitForGraph.setTransHasLock(false);
                waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);

                // lock for write
                LockTable lockTableW = new LockTable("write",-2, resourceClass, transId);
                Long ltWId = lockTableService.createLock(lockTableW);

                // create entry in graph
                WaitForGraph waitForGraphW = new WaitForGraph(lockTableW.getType(), lockTableW.getTable(), lockTableW.getObjectId(), true, false);
                Long wfgWId = waitForGraphService.createWaitForGraph(waitForGraphW);

                // check if there is already a lock
                waitForGraphList = waitForGraphService.getAllWaitForGraphs();
                for (WaitForGraph wfg : waitForGraphList) {
                    if (wfg.getLockTable().equals(waitForGraphW.getLockTable()) && wfg.isTransHasLock() && wfg.getId() != waitForGraphW.getId()) {
                        waitForGraphW.setTransHasLock(false);
                        waitForGraphW.setTransWaitsLock(true);
                        waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
                        wait();
                        waitForGraphW.setTransWaitsLock(false);
                        waitForGraphW.setTransHasLock(true);
                        waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
                    }
                }
                // execute write
                idReturned = carService.createCar(new Car(params.get(0), params.get(1)));

                // release lock
                waitForGraphW.setTransHasLock(false);
                waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
            }
            if (resourceClass.equals("truck")) {
                // execute read
                List<Truck> truckList = truckService.getAllTrucks();
                if (!truckList.isEmpty() && truckList.stream().anyMatch(truck -> truck.getModel().equals(params.get(1)))) {
                    transaction.setStatus("aborted");
                    transactionService.updateTransaction(transId, transaction);
                    waitForGraph.setTransHasLock(false);
                    waitForGraph.setTransWaitsLock(false);
                    notify();
                    waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);
                    return -1L;
                }


                //unlock Read
                waitForGraph.setTransHasLock(false);
                waitForGraphService.updateWaitForGraph(wfgId, waitForGraph);

                // lock for write
                LockTable lockTableW = new LockTable("write",-2, resourceClass, transId);
                Long ltWId = lockTableService.createLock(lockTableW);

                // create entry in graph
                WaitForGraph waitForGraphW = new WaitForGraph(lockTableW.getType(), lockTableW.getTable(), lockTableW.getObjectId(), true, false);
                Long wfgWId = waitForGraphService.createWaitForGraph(waitForGraphW);

                // check if there is already a lock
                waitForGraphList = waitForGraphService.getAllWaitForGraphs();
                for (WaitForGraph wfg : waitForGraphList) {
                    if (wfg.getLockTable().equals(waitForGraphW.getLockTable()) && wfg.isTransHasLock() && wfg.getId() != waitForGraphW.getId()) {
                        waitForGraphW.setTransHasLock(false);
                        waitForGraphW.setTransWaitsLock(true);
                        waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
                        wait();
                        waitForGraphW.setTransWaitsLock(false);
                        waitForGraphW.setTransHasLock(true);
                        waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
                    }
                }
                // execute write
                idReturned = truckService.createTruck(new Truck(Integer.parseInt(params.get(0)), params.get(1)));

                // release lock
                waitForGraphW.setTransHasLock(false);
                waitForGraphService.updateWaitForGraph(wfgWId, waitForGraphW);
            }

            transaction.setStatus("committed");
            transactionService.updateTransaction(transId, transaction);
            notify();
            return idReturned;
        }
    }
}
