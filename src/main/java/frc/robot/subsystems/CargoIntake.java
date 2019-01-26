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
public class CargoIntake extends Subsystem {
  private TalonSRX CargoIntakeMotor;
  // Subject to change.
  private int CargoIntakeMotorID = 6;
  @Override
  public void initDefaultCommand() {

  }
  public CargoIntake() {
    this.CargoIntakeMotor = new TalonSRX(CargoIntakeMotorID);
    CargoIntakeMotor.setInverted(true);
    CargoIntakeMotor.setNeutralMode(NeutralMode.Brake);
  }
  /**
   * runs motor at full speed, negtive speed, or doesn't move
   */
  public void intake(double speed) {
    CargoIntakeMotor.set(ControlMode.PercentOutput, speed);
  }

  public void outake(double speed) {
    CargoIntakeMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void stop() {
    CargoIntakeMotor.set(ControlMode.PercentOutput, 0);
  }

}
