package it.polito.tdp.crimes.db;

public class TestDao {

	public static void main(String[] args) {
		EventsDao dao = new EventsDao();
		System.out.println(dao.distrettoMinimo(2014));
	}

}
