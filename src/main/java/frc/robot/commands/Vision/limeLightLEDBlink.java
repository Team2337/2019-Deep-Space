package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Turns the Limelight LED off
 * <p><br/>Mode 2 turns off the LEDs</p>
 */
public class limeLightLEDBlink extends InstantCommand {

  // CONSTRUCTOR
  public limeLightLEDBlink() {
    requires(Robot.Vision);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.Vision.setLEDMode(2);
  }

}
