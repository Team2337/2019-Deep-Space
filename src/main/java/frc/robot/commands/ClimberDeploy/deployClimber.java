package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Deploy the climber systems - T-Rex arms and the RoboWrangler
 */
public class deployClimber extends Command {

  /**
   * Deploy the climber systems - T-Rex arms and the RoboWrangler
   */
  public deployClimber() {
    requires(Robot.ClimberDeploy);
    requires(Robot.Lift);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.oi.operatorControls.BlackSwitch.get()) {
      if (Robot.Lift.atClimbPosition(10)) {
        Robot.ClimberDeploy.deployClimber();
      }
    }
  }

  // Once the climber is deployed, this command no longer needs to run
  @Override
  protected boolean isFinished() {
    return Robot.ClimberDeploy.status();
  }

  // When the button is released, the lift will hold it's current position
  @Override
  protected void end() {
    Robot.Lift.setSetpoint(Robot.Lift.getPosition());
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}