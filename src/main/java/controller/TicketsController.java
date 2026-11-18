package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Movie;
import model.Ticket;
import model.dao.DAOFactory;
import model.dao.TicketDAO;

@WebServlet(urlPatterns = {"/tickets", "/ticket/form", "/ticket/insert", "/ticket/delete", "/ticket/update"})
public class TicketsController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/ticket/form": {
			CommonsController.listMovies(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-ticket.jsp");
			break;
		}
		case "/crud-manager/ticket/update": {
			String idStr = req.getParameter("ticketId");
			int idTicket = Integer.parseInt(idStr);
			
			TicketDAO dao = DAOFactory.createDAO(TicketDAO.class);
			Ticket ticket = null;
			try {
				ticket = dao.findById(idTicket);
			} catch (ModelException e) {
				e.printStackTrace();
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			
			CommonsController.listMovies(req);
			req.setAttribute("action", "update");
			req.setAttribute("ticket", ticket);
			ControllerUtil.forward(req, resp, "/form-ticket.jsp");
			break;
		}
		default:
			listTickets(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/tickets.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/ticket/insert":
			insertTicket(req, resp);
			break;
		case "/crud-manager/ticket/delete":
			deleteTicket(req, resp);
			break;
		case "/crud-manager/ticket/update":
			updateTicket(req, resp);
			break;
		default:
			System.out.println("URL inválida " + action);
		}
		
		ControllerUtil.redirect(resp, req.getContextPath() + "/tickets");
	}

	private void listTickets(HttpServletRequest req) {
		TicketDAO dao = DAOFactory.createDAO(TicketDAO.class);
		
		List<Ticket> tickets = null;
		int currentPage = 1;
		int pageSize = 5;

		String pageParam = req.getParameter("page");
		if (pageParam != null && !pageParam.isEmpty()) {
			try {
				currentPage = Integer.parseInt(pageParam);
			} catch (NumberFormatException e) {
				currentPage = 1;
			}
		}

		try {
			int totalTickets = dao.count();
			int totalPages = (int) Math.ceil((double) totalTickets / pageSize);
			if (currentPage < 1) {
				currentPage = 1;
			}
			if (currentPage > totalPages && totalPages > 0) {
				currentPage = totalPages;
			}
			tickets = dao.listPaginated(currentPage, pageSize);

			req.setAttribute("tickets", tickets);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("totalTickets", totalTickets);
			req.setAttribute("pageUrl", req.getContextPath() + "/tickets");

		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void insertTicket(HttpServletRequest req, HttpServletResponse resp) {
		String dateStr = req.getParameter("start");
		String timeStr = req.getParameter("startTime");
		double price = Double.parseDouble(req.getParameter("price"));
		boolean halfPriceTicket = req.getParameter("halfPriceTicket") != null;
		int movieId = Integer.parseInt(req.getParameter("movie_id"));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		Ticket ticket = new Ticket();
		try {
			ticket.setStart(dateFormat.parse(dateStr));
			ticket.setStartTime(timeFormat.parse(timeStr));
		} catch (ParseException e) {
			ticket.setStart(new Date());
			ticket.setStartTime(new Date());
		}

		ticket.setPrice(price);
		ticket.setHalfPriceTicket(halfPriceTicket);
		ticket.setMovie(new Movie(movieId));

		TicketDAO dao = DAOFactory.createDAO(TicketDAO.class);
		try {
			if (dao.save(ticket)) {
				ControllerUtil.sucessMessage(req, "Ingresso salvo com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Ingresso não pode ser salvo.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateTicket(HttpServletRequest req, HttpServletResponse resp) {
		int ticketId = Integer.parseInt(req.getParameter("ticketId"));
		String dateStr = req.getParameter("start");
		String timeStr = req.getParameter("startTime");
		double price = Double.parseDouble(req.getParameter("price"));
		boolean halfPriceTicket = req.getParameter("halfPriceTicket") != null;
		int movieId = Integer.parseInt(req.getParameter("movie_id"));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		Ticket ticket = new Ticket(ticketId);
		try {
			ticket.setStart(dateFormat.parse(dateStr));
			ticket.setStartTime(timeFormat.parse(timeStr));
		} catch (ParseException e) {
			ticket.setStart(new Date());
			ticket.setStartTime(new Date());
		}

		ticket.setPrice(price);
		ticket.setHalfPriceTicket(halfPriceTicket);
		ticket.setMovie(new Movie(movieId));

		TicketDAO dao = DAOFactory.createDAO(TicketDAO.class);
		try {
			if (dao.update(ticket)) {
				ControllerUtil.sucessMessage(req, "Ingresso atualizado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Ingresso não pode ser atualizado.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteTicket(HttpServletRequest req, HttpServletResponse resp) {
		int ticketId = Integer.parseInt(req.getParameter("id"));
		Ticket ticket = new Ticket(ticketId);

		TicketDAO dao = DAOFactory.createDAO(TicketDAO.class);
		try {
			if (dao.delete(ticket)) {
				ControllerUtil.sucessMessage(req, "Ingresso deletado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Ingresso não pode ser deletado.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
