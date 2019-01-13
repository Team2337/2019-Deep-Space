package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
//import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.Chassis.*;
import frc.robot.nerdyfiles.NerdyDrive;


/**
 * The main chassis runtime
 * 
 * @category CHASSIS
 * @author Team2337 - EngiNERDs
 */
public class Chassis extends Subsystem {

  //DECLERATIONS

private TalonSRX leftFront, rightFront;  
private VictorSPX leftMiddle, leftRear, rightMiddle, rightRear;
private NerdyDrive nerdyDrive;

  //Motor Controller Ports
private Integer leftFrontMotorPort   = 15;
private Integer leftMiddleMotorPort  = 14;
private Integer leftReadMotorPort    = 13;
private Integer rightFrontMotorPort  = 0;
private Integer rightMiddleMotorPort = 1;
private Integer rightRearMotorPort   = 2;

// CONSTRUCTOR

public Chassis() {

    // VARIABLE SETTING(s)

  /* --- Drive Left --- */
  leftFront = new TalonSRX(leftFrontMotorPort);  //chassisLeftFront
  leftMiddle = new VictorSPX(leftMiddleMotorPort);
  leftRear = new VictorSPX(leftReadMotorPort);

  leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
  leftFront.setSensorPhase(false);

  leftFront.setInverted(false);
  leftMiddle.setInverted(false);
  leftRear.setInverted(false);

  leftMiddle.follow(leftFront);
  leftRear.follow(leftFront);

		/* --- Drive Right --- */
		rightFront = new TalonSRX(rightFrontMotorPort);
		rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		rightFront.setSensorPhase(false);

		rightMiddle = new VictorSPX(rightMiddleMotorPort); 
		rightRear = new VictorSPX(rightRearMotorPort); 

		rightFront.setInverted(true);
		rightMiddle.setInverted(true);
		rightRear.setInverted(true);

		rightMiddle.follow(rightFront);
    rightRear.follow(rightFront);
    
    // Set Voltage Compensation on front motors.  Middle and rear are followers so they don't need it.

    rightFront.configVoltageCompSaturation(12, 0);
		rightFront.enableVoltageCompensation(true);
		
		leftFront.configVoltageCompSaturation(12, 0);
		leftFront.enableVoltageCompensation(true);
  
  nerdyDrive = new NerdyDrive(leftFront,rightFront);
}

  // Set the default command for a subsystem here, if desired.

@Override
public void initDefaultCommand() {
  // setDefaultCommand(new MySpecialCommand());
  setDefaultCommand(new driveByJoystick());
}

  // Put methods for controlling this subsystem here. Call these from Commands.

public void driveArcade(double moveSpeed, double turnSpeed, boolean squaredInputs) {
  this.nerdyDrive.arcadeDrive(moveSpeed, turnSpeed, squaredInputs);
}
public void driveCurvature(double moveSpeed, double turnSpeed, boolean isQuickTurn) {
  this.nerdyDrive.curvatureDrive(moveSpeed, turnSpeed, isQuickTurn);
}
public void driveTank(double leftSpeed, double rightSpeed, boolean squareInputs) {
  this.nerdyDrive.tankDrive(leftSpeed, rightSpeed, squareInputs);
}
public void stopDrive() {
  this.nerdyDrive.arcadeDrive(0, 0, true);
}
public void setEncoders(int pos){     
  rightFront.setSelectedSensorPosition(pos, 0, 0);
  leftFront.setSelectedSensorPosition(-pos, 0, 0);
}
public void resetEncoders(){
  rightFront.setSelectedSensorPosition(0, 0, 0);
  leftFront.setSelectedSensorPosition(0, 0, 0);
}

public void setBrakeMode(NeutralMode mode) {
  leftFront.setNeutralMode(mode);
  rightFront.setNeutralMode(mode);
  leftMiddle.setNeutralMode(mode);
  rightMiddle.setNeutralMode(mode);
  leftRear.setNeutralMode(mode);
  rightRear.setNeutralMode(mode);
}

/*
public void periodic() {
  if (RobotMap.chassisDebug) {
  SmartDashboard.putNumber("leftFront", RobotMap.chassis_leftFront.getMotorOutputPercent());
  SmartDashboard.putNumber("drive Joystick", Robot.oi.driverJoystick.getRawAxis(1));
  SmartDashboard.putNumber("right Chassis POWER", RobotMap.chassis_rightFront.getMotorOutputPercent());
  SmartDashboard.putNumber("left Chassis POWER", RobotMap.chassis_leftFront.getMotorOutputPercent());
  }


*/

}

  //  Other possible speedcontrollers:
  // Victor             import edu.wpi.first.wpilibj.  (use wpi differential drive)
  // VictorSP
  // Spark
  // SparkMax           import ????
  // WPI_VictorSPX      import com.ctre.phoenix.motorcontrol.can.
  // WPI_TalonSRX
  // VictorSPX
  // TalonSRX
