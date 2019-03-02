package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.nerdyfiles.BobDriveHelper;
import frc.robot.nerdyfiles.controller.NerdyUltimateXboxDriver;
import frc.robot.subsystems.Chassis;

/**
 * Uses controller joysticks to drive the robot using Cheesy-Bob drive output.
 */
public class driveByCheesyJoystick extends Command {

  private NerdyUltimateXboxDriver driverJoystick = Robot.oi.driverJoystick;   // Gets the driver joystick from OI.java

  private double moveSpeed, turnSpeed;      // values from the joysticks
  private double leftSpeed, rightSpeed;     // outputs to set motor velocities
  private double lastDirection;             // used to track drivers last intended direction
  private double quickTurnJoystickThreshold;// used to determine drivers last intended direction
  private double joystickThreshold;         // used to determine deadband
  private boolean quickTurn;                // used to track if quicturn button is pressed

  /**
   * Uses Cheesy Bob Drive to drive Neo motors
   * 
   */
  public driveByCheesyJoystick() {
    requires(Robot.Chassis);
  }

  protected void initialize() {
    joystickThreshold = 0.1;
    quickTurnJoystickThreshold = 0.4;
    lastDirection = 1;
  }

  protected void execute() {

    // Get Joystick Input
    moveSpeed = applyDeadband(driverJoystick.getLeftStickY(), joystickThreshold);
    turnSpeed = applyDeadband(driverJoystick.getRightStickX(), joystickThreshold);
    quickTurn = (Robot.oi.driverJoystick.bumperRight.get());

    // Track what the last intent of the driver is so that we know what to do when coasting
    if      (moveSpeed >  quickTurnJoystickThreshold) { lastDirection =  1.0; } 
    else if (moveSpeed < -quickTurnJoystickThreshold) { lastDirection = -1.0; }

    //  Calculate wheel speeds
    if (moveSpeed == 0) {
      // if coasting, determine pivot turn based on last intended driver direction.
      if      (turnSpeed > 0) { leftSpeed  = (lastDirection *  turnSpeed); } 
      else if (turnSpeed < 0) { rightSpeed = (lastDirection * -turnSpeed); }
    } 
    else {
      // Do cheesy math to calculate left and right drive values (-1 to 1).
      BobDriveHelper.cheesyDrive(moveSpeed, turnSpeed, quickTurn, true);
      // Set wheel speed based on cheesy math.
      leftSpeed  = BobDriveHelper.getLeft();
      rightSpeed = BobDriveHelper.getRight();
    }

    // Set the motor speeds.
    Chassis.neoDrive.tankDrive(leftSpeed, rightSpeed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  private double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

}