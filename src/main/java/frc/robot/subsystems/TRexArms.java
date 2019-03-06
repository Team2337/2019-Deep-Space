package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Robot;

/**
 * Controls the platform grabbing mechanism using pneumatics
 */
public class TRexArms extends Subsystem {

  private Solenoid suctionCup;

  /**
   * Controls the platform grabbing mechanism using pneumatics
   */
  public TRexArms() {
    suctionCup = new Solenoid(Robot.Constants.tRexGrabberPort);
  }

  @Override
  public void initDefaultCommand() {

  }

  /**
   * Grabs onto the platform using a suction cup
   */
  public void grabPlatform() {
    suctionCup.set(true);
  }

  /**
   * Releases the robot from the platform using pneumatics
   */
  public void releasePlatform() {
    suctionCup.set(false);
  }

  /**
   * Returns whether or not the robot is grabbing onto the platform
   * 
   * @return Whether or not the robot is grabbing onto the platform
   */
  public boolean status() {
    return suctionCup.get();
  }
}