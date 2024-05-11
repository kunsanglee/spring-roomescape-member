package roomescape.infrastructure.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import roomescape.domain.Member;
import roomescape.domain.PlayerName;
import roomescape.domain.Role;

public class MemberRowMapper {

    private MemberRowMapper() {
    }

    public static Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String role = rs.getString("role");
        return new Member(id, new PlayerName(name), email, password, Role.valueOf(role));
    }

    public static Member joinedMapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("member_id");
        String name = rs.getString("member_name");
        String email = rs.getString("member_email");
        String password = rs.getString("member_password");
        String role = rs.getString("member_role");
        return new Member(id, new PlayerName(name), email, password, Role.valueOf(role));
    }
}
