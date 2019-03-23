package frc.robot.commands.Auto.paths;

import jaci.pathfinder.*;

/**
 * Stores all of the test trajectory configurations
 * @author Bryce G.
 */
public class PathwaySideTwoHatchFromRight extends PathwayMain{
    
    public static Trajectory.Config config;
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
     * <strong>TEST</strong> drive forwards path
     */
    private static Waypoint[] sideTwoHatchFromRight = new Waypoint[] {
        // Waypoint @ x=0, y=0, exit angle=0 radians
        new Waypoint(0, 0, 0), 
        new Waypoint(inchesToMeters(100), inchesToMeters(50), degreesToRadians(-90)) // NEGATIVE is RIGHT  ///use 248 from the top platform. //174.8 
    };

    /**
     * <p>The first reverse drive to the cargo ship in auto <br/></p>
     * After this drive we will fire the autoHatchKicker to score the hatch
     * 
     * @param points - array of waypoints
     */
    public static Trajectory sideTwoHatchFromRight() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 0.35, 0.05, 0.1); //0.35, 0.05, 0.1  2.8, 1.2, 120
        trajectory = Pathfinder.generate(sideTwoHatchFromRight, config);
        return trajectory;
    }
}