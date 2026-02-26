const { OAuth2Client } = require("google-auth-library");
const jwt = require("jsonwebtoken");
const userModel = require("../models/userModel");

const allowedRoles = ["USER", "SELLER"];

const client = new OAuth2Client(process.env.GOOGLE_CLIENT_ID);

exports.googleLogin = async (req, res, next) => {
  try {
    const { idToken, role } = req.body;

    if (!idToken || !role) {
      return res.status(400).json({
        message: "ID Token and role are required",
      });
    }

    if (!allowedRoles.includes(role)) {
      return res.status(400).json({
        message: "Invalid role selected",
      });
    }

    const ticket = await client.verifyIdToken({
      idToken,
      audience: process.env.GOOGLE_CLIENT_ID,
    });

    const payload = ticket.getPayload();
    const { email, name, sub } = payload;

    if (!email) {
      return res.status(400).json({
        message: "Google account has no email",
      });
    }

    let user = await userModel.findUserByEmail(email);

    if (user) {
      // Role mismatch check
      if (user.role !== role) {
        return res.status(403).json({
          message: "Role mismatch. Please select correct role.",
        });
      }
    } else {
      // Create new Google user
      user = await userModel.createGoogleUser(
        name,
        email,
        sub,
        role
      );
    }

    const token = jwt.sign(
      { id: user.id, role: user.role },
      process.env.JWT_SECRET,
      { expiresIn: "7d" }
    );

    res.status(200).json({
      success: true,
      message: "Google login successful",
      data: {
        token,
        user: {
          id: user.id,
          name: user.name,
          email: user.email,
          role: user.role,
        },
      },
    });
  } catch (error) {
    next(error);
  }
};
