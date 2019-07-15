package frc.robot.commands.HatchBeak;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Opens the hatch beak, grabbing the hatch panel
 * 
 * @author Emily H.
 */
public class hatchBeakOpen extends Command {

  int timer = 0;

  public hatchBeakOpen() {
    requires(Robot.HatchBeak);
  }

  // Opens the beak to grab the hatch panel
  @Override
  protected void initialize() {
    Robot.HatchBeak.openHatchBeak();
    Robot.Vision.ultrasonicMode = false;
    Robot.oi.driverJoystick.setRumbleSpeed(0, 0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Robot.oi.driverJoystick.setRumbleSpeed(0, Robot.oi.driverJoystick.setRumbleTimer(timer, 1.0));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.oi.driverJoystick.setRumbleSpeed(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
