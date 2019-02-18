package frc.robot.commands.Auto.setpaths;

import com.revrobotics.CANSparkMax.IdleMode;

import org.junit.rules.Timeout;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Robot;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Reads the trajectory to drive to a position given by the waypoints in the reverse direction
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

  private double timeout, timer;

  /**
   * Reads the set trajectories into the drive, and sets it in reverse
   * @param trajectoryIn - desired trajectory
   * @param pidValues - PID values for the current trajcetory, given in the array
   * @see Pathway.java for more info on each row/column of the PID values
   */
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

    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
    Robot.Chassis.resetEncoders();

    timeout = (trajectory.length() / 10) + 1.5; //0.7
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
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("**** COMMAND ENDED ****");
    Robot.Chassis.neoDriveArcade(0, 0, false);
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

}
