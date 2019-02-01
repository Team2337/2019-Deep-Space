package frc.robot.subsystems;

import frc.robot.nerdyfiles.*;
import frc.robot.nerdyfiles.NeoNerdyDrive;
import frc.robot.nerdyfiles.NerdyDrive;
import frc.robot.nerdyfiles.pathway.EncoderFollower;
import frc.robot.Robot;
import frc.robot.commands.Auto.Pathway;
import frc.robot.commands.Auto.autoSetPath;
import frc.robot.commands.Auto.autoSetPathReverse;
import frc.robot.commands.Chassis.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;

import java.lang.module.ModuleDescriptor.Exports.Modifier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
// import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * The main chassis runtime
 * 
 * @category CHASSIS
 * @author Jack E.
 */
public class Chassis extends Subsystem {

  /**
   * Specifies whether or not the Chassis will be in debug mode.
   * 
   * @see #periodic()
   */
  boolean chassisDebug = true;
  boolean neoDebug = true;
  boolean pathFinderDebug = true;
  boolean pathfinderDebug = false;

  /* --- Drive Motor Declaration --- */
  public TalonSRX leftFrontMotor;
  public VictorSPX leftMidMotor;
  public VictorSPX leftRearMotor;

  public TalonSRX rightFrontMotor;
  public VictorSPX rightMidMotor;
  public VictorSPX rightRearMotor;

  public NerdyDrive nerdyDrive;

  /* --- Path Weaver Variables --- */ 
  private int ticksPerRev =  4096*3; // Gear ratio 
  private double wheelDiameter = 6.0 * 0.0254;
  private double wheelBase = 20.5 * 0.0254; //old practice bot: 21.5
  private double leftOutput, rightOutput, gyro_heading, desired_heading, turn, angleDifference;

  public TankModifier modifier;
  public EncoderFollower rightSideFollower;
  public EncoderFollower leftSideFollower;
  public double commandNum = 0;

  /* --- Talon Drive Motor Declaration --- */
  public static CANSparkMax neoLeftFrontMotor;
  // public static CANSparkMax neoLeftMidMotor;
  public static CANSparkMax neoLeftRearMotor;

  public static CANSparkMax neoRightFrontMotor;
  // public static CANSparkMax neoRightMidMotor;
  public static CANSparkMax neoRightRearMotor;
  public static CANEncoder neoLeftEncoder;
  public static CANEncoder neoRightEncoder;

  public static NerdyDrive drive;
  public static NeoNerdyDrive neoDrive;

  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private final static int rightMidID = 31;
  private final static int rightRearID = 32;
  private final static int leftMidID = 44;
  private final static int leftRearID = 43;
  
  // Encoder Talons / Talon Drive Motors
  private final static int rightFrontID = 2; //30 before
  private final static int leftFrontID = 13; //45 

  private final static int neoRightFrontID = 0;
  private final static int neoRightRearID = 1;
  private final static int neoLeftFrontID = 15;
  private final static int neoLeftRearID = 14;

  public Chassis() {


    /*****************************************/
    /* ------------------------------------- */
    /* --- Motor & Sensor Initialization --- */
    /* ------------------------------------- */
    /*****************************************/

    /* --- Talon Drive Left --- */

    // Sets up the left front motor as a Talon with a mag encoder that isn't
    // reversed
    leftFrontMotor = new TalonSRX(leftFrontID);
    leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    leftFrontMotor.setSensorPhase(false);

    // Sets up the other left side motors as Victors
    leftMidMotor = new VictorSPX(leftMidID);
    leftRearMotor = new VictorSPX(leftRearID);

    // None of the left motors are currently reversed
    leftFrontMotor.setInverted(false);
    leftMidMotor.setInverted(false);
    leftRearMotor.setInverted(false);

    // Sets the rear and mid left motors to do the same thing as the front motor
    leftRearMotor.follow(leftFrontMotor);
    leftMidMotor.follow(leftFrontMotor);

    // Sets the maximum voltage to send to the motor to 12 Volts
    leftFrontMotor.configVoltageCompSaturation(12, 0);
    leftFrontMotor.enableVoltageCompensation(true);

    ////////////////////////

    /* --- Talon Drive Right --- */

    // Sets up the right front motor as a Talon with a mag encoder that isn't
    // reversed
    rightFrontMotor = new TalonSRX(rightFrontID);
    rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    rightFrontMotor.setSensorPhase(false);

    // Sets up the other right side motors as Victors
    rightMidMotor = new VictorSPX(rightMidID);
    rightRearMotor = new VictorSPX(rightRearID);

    // All of the right motors are currently reversed
    rightFrontMotor.setInverted(true);
    rightMidMotor.setInverted(true);
    rightRearMotor.setInverted(true);

    // Sets the rear and mid right motors to do the same thing as the front motor
    rightMidMotor.follow(rightFrontMotor);
    rightRearMotor.follow(rightFrontMotor);

    // Sets the maximum voltage to send to the motor to 12 Volts
    rightFrontMotor.configVoltageCompSaturation(12, 0);
    rightFrontMotor.enableVoltageCompensation(true);

    /////////////////////////

    /* --- Neo Drive Left --- */

    // Sets up the left side motors as CAN SparkMax Brushless Motors
    neoLeftFrontMotor = new CANSparkMax(neoLeftFrontID, MotorType.kBrushless);
    // neoLeftMidMotor = new CANSparkMax(neoLeftMidID, MotorType.kBrushless);
    neoLeftRearMotor = new CANSparkMax(neoLeftRearID, MotorType.kBrushless);

    // Left side Neo encoder
    neoLeftEncoder = new CANEncoder(neoLeftFrontMotor);

    // Left side motors are not currently reversed
    neoLeftFrontMotor.setInverted(false);
    // neoLeftMidMotor.setInverted(false);
    neoLeftRearMotor.setInverted(false);

    // All left Neo motors currently follow the front left motor
    // neoLeftMidMotor.follow(neoLeftFrontMotor);
    neoLeftRearMotor.follow(neoLeftFrontMotor);

    ////////////////////////

    /* --- Neo Drive Right --- */

    // Sets up the right side motors as CAN SparkMax Brushless Motors
    neoRightFrontMotor = new CANSparkMax(neoRightFrontID, MotorType.kBrushless);
    // neoRightMidMotor = new CANSparkMax(neoRightMidID, MotorType.kBrushless);
    neoRightRearMotor = new CANSparkMax(neoRightRearID, MotorType.kBrushless);

    // Right side encoder
    neoRightEncoder = new CANEncoder(neoRightFrontMotor);

    // Right side motors aren't currently reversed
    neoRightFrontMotor.setInverted(true);
    // neoRightMidMotor.setInverted(true);
    neoRightRearMotor.setInverted(true);

    // All right Neo motors currently follow the front right motor
    // neoRightMidMotor.follow(neoRightFrontMotor);
    neoRightRearMotor.follow(neoRightFrontMotor);

    ////////////////////////

    /* --- Talon Nerdy Drive --- */
    drive = new NerdyDrive(leftFrontMotor, rightFrontMotor);

    /* --- Neo Nerdy Drive --- */
    neoDrive = new NeoNerdyDrive(neoLeftFrontMotor, neoRightFrontMotor);
  }
   
  // Sets the default drive command to drive using the joysticks on an XBox 360
  // controller
  public void initDefaultCommand() {
    // Pass the argument "true" to drive with a Neo drivetrain and no arg (or false)
    // to use Talon drive
    setDefaultCommand(new driveByJoystick(true));
  }

  /*****************************************/
  /* ------------------------------------- */
  /* ----------- Talon Methods ----------- */
  /* ------------------------------------- */
  /*****************************************/

  /**
   * Use this in execute 
   * These variables are constantly being updated
   */
  public void makePathForawrd() {
    leftOutput = leftSideFollower.calculate((int)getLeftPosition());
    rightOutput = rightSideFollower.calculate((int)getRightPosition());
    
    gyro_heading = Robot.Pigeon.getYaw();    
    desired_heading = Pathfinder.r2d(leftSideFollower.getHeading()); 

    angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    turn = 0.8 * (-1.0/80.0) * angleDifference;

    neoDrive.tankDrive(leftOutput + turn, rightOutput - turn, false);
  }

  /**
   * Use this in execute 
   * These variables are constantly being updated
   */
  public void makePathReverse() {
    leftOutput = leftSideFollower.calculate(-(int)getLeftPosition());
    rightOutput = rightSideFollower.calculate(-(int)getRightPosition());
    
    gyro_heading = -Robot.Pigeon.getYaw();    
    desired_heading = Pathfinder.r2d(leftSideFollower.getHeading()); 

    angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    //0.8 * (-1.0/80.0) * angleDifference
    turn = 1.6 * (-1.0/80.0) * angleDifference;
    
    neoDrive.tankDrive(-(leftOutput + turn), -(rightOutput - turn), false);
  }

 

  public void setTrajectory(Trajectory trajectory, double kP, double kI, double kD, double kA) {
    pathfinderDebug = true;
    modifier = new TankModifier(trajectory).modify(wheelBase);

    leftSideFollower = new EncoderFollower(modifier.getLeftTrajectory());
    rightSideFollower = new EncoderFollower(modifier.getRightTrajectory());

    leftSideFollower.configurePIDVA(kP, kI, kD, 1 / Pathway.config.max_velocity, kA);
    rightSideFollower.configurePIDVA(kP, kI, kD, 1 / Pathway.config.max_velocity, kA);

    leftSideFollower.configureEncoder((int)getLeftPosition(), ticksPerRev, wheelDiameter);
    rightSideFollower.configureEncoder((int)getRightPosition(), ticksPerRev, wheelDiameter);
  }

  /**
   * Manually set the rotational position of the drive encoders - UNTESTED
   * 
   * @param pos Position to set encoders to - in encoder ticks
   */
  public void setEncoders(int pos) {
    rightFrontMotor.setSelectedSensorPosition(pos, 0, 0);
    leftFrontMotor.setSelectedSensorPosition(-pos, 0, 0);
  }

  /**
   * Talon Encoder Values
   * @return - returns the encoder position on the right encoder
   */
  public double getRightPosition() {
    return -rightFrontMotor.getSelectedSensorPosition();
  }

  /**
   * Talon Encoder Values
   * @return - returns the encoders position on the left encoder
   */
  public double getLeftPosition() {
    return leftFrontMotor.getSelectedSensorPosition();
  }

  /**
   * @param moveSpeed - forward speed (-1.0 - 1.0)
   * @param turnSpeed - turn speed (-1.0 - 1.0)
   * @param squaredInputs
   */
  public void driveArcade(double moveSpeed, double turnSpeed, boolean squaredInputs) {
    this.nerdyDrive.arcadeDrive(moveSpeed, turnSpeed, squaredInputs);
  }
  
  /**
   * @param moveSpeed - forward speed (-1.0 - 1.0)
   * @param turnSpeed - turn speed (-1.0 - 1.0)
   * @param squaredInputs
   */
  public void driveCurvature(double moveSpeed, double turnSpeed, boolean isQuickTurn) {
    this.nerdyDrive.curvatureDrive(moveSpeed, turnSpeed, isQuickTurn);
  }
  
  /**
   * @param moveSpeed - forward speed (-1.0 - 1.0)
   * @param turnSpeed - turn speed (-1.0 - 1.0)
   * @param squaredInputs
   */
  public void driveTank(double leftSpeed, double rightSpeed, boolean squareInputs) {
    this.nerdyDrive.tankDrive(leftSpeed, rightSpeed, squareInputs);
  }
  
  /**
   * @param moveSpeed - forward speed (-1.0 - 1.0)
   * @param turnSpeed - turn speed (-1.0 - 1.0)
   * @param squaredInputs
   */
  public void stopDrive() {
    this.nerdyDrive.arcadeDrive(0, 0, true);
  }

  /**
   * Manually reset the rotational position of the drive encoders to 0 ticks
   */
  public void resetEncoders() {
    rightFrontMotor.setSelectedSensorPosition(0, 0, 0);
    leftFrontMotor.setSelectedSensorPosition(0, 0, 0);
  }
  

  /**
   * Determines what the Talon drive motors will do when no signal is given to them
   * 
   * @param mode The breaking mode to use
   *             <p>
   *             {@code NeutralMode.Coast} - Allow the robot to roll to a stop
   *             {@code NeutralMode.Break} - The motors run backwards and attempt
   *             stop the robot sooner
   *             </p>
   */
  public void setBrakeMode(NeutralMode mode) {
    leftFrontMotor.setNeutralMode(mode);
    rightFrontMotor.setNeutralMode(mode);
    leftMidMotor.setNeutralMode(mode);
    rightMidMotor.setNeutralMode(mode);
    leftRearMotor.setNeutralMode(mode);
    rightRearMotor.setNeutralMode(mode);
  }

  /*****************************************/
  /* ------------------------------------- */
  /* ------------ Neo Methods ------------ */
  /* ------------------------------------- */
  /*****************************************/

  /**
   * Get the average value of the two drive encoders
   * 
   * @return The average of the two drive encoder values
   */
  public double getAverageNeoEncoder() {
    return (neoRightEncoder.getPosition() + neoLeftEncoder.getPosition()) / 2;
  }

  /**
   * Manually set the rotational position of the drive encoders
   * 
   * @param pos Position to set encoders to - in encoder ticks
   */
  public void setNeoEncoders(int pos) {
    // As of 1/24/19, no way to set Neo encoder values
  }

  /**
   * Manually reset the rotational position of the drive encoders to 0 ticks
   */
  public void resetNeoEncoders() {
    // As of 1/24/19, no way to set Neo encoder values
  }

  /**
   * Determines what the drive motors will do when no signal is given to them
   * 
   * @param mode The breaking mode to use
   *             <p>
   *             {@code IdleMode.kCoast} - Allow the robot to roll to a stop
   *             {@code IdleMode.kBrake} - The motors run backwards and attempt
   *             stop the robot sooner
   *             </p>
   */
  public void setAllNeoBrakeMode(IdleMode mode) {
    neoLeftFrontMotor.setIdleMode(mode);
    // neoLeftMidMotor.setIdleMode(mode);
    neoLeftRearMotor.setIdleMode(mode);

    neoRightFrontMotor.setIdleMode(mode);
    // neoRightMidMotor.setIdleMode(mode);
    neoRightRearMotor.setIdleMode(mode);
  }

  /**
   * Determines what the drive motors will do when no signal is given to them
   * 
   * @param motor A CANSparkMax motor to assign a breakmode to
   * @param mode  The breaking mode to use
   *              <p>
   *              {@code IdleMode.kCoast} - Allow the robot to roll to a stop
   *              {@code IdleMode.kBreak} - The motors run backwards and attempt
   *              stop the robot sooner
   *              </p>
   */
  public void setSingleNeoBreakMode(CANSparkMax motor, IdleMode mode) {
    motor.setIdleMode(mode);
  }

  /**
   * Runs continuously during runtime. Currently used to display SmartDashboard
   * values
   */
  public void periodic() {
    if (chassisDebug) {
      // ***** FIX FOR COMP BOT ********
      // ***** SENSOR PHASE NOT INVERTING ******
      SmartDashboard.putNumber("Right Encoder Value", getRightPosition()); //rightFrontMotor.getSelectedSensorPosition());
      SmartDashboard.putNumber("Left Encoder Value", getLeftPosition()); //leftFrontMotor.getSelectedSensorPosition());
      SmartDashboard.putNumber("leftFront", leftFrontMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("drive Joystick", Robot.oi.driverJoystick.getRawAxis(1));
      SmartDashboard.putNumber("right Chassis POWER", rightFrontMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("left Chassis POWER", leftFrontMotor.getMotorOutputPercent());

      SmartDashboard.putNumber("Command Number", commandNum);

      SmartDashboard.putNumber("Auto P Input", autoSetPath.kP);
      SmartDashboard.putNumber("Auto I Input", autoSetPath.kI);
      SmartDashboard.putNumber("Auto D Input", autoSetPath.kD);
      SmartDashboard.putNumber("Auto A Input", autoSetPath.kP);
      SmartDashboard.putNumber("Reverse Auto P Input", autoSetPathReverse.kP);
      SmartDashboard.putNumber("Reverse Auto I Input", autoSetPathReverse.kI);
      SmartDashboard.putNumber("Reverse Auto D Input", autoSetPathReverse.kD);
      SmartDashboard.putNumber("Reverse Auto A Input", autoSetPathReverse.kP);

      SmartDashboard.putNumber("printX", autoSetPath.printX);
    }

    if(pathFinderDebug) {
      SmartDashboard.putNumber("RightVelocity", rightFrontMotor.getSelectedSensorVelocity());
      SmartDashboard.putNumber("LeftVelocity", leftFrontMotor.getSelectedSensorVelocity());
      SmartDashboard.putNumber("Turn Value", this.turn);
      SmartDashboard.putNumber("AngleDifferance", this.angleDifference);
      SmartDashboard.putNumber("leftOutput", this.leftOutput);
      SmartDashboard.putNumber("rightOutput", this.rightOutput);
    }

    if(neoDebug) {
      SmartDashboard.putNumber("neoRightEncoder", neoRightEncoder.getPosition());
      SmartDashboard.putNumber("neoLeftEncoder", neoLeftEncoder.getPosition());
      SmartDashboard.putNumber("Neo Right Percent Power", neoRightFrontMotor.get());
      SmartDashboard.putNumber("Neo Left Percent Power", neoLeftFrontMotor.get());
    }

    if(pathfinderDebug) {
      // SmartDashboard.putNumber("Encoder Follower: last_error", EncoderFollower.last_error);
      // SmartDashboard.putNumber("Encoder Follower: key", EncoderFollower.segment);
      // SmartDashboard.putNumber("Encoder Follower: Start position", EncoderFollower.encoder_offset);
      // SmartDashboard.putNumber("Encoder Follower: kp", EncoderFollower.kp);
      // SmartDashboard.putNumber("Encoder Follower: LEFT calculated_value", leftSideFollower.calculated_value);
      // SmartDashboard.putNumber("Encoder Follower: RIGHT calculated_value", rightSideFollower.calculated_value);
      SmartDashboard.putNumber("Encoder Follower: LEFT error", leftSideFollower.error);
      SmartDashboard.putNumber("Encoder Follower: RIGHT error", rightSideFollower.error);
      SmartDashboard.putNumber("Encoder Follower: seg.position", rightSideFollower.seg.position);
      // SmartDashboard.putNumber("Encoder Follower: distance_covered", EncoderFollower.distance_covered);
      // SmartDashboard.putNumber("Encoder Follower: LEFT error", getLeftPosition() - leftSideFollower.calculated_value);
      // SmartDashboard.putNumber("Encoder Follower: RIGHT error", getRightPosition() - rightSideFollower.calculated_value);
      }
  }
}