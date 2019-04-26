package frc.robot.commands.Auto;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Uses a PID to move the robot closer to the vision target
 * The drive system uses the limelight to determine the error
 * @category AUTO
 * @author Bryce G.
 */
public class autoPIDVisionDrive extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, m_speed, m_timeout, targetDistance, ta, tx, timeout, maxSpeed, maxTurn;
  double p, i, d, largeAngleP, smallAngleP;

  boolean turnInPlace = false;

  /**
   * Auton Vision dirve using the limelight
   * @param timeout - how long (in seconds) the command should run for (in the event the command has not ended otherwise)
   * @param smallAngleP - P value for angles under 10 degree
   * @param largeAngleP - P value for angles over 10 degree
   * @param maxSpeed - maximum speed of the robot
   * @param targetDistance - ta distance away from the target
   */
  public autoPIDVisionDrive(double timeout, double smallAngleP, double largeAngleP, double maxSpeed) {
    super("autoPIDVisionDrive", 0.05, 0, 0);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-0.3, 0.3);       // output range for 'turn' input to drive command

    this.smallAngleP = smallAngleP;
    this.largeAngleP = largeAngleP;
    this.timeout = timeout;
    this.maxSpeed = maxSpeed;
    targetAngle = 0;              // target tx value (limelight horizontal offset from center)
    targetDistance = 8.5;
      
    requires(Robot.Chassis);
  }

  /**
   * Auton Vision dirve using the limelight
   * @param timeout - how long (in seconds) the command should run for (in the event the command has not ended otherwise)
   * @param smallAngleP - P value for angles under 10 degree
   * @param largeAngleP - P value for angles over 10 degree
   * @param maxSpeed - maximum speed of the robot
   * @param targetDistance - ta distance away from the target
   */
  public autoPIDVisionDrive(double timeout, double smallAngleP, double largeAngleP, double maxSpeed, double maxTurn) {
    super("autoPIDVisionDrive", 0.05, 0, 0);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    this.maxTurn = maxTurn;
    getPIDController().setOutputRange(-maxTurn, maxTurn);       // output range for 'turn' input to drive command

    this.smallAngleP = smallAngleP;
    this.largeAngleP = largeAngleP;
    this.timeout = timeout;
    this.maxSpeed = maxSpeed;
    targetAngle = 0;              // target tx value (limelight horizontal offset from center)
    targetDistance = 8.5;
      
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

      if(Math.abs(tx) < 10) {
        this.getPIDController().setPID(smallAngleP, 0, 0); //p was 0.05 
      } else {
        this.getPIDController().setPID(largeAngleP, 0, 0); //p was 0.025
      }

      m_speed = maxSpeed;
      // m_speed = 0.6;

      if(ta > 0) {
      m_speed = (m_speed * ((targetDistance - ta)/targetDistance));

      } else {
        m_speed = 0;
        output = 0;
      }

      // System.out.println("ta: " + ta + " ***** " + "Speed: " + m_speed + " *** " + "tx: " + tx + " ***** " + "output: " + output);

      if(turnInPlace) {
        m_speed = 0;
      }

      if(m_speed < 0.3) {
        m_speed = 0.3;
      }

      if(m_speed < 0) {
        m_speed = 0;
      }
      Chassis.neoArcade(m_speed, -output, false);
  }

  protected void initialize() {
    Robot.Vision.setLEDMode(3);
    setTimeout(timeout);
    this.setSetpoint(targetAngle);
  }

  protected void execute() {
    
  }

  protected boolean isFinished() {
    return isTimedOut() || (timeSinceInitialized() > 1 && Robot.Chassis.getAverageNeoVelocity() <= 10);
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