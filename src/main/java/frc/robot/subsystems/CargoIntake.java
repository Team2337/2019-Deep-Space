package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the intake mechanism for cargo
 */
public class CargoIntake extends Subsystem {

  // The motor to run the cargo intake
  public TalonSRX CargoIntakeMotor;

  public DigitalInput tripWire = new DigitalInput(2);

  /* ---- CAN ID SETUP ---- */
  // Do not update without updating the wiki, too!
  private int CargoIntakeMotorID = 13;

  public CargoIntake() {
    // Configurations for the cargo intake motor
    this.CargoIntakeMotor = new TalonSRX(CargoIntakeMotorID);
    //Used to read encoder values for the Chassis
    CargoIntakeMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    CargoIntakeMotor.setSensorPhase(false);
    CargoIntakeMotor.setInverted(false);
    CargoIntakeMotor.setNeutralMode(NeutralMode.Brake);
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
    CargoIntakeMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Run the cargo intake motor in reverse at a specified speed
   * 
   * @param speed A decimal value from -1 to 1 to set the cargo intake motor speed
   *              to (going in reverse)
   */
  public void rollOut(double speed) {
    CargoIntakeMotor.set(ControlMode.PercentOutput, -speed);
  }

  /**
   * Stop the cargo intake motor
   */
  public void stop() {
    CargoIntakeMotor.set(ControlMode.PercentOutput, 0);
  }
}