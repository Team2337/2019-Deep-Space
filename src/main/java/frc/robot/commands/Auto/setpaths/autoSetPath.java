package frc.robot.commands.Auto.setpaths;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Reads the trajectory to drive to a position given by the waypoints in the forawrd direction
 * @author Bryce G.
 */
public class autoSetPath extends Command {

  /* --- Path Weaver Variables --- */

  public TankModifier modifier;
  public Trajectory.Config config;
  public Trajectory trajectory;

  public static double kP, kI, kD, kA, printX;
  private double[] pidValues;


  private double timeout;

  /**
   * Reads the set trajectories into the drive 
   * @param trajectoryIn - desired trajectory
   * @param pidValues - PID values for the current trajcetory, given in the array
   * @see Pathway.java for more info on each row/column of the PID values
   */
  public autoSetPath(Trajectory trajectoryIn, double[] pidValues) {
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
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    Robot.Chassis.resetEncoders();
    Robot.Chassis.resetNeoEncoders();

    //The timeout is converting the lenght of the trajectory by the time step in the trajectory (1/10 of a second)
    //This equates to trajectory points per second
    timeout = (trajectory.length() / 10) + 1.5; 
    setTimeout(timeout);
    Robot.NerdyPath.setTrajectory(trajectory, kP, kI, kD, kA);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.NerdyPath.makePathForawrd();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;//isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
