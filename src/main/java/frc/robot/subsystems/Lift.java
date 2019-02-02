package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.commands.Lift.liftWithJoystick;
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
  boolean liftDebug = false;

  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private final static int liftRightFrontID = 8;
  private final static int liftRightBackID = 60;
  private final static int liftLeftFrontID = 9;
  private final static int liftLeftBackID = 61;

  /**
   * The front right motor is the master for the other three as they will all
   * receive the same commands
   */
  public static TalonSRX liftRightFrontMotor;
  
  /* --- Follower Victors --- */
  public static VictorSPX liftRightBackMotor;
  public static VictorSPX liftLeftFrontMotor;
  public static VictorSPX liftLeftBackMotor;

  // Configures the maximum/minumum speeds the lift can travel at
  private double maxSpeedUp = 0.8;
  private double maxSpeedDown = 0.5;
  private double nominalSpeed = 0;

  // PID Constants - Refer to the Wiki to learn what each of these do
  private double kP = 3;
  private double kI = 0;
  private double kD = 0;
  private double kF = 0;

  // How much the actual position may vary from the set target position (in
  // encoder ticks)
  private int allowableError = 0;

  /**
   * Sets the boundary of where the mechanism cannot go outside of (in current
   * analog sensor (in this case a stringpot) values)
   * 
   * @see #setSoftLimits()
   */
  public static int forwardLiftSoftLimit = 500;
  public static int reverseLiftSoftLimit = 100;

  protected void initDefaultCommand() {
    setDefaultCommand(new liftWithJoystick());
  }

  public Lift() {

    /*
     * Configurations for the right motors, set up to follow the motions of the
     * right front motor
     */
    liftRightFrontMotor = new TalonSRX(liftRightFrontID);
    liftRightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0); // Typically a stringpot
    liftRightFrontMotor.setSensorPhase(false);
    liftRightFrontMotor.setInverted(false);
    liftRightFrontMotor.setStatusFramePeriod(0, 0, 0);
    liftRightFrontMotor.setNeutralMode(NeutralMode.Brake);

    liftRightBackMotor = new VictorSPX(liftRightBackID);
    liftRightBackMotor.follow(liftRightFrontMotor);
    liftRightBackMotor.setInverted(false);
    liftRightBackMotor.setNeutralMode(NeutralMode.Brake);

    /*
     * Configurations for the left motors, set up to follow the motions of the right
     * front motor
     */
    liftLeftFrontMotor = new VictorSPX(liftLeftFrontID);
    liftLeftFrontMotor.follow(liftRightFrontMotor);
    liftLeftFrontMotor.setInverted(true);
    liftLeftFrontMotor.setNeutralMode(NeutralMode.Brake);

    liftLeftBackMotor = new VictorSPX(liftLeftBackID);
    liftLeftBackMotor.follow(liftRightFrontMotor);
    liftLeftBackMotor.setInverted(true);
    liftLeftBackMotor.setNeutralMode(NeutralMode.Brake);

    // Enable/disable soft limits for when the motor is going forwards
    liftRightFrontMotor.configForwardSoftLimitEnable(true, 0);
    liftLeftFrontMotor.configForwardSoftLimitEnable(false, 0);

    // Enable/disable soft limits for when the motor is going backwards
    liftRightFrontMotor.configReverseSoftLimitEnable(true, 0);
    liftLeftFrontMotor.configReverseSoftLimitEnable(false, 0);

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
    liftRightFrontMotor.configPeakOutputForward(maxSpeedUp, 0); // Forwards
    liftRightFrontMotor.configNominalOutputForward(nominalSpeed, 0);
    liftRightFrontMotor.configPeakOutputReverse(-maxSpeedDown, 0); // Reverse
    liftRightFrontMotor.configNominalOutputReverse(nominalSpeed, 0);

    liftRightBackMotor.configPeakOutputForward(maxSpeedUp, 0); // Forwards
    liftRightBackMotor.configNominalOutputForward(nominalSpeed, 0);
    liftRightBackMotor.configPeakOutputReverse(-maxSpeedDown, 0); // Reverse
    liftRightBackMotor.configNominalOutputReverse(nominalSpeed, 0);

    /*
     * Sets the allowable closed-loop error, the motor output will be neutral within
     * this range (causing it to break or coast)
     */
    liftRightFrontMotor.configAllowableClosedloopError(0, allowableError, 0);

    /* Set closed loop gains in slot0, typically kF stays zero. */
    liftRightFrontMotor.config_kP(0, kP, 0);
    liftRightFrontMotor.config_kI(0, kI, 0);
    liftRightFrontMotor.config_kD(0, kD, 0);
    liftRightFrontMotor.config_kF(0, kF, 0);

  }

  /**
   * Sets the setpoint that the lift will move to
   * 
   * @param pos Analog position that the arm will move to
   */
  public void setSetpoint(double pos) {
    liftRightFrontMotor.set(ControlMode.Position, pos);
  }

  /**
   * Gets the current setpoint that the lift is currently moving to
   * 
   * @return Analog position that the arm is moving to
   */
  public double getSetpoint() {
    return liftRightFrontMotor.getClosedLoopTarget(0);
  }

  /**
   * Gets the current position of the lift
   * 
   * @return Analog position that the arm is at
   */
  public double getPosition() {
    return liftRightFrontMotor.getSelectedSensorPosition(0);
  }

  /**
   * Runs the lift by using a given power
   * 
   * @param power A decimal value from 1 to -1 to supply power to the lift
   */
  public void move(double power) {
    liftRightFrontMotor.set(ControlMode.PercentOutput, power);
  }

  /**
   * Stops the lift motors
   */
  public void stop() {
    liftRightFrontMotor.set(ControlMode.PercentOutput, 0);
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

    liftRightFrontMotor.configForwardSoftLimitThreshold(forwardLiftSoftLimit, 0);
    liftRightFrontMotor.configReverseSoftLimitThreshold(reverseLiftSoftLimit, 0);
  }

  /**
   * Disables the software limitations for all lift motors
   */
  public void disableSoftLimits() {
    liftRightFrontMotor.configForwardSoftLimitEnable(false);
    liftRightFrontMotor.configReverseSoftLimitEnable(false);
  }

  /**
   * Enables the software limitations for all lift motors
   */
  public void enableSoftLimits() {
    liftRightFrontMotor.configForwardSoftLimitEnable(true);
    liftRightFrontMotor.configReverseSoftLimitEnable(true);
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
      SmartDashboard.putNumber("percentoutput", liftRightFrontMotor.getMotorOutputPercent());
    }
  }
}
