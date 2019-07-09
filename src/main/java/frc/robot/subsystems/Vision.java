package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * Vision subsystem for the limelight, and any other camera/vision sensors
 * 
 * @author Bryce G.
 */
public class Vision extends Subsystem {

  public AnalogInput ultrasonic; 
  public double voltsToInch = 0.4;
  public boolean ultrasonicMode = false; 

  public Vision() {
    ultrasonic = new AnalogInput(3);
  }

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

  /**
   * Gets the Limelight's LED mode from network tables
   * @return - returns the LED mode value as an int from networktables
   */
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
   * Sets the pipeline of the limelight
   * @param pipeline - desired pipeline number btween 0-9
   * 0: Default
   * 9: Driver Cam
   */
  public void switchPipeLine(int pipeline) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeline);
  }

  /* --- Ultra Sonic Code --- */

  /**
   * Returns the range of the object in Inches
   * @return - double value = range of object in inches
   */
  public double getUltraSonicDistance() {
    return ultrasonic.getVoltage();
  }

  /**
   * Returns a boolean value to determine whether or not the alliance wall is at the right distance for hatch intaking
   * @return - true = at correct distance for intaking; false = not close enough to the wall for hatch intaking
   */
  public boolean isAtDistance() {
    return getUltraSonicDistance() < Robot.HatchBeak.hatchIntakeDistance ? true : false;
  }

}