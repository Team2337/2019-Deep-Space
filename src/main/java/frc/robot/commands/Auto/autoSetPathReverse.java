package frc.robot.commands.Auto;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * This command is mainly a placeholder command, but it can be used
 * functionally. It does just as it says: nothing.
 */
public class autoSetPathReverse extends Command {

  /* --- Path Weaver Variables --- */

  public TankModifier modifier;
  public Trajectory.Config config;
  public Trajectory trajectory;
  public EncoderFollower rightSideFollower;
  public EncoderFollower leftSideFollower;

  private double kP;
  private double kI;
  private double kD;
  private double kA;

  private double currentRightPos, currentLeftPos, rightTarget, leftTarget, rightThreshold, leftThreshold, timeout;

  // CONSTRUCTOR
  public autoSetPathReverse(Trajectory trajectoryIn, double[] pidValues) {
    this.trajectory = trajectoryIn;
    this.kP = pidValues[0];
    this.kI = pidValues[1];
    this.kD = pidValues[2];
    this.kA = pidValues[3];
    // this.kP = kP;
    // this.kI = kI;
    // this.kD = kD;
    // this.kA = kA;
    requires(Robot.Chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.Pigeon.resetPidgey();

    Robot.Chassis.rightFrontMotor.configPeakOutputForward(1.0);
    Robot.Chassis.rightFrontMotor.configPeakOutputReverse(-1.0);

    Robot.Chassis.leftFrontMotor.configPeakOutputForward(1.0);
    Robot.Chassis.leftFrontMotor.configPeakOutputReverse(-1.0);

    Robot.Chassis.setBrakeMode(NeutralMode.Brake);
    Robot.Chassis.resetEncoders();

    timeout = (trajectory.length() / 50)+0.7;
    setTimeout(timeout);

    Robot.Chassis.setTrajectory(trajectory, kP, kI, kD, kA);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    SmartDashboard.putNumber("Auto P Input", kP);
    SmartDashboard.putNumber("Auto I Input", kI);
    SmartDashboard.putNumber("Auto D Input", kD);
    SmartDashboard.putNumber("Auto A Input", kA);
    SmartDashboard.putString("Command Running", "autoSetPathReverse");
    Robot.Chassis.makePathReverse();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return isTimedOut();
    //(Robot.Chassis.leftSideFollower.isFinished() && Robot.Chassis.rightSideFollower.isFinished());
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("**** COMMAND ENDED ****");
    // Robot.Pigeon.resetPidgey();
    // Robot.Chassis.leftFrontMotor.set(ControlMode.PercentOutput, 0);
    // Robot.Chassis.rightFrontMotor.set(ControlMode.PercentOutput, 0);
    Robot.Chassis.setBrakeMode(NeutralMode.Brake);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

}
