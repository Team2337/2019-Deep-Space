
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the raising and lowering of the cargo drawbridge
 */
public class CargoDrawbridge extends Subsystem {

  private Solenoid CargoDrawbridge;
  private int CargoDrawbridgeID = 0;

  public CargoDrawbridge() {
    CargoDrawbridge = new Solenoid(CargoDrawbridgeID);
  }

  public void initDefaultCommand() {
  }

  /**
   * Lower the cargo intake using pneumatics
   */
  public void lowerTheDrawbridge() {
    CargoDrawbridge.set(true);
  }

  /**
   * Raise the cargo intake using pneumatics
   */
  public void raiseTheDrawbridge() {
    CargoDrawbridge.set(false);
  }
}