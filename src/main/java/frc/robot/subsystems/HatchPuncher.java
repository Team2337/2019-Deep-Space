package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;


/**
 * This is the subsystem that controls the pnuematics
 * 
 * @author Hunter B
 */
public class HatchPuncher extends Subsystem {

  private Solenoid hatchPuncherLeft = new Solenoid(0,1);
  private Solenoid hatchPuncherRight = new Solenoid(0,2);

  public HatchPuncher() {

  }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
     // setDefaultCommand(new hatchIntakeDoNothing());
  }

  /**
   * Extend the pnuematic that shoots the hatch
   */
  public void extend() {
    hatchPuncherRight.set(true);
    hatchPuncherLeft.set(true);
  }

  /**
   * Retracted the Pnuematic that shoots the hatch
   */
  public void retract() {
    hatchPuncherRight.set(false);
    hatchPuncherLeft.set(false);

  }
  
  /**
   * Display the status on the smartdashboard
   */
  public void periodic() {

  }

}
