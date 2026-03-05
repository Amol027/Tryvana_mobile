const cartModel = require("../models/cartModel");

exports.addToCart = async (req, res, next) => {
  try {
    const { product_id, quantity } = req.body;

    const item = await cartModel.addToCart(
      req.user.id,
      product_id,
      quantity
    );

    res.json({ success: true, data: item.rows[0] });
  } catch (err) {
    next(err);
  }
};

exports.getCart = async (req, res, next) => {
  try {
    const items = await cartModel.getCart(req.user.id);
    res.json({ success: true, data: items });
  } catch (err) {
    next(err);
  }
};

exports.removeFromCart = async (req, res, next) => {
  try {
    await cartModel.removeFromCart(req.user.id, req.params.productId);
    res.json({ success: true, message: "Item removed" });
  } catch (err) {
    next(err);
  }
};
