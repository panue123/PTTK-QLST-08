package dao;

import model.Address;
import model.Customer;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    public List<Address> getAddressOfCustomer(int customerId) throws SQLException {
        String sql = "SELECT id, street, province, country FROM tblAddress WHERE customerId = ?";

        List<Address> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(customerId);

                    Address a = new Address();
                    a.setId(rs.getInt("id"));
                    a.setCustomer(customer);
                    a.setStreet(rs.getString("street"));
                    a.setProvince(rs.getString("province"));
                    a.setCountry(rs.getString("country"));

                    list.add(a);
                }
            }
        }
        return list;
    }
}
