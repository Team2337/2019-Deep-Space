package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Extends autoHatchKicker
 * 
 * @author John R. Hunter B.
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
    //If the line sensor is tripped (false) extend the kicker
    if(!Robot.Chassis.autoLineSensor.get()) {
      Robot.AutoHatchKicker.hatchKickerExtend();
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
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}