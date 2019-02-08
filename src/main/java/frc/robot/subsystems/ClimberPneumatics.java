package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class ClimberPneumatics extends Subsystem {

  private Solenoid pistonRight;
  private Solenoid pistonLeft;
  private int rightPort;
  private int leftPort;
  private int PCM;


  


  public ClimberPneumatics() {
    PCM = 0;
    rightPort  = 6;
    leftPort = 7;
    pistonLeft = new Solenoid(PCM, leftPort);
    pistonRight = new Solenoid(PCM, rightPort);
   }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new Do_Nothing());
  }
   public void platformGrab(){
    pistonRight.set(true);
    pistonLeft.set(true);
   }
   public void platformRetract(){
    pistonLeft.set(false);
    pistonRight.set(false);
   }
}