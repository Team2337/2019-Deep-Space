package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * This command is mainly a placeholder command, but it can be used
 * functionally. It does just as it says: nothing.
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
