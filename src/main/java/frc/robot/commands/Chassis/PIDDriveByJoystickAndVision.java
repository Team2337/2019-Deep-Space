/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Chassis;

import javax.lang.model.util.ElementScanner6;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * PIDDriveByJoystickAndVision
 * Driving to the vision target using the limelight, and joystick control, with PID integration to
 * reduce jostling. 
 */
public class PIDDriveByJoystickAndVision extends PIDCommand {

  private double turnConstant, turn, speed;
  private double tx, ta, ty, tv;
  private double target;

  private double p, i, d, setPoint;

  // CONSTRUCTOR
  public PIDDriveByJoystickAndVision(double p, double i, double d, double setPoint) {
    super(p, i, d);
    this.p = p;
    this.i = i;
    this.d = d;
    this.setPoint = setPoint;

    requires(Robot.Chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    turnConstant = 0.075;
    target = 0.03;
    this.getPIDController().enable();
    Robot.Chassis.setBrakeMode(NeutralMode.Brake);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0); // has valid target
    tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0); // tx horizontal offset degrees from crosshair +-27 degrees
    ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0); // ty vertical offset from crosshair +-20.5 degrees.
    ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0); // target % of image

    speed = Robot.oi.driverJoystick.getRawAxis(1); // Left y

    if (true) {   //tv == 1.0
      turn = tx * turnConstant;
      speed = speed * ((target - ta)/target);
    } else {
      turn = Robot.oi.driverJoystick.getRawAxis(4);  // Right x
    }
    //1 rotation = 1.570795ft (4096 ticks)
    //pixels to ft = 
    //resolution = 320 x 240

    boolean squaredInputs = true;

    // this.setSetpointRelative(1);
    this.usePIDOutput(tx);
    this.setSetpoint(setPoint);
    this.getPIDController().setSetpoint(setPoint);

    turn = this.returnPIDInput();

    Robot.Chassis.driveArcade(0, turn, squaredInputs);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.Chassis.driveArcade(0, 0, false);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

  @Override
  protected double returnPIDInput() {
    return 0;
  }

  @Override
  protected void usePIDOutput(double output) {
    //Robot.Chassis.driveArcade(speed, turnSpeed, squaredInputs);
  }
  @Override
  public double getPosition(){
		return this.tx;
	}
}
