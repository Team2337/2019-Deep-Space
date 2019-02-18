package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;

/**
 * Controls the escalator/conveyor for cargo
 */
public class CargoEscalator extends Subsystem {

  // The motor to run the cargo escalator
  private TalonSRX CargoEscalatorMotor;

  /* ---- CAN ID SETUP ---- */
  // Do not update without updating the wiki, too!
  private int CargoEscalatorMotorID = Constants.cargoEscalatorID;

  public CargoEscalator() {
    // Configurations for the cargo escalator motor
    this.CargoEscalatorMotor = new TalonSRX(CargoEscalatorMotorID);
    CargoEscalatorMotor.setInverted(false);
    CargoEscalatorMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void initDefaultCommand() {

  }

  /**
   * Run the cargo escalator motor at a specified speed
   * 
   * @param speed A decimal value from -1 to 1 to set the cargo escalator motor
   *              speed to
   */
  public void rollUp(double speed) {
    CargoEscalatorMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Run the cargo escalator motor in reverse at a specified speed
   * 
   * @param speed A decimal value from -1 to 1 to set the cargo escalator motor
   *              speed to (going in reverse)
   */
  public void rollDown(double speed) {
    CargoEscalatorMotor.set(ControlMode.PercentOutput, -speed);
  }

  /**
   * Stop the cargo escalator motor
   */
  public void stop() {
    CargoEscalatorMotor.set(ControlMode.PercentOutput, 0);
  }
}