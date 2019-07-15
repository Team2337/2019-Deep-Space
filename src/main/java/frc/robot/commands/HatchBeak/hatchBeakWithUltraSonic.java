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

  boolean rumble = false;
  int timer = 0;
    // CONSTRUCTOR
    public hatchBeakWithUltraSonic(int timer) {
    requires(Robot.HatchBeak);
    this.timer = timer;
  }

  @Override
  protected void initialize() {
    Robot.HatchBeak.beakMode = true;
    Robot.HatchBeak.closeHatchBeak();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(timer < 100) {
      Robot.oi.operatorJoystick.setRumble(0, 1.0);
      Robot.oi.driverJoystick.setRumbleSpeed(0, 1.0);
    } else {
      Robot.oi.operatorJoystick.setRumble(0, 0);
      Robot.oi.driverJoystick.setRumbleSpeed(0, 0);
      timer ++;
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
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
