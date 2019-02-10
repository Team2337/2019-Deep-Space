package frc.robot.commands.CargoDrawbridge;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * The command lowers the cargo drawbridge for the intake
 */
public class lowerTheDrawbridge extends Command {

  /**
   * Laises the cargo drawbridge for the intake
   */
  public lowerTheDrawbridge() {
    requires(Robot.CargoDrawbridge);
  }

  // Retract the drawbridge pneumatic
  @Override
  protected void initialize() {
    Robot.CargoDrawbridge.lowerTheDrawbridge();
  }

  // The solenoid only needs to be set once, so nothing happens in execute()
  @Override
  protected void execute() {

  }

  // This command is not meant to run after it has retracted the solenoid
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