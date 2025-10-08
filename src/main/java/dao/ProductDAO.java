package dao;

import model.Product;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO{

    public List<Product> getProductByName(String name) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name LIKE ? AND quantity > 0 ORDER BY id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setPrice(rs.getFloat("price"));
                    p.setQuantity(rs.getInt("quantity"));
                    products.add(p);
                }
            }
        }
        return products;
    }

    public List<Product> getAllProduct() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE quantity > 0 ORDER BY id DESC";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getFloat("price"));
                p.setQuantity(rs.getInt("quantity"));
                products.add(p);
            }
        }
        return products;
    }

    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE product SET name=?, description=?, price=?, quantity=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setFloat(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setInt(5, product.getId());
            ps.executeUpdate();
        }
    }

    public Product getProductDetail(int productId) throws SQLException {
        String sql = "SELECT * FROM product WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setPrice(rs.getFloat("price"));
                    p.setQuantity(rs.getInt("quantity"));
                    return p;
                }
            }
        }
        return null;
    }
}
