package frc.robot.commands.HatchBeak;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Closes the hatch beak, until the ultrasonic sensor has read the alliance station wall to be close enough to 
 * fire the beak, in order to intake the hatch. 
 * 
 * @author Bryce G.
 */
public class hatchBeakWithUltraSonic extends Command {

    // CONSTRUCTOR
    public hatchBeakWithUltraSonic() {
    requires(Robot.HatchBeak);
  }

  @Override
  protected void initialize() {
    Robot.Vision.ultrasonicMode = true;
    Robot.HatchBeak.beakMode = true;
    Robot.HatchBeak.closeHatchBeak();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.Vision.isAtDistance()) {
      Robot.HatchBeak.openHatchBeak();
      Robot.Vision.ultrasonicMode = false;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return !Robot.Vision.ultrasonicMode;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.HatchBeak.beakMode = false;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
