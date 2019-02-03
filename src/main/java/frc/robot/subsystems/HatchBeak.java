package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * This subsystem controls the hatch beak, which acquires the hatch panel
 * 
 * @author Emily H.
 */
public class HatchBeak extends Subsystem {

  private Solenoid hatchBeakSolenoid = new Solenoid(0, 0);

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
    hatchBeakSolenoid.set(true);
  }

  /**
   * Close the hatch beak to launch the hatch panel
   */
  public void closeHatchBeak() {
    hatchBeakSolenoid.set(false);
  }

  /**
   * Display the status on the smartdashboard
   */
  public void periodic() {

  }

}
