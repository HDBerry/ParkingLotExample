package com.parkinglot;

import static com.parkinglot.ParkingLot.ParkingSpot.MOTORCYCLE;
import static com.parkinglot.ParkingLot.ParkingSpot.CAR;
import static com.parkinglot.ParkingLot.ParkingSpot.VAN;

import com.vehicle.Vehicle;
import java.util.ArrayList;
import java.util.List;

/**
 * Parking Lot Object Class
 */
public class ParkingLot {
  int totalMotorCycleSpots;
  int totalCarSpots;
  int totalVanSpots;
  int availableMotorcycleSpots;
  int availableCarSpots;
  int availableVanSpots;

  List<Vehicle> vehicles;

  /**
   * Enum containing available parking spot types
   */
  public enum ParkingSpot {
    MOTORCYCLE,
    CAR,
    VAN
  }

  /**
   * Constructor to set the total and available spots, as well as create an empty list that will
   * contain vehicles as they enter the lot
   * @param totalMotorCycleSpots maximum amount of motorcycle lots for the lot
   * @param totalCarSpots maximum amount of car spots for the lot
   * @param totalVanSpots maximum amount of van spots for the lot
   */
  public ParkingLot(int totalMotorCycleSpots, int totalCarSpots, int totalVanSpots) {
    this.totalMotorCycleSpots = totalMotorCycleSpots;
    this.totalCarSpots = totalCarSpots;
    this.totalVanSpots = totalVanSpots;
    availableMotorcycleSpots = this.totalMotorCycleSpots;
    availableCarSpots = this.totalCarSpots;
    availableVanSpots = this.totalVanSpots;
    vehicles = new ArrayList<>();
  }

  /**
   * Gets the number of available spots in the lot, takes all spot types into account
   * @return the number of available spots
   */
  public int getAvailableSpots() {
    return availableMotorcycleSpots + availableCarSpots + availableVanSpots;
  }

  /**
   * Gets the number of available spots in the lot for the provided spot type
   * @param spot type of spot to get the availability of
   * @return the available number of spots
   */
  public int getAvailableSpots(ParkingSpot spot) {
    switch (spot) {
      case MOTORCYCLE:
        return availableMotorcycleSpots;
      case CAR:
        return availableCarSpots;
      case VAN:
        return availableVanSpots;
      default:
        return 0;
    }
  }

  /**
   * Gets the Maximum number of spots in the lot, takes all spot types into account
   * @return the number of total spots
   */
  public int getTotalSpots() {
    return totalCarSpots + totalMotorCycleSpots + totalVanSpots;
  }

  /**
   * Checks if the lots is full
   * @return true if full, else false
   */
  public boolean isFull() {
    return getAvailableSpots() == 0;
  }

  /**
   * Checks if the lot is empty
   * @return true if empty, else false
   */
  public boolean isEmpty() {
    return getAvailableSpots() == getTotalSpots();
  }

  /**
   * Checks if the provided vehicle can fit in the lot
   * @param vehicle to check
   * @return true if the vehicle can fit, else false
   */
  boolean canVehicleFit(Vehicle vehicle) {
    switch(vehicle.getBaseSpotType()) {
      case MOTORCYCLE:
        return getAvailableSpots() > 0;
      case CAR:
        return availableCarSpots + availableVanSpots > 0;
      case VAN:
        return availableCarSpots >= 3 || availableVanSpots > 0;
      default:
        return false;
    }
  }

  /**
   * Adds the provided vehicle to the lot if possible, else prints the vehicle does not fit.
   * @param vehicle to add
   */
  public void addVehicle(Vehicle vehicle) {
    if (canVehicleFit(vehicle)) {
      switch (vehicle.getBaseSpotType()) {
        case MOTORCYCLE:
          addMotorCycle(vehicle);
          break;
        case CAR:
          addCar(vehicle);
          break;
        case VAN:
          addVan(vehicle);
          break;
        default:
          System.out.println("Invalid vehicle");
      }
      vehicles.add(vehicle);
    } else {
      System.out.println(vehicle.getBaseSpotType().toString() + " can not fit!!");
    }
  }

  /**
   * Utility method to add multiple vehicles with one mehtod call
   * @param vehicles 1 or more comma separated vehicles
   */
  public void addVehicles(Vehicle... vehicles) {
    for (Vehicle vehicle : vehicles) {
      addVehicle(vehicle);
    }
  }

  /**
   * Private method to add a motorcycle to the lot, reduce available count, and set vehicle occupied
   * spot type
   * @param vehicle to add
   */
  private void addMotorCycle(Vehicle vehicle) {
    if (availableMotorcycleSpots > 0) {
      availableMotorcycleSpots--;
      vehicle.setOccupiedSpot(MOTORCYCLE);
      System.out.println("MOTORCYCLE added to MOTORCYCLE spot");
    } else {
      addCar(vehicle);
    }
  }

  /**
   * Private method to add a car (or motorcycle) to the lot, reduce the available count, and set
   * vehicle occupied spot type
   * @param vehicle to add
   */
  private void addCar(Vehicle vehicle) {
    if (availableCarSpots > 0) {
      availableCarSpots--;
      vehicle.setOccupiedSpot(CAR);
      System.out.println(vehicle.getBaseSpotType() + " added to CAR spot");
    } else {
      availableVanSpots--;
      vehicle.setOccupiedSpot(VAN);
      System.out.println(vehicle.getBaseSpotType() + " added to VAN spot");
    }
  }

  /**
   * Private method to add a van to the lot, reduce the available count, and set vehicle occupied
   * spot type
   * @param vehicle to add
   */
  private void addVan(Vehicle vehicle) {
    if (availableVanSpots > 0) {
      availableVanSpots--;
      vehicle.setOccupiedSpot(VAN);
      System.out.println("VAN added to VAN spot");
    } else {
      availableCarSpots = availableCarSpots - 3;
      vehicle.setOccupiedSpot(CAR);
      System.out.println("VAN added to 3 CAR spots");
    }
  }

  /**
   * Checks to see if the provided vehicle object is present in the lot
   * @param vehicle to check
   * @return true if present, else false
   */
  public boolean isVehiclePresent(Vehicle vehicle) {
    return vehicles.contains(vehicle);
  }

  /**
   * If the vehicle is present, this removes the provided vehicle, increases the available count,
   * and set the vehicle occupied spot type to null
   * @param vehicle to remove
   */
  public void removeVehicle(Vehicle vehicle) {
    if (!isVehiclePresent(vehicle)) {
      System.out.println("Vehicle not found! cannot remove");
      return;
    } else {
      switch (vehicle.getOccupiedSpot()) {
        case MOTORCYCLE:
          availableMotorcycleSpots++;
          System.out.println("Available Motorcycle Spots Increased by 1");
          break;
        case CAR:
          if (vehicle.getBaseSpotType().equals(VAN)) {
            availableCarSpots = availableCarSpots + 3;
            System.out.println("Available Car Spots Increased by 3");
          } else {
            availableCarSpots++;
            System.out.println("Available Car Spots Increased by 1");
          }
          break;
        case VAN:
          availableVanSpots++;
          System.out.println("Available Van Spots Increased by 1");
          break;
      }
      vehicles.remove(vehicle);
      vehicle.setOccupiedSpot(null);
    }
  }

  /**
   * Utility Method to remove multiple vehicles
   * @param vehicles 1 or more comma separated vehicles
   */
  public void removeVehicles(Vehicle... vehicles) {
    for (Vehicle vehicle : vehicles) {
      removeVehicle( vehicle);
    }
  }

  /**
   * Utility method to quickly print lot info
   */
  public void printLotInfo() {
    String delimiter = "----------------------------------";
    System.out.println(delimiter);
    System.out.println("**PARKING LOT**");
    System.out.println("Total Spots: " + getTotalSpots());
    System.out.println("Available Spots: " + getAvailableSpots());
    System.out.println(delimiter);
    System.out.println("Total Moto Spots: " + totalMotorCycleSpots);
    System.out.println("Total Car Spots: " + totalCarSpots);
    System.out.println("Total Van Spots: " + totalVanSpots);
    System.out.println(delimiter);
    System.out.println("Moto Spots Available: " + availableMotorcycleSpots);
    System.out.println("Car Spots Available: " + availableCarSpots);
    System.out.println("Van Spots Available: " + availableVanSpots);
    System.out.println(delimiter);
  }
}
