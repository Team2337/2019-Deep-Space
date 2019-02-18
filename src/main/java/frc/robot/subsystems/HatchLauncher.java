package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Controls the hatch launcher, which propels the hatch panel away from the
 * robot
 * 
 * @author Hunter B
 */
public class HatchLauncher extends Subsystem {

  private Solenoid launcherPiston;

  public HatchLauncher() {
    launcherPiston = new Solenoid(Robot.Constants.hatchLauncherPort);
  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {

  }

  /**
   * Extend the hatch launcher
   */
  public void extend() {
    launcherPiston.set(true);
  }

  /**
   * Retract the hatch launcher
   */
  public void retract() {
    launcherPiston.set(false);

  }

  /**
   * Display the status on the smartdashboard
   */
  public void periodic() {

  }

}
