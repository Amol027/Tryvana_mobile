const productModel = require("../models/productModel");

exports.addProduct = async (req, res, next) => {
  try {
    if (!req.body.title || !req.body.price) {
      const error = new Error("Title and price are required");
      error.status = 400;
      throw error;
    }

    const imageUrl = req.file ? req.file.path : null;

    const product = await productModel.createProduct({
      seller_id: req.user.id,
      ...req.body,
      image_url: imageUrl
    });

    res.status(201).json({
      success: true,
      message: "Product added successfully",
      data: product,
    });
  } catch (error) {
    next(error);
  }
};



exports.getMyProducts = async (req, res, next) => {
  try {
    const products = await productModel.getSellerProducts(req.user.id);

    res.json({
      success: true,
      data: {
        products: products || []  
      },
    });
  } catch (error) {
    next(error);
  }
};

exports.updateProduct = async (req, res, next) => {
  try {
    const updated = await productModel.updateProduct(
      req.params.id,
      req.user.id,
      req.body
    );

    if (!updated) {
      const error = new Error("Product not found or not yours");
      error.status = 404;
      throw error;
    }

    res.json({
      success: true,
      message: "Product updated successfully",
      data: updated,
    });
  } catch (error) {
    next(error);
  }
};

exports.deleteProduct = async (req, res, next) => {
  try {
    const deleted = await productModel.deleteProduct(
      req.params.id,
      req.user.id
    );

    if (!deleted) {
      const error = new Error("Product not found or not yours");
      error.status = 404;
      throw error;
    }

    res.json({
      success: true,
      message: "Product deleted successfully",
    });
  } catch (error) {
    next(error);
  }
};

exports.getAllProducts = async (req, res, next) => {
  try {
    const page = parseInt(req.query.page) || 1;
    const limit = 5;
    const offset = (page - 1) * limit;
    const search = req.query.search || "";

    const products = await productModel.getAllProducts(limit, offset, search);

    res.json({
      success: true,
      data: {
        page,
        products,
      },
    });
  } catch (error) {
    next(error);
  }
};
