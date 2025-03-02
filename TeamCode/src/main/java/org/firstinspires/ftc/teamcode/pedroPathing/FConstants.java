package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Localizers;

public class FConstants {
    static {
        // Use the Pinpoint Localizer
        FollowerConstants.localizers = Localizers.PINPOINT;

        // Mass of the robot (adjust during tuning)
        FollowerConstants.mass = 10; // Mass in kg
    }
}