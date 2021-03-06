package frc.robot.commands.Auto;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.command.PIDCommand;

/**
 * Turns to given degree using a PID
 * @category AUTO
 * @author Bryce G.
 */
public class autoTurnToDegree extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, timeout, targetDistance;
  double p, i, d;

  boolean turnInPlace = false;

  /**
   * @param p - P value (Ex: 0.05 (percent of the stop distance))
   * @param i - I value (Ex: 0.05 (lowers/raises the steady coarse rate)) 
   * @param d - D value (Ex: 0.05 (dampens the ocilation))
   * @param targetAngle - Desired angle of the robot (right is negative, left is positive)
   * @param timeout
   */
  public autoTurnToDegree(double p, double i, double d, double targetAngle, double timeout) {
    super("autoTurnToDegree", 0.04, i, d);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-0.35, 0.35);       // output range for 'turn' input to drive command


    this.targetAngle = targetAngle;              // target tx value (limelight horizontal offset from center)
    this.timeout = timeout;              // time before command will end, even if target not found

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
      
      Chassis.neoArcade(0, -output, false);
  }

  protected void initialize() {
    setTimeout(timeout);
    this.setSetpoint(targetAngle);
  }

  protected void execute() {
      
  }

  protected boolean isFinished() {
    return isTimedOut();
  }

  protected void end() {
    Robot.Chassis.stopNeoDrive();
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
  }

  protected void interrupted() {
    this.end();
  }

}