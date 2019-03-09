package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Deploy the climber systems - T-Rex arms and the RoboWrangler
 */
public class deployClimberManual extends Command {

  /**
   * Deploy the climber systems - T-Rex arms and the RoboWrangler
   */
  public deployClimberManual() {
    requires(Robot.ClimberDeploy);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.ClimberDeploy.deployClimber();
    setTimeout(1);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.RoboWrangler.drive(-0.33);
  }

  // This command doesn't need to be run any more than once
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  @Override
  protected void end() {
    Robot.RoboWrangler.stop();
    Robot.ClimberDeploy.undeployClimber();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}