package frc.robot.commands.HatchBeak;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Closes the hatch beak to allow the robot to launch the hatch panel
 * 
 * @author Emily H.
 */
public class hatchBeakClose extends Command {

  double timer = 0;
  // CONSTRUCTOR
  public hatchBeakClose(double timer) {
    requires(Robot.HatchBeak);
    this.timer = timer;
  }

  public hatchBeakClose() {
    requires(Robot.HatchBeak);
  }

  // Collapse the hatch beak to allow the  robot to launch the panel
  @Override
  protected void initialize() {
    Robot.HatchBeak.beakMode = true;
    Robot.HatchBeak.closeHatchBeak();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(timer < 100) {
      Robot.oi.driverJoystick.setRumbleSpeed(0, 1.0);
    } else {
      Robot.oi.driverJoystick.setRumbleSpeed(0, 0);
      timer++;
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
