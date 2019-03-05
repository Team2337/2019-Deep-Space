package frc.robot.commands.Auto;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Uses a PID to get us closer to the vision target
 * The drive system uses the limelight to determine the error
 * @author Bryce G.
 */
public class autoPIDVisionDrive extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, m_speed, m_timeout, targetDistance, ta, tx;
  double p, i, d;

  boolean turnInPlace = false;

  /**
   * 
   * @param p - P value (Ex: 0.05 (percent of the stop distance))
   * @param i - I value (Ex: 0.05 (lowers/raises the steady coarse rate)) 
   * @param d - D value (Ex: 0.05 (dampens the ocilation))
   * @param mode - String value that tells what mode the Vision drive is in
   * Example: "turnInPlace" - sets the chassis to turn towards the target without driving forward or back
   */
  public autoPIDVisionDrive(double p, double i, double d, String mode) {
    super("PIDLimelightTurn", p, i, d);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-0.5, 0.5);       // output range for 'turn' input to drive command


    targetAngle = 0;              // target tx value (limelight horizontal offset from center)
    targetDistance = 5;        // not used yet but will be used to drive forward to target based on ta
    m_timeout = 5;              // time before command will end, even if target not found

    switch(mode) {
      case "turnInPlace":
      turnInPlace = true;
      break;
      default :
      turnInPlace = false;
      break;
    }
    requires(Robot.Chassis);
  }

  /**
   * Reads the tx value from the limelight and uses it as our input to the PID Object
   */
  protected double returnPIDInput() {
    return (NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
  }

  /**
   *  The PID object outputs a value here and we then use it in our drive command below
   * (after gathering some other info first)
   * Takes the target distance from networktables 
   * @param output - the output given by the PID Objects
   */
  protected void usePIDOutput(double output) {
      ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
      tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
      m_speed = 0.6;//Robot.oi.driverJoystick.getRawAxis(1);

      if(ta > 0) {
      m_speed = (m_speed * ((targetDistance - ta)/targetDistance));

      } else {
        m_speed = 0;
        output = 0;
      }
      // System.out.println("ta: " + ta + " ***** " + "Speed: " + m_speed);
      System.out.println("tx: " + tx + " ***** " + "output: " + output);

      if(turnInPlace) {
        m_speed = 0;
        System.out.println("turnInPlace: " + turnInPlace);
      }
      Chassis.neoArcade(m_speed, 0, false);
  }

  protected void initialize() {
    Robot.Vision.setLEDMode(3);
    setTimeout(m_timeout);
    this.setSetpoint(targetAngle);
  }

  protected void execute() {
      
  }

  protected boolean isFinished() {
    return isTimedOut();
  }

  protected void end() {
    Robot.Vision.setLEDMode(1);
    Robot.Chassis.stopNeoDrive();
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
  }

  protected void interrupted() {
    this.end();
  }

}