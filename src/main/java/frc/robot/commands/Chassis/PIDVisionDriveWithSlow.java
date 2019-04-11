package frc.robot.commands.Chassis;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Runs the limelight turn drive PID in teleop when a button is pressed
 * The driver is able to drive forward while holding the button and have the code adjust towrds the target
 * @author Bryce G.
 */
public class PIDVisionDriveWithSlow extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, m_speed, speed, m_timeout, ta, tx, speedModifier;
  double p, i, d;
  double distanceDriven = 0;
  double distanceModifier = 8000;
  double initialDistance = 0;
  double ticksPerInch = 582;
  double minSpeed = 0.3;
  double targetDistance = 1;

  double[] limelight3D;
  double[] limelightValues = new double[6];

  boolean turnInPlace = false;
  boolean noDistance = true;

  /**
   * Runs the limelight turn drive PID in teleop when a button is pressed
   * The driver is able to drive forward while holding the button and have the code adjust towrds the target
   * @param p - P value (Ex: 0.05 (percent of the stop distance))
   * @param i - I value (Ex: 0.05 (lowers/raises the steady coarse rate)) 
   * @param d - D value (Ex: 0.05 (dampens the ocilation))
   */
  public PIDVisionDriveWithSlow(double p, double i, double d) {
    super("PIDLimelightTurn", p, i, d);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-0.3, 0.3);       // output range for 'turn' input to drive command

    limelight3D = new double[6];
    targetAngle = 0;              // target tx value (limelight horizontal offset from center)

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
    SmartDashboard.putNumber("output", output);
    
    if (initialDistance == 0 && noDistance) {
      initialDistance = limelight3D[2]; // init distance
    } else if (noDistance) {
      Robot.Chassis.resetEncoders();
      targetDistance = (Math.abs(initialDistance * ticksPerInch) + distanceModifier);
      noDistance = false;
    } else {
      distanceDriven = Math.abs(Robot.Chassis.getAverageEncoderPosition());
    }

    tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);

    if(tx == 0) {
      output = -Robot.oi.driverJoystick.getRightStickX();
    }

    speedModifier = (1.1 * (targetDistance - distanceDriven)/targetDistance);

    //If the angle error is close to the target, we want a higher P to have a sharper turn, otherwise it's a small turn
    if(Math.abs(tx) < 8) {
      this.getPIDController().setPID(0.09, 0, 0); 
    } else {
      this.getPIDController().setPID(0.06, 0, 0);
    }

    // Keep for testing 
    System.out.println("tx: " + tx + " ***** " + "output: " + output); 

    //Limit the forward drive to 60% while this command is active
    if(Robot.oi.driverJoystick.getLeftStickY() < 0.6) {
      speed = Robot.oi.driverJoystick.getLeftStickY();
    } else {
      speed = 0.6;
    }

    speed *= speedModifier;

      //Limit the forward drive to the last speed while this command is active
     if(speed < minSpeed) {
        speed = minSpeed;
      }    

      if(Robot.Lift.getPosition() >= Robot.Lift.hatchLowScorePosition + 30) {
        speed = 0;
        output = 0.001;
      }

      Chassis.neoArcade(speed, -(output), false);
      System.out.println("Speed: " + speed + "targetDistance: " + targetDistance + "minus DistanceDriven: " + distanceDriven + "divided by " + targetDistance + 
      " = speedModifier: " + speedModifier + " initialDistance: " + initialDistance);
  }

  protected void initialize() {
    if (Robot.autonomousCommand != null) {
      Robot.autonomousCommand.cancel();
    }

    Robot.Vision.setLEDMode(3);
    Robot.Chassis.setNeoOpenLoopRampRate(0);
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
    this.setSetpoint(targetAngle);
    noDistance = true;
    distanceModifier = 8000;
    distanceDriven = 0;
    initialDistance = 0;
    ticksPerInch = 582;
    targetDistance = 1;
    minSpeed = 0.3;
  }

  protected void execute() {
      limelight3D = NetworkTableInstance.getDefault().getTable("limelight").getEntry("camtran").getDoubleArray(limelightValues);     
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    Robot.Vision.setLEDMode(1);
  }

  protected void interrupted() {
    this.end();
  }

}