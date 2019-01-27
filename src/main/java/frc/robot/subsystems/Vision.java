package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 */
public class Vision extends Subsystem {

  public AnalogInput ultrasonic;
  public double voltsToInch = 0.4;

  private int analogPort = 1;
  private boolean visionDebug = true;
  private boolean distSensorDebug = false;

  public Vision() {

    ultrasonic = new AnalogInput(analogPort);
  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    
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

  public int getLEDMode() {
    return (int)NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").getValue().getDouble();
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
   * Sets the stream mode using the limelight and an external webcam 
   * @param mode - stream mode
   * Example: 
   * 0: Standard - Side-by-side streams if a webcam is attached to Limelight
   * 1: PiP Main - The secondary camera stream is placed in the lower-right corner of the primary camera stream
   * 2: PiP Secondary - The primary camera stream is placed in the lower-right corner of the secondary camera stream
   */
  public void streamMode(int mode) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(mode);
  }


  /**
   * 
   * @return - returns the raw distance output in the form of voltage, from the ultrasonic sensor
   */
  public double getVoltage() {
    return ultrasonic.getVoltage();
  }

  /**
   * 
   * @return - returns the voltage value multiplied by the voltsToInch conversion constant
   * @see getVoltage()
   */
  public double getDistance() {
   return (getVoltage() * voltsToInch); 
  }

  @Override
  public void periodic() {
    if(distSensorDebug) {
    SmartDashboard.putNumber("UltraSonic - Distance", getDistance());
    SmartDashboard.putNumber("UltraSonic - Voltage", getVoltage());
    }
  }

}