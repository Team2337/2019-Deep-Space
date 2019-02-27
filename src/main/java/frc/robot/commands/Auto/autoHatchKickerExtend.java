package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * @author John Reno/ Hunter Buzzell Extends pnumatic
 */
public class autoHatchKickerExtend extends Command {

    double timeout;
    boolean fin = false; 

  // CONSTRUCTOR
  public autoHatchKickerExtend(double timeout) {
    this.timeout = timeout;
    fin = false;
    requires(Robot.AutoHatchKicker);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(timeout);    
    fin = false;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(!Robot.Chassis.autoLineSensor.get()) {
      fin = true;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut() || fin;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.AutoHatchKicker.hatchKickerExtend();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
