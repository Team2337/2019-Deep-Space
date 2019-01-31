package frc.robot.commands.CargoScore;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the scoring mechanism outwards to score the cargo in a
 * designated goal (cargo ship or rocket)
 * 
 * @author Zayd A. Jack E.
 */
public class cargoScoreOut extends Command {

  private double speed;

  // CONSTRUCTOR
  public cargoScoreOut(double speed) {
    requires(Robot.CargoScore);
    this.speed = speed;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.CargoScore.rollOut(this.speed);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.CargoScore.stop();

  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}