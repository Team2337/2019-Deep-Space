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

    private static double timeStep = 0.2;

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
        { 1, 0, 0, 0 }, // autoReverseToShipFromLvl1
        { 1.3, 0, 0, 0 }, // curveFromToHatchRightT
        { 1.0, 0, 0, 0 }, // fromRightLoadJTurnToCargoShipT
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
        new Waypoint(3.5, inchesToMeters(80.7), 0.2), //supposed to be 139 // 3.5, 2.05
        new Waypoint(5, inchesToMeters(89), 0) //supposed to be 147 // 5, 2.25
    };

    /**
     * <p>S-Curves from the cargo ship, to the <strong>RIGHT</strong> load station to grab a hatch
     * 
     * @param points - array of waypoints
     */
    public static Trajectory curveFromToHatchRight() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep,  0.35, 0.05, 0.1); //1.5, 0.35, 0.1); // 2, 1.7, 10
        trajectory = Pathfinder.generate(curveFromToHatchRight, config);
        return trajectory;
    }

    /**
     * <p>Drives from the <strong>RIGHT</strong> load station, and J-turns back to the wall and 
     * points the beak towards the cargo ship
     */
    private static Waypoint[] fromRightLoadJTurnToCargoShip = new Waypoint[] { 
        new Waypoint(0, 0, 0),
        new Waypoint(3, -1, -0.3), // 33 degrees
        // new Waypoint(5.5, -0.6, -0.05),
        new Waypoint(6.5, 0.1, 1) //-0.2 
        };

    /**
     * <p>Drives from the <strong>RIGHT</strong> load station, and J-turns back to the wall and 
     * points the beak towards the cargo ship
     * @param points - array of waypoints
     */
    public static Trajectory fromRightLoadJTurnToCargoShip() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 0.35, 0.1, 4); // 0.35, 0.05, 0.1
        trajectory = Pathfinder.generate(fromRightLoadJTurnToCargoShip, config);
        return trajectory;
    }

    /**
     * Coming out of the <strong>RIGHT</strong> J-Turn and drives to the cargo ship
     */
    private static Waypoint[] JTurnToCargoShipRight = new Waypoint[] { 
            new Waypoint(0, 0, 0),
            new Waypoint(1, -0.25, -0.3) 
        };

    /**
     * Coming out of the <strong>RIGHT</strong> J-Turn and drives to the cargo ship
     * 
     * @param points - array of waypoints
     */
    public static Trajectory jTurnToCargoShipRight() {
        config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, timeStep, 0.35, 0.05, 0.1); // 2, 1.7
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

//5.13x^2, 10.26x, 10.26
//4.25x^2, 8.5x, 8.5