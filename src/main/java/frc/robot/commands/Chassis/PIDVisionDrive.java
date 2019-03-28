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
public class PIDVisionDrive extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, m_speed, speed, m_timeout, targetDistance, ta, tx;
  double p, i, d;

  boolean turnInPlace = false;

  /**
   * Runs the limelight turn drive PID in teleop when a button is pressed
   * The driver is able to drive forward while holding the button and have the code adjust towrds the target
   * @param p - P value (Ex: 0.05 (percent of the stop distance))
   * @param i - I value (Ex: 0.05 (lowers/raises the steady coarse rate)) 
   * @param d - D value (Ex: 0.05 (dampens the ocilation))
   */
  public PIDVisionDrive(double p, double i, double d) {
    super("PIDLimelightTurn", p, i, d);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-0.3, 0.3);       // output range for 'turn' input to drive command

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
      tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);

      if(tx == 0) {
        output = -Robot.oi.driverJoystick.getRightStickX();
      }

      //If the angle error is close to the target, we want a higher P to have a sharper turn, otherwise it's a small turn
      if(Math.abs(tx) < 8) {
        this.getPIDController().setPID(0.09, 0, 0); 
      } else {
        this.getPIDController().setPID(0.04, 0, 0);
      }

      // Keep for testing 
      System.out.println("tx: " + tx + " ***** " + "output: " + output); 

      //Limit the forward drive to 40% while this command is active
      if(Robot.oi.driverJoystick.getLeftStickY() < 0.5) {
        speed = Robot.oi.driverJoystick.getLeftStickY();
      } else {
        speed = 0.5;
      }
      Chassis.neoArcade(speed, -(output), false);
  }

  protected void initialize() {
    Robot.Vision.setLEDMode(3);
    Robot.Chassis.setNeoOpenLoopRampRate(0);
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
    this.setSetpoint(targetAngle);
  }

  protected void execute() {
      
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.Chassis.setNeoOpenLoopRampRate(Robot.rampRate);
    Robot.Vision.setLEDMode(1);
  }

  protected void interrupted() {
    this.end();
  }

}