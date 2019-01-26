package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.OI;
import frc.robot.commands.Arm.armWithJoystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls Four Bar Arm movement using PID setpoints
 * 
 * NOTE: Setpoints set using analog sensor values
 * 
 * For reference, there are 4096 ticks per 360 degree revolution
 * 
 * @category ARM
 * @author Bryce G. Jack E.
 */
public class Arm extends Subsystem {

  /**
   * Specifies whether or not the Arm will be in debug mode.
   * 
   * @see #periodic()
   */
  boolean armDebug = true;

  /* --- CAN ID SETUP --- */
  // Do not update without updating the wiki, too!
  private final static int liftRightID = 4;

  public static TalonSRX armMotor;

  // Configures the maximum/minumum speeds the lift can travel at
  private double maxSpeed = 1;
  private double nominalSpeed = 0;

  // PID Constants - Refer to the Wiki to learn what each of these do
  private double kP = 1;
  private double kI = 0;
  private double kD = 0;
  private double kF = 0;

  // How much the actual position vary from the target 
  private int allowableError = 0;

  /**
   * Sets the imits set in the code where the mechanism cannot go outside of 
   *(in current analog sensor values) 
   * @see #setSoftLimits()
   */
  public static int forwardLiftSoftLimit = 590;
  public static int reverseLiftSoftLimit = 50;

  protected void initDefaultCommand() {
     setDefaultCommand(new armWithJoystick());
  }

  public Arm() {
    
    armMotor = new TalonSRX(liftRightID);
    armMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0); // Analog sensor
    armMotor.setSensorPhase(false);
    armMotor.setInverted(false);
    armMotor.setStatusFramePeriod(0, 0, 0);
    armMotor.setNeutralMode(NeutralMode.Brake);
    
    // Enable/disable soft limits for when the motor is going forwards
    armMotor.configForwardSoftLimitEnable(true, 0);
    // Enable/disable soft limits for when the motor is going backwards
    armMotor.configReverseSoftLimitEnable(true, 0);

    // Sets the soft limits for the lift that were decided above
    setSoftLimits(forwardLiftSoftLimit, reverseLiftSoftLimit);

    /*
     * Set the peak (maximum) and nominal (minimum) output voltages for the motors
     * according to whether they are moving forwards or in reverse
     * 
     * If the motor is given a voltage value below the nominal voltage, or above the peak voltage,
     * it will be bumped up/down to return it to the set nominal voltage.
     */
    armMotor.configPeakOutputForward(maxSpeed, 0); // Forwards
    armMotor.configNominalOutputForward(nominalSpeed, 0);
    armMotor.configPeakOutputReverse(-maxSpeed, 0); // Reverse
    armMotor.configNominalOutputReverse(nominalSpeed, 0);

    /*
     * Sets the allowable closed-loop error, Closed-Loop output will be neutral
     * within this range. See Table in Section 17.2.1 for native units per rotation.
     */
    armMotor.configAllowableClosedloopError(0, allowableError, 0);

    /* set closed loop gains in slot0, typically kF stays zero. */
    armMotor.config_kP(0, kP, 0);
    armMotor.config_kI(0, kI, 0);
    armMotor.config_kD(0, kD, 0);
    armMotor.config_kF(0, kF, 0); 

  }

  /**
   * Sets the setpoint that the arm will move to
   * @param pos Analog position that the arm will move to
   */
  public void setSetpoint(double pos) {
    armMotor.set(ControlMode.Position, pos);
  }

  /**
   * Gets the current setpoint that the arm is currently moving to
   * @return Analog position that the arm is moving to
   */
  public double getSetpoint() {
    return armMotor.getClosedLoopTarget(0);
  }

  /**
   * Gets the current position of the arm
   * @return Analog position that the arm is at
   */
  public double getPosition() {
    return armMotor.getSelectedSensorPosition(0);
  }

  /**
   * Runs the arm by using a given power
   * @param power A decimal value from 1 to -1 to supply power to the arm
   */
  public void move(double power) {
    armMotor.set(ControlMode.PercentOutput, power);
  }

  /**
   * Stops the arm motors
   */
  public void stop() {
    armMotor.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Sets soft limits within the code, defining a range of motion for the motors. 
   * If the sensor value surpasses this range, the code will run the motors in 
   * the opposite direction to get the sensor value to be back within the given range.
   * @param forward The soft limit for when the motor is going forwards
   * @param reverse The soft limit for when the motor is going backwards
   */
  public static void setSoftLimits(int forward, int reverse) {
    armMotor.configForwardSoftLimitThreshold(forward, 0);
    armMotor.configReverseSoftLimitThreshold(reverse, 0);
  }

  /**
   * Runs continuously during runtime. Currently used to display SmartDashboard values
   */
  public void periodic() {
    if (armDebug) {
      SmartDashboard.putNumber("forwardARMSoftLimit", forwardLiftSoftLimit);
      SmartDashboard.putNumber("reverseARMSoftLimit", reverseLiftSoftLimit);
      SmartDashboard.putNumber("Arm setpoint", getSetpoint());
      SmartDashboard.putNumber("Arm position", getPosition());
    }
  }
}
