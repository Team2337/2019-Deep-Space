package frc.robot.commands.HatchIntake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command retracts the hatch intake (loaded)
 * 
 * @author Emily H.
 */
public class hatchIntakeRetract extends Command {

  // CONSTRUCTOR
  public hatchIntakeRetract() {
    requires(Robot.HatchIntake);
  }

  // Retract the hatch grabber to score the hatch panel game piece
  @Override
  protected void initialize() {
    Robot.HatchIntake.retractHatchGrabber();
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
