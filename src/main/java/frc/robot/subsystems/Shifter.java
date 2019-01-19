package frc.robot.subsystems;


import frc.robot.commands.Shifter.shifterHighGear;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Shifts the chassis gear ratio from speed to torque 
 * Speed = High Gear 
 * Torque = Low Gear
 * 
 * @author Emily H.
 */
public class Shifter extends Subsystem {
/*
  private int PCM = 1;
  private int portRight = 5;//5;
  private int portLeft = 6;//6;

  private Solenoid leftSide = new Solenoid(PCM, portLeft);
  private Solenoid rightSide = new Solenoid(PCM, portRight);
*/
  public Shifter() {

  }

  // Set the default command for a subsystem here.
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new shifterHighGear());
  }

  /**
   * Shift the robot into high gear (Speed)
   */
  public void shiftHighGear() {
    // leftSide.set(true);
    // rightSide.set(true);
  }

  /**
   * Shift the robot into low gear (Torque)
   */
  public void shiftLowGear() {
    // leftSide.set(false);
    // rightSide.set(false);
  }
}