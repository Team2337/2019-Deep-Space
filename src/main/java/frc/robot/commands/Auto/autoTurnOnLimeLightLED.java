package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import jaci.pathfinder.Trajectory;

public class autoTurnOnLimeLightLED extends Command{

    private Trajectory trajectory = Robot.curveFromToHatchRightT;
  public autoTurnOnLimeLightLED() {
    requires(Robot.Vision);
  }

   // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(timeSinceInitialized() >= (trajectory.length()-70) / 50) {
        Robot.Vision.setLEDMode(3);
      }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return (Robot.Vision.getLEDMode() == 3);
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