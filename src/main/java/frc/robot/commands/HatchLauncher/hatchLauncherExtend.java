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

  // Moves the hatch panel away from the robot
  @Override
  protected void initialize() {
    // If the lift is moving to the mid hatch scoring position, it will extend in
    // execute. Otherwise, extend the hatch launcher
    if (Robot.Lift.getSetpoint() != Robot.Lift.hatchMidScorePosition) {
      Robot.HatchLauncher.extend();
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // If the lift is moving to the mid hatch scoring position, wait until it
    // reaches the correct height before extending
    if (Robot.Lift.getSetpoint() == Robot.Lift.hatchMidScorePosition) {
      if (Robot.Lift.atHatchMidPosition(10)) {
        Robot.HatchLauncher.extend();
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //If the lift is moving towards the
    if (Robot.Lift.getSetpoint() == Robot.Lift.hatchMidScorePosition) {
      return Robot.Lift.atHatchMidPosition(10);
    } else {
      return true;
    }
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
