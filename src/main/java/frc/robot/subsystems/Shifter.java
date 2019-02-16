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

  private int PCM = 0;
  private int shifterPort = 1;

  private Solenoid shifter = new Solenoid(PCM, shifterPort);

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
    shifter.set(false);
  }

  /**
   * Shift the robot into low gear (Torque)
   */
  public void shiftLowGear() {
    shifter.set(true);
  }
}