package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

/**
 * @author John Reno/ Hunter Buzzell It makes the pnunumetics in the kicker
 *         extend and retract.
 */
public class AutoHatchKicker extends Subsystem {

  private Solenoid hatchkicker;

  public AutoHatchKicker() {
    hatchkicker = new Solenoid(Constants.hatchKickerPort);
  }

  @Override
  public void initDefaultCommand() {

  }

  /**
   * This extends the hatch kicker
   */
  public void hatchKickerExtend() {
    hatchkicker.set(true);
  }

  /**
   * This retracts the hatch kicker
   */
  public void hatckKickerRetract() {
    hatchkicker.set(false);
  }
}