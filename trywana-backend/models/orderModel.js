const pool = require("../config/db");

// Create Order
exports.createOrder = async (user_id, product_id, quantity) => {
  // Get product price
  const productResult = await pool.query(
    "SELECT * FROM products WHERE id = $1",
    [product_id]
  );

  if (productResult.rows.length === 0) {
    throw new Error("Product not found");
  }

  const product = productResult.rows[0];

  if (product.stock < quantity) {
    throw new Error("Not enough stock available");
  }

  const total_price = product.price * quantity;

  // Insert order
  const orderResult = await pool.query(
    `INSERT INTO orders 
     (user_id, product_id, quantity, total_price) 
     VALUES ($1,$2,$3,$4) 
     RETURNING *`,
    [user_id, product_id, quantity, total_price]
  );

  // Reduce stock
  await pool.query(
    `UPDATE products SET stock = stock - $1 WHERE id = $2`,
    [quantity, product_id]
  );

  return orderResult.rows[0];
};

// User Orders
exports.getUserOrders = async (user_id) => {
  const result = await pool.query(
    `SELECT o.*, p.title 
     FROM orders o
     JOIN products p ON o.product_id = p.id
     WHERE o.user_id = $1
     ORDER BY o.created_at DESC`,
    [user_id]
  );

  return result.rows;
};

// Seller Orders
exports.getSellerOrders = async (seller_id) => {
  const result = await pool.query(
    `SELECT o.*, p.title 
     FROM orders o
     JOIN products p ON o.product_id = p.id
     WHERE p.seller_id = $1
     ORDER BY o.created_at DESC`,
    [seller_id]
  );

  return result.rows;
};

// Update Order Status (Seller)
exports.updateOrderStatus = async (order_id, seller_id, status) => {
  // Allowed statuses
  const allowedStatuses = ["CONFIRMED", "SHIPPED", "DELIVERED"];

  if (!allowedStatuses.includes(status)) {
    throw new Error("Invalid status value");
  }

  // Ensure seller owns the product
  const result = await pool.query(
    `UPDATE orders o
     SET status = $1
     FROM products p
     WHERE o.product_id = p.id
     AND o.id = $2
     AND p.seller_id = $3
     RETURNING o.*`,
    [status, order_id, seller_id]
  );

  return result.rows[0];
};
