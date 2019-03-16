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
import com.revrobotics.CANSparkMaxLowLevel.ConfigParameter;
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
  boolean chassisDebug = false;
  boolean neoDebug = true;
  boolean pathFinderDebug = false;

  public double jumpModifier = 0.8;

  public boolean print = false, crossedLine = false;
  public int encoderTicks = 0, linesCrossed = 0;

  public DigitalInput autoLineSensor;

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

  public int stallLimit = 40; //in amps - Used in shifterLowGear
  public int currentLimit = 60;//80; //in amps
  public int rpmLimit = 10;

  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private static int rightFrontID;
  private static int rightRearID;
  // private static int rightEncoderTalonID;
  private static int leftFrontID;
  private static int leftRearID;
  private static int leftEncoderTalonID;

  // private static int talonRightMidID;
  // private static int talonRightRearID;
  private static int talonLeftMidID;
  private static int talonLeftRearID;

  /* --- Dashboard Arrays  --- */	
  public static double[] velocities;	
  public static double[] positions;	
  public static double[] ctreEncoders;	
  public static double[] neoEncoders;	
  public static double[] leftPosVelOut;	
  public static double[] rightPosVelOut, neoCurrentRight, neoCurrentLeft;

  public Chassis() {
    rightFrontID = Robot.Constants.chassisRightFrontID;
    rightRearID = Robot.Constants.chassisRightRearID;
    // rightEncoderTalonID = Robot.Constants.cargoIntakeID;
    leftFrontID = Robot.Constants.chassisFrontLeftID;
    leftRearID = Robot.Constants.chassisRearLeftID;
    leftEncoderTalonID = Robot.Constants.wranglerDriveID;

    // talonRightMidID = Robot.Constants.chassisTalonRightMidID;
    // talonRightRearID = Robot.Constants.chassisTalonRightRearID;
    talonLeftMidID = Robot.Constants.chassisTalonLeftMidID;
    talonLeftRearID = Robot.Constants.chassisTalonLeftRearID;

    autoLineSensor = new DigitalInput(Robot.Constants.autoLineSensorID);

    /*****************************************/
    /* ------------------------------------- */
    /* --- Motor & Sensor Initialization --- */
    /* ------------------------------------- */
    /*****************************************/

    /* --- Talon Drive Left --- */

    // Sets up the left front motor as a Talon with a mag encoder that isn't
    // reversed
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
    /*
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

    // Sets up the left side motors as CAN SparkMax Brushless Motors`
    neoLeftFrontMotor = new CANSparkMax(leftFrontID, MotorType.kBrushless);
    neoLeftRearMotor = new CANSparkMax(leftRearID, MotorType.kBrushless);

    // Left side Neo encoder
    neoLeftFrontEncoder = new CANEncoder(neoLeftFrontMotor);
    neoLeftRearEncoder = new CANEncoder(neoLeftRearMotor);

    neoLeftFrontMotor.setSmartCurrentLimit(stallLimit, currentLimit, rpmLimit);
    neoLeftRearMotor.setSmartCurrentLimit(stallLimit, currentLimit, rpmLimit);

    neoLeftRearMotor.clearFaults();

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

    neoRightFrontMotor.setSmartCurrentLimit(stallLimit, currentLimit, rpmLimit);
    neoRightRearMotor.setSmartCurrentLimit(stallLimit, currentLimit, rpmLimit);
    

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
   * Manually set the rotational position of the TALON drive encoders
   * 
   * @param pos Position to set encoders to - in encoder ticks
   */
  public void setEncoders(int pos) {
    Robot.CargoIntake.CargoIntakeMotor.setSelectedSensorPosition(pos, 0, 0);
    leftFrontMotor.setSelectedSensorPosition(-pos, 0, 0);
  }

  /**
   * Talon Methods
   * 
   * @return - returns the encoder position on the right encoder
   */
  public double getRightPosition() {
    return -Robot.CargoIntake.CargoIntakeMotor.getSelectedSensorPosition();
  }

  /**
   * Talon Methods
   * 
   * @return - returns the encoders position on the left encoder
   */
  public double getLeftPosition() {
    return leftFrontMotor.getSelectedSensorPosition();
  }

  public double getAverageEncoderPosition() {
    return (getLeftPosition() + getRightPosition()) /2;
  }

  /**
   * Talon Method
   * 
   * Drives the robot using arcade drive
   * @param moveSpeed     - forward speed (-1.0 - 1.0)
   * @param turnSpeed     - turn speed (-1.0 - 1.0)
   * @param squaredInputs - boolean value determining if the inputs are squared
   */
  public void driveArcade(double moveSpeed, double turnSpeed, boolean squaredInputs) {
    talonDrive.arcadeDrive(moveSpeed, turnSpeed, squaredInputs);
  }

  /**
   * Talon Method
   * 
   * Drives the robot chassis motors using curvature drive
   * - Does calculations to curve 
   * @param moveSpeed     - forward speed (-1.0 - 1.0)
   * @param turnSpeed     - turn speed (-1.0 - 1.0)
   * @param squaredInputs - boolean value determining if the inputs are squared
   */
  public void driveCurvature(double moveSpeed, double turnSpeed, boolean isQuickTurn) {
    talonDrive.curvatureDrive(moveSpeed, turnSpeed, isQuickTurn);
  }

  /**
   * Talon Method
   * 
   * Drives the robot using tank drive
   * @param moveSpeed     - forward speed (-1.0 - 1.0)
   * @param turnSpeed     - turn speed (-1.0 - 1.0)
   * @param squaredInputs - boolean value determining if the inputs are squared
   */
  public void driveTank(double leftSpeed, double rightSpeed, boolean squareInputs) {
    talonDrive.tankDrive(leftSpeed, rightSpeed, squareInputs);
  }

  /**
   * Talon Method
   * 
   * Stops the drive
   */
  public void stopDrive() {
    talonDrive.arcadeDrive(0, 0, true);
  }

  /**
   * Manually reset the rotational position of the Talon drive encoders to 0 ticks
   */
  public void resetEncoders() {
    Robot.CargoIntake.CargoIntakeMotor.setSelectedSensorPosition(0, 0, 0);
    leftFrontMotor.setSelectedSensorPosition(0, 0, 0);
  }

  /**
   * Determines what the Talon drive motors will do when no signal is given to
   * them
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
    Robot.CargoIntake.CargoIntakeMotor.setNeutralMode(mode);
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

  public void setNeoCurrentLimits(int stallLimit, int currentLimit, int rpmLimit) {
    neoLeftFrontMotor.setSmartCurrentLimit(stallLimit, currentLimit, rpmLimit);
    neoLeftRearMotor.setSmartCurrentLimit(stallLimit, currentLimit, rpmLimit);
    neoRightFrontMotor.setSmartCurrentLimit(stallLimit, currentLimit, rpmLimit);
    neoRightRearMotor.setSmartCurrentLimit(stallLimit, currentLimit, rpmLimit);
  }

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
   * Get the average Velocity value of both NEO drive side's encoder averages
   * 
   * @return The average Velocity of both drive side's encoder averages
   */
  public double getAverageNeoVelocity() {
    return (getAverageLeftNeoVelocity() + getAverageRightNeoVelocity()) / 2;
  }

  /**
   * Get the average Velocity of all the right side neo drive encoders
   * 
   * @return The average rotational Velocity of the drive's right side (in ticks)
   */
  public double getAverageRightNeoVelocity() {
    return (neoRightFrontEncoder.getVelocity() + neoRightRearEncoder.getVelocity()) /2;
  }

  /**
   * Get the average Velocity of all the right side neo drive encoders
   * 
   * @return The average rotational Velocity of the drive's right side (in ticks)
   */
  public double getAverageLeftNeoVelocity() {
    return (neoLeftFrontEncoder.getVelocity() + neoLeftRearEncoder.getVelocity()) /2;
  }

  /**
   * Neo arcade drive
   * @param moveSpeed - forward and reverse speed (positive forward, negative left)
   * @param turnSpeed - left to right speed (positive right, negative left)
   * @param squaredInputs - value to set square the inputs
   */
  public static void neoArcade(double moveSpeed, double turnSpeed, boolean squaredInputs) {
    neoDrive.arcadeDrive(moveSpeed, turnSpeed, squaredInputs);
  }

  /**
   * Manually set the rotational position of the NEO drive encoders
   * 
   * @param pos The position to set the encoder to (in ticks)
   */
  public void setNeoEncoders(int pos) {
    neoLeftFrontEncoder.setPosition(pos);
    neoRightFrontEncoder.setPosition(pos);
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

  public double getAppliedOutputLeft() {
    return neoLeftFrontMotor.getAppliedOutput();
  }
  public double getAppliedOutputRight() {
    return neoRightFrontMotor.getAppliedOutput();
  }

  /**
   * Runs continuously during runtime. Currently used to display SmartDashboard
   * values
   */
  public void periodic() {
    if (chassisDebug) {
      neoEncoders = new double[] {getAverageLeftNeoEncoder(), getAverageRightNeoEncoder()};	      
      SmartDashboard.putNumber("Right Encoder Value", getRightPosition()); 
      SmartDashboard.putNumber("Right_Encoder_Value", getRightPosition()); 
      SmartDashboard.putNumber("Left_Encoder_Value", getLeftPosition());     
      SmartDashboard.putNumber("drive Joystick", Robot.oi.driverJoystick.getRawAxis(1));
      SmartDashboard.putNumber("Left_Chassis_POWER", leftFrontMotor.getMotorOutputPercent());	      
      SmartDashboard.putNumber("Drive_Joystick", Robot.oi.driverJoystick.getRawAxis(1));	

      SmartDashboard.putNumber("Auto P Input", autoSetPath.kP);
      SmartDashboard.putNumber("Auto_P_Input", autoSetPath.kP);	      SmartDashboard.putNumber("Auto I Input", autoSetPath.kI);
      SmartDashboard.putNumber("Auto_I_Input", autoSetPath.kI);	      SmartDashboard.putNumber("Auto D Input", autoSetPath.kD);
      SmartDashboard.putNumber("Auto_D_Input", autoSetPath.kD);	      SmartDashboard.putNumber("Auto A Input", autoSetPath.kP);
      SmartDashboard.putNumber("Auto_A_Input", autoSetPath.kP);	      SmartDashboard.putNumber("Reverse Auto P Input", autoSetPathReverse.kP);

      SmartDashboard.putNumber("Reverse Auto I Input", autoSetPathReverse.kI);
      SmartDashboard.putNumber("Reverse_Auto_P_Input", autoSetPathReverse.kP);	      SmartDashboard.putNumber("Reverse Auto D Input", autoSetPathReverse.kD);
      SmartDashboard.putNumber("Reverse_Auto_I_Input", autoSetPathReverse.kI);	      SmartDashboard.putNumber("Reverse Auto A Input", autoSetPathReverse.kP);
      SmartDashboard.putNumber("Reverse_Auto_D_Input", autoSetPathReverse.kD);	
      SmartDashboard.putNumber("Reverse_Auto_A_Input", autoSetPathReverse.kP);	


       SmartDashboard.putNumber("printX", autoSetPath.printX);	      SmartDashboard.putNumber("printX", autoSetPath.printX);
       ctreEncoders = new double[] {getLeftPosition(), getRightPosition()};
       SmartDashboard.putNumberArray("CTRE_Encoder_Positions", ctreEncoders);

    }

    if (neoDebug) {
      SmartDashboard.putNumber("neoRightEncoder", neoRightFrontEncoder.getPosition());
      SmartDashboard.putNumber("neoLeftEncoder", neoLeftFrontEncoder.getPosition());
      SmartDashboard.putNumber("Neo Right Percent Power", neoRightFrontMotor.get());
      SmartDashboard.putNumber("Neo Left Percent Power", neoLeftFrontMotor.get());
      velocities = new double[] {getAverageLeftNeoVelocity(), getAverageRightNeoVelocity()};
      leftPosVelOut =  new double[] {getLeftPosition(), getAverageLeftNeoVelocity(), neoLeftFrontMotor.getOutputCurrent()};
      rightPosVelOut =  new double[] {getRightPosition(), getAverageRightNeoVelocity(), neoRightFrontMotor.getOutputCurrent()};
      neoEncoders = new double[] {neoLeftFrontEncoder.getPosition(), neoRightFrontEncoder.getPosition()};
      neoCurrentRight = new double[] {neoRightFrontMotor.getOutputCurrent(), neoRightRearMotor.getOutputCurrent()};
      neoCurrentLeft = new double[] {neoLeftFrontMotor.getOutputCurrent(), neoLeftRearMotor.getOutputCurrent()};
      SmartDashboard.putNumber("neo_Right_Encoder", neoRightFrontEncoder.getPosition());	
      SmartDashboard.putNumber("neo_Left_Encoder", neoLeftFrontEncoder.getPosition());	
      SmartDashboard.putNumber("Neo_Right_Percent_Power", neoRightFrontMotor.get());	
      SmartDashboard.putNumber("Neo_Left_Percent_Power", neoLeftFrontMotor.get());	
      SmartDashboard.putNumber("Neo_Right_Temperature", neoRightFrontMotor.getMotorTemperature());	
      SmartDashboard.putNumber("Neo_Left_Temperature", neoLeftFrontMotor.getMotorTemperature());	
      SmartDashboard.putNumber("Neo_Right_Current", neoRightFrontMotor.getOutputCurrent());	
      SmartDashboard.putNumber("Neo_Left_Current", neoLeftFrontMotor.getOutputCurrent());	
      SmartDashboard.putNumber("Neo_Right_Encoder_Velocity", neoRightFrontEncoder.getVelocity());	
      SmartDashboard.putNumber("Neo_Left_Encoder_Velocity", neoLeftFrontEncoder.getVelocity());	

      
      SmartDashboard.putNumberArray("Neo_Current_Left", neoCurrentLeft);
      SmartDashboard.putNumberArray("Neo_Current_Right", neoCurrentRight);
      
      SmartDashboard.putNumber("Neo_Current_Left_Front", neoLeftFrontMotor.getOutputCurrent());
      SmartDashboard.putNumber("Neo_Current_Right_Front", neoRightFrontMotor.getOutputCurrent());
      SmartDashboard.putNumber("Neo_Current_Left_Rear", neoLeftRearMotor.getOutputCurrent());
      SmartDashboard.putNumber("Neo_Current_Right_Rear", neoRightRearMotor.getOutputCurrent());

      SmartDashboard.putNumberArray("Neo_Velocities", velocities);	
      SmartDashboard.putNumberArray("Neo_Positions", neoEncoders);	

      SmartDashboard.putNumberArray("Neo_left_graph", leftPosVelOut);	
      SmartDashboard.putNumberArray("Neo_right_graph", rightPosVelOut);
    }
  }
}