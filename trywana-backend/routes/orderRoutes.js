const express = require("express");
const router = express.Router();
const orderController = require("../controllers/orderController");
const { verifyToken } = require("../middleware/authMiddleware");
const { checkRole } = require("../middleware/roleMiddleware");

// User place order
// router.post(
//   "/place",
//   verifyToken,
//   checkRole(["USER"]),
//   orderController.placeOrder
// );

// User orders
router.get(
  "/my",
  verifyToken,
  checkRole(["USER"]),
  orderController.getMyOrders
);

// Seller orders
router.get(
  "/seller",
  verifyToken,
  checkRole(["SELLER"]),
  orderController.getSellerOrders
);

// Seller update order status
router.put(
  "/:id/status",
  verifyToken,
  checkRole(["SELLER"]),
  orderController.updateOrderStatus
);
router.post(
  "/cart",
  verifyToken,
  checkRole(["USER"]),
  orderController.buyCart
);



module.exports = router;
