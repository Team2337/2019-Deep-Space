package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class ClimberPneumatics extends Subsystem {

  private Solenoid piston;
  private int climberPort;
  private int PCM;

  public ClimberPneumatics() {
    PCM = 0;
    climberPort = 5;
    piston = new Solenoid(PCM, climberPort);
   }

  // Set the default command for a subsystem here.

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new Do_Nothing());
  }
   public void platformGrab(){
    piston.set(true);
   }
   public void platformRetract(){
    piston.set(false);
   }
}