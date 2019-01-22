package frc.robot.subsystems;

import frc.robot.NerdyFiles.NerdyDrive;
import frc.robot.Robot;
import frc.robot.commands.Auto.Pathway;
import frc.robot.commands.Chassis.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;

import java.lang.module.ModuleDescriptor.Exports.Modifier;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * The main chassis runtime
 * 
 * @category CHASSIS
 * @author Team2337 - EngiNERDs
 */
public class Chassis extends Subsystem {

  /**
   * Specifies whether or not the Chassis will be in debug mode.
   * 
   * @see #periodic()
   */
  boolean chassisDebug = true;

  /* --- Drive Motor Declaration --- */
  public TalonSRX leftFrontMotor;
  public VictorSPX leftMidMotor;
  public VictorSPX leftRearMotor;

  public TalonSRX rightFrontMotor;
  public VictorSPX rightMidMotor;
  public VictorSPX rightRearMotor;

  public static NerdyDrive drive;

  /* --- Path Weaver Variables --- */ 
  private int ticksPerRev =  4096*3; // Gear ratio 
  private double wheelDiameter = 6.0 * 0.0254;
  private double kP = 1.3; //2 - working P
  private double kI = 0;
  private double kD = 0.15;
  private double kAccelerationGain = 0;
  private double wheelBase = 21.5 * 0.0254;
  private double timeStep = 0.02;
  private double leftOutput, rightOutput, gyro_heading, desired_heading, turn, angleDifference;

  public TankModifier modifier;
  public Trajectory.Config config;
  public Trajectory trajectory;
  public EncoderFollower rightSideFollower;
  public EncoderFollower leftSideFollower;
  public Waypoint[] points;


  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private final static int rightFrontID = 0;
  private final static int rightMidID = 1;
  private final static int rightRearID = 2;
  private final static int leftFrontID = 15;
  private final static int leftMidID = 14;
  private final static int leftRearID = 13;

  public Chassis() {


    /*****************************************/
    /* ------------------------------------- */
    /* --- Motor & Sensor Initialization --- */
    /* ------------------------------------- */
    /*****************************************/

    /* --- Drive Left --- */

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

    /* --- Drive Right --- */

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

    /* --- Nerdy Drive --- */
    drive = new NerdyDrive(leftFrontMotor, rightFrontMotor);

    /* --- Pathfinder code --- */

   

  }
   
  // Sets the default drive command to drive using the joysticks on an XBox 360
  // controller
  public void initDefaultCommand() {
    setDefaultCommand(new driveByJoystick());
  }

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
    
    drive.tankDrive(leftOutput + turn, rightOutput - turn, false);
  }

  /**
   * Use this in execute 
   * These variables are constantly being updated
   */
  public void makePathReverse() {
    leftOutput = leftSideFollower.calculate(-(int)getLeftPosition());
    rightOutput = rightSideFollower.calculate(-(int)getRightPosition());
    
    gyro_heading = Robot.Pigeon.getYaw();    
    desired_heading = Pathfinder.r2d(leftSideFollower.getHeading()); 

    angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
    turn = 0.8 * (-1.0/80.0) * angleDifference;
    
    drive.tankDrive(-(leftOutput + turn), -(rightOutput - turn), false);
  }

  public void setWaypoints(Waypoint[] points) {
    this.points = points;
  }
  /**
   * Converts the waypoints to generate the path into values readable by the code
   * @param points - array of waypoints
   */
  public Trajectory wayPoints(Waypoint[] points) {
    //old = 1.7, 2.0, 60.0
    //new = 4.267, 5.0, 150.6
    //2,1.75,10
    // FIRST S CURVE -- config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2, 1.9, 10.0);
    // 1.7
    config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2, 1.9, 10.0);
    
    trajectory = Pathfinder.generate(points, config);

    return trajectory;
  }

  public void setTrajectory(Trajectory trajectory) {
    modifier = new TankModifier(trajectory).modify(wheelBase);

    leftSideFollower = new EncoderFollower(modifier.getLeftTrajectory());
    rightSideFollower = new EncoderFollower(modifier.getRightTrajectory());

    leftSideFollower.configurePIDVA(kP, kI, kD, 1 / config.max_velocity, kAccelerationGain);
    rightSideFollower.configurePIDVA(kP, kI, kD, 1 / config.max_velocity, kAccelerationGain);

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

  public double getRightPosition() {
    return rightFrontMotor.getSelectedSensorPosition();
  }

  public double getLeftPosition() {
    return leftFrontMotor.getSelectedSensorPosition();
  }

  /**
   * Manually reset the rotational position of the drive encoders to 0 ticks
   */
  public void resetEncoders() {
    rightFrontMotor.setSelectedSensorPosition(0, 0, 0);
    leftFrontMotor.setSelectedSensorPosition(0, 0, 0);
  }
  

  /**
   * Determines what the drive motors will do when no signal is given to them
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

  /**
   * Run continuously during runtime. Currently used to display SmartDashboard
   * values
   */
  public void periodic() {
    if (chassisDebug) {
      SmartDashboard.putNumber("Right Encoder Value", rightFrontMotor.getSelectedSensorPosition());
      SmartDashboard.putNumber("Left Encoder Value", leftFrontMotor.getSelectedSensorPosition());
      SmartDashboard.putNumber("leftFront", leftFrontMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("drive Joystick", Robot.oi.driverJoystick.getRawAxis(1));
      SmartDashboard.putNumber("right Chassis POWER", rightFrontMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("left Chassis POWER", leftFrontMotor.getMotorOutputPercent());

      SmartDashboard.putNumber("Right Encoder Velocity", rightFrontMotor.getSelectedSensorVelocity());
      SmartDashboard.putNumber("Left Encoder Velocity", leftFrontMotor.getSelectedSensorVelocity());
      SmartDashboard.putNumber("Turn Value", this.turn);
      SmartDashboard.putNumber("Angle Differance", this.angleDifference);
      SmartDashboard.putNumber("leftOutput", this.leftOutput);
      SmartDashboard.putNumber("rightOutput", this.rightOutput);
    }
  }
}