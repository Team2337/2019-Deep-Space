/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Zayd A.
 * Allows you to roll in and out the cargo
 */
public class CargoIntake extends Subsystem {

  private TalonSRX rightMotor;
  private TalonSRX leftMotor;
  //Subject to change.
  private int rightPort = 1;
  private int leftPort = 2;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new doNothing());
  }

  public CargoIntake() {
    this.leftMotor = new TalonSRX(leftPort);
    leftMotor.setInverted(true);
    leftMotor.setNeutralMode(NeutralMode.Brake);

    this.rightMotor = new TalonSRX(rightPort);
    rightMotor.setInverted(false);
    rightMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void rollIn(double speed) {
    rightMotor.set(ControlMode.PercentOutput, speed);
    leftMotor.set(ControlMode.PercentOutput, speed);
  }

  public void rollOut(double speed) {
    rightMotor.set(ControlMode.PercentOutput, -speed);
    leftMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void rollStop() {
    rightMotor.set(ControlMode.PercentOutput, 0);
    leftMotor.set(ControlMode.PercentOutput, 0);
  }

}
