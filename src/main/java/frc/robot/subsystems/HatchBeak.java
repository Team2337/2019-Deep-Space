package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * This subsystem controls the hatch beak, which acquires the hatch panel
 * 
 * @author Emily H.
 */
public class HatchBeak extends Subsystem {
  public DigitalInput tripWire = new DigitalInput(2);

  private Solenoid hatchBeakSolenoid = new Solenoid(0, 2);

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
   * Display the status on the smartdashboard
   */
  public void periodic() {
    SmartDashboard.putBoolean("tripWire", tripWire.get());
  }

}
