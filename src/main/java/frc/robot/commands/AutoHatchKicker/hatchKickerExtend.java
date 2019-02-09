package frc.robot.commands.AutoHatchKicker;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * @author John Reno/ Hunter Buzzell Extends pnumatic
 */
public class hatchKickerExtend extends Command {

  // CONSTRUCTOR
  public hatchKickerExtend() {

    requires(Robot.AutoHatchKicker);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.AutoHatchKicker.hatchKickerExtend();
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
