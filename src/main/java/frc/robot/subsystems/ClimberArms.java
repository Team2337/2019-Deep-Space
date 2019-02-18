package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls the intake mechanism for cargo
 */
public class ClimberArms extends Subsystem {

  // The motor to run the cargo intake
  private TalonSRX tRexArmsMotor;


  /* ---- CAN ID SETUP ---- */
  // Do not update without updating the wiki, too!
  private int tRexArmMotorID = 6;

  public ClimberArms() {
    // Configurations for the cargo intake motor
    this.tRexArmsMotor = new TalonSRX(tRexArmMotorID);
    tRexArmsMotor.setInverted(false);
    tRexArmsMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void initDefaultCommand() {

  }

  /**
   * Moves trex arms down
   * 
   * @param speed A decimal value from -1 to 1 to set the cargo intake motor speed
   *              to
   */
  public void moveDown(double speed) {
    tRexArmsMotor.set(ControlMode.PercentOutput, -speed);
  }

  /**
   * Moves trex arms up
   * 
   * @param speed A decimal value from -1 to 1 to set the cargo intake motor speed
   *              to (going in reverse)
   */
  public void moveUp(double speed) {
    tRexArmsMotor.set(ControlMode.PercentOutput, speed);
  }

  /**
   * Stop the cargo intake motor
   */
  public void stop() {
    tRexArmsMotor.set(ControlMode.PercentOutput, 0);
  }

}