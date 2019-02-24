package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * TODO: Impliment this command
 */
public class switchDriveCam extends InstantCommand {

  // CONSTRUCTOR
  public switchDriveCam() {
    requires(Robot.Vision);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }
}
