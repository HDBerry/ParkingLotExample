package com;

import static com.parkinglot.ParkingLot.ParkingSpot.CAR;
import static com.parkinglot.ParkingLot.ParkingSpot.MOTORCYCLE;
import static com.parkinglot.ParkingLot.ParkingSpot.VAN;

import com.parkinglot.ParkingLot;
import com.vehicle.Vehicle.Car;
import com.vehicle.Vehicle.MotorCycle;
import com.vehicle.Vehicle.Van;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Driver class for Parking Lot Tests
 */
public class ParkingLotTests {

  /**
   * Test all basic lot info methods work as intended
   */
  @Test
  public void basicLotFunctions() {
    ParkingLot lot = new ParkingLot(1, 1, 1);
    Assert.assertEquals(lot.getAvailableSpots(), 3);
    Assert.assertEquals(lot.getAvailableSpots(MOTORCYCLE), 1);
    Assert.assertEquals(lot.getAvailableSpots(CAR), 1);
    Assert.assertEquals(lot.getAvailableSpots(VAN), 1);
    Assert.assertTrue(lot.isEmpty());
    Assert.assertFalse(lot.isFull());
    lot.printLotInfo();
  }

  /**
   * Test that motorcycles use the correct spot types based on availability, and are added and
   * removed in a way that correctly modifies lot capacity
   */
  @Test
  public void motorCycleFunctionality() {
    ParkingLot lot = new ParkingLot(1, 1, 1);
    // Motorcycles will be assigned to Motorcycle spots until full, then cars, then vans
    MotorCycle mc1 = new MotorCycle();
    MotorCycle mc2 = new MotorCycle();
    MotorCycle mc3 = new MotorCycle();
    // Add all vehicles and check that the lot is full, and all spot availability is empty
    lot.addVehicles(mc1, mc2, mc3);
    Assert.assertEquals(lot.getAvailableSpots(), 0);
    Assert.assertEquals(lot.getAvailableSpots(MOTORCYCLE), 0);
    Assert.assertEquals(lot.getAvailableSpots(CAR), 0);
    Assert.assertEquals(lot.getAvailableSpots(VAN), 0);
    // Remove mc1, and check that 1 motorcycle spot becomes available.
    lot.removeVehicle(mc1);
    Assert.assertFalse(lot.isFull());
    Assert.assertEquals(lot.getAvailableSpots(), 1);
    Assert.assertEquals(lot.getAvailableSpots(MOTORCYCLE), 1);
    Assert.assertEquals(lot.getAvailableSpots(CAR), 0);
    Assert.assertEquals(lot.getAvailableSpots(VAN), 0);
    // Remove mc2, and check that 1 car spot becomes available.
    lot.removeVehicle(mc2);
    Assert.assertEquals(lot.getAvailableSpots(), 2);
    Assert.assertEquals(lot.getAvailableSpots(MOTORCYCLE), 1);
    Assert.assertEquals(lot.getAvailableSpots(CAR), 1);
    Assert.assertEquals(lot.getAvailableSpots(VAN), 0);
    // Remove mc3, and check that 1 van spot becomes available
    lot.removeVehicle(mc3);
    Assert.assertEquals(lot.getAvailableSpots(), 3);
    Assert.assertEquals(lot.getAvailableSpots(MOTORCYCLE), 1);
    Assert.assertEquals(lot.getAvailableSpots(CAR), 1);
    Assert.assertEquals(lot.getAvailableSpots(VAN), 1);
  }

  /**
   * Test that a van is added to 3 car spots when no van spots are available, and there are 3 open
   * car spots
   */
  @Test
  public void canAddVanToCarSpots() {
    ParkingLot lot = new ParkingLot(0, 10, 0);
    Van van = new Van();
    lot.addVehicle(van);
    Assert.assertTrue(lot.isVehiclePresent(van));
    Assert.assertEquals(lot.getAvailableSpots(CAR), 7);
  }

  @Test
  public void lotCapacityTest() {
    ParkingLot lot = new ParkingLot(1, 1, 1);
    MotorCycle mc = new MotorCycle();
    Car car = new Car();
    Van van1 = new Van();
    Van van2 = new Van();
    // Van 2 should not bed added, as there is not enough spaces
    lot.addVehicles(mc, car, van1, van2);
    Assert.assertFalse(lot.isVehiclePresent(van2));
    // Remove the car, and validate lot capacities
    lot.removeVehicles(car);
    Assert.assertEquals(lot.getAvailableSpots(), 1);
    Assert.assertEquals(lot.getAvailableSpots(CAR), 1);
    Assert.assertFalse(lot.isFull());
    Assert.assertFalse(lot.isEmpty());
    Assert.assertFalse(lot.isVehiclePresent(car));
    lot.removeVehicles(mc, van1);
    Assert.assertTrue(lot.isEmpty());
  }
}
