package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * This detects if the robot is in line to the cargo ship and ready to do the hatch kicker
 * @author Bryce G. Zayd A. Hunter B. Tyler G.
 */
public class autoLineSensorDrive extends Command {
  private double moveSpeed = -1.0;
  private double turnSpeed = 0;

  private boolean squaredInputs;
  private boolean finished = false;

  public autoLineSensorDrive() {
    
    requires(Robot.Chassis);
  }

  
  protected void initialize() {
  }
  
  protected void execute() {
    if(Robot.Chassis.lineSensorBack.get()) {
      moveSpeed = -0.1;
    } else if(Robot.Chassis.lineSensorFront.get()) {
      moveSpeed = 0.1;
    } else if(Robot.Chassis.lineSensorMiddle.get()) {
      moveSpeed = 0;
      finished = true;
    } else {
      moveSpeed = 0;
      finished = false;
      System.out.println("NO LINE");
    }
    SmartDashboard.putNumber("moveSpeed", moveSpeed);
    Chassis.neoDrive.arcadeDrive(moveSpeed, turnSpeed, squaredInputs);
  }

  protected boolean isFinished() {
    return finished;
  }

  protected void end() {
    
  }

  protected void interrupted() {
    this.end();
  }

}