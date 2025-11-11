package dao;

import model.OnlineOrder;
import model.OrderDetail;
import util.DBUtil;

import java.sql.*;
import java.util.Comparator;
import java.util.List;

public class OnlineOrderDAO {

    public int createOrder(OnlineOrder order, List<OrderDetail> orderDetails) throws Exception {
        int orderId = -1;

        try (Connection conn = DBUtil.getConnection()) {
            try {
                conn.setAutoCommit(false);

                String sqlOrder = "INSERT INTO tblOnlineOrder (customerId, addressId, status, orderDate) VALUES (?,?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, order.getCustomer().getId());
                    ps.setInt(2, order.getAddress().getId());
                    ps.setString(3, order.getStatus());
                    ps.setTimestamp(4, Timestamp.valueOf(order.getOrderDate()));
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            orderId = rs.getInt(1);
                        }
                    }
                }

                if (orderId <= 0) {
                    throw new SQLException("Failed to create order.");
                }

                orderDetails.sort(Comparator.comparingInt(od -> od.getProduct().getId()));

                String selectProduct = "SELECT quantity FROM tblProduct WHERE id = ? FOR UPDATE";
                String updateProduct = "UPDATE tblProduct SET quantity = quantity - ? WHERE id = ?";
                String insertOrderDetail = "INSERT INTO tblOrderDetail (onlineOrderId, productId, price, quantity) VALUES (?,?,?,?)";

                try (PreparedStatement psSelect = conn.prepareStatement(selectProduct);
                     PreparedStatement psUpdate = conn.prepareStatement(updateProduct);
                     PreparedStatement psInsertOD = conn.prepareStatement(insertOrderDetail)) {

                    for (OrderDetail od : orderDetails) {
                        int productId = od.getProduct().getId();
                        int soldQty = od.getQuantity();

                        psSelect.setInt(1, productId);
                        try (ResultSet rs = psSelect.executeQuery()) {
                            if (rs.next()) {
                                int currentQty = rs.getInt("quantity");
                                if (currentQty < soldQty) {
                                    throw new SQLException("Not enough stock for product ID: " + productId);
                                }
                            } else {
                                throw new SQLException("Product not found ID: " + productId);
                            }
                        }

                        psUpdate.setInt(1, soldQty);
                        psUpdate.setInt(2, productId);
                        psUpdate.executeUpdate();

                        psInsertOD.setInt(1, orderId);
                        psInsertOD.setInt(2, productId);
                        psInsertOD.setDouble(3, od.getPrice());
                        psInsertOD.setInt(4, soldQty);
                        psInsertOD.executeUpdate();
                    }
                }

                conn.commit();
                return orderId;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
