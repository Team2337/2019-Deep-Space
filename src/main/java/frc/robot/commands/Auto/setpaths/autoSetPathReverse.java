package frc.robot.commands.Auto.setpaths;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * This command is mainly a placeholder command, but it can be used
 * functionally. It does just as it says: nothing.
 * @author Bryce G.
 */
public class autoSetPathReverse extends Command {

  /* --- Path Weaver Variables --- */

  public TankModifier modifier;
  public Trajectory.Config config;
  public Trajectory trajectory;
  public EncoderFollower rightSideFollower;
  public EncoderFollower leftSideFollower;

  public static double kP, kI, kD, kA;
  private double[] pidValues;

  private double timeout;

  // CONSTRUCTOR
  public autoSetPathReverse(Trajectory trajectoryIn, double[] pidValues) {
    this.trajectory = trajectoryIn;
    this.pidValues = pidValues;
    requires(Robot.Chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    kP = pidValues[0];
    kI = pidValues[1];
    kD = pidValues[2];
    kA = pidValues[3];
    Robot.Pigeon.resetPidgey();

    // Robot.Chassis.rightFrontMotor.configPeakOutputForward(1.0);
    // Robot.Chassis.rightFrontMotor.configPeakOutputReverse(-1.0);

    // Robot.Chassis.leftFrontMotor.configPeakOutputForward(1.0);
    // Robot.Chassis.leftFrontMotor.configPeakOutputReverse(-1.0);

    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
    Robot.Chassis.resetEncoders();

    timeout = (trajectory.length() / 50)+0.7;
    setTimeout(timeout);

    Robot.NerdyPath.setTrajectory(trajectory, kP, kI, kD, kA);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.NerdyPath.makePathReverse();
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
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

}
