package frc.robot.subsystems;

import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Runs the intake and outake 
 * 
 * @author Zayd A.
 */
public class Cargo extends Subsystem {
  private TalonSRX CargoMotor;
  // Subject to change.
  private int CargoID = 7;
  @Override
  public void initDefaultCommand() {

  }
  public Cargo() {
    this.CargoMotor = new TalonSRX(CargoID);
    CargoMotor.setInverted(true);
    CargoMotor.setNeutralMode(NeutralMode.Brake);
  }
  /**
   * runs motor at full speed, negtive speed, or doesn't move
   */
  public void intake(double speed) {
    CargoMotor.set(ControlMode.PercentOutput, speed);
  }

  public void outake(double speed) {
    CargoMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void stop() {
    CargoMotor.set(ControlMode.PercentOutput, 0);
  }

}
