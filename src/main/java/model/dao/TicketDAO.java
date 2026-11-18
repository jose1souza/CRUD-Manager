package model.dao;

import java.util.List;

import model.ModelException;
import model.Ticket;

public interface TicketDAO {
	boolean save(Ticket ticket) throws ModelException;
	boolean update(Ticket ticket) throws ModelException;
	boolean delete(Ticket ticket) throws ModelException;
	List<Ticket> listAll() throws ModelException;
	Ticket findById(int id) throws ModelException;
	int count() throws ModelException;
	List<Ticket> listPaginated(int currentPage, int pageSize) throws ModelException;
}
