const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const userModel = require("../models/userModel");

exports.register = async (req, res, next) => {
  try {
    const { name, email, phone, password, role } = req.body;

    if (!name || !email || !password) {
      const error = new Error("Name, email and password are required");
      error.status = 400;
      throw error;
    }

    const existingUser = await userModel.findUserByEmail(email);
    if (existingUser) {
      const error = new Error("Email already exists");
      error.status = 400;
      throw error;
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const user = await userModel.createUser(
      name,
      email,
      phone,
      hashedPassword,
      role || "USER"
    );

    res.status(201).json({
      success: true,
      message: "User registered successfully",
      data: user,
    });
  } catch (error) {
    next(error);
  }
};

exports.login = async (req, res, next) => {
  try {
    const { email, password } = req.body;

    const user = await userModel.findUserByEmail(email);
    if (!user) {
      const error = new Error("Invalid credentials");
      error.status = 400;
      throw error;
    }

    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) {
      const error = new Error("Invalid credentials");
      error.status = 400;
      throw error;
    }

    const token = jwt.sign(
      { id: user.id, role: user.role },
      process.env.JWT_SECRET,
      { expiresIn: "7d" }
    );

    res.json({
      success: true,
      data: {
        token,
        user: {
          id: user.id,
          name: user.name,
          role: user.role,
        },
      },
    });
  } catch (error) {
    next(error);
  }
};
