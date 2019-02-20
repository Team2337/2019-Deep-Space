package frc.robot.commands.Auto;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * This detects if the robot is in line to the cargo ship and ready to do the
 * hatch kicker
 * 
 * @author Bryce G. Zayd A. Hunter B. Tyler G.
 */
public class autoLineSensorDrive extends Command {
  private double moveSpeed = -1.0;
  private double turnSpeed = 0;

  private boolean squaredInputs = false;
  private boolean finished = false;
  // timer for when it goes between brake and coast after coast it ends command
  private int timer = 0;

  public autoLineSensorDrive() {

    requires(Robot.Chassis);
  }

  protected void initialize() {
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    finished = false;

    timer = 0;
  }

  protected void execute() {
    if (Robot.Chassis.lineSensorMiddle.get()) {
      moveSpeed = 0;
      if (timer == 0){
        Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
      }
      timer++;
      if (timer == 12){
        Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
        finished = true;
      } 
    } else if (!Robot.Chassis.lineSensorBack.get()) {
      moveSpeed = -0.5;
      timer = 0;
    } else if (Robot.Chassis.lineSensorFront.get()) {
      moveSpeed = 0.5;
      timer = 0;
    } else {
      timer = 0;
      moveSpeed = 0;
      finished = false;
      System.out.println("NO LINE");
    }



    SmartDashboard.putNumber("moveSpeed", moveSpeed);
    Robot.Chassis.neoDriveArcade(moveSpeed, turnSpeed, squaredInputs);
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