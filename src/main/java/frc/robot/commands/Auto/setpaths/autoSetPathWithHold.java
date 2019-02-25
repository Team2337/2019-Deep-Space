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
public class autoSetPathWithHold extends Command {

  /* --- Path Weaver Variables --- */

  public TankModifier modifier;
  public Trajectory.Config config;
  public Trajectory trajectory;
  public EncoderFollower rightSideFollower;
  public EncoderFollower leftSideFollower;

  public static double kP, kI, kD, kA;
  private double[] pidValues;

  private double timeout;

  /**
   * Reads the set trajectories into the drive, and ends the command using a timeout,
   * before the robot reaches all of its points
   * @param trajectoryIn - desired trajectory
   * @param pidValues - PID values for the current trajcetory, given in the array
   * @see Pathway.java for more info on each row/column of the PID values
   */
  public autoSetPathWithHold(Trajectory trajectoryIn, double[] pidValues) {
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

    timeout = ((trajectory.length()-65) / 50);
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
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    System.out.println("Right Motor Percent Output: " + Robot.CargoIntake.CargoIntakeMotor.getMotorOutputPercent());
    System.out.println("Left Motor Percent Output: " + Robot.Chassis.leftFrontMotor.getMotorOutputPercent());
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
    Robot.Vision.setLEDMode(3);
    System.out.println("**** COMMAND ENDED ****");
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
