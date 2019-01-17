package frc.robot.subsystems;

import frc.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Runs the claw pressure and open and close of it as well
 * 
 * @author Zayd A. 
 */
public class CargoClaw extends Subsystem {


  private TalonSRX CargoClawMotor;
  //Subject to change.
  private int CargoClawPort = 7;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new doNothing());
  }

  public CargoClaw() {
    this.CargoClawMotor = new TalonSRX(CargoClawPort);
    CargoClawMotor.setInverted(true);
    CargoClawMotor.setNeutralMode(NeutralMode.Brake);

  
  }
/**
 * Opens claw at full speed, negtive speed, or doesn't move
 */
  public void rollIn(double speed) {
    CargoClawMotor.set(ControlMode.PercentOutput, speed);
  }

  public void rollOut(double speed) {
    CargoClawMotor.set(ControlMode.PercentOutput, -speed);
  }

  public void rollStop() {
    CargoClawMotor.set(ControlMode.PercentOutput, 0);
  }

}
