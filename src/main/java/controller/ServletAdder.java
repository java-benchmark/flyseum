package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProductBean;
import model.ProductBeanDAO;

/**
 * Servlet implementation class ServletAdder
 */
@WebServlet("/ServletAdder/*")
public class ServletAdder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAdder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String dest = request.getParameter("newArrival");
		String depart = request.getParameter("newDeparture");
		String company = request.getParameter("newCompany");
		Float prezzo = Float.parseFloat(request.getParameter("newPrice"));
		//Timestamp dateDeparture = Timestamp.valueOf(request.getParameter("newDateDep")+" "+request.getParameter("newTimeDep")+":00");
		//Timestamp dateArrival = Timestamp.valueOf(request.getParameter("newDateArr")+" "+request.getParameter("newTimeArr")+":00");
		long retryDate = System.currentTimeMillis();

		int sec = 600;

		Timestamp dateDeparture = new Timestamp(retryDate);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dateDeparture.getTime());
		cal.add(Calendar.SECOND, sec);
		Timestamp dateArrival = new Timestamp(cal.getTime().getTime());
		System.out.println("dateDeparture");
		System.out.println(dateDeparture);
		System.out.println("dateArrival");
		System.out.println(dateArrival);
		//Integer posti = Integer.parseInt(request.getParameter("newMax"));
		
		String mete[] = {"tirana","andorra la vella","vienna","bruxelles","minsk","sarajevo","sofia",
		  "nicosia","zagabria","copenaghen","tallinn","helsinki","parigi","berlino",
		  "atene","dublino","reykjavík","roma","pristina","riga","vaduz","vilnius",
		  "lussemburgo","skopje","la valletta","chisinau","monaco","podgorica","oslo",
		  "amsterdam","varsavia","lisbona","londra","praga","bucarest","mosca","città di san marino",
		  "belgrado","bratislava","lubiana","madrid","stoccolma","berna","kiev","budapest","città del vaticano"};
		
		dest.toLowerCase();
		
		try {
			Timestamp today = new Timestamp(System.currentTimeMillis());
			
			ProductBeanDAO pbDAO = new ProductBeanDAO();
			ProductBean pb = pbDAO.newProduct(dest, depart, company, prezzo, dateDeparture, dateArrival);
			
			if(pb != null && Arrays.asList(mete).contains(dest) && dateArrival.after(dateDeparture) && dateDeparture.after(today)) {
				response.setStatus(200);
			}
			else {
				response.setStatus(400);
			}
		}catch(Exception e) {
			//request.setAttribute("exception", e);
			//RequestDispatcher rq2 = request.getRequestDispatcher("./infopages/error.jsp");
			//rq2.forward(request, response);
			response.setStatus(500);
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
