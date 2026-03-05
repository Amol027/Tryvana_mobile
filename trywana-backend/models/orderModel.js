const pool = require("../config/db");

// Create Order
// exports.createOrder = async (user_id, product_id, quantity) => {
//   // Get product price
//   const productResult = await pool.query(
//     "SELECT * FROM products WHERE id = $1",
//     [product_id]
//   );

//   if (productResult.rows.length === 0) {
//     throw new Error("Product not found");
//   }

//   const product = productResult.rows[0];

//   if (product.stock < quantity) {
//     throw new Error("Not enough stock available");
//   }

//   const total_price = product.price * quantity;

//   // Insert order
//   const orderResult = await pool.query(
//     `INSERT INTO orders 
//      (user_id, product_id, quantity, total_price) 
//      VALUES ($1,$2,$3,$4) 
//      RETURNING *`,
//     [user_id, product_id, quantity, total_price]
//   );

//   // Reduce stock
//   await pool.query(
//     `UPDATE products SET stock = stock - $1 WHERE id = $2`,
//     [quantity, product_id]
//   );

//   return orderResult.rows[0];
// };

// User Orders
exports.getUserOrders = async (user_id) => {
  const result = await pool.query(
    `SELECT 
        o.id AS order_id,
        o.total_price,
        o.status,
        o.created_at,
        json_agg(
          json_build_object(
            'product_id', oi.product_id,
            'quantity', oi.quantity,
            'price', oi.price,
            'title', p.title
          )
        ) AS items
     FROM orders o
     JOIN order_items oi ON o.id = oi.order_id
     JOIN products p ON oi.product_id = p.id
     WHERE o.user_id = $1
     GROUP BY o.id
     ORDER BY o.created_at DESC`,
    [user_id]
  );

  return result.rows;
};


// Seller Orders
exports.getSellerOrders = async (seller_id) => {
  const result = await pool.query(
    `SELECT 
        o.id AS order_id,
        o.total_price,
        o.status,
        o.created_at,
        json_agg(
          json_build_object(
            'product_id', oi.product_id,
            'quantity', oi.quantity,
            'price', oi.price,
            'title', p.title
          )
        ) AS items
     FROM orders o
     JOIN order_items oi ON o.id = oi.order_id
     JOIN products p ON oi.product_id = p.id
     WHERE p.seller_id = $1
     GROUP BY o.id
     ORDER BY o.created_at DESC`,
    [seller_id]
  );

  return result.rows;
};

// Update Order Status (Seller)
exports.updateOrderStatus = async (order_id, seller_id, status) => {

  const allowedStatuses = ["CONFIRMED", "SHIPPED", "DELIVERED"];

  if (!allowedStatuses.includes(status)) {
    throw new Error("Invalid status value");
  }

  const result = await pool.query(
    `UPDATE orders
     SET status = $1
     WHERE id = $2
     RETURNING *`,
    [status, order_id]
  );

  return result.rows[0];
};


exports.createOrderFromCart = async (user_id) => {
  const client = await pool.connect();
  try {
    await client.query("BEGIN");

    // 1. Get items from cart
    const cartItems = await client.query(
      "SELECT * FROM cart WHERE user_id=$1",
      [user_id]
    );

    console.log("DEBUG: Cart items found:", cartItems.rows.length);

    if (cartItems.rows.length === 0) throw new Error("Cart is empty");

    // 2. Create main order entry
    const order = await client.query(
      "INSERT INTO orders (user_id, total_price) VALUES ($1, 0) RETURNING *",
      [user_id]
    );

    let total = 0;

    // 3. Loop through cart items
    for (let item of cartItems.rows) {
      console.log(`DEBUG: Checking Product ID: ${item.product_id}`); // 👈 Check point

      const product = await client.query(
        "SELECT * FROM products WHERE id=$1 FOR UPDATE",
        [item.product_id]
      );

      // AGAR YAHAN ERROR AA RAHA HAI:
      if (product.rows.length === 0) {
        throw new Error(`Product with ID ${item.product_id} not found in products table`);
      }

      const productData = product.rows[0];
      
      if (productData.stock < item.quantity) {
        throw new Error(`Not enough stock for ${productData.title}`);
      }

      const itemTotal = parseFloat(productData.price) * item.quantity;
      total += itemTotal;

      // 4. Insert into order_items
      await client.query(
        `INSERT INTO order_items (order_id, product_id, quantity, price)
         VALUES ($1, $2, $3, $4)`,
        [order.rows[0].id, item.product_id, item.quantity, productData.price]
      );

      // 5. Reduce stock
      await client.query(
        "UPDATE products SET stock = stock - $1 WHERE id=$2",
        [item.quantity, item.product_id]
      );
    }

    // 6. Update total price & Clear cart
    await client.query("UPDATE orders SET total_price=$1 WHERE id=$2", [total, order.rows[0].id]);
    await client.query("DELETE FROM cart WHERE user_id=$1", [user_id]);

    await client.query("COMMIT");
    return order.rows[0];

  } catch (err) {
    await client.query("ROLLBACK");
    console.error("❌ Transaction Failed:", err.message); // 👈 Terminal mein exact message dikhega
    throw err;
  } finally {
    client.release();
  }
};
