package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * Controls the scoring mechanism for cargo
 *
 * @category CARGO
 * @author Jack E.
 */
public class CargoScore extends Subsystem {

  // The motor to run the scoring mechanism
  private VictorSPX CargoScoreMotor;

  /* ---- CAN ID SETUP ---- */
  // Do not update without updating the wiki, too!
  private int CargoScoreMotorID = Robot.Constants.cargoScoreID;

  /**
   * Subsystem to score the cargo into the:
   * <ul>
   * <li>Low Rocket</li>
   * <li>Mid Rocket</li>
   * <li>Cargo Ship</li>
   */
  public CargoScore() {
    // Configurations for the scoring mechanism motor
    this.CargoScoreMotor = new VictorSPX(CargoScoreMotorID);
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
  public void rollReverse(double speed) {
    CargoScoreMotor.set(ControlMode.PercentOutput, -speed);
  }

  /**
   * Run the cargo scoring mechanisms motor at a specified speed
   * 
   * @param speed A decimal value from -1 to 1 to set the cargo scoring mechanism
   *              motor speed to
   */
  public void rollForwards(double speed) {
    CargoScoreMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Stop the cargo scoring mechansism motor
   */
  public void stop() {
    CargoScoreMotor.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Cargo scoring motor status
   */
  public double status() {
    return CargoScoreMotor.getMotorOutputPercent();
  }
}