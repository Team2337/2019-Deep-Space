package frc.robot.commands.Shifter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command shifts the chassis to high gear (speed)
 * 
 * @author Emily H.
 */
public class shifterHighGear extends Command {

  // CONSTRUCTOR
  public shifterHighGear() {

    requires(Robot.Shifter);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.Shifter.shiftHighGear();
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
