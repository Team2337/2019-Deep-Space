package frc.robot.commands.Auto.setpaths;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 * Reads the trajectory to drive to a position given by the waypoints in the
 * reverse direction
 * 
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
  private int segment, wait;
  private double timeout, finishTime;
  //TODO: Variablize lineDistance in the constructor
  private double lineDistance = 56000;
  private boolean finished, crossedLine;
  private boolean lastState = false;
  public static boolean fin;

  /**
   * Reads the set trajectories into the drive, and sets it in reverse
   * 
   * @param trajectoryIn - desired trajectory
   * @param pidValues    - PID values for the current trajcetory, given in the
   *                     array
   * @see Pathway.java for more info on each row/column of the PID values
   */
  public autoSetPathReverse(Trajectory trajectoryIn, double[] pidValues, double timeout) {
    this.trajectory = trajectoryIn;
    this.pidValues = pidValues;
    this.timeout = timeout;
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
    Robot.NerdyPath.setTrajectory(trajectory, kP, kI, kD, kA);
    segment = 0;
    wait = 0;
    finished = false;
    finishTime = timeout * 50;

    Robot.Chassis.crossedLine = false;
    Robot.Chassis.linesCrossed = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    Robot.NerdyPath.makePathReverse();
    segment++;
    if (segment >= (trajectory.length() - 30)) { //segment >= (trajectory.length() - 50)
      finished = true;
    }
    if (finished) {
      wait++;
    }

    //Line sensor drive 
    
    if(Math.abs(Robot.Chassis.getLeftPosition()) >= lineDistance) {

      if(lastState && Robot.Chassis.autoLineSensor.get()) {
        Robot.Chassis.linesCrossed++;
      }
      if(!Robot.Chassis.autoLineSensor.get()) {
        lastState = true;
      }
      if(Robot.Chassis.autoLineSensor.get()) {
        lastState = false;
      }
      

      
      if(crossedLine && Robot.Chassis.autoLineSensor.get()) {
        Robot.Chassis.linesCrossed = 1;
      }
      if(Robot.Chassis.linesCrossed == 1 && Robot.Chassis.crossedLine && !Robot.Chassis.autoLineSensor.get()) {
        Robot.Chassis.linesCrossed = 2;
      }
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    //ends the command once the profile has finished
    return finished; //wait >= finishTime;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    Robot.Chassis.neoLeftFrontMotor.set(-0.3);
    Robot.Chassis.neoRightFrontMotor.set(-0.3);
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }

}