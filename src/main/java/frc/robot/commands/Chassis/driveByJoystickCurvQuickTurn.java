package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.nerdyfiles.controller.NerdyUltimateXboxDriver;
import frc.robot.subsystems.Chassis;

/**
 * Uses controller joysticks to drive the robot using ArcadeDrive
 */
public class driveByJoystickCurvQuickTurn extends Command {

  // Gets the driver joystick from OI.java
  private NerdyUltimateXboxDriver driverJoystick;
  private boolean isNeoDrive;

  // How fast the robot moves overall
  double moveSpeed;

  // Adjusts the turn intensity
  double turnSpeed;

  /**
   * Uses Arcade Drive to drive either Neo or Talon motor controllers
   * 
   * @param isNeoDrive A boolean representing whether or not the joystick should
   *                   control Neos to drive
   */
  public driveByJoystickCurvQuickTurn(boolean isNeoDrive) {
    this.isNeoDrive = isNeoDrive;
    requires(Robot.Chassis);
  }
  
  protected void initialize() {
    driverJoystick = Robot.oi.driverJoystick;
  }

  // Supplys the correct values to the arcadeDrive command to drive the robot
  protected void execute() {

    // Left joystick's front/back movement as a number from -1 to 1
    moveSpeed = driverJoystick.getLeftStickY();

    // Right joysticks left/right movement as a number from -1 to 1
    turnSpeed = driverJoystick.getRightStickX();

    if(Robot.Lift.getPosition() > 300 && moveSpeed > 0.65) {
      moveSpeed = 0.65;
    }

    // If the robot is driving with Neos, send the values to neoDrive, otherwise,
    // send the values to talonDrive
    if (this.isNeoDrive) {
      Chassis.neoDrive.curvatureDrive(moveSpeed, turnSpeed, true);
      // Chassis.neoDrive.arcadeDrive(moveSpeed, turnSpeed, true);
    } else {
      // Chassis.talonDrive.arcadeDrive(moveSpeed, turnSpeed, true);
    }
  }

  // This command is not meant to exit
  protected boolean isFinished() {
    return false;
  }

  protected void end() {

  }

  @Override
  protected void interrupted() {
    this.end();
  }
}
