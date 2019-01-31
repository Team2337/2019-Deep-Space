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

  /**
   * Sets the speed of the cargo scoring mechanism motors (going backwards)
   * 
   * @param speed A double value from -1 to 1 to set the speed of the cargo
   *              scoring mechanism motors to
   */
  public cargoScoreOut(double speed) {
    requires(Robot.CargoScore);
    this.speed = speed;
  }

  // Set the speed of the cargo scoring mechanism motors
  @Override
  protected void initialize() {
    Robot.CargoScore.rollOut(this.speed);
  }

  // The speed only needs to be set once, so nothing happens in execute()
  @Override
  protected void execute() {

  }

  // This command is not meant to end until the button is released
  @Override
  protected boolean isFinished() {
    return false;
  }

  // When the command ends, stop the scoring mechanism motors
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