const requiredEnv = ["DB_USER","DB_HOST","DB_NAME","DB_PASSWORD","JWT_SECRET"];

requiredEnv.forEach((key) => {
  if (!process.env[key]) {
    console.error(`❌ Missing environment variable: ${key}`);
    process.exit(1);
  }
});
