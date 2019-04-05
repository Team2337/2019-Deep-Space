package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Drives the robot at a set speed of 75% forward, and 40% turn
 */
public class driveAtSetSpeed extends Command {

  public double turnSpeed;

  public driveAtSetSpeed(double turnSpeed) {
    this.turnSpeed = turnSpeed;
    setTimeout(0.5);
    requires(Robot.Chassis);
  }

  protected void execute() {
      Chassis.neoDrive.arcadeDrive(0.75, turnSpeed, false);
  }
  
  protected boolean isFinished() {
    return isTimedOut();
  }

  protected void end() {
  }
}
