package frc.robot.commands.Shifter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command shifts the chassis to low gear (torque)
 * 
 * @author Emily H.
 */
public class shifterLowGear extends Command {

  // CONSTRUCTOR
  public shifterLowGear() {

    requires(Robot.Shifter);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.Shifter.shiftLowGear();
    Robot.Chassis.setMaxOutput(1.0);
    Robot.Chassis.setNeoCurrentLimits(55, 40, 0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
  }

  // Make this return true when this Command no longer needs to run (execute)
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.Chassis.setNeoCurrentLimits(38, 38, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
