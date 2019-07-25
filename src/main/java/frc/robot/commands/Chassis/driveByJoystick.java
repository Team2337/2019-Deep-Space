package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.nerdyfiles.controller.NerdyUltimateXboxDriver;
import frc.robot.subsystems.Chassis;

/**
 * Uses controller joysticks to drive the robot using ArcadeDrive
 */
public class driveByJoystick extends Command {

  // Gets the driver joystick from OI.java
  private NerdyUltimateXboxDriver driverJoystick = Robot.oi.driverJoystick;
  private boolean isNeoDrive;
  private boolean isCurvature;
  private boolean highVelocity;
  private double timer;

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
  public driveByJoystick(boolean isNeoDrive, boolean isCurvature) {
    this.isNeoDrive = isNeoDrive;
    this.isCurvature = isCurvature;
    requires(Robot.Chassis);
  }

  protected void initialize() {
    timer = 0;
    highVelocity = false;
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

    /* --- Anti-Tip --- */
    if(Robot.Chassis.getAverageNeoVelocity() > 3500) {
      highVelocity = true;
      timer = 0;
    }
    if(Robot.Chassis.getAverageNeoVelocity() < 0) {
      highVelocity = false;
    }
    
    if(highVelocity) {
      if(moveSpeed <= 0.3) {
        if(timer < 10) {
          moveSpeed = 0.5;
          timer++;
        } if(timer > 10 && Robot.Pigeon.getRoll() <= 11) {
          highVelocity = false;
        }
      }
    }

    // If the robot is driving with Neos, send the values to neoDrive, otherwise,
    // send the values to talonDrive
    if (this.isNeoDrive) {
      // Chassis.neoDrive.arcadeDrive(moveSpeed, turnSpeed, true);
      if(this.isCurvature) {
        Chassis.neoDrive.curvatureDrive(moveSpeed, turnSpeed, false);
      } else {
        Chassis.neoDrive.arcadeDrive(moveSpeed, turnSpeed, true);
      }
    } else {
      // Chassis.talonDrive.arcadeDrive(moveSpeed, turnSpeed, true);
    }
  }

  // This command is not meant to exit
  protected boolean isFinished() {
    return false;
  }
}
