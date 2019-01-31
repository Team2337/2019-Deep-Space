package frc.robot.subsystems;

import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class CargoScore extends Subsystem {
  private TalonSRX CargoScoreMotor;
  private int CargoScoreMotorID = 6;

  public DigitalInput cargoSensor;

  public CargoScore() {
    this.CargoScoreMotor = new TalonSRX(CargoScoreMotorID);
    CargoScoreMotor.setInverted(true);
    CargoScoreMotor.setNeutralMode(NeutralMode.Brake);

    cargoSensor = new DigitalInput(0);
  }

  public void initDefaultCommand() {
    // setDefaultCommand(new doNothing());
  }

  public void rollOut(double speed) {
    CargoScoreMotor.set(ControlMode.PercentOutput, speed);
  }

  public void rollIn(double speed) {
    CargoScoreMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void stop() {
    CargoScoreMotor.set(ControlMode.PercentOutput, 0);
  }

  public boolean hasCargo(){
    return !cargoSensor.get();
  }
}