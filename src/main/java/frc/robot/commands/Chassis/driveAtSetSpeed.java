package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.nerdyfiles.controller.NerdyUltimateXboxDriver;
import frc.robot.subsystems.Chassis;

/**
 * Uses controller joysticks to drive the robot using ArcadeDrive
 */
public class driveAtSetSpeed extends Command {

  /**
   * Uses Arcade Drive to drive either Neo or Talon motor controllers
   * 
   * @param isNeoDrive A boolean representing whether or not the joystick should
   *                   control Neos to drive
   */
  public driveAtSetSpeed() {
    setTimeout(0.5);
    requires(Robot.Chassis);
  }

  // Supplys the correct values to the arcadeDrive command to drive the robot
  protected void execute() {

      Chassis.neoDrive.arcadeDrive(0.75, 0.4, false);
  }

  // This command is not meant to exit
  protected boolean isFinished() {
    return isTimedOut();
  }

  protected void end() {
  }
}
