package frc.robot.subsystems;

import frc.robot.nerdyfiles.NeoNerdyDrive;
import frc.robot.nerdyfiles.TalonNerdyDrive;
import frc.robot.Robot;
import frc.robot.commands.Auto.setpaths.autoSetPath;
import frc.robot.commands.Auto.setpaths.autoSetPathReverse;
import frc.robot.commands.Chassis.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.DigitalInput;
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
  boolean chassisDebug = true;
  boolean neoDebug = true;
  boolean lineSensorDebug = true;

  /* --- Line Sensors --- */
  public DigitalInput lineSensorFront;
  public DigitalInput lineSensorMiddle;
  public DigitalInput lineSensorBack;


  /* --- Drive Motor Declaration --- */
  public TalonSRX leftFrontMotor;
  public VictorSPX leftMidMotor;
  public VictorSPX leftRearMotor;

  public TalonSRX rightFrontMotor;
  public VictorSPX rightMidMotor;
  public VictorSPX rightRearMotor;

  /* --- Talon Drive Motor Declaration --- */
  public CANSparkMax neoLeftFrontMotor;
  // public static CANSparkMax neoLeftMidMotor;
  public CANSparkMax neoLeftRearMotor;

  public CANSparkMax neoRightFrontMotor;
  // public static CANSparkMax neoRightMidMotor;
  public CANSparkMax neoRightRearMotor;

  public static CANEncoder neoLeftFrontEncoder;
  public static CANEncoder neoLeftRearEncoder; 
  public static CANEncoder neoRightFrontEncoder;
  public static CANEncoder neoRightRearEncoder; 

  /* --- Drive Declarations --- */
  public static TalonNerdyDrive talonDrive;
  public static NeoNerdyDrive neoDrive;

  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private final static int rightFrontID = 60;
  private final static int rightRearID = 1;
  private final static int rightEncoderTalonID = 2;
  private final static int leftFrontID = 15;
  private final static int leftRearID = 14;
  // private final static int leftEncoderTalonID = 53; //move 13 into cargo intake

  private final static int talonRightMidID = 31;
  private final static int talonRightRearID = 32;
  private final static int talonLeftMidID = 46;
  private final static int talonLeftRearID = 47;

  public Chassis() {

    lineSensorFront = new DigitalInput(9);
    lineSensorMiddle = new DigitalInput(8);
    lineSensorBack = new DigitalInput(7);

    /*****************************************/
    /* ------------------------------------- */
    /* --- Motor & Sensor Initialization --- */
    /* ------------------------------------- */
    /*****************************************/

    /* --- Talon Drive Left --- */

    // Sets up the left front motor as a Talon with a mag encoder that isn't
    // reversed
    /*
    leftFrontMotor = new TalonSRX(leftEncoderTalonID);
    leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    leftFrontMotor.setSensorPhase(false);

    // Sets up the other left side motors as Victors
    leftMidMotor = new VictorSPX(talonLeftMidID);
    leftRearMotor = new VictorSPX(talonLeftRearID);

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
    rightFrontMotor = new TalonSRX(rightEncoderTalonID);
    rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    rightFrontMotor.setSensorPhase(false);

    // Sets up the other right side motors as Victors
    rightMidMotor = new VictorSPX(talonRightMidID);
    rightRearMotor = new VictorSPX(talonRightRearID);

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
    neoLeftFrontMotor = new CANSparkMax(leftFrontID, MotorType.kBrushless);
    neoLeftRearMotor = new CANSparkMax(leftRearID, MotorType.kBrushless);

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
    neoRightFrontMotor = new CANSparkMax(rightFrontID, MotorType.kBrushless);
    neoRightRearMotor = new CANSparkMax(rightRearID, MotorType.kBrushless);

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
    // talonDrive = new TalonNerdyDrive(leftFrontMotor, rightFrontMotor);

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
   * Manually set the rotational position of the TALON drive encoders 
   * 
   * @param pos Position to set encoders to - in encoder ticks
   */
  public void setEncoders(int pos) {
    rightFrontMotor.setSelectedSensorPosition(pos, 0, 0);
    Robot.CargoIntake.CargoIntakeMotor.setSelectedSensorPosition(-pos, 0, 0);
  }

  /**
   * Talon Methods
   * @return - returns the encoder position on the right encoder
   */
  public double getRightPosition() {
    return rightFrontMotor.getSelectedSensorPosition();
  }

  /**
   * Talon Methods
   * @return - returns the encoders position on the left encoder
   */
  public double getLeftPosition() {
    return Robot.CargoIntake.CargoIntakeMotor.getSelectedSensorPosition();
  }

  /**
   * Talon Method
   * @param moveSpeed - forward speed (-1.0 - 1.0)
   * @param turnSpeed - turn speed (-1.0 - 1.0)
   * @param squaredInputs
   */
  public void neoDriveArcade(double moveSpeed, double turnSpeed, boolean squaredInputs) {
    neoDrive.arcadeDrive(moveSpeed, turnSpeed, squaredInputs);
  }

  /**
   * Talon Method
   * @param moveSpeed - forward speed (-1.0 - 1.0)
   * @param turnSpeed - turn speed (-1.0 - 1.0)
   * @param squaredInputs
   */
  public void driveArcade(double moveSpeed, double turnSpeed, boolean squaredInputs) {
    talonDrive.arcadeDrive(moveSpeed, turnSpeed, squaredInputs);
  }
  
  /**
   * Talon Method
   * @param moveSpeed - forward speed (-1.0 - 1.0)
   * @param turnSpeed - turn speed (-1.0 - 1.0)
   * @param squaredInputs
   */
  public void driveCurvature(double moveSpeed, double turnSpeed, boolean isQuickTurn) {
    talonDrive.curvatureDrive(moveSpeed, turnSpeed, isQuickTurn);
  }
  
  /**
   * Talon Method
   * @param moveSpeed - forward speed (-1.0 - 1.0)
   * @param turnSpeed - turn speed (-1.0 - 1.0)
   * @param squaredInputs
   */
  public void driveTank(double leftSpeed, double rightSpeed, boolean squareInputs) {
    talonDrive.tankDrive(leftSpeed, rightSpeed, squareInputs);
  }
  
  /**
   * Talon Method
   * @param moveSpeed - forward speed (-1.0 - 1.0)
   * @param turnSpeed - turn speed (-1.0 - 1.0)
   * @param squaredInputs
   */
  public void stopDrive() {
    talonDrive.arcadeDrive(0, 0, true);
  }

  /**
   * Manually reset the rotational position of the Talon drive encoders to 0 ticks
   */
  public void resetEncoders() {
    rightFrontMotor.setSelectedSensorPosition(0, 0, 0);
    Robot.CargoIntake.CargoIntakeMotor.setSelectedSensorPosition(0, 0, 0);
  }
  

  /**
   * Determines what the Talon drive motors will do when no signal is given to them
   * 
   * @param mode The braking mode to use
   *             <p>
   *             {@code NeutralMode.Coast} - Allow the robot to roll to a stop
   *             {@code NeutralMode.Brake} - The motors run backwards and attempt
   *             stop the robot sooner
   *             </p>
   */
  public void setBrakeMode(NeutralMode mode) {
    Robot.CargoIntake.CargoIntakeMotor.setNeutralMode(mode);
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
   * Get the average value of both NEO drive side's encoder averages
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
   * Manually set the rotational position of the NEO drive encoders
   * 
   * @param pos The position to set the encoder to (in ticks)
   */
  public void setNeoEncoders(int pos) {
    // As of 1/24/19, no way to set Neo encoder values
  }

  /**
   * Manually reset the rotational position of the NEO drive encoders to 0 ticks
   */
  public void resetNeoEncoders() {
    neoLeftFrontEncoder.setPosition(0);
    neoRightFrontEncoder.setPosition(0);
  }

  /**
   * Determines what the NEO drive motors will do when no signal is given to them
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
    neoLeftRearMotor.setIdleMode(mode);

    neoRightFrontMotor.setIdleMode(mode);
    neoRightRearMotor.setIdleMode(mode);
  }

  public void stopNeoDrive() {
    neoDrive.arcadeDrive(0, 0, false);
  }

  /**
   * Determines what the NEO drive motors will do when no signal is given to them
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
      SmartDashboard.putNumber("Right Encoder Value", getRightPosition()); //rightFrontMotor.getSelectedSensorPosition());
      SmartDashboard.putNumber("Left Encoder Value", getLeftPosition()); //leftFrontMotor.getSelectedSensorPosition());
      SmartDashboard.putNumber("Right encoder dist INCH", (getRightPosition()/13988)*20);
      SmartDashboard.putNumber("Left encoder dist INCH", (getLeftPosition()/13988)*20);

      SmartDashboard.putNumber("leftFront", Robot.CargoIntake.CargoIntakeMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("drive Joystick", Robot.oi.driverJoystick.getRawAxis(1));
      SmartDashboard.putNumber("right Chassis POWER", rightFrontMotor.getMotorOutputPercent());
      SmartDashboard.putNumber("left Chassis POWER", Robot.CargoIntake.CargoIntakeMotor.getMotorOutputPercent());

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

    if(neoDebug) {
      SmartDashboard.putNumber("neoRightEncoder", neoRightFrontEncoder.getPosition());
      SmartDashboard.putNumber("neoLeftEncoder", neoLeftFrontEncoder.getPosition());
      SmartDashboard.putNumber("Neo Right Percent Power", neoRightFrontMotor.get());
      SmartDashboard.putNumber("Neo Left Percent Power", neoLeftFrontMotor.get());
    }

    if(lineSensorDebug) {
      SmartDashboard.putBoolean("Front Line Sensor", lineSensorFront.get());
      SmartDashboard.putBoolean("Mid Line Sensor", lineSensorMiddle.get());
      SmartDashboard.putBoolean("Back Line Sensor", lineSensorBack.get());
    }
  }
}