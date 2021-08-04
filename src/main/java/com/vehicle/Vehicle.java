package com.vehicle;

import com.parkinglot.ParkingLot.ParkingSpot;

/**
 * Vehicle Object Class
 */
public class Vehicle {
  private ParkingSpot baseSpotType;
  private ParkingSpot occupiedSpot;

  /**
   * Constructor Sets the base spot type. This method is private to restrict the creation of
   * a Generic vehicle type, and limit its use to the sub classes
   * @param baseSpotType to set
   */
  private Vehicle(ParkingSpot baseSpotType) {
    this.baseSpotType = baseSpotType;
  }

  // Getters and Setters
  public ParkingSpot getBaseSpotType() {
    return this.baseSpotType;
  }

  // No setter for the base spot type so that it can not be modified

  public ParkingSpot getOccupiedSpot() {
    return this.occupiedSpot;
  }

  public void setOccupiedSpot(ParkingSpot spot) {
    this.occupiedSpot = spot;
  }

  /**
   * Motorcycle sub-type
   */
  public static class MotorCycle extends Vehicle {
    public MotorCycle() {
      super(ParkingSpot.MOTORCYCLE);
    }
  }

  /**
   * Car sub-type
   */
  public static class Car extends Vehicle {
    public Car()  {
      super(ParkingSpot.CAR);
    }
  }

  /**
   * Van sub-type
   */
  public static class Van extends Vehicle {
    public Van()  {
      super(ParkingSpot.VAN);
    }
  }
}
