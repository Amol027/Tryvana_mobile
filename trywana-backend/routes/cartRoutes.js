const express = require("express");
const router = express.Router();

const cartController = require("../controllers/cartController");
const { verifyToken } = require("../middleware/authMiddleware");
const { checkRole } = require("../middleware/roleMiddleware");




// ✅ Add to Cart
router.post(
  "/",
  verifyToken,
  checkRole(["USER"]),
  cartController.addToCart
);


// ✅ Get My Cart
router.get(
  "/",
  verifyToken,
  checkRole(["USER"]),
  cartController.getCart
);


// ✅ Remove Item From Cart
router.delete(
  "/:productId",
  verifyToken,
  checkRole(["USER"]),
  cartController.removeFromCart
);


module.exports = router;
