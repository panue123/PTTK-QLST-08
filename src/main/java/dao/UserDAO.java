package dao;

import org.mindrot.jbcrypt.BCrypt;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public Integer login(String username, String password) throws SQLException {
        String sql = "SELECT id, password FROM user WHERE username = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPass = rs.getString("password");
                    if (BCrypt.checkpw(password, hashedPass)) {
                        return rs.getInt("id");
                    }
                }
                return null;
            }
        }
    }
}
