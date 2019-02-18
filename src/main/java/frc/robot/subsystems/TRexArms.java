package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class TRexArms extends Subsystem {

  private Solenoid suctionCup;

  public TRexArms() {
    suctionCup = new Solenoid(6);
  }

  @Override
  public void initDefaultCommand() {

  }

  public void platformGrab() {
    suctionCup.set(true);
  }

  public void platformRelease() {
    suctionCup.set(false);
  }
}