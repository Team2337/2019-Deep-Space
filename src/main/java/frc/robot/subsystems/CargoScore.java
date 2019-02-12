package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the scoring mechanism for cargo
 *
 * @category CARGO
 * @author Jack E.
 */
public class CargoScore extends Subsystem {

  // The motor to run the scoring mechanism
  private TalonSRX CargoScoreMotor;

  /* ---- CAN ID SETUP ---- */
  // Do not update without updating the wiki, too!
  private int CargoScoreMotorID = 6;

  public CargoScore() {
    // Configurations for the scoring mechanism motor
    this.CargoScoreMotor = new TalonSRX(CargoScoreMotorID);
    CargoScoreMotor.setInverted(false);
    CargoScoreMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void initDefaultCommand() {

  }

  /**
   * Run the cargo scoring mechanisms motor in reverse at a specified speed
   * 
   * @param speed A decimal value from -1 to 1 to set the cargo scoring mechanism
   *              motor speed to (going in reverse)
   */
  public void rollOut(double speed) {
    CargoScoreMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Run the cargo scoring mechanisms motor at a specified speed
   * 
   * @param speed A decimal value from -1 to 1 to set the cargo scoring mechanism
   *              motor speed to
   */
  public void rollIn(double speed) {
    CargoScoreMotor.set(ControlMode.PercentOutput, -speed);
  }

  /**
   * Stop the cargo scoring mechansism motor
   */
  public void stop() {
    CargoScoreMotor.set(ControlMode.PercentOutput, 0);
  }
}