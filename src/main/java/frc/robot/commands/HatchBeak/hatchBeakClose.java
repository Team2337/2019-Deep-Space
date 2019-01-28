package frc.robot.commands.HatchBeak;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Closes the hatch beak
 * 
 * @author Emily H.
 */
public class hatchBeakClose extends Command {

  // CONSTRUCTOR
  public hatchBeakClose() {
    requires(Robot.HatchBeak);
  }

  // Collapse the hatch beak to allow the robot to launch the panel
  @Override
  protected void initialize() {
    Robot.HatchBeak.closeHatchBeak();
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
