package frc.robot.commands.Chassis;

import frc.robot.Robot;

//import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.PIDCommand;

public class PIDVisionDrive extends PIDCommand {

  double turnValue, targetAngle, leftJoystick, m_speed, m_timeout, targetDistance, ta;
  double p, i, d;

  public PIDVisionDrive(double p, double i, double d) {
    // chassis_TargetWithGyroPID(String name, double p, double i, double d)

    super("PIDLimelightTurn", .03, 0, 0.02);        // set name, P, I, D.
    getPIDController().setAbsoluteTolerance(0.1);   // acceptable tx offset to end PID
    getPIDController().setContinuous(false);        // not continuous like a compass
    getPIDController().setOutputRange(-1, 1);       // output range for 'turn' input to drive command


    targetAngle = 0;              // target tx value (limelight horizontal offset from center)
    targetDistance = 0.08;        // not used yet but will be used to drive forward to target based on ta
    m_timeout = 5.0;              // time before command will end, even if target not found

    // LiveWindow.addActuator("TargetPID", "PIDSubsystem Controller",

    requires(Robot.Chassis);
  }

  protected double returnPIDInput() {
    //  this reads the tx value from the LImelight and uses it as our input to the PID object.
    return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
  }

  protected void usePIDOutput(double output) {
      // The PID object outputs a value here and we then use it in our drive comand below
      // (after gathering some other info first)
      ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
      m_speed = Robot.oi.driverJoystick.getRawAxis(5);
      m_speed = m_speed * ((targetDistance - ta)/ta);

      Robot.Chassis.driveArcade(m_speed, output, false);  // <== here we use the output to do something
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
    Robot.Chassis.stopDrive();
    //Robot.m_chassis.setBrakeMode(); // set brakemode back to coast??
  }

  protected void interrupted() {
    this.end();
  }

}