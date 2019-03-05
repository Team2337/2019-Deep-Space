package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Deploy the climber systems - T-Rex arms and the RoboWrangler
 */
public class deployClimber extends Command {

  double timeout;

  /**
   * Deploy the climber systems - T-Rex arms and the RoboWrangler
   */
  public deployClimber(double timeout) {
    this.timeout = timeout;
    requires(Robot.ClimberDeploy);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    setTimeout(timeout);
    Robot.ClimberDeploy.deployClimber();
    Robot.ClimberDeploy.climberPhase = 2;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Once the climber is deployed, this command no longer needs to run
  @Override
  protected boolean isFinished() {
    return isTimedOut();
  }

  // When the button is released, the lift will hold it's current position
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