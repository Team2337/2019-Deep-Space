package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author John Reno/ Hunter Buzzell It makes the pnunumetics in the kicker
 *         extend and retract.
 */
public class AutoHatchKicker extends Subsystem {

  private Solenoid hatchkicker;
  private int hatchkickerport;
  private int PCM;

  public AutoHatchKicker() {
    PCM = 0;
    hatchkickerport = 7;
    hatchkicker = new Solenoid(PCM, hatchkickerport);
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