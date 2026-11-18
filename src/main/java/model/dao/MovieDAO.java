package model.dao;

import java.util.List;

import model.ModelException;
import model.Movie;

public interface MovieDAO {
	boolean save(Movie movie) throws ModelException;
	boolean update(Movie movie) throws ModelException;
	boolean delete(Movie movie) throws ModelException;
	List<Movie> listAll() throws ModelException;
	Movie findById(int id) throws ModelException;
	int count() throws ModelException;
	List<Movie> listPaginated(int currentPage, int pageSize) throws ModelException;
}
