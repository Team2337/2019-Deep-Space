package frc.robot.subsystems;

import frc.robot.commands.*;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 */
public class Vision extends Subsystem {

  NetworkTable table;
  NetworkTableEntry tx, ty, ta, tv, ledMode;

  double x, y, a;
  boolean v;

  private boolean limelightDebug = true;

  public Vision() {
    //Gets the network tables limelight table variables
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("<variablename>").getDouble(0);
  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Do_Nothing());
  }

  /**
   * Updates the limelight values
   */
  public void updateLimeLight() {
    this.x = this.tx.getDouble(0.0);
    this.y = this.ty.getDouble(0.0);
    this.a = this.ta.getDouble(0.0);
    this.v = this.tv.isValid();
  }

  /**
   * Sets the current pipeline
   * @param pipeline - tells which pipline is currently in use
   */
  public void setPipeline(int pipeline) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeline);
  }

  /**
   * Sets the mode on the LEDs
   * @param state - Sets the state of the LEDs onboard the limelight
   * Examples: 
   * 0 = use the LED Mode set in the current pipeline 
   * 1 = force off
   * 2 = force blink
   * 3 = force on
   */
  public void setLimeLightLEDStatus(int state) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(state);
  }

  /**
   * Sets limelight’s operation mode
   * @param mode - Sets the mode of the camera
   * Examples:
   * 0 = Vision Processor mode
   * 1 = Driver Camera mode
   */
  public void setCamMode(int mode) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(mode);
  }

  /**
   * Allows snapshots to be taken during a match
   * @param mode - Tells if snapshots are enabled
   * Examples: 
   * 0 = stop taking snapshots
   * 1 = take two snapshots per second
   */
  public void enableSnapshots(int mode) {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("snapshot").setNumber(mode);
  }

  public void periodic() {
    if(limelightDebug) {
    SmartDashboard.putNumber("LimeLightX", this.x);
    SmartDashboard.putNumber("LimeLightY", this.y);
    SmartDashboard.putNumber("LimeLightArea", this.a);
    SmartDashboard.putBoolean("LimeLightHsaValidTarget", this.v);
    }
  }

}