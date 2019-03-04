package frc.robot.commands.Chassis;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Uses a PID to get us closer to the vision target
 * The drive system uses the limelight to determine the error
 * <br/>
 * Using the new 3D mapping system allows us to be more accurate getting to the target
 * @author Bryce G.
 */
public class PID3DLimelight extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, m_speed, m_timeout, targetDistance, distanceAway;
  double p, i, d;
  double[] limelight3D;
  double[] limelightValues = new double[6];

  boolean turnInPlace = false;

  /**
   * Vision targeting that allows the robot to align to the target
   * @param p - P value (Ex: 0.05 (percent of the stop distance))
   * @param i - I value (Ex: 0.05 (lowers/raises the steady coarse rate)) 
   * @param d - D value (Ex: 0.05 (dampens the ocilation))
   * @param mode - String value that tells what mode the Vision drive is in
   * Example: "turnInPlace" - sets the chassis to turn towards the target without driving forward or back
   */
  public PID3DLimelight(double p, double i, double d, String mode) {
    super("PID3DLimelight", p, i, d);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-1, 1);       // output range for 'turn' input to drive command

    limelight3D = new double[6];
    targetAngle = 0;              // target tx value (limelight horizontal offset from center)
    targetDistance = 11;          // distance we want to be away from the target in inches
    m_timeout = 5;              // time before command will end, even if target not found

    switch(mode.toLowerCase()) {
      case "turninplace":
      turnInPlace = true;
      break;
      default :
      turnInPlace = false;
      break;
    }
    requires(Robot.Chassis);
  }

  /**
   * Reads the tx value (left and right distance) from the limelight and uses it as our input to the PID Object
   * The motor power is returned as output
   */
  protected double returnPIDInput() {
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);//limelight3D[0];
  }

  /**
   *  The PID object outputs a value here and we then use it in our drive command below
   * (after gathering some other info first)
   * Takes the target distance from networktables 
   * @param output - the output given by the PID Objects
   */
  protected void usePIDOutput(double output) {
      distanceAway = limelight3D[2]; //converts to feet from inches
      m_speed = 0.5;//Robot.oi.driverJoystick.getRawAxis(1);

      if(Math.abs(distanceAway) > 0) {
      m_speed = (m_speed * (Math.abs(distanceAway) - targetDistance)/Math.abs(distanceAway));

        if(m_speed <= -0.5) {
          m_speed = -0.5;
        } 
      } else {
        m_speed = 0;
        output = 0;
      }
      System.out.println("ta: " + distanceAway + " ***** " + "Speed: " + m_speed);

      if(turnInPlace) {
        m_speed = 0;
        System.out.println("turnInPlace: " + turnInPlace);
      }
      // Chassis.talonDrive.arcadeDrive(m_speed, -output, false);
    Chassis.neoDrive.arcadeDrive(m_speed, -output, false);
  }

  protected void initialize() {
    Robot.Vision.setLEDMode(3);
    setTimeout(m_timeout);
    this.setSetpoint(targetAngle);
  }

  protected void execute() {
    limelight3D = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camtran").getDoubleArray(limelightValues);
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