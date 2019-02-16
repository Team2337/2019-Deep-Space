package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Turns the Limelights LEDs on 
 * <p><br/>Mode 3 is to turn on the LED</p>
 */
public class limeLightLEDOn extends InstantCommand {

  // CONSTRUCTOR
  public limeLightLEDOn() {
    requires(Robot.Vision);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.Vision.setLEDMode(3);
  }

}
