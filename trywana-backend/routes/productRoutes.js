
const express = require("express");
const router = express.Router();
const productController = require("../controllers/productController");
const { verifyToken } = require("../middleware/authMiddleware");
const { checkRole } = require("../middleware/roleMiddleware");

// Seller Routes
router.post("/add", verifyToken, checkRole(["SELLER"]), productController.addProduct);

router.get("/my", verifyToken, checkRole(["SELLER"]), productController.getMyProducts);

router.put("/:id", verifyToken, checkRole(["SELLER"]), productController.updateProduct);

router.delete("/:id", verifyToken, checkRole(["SELLER"]), productController.deleteProduct);

// Public Route
router.get("/all", productController.getAllProducts);

module.exports = router;
