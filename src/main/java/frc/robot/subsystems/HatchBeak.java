package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.Solenoid;


/**
 * This subsystem controls the intake to obtain the hatch panel game piece.
 * 
 * @author Emily H.
 */
public class HatchBeak extends Subsystem {

  private Solenoid hatchSolenoid = new Solenoid(0, 0);

  public HatchBeak() {

  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
     // setDefaultCommand(new hatchIntakeDoNothing());
  }

  /**
   * Extend the Hatch Grabber to obtain the hatch panel
   */
  public void extendHatchGrabber() {
    hatchSolenoid.set(true);
  }

  /**
   * Retracted the Hatch Grabber to score the hatch panel
   */
  public void retractHatchGrabber() {
    hatchSolenoid.set(false);
  }

  /**
   * Display the status on the smartdashboard
   */
  public void periodic() {

  }

}
