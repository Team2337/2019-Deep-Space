package frc.robot.commands.HatchBeak;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * Closes the hatch beak to allow the robot to launch the hatch panel
 * 
 * @author Emily H.
 */
public class hatchBeakClose extends Command {

  int timer = 0;
  boolean isAuton;
  // CONSTRUCTOR
  /**
   * Enables rumble 
   * @param timer - timer that determines hoow long the rumble goes for
   */
  public hatchBeakClose(int timer) {
    requires(Robot.HatchBeak);
    this.isAuton = false;
  }

  /**
   * Tells if the hatch is being used in auton or not
   */
  public hatchBeakClose() {
    requires(Robot.HatchBeak);
    this.isAuton = true;
  }

  // Collapse the hatch beak to allow the  robot to launch the panel
  @Override
  protected void initialize() {
    Robot.HatchBeak.beakMode = true;
    Robot.oi.driverJoystick.setRumbleSpeed(0, 1.0);
    Robot.HatchBeak.closeHatchBeak();
  }

  // Called repeatedly when this Command is scheduled to run
  
  @Override
  protected void execute() {
    // Robot.oi.driverJoystick.setRumbleSpeed(0, 1.0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.HatchBeak.beakMode = false;
    // Robot.oi.driverJoystick.setRumbleSpeed(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
