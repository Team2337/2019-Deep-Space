package frc.robot.commands.Chassis;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Uses a PID to get us closer to the vision target The drive system uses the
 * limelight to determine the error <br/>
 * Using the new 3D mapping system allows us to be more accurate getting to the
 * target
 * 
 * @author Bryce G.
 */
public class PID3DLimelight extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, m_speed, m_timeout, endDistanceFromTarget, tx;
  double targetDistance;
  double distanceAway = 0;
  double initialDistance;
  double distanceModifier = 10000;// 13000;
  double driveP = 1.7;
  double ticksPerInch = 686;
  double p, i, d;
  double[] limelight3D;
  double[] limelightValues = new double[6];

  boolean turnInPlace = false;
  boolean angleNotSet = true;
  boolean noDistance = true;

  /**
   * Vision targeting that allows the robot to align to the target
   * 
   * @param p    - P value (Ex: 0.05 (percent of the stop distance))
   * @param i    - I value (Ex: 0.05 (lowers/raises the steady coarse rate))
   * @param d    - D value (Ex: 0.05 (dampens the ocilation))
   * @param mode - String value that tells what mode the Vision drive is in
   *             Example: "turnInPlace" - sets the chassis to turn towards the
   *             target without driving forward or back
   */
  public PID3DLimelight(double p, double i, double d, String mode) {
    super("PID3DLimelight", p, i, d); // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1); // acceptable tx offset to end PID
    getPIDController().setContinuous(false); // not continuous like a compass
    getPIDController().setOutputRange(-0.8, 0.8); // output range for 'turn' input to drive command

    limelight3D = new double[6];
    targetAngle = 0; // target tx value (limelight horizontal offset from center)
    m_timeout = 5; // time before command will end, even if target not found
    endDistanceFromTarget = 11 * ticksPerInch; // distance we want to be away from the target in inches

    switch (mode.toLowerCase()) {
    case "turninplace":
      turnInPlace = true;
      break;
    default:
      turnInPlace = false;
      break;
    }
    requires(Robot.Chassis);
  }

  protected void initialize() {
    Robot.Vision.setLEDMode(3);
    Robot.Pigeon.resetPidgey();
    Robot.Chassis.resetEncoders();
    tx = 0;
    targetDistance = 0;
    initialDistance = 0;
    angleNotSet = true;
    noDistance = true;
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
  }

  /**
   * Reads the tx value (left and right distance) from the limelight and uses it
   * as our input to the PID Object The motor power is returned as output
   */
  protected double returnPIDInput() {
    return -Robot.Pigeon.getYaw();
  }

  /**
   * The PID object outputs a value here and we then use it in our drive command
   * below (after gathering some other info first) Takes the target distance from
   * networktables
   * 
   * @param output - the output given by the PID Objects
   */
  protected void usePIDOutput(double output) {
    SmartDashboard.putNumber("output", output);

    if (Math.abs(Robot.Pigeon.getYaw()) < 6) {
      this.getPIDController().setPID(0.1, 0, 0);
    } else {
      this.getPIDController().setPID(0.01, 0, 0);
    }

      m_speed = 0.5;

    if (initialDistance == 0 && noDistance) {
      initialDistance = limelight3D[2]; // init distance
    } else if (noDistance) {
      Robot.Chassis.resetEncoders();
      targetDistance = (Math.abs(initialDistance * ticksPerInch) + distanceModifier/* endDistanceFromTarget */);
      noDistance = false;
    } else {
      distanceAway = Robot.Chassis.getAverageEncoderPosition();
    }

    if (Math.abs(targetDistance) > 0) {
      if (distanceAway == 0) {
        distanceAway = 1;
      }

      if(output > 0.2) {
        output = 0.2;
      }
      if(output < -0.2) {
        output = -0.2;
      }

      System.out.println("Output: " + output + " *** " + "Error: " + (-Robot.Pigeon.getYaw() - tx) + " *** " + "Output: " + (m_speed * (Math.abs(distanceAway) - targetDistance) / Math.abs(targetDistance))
          + " **** " + "(" + "speed: " + m_speed + "(Math.abs(" + "distanceAway: " + distanceAway + ") - "
          + "targetDistance: " + targetDistance + ")/Math.abs(" + "ditanceAway: " + targetDistance + "))"
          + "*** Encoder Ticks: " + Robot.Chassis.getAverageEncoderPosition());
      if (!noDistance) {
        m_speed = -(driveP * (m_speed * ((Math.abs(distanceAway) - targetDistance) / Math.abs(targetDistance))));
      }

      if(m_speed > 0.6) {
        m_speed = 0.6;
      }

      if(m_speed < 0.20) {
        m_speed = 0.20;
      }

      if (m_speed < 0) {
        m_speed = 0;
      }
    } else {
      m_speed = Robot.oi.driverJoystick.getLeftStickY();
      // output = 0;
    }
    
    Chassis.neoDrive.arcadeDrive(m_speed, output, false);
  }

  protected void execute() {
    limelight3D = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camtran")
        .getDoubleArray(limelightValues);

    if (tx == 0 && angleNotSet) {
      tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    } else if (angleNotSet) {
      Robot.Pigeon.resetPidgey();
      this.setSetpoint(tx);
      angleNotSet = false;
    }
  }

  protected boolean isFinished() {
    return false; // isTimedOut();
  }

  protected void end() {
    noDistance = true;
    targetDistance = 0;
    initialDistance = 0;
    Robot.Vision.setLEDMode(1);
    Robot.Chassis.stopNeoDrive();
  }

  protected void interrupted() {
    this.end();
  }

}