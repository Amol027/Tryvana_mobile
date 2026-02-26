const express = require("express");
const router = express.Router();
const authController = require("../controllers/authController");
const googleAuthController = require("../controllers/googleAuthController");



router.post("/google-login", googleAuthController.googleLogin);
router.post("/register", authController.register);
router.post("/login", authController.login);

module.exports = router;
