package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Retract the climber systems - T-Rex arms and the RoboWrangler
 */
public class retractClimber extends Command {

  /**
   * Retract the climber systems - T-Rex arms and the RoboWrangler
   */
  public retractClimber() {
    requires(Robot.ClimberDeploy);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.ClimberDeploy.undeployClimber();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // This command doesn't need to be run any more than once
  @Override
  protected boolean isFinished() {
    return true;
  }

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