package frc.robot.commands.HatchBeak;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command retracts the hatch intake (loaded)
 * 
 * @author Emily H.
 */
public class hatchBeakRetract extends Command {

  // CONSTRUCTOR
  public hatchBeakRetract() {
    requires(Robot.HatchBeak);
  }

  // Retract the hatch grabber to score the hatch panel game piece
  @Override
  protected void initialize() {
    Robot.HatchBeak.retractHatchGrabber();
    Robot.HatchBeak.launchersExtend();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.HatchBeak.launchersRetract();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
