package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Auto.*;
import frc.robot.commands.Auto.CommandGroups.*;
import frc.robot.commands.Auto.setpaths.*;
import frc.robot.nerdyfiles.pathway.NerdyPath;
import frc.robot.subsystems.*;
import jaci.pathfinder.Trajectory;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * Specifies whether or not the Robot is the competition robot
   */
  public static boolean isComp = false;
  public static boolean stringPotBroken = false;

  public double[][] valuesPID = pathway.valuesPID;

  // DECLARATIONS
  public static AirCompressor AirCompressor;
  public static AutoHatchKicker AutoHatchKicker;
  public static CargoBigBrother CargoBigBrother;
  public static CargoDrawbridge CargoDrawbridge;
  public static CargoIntake CargoIntake;
  public static CargoEscalator CargoEscalator;
  public static CargoScore CargoScore;
  public static Chassis Chassis;
  public static ClimberPneumatics ClimberPneumatics;
  public static Constants Constants;
  public static HatchLauncher HatchLauncher;
  public static HatchBeak HatchBeak;
  public static LED LED;
  public static Lift Lift;
  public static NerdyPath NerdyPath;
  public static OI oi;
  public static Pigeon Pigeon;
  public static Shifter Shifter;
  public static Vision Vision;

  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  public static Trajectory curveFromToHatchRightT;
  public static Trajectory driveForwardFile;
  public static Trajectory driveForwardT;
  public static Trajectory fromRightLoadJTurnToCargoShipT;
  public static Trajectory initTrajectory;
  public static Trajectory initTrajectory2;
  public static Trajectory jTurnToCargoShipRightT;
  public static Trajectory testSCurveT;

  public static boolean logger;
  private String selectedAuto;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    
    // CONSTRUCTORS
    // Keep above other subsystems, as these have dependencies for other subsystems
    // to be instantiated first.
    Constants = new Constants();
    
    AirCompressor = new AirCompressor();
    AutoHatchKicker = new AutoHatchKicker();
    CargoDrawbridge = new CargoDrawbridge();
    CargoEscalator = new CargoEscalator();
    CargoIntake = new CargoIntake();
    CargoScore = new CargoScore();
    Chassis = new Chassis();
    ClimberPneumatics = new ClimberPneumatics();
    HatchBeak = new HatchBeak();
    HatchLauncher = new HatchLauncher();
    LED = new LED();
    Lift = new Lift();
    Pigeon = new Pigeon();
    Shifter = new Shifter();
    Vision = new Vision();

    /* 
     * Keep below other subsystems as these have dependencies for other subsystems
     * to be instantiated first.
     */

    NerdyPath = new NerdyPath();
    CargoBigBrother = new CargoBigBrother();

    // Turn off the Limelight LED if it is on.
    Vision.setLEDMode(1);

    // Used to load the points for the auton. These points take a long time to load,
    // so to reduce time, we only load the ones we need for the current auton we're
    // going to run
    selectedAuto = "";

    switch (selectedAuto) {
    default:
      driveForwardT = pathway.driveForward();
      curveFromToHatchRightT = pathway.curveFromToHatchRight();
      fromRightLoadJTurnToCargoShipT = pathway.fromRightLoadJTurnToCargoShip();
      jTurnToCargoShipRightT = pathway.jTurnToCargoShipRight();
      break;
    }
    // testSCurveT = pathway.testSCurve();

    // Writing a trajectory to a file (keep commented out until needed)
    // Robot.NerdyPath.writeFile("locations", driveForwardT);

    oi = new OI();

    chooser.addOption("My Auto", new CGTwoHatchAutoRight());
    chooser.addOption("Do Nothing", new autoDoNothing());

    Robot.Chassis.resetEncoders();
    Robot.Pigeon.resetPidgey();
    SmartDashboard.putData("Auto mode", chooser);
    Vision.streamMode(2);
    // Hold the current lift position so that the lift doesn't move on startup
    Robot.Lift.setSetpoint(Robot.Lift.getPosition());
    // Disable the air compressor so it doesn't run every time we start the robot.
    // Robot.AirCompressor.disable();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //TODO: Determine what should go on the driver dashboard
    SmartDashboard.putBoolean("Logger", logger);
    if (Robot.Lift.getPosition() < Robot.Lift.minValue || Robot.Lift.getPosition() > Robot.Lift.maxValue) {
      stringPotBroken = true;
    } else {
      stringPotBroken = false;
    }

    /* --- Dashboard Items --- */
    SmartDashboard.putBoolean("String Pot Broken", stringPotBroken);
    SmartDashboard.putBoolean("Trolley Sensor", Robot.CargoBigBrother.cargoTrolleySensor.get());
    SmartDashboard.putNumber("Air Pressure (PSI)", Robot.AirCompressor.getPressure());

    SmartDashboard.putNumber("Right Distance", (Robot.Chassis.getRightPosition()  /13988) * 20);
    SmartDashboard.putNumber("Left Distance", (Robot.Chassis.getLeftPosition()  /13988) * 20);
    SmartDashboard.putNumber("Right Encoder", Robot.Chassis.getRightPosition());
    SmartDashboard.putNumber("Left Encoder", Robot.Chassis.getLeftPosition());
    SmartDashboard.putNumber("Sting Pot", Robot.Lift.getPosition());
    SmartDashboard.putNumber("Right Velocity", Robot.Chassis.neoRightFrontEncoder.getVelocity());
    SmartDashboard.putNumber("Left Velocity", Robot.Chassis.neoLeftFrontEncoder.getVelocity());
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    Robot.Vision.setLEDMode(1);
    logger = false;
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    Robot.Lift.setSetpoint(Robot.Lift.getPosition());
    autonomousCommand = chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
     * switch(autoSelected) { case "My Auto": autonomousCommand = new
     * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
     * ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    Robot.Lift.setSetpoint(Robot.Lift.getPosition());
    /*
     * This makes sure that the autonomous stops running when
     * teleop starts running. If you want the autonomous to
     * continue until interrupted by another command, remove
     * this line or comment it out.
     */
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    logger = true;
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

}
