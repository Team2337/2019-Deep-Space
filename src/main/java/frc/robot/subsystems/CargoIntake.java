package frc.robot.subsystems;

import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class CargoIntake extends Subsystem {
  private TalonSRX CargoIntakeMotor;
  private int CargoIntakeMotorID = 6;

  public CargoIntake() {
    this.CargoIntakeMotor = new TalonSRX(CargoIntakeMotorID);
    CargoIntakeMotor.setInverted(true);
    CargoIntakeMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void initDefaultCommand() {
    // setDefaultCommand(new doNothing());
  }

  public void rollIn(double speed) {
    CargoIntakeMotor.set(ControlMode.PercentOutput, speed);
  }

  public void rollOut(double speed) {
    CargoIntakeMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void stop(){
    CargoIntakeMotor.set(ControlMode.PercentOutput, 0);
  }

}