package frc.robot.subsystems;

import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class ClimberMotors extends Subsystem {
private TalonSRX rightMotor;
private TalonSRX leftMotor;
private int portLeft;
private int portRight;



  public ClimberMotors() {
    portRight = 3;
    portLeft = 4;
rightMotor = new TalonSRX(portRight);
leftMotor = new TalonSRX(portLeft);
rightMotor.setInverted(false);
leftMotor.setInverted(true);
rightMotor.setNeutralMode(NeutralMode.Brake);
leftMotor.setNeutralMode(NeutralMode.Brake);


  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Do_Nothing());
  }
  public void flipUp(double speed){
  rightMotor.set(ControlMode.PercentOutput, speed);
  leftMotor.set(ControlMode.PercentOutput, speed);
  }
  public void flipDowm(double speed){
    rightMotor.set(ControlMode.PercentOutput, -speed);
    leftMotor.set(ControlMode.PercentOutput, -speed);

  }
}