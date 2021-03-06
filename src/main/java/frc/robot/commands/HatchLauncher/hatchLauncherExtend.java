package frc.robot.commands.HatchLauncher;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Extends the hatch launching mechanism
 * 
 * @author Hunter B.
 */
public class hatchLauncherExtend extends Command {

  // CONSTRUCTOR
  public hatchLauncherExtend() {
    requires(Robot.HatchLauncher);
  }

  // Propels the hatch panel away from the robot
  @Override
  protected void initialize() {
    Robot.HatchLauncher.extend();
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
