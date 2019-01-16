package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command is mainly a placeholder command, but it can be used
 * functionally. It does just as it says: nothing.
 */
public class DriveWithDistanceSensor extends Command {

  private double speed;

  public DriveWithDistanceSensor() {

    requires(Robot.Chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    speed = Robot.Vision.getDistance();

    Robot.Chassis.driveArcade(speed, 0, false);

    System.out.println(Robot.Vision.getDistance());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
