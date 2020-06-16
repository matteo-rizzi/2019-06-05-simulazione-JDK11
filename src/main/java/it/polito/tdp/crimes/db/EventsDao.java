package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.crimes.model.CoordinataDistretto;
import it.polito.tdp.crimes.model.Event;

public class EventsDao {

	public List<Event> listAllEvents() {
		String sql = "SELECT * FROM events";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Event> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"), res.getInt("offense_code"),
							res.getInt("offense_code_extension"), res.getString("offense_type_id"),
							res.getString("offense_category_id"), res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"), res.getDouble("geo_lon"), res.getDouble("geo_lat"),
							res.getInt("district_id"), res.getInt("precinct_id"), res.getString("neighborhood_id"),
							res.getInt("is_crime"), res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Integer> getAnni() {
		String sql = "SELECT DISTINCT YEAR(reported_date) AS anno FROM events";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Integer> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(res.getInt("anno"));
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getDistretti() {
		String sql="SELECT DISTINCT district_id FROM events";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Integer> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(res.getInt("district_id"));
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<CoordinataDistretto> getCoordinateDistretti(int anno) {
		String sql="SELECT district_id, AVG(geo_lon) AS mediaLon, AVG(geo_lat) AS mediaLat FROM events WHERE YEAR(reported_date) = ? GROUP BY district_id";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);

			List<CoordinataDistretto> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new CoordinataDistretto(res.getInt("district_id"), res.getDouble("mediaLon"), res.getDouble("mediaLat")));
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getMesi(int anno) {
		String sql = "SELECT DISTINCT MONTH(reported_date) AS mese FROM EVENTS WHERE YEAR(reported_date) = ? ORDER BY MONTH(reported_date)";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);

			List<Integer> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(res.getInt("mese"));
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getGiorni(int anno, int mese) {
		String sql = "SELECT DISTINCT DAY(reported_date) AS giorno FROM EVENTS WHERE YEAR(reported_date) = ? AND MONTH(reported_date) = ? ORDER BY DAY(reported_date)";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, mese);

			List<Integer> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(res.getInt("giorno"));
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer distrettoMinimo(int anno) {
		String sql = "SELECT district_id, COUNT(incident_id) AS minimo FROM EVENTS WHERE YEAR(reported_date) = ? GROUP BY district_id ORDER BY COUNT(incident_id)";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);

			ResultSet res = st.executeQuery();
			res.next();
		
			conn.close();
			
			return res.getInt("district_id");

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Event> getEventsByAnnoMeseGiorno(int anno, int mese, int giorno) {
		String sql = "SELECT * FROM events WHERE YEAR(reported_date) = ? AND MONTH(reported_date) = ? AND DAY(reported_date) =? ORDER BY reported_date";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);

			List<Event> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"), res.getInt("offense_code"),
							res.getInt("offense_code_extension"), res.getString("offense_type_id"),
							res.getString("offense_category_id"), res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"), res.getDouble("geo_lon"), res.getDouble("geo_lat"),
							res.getInt("district_id"), res.getInt("precinct_id"), res.getString("neighborhood_id"),
							res.getInt("is_crime"), res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
