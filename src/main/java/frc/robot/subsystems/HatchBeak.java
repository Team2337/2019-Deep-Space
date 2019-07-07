package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * This subsystem controls the hatch beak, which acquires the hatch panel
 * 
 * @author Emily H. Jack E.
 */
public class HatchBeak extends Subsystem {
  
  private Solenoid hatchBeakSolenoid;
  private boolean isAtDistanceUS = false;
  public boolean beakMode = false;
  public double hatchIntakeDistance = 1.0;
  
  public HatchBeak() {
    hatchBeakSolenoid = new Solenoid(Robot.Constants.PCM0, Robot.Constants.hatchBeakPort);
  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {

  }

  /**
   * Opens the hatch beak to obtain the hatch panel
   */
  public void openHatchBeak() {
    hatchBeakSolenoid.set(false);
  }

  /**
   * Close the hatch beak to launch the hatch panel
   */
  public void closeHatchBeak() {
    hatchBeakSolenoid.set(true);
  }

  /**
   * Hatch Beak status
   */
  public boolean status() {
    return hatchBeakSolenoid.get();
  }

  /**
   * Sets a boolean value to change the state of the isAtDistance variable to tell whether or not the robot is at the right distance away from 
   * the alliance wall to fire the hatch beak, intkaing the hatch. 
   * @param atDistance - boolean value: true = at correct distance (or closer); false = not close enough
   */
  public void setAtDistanceUS(boolean atDistance) {
    isAtDistanceUS = true;
  }

  /**
   * Returns a boolean value to tell whether or not the robot is close enough to the alliance wall to intake the hatch
   * @return - true: at distance close enough to fire hatch beak; false: not close enough to fire hatch beak
   */
  public boolean getAtDistanceUS() {
    return isAtDistanceUS;
  }

  public void setHatchIntakeDistance(double hatchIntakeDistance) {
    this.hatchIntakeDistance = hatchIntakeDistance;
  }

  /**
   * Display the status on the smartdashboard
   */
  public void periodic() {
    
  }

}
