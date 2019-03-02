package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * Controls the intake mechanism for cargo
 */
public class CargoIntake extends Subsystem {

  // The motor to run the cargo intake
  public TalonSRX CargoIntakeMotor;

  /* ---- CAN ID SETUP ---- */
  // Do not update without updating the wiki, too!
  private int CargoIntakeMotorID = Robot.Constants.cargoIntakeID;

  /**
   * Subsystem to intake the cargo from the floor
   */
  public CargoIntake() {
    // Configurations for the cargo intake motor
    this.CargoIntakeMotor = new TalonSRX(CargoIntakeMotorID);
    CargoIntakeMotor.setInverted(false);
    CargoIntakeMotor.setNeutralMode(NeutralMode.Brake);
    CargoIntakeMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
  }

  public void initDefaultCommand() {

  }

  /**
   * Run the cargo intake motor at a specified speed
   * 
   * @param speed A decimal value from -1 to 1 to set the cargo intake motor speed
   *              to
   */
  public void rollIn(double speed) {
    CargoIntakeMotor.set(ControlMode.PercentOutput, speed); // Inverted from original
  }

  /**
   * Run the cargo intake motor in reverse at a specified speed
   * 
   * @param speed A decimal value from -1 to 1 to set the cargo intake motor speed
   *              to (going in reverse)
   */
  public void rollOut(double speed) {
    CargoIntakeMotor.set(ControlMode.PercentOutput, -speed); // Inverted from original
  }

  /**
   * Stop the cargo intake motor
   */
  public void stop() {
    CargoIntakeMotor.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Runs the intake outwards at 10% power to keep other cargo from being intaken while the robot has a game piece
   */
  public void intakeSafety() {
    CargoIntakeMotor.set(ControlMode.PercentOutput, 0.1);
  }
  
  /**
   * Cargo Intake Motor status
   */
  public double status() {
    return CargoIntakeMotor.getMotorOutputPercent();
  }
}