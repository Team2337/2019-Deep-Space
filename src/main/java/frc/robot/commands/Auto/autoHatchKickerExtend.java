package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * @author John Reno/ Hunter Buzzell Extends pnumatic
 */
public class autoHatchKickerExtend extends Command {

    double timeout;

  // CONSTRUCTOR
  public autoHatchKickerExtend(double timeout) {
    this.timeout = timeout;
    requires(Robot.AutoHatchKicker);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(timeout);
    Robot.AutoHatchKicker.hatchKickerExtend();
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
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
