<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="database.SearchResult" %>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <style type="text/css"></style>


    <title>Sengine - Search Engine</title>
    <!-- Place favicon.ico & apple-touch-icon.png in the root of your domain and delete these references -->
    <!-- CSS : implied media="all" -->
    <link rel="stylesheet" href="stylesheet/style.css" />
    <link rel="Stylesheet" href="stylesheet/box.css" />
    <link rel="Stylesheet" href="stylesheet/menu.css" />
    <style type="text/css"></style>


    <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
    <script src="JS/jquery.autocomplete.js"></script>
</head>

<body>
<div id='cssmenu'>
<ul>
   <li class='active '><a href='http://localhost:8080/Sengine/sengine.jsp'><span>Home</span></a></li>
   <li class='has-sub '><a href='#'><span>Tools</span></a>
      <ul>
		 <li><a href='http://localhost:8080/Sengine/settings.jsp'><span>Settings</span></a></li>
	  
      </ul>
   </li>
   <li><a href='http://localhost:8080/Sengine/form.html'><span>Contact</span></a></li>
   <li><a href='#'><span>Help</span></a></li>
</ul>
</div>
    <!-- endof links to blog and shop -->

    <div id="wrapper">
        <div id="wrapper2">
            <!-- div for background -->


            <div id="container">

                <div id="logo">
                    <!-- start of logo -->
                    <a href="http://localhost:8080/Sengine/sengine.jsp">
                        <img src="images/sengine_logo.png" width="134" height="131" alt="Sengine" />
                    </a>
                </div>
                <!-- end of logo -->

                <div id="tagline">
                    <!-- start of the tagline -->
                    <h1>A Simple &amp; Elegant Search Engine</h1>
                </div>
                <!-- end of the tagline -->

   				<form action="Sengine" method="post" >
   				
   				<%
   				String display = (String) request.getAttribute("key");
   				if(display==null)display = "";
   				%>
					<script>
						jQuery(function() {
							$("#country").autocomplete("list.jsp");
						});
					</script>
					<div id="searchBar">
                    <div id="searchInput">
                    <input type='hidden' name='jumpToPage' value="1"/>
                        <input class="input_text" id="country" name="searchContent" type="text" placeholder="Search..."  value="<%=display%>"/>
                    </div>
                    <div id="searchButton">
                        <button class="threeD" type="submit" value="Go" >GO</button>
                        
                        
                        
                    </div>
				</form>
                    <div class="clear" />

                </div>
                <!-- end of header -->
            </div>


		<!-- main content display area -->

<%	
		
		out.print("<div id='content'>");
		ArrayList<SearchResult> srList = (ArrayList<SearchResult>) request.getAttribute("result");
		String keyString = (String) request.getAttribute("key");
		String targetPageStrig = (String)request.getAttribute("page");
		
		String resultperpageString = (String)request.getAttribute("resultperpage");
		
		int itemMaxNum = 3;
		
		try{
			itemMaxNum = Integer.parseInt(resultperpageString);
		}catch(Exception e){
			//do nothing
		}finally{
			
		}
		int resultNum = 0;
		int displayNum = 0;
		int pageCount = 0;
		
		int currentPage=1;
		
		
		if (srList != null && srList.size()!=0){
			out.print("<div class='seperator'></div>");
			resultNum=srList.size();
			
			if(resultNum > itemMaxNum){
				displayNum = itemMaxNum;
			}else{
				displayNum = resultNum;
			}
			
			if(targetPageStrig!= null) currentPage = Integer.parseInt(targetPageStrig);
			
			int displayCount = (currentPage-1)*itemMaxNum+itemMaxNum;
			for(int i=(currentPage-1)*itemMaxNum; i<displayCount&&i<resultNum ;i++){
			SearchResult sr = srList.get(i);
			
			
			out.print("<div class='box10'>");
			out.print("<a href=http://"+sr.getUrl()+"><h2>"+sr.getUrl()+"</h2></a>");
			out.print("<a href=http://"+sr.getUrl()+">"+sr.getUrl()+"</a>");
			out.print("<p>"+ sr.getDescription()+"</p>");
			out.print("<br />");
			out.print("</div>");
			
			}
			
			pageCount = (int)Math.ceil((double)resultNum/(double)itemMaxNum);
			
			out.print(" <div id='navcnt'>");
			out.print("<table id='nav' style='border-collapse:collapse;text-align:center;margin:17px auto; direction:ltr'>");
			out.print("<tbody>");
			out.print("<tr valign='top'>");
			out.print("<td><img alt='seperator' src='images/seperator.png'/></td>");//Add seperator
			
			if(currentPage !=1){
	    		out.print("<td>");
	    		out.print("<form name='back' action='Sengine' method='post'>"); //name='jumpTo'
	    		out.print("<input type='hidden' name='jumpToPage' value='"+(currentPage-1)+"'>");
	            out.print("<input type='hidden' name='searchContent' value='"+keyString+"'>");
	    		out.print("<a href='javascript:document.back.submit()'><img alt='arrow' src='images/arrow_left.png' />");// // add arrow, if there are more than 5 pages.
	    		out.print("</a>");
	    		out.print("</form>");
	    		out.print("</td>");
	    	}
			
			
			/*
	         the following is to display the indicator with a number
	        */
	        for(int i=0; i< pageCount; i++){
	        	out.print("<td>");
	        	out.print("<form name='jumpTo' action='Sengine' method='post'>"); //name='jumpTo'
	            out.print("<input type='hidden' name='jumpToPage' value='"+(i+1)+"'>");
	            out.print("<input type='hidden' name='searchContent' value='"+keyString+"'>");
	            if(i==currentPage-1)out.print("<a href='javascript:document.jumpTo["+i+"].submit()'><img alt='seperator' src='images/indicator3.png' />");//  link
	            else out.print("<a href='javascript:document.jumpTo["+i+"].submit()'><img alt='seperator' src='images/indicator.png' />");//  link
	    		out.print("<span style='background-position:-74px 0; width:20px'></span>");//  link
	    		out.print("<br />");
	    		if(i==currentPage-1) out.print("<b>"+(i+1)+"</b>");//index
	    		else out.print(i+1);
	    		out.print("</a>");
	    		out.print("</form>");
	    		out.print("</td>");
	        }
	    	
	    	if(currentPage!=pageCount){
	    		out.print("<td>");
	    		out.print("<form name='forward' action='Sengine' method='post'>"); //name='jumpTo'
	    		out.print("<input type='hidden' name='jumpToPage' value='"+(currentPage+1)+"'>");
	            out.print("<input type='hidden' name='searchContent' value='"+keyString+"'>");
	    		out.print("<a href='javascript:document.forward.submit()'><img alt='arrow' src='images/arrow_right.png' />");// // add arrow, if there are more than 5 pages.
	    		out.print("</a>");
	    		out.print("</form>");
	    		out.print("</td>");
	    	}
	    	out.print("<td><img alt='seperator' src='images/seperator.png' /></td>");// add seperator
	    	out.print(" </tr>");
	    	out.print("</tbody>");
	    	out.print("</table>");
	    	out.print("</div>");
	    	
	    	//out.print("currentPage:"+currentPage+"<br/>");
	    	//out.print("itemMaxNum:"+itemMaxNum+"<br/>");
			//out.print("pageCount:"+pageCount);
	    	
	    	
	    	
		}
		if(srList != null && srList.size()==0){
			out.print(" Your search - <b>"+keyString+"</b> did not match any documents.");
		}
		out.print("</div>");
%>
		

             <div class="clear"></div>
            <!-- clearing div -->
                    <!-- start of the footer area -->
            
            
                    <div id="copyright" style="bottom:1px">
                        <!-- start of the copyright area -->
                        <p>
                            Copyright @ Sengine
                            <script language="JavaScript" type="text/javascript">
                                var d = new Date();
                                yr = d.getFullYear();
                                document.write(yr);
                            </script>
                            
                        </p>
                    </div>
            
                    <!-- end of te copyright area -->

                    <div id="goingup">
                        <!--<a href="#top">Going Up?</a>-->
                    </div>

                    <div class="clear"></div>
                    <!-- clearing div -->
            
                
       

    </div>
	

	<!-- end of wrapper div for background -->
</body>
</html>

