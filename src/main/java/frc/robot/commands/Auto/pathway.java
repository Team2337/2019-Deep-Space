package frc.robot.commands.Auto;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

/**
 * Stores all of the waypoints and max velocity, acceleration, and jerk values
 * <p><br/>Each of the waypoints are set in an array</p>
 * @author Bryce G.
 */
public class pathway {
    public static double metersConversion = 0.0254; 
    public static Trajectory.Config config;
    public static Trajectory trajectory;

    private static double timeStep = 0.1;

    /**
     * Converts inches to meters
     * @param inches - the desired distance in inches
     * @return - returns the distance in meters
     */
    private static double inchesToMeters(double inches) {
        return inches * metersConversion;
    }

    /**
     * Converts input degrees to radians
     * @param degrees - the desired angle in degrees
     * @return - returns the desired angle in radians, converted from degrees
     */
    private static double degreesToRadians(double degrees) {
        return degrees * (3.14159/180);
    }

    /**
     * Double 2D array The PID values for the drive commands. Call each value by
     * using an int value, to grab the row or column First dimension: row Second
     * dimension: column
     */
    public static double valuesPID[][] = new double[][] { 
        { 1.5, 0, 0.15, 0 }, // autoReverseToShipFromLvl1
        { 1.3, 0, 0, 0 }, // curveFromToHatchRightT
        { 2, 0, 0, 0 }, // fromRightLoadJTurnToCargoShipT
        { 2, 0, 0, 0 }, // jTurnToCargoShipRightT
        { 1, 0, 0, 0 }, // TEST
    };

    /**
     * Drives backwards starting on the platform
     */
    private static Waypoint[] autoReverseToShipFromLvl1 = new Waypoint[] {
        // Waypoint @ x=0, y=0, exit angle=0 radians
        new Waypoint(0, 0, 0), new Waypoint(5, 0, 0)
    };

    /**
     * Converts the waypoints to generate the path into values readable by the code
     * 
     * @param points - array of waypoints
     */
    public static Trajectory autoReverseToShipFromLvl1() {
        /*
         * old = 1.7, 2.0, 60.0
         * new = 4.267, 5.0, 150.6
         * 2,1.75,10
         * FIRST S CURVE -- config = new
         * Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
         * Trajectory.Config.SAMPLES_HIGH, timeStep, 2, 1.9, 10.0);
         * 1.7
         */
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2, 1.5, 10.0);
        trajectory = Pathfinder.generate(autoReverseToShipFromLvl1, config);
        return trajectory;
    }
    
    /**
     * <p>S-Curves from the cargo ship, to the <strong>RIGHT</strong> load station to grab a hatch
     */
    private static Waypoint[] curveFromToHatchRight = new Waypoint[] { 
        new Waypoint(0, 0, 0),
        new Waypoint(3.5, 2, 0.42), // 3.5, 2.35
        new Waypoint(5.5, 2.35, 0) // 5.95
    };

    /**
     * Converts the waypoints to generate the path into values readable by the code
     * 
     * @param points - array of waypoints
     */
    public static Trajectory curveFromToHatchRight() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 1.5, 0.35, 0.1); // 2, 1.7, 10
        trajectory = Pathfinder.generate(curveFromToHatchRight, config);
        return trajectory;
    }

    /**
     * <p>Drives from the <strong>RIGHT</strong> load station, and J-turns back to the wall and points the beak towards the cargo ship
     */
    private static Waypoint[] fromRightLoadJTurnToCargoShip = new Waypoint[] { 
        new Waypoint(0, 0, 0),
        new Waypoint(2, -.8, -0.58), // 33 degrees
        // new Waypoint(4, -1.2, 0.1),
        new Waypoint(5.8, -0.6, 1.2) 
        };

    /**
     * Converts the waypoints to generate the path into values readable by the code
     * 
     * @param points - array of waypoints
     */
    public static Trajectory fromRightLoadJTurnToCargoShip() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 2.5, 1.9, 10.0); // 2, 1.7
        trajectory = Pathfinder.generate(fromRightLoadJTurnToCargoShip, config);
        return trajectory;
    }

    /**
     * Coming out of the <strong>RIGHT</strong> J-Turn and drives to the cargo ship
     */
    private static Waypoint[] JTurnToCargoShipRight = new Waypoint[] { 
            new Waypoint(0, 0, 0),
            new Waypoint(2, 0, -0.58) 
        };

    /**
     * Converts the waypoints to generate the path into values readable by the code
     * 
     * @param points - array of waypoints
     */
    public static Trajectory jTurnToCargoShipRight() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep,2.5, 1.9, 10.0); // 2, 1.7
        trajectory = Pathfinder.generate(JTurnToCargoShipRight, config);
        return trajectory;
    }

    /**
     * <strong>TEST</strong> S-Curve
     */
    private static Waypoint[] testSCurve = new Waypoint[] { 
        new Waypoint(0, 0, 0), 
        new Waypoint(3, 1, 0) // POSITIVE IS LEFT
    };

    /**
     * Converts the waypoints to generate the path into values readable by the code
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
        new Waypoint(inchesToMeters(198), 0, degreesToRadians(0)) // NEGATIVE is RIGHT
    };

    /**
     * Converts the waypoints to generate the path into values readable by the code
     * 
     * @param points - array of waypoints
     */
    public static Trajectory driveForward() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 0.35, 0.05, 0.1); //0.35, 0.05, 0.1
        trajectory = Pathfinder.generate(driveForward, config);
        return trajectory;
    }
}