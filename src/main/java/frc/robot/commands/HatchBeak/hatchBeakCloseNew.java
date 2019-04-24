package frc.robot.commands.HatchBeak;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Closes the hatch beak to allow the robot to launch the hatch panel
 * 
 * @author Emily H.
 */
public class hatchBeakCloseNew extends Command {

  // CONSTRUCTOR
  public hatchBeakCloseNew() {
    requires(Robot.HatchBeak);
  }

  // Collapse the hatch beak to allow the  robot to launch the panel
  @Override
  protected void initialize() {
    Robot.HatchBeak.beakMode = true;
    Robot.HatchBeak.closeHatchBeak();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.Lift.getSetpoint() > Robot.Lift.hatchLowScorePosition) {
      Robot.Lift.setSetpoint(Robot.Lift.getSetpoint() - 10);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.HatchBeak.beakMode = false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
