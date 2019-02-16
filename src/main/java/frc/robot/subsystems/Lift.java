package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls lift movement using PID setpoints
 * 
 * @category LIFT
 * @author Bryce G. Tyler G.
 */
public class Lift extends Subsystem {

  /**
   * Specifies whether or not the Lift will be in debug mode.
   * 
   * @see #periodic()
   */
  boolean liftDebug = true;

  public double currentPosition;

  // Position to score in the low rocket
  public double cargoLowScorePosition = 150; // 201
  // Position to score in the mid rocket
  public double cargoMidScorePosition = 703;
  // Position to score in the cargo ship
  public double cargoShipScorePosition = 469;// 225;
  // Position to allow the escalator to feed a ball into the trolley
  public double cargoIntakePosition = 150;
  // Position to store the cargo after loaded but before scoring
  public double cargoLoadedPosition = 150; //208
  // Position to eject the cargo ball (if applicable) - to be used if we are mid
  // and need to eject the ball, it would be faster than to go through the robot
  public double cargoEjectPosition = 500;

  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private final static int liftRightFrontID = 4;
  private final static int liftRightBackID = 5;
  private final static int liftLeftFrontID = 10;
  private final static int liftLeftBackID = 11;

  /**
   * The front right motor is the master for the other three as they will all
   * receive the same commands
   */
  public static TalonSRX liftLeftFrontMotor;

  /* --- Follower Victors --- */
  public static VictorSPX liftRightFrontMotor;
  public static VictorSPX liftRightBackMotor;
  public static VictorSPX liftLeftBackMotor;

  // Configures the maximum/minumum speeds the lift can travel at
  private double maxSpeedUp = 1.0; // 0.8
  private double maxSpeedDown = 1.0; // 0.5
  private double nominalSpeed = 0;

  // PID Constants - Refer to the Wiki to learn what each of these do
  private double kP = 14;
  private double kI = 0;
  private double kD = 0;
  private double kF = 0;

  // How much the actual position may vary from the set target position (in
  // current analog sensor (in this case a stringpot) values)
  public int allowableError = 5;

  /**
   * Sets the boundary of where the mechanism cannot go outside of (in current
   * analog sensor (in this case a stringpot) values)
   * 
   * @see #setSoftLimits()
   */
  public static int forwardLiftSoftLimit = 700;
  public static int reverseLiftSoftLimit = 125;

  // min and max bounds the string pot can go to
  public int maxValue = 730;
  public int minValue = 40;

  protected void initDefaultCommand() {
    // setDefaultCommand(new goToPosition(currentPosition));
  }

  public Lift() {

    /*
     * Configurations for the left motors, set up to follow the motions of the right
     * front motor
     */
    liftLeftFrontMotor = new TalonSRX(liftLeftFrontID);
    liftLeftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0); // Typically a stringpot
    liftLeftFrontMotor.setSensorPhase(true);
    liftLeftFrontMotor.setInverted(true);
    liftLeftFrontMotor.setStatusFramePeriod(0, 0, 0);
    liftLeftFrontMotor.setNeutralMode(NeutralMode.Brake);

    liftLeftBackMotor = new VictorSPX(liftLeftBackID);
    liftLeftBackMotor.follow(liftLeftFrontMotor);
    liftLeftBackMotor.setInverted(true);
    liftLeftBackMotor.setNeutralMode(NeutralMode.Brake);

    /*
     * Configurations for the right motors, set up to follow the motions of the
     * right front motor
     */
    liftRightFrontMotor = new VictorSPX(liftRightFrontID);
    liftRightFrontMotor.follow(liftLeftFrontMotor);
    liftRightFrontMotor.setInverted(false);
    liftRightFrontMotor.setNeutralMode(NeutralMode.Brake);

    liftRightBackMotor = new VictorSPX(liftRightBackID);
    liftRightBackMotor.follow(liftLeftFrontMotor);
    liftRightBackMotor.setInverted(false);
    liftRightBackMotor.setNeutralMode(NeutralMode.Brake);

    // Enable/disable soft limits for when the motor is going forwards
    liftLeftFrontMotor.configForwardSoftLimitEnable(true, 0);

    // Enable/disable soft limits for when the motor is going backwards
    liftLeftFrontMotor.configReverseSoftLimitEnable(true, 0);

    // Sets the soft limits for the lift that were decided above
    setSoftLimits(forwardLiftSoftLimit, reverseLiftSoftLimit);

    /*
     * Set the peak (maximum) and nominal (minimum) output voltages for the motors
     * according to whether they are moving forward or in reverse
     * 
     * If the motor is given a voltage value below the nominal voltage, or above the
     * peak voltage, it will be bumped up/down to return it to the set nominal
     * voltage.
     */
    liftLeftFrontMotor.configPeakOutputForward(maxSpeedUp, 0); // Forwards
    liftLeftFrontMotor.configNominalOutputForward(nominalSpeed, 0);
    liftLeftFrontMotor.configPeakOutputReverse(-maxSpeedDown, 0); // Reverse
    liftLeftFrontMotor.configNominalOutputReverse(nominalSpeed, 0);

    /*
     * Sets the allowable closed-loop error, the motor output will be neutral within
     * this range (causing it to break or coast)
     */
    liftLeftFrontMotor.configAllowableClosedloopError(0, allowableError, 0);

    /* Set closed loop gains in slot0, typically kF stays zero. */
    liftLeftFrontMotor.config_kP(0, kP, 0);
    liftLeftFrontMotor.config_kI(0, kI, 0);
    liftLeftFrontMotor.config_kD(0, kD, 0);
    liftLeftFrontMotor.config_kF(0, kF, 0);

    currentPosition = getPosition();

  }

  /**
   * Sets the setpoint that the lift will move to
   * 
   * @param pos Analog position that the arm will move to
   */
  public void setSetpoint(double pos) {
    liftLeftFrontMotor.set(ControlMode.Position, pos);
  }

  /**
   * Gets the current setpoint that the lift is currently moving to
   * 
   * @return Analog position that the arm is moving to
   */
  public double getSetpoint() {
    return liftLeftFrontMotor.getClosedLoopTarget(0);
  }

  /**
   * 
   * @param tolerance
   * @return
   */
  public boolean atPosition(double tolerance) {
    return Math.abs(getSetpoint() - getPosition()) <= tolerance;
  }

  /**
   * 
   */
  public boolean atCargoLowPosition(double tolerance) {
    return Math.abs(cargoLowScorePosition - getPosition()) <= tolerance;
  }

  /**
  * 
  */
  public boolean atCargoMidPosition(double tolerance) {
    return Math.abs(cargoMidScorePosition - getPosition()) <= tolerance;
  }

  /**
  * 
  */
  public boolean atCargoShipPosition(double tolerance) {
    return Math.abs(cargoShipScorePosition - getPosition()) <= tolerance;
  }

  /**
  * Sets the tolerance when the lift is at the intake position
  */
  public boolean atCargoIntakePosition(double tolerance) {
    return Math.abs(cargoIntakePosition - getPosition()) <= tolerance;
  }

  /**
  * 
  */
  public boolean atCargoEjectPosition(double tolerance) {
    return Math.abs(cargoEjectPosition - getPosition()) <= tolerance;
  }

  /**
   * Gets the current position of the lift
   * 
   * @return Analog position that the arm is at
   */
  public double getPosition() {
    return liftLeftFrontMotor.getSelectedSensorPosition(0);
  }

  /**
   * Sets the minimum, maximum, and nominal speeds of all lift motors, with the
   * nominal speed set to 0
   * 
   * @param up   A decimal value from 0 to 1 to set the maximum lift speed to
   * @param down A decimal value from -1 to 0 to set the minimum lift speed to
   */
  public void setMinMaxSpeed(double up, double down) {
    double nominal = 0.0;
    setMinMaxSpeed(up, down, nominal);
  }

  /**
   * Sets the minimum, maximum, and nominal speeds of all lift motors
   * 
   * @param up      A decimal value from 0 to 1 to set the maximum lift speed to
   * @param down    A decimal value from -1 to 0 to set the minimum lift speed to
   * @param nominal A decimal value from 0 to 1 to set the nominal lift speed to -
   *                for both up and down
   */
  public void setMinMaxSpeed(double up, double down, double nominal) {
    liftLeftFrontMotor.configPeakOutputForward(up, 0); // Forwards
    liftLeftFrontMotor.configNominalOutputForward(nominal, 0);
    liftLeftFrontMotor.configPeakOutputReverse(down, 0); // Reverse
    liftLeftFrontMotor.configNominalOutputReverse(-nominal, 0);
  }

  /**
   * Runs the lift by using a given power
   * 
   * @param power A decimal value from -1 to 1 to supply power to the lift
   */
  public void move(double power) {
    liftLeftFrontMotor.set(ControlMode.PercentOutput, power);
  }

  /**
   * Stops the lift motors
   */
  public void stop() {
    liftLeftFrontMotor.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Sets soft limits within the code, defining a range of motion for the motors.
   * If the sensor value surpasses this range, the code will run the motors in the
   * opposite direction to get the sensor value to be back within the given range.
   * 
   * @param forward The soft limit for when the motor is going forwards
   * @param reverse The soft limit for when the motor is going backwards
   */
  public static void setSoftLimits(int forward, int reverse) {
    forwardLiftSoftLimit = forward;
    reverseLiftSoftLimit = reverse;

    liftLeftFrontMotor.configForwardSoftLimitThreshold(forwardLiftSoftLimit, 0);
    liftLeftFrontMotor.configReverseSoftLimitThreshold(reverseLiftSoftLimit, 0);
  }

  /**
   * Disables the software limitations for all lift motors
   */
  public void disableSoftLimits() {
    liftLeftFrontMotor.configForwardSoftLimitEnable(false);
    liftLeftFrontMotor.configReverseSoftLimitEnable(false);
  }

  /**
   * Enables the software limitations for all lift motors
   */
  public void enableSoftLimits() {
    liftLeftFrontMotor.configForwardSoftLimitEnable(true);
    liftLeftFrontMotor.configReverseSoftLimitEnable(true);
  }

  /**
   * Runs continuously during runtime. Currently used to display SmartDashboard
   * values
   */
  public void periodic() {
    if (liftDebug) {
      SmartDashboard.putNumber("forwardLIFTSoftLimit", forwardLiftSoftLimit);
      SmartDashboard.putNumber("reverseLIFTSoftLimit", reverseLiftSoftLimit);
      SmartDashboard.putNumber("StringPot", getPosition());
      SmartDashboard.putNumber("SetPoint", getSetpoint());
      SmartDashboard.putNumber("percentoutput", liftLeftFrontMotor.getMotorOutputPercent());
    }
  }
}
