package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserBean;
import model.UserBeanDAO;

/**
 * Servlet implementation class ServletRegister
 */
@WebServlet("/register")
public class ServletRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String ID = request.getParameter("userEmail");
		String password = request.getParameter("userPassword");
		String nome = request.getParameter("userName");
		String cognome = request.getParameter("userSurname");
		
		try {
			UserBeanDAO ubDAO = new UserBeanDAO();
			UserBean ub = ubDAO.UserRegistration(ID,password,nome,cognome);
			
			if(ub.getEmail() != "duplicato" &&  ub.getPsw() != "duplicato" ) {
				RequestDispatcher rq = request.getRequestDispatcher("./infopages/success.jsp");
				rq.forward(request, response);
			}
			else {
				request.setAttribute("duplicato", true);
				RequestDispatcher rq2 = request.getRequestDispatcher("./register.jsp");
				rq2.forward(request, response);
			}
		}catch(Exception e) {
			request.setAttribute("exception", e);
			RequestDispatcher rq3 = request.getRequestDispatcher("./infopages/error.jsp");
			rq3.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
