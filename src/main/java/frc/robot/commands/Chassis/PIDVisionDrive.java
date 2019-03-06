package frc.robot.commands.Chassis;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Uses a PID to get us closer to the vision target
 * The drive system uses the limelight to determine the error
 * @author Bryce G.
 */
public class PIDVisionDrive extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, m_speed, speed, m_timeout, targetDistance, ta, tx;
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
  public PIDVisionDrive(double p, double i, double d) {
    super("PIDLimelightTurn", p, i, d);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-0.4, 0.4);       // output range for 'turn' input to drive command


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

      if(Math.abs(tx) < 8) {
        this.getPIDController().setPID(0.06, 0, 0); 
      } else {
        this.getPIDController().setPID(0.02, 0, 0);
      }

      System.out.println("tx: " + tx + " ***** " + "output: " + output);

      //Robot.oi.driverJoystick.getLeftStickY()
      if(Robot.oi.driverJoystick.getLeftStickY() < 0.4) {
        speed = Robot.oi.driverJoystick.getLeftStickY();
      } else {
        speed = 0.4;
      }
      Chassis.neoArcade(speed, -(output), false);
  }

  protected void initialize() {
    Robot.Vision.setLEDMode(3);
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
    this.setSetpoint(targetAngle);
  }

  protected void execute() {
      
  }

  protected boolean isFinished() {
    return false;
  }

  protected void end() {
    Robot.Vision.setLEDMode(1);
  }

  protected void interrupted() {
    this.end();
  }

}