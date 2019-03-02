
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * Controls the raising and lowering of the cargo drawbridge
 */
public class CargoDrawbridge extends Subsystem {

  private Solenoid CargoDrawbridge;

  public CargoDrawbridge() {
    CargoDrawbridge = new Solenoid(Robot.Constants.PCM0, Robot.Constants.cargoDrawbridgePort);
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
  
  /**
   * Cargo Drawbridge status
   */
  public boolean status() {
    return CargoDrawbridge.get();
  }
}