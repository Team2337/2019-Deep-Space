package frc.robot.commands.CargoIntake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Closes the hatch beak to allow the robot to launch the hatch panel
 * 
 * @author 
 */
public class cargoIntakeIn extends Command {

  // CONSTRUCTOR
  public cargoIntakeIn() {
    requires(Robot.CargoIntake);
  }

  // Collapse the hatch beak to allow the robot to launch the panel
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.CargoIntake.rollIn(-0.5);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
