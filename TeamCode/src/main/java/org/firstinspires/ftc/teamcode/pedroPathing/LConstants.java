package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.localization.Encoder;
import com.pedropathing.localization.Localizers;
import com.pedropathing.localization.constants.DriveEncoderConstants;

public class LConstants {
    static {
        DriveEncoderConstants.forwardTicksToInches = 0.912;
        DriveEncoderConstants.strafeTicksToInches = 1;
        DriveEncoderConstants.turnTicksToInches = 1;

        DriveEncoderConstants.robot_Width = 16;
        DriveEncoderConstants.robot_Length = 16.5;

        DriveEncoderConstants.leftFrontEncoderDirection = Encoder.REVERSE;
        DriveEncoderConstants.rightFrontEncoderDirection = Encoder.REVERSE;
        DriveEncoderConstants.leftRearEncoderDirection = Encoder.REVERSE;
        DriveEncoderConstants.rightRearEncoderDirection = Encoder.FORWARD;
    }
}