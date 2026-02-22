const orderModel = require("../models/orderModel");

exports.placeOrder = async (req, res, next) => {
  try {
    const user_id = req.user.id;
    const { product_id, quantity } = req.body;

    const order = await orderModel.createOrder(
      user_id,
      product_id,
      quantity
    );

    res.status(201).json({
      success: true,
      message: "Order placed successfully",
      data: order,
    });
  } catch (error) {
    next(error);
  }
};

exports.getMyOrders = async (req, res, next) => {
  try {
    const orders = await orderModel.getUserOrders(req.user.id);

    res.json({
      success: true,
      data: orders,
    });
  } catch (error) {
    next(error);
  }
};

exports.getSellerOrders = async (req, res, next) => {
  try {
    const orders = await orderModel.getSellerOrders(req.user.id);

    res.json({
      success: true,
      data: orders,
    });
  } catch (error) {
    next(error);
  }
};

exports.updateOrderStatus = async (req, res, next) => {
  try {
    const updated = await orderModel.updateOrderStatus(
      req.params.id,
      req.user.id,
      req.body.status
    );

    if (!updated) {
      const error = new Error("Order not found or not yours");
      error.status = 404;
      throw error;
    }

    res.json({
      success: true,
      message: "Order status updated successfully",
      data: updated,
    });
  } catch (error) {
    next(error);
  }
};
