

const pool = require("../config/db");

// Add Product
exports.createProduct = async (data) => {
  const { seller_id, title, description, price, stock, category, image_url } = data;

  const result = await pool.query(
    `INSERT INTO products 
     (seller_id, title, description, price, stock, category, image_url) 
     VALUES ($1,$2,$3,$4,$5,$6,$7) 
     RETURNING *`,
    [seller_id, title, description, price, stock, category, image_url]
  );

  return result.rows[0];
};

// Get Seller Products
exports.getSellerProducts = async (seller_id) => {
  const result = await pool.query(
    `SELECT * FROM products WHERE seller_id = $1 ORDER BY created_at DESC`,
    [seller_id]
  );

  return result.rows;
};

// Update Product
exports.updateProduct = async (id, seller_id, data) => {
  const { title, description, price, stock, category, image_url } = data;

  const result = await pool.query(
    `UPDATE products 
     SET title=$1, description=$2, price=$3, stock=$4, category=$5, image_url=$6
     WHERE id=$7 AND seller_id=$8
     RETURNING *`,
    [title, description, price, stock, category, image_url, id, seller_id]
  );

  return result.rows[0];
};

// Delete Product
exports.deleteProduct = async (id, seller_id) => {
  const result = await pool.query(
    `DELETE FROM products 
     WHERE id=$1 AND seller_id=$2
     RETURNING *`,
    [id, seller_id]
  );

  return result.rows[0];
};

// Public Products with Pagination + Search
exports.getAllProducts = async (limit, offset, search) => {
  let query = `SELECT * FROM products`;
  let values = [];

  if (search) {
    query += ` WHERE title ILIKE $1`;
    values.push(`%${search}%`);
  }

  query += ` ORDER BY created_at DESC LIMIT $${values.length + 1} OFFSET $${values.length + 2}`;
  values.push(limit, offset);

  const result = await pool.query(query, values);
  return result.rows;
};
