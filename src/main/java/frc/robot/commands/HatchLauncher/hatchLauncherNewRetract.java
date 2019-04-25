package frc.robot.commands.HatchLauncher;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command retracts the hatch launchers
 * 
 * @author Hunter B
 */
public class hatchLauncherNewRetract extends Command {

  // CONSTRUCTOR
  public hatchLauncherNewRetract() {
    requires(Robot.HatchLauncher);
    requires(Robot.Lift);
  }

  // Retracts the launching mechanism to allow the beak to grab another panel
  @Override
  protected void initialize() {
    Robot.HatchLauncher.retract();
    if(Robot.Lift.getSetpoint() == Robot.Lift.hatchLowScorePosition) {
      Robot.Lift.setSetpoint(Robot.Lift.hatchAboveBumpers);
    }
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
