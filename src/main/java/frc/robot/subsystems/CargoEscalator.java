package frc.robot.subsystems;

import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class CargoEscalator extends Subsystem {
  private TalonSRX CargoEscalatorMotor;
  private int CargoEscalatorMotorID = 6;

  public CargoEscalator() {
    this.CargoEscalatorMotor = new TalonSRX(CargoEscalatorMotorID);
    CargoEscalatorMotor.setInverted(true);
    CargoEscalatorMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void initDefaultCommand() {
    // setDefaultCommand(new doNothing());
  }

  public void rollUp(double speed) {
    CargoEscalatorMotor.set(ControlMode.PercentOutput, speed);
  }

  public void rollDown(double speed) {
    CargoEscalatorMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void stop() {
    CargoEscalatorMotor.set(ControlMode.PercentOutput, 0);
  }
}