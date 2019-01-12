package frc.robot.subsystems;

import frc.robot.commands.*;
import frc.robot.commands.HatchIntake.hatchIntakeDoNothing;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * This subsystem controls the intake to obtain the hatch panel game piece.
 */
public class HatchIntake extends Subsystem {

  private DoubleSolenoid hatchSolenoid = new DoubleSolenoid(0, 1, 2);

  public HatchIntake()
  {

  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new hatchIntakeDoNothing());
  }

  // Extend the Hatch Grabber to obtain the hatch panel
  public void extendHatchGrabber() {
    hatchSolenoid.set(Value.kForward);
  }

  // Retracted the Hatch Grabber to score the hatch panel
  public void retractHatchGrabber() {
    hatchSolenoid.set(Value.kReverse);
  }

  // Turn off the double solenoid
  public void hatchGrabberOff() {
    hatchSolenoid.set(Value.kOff);
  }

  // Display the status on the smartdashboard
  public void periodic() {

  }

}