package frc.robot.commands.Auto;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Turns the robot to given angle 
 * @author Bryce G.
 */
public class autoDriveToAngle extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, timeout, targetDistance, distance, speed;
  double p, i, d;

  boolean turnInPlace = false;

  /**
   * 
   * @param p - P value (Ex: 0.05 (percent of the stop distance))
   * @param i - I value (Ex: 0.05 (lowers/raises the steady coarse rate)) 
   * @param d - D value (Ex: 0.05 (dampens the ocilation))
   * 
   */
  public autoDriveToAngle(double p, double i, double d, double targetAngle, double timeout, double distance, double speed) {
    super("autoDriveToAngle", 0.005, i, d);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-0.5, 0.5);       // output range for 'turn' input to drive command


    this.targetAngle = targetAngle;              // target tx value (limelight horizontal offset from center)
    this.timeout = timeout;              // time before command will end, even if target not found
    this.distance = distance;
    this.speed = speed;

    requires(Robot.Chassis);
  }

  /**
   * Reads the tx value from the limelight and uses it as our input to the PID Object
   */
  protected double returnPIDInput() {
    return Robot.Pigeon.getYaw();
  }

  /**
   *  The PID object outputs a value here and we then use it in our drive command below
   * (after gathering some other info first)
   * Takes the target distance from networktables 
   * @param output - the output given by the PID Objects
   */
  protected void usePIDOutput(double output) {
      
      Chassis.neoArcade(speed, -output, false);
  }

  protected void initialize() {
    setTimeout(timeout);
    Robot.Chassis.resetEncoders();
    this.setSetpoint(targetAngle);
  }

  protected void execute() {
      
  }

  protected boolean isFinished() {
    return (Math.abs(Robot.Chassis.getRightPosition()) > distance) || isTimedOut();
  }

  protected void end() {
    Robot.Chassis.stopNeoDrive();
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
  }

  protected void interrupted() {
    this.end();
  }

}