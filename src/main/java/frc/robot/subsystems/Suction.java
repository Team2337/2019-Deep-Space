package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/** This program controls pneumatics to press down suction cups on platform
 * @author Hunter B
 */
public class Suction extends Subsystem {

  private Solenoid pistonRight;
  private Solenoid pistonLeft;
  private int rightPort;
  private int leftPort;
  private int PCM;

  /**
   * presses down pnuematics with suction cups on the end of them to the platform.
   */
  public Suction() {
    PCM = 0;
    rightPort  = 3;
    leftPort = 4;
    pistonLeft = new Solenoid(PCM, leftPort);
    pistonRight = new Solenoid(PCM, rightPort);
   }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new Do_Nothing());
  }

  /**
   * Presses suction down
   */
   public void suctionDown(){
    pistonRight.set(true);
    pistonLeft.set(true);
   }
  
}