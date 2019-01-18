package frc.robot.commands.CargoClaw;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will close the claw
 * @author Zayd A
 */
public class cargoClawClose extends Command {

  // CONSTRUCTOR
  public cargoClawClose() {
    requires(Robot.CargoClaw);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // Robot.CargoScore.clawClose();

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
