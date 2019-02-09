package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class ClimberMotors extends Subsystem {
  private TalonSRX rightMotor;
  private int portRight;

  public ClimberMotors() {
    portRight = 7;
    rightMotor = new TalonSRX(portRight);
    rightMotor.setInverted(false);
    rightMotor.setNeutralMode(NeutralMode.Brake);
  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {

  }

  public void flipUp(double speed) {
    rightMotor.set(ControlMode.PercentOutput, speed);
  }

  public void flipDowm(double speed) {
    rightMotor.set(ControlMode.PercentOutput, -speed);

  }
}