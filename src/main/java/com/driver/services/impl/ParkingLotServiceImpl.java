package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {

        ParkingLot parkingLot = new ParkingLot();

        parkingLot.setName(name);
        parkingLot.setAddress(address);

        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        //create a new spot in the parkingLot with given id
        //the spot type should be the next biggest type in case the number of wheels are not 2 or 4, for 4+ wheels, it is others
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();

        Spot spot = new Spot();

        spot.setPricePerHour(pricePerHour);

        if(numberOfWheels<=2) spot.setSpotType(SpotType.TWO_WHEELER);

        else if(numberOfWheels<=4) spot.setSpotType(SpotType.FOUR_WHEELER);

        else
            spot.setSpotType(SpotType.OTHERS);

        spot.setOccupied(false);

        spot.setParkingLot(parkingLot);

        parkingLot.getSpotList().add(spot);

        parkingLotRepository1.save(parkingLot);

        return spot;

    }

    @Override
    public void deleteSpot(int spotId) {

        //delete a spot from given parking lot
        Spot spot = spotRepository1.findById(spotId).get();

        spot.getParkingLot().getSpotList().remove(spot);

        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot = spotRepository1.findById(spotId).get();
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();

        spot.setPricePerHour(pricePerHour);

        parkingLotRepository1.save(parkingLot);

        return spot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {

        parkingLotRepository1.deleteById(parkingLotId);
    }
}
