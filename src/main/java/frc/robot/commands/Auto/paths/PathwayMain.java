package frc.robot.commands.Auto.paths;
//TODO: test all of the pathway files
/**
 * Main file for all of the methods needed for the trajectory configurations
 * @author Bryce G.
 */
public class PathwayMain {
    private static double metersConversion = 0.0254; 
    protected static double timeStep = 0.2;

    /**
     * Converts inches to meters
     * @param inches - the desired distance in inches
     * @return - returns the distance in meters
     */
    protected static double inchesToMeters(double inches) {
        return inches * metersConversion;
    }

    /**
     * Converts input degrees to radians
     * @param degrees - the desired angle in degrees
     * @return - returns the desired angle in radians, converted from degrees
     */
    protected static double degreesToRadians(double degrees) {
        return degrees * (3.14159/180);
    }
}