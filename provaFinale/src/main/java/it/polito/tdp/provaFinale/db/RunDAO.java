package it.polito.tdp.provaFinale.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import it.polito.tdp.provaFinale.model.Istance;

public class RunDAO {

	public Integer getAvgSuccessDuration(Istance is) {
		
		final String sql = "SELECT r.istance, AVG(r.duration) FROM runs r WHERE r.`date`> '2023/10/15' AND r.`status` = 1 AND r.istance = "+is.getId()+" GROUP BY r.istance ";
		Integer avgDuration = 0;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			if(res.next())
				avgDuration = res.getInt(2);
			else
				avgDuration = null;
			/*while (res.next()) {
				
				avgDuration = res.getInt(1);
			}*/
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return avgDuration;
	}
}
