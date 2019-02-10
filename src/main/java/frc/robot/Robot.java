package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.subsystems.AirCompressor;
import frc.robot.subsystems.AutoHatchKicker;
import frc.robot.subsystems.CargoEscalator;
import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.CargoScore;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.ClimberPneumatics;
import frc.robot.commands.Auto.Pathway;
import frc.robot.nerdyfiles.pathway.NerdyPath;
import frc.robot.subsystems.HatchBeak;
import frc.robot.subsystems.HatchLauncher;
import frc.robot.subsystems.LED;
import frc.robot.subsystems.Lift;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Shifter;
import frc.robot.subsystems.Vision;
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

  // DECLARATIONS
  public static AirCompressor AirCompressor;
  public static AutoHatchKicker AutoHatchKicker;
  public static Chassis Chassis;
  public static CargoIntake CargoIntake;
  public static CargoEscalator CargoEscalator;
  public static CargoScore CargoScore;
  public static ClimberPneumatics ClimberPneumatics;
  public static HatchLauncher HatchLauncher;
  public static HatchBeak HatchBeak;
  public static LED LED;
  public static Lift Lift;
  public static Pigeon Pigeon;
  public static Shifter Shifter;
  public static Vision Vision;

  public static NerdyPath NerdyPath;
  
  public static Constants Constants;
  public static OI oi;

  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  public static Trajectory initTrajectory;
  public static Trajectory initTrajectory2;
  public static Trajectory curveFromToHatchRightT;
  public static Trajectory fromRightLoadJTurnToCargoShipT;
  public static Trajectory jTurnToCargoShipRightT;
  public static Trajectory driveForwardT;

  public static Trajectory driveForwardFile;

  private boolean logger;
  private String selectedAuto;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
   
    // CONSTRUCTORS
    AirCompressor = new AirCompressor();
    AutoHatchKicker = new AutoHatchKicker();
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

    NerdyPath = new NerdyPath();
    Constants = new Constants();

    System.out.println("Start");
    Robot.Vision.setLEDMode(1);
    //Used to load the points for the auton. These points take a long time to load, so to reduce time, 
    //we only load the ones we need for the current auton we're going to run
    selectedAuto = "";

    switch(selectedAuto) {
      default :
      // initTrajectory = Pathway.autoReverseToShipFromLvl1();
      //  initTrajectory2 = Pathway.testSCurve();
      // fromRightLoadJTurnToCargoShipT = Pathway.fromRightLoadJTurnToCargoShip();
      // jTurnToCargoShipRightT = Pathway.jTurnToCargoShipRight();
      driveForwardT = Pathway.driveForward();
      // curveFromToHatchRightT = Pathway.curveFromToHatchRight();
      // System.out.println(FileUtilities.getFilePath());
      // driveForwardFile = NerdyPath.loadTrajectoryFile("test");
      break;
    }
    // Robot.NerdyPath.writeFile("locations", driveForwardT);
    System.out.println("Fin (fish)");

    oi = new OI();

    // Robot.NerdyPath.makeTrajectoryFile(driveForwardT, "driveForwardTrajectory");
    // System.out.println("*****" + Filesystem.getDeployDirectory().toString() + "*****");

    // chooser.addOption("My Auto", new MyAutoCommand());
    
    Robot.Chassis.resetEncoders();
    Robot.Pigeon.resetPidgey();
    SmartDashboard.putData("Auto mode", chooser);
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
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
    Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    // Robot.Chassis.setBrakeMode(NeutralMode.Coast);
    Robot.Vision.setLEDMode(1);
    logger = false;
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
    allPeriodic();
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
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
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
    allPeriodic();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void allPeriodic() {
    SmartDashboard.putBoolean("Logger", logger);
  }
}
