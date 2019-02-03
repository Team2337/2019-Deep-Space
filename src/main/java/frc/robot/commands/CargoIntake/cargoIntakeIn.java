package frc.robot.commands.CargoIntake;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the intake inwards to acquire cargo and feed it to the
 * escalator system
 * 
 * @author Zayd A. Jack E.
 */
public class cargoIntakeIn extends Command {

  private double speed;

  /**
   * Sets the speed of the cargo intake motors
   * 
   * @param speed A double value from -1 to 1 to set the speed of the cargo intake
   *              motors to
   */
  public cargoIntakeIn(double speed) {
    requires(Robot.CargoIntake);
    this.speed = speed;
  }

  // Set the speed of the cargo intake motors
  @Override
  protected void initialize() {
    Robot.CargoIntake.rollIn(this.speed);
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

  // When the command ends, stop the intake motors
  @Override
  protected void end() {
    Robot.CargoIntake.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}