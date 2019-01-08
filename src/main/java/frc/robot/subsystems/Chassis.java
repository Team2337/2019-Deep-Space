/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.commands.*;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * 
 */
public class Chassis extends Subsystem {

  // DECLARATIONS
  private VictorSP leftMotor;
  private SpeedControllerGroup speed_Controller_Group_Left;
  private VictorSP rightMotor;
  private SpeedControllerGroup speed_Controller_Group_Right;
  private DifferentialDrive differentialDrive;

  public Chassis() {

    // CONSTRUCTORS
    leftMotor = new VictorSP(0);
    addChild("LeftMotor", leftMotor);
    leftMotor.setInverted(true);

    // Other possible speedcontrollers include:
    // motor = new WPI_VictorSPX(0);
    // motor = new WPI_TalonSRX(0);
    // motor = new Spark(0);
    // ( 3rd party library(s) required for following: )
    // motor = new WPI_TalonSRX(0); //http://www.ctr-electronics.com/hro.html#product_tabs_technical_resources
    // motor = new WPI_VictorSPX(0)
    // sparkMax = // java api to be released soon. // http://www.revrobotics.com/sparkmax-software/

    speed_Controller_Group_Left = new SpeedControllerGroup(leftMotor);
    addChild("Speed_Controller_Group_Left", speed_Controller_Group_Left);

    rightMotor = new VictorSP(1);
    addChild("RightMotor", rightMotor);
    rightMotor.setInverted(false);

    speed_Controller_Group_Right = new SpeedControllerGroup(rightMotor);
    addChild("Speed_Controller_Group_Right", speed_Controller_Group_Right);

    differentialDrive = new DifferentialDrive(speed_Controller_Group_Left, speed_Controller_Group_Right);
    addChild("Differential Drive", differentialDrive);
    differentialDrive.setSafetyEnabled(true);
    differentialDrive.setExpiration(0.1);
    differentialDrive.setMaxOutput(1.0);
  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new JoystickDrive());
  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void driveArcade(double moveSpeed, double turnSpeed, boolean squaredInputs) {
    this.differentialDrive.arcadeDrive(moveSpeed, turnSpeed, squaredInputs);
  }

  public void driveCurvature(double moveSpeed, double turnSpeed, boolean isQuickTurn) {
    this.differentialDrive.curvatureDrive(moveSpeed, turnSpeed, isQuickTurn);
  }

  public void driveTank(double leftSpeed, double rightSpeed, boolean squareInputs) {
    this.differentialDrive.tankDrive(leftSpeed, rightSpeed, squareInputs);
  }

  public void stopDrive() {
    this.differentialDrive.arcadeDrive(0, 0, true);
  }
}
