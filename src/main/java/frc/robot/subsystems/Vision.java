package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Runs the Limelight vision code to align towards to the vision targets
 * 
 * @category VISION
 * @author Bryce G.
 */
public class Vision extends Subsystem {

  public AnalogInput ultrasonic;
  public double voltsToInch = 0.4;

  private int analogPort = 1;
  private boolean visionDebug = true;

  public Vision() {

    ultrasonic = new AnalogInput(analogPort);
  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new Do_Nothing());
  }


  /*****************************/
  /* ------------------------- */
  /* --- LIMELIGHT METHODS --- */
  /* ------------------------- */
  /*****************************/


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


  /**
   * @return - returns the raw distance output in the form of voltage, from the ultrasonic sensor
   */
  public double getVoltage() {
    return ultrasonic.getVoltage();
  }

  /**
   * @return - returns the voltage value multiplied by the voltsToInch conversion constant
   * @see getVoltage()
   */
  public double getDistance() {
   return (getVoltage() * voltsToInch); 
  }

  @Override
  public void periodic() {
    if(visionDebug) {
    SmartDashboard.putNumber("UltraSonic - Distance", getDistance());
    SmartDashboard.putNumber("UltraSonic - Voltage", getVoltage());
    }
  }

}