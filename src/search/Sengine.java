package search;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

import database.SearchResult;

/**
 * Servlet implementation class MyTest1
 */
public class Sengine extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	Search s = new Search();
	ArrayList<SearchResult> result = null;
	static int displayCount = 3;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sengine() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		
		String[] paramValues = request.getParameterValues("searchContent");
		
		
		if (paramValues.length == 1) {
	        String paramValue = paramValues[0];
	        if (paramValue.length() != 0)
	        	result=  s.sendback(paramValue);
	        request.setAttribute("result", result);
	        request.setAttribute("count", result.size());
	        
//	        System.out.println(paramValue);
//	  		for (SearchResult sr : result){
//	  			System.out.println(sr.getUrl());
//	  		}
	        
	      }
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		
		String[] searchValues = request.getParameterValues("searchContent");
		String[] jumpValues = request.getParameterValues("jumpToPage");
		
		String[] resultperpageValues = request.getParameterValues("resultperpage");
		System.out.print("displayCount "+displayCount);
		if(resultperpageValues!=null){
			try{
				displayCount = Integer.parseInt(resultperpageValues[0]);
				System.out.print("displayCount "+displayCount);
			}catch(Exception e){
				
			}
		}
		if (searchValues != null && jumpValues != null) {
			if (searchValues.length == 1 && jumpValues.length == 1) {
				String keyValue = searchValues[0];
				String pageValue = jumpValues[0];
				if (keyValue.length() != 0)
					result = Search.returnSort(s.sendback(keyValue));
				request.setAttribute("resultperpage", ""+displayCount);
				request.setAttribute("page", pageValue);
				request.setAttribute("key", keyValue);
				request.setAttribute("result", result);
				getServletContext().getRequestDispatcher("/sengine.jsp")
						.forward(request, response);
			}
		}else{
			request.setAttribute("resultperpage", ""+displayCount);
			getServletContext().getRequestDispatcher("/sengine.jsp")
			.forward(request, response);
		}
		
	}

}
