package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;
import model.ModelException;
import model.Movie;
import model.dao.DAOFactory;
import model.dao.MovieDAO;

@WebServlet(urlPatterns = {"/movies", "/movie/form", "/movie/insert", "/movie/delete", "/movie/update"})
public class MoviesController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/movie/form": {
			CommonsController.listCompanies(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-movie.jsp");
			break;
		}
		case "/crud-manager/movie/update": {
			String idStr = req.getParameter("movieId");
			int idMovie = Integer.parseInt(idStr);
			
			MovieDAO dao = DAOFactory.createDAO(MovieDAO.class);
			Movie movie = null;
			try {
				movie = dao.findById(idMovie);
			} catch (ModelException e) {
				e.printStackTrace();
				ControllerUtil.errorMessage(req, e.getMessage());
			}
			
			CommonsController.listCompanies(req);
			req.setAttribute("action", "update");
			req.setAttribute("movie", movie);
			ControllerUtil.forward(req, resp, "/form-movie.jsp");
			break;
		}
		default:
			listMovies(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/movies.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		String action = req.getRequestURI();
		
		switch (action) {
		case "/crud-manager/movie/insert":
			insertMovie(req, resp);
			break;
		case "/crud-manager/movie/delete":
			deleteMovie(req, resp);
			break;
		case "/crud-manager/movie/update":
			updateMovie(req, resp);
			break;
		default:
			System.out.println("URL inválida " + action);
		}
		
		ControllerUtil.redirect(resp, req.getContextPath() + "/movies");
	}

	private void listMovies(HttpServletRequest req) {
		MovieDAO dao = DAOFactory.createDAO(MovieDAO.class);
		
		List<Movie> movies = null;
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
			int totalMovies = dao.count();
			int totalPages = (int) Math.ceil((double) totalMovies / pageSize);
			if (currentPage < 1) {
				currentPage = 1;
			}
			if (currentPage > totalPages && totalPages > 0) {
				currentPage = totalPages;
			}
			movies = dao.listPaginated(currentPage, pageSize);

			req.setAttribute("movies", movies);
			req.setAttribute("currentPage", currentPage);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("totalMovies", totalMovies);
			req.setAttribute("pageUrl", req.getContextPath() + "/movies");

		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void insertMovie(HttpServletRequest req, HttpServletResponse resp) {
		String title = req.getParameter("title");
		int duration = Integer.parseInt(req.getParameter("duration"));
		String color = req.getParameter("color");
		String file = req.getParameter("file");
		int companyId = Integer.parseInt(req.getParameter("company_id"));

		Movie movie = new Movie();
		movie.setTitle(title);
		movie.setDuration(duration);
		movie.setColor(color);
		movie.setFile(file);
		movie.setCompany(new Company(companyId));

		MovieDAO dao = DAOFactory.createDAO(MovieDAO.class);
		try {
			if (dao.save(movie)) {
				ControllerUtil.sucessMessage(req, "Filme '" + movie.getTitle() + "' salvo com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Filme '" + movie.getTitle() + "' não pode ser salvo.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateMovie(HttpServletRequest req, HttpServletResponse resp) {
		int movieId = Integer.parseInt(req.getParameter("movieId"));
		String title = req.getParameter("title");
		int duration = Integer.parseInt(req.getParameter("duration"));
		String color = req.getParameter("color");
		String file = req.getParameter("file");
		int companyId = Integer.parseInt(req.getParameter("company_id"));

		if (file == null || file.trim().isEmpty()) {
			file = req.getParameter("existingFile");
		}

		Movie movie = new Movie(movieId);
		movie.setTitle(title);
		movie.setDuration(duration);
		movie.setColor(color);
		movie.setFile(file);
		movie.setCompany(new Company(companyId));

		MovieDAO dao = DAOFactory.createDAO(MovieDAO.class);
		try {
			if (dao.update(movie)) {
				ControllerUtil.sucessMessage(req, "Filme '" + movie.getTitle() + "' atualizado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Filme '" + movie.getTitle() + "' não pode ser atualizado.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteMovie(HttpServletRequest req, HttpServletResponse resp) {
		int movieId = Integer.parseInt(req.getParameter("id"));
		Movie movie = new Movie(movieId);

		MovieDAO dao = DAOFactory.createDAO(MovieDAO.class);
		try {
			if (dao.delete(movie)) {
				ControllerUtil.sucessMessage(req, "Filme deletado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Filme '" + movie.getTitle() + 
						" não pode ser deletado. Pois há dados relacionados ao filme.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}
}
