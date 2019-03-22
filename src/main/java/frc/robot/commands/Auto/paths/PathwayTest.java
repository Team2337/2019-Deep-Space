package frc.robot.commands.Auto.paths;

import jaci.pathfinder.*;

/**
 * Stores all of the test trajectory configurations
 * @author Bryce G.
 */
public class PathwayTest extends PathwayMain{
    
    private static Trajectory.Config config;
    private static Trajectory trajectory;

    /**
     * Double 2D array The PID values for the drive commands. Call each value by
     * using an int value, to grab the row or column First dimension: row Second
     * dimension: column
     */
    public static double valuesPID[][] = new double[][] {
        { 1, 0, 0, 0 }, // TEST
    };

    /**
     * <strong>TEST</strong> S-Curve
     */
    private static Waypoint[] testSCurve = new Waypoint[] { 
        new Waypoint(0, 0, 0), 
        new Waypoint(3, 1, 0) // POSITIVE IS LEFT
    };

    /**
     * Test S-Curve 
     * <h1><strong>DON'T USE IN COMP</strong></h1>
     * 
     * @param points - array of waypoints
     */
    public static Trajectory testSCurve() {
        // old = 1.7, 2.0, 60.0
        // new = 4.267, 5.0, 150.6
        // 2,1.75,10
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep,
                0.75, 0.35, 10.0);
        trajectory = Pathfinder.generate(testSCurve, config);
        return trajectory;
    }

    /**
     * <strong>TEST</strong> drive forwards path
     */
    private static Waypoint[] driveForward = new Waypoint[] {
        // Waypoint @ x=0, y=0, exit angle=0 radians
        new Waypoint(0, 0, 0), 
        new Waypoint(inchesToMeters(50), 0, degreesToRadians(0)) // NEGATIVE is RIGHT  ///use 248 from the top platform. //198 
    };

    /**
     * <p>The first reverse drive to the cargo ship in auto <br/></p>
     * After this drive we will fire the autoHatchKicker to score the hatch
     * 
     * @param points - array of waypoints
     */
    public static Trajectory driveForward() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 0.35, 0.05, 0.1); //0.35, 0.05, 0.1  2.8, 1.2, 120
        trajectory = Pathfinder.generate(driveForward, config);
        return trajectory;
    }
}