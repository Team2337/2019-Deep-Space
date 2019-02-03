package frc.robot.commands.HatchBeak;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Opens the hatch beak, grabbing the hatch panel
 * 
 * @author Emily H.
 */
public class hatchBeakOpen extends Command {

  // CONSTRUCTOR
  public hatchBeakOpen() {
    requires(Robot.HatchBeak);
  }

  // Opens the beak to grab the hatch panel
  @Override
  protected void initialize() {
    Robot.HatchBeak.openHatchBeak();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
