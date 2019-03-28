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

  private Solenoid hatchBeakSolenoid = new Solenoid(Robot.Constants.PCM0, Robot.Constants.hatchBeakPort);
  public boolean beakMode = false;

  public HatchBeak() {

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
   * Display the status on the smartdashboard
   */
  public void periodic() {
    
  }

}
