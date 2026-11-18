package model.dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.ModelException;
import model.Movie;
import model.Ticket;

public class MySQLTicketDAO implements TicketDAO {

	@Override
	public boolean save(Ticket ticket) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "INSERT INTO tickets (start, start_time, price, half_price_ticket, movie_id) "
				+ " VALUES (?, ?, ?, ?, ?);";
		
		db.prepareStatement(sql);
		db.setDate(1, ticket.getStart());
		
		// Converte Date com hora para java.sql.Time
		Time time = new Time(ticket.getStartTime().getTime());
		db.setTime(2, time);
		
		db.setDouble(3, ticket.getPrice());
		db.setBoolean(4, ticket.isHalfPriceTicket());
		db.setInt(5, ticket.getMovie().getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Ticket ticket) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "UPDATE tickets SET "
				+ " start = ?, "
				+ " start_time = ?, "
				+ " price = ?, "
				+ " half_price_ticket = ?, "
				+ " movie_id = ? "
				+ " WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setDate(1, ticket.getStart());
		
		Time time = new Time(ticket.getStartTime().getTime());
		db.setTime(2, time);
		
		db.setDouble(3, ticket.getPrice());
		db.setBoolean(4, ticket.isHalfPriceTicket());
		db.setInt(5, ticket.getMovie().getId());
		db.setInt(6, ticket.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Ticket ticket) throws ModelException {
		DBHandler db = new DBHandler();
		String sql = "DELETE FROM tickets WHERE id = ?;";
		db.prepareStatement(sql);
		db.setInt(1, ticket.getId());
		return db.executeUpdate() > 0;
	}

	@Override
	public List<Ticket> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		List<Ticket> tickets = new ArrayList<>();
		
		String sql = "SELECT t.*, m.title as movie_title, m.color as movie_color, m.duration as movie_duration "
				+ " FROM tickets t "
				+ " LEFT JOIN movies m ON t.movie_id = m.id "
				+ " ORDER BY t.id;";
		
		db.createStatement();
		db.executeQuery(sql);
		
		while (db.next()) {
			tickets.add(createTicket(db));
		}
		
		return tickets;
	}

	@Override
	public Ticket findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "SELECT t.*, m.title as movie_title, m.color as movie_color, m.duration as movie_duration "
				+ " FROM tickets t "
				+ " LEFT JOIN movies m ON t.movie_id = m.id "
				+ " WHERE t.id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Ticket ticket = null;
		if (db.next()) {
			ticket = createTicket(db);
		}
		
		return ticket;
	}

	@Override
	public int count() throws ModelException {
		DBHandler db = new DBHandler();
		String sql = "SELECT COUNT(*) AS total FROM tickets;";
		db.prepareStatement(sql);
		db.executeQuery();
		
		if (db.next()) {
			return db.getInt("total");
		}
		return 0;
	}

	@Override
	public List<Ticket> listPaginated(int currentPage, int pageSize) throws ModelException {
		DBHandler db = new DBHandler();
		List<Ticket> tickets = new ArrayList<>();
		
		int offset = (currentPage - 1) * pageSize;
		
		String sql = "SELECT t.*, m.title as movie_title, m.color as movie_color, m.duration as movie_duration "
				+ " FROM tickets t "
				+ " LEFT JOIN movies m ON t.movie_id = m.id "
				+ " ORDER BY t.id "
				+ " LIMIT ?, ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, offset);
		db.setInt(2, pageSize);
		db.executeQuery();
		
		while (db.next()) {
			tickets.add(createTicket(db));
		}
		
		return tickets;
	}

	private Ticket createTicket(DBHandler db) throws ModelException {
		Ticket ticket = new Ticket(db.getInt("id"));
		ticket.setStart(db.getDate("start"));
		ticket.setStartTime(db.getTime("start_time"));
		ticket.setPrice(db.getDouble("price"));
		ticket.setHalfPriceTicket(db.getBoolean("half_price_ticket"));
		
		Movie movie = new Movie(db.getInt("movie_id"));
		movie.setTitle(db.getString("movie_title"));
		movie.setColor(db.getString("movie_color"));
		movie.setDuration(db.getInt("movie_duration"));
		ticket.setMovie(movie);
		
		return ticket;
	}
}
