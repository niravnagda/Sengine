<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="Autofill.Cyberminer" %>
<%@ page import="database.SearchShift" %>
<%
Cyberminer.initShiftInfo();

	ArrayList<SearchShift> arrayList = Cyberminer.initShiftInfo();
	ArrayList<String> stringList = new ArrayList<String>();
	String countries[] = {"abc","def"};
	for(int i=0; i<arrayList.size();i++){
		SearchShift ss = arrayList.get(i);
		String[] array = ss.getDescription().split(" ");
		if(!stringList.contains(array[0]))
			stringList.add(array[0]);
	}
	
	
	String query = (String)request.getParameter("q");
	//System.out.println("1"+request.getParameterNames().nextElement());
	response.setHeader("Content-Type", "text/html");
	int cnt=1;
	for(int i=0;i<stringList.size();i++)
	{
		if(stringList.get(i).toUpperCase().startsWith(query.toUpperCase()))
		{
			out.print(stringList.get(i)+"\n");
			if(cnt>=10)
				break;
			cnt++;
		}
	}
%>