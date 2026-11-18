package model.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.ModelException;
import model.Movie;

public class MySQLMovieDAO implements MovieDAO {

	@Override
	public boolean save(Movie movie) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlInsert = "INSERT INTO movies (title, duration, color, file, company_id) "
				+ " VALUES (?, ?, ?, ?, ?);";
		
		db.prepareStatement(sqlInsert);
		db.setString(1, movie.getTitle());
		db.setInt(2, movie.getDuration());
		db.setString(3, movie.getColor());
		db.setString(4, movie.getFile());
		db.setInt(5, movie.getCompany().getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Movie movie) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlUpdate = "UPDATE movies SET "
				+ " title = ?, "
				+ " duration = ?, "
				+ " color = ?, "
				+ " file = ?, "
				+ " company_id = ? "
				+ " WHERE id = ?;";
		
		db.prepareStatement(sqlUpdate);
		db.setString(1, movie.getTitle());
		db.setInt(2, movie.getDuration());
		db.setString(3, movie.getColor());
		db.setString(4, movie.getFile());
		db.setInt(5, movie.getCompany().getId());
		db.setInt(6, movie.getId());
		
		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Movie movie) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sqlDelete = "DELETE FROM movies WHERE id = ?;";
		
		db.prepareStatement(sqlDelete);
		db.setInt(1, movie.getId());
		
		try {
			return db.executeUpdate() > 0;
		} catch (ModelException me) {
			if(me.getCause() instanceof SQLIntegrityConstraintViolationException)
				return false;
				
			throw me;
		}
		
	}

	@Override
	public List<Movie> listAll() throws ModelException {
		DBHandler db = new DBHandler();
		List<Movie> movies = new ArrayList<>();
		
		String sql = "SELECT m.*, c.name as company_name, c.role as company_role "
				+ " FROM movies m "
				+ " LEFT JOIN companies c ON m.company_id = c.id "
				+ " ORDER BY m.id;";
		
		db.createStatement();
		db.executeQuery(sql);
		
		while (db.next()) {
			movies.add(createMovie(db));
		}
		
		return movies;
	}

	@Override
	public Movie findById(int id) throws ModelException {
		DBHandler db = new DBHandler();
		
		String sql = "SELECT m.*, c.name as company_name, c.role as company_role "
				+ " FROM movies m "
				+ " LEFT JOIN companies c ON m.company_id = c.id "
				+ " WHERE m.id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Movie movie = null;
		if (db.next()) {
			movie = createMovie(db);
		}
		
		return movie;
	}

	@Override
	public int count() throws ModelException {
		DBHandler db = new DBHandler();
		String sql = "SELECT COUNT(*) AS total FROM movies;";
		db.prepareStatement(sql);
		db.executeQuery();
		
		if (db.next()) {
			return db.getInt("total");
		}
		return 0;
	}

	@Override
	public List<Movie> listPaginated(int currentPage, int pageSize) throws ModelException {
		DBHandler db = new DBHandler();
		List<Movie> movies = new ArrayList<>();
		
		int offset = (currentPage - 1) * pageSize;
		
		String sql = "SELECT m.*, c.name as company_name, c.role as company_role "
				+ " FROM movies m "
				+ " LEFT JOIN companies c ON m.company_id = c.id "
				+ " ORDER BY m.id "
				+ " LIMIT ?, ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, offset);
		db.setInt(2, pageSize);
		db.executeQuery();
		
		while (db.next()) {
			movies.add(createMovie(db));
		}
		
		return movies;
	}

	private Movie createMovie(DBHandler db) throws ModelException {
		Movie movie = new Movie(db.getInt("id"));
		movie.setTitle(db.getString("title"));
		movie.setDuration(db.getInt("duration"));
		movie.setColor(db.getString("color"));
		movie.setFile(db.getString("file"));
		
		Company company = new Company(db.getInt("company_id"));
		company.setName(db.getString("company_name"));
		company.setRole(db.getString("company_role"));
		movie.setCompany(company);
		
		return movie;
	}
}
