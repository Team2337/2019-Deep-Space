package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * 
 */
public class ClimberPneumatics extends Subsystem {

  private Solenoid piston;

  public ClimberPneumatics() {
    piston = new Solenoid(Robot.Constants.PCM0, Robot.Constants.climberPort);
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
   public boolean status(){
    return piston.get();
   }
}