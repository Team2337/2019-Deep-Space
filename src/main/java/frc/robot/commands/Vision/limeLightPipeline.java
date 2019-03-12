package frc.robot.commands.Vision;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Turns the Limelights LEDs on 
 * <p><br/>Mode 3 is to turn on the LED</p>
 */
public class limeLightPipeline extends InstantCommand {

  int pipe;
  // CONSTRUCTOR
  public limeLightPipeline(int pipe) {
    requires(Robot.Vision);
    this.pipe = pipe;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.Vision.switchPipeLine(pipe);
  }

}
