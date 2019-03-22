package frc.robot.commands.Auto.paths;

import jaci.pathfinder.*;

/**
 * Holds the trajectory configurations for the Two Hatch, <strong>Right Rocket</strong> auton
 * @author Bryce G.
 */
public class PathwayRightRocketTwoHatch extends PathwayMain{
    
    private static Trajectory.Config config;
    private static Trajectory trajectory;

    /**
     * Double 2D array The PID values for the drive commands. Call each value by
     * using an int value, to grab the row or column First dimension: row Second
     * dimension: column
     */
    public static double valuesPID[][] = new double[][] { 
        { 1.5, 0, 0, 0 }, // driveOffRightLvl2ToBackRightRocket
    };

    /******************************/
    /* -------------------------- */
    /* --- Right Rocket Paths --- */
    /* -------------------------- */
    /******************************/

    /**
     * Drive Reverse to back rocket
     */
    private static Waypoint[] driveOffRightLvl2ToBackRightRocket = new Waypoint[] {
        // Waypoint @ x=0, y=0, exit angle=0 radians
        new Waypoint(0, 0, 0), 
        new Waypoint(inchesToMeters(225), inchesToMeters(75), -degreesToRadians(5)) // NEGATIVE is RIGHT  ///use 248 from the top platform. //198 
    };

    /**
     * <p>The first reverse drive to the cargo ship in auto <br/></p>
     * After this drive we will fire the autoHatchKicker to score the hatch
     * 
     * @param points - array of waypoints
     */
    public static Trajectory driveOffRightLvl2ToBackRightRocket() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 0.5, 0.13, 0.5); //0.35, 0.05, 0.1  
        trajectory = Pathfinder.generate(driveOffRightLvl2ToBackRightRocket, config);
        return trajectory;
    }

    /**
     * Drive Reverse to back rocket
     */
    private static Waypoint[] driveAwayFromBackRightRocket = new Waypoint[] {
        // Waypoint @ x=0, y=0, exit angle=0 radians
        new Waypoint(0, 0, 0), 
        new Waypoint(inchesToMeters(30), inchesToMeters(0), degreesToRadians(30)) // NEGATIVE is RIGHT  ///use 248 from the top platform. //198 
    };

    /**
     * <p>The first reverse drive to the cargo ship in auto <br/></p>
     * After this drive we will fire the autoHatchKicker to score the hatch
     * 
     * @param points - array of waypoints
     */
    public static Trajectory driveAwayFromBackRightRocket() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 0.35, 0.05, 0.1); //0.35, 0.05, 0.1  2.8, 1.2, 120
        trajectory = Pathfinder.generate(driveAwayFromBackRightRocket, config);
        return trajectory;
    }

}