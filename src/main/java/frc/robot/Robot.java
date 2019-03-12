package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.Auto.autoDoNothing;
import frc.robot.commands.Auto.pathway;
import frc.robot.commands.Auto.CommandGroups.CGTwoHatchAutoRight;
import frc.robot.commands.Auto.setpaths.autoSetPathReverse;
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
  public static ClimberDeploy ClimberDeploy;
  public static Constants Constants;
  public static HatchLauncher HatchLauncher;
  public static HatchBeak HatchBeak;
  public static LED LED;
  public static Lift Lift;
  public static NerdyPath NerdyPath;
  public static OI oi;
  public static Pigeon Pigeon;
  public static RoboWrangler RoboWrangler;
  public static PowerDistributionPanel PDP;
  public static Shifter Shifter;
  public static TRexArms TRexArms;
  public static Vision Vision;

  public static Command autonomousCommand;
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
    ClimberDeploy = new ClimberDeploy();
    HatchBeak = new HatchBeak();
    HatchLauncher = new HatchLauncher();
    LED = new LED();
    Lift = new Lift();
    Pigeon = new Pigeon();
    RoboWrangler = new RoboWrangler();
    //PDP = new PowerDistributionPanel();
    Pigeon = new Pigeon();
    Shifter = new Shifter();
    TRexArms = new TRexArms();
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
    selectedAuto = "twoHatch";

    switch (selectedAuto) {
      case "twoHatch":
        driveForwardT = pathway.driveForward();
        // curveFromToHatchRightT = pathway.curveFromToHatchRight();
        // fromRightLoadJTurnToCargoShipT = pathway.fromRightLoadJTurnToCargoShip();
        // jTurnToCargoShipRightT = pathway.jTurnToCargoShipRight();
        break;
      default:
      
        break;
    }

    // Writing a trajectory to a file (keep commented out until needed)
    // Robot.NerdyPath.writeFile("driveForward184", driveForwardT); //187

    oi = new OI();

    chooser.setDefaultOption("Two Hatch Auton Right", new CGTwoHatchAutoRight());
    chooser.addOption("Do Nothing", new autoDoNothing());
    chooser.addOption("driveForward", new autoSetPathReverse(Robot.NerdyPath.readFile("driveForward"), valuesPID[0], 2));

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
    SmartDashboard.putBoolean("Logger", logger);
    if (Robot.Lift.getPosition() < Robot.Lift.minValue || Robot.Lift.getPosition() > Robot.Lift.maxValue) {
      stringPotBroken = true;
    } else {
      stringPotBroken = false;
    }

    /* --- Driver Dashboard Items --- */
    SmartDashboard.putBoolean("Driver/String Pot Broken", stringPotBroken);
    SmartDashboard.putBoolean("Driver/Trolley Sensor", Robot.CargoBigBrother.cargoTrolleySensor.get());
    SmartDashboard.putBoolean("Driver/Intake sensor", Robot.CargoBigBrother.cargoIntakeSensor.get());
    SmartDashboard.putBoolean("Driver/Escalator sensor", !Robot.CargoBigBrother.cargoEscalatorSensor.get());
    SmartDashboard.putNumber("Driver/Air Pressure (PSI)", Robot.AirCompressor.getPressure());
    SmartDashboard.putBoolean("is Comp", isComp);
    SmartDashboard.putNumber("Driver/Neo_LF_Temp", (((Robot.Chassis.neoLeftFrontMotor.getMotorTemperature() * 9)/5) + 32));
    SmartDashboard.putNumber("Driver/Neo_LR_Temp", (((Robot.Chassis.neoLeftRearMotor.getMotorTemperature() * 9)/5) + 32));
    SmartDashboard.putNumber("Driver/Neo_RF_Temp", (((Robot.Chassis.neoRightFrontMotor.getMotorTemperature() * 9)/5) + 32));
    SmartDashboard.putNumber("Driver/Neo_RR_Temp", (((Robot.Chassis.neoRightRearMotor.getMotorTemperature() * 9)/5) + 32));
    SmartDashboard.putNumber("Driver/Right_Encoder", Robot.Chassis.getRightPosition());
    SmartDashboard.putNumber("Driver/Left_Encoder", Robot.Chassis.getLeftPosition());
    SmartDashboard.putNumber("Driver/Compass_Heading", Robot.Pigeon.getYaw());
    SmartDashboard.putNumber("Driver/Lift_Position", Robot.Lift.getPosition());
    SmartDashboard.putBoolean("Driver/Compressor On?", Robot.AirCompressor.status());
    SmartDashboard.putBoolean("Driver/Auto_Line_Sensor", Robot.Chassis.autoLineSensor.get());
    SmartDashboard.putBoolean("Driver/Climber Line Sensor", Robot.ClimberDeploy.climbLineSensor.get());

    SmartDashboard.putNumber("Climber phase", Robot.ClimberDeploy.climberPhase);
    SmartDashboard.putBoolean("Ready to climb", Robot.ClimberDeploy.readyToClimb);
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