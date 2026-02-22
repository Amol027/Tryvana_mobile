const express = require("express");
const router = express.Router();

const { verifyToken } = require("../middleware/authMiddleware");
const { checkRole } = require("../middleware/roleMiddleware");

// Admin Only
router.get(
  "/admin",
  verifyToken,
  checkRole(["ADMIN"]),
  (req, res) => {
    res.json({ message: "Welcome Admin 👑" });
  }
);

// Seller Only
router.get(
  "/seller",
  verifyToken,
  checkRole(["SELLER"]),
  (req, res) => {
    res.json({ message: "Welcome Seller 🛍" });
  }
);

// Delivery Only
router.get(
  "/delivery",
  verifyToken,
  checkRole(["DELIVERY"]),
  (req, res) => {
    res.json({ message: "Welcome Delivery Partner 🚚" });
  }
);

// User Only
router.get(
  "/user",
  verifyToken,
  checkRole(["USER"]),
  (req, res) => {
    res.json({ message: "Welcome User 👤" });
  }
);

module.exports = router;
