package frc.robot.subsystems;

import frc.robot.nerdyfiles.NeoNerdyDrive;
import frc.robot.nerdyfiles.TalonNerdyDrive;
import frc.robot.Robot;
import frc.robot.commands.Chassis.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
  boolean chassisDebug = false;

  /* --- Talon Drive Motor Declaration --- */
  private static TalonSRX leftFrontMotor;
  private static VictorSPX leftMidMotor;
  private static VictorSPX leftRearMotor;

  private static TalonSRX rightFrontMotor;
  private static VictorSPX rightMidMotor;
  private static VictorSPX rightRearMotor;

  /* --- Neo Drive Motor Declaration --- */
  private static CANSparkMax neoLeftFrontMotor;
  private static CANSparkMax neoLeftRearMotor;
  private static CANEncoder neoLeftFrontEncoder;
  private static CANEncoder neoLeftRearEncoder;

  private static CANSparkMax neoRightFrontMotor;
  private static CANSparkMax neoRightRearMotor;
  private static CANEncoder neoRightFrontEncoder;
  private static CANEncoder neoRightRearEncoder;

  /* --- Drive Declarations --- */
  public static TalonNerdyDrive talonDrive;
  public static NeoNerdyDrive neoDrive;

  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private final static int rightFrontID = 0;
  private final static int rightMidID = 1;
  private final static int rightRearID = 2;
  private final static int leftFrontID = 15;
  private final static int leftMidID = 14;
  private final static int leftRearID = 13;

  private final static int neoRightFrontID = 30;
  private final static int neoRightRearID = 31;
  private final static int neoLeftFrontID = 45;
  private final static int neoLeftRearID = 46;

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
    neoLeftRearMotor = new CANSparkMax(neoLeftRearID, MotorType.kBrushless);

    // Left side Neo encoder
    neoLeftFrontEncoder = new CANEncoder(neoLeftFrontMotor);
    neoLeftRearEncoder = new CANEncoder(neoLeftRearMotor);

    // Left side motors are not currently reversed
    neoLeftFrontMotor.setInverted(false);
    neoLeftRearMotor.setInverted(false);

    // The rear left neo motor follows the front left one
    neoLeftRearMotor.follow(neoLeftFrontMotor);

    // By default, the drive motors will coast to a stop
    neoLeftFrontMotor.setIdleMode(IdleMode.kCoast);
    neoLeftRearMotor.setIdleMode(IdleMode.kCoast);

    ////////////////////////

    /* --- Neo Drive Right --- */

    // Sets up the right side motors as CAN SparkMax Brushless Motors
    neoRightFrontMotor = new CANSparkMax(neoRightFrontID, MotorType.kBrushless);
    neoRightRearMotor = new CANSparkMax(neoRightRearID, MotorType.kBrushless);

    // Right side encoders
    neoRightFrontEncoder = new CANEncoder(neoRightFrontMotor);
    neoRightRearEncoder = new CANEncoder(neoRightRearMotor);

    // Right side motors aren't currently reversed
    neoRightFrontMotor.setInverted(true);
    neoRightRearMotor.setInverted(true);

    // The rear right neo motor follows the front right one
    neoRightRearMotor.follow(neoRightFrontMotor);

    // By default, the drive motors will coast to a stop
    neoRightFrontMotor.setIdleMode(IdleMode.kCoast);
    neoRightRearMotor.setIdleMode(IdleMode.kCoast);

    ////////////////////////

    /* --- Talon Nerdy Drive --- */
    talonDrive = new TalonNerdyDrive(leftFrontMotor, rightFrontMotor);

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
   * Manually set the rotational position of the drive encoders
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
   * @param mode The braking mode to use
   *             <p>
   *             {@code NeutralMode.Coast} - Allow the robot to roll to a stop
   *             {@code NeutralMode.Brake} - The motors run backwards and attempt
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
   * Get the average value of both drive side's encoder averages
   * 
   * @return The average of both drive side's encoder averages
   */
  public double getAverageNeoEncoder() {
    return (getAverageLeftNeoEncoder() + getAverageRightNeoEncoder()) / 2;
  }

  /**
   * Get the average position of all the right side neo drive encoders
   * 
   * @return The average rotational position of the drive's right side (in ticks)
   */
  public double getAverageRightNeoEncoder() {
    return neoRightFrontEncoder.getPosition() + neoRightRearEncoder.getPosition();
  }

  /**
   * Get the average position of all the right side neo drive encoders
   * 
   * @return The average rotational position of the drive's right side (in ticks)
   */
  public double getAverageLeftNeoEncoder() {
    return neoLeftFrontEncoder.getPosition() + neoLeftRearEncoder.getPosition();
  }

  /**
   * Manually set the rotational position of the drive encoders
   * 
   * @param pos The position to set the encoder to (in ticks)
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
   *             {@code IdleMode.kBreak} - The motors run backwards and attempt
   *             stop the robot sooner
   *             </p>
   */
  public void setAllNeoBrakeMode(IdleMode mode) {
    neoLeftFrontMotor.setIdleMode(mode);
    neoLeftRearMotor.setIdleMode(mode);

    neoRightFrontMotor.setIdleMode(mode);
    neoRightRearMotor.setIdleMode(mode);
  }

  /**
   * Determines what the drive motors will do when no signal is given to them
   * 
   * @param motor A CANSparkMax motor to assign a breakmode to
   * @param mode  The braking mode to use
   *              <p>
   *              {@code IdleMode.kCoast} - Allow the robot to roll to a stop
   *              {@code IdleMode.kBrake} - The motors run backwards and attempt
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
      SmartDashboard.putNumber("leftFront", leftFrontMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("drive Joystick", Robot.oi.driverJoystick.getRawAxis(1));
      SmartDashboard.putNumber("right Chassis POWER", rightFrontMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("left Chassis POWER", leftFrontMotor.getMotorOutputPercent());
    }
  }
}