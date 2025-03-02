package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.localization.PinpointConstants;
import com.pedropathing.hardware.Encoder;
import com.pedropathing.hardware.GoBildaPinpointDriver;

public class LConstants {
    static {
        // Pinpoint module name (name of the I2C device in the hardware map)
        PinpointConstants.hardwareMapName = "pinpoint";

        // Odometry pod positions relative to the robot's center of rotation
        PinpointConstants.forwardY = 1.0; // Distance in inches (adjust based on your robot)
        PinpointConstants.strafeX = -2.5; // Distance in inches (adjust based on your robot)

        // Measurement unit
        PinpointConstants.distanceUnit = GoBildaPinpointDriver.DistanceUnit.INCH;

        // Encoder resolution settings
        PinpointConstants.useCustomEncoderResolution = false; // Set to true if using custom resolution
        PinpointConstants.encoderResolution = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD;
        PinpointConstants.customEncoderResolution = 13.26291192; // Use this only if custom resolution is enabled

        // Encoder directions
        PinpointConstants.forwardEncoderDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED; // Adjust if necessary
        PinpointConstants.strafeEncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD; // Adjust if necessary

        // Yaw Scalar (optional, set to true only if necessary)
        PinpointConstants.useYawScalar = false; // Leave false unless you have a reason to change it
        PinpointConstants.yawScalar = 1.0; // Adjust if `useYawScalar` is true
    }
}