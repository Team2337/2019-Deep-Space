package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Robot;
import frc.robot.nerdyfiles.BobDriveHelper;
//import frc.robot.nerdyfiles.DriveSignal;
import frc.robot.nerdyfiles.controller.NerdyUltimateXboxDriver;
import frc.robot.subsystems.Chassis;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

/**
 * Uses controller joysticks to drive the robot using Cheesy-Bob drive output * maxRPM then input into a velocity pid.
 */
public class driveByVelocity extends Command {

  private NerdyUltimateXboxDriver driverJoystick = Robot.oi.driverJoystick;   // Gets the driver joystick from OI.java

  private double moveSpeed, turnSpeed; // values from the joysticks
  private double leftVelocity, rightVelocity; // outputs to set motor velocities
  private double leftEncodersVelocity, rightEncodersVelocity;  // output from Neo encoders
  private double lastDirection; // tracks if driver was last intending to drive forward or backwards (over 0.3 on joystick in a direction)
  private double quickTurnJoystickThreshold;
  private double joystickThreshold;
  private boolean quickTurn; 
 
  //private BobDriveHelper helper; // the cheesy class to do cheesy math
 // private DriveSignal driveSignal;
 
  private CANPIDController right_pidController, left_pidController;
  private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

  /**
   * Uses Cheesy Bob Velocity Drive to drive Neo motors based on velocity
   * 
   */
  public driveByVelocity() {
    requires(Robot.Chassis);
  }

  protected void initialize() {

    maxRPM = 2400; // 5200forward 5400 backwards
    //quickTurnVelocityThreshold = 1000;
    joystickThreshold = 0.2;
    quickTurnJoystickThreshold = 0.4;
    lastDirection = 1;
    //helper = new BobDriveHelper();

    /**
     * In order to use PID functionality for a controller, a CANPIDController object
     * is constructed by calling the getPIDController() method on an existing
     * CANSparkMax object
     */
    left_pidController = Robot.Chassis.neoLeftFrontMotor.getPIDController();
    right_pidController = Robot.Chassis.neoRightFrontMotor.getPIDController();

    // PID coefficients
    kP = 5e-5;
    kI = 1e-6;
    kD = 0;
    kIz = 0;
    kFF = 0;
    kMaxOutput = 1;
    kMinOutput = -1;

    // set PID coefficients
    left_pidController.setP(kP);
    left_pidController.setI(kI);
    left_pidController.setD(kD);
    left_pidController.setIZone(kIz);
    left_pidController.setFF(kFF);
    left_pidController.setOutputRange(kMinOutput, kMaxOutput);

    right_pidController.setP(kP);
    right_pidController.setI(kI);
    right_pidController.setD(kD);
    right_pidController.setIZone(kIz);
    right_pidController.setFF(kFF);
    right_pidController.setOutputRange(kMinOutput, kMaxOutput);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);
  }

  protected void execute() {

    checkSmartDashboardPIDValues();

    moveSpeed = applyDeadband(driverJoystick.getLeftStickY(), joystickThreshold);
    turnSpeed = applyDeadband(driverJoystick.getRightStickX(), joystickThreshold);
    quickTurn = (Robot.oi.driverJoystick.bumperRight.get());

    leftEncodersVelocity  = Robot.Chassis.getAverageLeftNeoVelocity();
    rightEncodersVelocity = Robot.Chassis.getAverageRightNeoVelocity();

    // or should we reference last velocity or last joystick(movespeed) to set this??? TODO:
    if      (moveSpeed >  quickTurnJoystickThreshold) { lastDirection =  1.0; } 
    else if (moveSpeed < -quickTurnJoystickThreshold) { lastDirection = -1.0; }

    // Do cheesy math to calculate left and right drive values (-1 to 1).
    //driveSignal = helper.cheesyDrive(moveSpeed, turnSpeed, quickTurn, true);
    BobDriveHelper.cheesyDrive(moveSpeed, turnSpeed, quickTurn, true);

    if (moveSpeed == 0) {
      if      (turnSpeed > 0) { leftVelocity  = (lastDirection *  turnSpeed); } 
      else if (turnSpeed < 0) { rightVelocity = (lastDirection * -turnSpeed); }

      Chassis.neoDrive.tankDrive(leftVelocity, rightVelocity);
    } 
    else {

      leftVelocity  = BobDriveHelper.getLeft() * maxRPM;
      rightVelocity = BobDriveHelper.getRight() * maxRPM;

      left_pidController.setReference(leftVelocity, ControlType.kVelocity);
      right_pidController.setReference(rightVelocity, ControlType.kVelocity);
    }

    writeToSmartDashboard();

    /**
     * PIDController objects are commanded to a set point using the SetReference()
     * method.
     * 
     * The first parameter is the value of the set point, whose units vary depending
     * on the control type set in the second parameter.
     * 
     * The second parameter is the control type can be set to one of four
     * parameters: 
     * com.revrobotics.ControlType.kDutyCycle
     * com.revrobotics.ControlType.kPosition 
     * com.revrobotics.ControlType.kVelocity
     * com.revrobotics.ControlType.kVoltage
     */
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  private double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  private void checkSmartDashboardPIDValues() {
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to
    // controller
    if ((p != kP)) {
      right_pidController.setP(p);
      left_pidController.setP(p);
      kP = p;
    }
    if ((i != kI)) {
      right_pidController.setI(i);
      left_pidController.setI(i);
      kI = i;
    }
    if ((d != kD)) {
      right_pidController.setD(d);
      left_pidController.setD(d);
      kD = d;
    }
    if ((iz != kIz)) {
      right_pidController.setIZone(iz);
      left_pidController.setIZone(iz);
      kIz = iz;
    }
    if ((ff != kFF)) {
      right_pidController.setFF(ff);
      left_pidController.setFF(ff);
      kFF = ff;
    }
    if ((max != kMaxOutput) || (min != kMinOutput)) {
      right_pidController.setOutputRange(min, max);
      left_pidController.setOutputRange(min, max);
      kMinOutput = min;
      kMaxOutput = max;
    }
  }

  private void writeToSmartDashboard() {
    SmartDashboard.putNumber("Target_leftVelocity", leftVelocity);
    SmartDashboard.putNumber("leftVelocity", leftEncodersVelocity);
    SmartDashboard.putNumber("Output_Right", Robot.Chassis.getAppliedOutputRight());
    SmartDashboard.putNumber("Output_Left", Robot.Chassis.getAppliedOutputLeft());
    SmartDashboard.putNumber("Target_rightVelocity", rightVelocity);
    SmartDashboard.putNumber("rightVelocity", rightEncodersVelocity);
  }
}

/**
 * The RestoreFactoryDefaults method can be used to reset the configuration
 * parameters in the SPARK MAX to their factory default state. If no argument is
 * passed, these parameters will not persist between power cycles
 */
// m_motor.restoreFactoryDefaults();