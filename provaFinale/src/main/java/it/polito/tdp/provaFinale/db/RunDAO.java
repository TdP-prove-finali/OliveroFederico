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
	
	public Integer getIstanceSuccesses(Istance is) {
		
		final String sql = "SELECT COUNT(*) AS successi FROM (SELECT * fROM runs r WHERE r.istance = "+is.getId()+" AND r.`status` = 1 GROUP BY r.date) AS tabella";
		
		Integer ritorno = null;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			if(res.next())
				ritorno = res.getInt("successi");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return ritorno;
		
	}
	
	public Integer getMaxFailur(Istance is) {
		
		final String sql = "SELECT MAX(dailyFailur) AS maxFailur FROM (SELECT COUNT(*) dailyFailur fROM runs r WHERE r.istance = "+is.getId()+" AND r.`status` = 0 GROUP BY r.date) AS tabella";
		
		Integer ritorno = null;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			if(res.next())
				ritorno = res.getInt("maxFailur");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return ritorno;
		
	}
	
	public Integer getFailurDays(Istance is) {
		
		final String sql = "SELECT COUNT(DISTINCT r.date) AS numberOfFailurDays FROM runs r WHERE r.istance = '"+is.getId()+"' AND r.`status` = '0'";
		
		Integer ritorno = null;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			if(res.next())
				ritorno = res.getInt("numberOfFailurDays");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return ritorno;
		
	}
	
	public Double getAverageFailurs(Istance is) {
		
		final String sql = "SELECT AVG(dailyFailur) AS avgFailur FROM (SELECT COUNT(*) dailyFailur fROM runs r WHERE r.istance = "+is.getId()+" AND r.`status` = 0 GROUP BY r.date) AS tabella";
		
		Double ritorno = null;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			if(res.next())
				ritorno = res.getDouble("avgFailur");
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return ritorno;
	}
}
