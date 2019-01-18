package frc.robot.subsystems;

import frc.robot.nerdyfiles.NeoNerdyDrive;
import frc.robot.nerdyfiles.NerdyDrive;
import frc.robot.Robot;
import frc.robot.commands.Chassis.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

  /* --- Talon Drive Motor Declaration --- */
  private static TalonSRX leftFrontMotor;
  private static VictorSPX leftMidMotor;
  private static VictorSPX leftRearMotor;

  private static TalonSRX rightFrontMotor;
  private static VictorSPX rightMidMotor;
  private static VictorSPX rightRearMotor;

  /* --- Talon Drive Motor Declaration --- */
  private static CANSparkMax neoLeftFrontMotor;
  private static CANSparkMax neoLeftMidMotor;
  private static CANSparkMax neoLeftRearMotor;

  private static CANSparkMax neoRightFrontMotor;
  private static CANSparkMax neoRightMidMotor;
  private static CANSparkMax neoRightRearMotor;
  private static CANEncoder leftEncoder;
  private static CANEncoder rightEncoder;

  public static NerdyDrive drive;
  public static NeoNerdyDrive neoDrive;

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

    // Sets up the left side motors as CAN Spark Brushless Motors
    neoLeftFrontMotor = new CANSparkMax(leftFrontID, MotorType.kBrushless);
    neoLeftMidMotor = new CANSparkMax(leftMidID, MotorType.kBrushless);
    neoLeftMidMotor = new CANSparkMax(leftRearID, MotorType.kBrushless);
    leftEncoder = new CANEncoder(neoLeftFrontMotor);

    // Left side motors aren't currently inverted
    neoLeftFrontMotor.setInverted(false);
    neoLeftMidMotor.setInverted(false);
    neoLeftRearMotor.setInverted(false);

    // All left neo motors currently follow the front left motor
    neoLeftMidMotor.follow(neoLeftFrontMotor);
    neoLeftRearMotor.follow(neoLeftFrontMotor);

    ////////////////////////

    /* --- Neo Drive Right --- */

    // Sets up the right side motors as CAN Spark Brushless Motors
    neoRightFrontMotor = new CANSparkMax(rightFrontID, MotorType.kBrushless);
    neoRightMidMotor = new CANSparkMax(rightMidID, MotorType.kBrushless);
    neoRightMidMotor = new CANSparkMax(rightRearID, MotorType.kBrushless);
    rightEncoder = new CANEncoder(neoRightFrontMotor);

    // Right side motors aren't currently inverted
    neoRightFrontMotor.setInverted(true);
    neoRightMidMotor.setInverted(true);
    neoRightRearMotor.setInverted(true);

    // All right neo motors currently follow the front right motor
    neoRightMidMotor.follow(neoRightFrontMotor);
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