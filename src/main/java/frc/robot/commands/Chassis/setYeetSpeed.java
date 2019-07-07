package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.nerdyfiles.NeoNerdyDriveBU;

/**
 * Changes the maximum output of the chassis motors to preform the "yeet"
 * <br/>
 * <br/>
 * <strong>1.0</strong>: Full power - needed to jump from lvl1 to lvl2
 * <br/>
 * <br/>
 * <strong>0.85</strong>: Lowered drive speed to prevent tipping and increase efficiency 
 * 
 * @author Bryce G.
 */
public class setYeetSpeed extends Command {

  private double yeetSpeed; 
    
  public setYeetSpeed(double yeetSpeed) {
    requires(Robot.Chassis);
    this.yeetSpeed = yeetSpeed;
  }

  @Override
  protected void initialize() {
    Robot.Chassis.setMaxOutput(yeetSpeed); 
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
    this.end();
  }
}
