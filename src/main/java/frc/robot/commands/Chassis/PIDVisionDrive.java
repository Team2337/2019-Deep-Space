package frc.robot.commands.Chassis;

import frc.robot.Robot;

//import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Uses a PID to get us closer 
 * @author Bryce G.
 */
public class PIDVisionDrive extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, m_speed, m_timeout, targetDistance, ta;
  double p, i, d;

  boolean turnInPlace = false;

  /**
   * 
   * @param p - P value (Ex: 0.05 (percent of the stop distance))
   * @param i - I value (Ex: 0.05 (lowers/raises the steady coarse rate)) 
   * @param d - D value (Ex: 0.05 (dampens the ocilation))
   */
  public PIDVisionDrive(double p, double i, double d) {
    // chassis_TargetWithGyroPID(String name, double p, double i, double d)
    // p,i,d -> .03, 0, 0.02
    super("PIDLimelightTurn", p, i, d);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-1, 1);       // output range for 'turn' input to drive command


    targetAngle = 0;              // target tx value (limelight horizontal offset from center)
    targetDistance = 2;        // not used yet but will be used to drive forward to target based on ta
    m_timeout = 5.0;              // time before command will end, even if target not found

    // LiveWindow.addActuator("TargetPID", "PIDSubsystem Controller",

    requires(Robot.Chassis);
  }

  /**
   * 
   * @param p - P value (Ex: 0.05 (percent of the stop distance))
   * @param i - I value (Ex: 0.05 (lowers/raises the steady coarse rate)) 
   * @param d - D value (Ex: 0.05 (dampens the ocilation))
   * @param mode - String value that tells what mode the Vision drive is in
   * Example: "turnInPlace" - sets the chassis to turn towards the target without driving forward or back
   */
  public PIDVisionDrive(double p, double i, double d, String mode) {
    super("PIDLimelightTurn", p, i, d);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-1, 1);       // output range for 'turn' input to drive command


    targetAngle = 0;              // target tx value (limelight horizontal offset from center)
    targetDistance = 2;        // not used yet but will be used to drive forward to target based on ta
    m_timeout = 5.0;              // time before command will end, even if target not found

    switch(mode) {
      case "turnInPlace":
      turnInPlace = true;
      break;
    }
    requires(Robot.Chassis);
  }

  /**
   * Reads the tx value from the limelight and uses it as our input to the PID Object
   */
  protected double returnPIDInput() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  }

  /**
   *  The PID object outputs a value here and we then use it in our drive command below
   * (after gathering some other info first)
   * Takes the target distance from networktables 
   * @param output - the output given by the PID Objects
   */
  protected void usePIDOutput(double output) {
      ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
      m_speed = 0.6;//Robot.oi.driverJoystick.getRawAxis(1);

      if(ta > 0) {
      m_speed = (m_speed * ((targetDistance - ta)/targetDistance));

        if(m_speed <= -0.5) {
          m_speed = -0.5;
        } 
      } else {
        m_speed = 0;
        output = 0;
      }

      System.out.println("ta: " + ta + " ***** " + "Speed: " + m_speed);

      if(turnInPlace) {
        m_speed = 0;
        System.out.println("turnInPlace: " + turnInPlace);
      }

      Robot.Chassis.talonDrive.arcadeDrive(m_speed, -output, false);  // <== here we use the output to do something
  }

  protected void initialize() {
    setTimeout(m_timeout);
    this.setSetpoint(targetAngle);

    // Robot.m_chassis.setBrakeMode(NeutralMode brake);  //probably want to turn on brakemode to lessen overshoot??
  }

  protected void execute() {
      //  Nothing to put here.  The heavy lifting is done inside the PID object & executed in the isPIDOutput (above).
  }

  protected boolean isFinished() {
    return (isTimedOut() || getPIDController().onTarget());
  }

  protected void end() {
    Robot.Chassis.talonDrive.tankDrive(0, 0);
    //Robot.m_chassis.setBrakeMode(); // set brakemode back to coast??
  }

  protected void interrupted() {
    this.end();
  }

}