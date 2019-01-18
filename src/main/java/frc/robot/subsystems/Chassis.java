package frc.robot.subsystems;

import frc.robot.nerdyfiles.NerdyDrive;
import frc.robot.Robot;
import frc.robot.commands.Auto.pathway;
import frc.robot.commands.Chassis.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

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
  boolean chassisDebug = false;

  /* --- Drive Motor Declaration --- */
  private static TalonSRX leftFrontMotor;
  private static VictorSPX leftMidMotor;
  private static VictorSPX leftRearMotor;

  private static TalonSRX rightFrontMotor;
  private static VictorSPX rightMidMotor;
  private static VictorSPX rightRearMotor;

  public static NerdyDrive drive;

  /* --- Path Weaver Variables --- */ 
  private static final int k_ticks_per_rev = 1024;
  private static final double k_wheel_diameter = 4.0 / 12.0;
  private static final double k_max_velocity = 10;

  private static final int k_left_channel = 0;
  private static final int k_right_channel = 1;

  private static final int k_left_encoder_port_a = 0;
  private static final int k_left_encoder_port_b = 1;
  private static final int k_right_encoder_port_a = 2;
  private static final int k_right_encoder_port_b = 3;

  private static final int k_gyro_port = 0;

  private static final String k_path_name = "example";

  private CANSparkMax m_left_motor;
  private CANSparkMax m_right_motor;

  private Encoder m_left_encoder;
  private Encoder m_right_encoder;

  private AnalogGyro m_gyro;

  private EncoderFollower m_left_follower;
  private EncoderFollower m_right_follower;

  private Notifier m_follower_notifier;

  private pathway pathway;


  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private final static int rightFrontID = 0;
  private final static int rightMidID = 1;
  private final static int rightRearID = 2;
  private final static int leftFrontID = 3;
  private final static int leftMidID = 4;
  private final static int leftRearID = 5;

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

    pathway = new pathway();
  }

  // Sets the default drive command to drive using the joysticks on an XBox 360
  // controller
  public void initDefaultCommand() {
    setDefaultCommand(new driveByJoystick());
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
   * Sets up the motors and sensors and will be used when auton is initilized 
   */
  public void pathWeaverAutoInit() {
    Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
    Trajectory left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");
    Trajectory right_trajectory = Pathfinder.generate(pathway.points, config);//PathfinderFRC.getTrajectory(k_path_name + ".right");

    m_left_follower = new EncoderFollower(left_trajectory);
    m_right_follower = new EncoderFollower(right_trajectory);

    m_left_follower.configureEncoder(m_left_encoder.get(), k_ticks_per_rev, k_wheel_diameter);
    // You must tune the PID values on the following line!
    m_left_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);

    m_right_follower.configureEncoder(m_right_encoder.get(), k_ticks_per_rev, k_wheel_diameter);
    // You must tune the PID values on the following line!
    m_right_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / k_max_velocity, 0);
    
    m_follower_notifier = new Notifier(this::followPath);
    m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
  }

  /**
   * Follow path method used to set the variables for the path
   */
  private void followPath() {
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
      m_follower_notifier.stop();
    } else {
      double left_speed = m_left_follower.calculate(m_left_encoder.get());
      double right_speed = m_right_follower.calculate(m_right_encoder.get());
      double heading = m_gyro.getAngle();
      double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
      double turn =  0.8 * (-1.0/80.0) * heading_difference;
      m_left_motor.set(left_speed + turn);
      m_right_motor.set(right_speed - turn);
    }
  }

  /**
   * Resets all of the path finder values
   */
  public void resetPathFinder() {
    m_follower_notifier.stop();
    m_left_motor.set(0);
    m_right_motor.set(0);
  }

  /**
   * Run continuously during runtime. Currently used to display SmartDashboard
   * values
   */
  public void periodic() {
    if (chassisDebug) {
      SmartDashboard.putNumber("leftFront", leftFrontMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("drive Joystick", Robot.oi.driverJoystick.getRawAxis(1));
      SmartDashboard.putNumber("right Chassis POWER", rightFrontMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("left Chassis POWER", leftFrontMotor.getMotorOutputPercent());
    }
  }
}