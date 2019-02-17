package frc.robot.subsystems;

import frc.robot.nerdyfiles.TalonNerdyDrive;
import frc.robot.nerdyfiles.TalonSRXPID;
import frc.robot.Robot;
import frc.robot.commands.Chassis.*;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The main chassis runtime
 * 
 * @category CHASSIS
 * @author 
 */
public class Chassis extends Subsystem {

  /**
   * Specifies whether or not the Chassis will be in debug mode.
   * 
   * @see #periodic()
   */
  boolean chassisDebug = false;

  /* --- Talon Drive Motor Declaration --- */
  private static TalonSRXPID leftFrontMotor;
  private static VictorSPX leftMidMotor;
  private static VictorSPX leftRearMotor;

  private static TalonSRXPID rightFrontMotor;
  private static VictorSPX rightMidMotor;
  private static VictorSPX rightRearMotor;

  /* --- Drive Declarations --- */
  public static TalonNerdyDrive talonDrive;

  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private final static int rightFrontID = 0;
  private final static int leftFrontID = 13;

  private final static int talonRightMidID = 1;
  private final static int talonRightRearID = 2;
  private final static int talonLeftMidID = 14;
  private final static int talonLeftRearID = 15;

  private static PIDController ChassisPID;

  public Chassis() {

    /*****************************************/
    /* ------------------------------------- */
    /* --- Motor & Sensor Initialization --- */
    /* ------------------------------------- */
    /*****************************************/

    /* --- Talon Drive Left --- */

    // Sets up the left front motor as a Talon with a mag encoder that isn't
    // reversed
    leftFrontMotor = new TalonSRXPID(leftFrontID);
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
    rightFrontMotor = new TalonSRXPID(rightFrontID);
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



    /* --- Talon Nerdy Drive --- */
    talonDrive = new TalonNerdyDrive(leftFrontMotor, rightFrontMotor);

    ChassisPID = new PIDController(1, 0, 0 ,0, rightFrontMotor, rightFrontMotor);
    LiveWindow.addActuator("chassis", "pid", ChassisPID);
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