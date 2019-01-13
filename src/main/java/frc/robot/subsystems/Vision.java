package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class Vision extends Subsystem {



  public Vision() {

  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new Do_Nothing());
  }

  /**
   * Sets the LED mode to on, off, or blink
   * @param mode - the mode of the LEDs
   * Example: 
   * 0: Sets the mode to what is in the current pipeline
   * 1: Turns off the LEDs
   * 2: Blink mode on LEDs
   * 3: Turns on the LEDs
   */
  public void setLEDMode(int mode) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(mode);
  }

  /**
   * Takes snapshots every 0.5 seconds if enabled
   * @param mode - snapshot mode
   * Example: 
   * 0: no snapshots
   * 1: two snapshots per second
   */
  public void snapShotMode(int mode) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("snapshot").setNumber(mode);
  }

}