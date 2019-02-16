package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Turns the Limelight LED off
 * <p><br/>Mode 1 turns off the LEDs</p>
 */
public class limeLightLEDOff extends InstantCommand {

  // CONSTRUCTOR
  public limeLightLEDOff() {
    requires(Robot.Vision);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.Vision.setLEDMode(1);
  }

}
