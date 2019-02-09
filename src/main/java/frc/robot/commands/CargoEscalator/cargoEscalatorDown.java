package frc.robot.commands.CargoEscalator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the cargo escalator downwards to help eject cargo from
 * the robot (must be assisted by the intake)
 * 
 * @author Jack E.
 */
public class cargoEscalatorDown extends Command {

  private double speed;

  /**
   * Sets the speed of the cargo escalator motors (going backwards)
   * 
   * @param speed A double value from -1 to 1 to set the backwards speed of the
   *              cargo escalator motors to
   */
  public cargoEscalatorDown(double speed) {
    requires(Robot.CargoEscalator);
    this.speed = speed;
  }

  // Set the speed of the escalator motors (going backwards)
  @Override
  protected void initialize() {
    Robot.CargoEscalator.rollDown(this.speed);
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