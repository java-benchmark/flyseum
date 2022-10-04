package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CartBean;
import model.CartBeanDAO;
import model.UserBean;
import model.UserBeanDAO;
import model.OrderBean;
import model.OrderBeanDAO;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/login")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String email = request.getParameter("userEmail");
		String pwd = request.getParameter("userPassword");
		UserBean beanUser = null;
		
		try {	
			UserBeanDAO beanUserD = new UserBeanDAO();
			CartBean cartUser = new CartBean();
			CartBeanDAO cartUserDAO = new CartBeanDAO();
			
			cartUser = cartUserDAO.restore(email);
			if(cartUser.isEmpty()) {
				cartUser = new CartBean();
				//testing
				System.out.println("cartUser empty, making a new cart");
			}
			
			beanUser = beanUserD.doRetrieveByKey(email, pwd);
			if(beanUser == null) {
				request.setAttribute("denied", true);
				RequestDispatcher rq = request.getRequestDispatcher("./register.jsp");
				rq.forward(request, response);
			}
			else {
				cartUser.setUser(email);
				session.setAttribute("UserBean", beanUser);
				session.setAttribute("cart", cartUser);
				// testing
				System.out.println(beanUser.getEmail()+" "+cartUser.isEmpty());
				System.out.print(cartUser.getAllProduct());
				OrderBeanDAO obDAO = new OrderBeanDAO();
				ArrayList<OrderBean> ordini = obDAO.getOrders(beanUser.getEmail());
				if (ordini.isEmpty()) {
					session.setAttribute("nordini", 0);
				} else {
					session.setAttribute("nordini", ordini.size());
				}
				RequestDispatcher rq = request.getRequestDispatcher("./infopages/success.jsp");
				rq.forward(request, response);
			}
		}catch(Exception e){
			request.setAttribute("exception", e);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("./infopages/error.jsp");
			requestDispatcher.forward(request, response);
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
