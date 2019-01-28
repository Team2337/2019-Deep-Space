package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Controls the hatch launcher, which propels the hatch panel away from the
 * robot
 * 
 * @author Hunter B
 */
public class HatchLauncher extends Subsystem {

  private Solenoid hatchLauncherPneumatics = new Solenoid(0, 1);

  public HatchLauncher() {

  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {

  }

  /**
   * Extend the hatch launcher
   */
  public void extend() {
    hatchLauncherPneumatics.set(true);
  }

  /**
   * Retract the hatch launcher
   */
  public void retract() {
    hatchLauncherPneumatics.set(false);

  }

  /**
   * Display the status on the smartdashboard
   */
  public void periodic() {

  }

}
