package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls lift movement using PID setpoints
 * 
 * NOTE: Setpoints are all in encoder ticks
 * 
 * For reference, there are 4096 ticks per 360 degree revolution
 * 
 * @category LIFT
 * @author Bryce G. Jack E.
 */
public class Lift extends Subsystem {

  /**
   * Specifies whether or not the Chassis will be in debug mode.
   * 
   * @see #periodic()
   */
  boolean liftDebug = false;

  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private final static int liftRightID = 8;
  private final static int liftLeftID = 9;

  /*
   * The left motor is a victor, since its only going to follow the right This
   * means that we only need to send commands to the right motor
   */
  public static TalonSRX liftRightMotor;
  public static VictorSPX liftLeftMotor;

  // Configures the maximum/minumum speeds the lift can travel at
  private double maxSpeedUp = 0.8;
  private double maxSpeedDown = 0.5;
  private double nominalSpeed = 0;

  // PID Constants - Refer to the Wiki to learn what each of these do
  private double kP = 14;
  private double kI = 0;
  private double kD = 0;
  private double kF = 0;

  // How much the actual position vary from the target (in encoder ticks)
  private int allowableError = 0;

  /**
   * Gives the values for the lifts softlimits - purley referance right now
   * 
   * @see #setSoftLimits()
   */
  public static int forwardLiftSoftLimit = 590;
  public static int reverseLiftSoftLimit = 40;

  protected void initDefaultCommand() {
     setDefaultCommand(new liftWithJoystick());
  }

  public Lift() {
    // Refer to Chassis.java for information on what this block does
    liftRightMotor = new TalonSRX(liftRightID);
    liftRightMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0); // String Potentiometer
    liftRightMotor.setSensorPhase(false);
    liftRightMotor.setInverted(false);
    liftRightMotor.setStatusFramePeriod(0, 0, 0);
    liftRightMotor.setNeutralMode(NeutralMode.Brake);

    liftLeftMotor = new VictorSPX(liftLeftID);
    liftLeftMotor.follow(liftRightMotor);
    liftLeftMotor.setInverted(true);
    liftLeftMotor.setNeutralMode(NeutralMode.Brake);

    // Enable/disable soft limits for when the motor is going forwards
    liftRightMotor.configForwardSoftLimitEnable(false, 0);
    liftLeftMotor.configForwardSoftLimitEnable(false, 0);

    // Enable/disable soft limits for when the motor is going backwards
    liftRightMotor.configReverseSoftLimitEnable(false, 0);
    liftLeftMotor.configReverseSoftLimitEnable(false, 0);

    // Sets the soft limits for the lift that were decided above
    setSoftLimits(forwardLiftSoftLimit, reverseLiftSoftLimit);

    /*
     * Set the peak (maximum) and nominal (minimum) output voltages for the motors
     * according to whether they are moving forwards or in reverse
     * 
     * If the motor is given below the nominal voltage, it will be bumped up and
     * vice versa for the peak voltage
     */
    liftRightMotor.configPeakOutputForward(maxSpeedUp, 0); // Forwards
    liftRightMotor.configNominalOutputForward(nominalSpeed, 0);
    liftRightMotor.configPeakOutputReverse(-maxSpeedDown, 0); // Reverse
    liftRightMotor.configNominalOutputReverse(nominalSpeed, 0);

    /*
     * Sets the allowable closed-loop error, Closed-Loop output will be neutral
     * within this range. See Table in Section 17.2.1 for native units per rotation.
     */
    liftRightMotor.configAllowableClosedloopError(0, allowableError, 0);

    /* set closed loop gains in slot0, typically kF stays zero. */
    liftRightMotor.config_kP(0, kP, 0);
    liftRightMotor.config_kI(0, kI, 0);
    liftRightMotor.config_kD(0, kD, 0);
    liftRightMotor.config_kF(0, kF, 0); // Shifter

  }

  /**
   * Sets the setpoint that the lift will move to
   * 
   * @param pos Number of encoder ticks (Stringpot) that the lift will move to
   */
  public void setSetpoint(double pos) {
    liftRightMotor.set(ControlMode.Position, pos);
  }

  /**
   * Gets the current setpoint that the lift is currently moving to
   * 
   * @return Number of encoder ticks (Stringpot) that the lift is moving to
   */
  public double getSetpoint() {
    return liftRightMotor.getClosedLoopTarget(0);
  }

  /**
   * Gets the current position of the lift
   * 
   * @return Number of encoder ticks (Stringpot) that the lift is at
   */
  public double getPosition() {
    return liftRightMotor.getSelectedSensorPosition(0);
  }

  /**
   * Runs the lift by using a given power
   * 
   * @param power A decimal value from 1 to -1 to supply to the lift
   */
  public void move(double power) {
    liftRightMotor.set(ControlMode.PercentOutput, power);
  }

  /**
   * Stops the lift
   */
  public void stop() {
    liftRightMotor.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Sets the SOFTware limits for the motors. After they surpass these limits, the
   * motors will default to their NormalModes, which could be either be to coast
   * to a stop, or (more likely) to brake as quickly as possible
   * 
   * @param forward The soft limit for when the motor is going forwards
   * @param reverse The soft limit for when the motor is going backwards
   */
  public static void setSoftLimits(int forward, int reverse) {
    forwardLiftSoftLimit = forward;
    reverseLiftSoftLimit = reverse;

    liftRightMotor.configForwardSoftLimitThreshold(forwardLiftSoftLimit, 0);
    // leftFront.configForwardSoftLimitThreshold(forwardLIFTSoftLimit, 0);

    liftRightMotor.configReverseSoftLimitThreshold(reverseLiftSoftLimit, 0);
    // leftFront.configReverseSoftLimitThreshold(reverseLIFTSoftLimit, 0);
  }

  /**
   * Runs continuously during runtime. Currently used to display SmartDashboard
   * values
   */
  public void periodic() {
    if (liftDebug) {
      SmartDashboard.putNumber("forwardLIFTSoftLimit", forwardLiftSoftLimit);
      SmartDashboard.putNumber("reverseLIFTSoftLimit", reverseLiftSoftLimit);
    }
  }
}
