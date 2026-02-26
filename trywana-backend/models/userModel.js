const pool = require("../config/db");

exports.createUser = async (name, email, phone, password, role) => {
  const result = await pool.query(
    `INSERT INTO users (name, email, phone, password, role)
     VALUES ($1,$2,$3,$4,$5) RETURNING *`,
    [name, email, phone, password, role]
  );
  return result.rows[0];
};

exports.findUserByEmail = async (email) => {
  const result = await pool.query(
    "SELECT * FROM users WHERE email=$1",
    [email]
  );
  return result.rows[0];
};
exports.createGoogleUser = async (name, email, googleId, role) => {
  const query = `
    INSERT INTO users (name, email, google_id, role)
    VALUES ($1, $2, $3, $4)
    RETURNING *;
  `;

  const values = [name, email, googleId, role];
  const result = await pool.query(query, values);
  return result.rows[0];
};
