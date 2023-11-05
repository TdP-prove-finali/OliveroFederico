package it.polito.tdp.provaFinale.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.provaFinale.model.Istance;

public class IstanceDAO {
	
	
	public List<Istance> getAllIstances() {
		
		final String sql = "SELECT * FROM istances";
		List<Istance> result = new ArrayList<Istance>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
				Istance is = new Istance(res.getInt("id"), res.getString("scenary"), res.getString("company"), res.getString("product")); 
				result.add(is);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
}
