package frc.robot.commands.Chassis;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.nerdyfiles.controller.NerdyUltimateXboxDriver;
import frc.robot.subsystems.Chassis;

import frc.robot.nerdyfiles.BobDriveHelper;
import frc.robot.nerdyfiles.DriveSignal;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Uses controller joysticks to drive the robot using ArcadeDrive
 */
public class driveByJoystickVelocity extends Command {

  // Gets the driver joystick from OI.java
  private NerdyUltimateXboxDriver driverJoystick = Robot.oi.driverJoystick;

  double moveSpeed, turnSpeed;
  double leftVelocity, rightVelocity;
  double quickTurnThreshold;
  BobDriveHelper helper;
  double averageVelocity, quickTurnVelocityThreshold;
  double lastDirection;

  private CANPIDController right_pidController, left_pidController;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;

  /**
   * Uses CheeseBob Drive to drive Neo motors based on velocity
   * 
   *
   */
  public driveByJoystickVelocity() {
    requires(Robot.Chassis);
  }

  protected void initialize() {

    quickTurnThreshold = 0.1;
    lastDirection = 1;
    helper = new BobDriveHelper();

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
    maxRPM = 5400; // 5200forward 5400 backwards
    quickTurnVelocityThreshold = 1000;

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

    moveSpeed = applyDeadband(driverJoystick.getLeftStickY(), 0.2);
    turnSpeed = applyDeadband(driverJoystick.getRightStickX(), 0.2);
    // boolean quickTurn = (moveSpeed < quickTurnThreshold && moveSpeed >
    // -quickTurnThreshold);
    boolean quickTurn = (Robot.oi.driverJoystick.bumperRight.get());
    // boolean quickTurn = (moveSpeed == 0);

    if (moveSpeed > 0.4) {lastDirection = 1.0;}
    else if (moveSpeed < -0.4) {lastDirection = -1.0;}

    // Do cheesy math to calculate left and right drive values (-1 to 1).
    DriveSignal driveSignal = helper.cheesyDrive(-moveSpeed, turnSpeed, quickTurn, false);

    if (moveSpeed == 0) {
      if (turnSpeed > 0) {
        leftVelocity = (lastDirection * turnSpeed * maxRPM) + Robot.Chassis.getAverageLeftNeoVelocity();
      } else if (turnSpeed < 0) {
        rightVelocity = (lastDirection * turnSpeed * maxRPM) + Robot.Chassis.getAverageRightNeoVelocity();
      }
    } else {
      leftVelocity = driveSignal.getLeft() * maxRPM;
      rightVelocity = driveSignal.getRight() * maxRPM;
    }
  

  /**
   * PIDController objects are commanded to a set point using the SetReference()
   * method.
   * 
   * The first parameter is the value of the set point, whose units vary depending
   * on the control type set in the second parameter.
   * 
   * The second parameter is the control type can be set to one of four
   * parameters: com.revrobotics.ControlType.kDutyCycle
   * com.revrobotics.ControlType.kPosition com.revrobotics.ControlType.kVelocity
   * com.revrobotics.ControlType.kVoltage
   */

  left_pidController.setReference(leftVelocity,ControlType.kVelocity);
  right_pidController.setReference(rightVelocity,ControlType.kVelocity);

  writeToSmartDashboard();

  // If the robot is driving with Neos, send the values to neoDrive, otherwise,
  // send the values to talonDrive
  // Chassis.neoDrive.arcadeDrive(moveSpeed, turnSpeed, true);
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
    SmartDashboard.putNumber("Target leftVelocity", leftVelocity);
    SmartDashboard.putNumber("leftVelocity", Robot.Chassis.getAverageLeftNeoVelocity());
    SmartDashboard.putNumber("Target rightVelocity", rightVelocity);
    SmartDashboard.putNumber("rightVelocity", Robot.Chassis.getAverageRightNeoVelocity());
  }
}

/**
 * The RestoreFactoryDefaults method can be used to reset the configuration
 * parameters in the SPARK MAX to their factory default state. If no argument is
 * passed, these parameters will not persist between power cycles
 */
// m_motor.restoreFactoryDefaults();