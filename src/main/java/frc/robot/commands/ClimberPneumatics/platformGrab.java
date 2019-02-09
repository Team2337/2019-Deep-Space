package frc.robot.commands.ClimberPneumatics;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * An example command. You can replace me with your own command.
 */
public class platformGrab extends Command {

  // CONSTRUCTOR
  public platformGrab() {

    requires(Robot.ClimberPneumatics);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
   Robot.ClimberPneumatics.platformGrab();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    return true;
  }
}