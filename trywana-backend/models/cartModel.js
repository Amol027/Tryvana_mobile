const pool = require("../config/db");

// models/cartModel.js

// 1. Add to Cart Query
exports.addToCart = async (user_id, product_id, quantity) => {
  return await pool.query(
    `INSERT INTO cart (user_id, product_id, quantity) 
     VALUES ($1, $2, $3) 
     ON CONFLICT (user_id, product_id) 
     DO UPDATE SET quantity = cart.quantity + $3 
     RETURNING *`,
    [user_id, product_id, quantity]
  );
};

// 2. Get Cart Query (YAHAN ERROR HAI)
exports.getCart = async (user_id) => {
  const result = await pool.query(
    `SELECT 
        c.id, 
        c.product_id, -- 👈 Make sure yahan product_id likha ho, productId nahi
        c.quantity, 
        p.title, 
        p.price, 
        p.image_url 
     FROM cart c 
     JOIN products p ON c.product_id = p.id 
     WHERE c.user_id = $1`,
    [user_id]
  );
  return result.rows;
};


exports.removeFromCart = async (user_id, product_id) => {
  await pool.query(
    "DELETE FROM cart WHERE user_id=$1 AND product_id=$2",
    [user_id, product_id]
  );
};
