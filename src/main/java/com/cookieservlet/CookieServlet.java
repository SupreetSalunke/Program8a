/*
 * 8a. Build a servlet program to create a cookie to get your name through text box and press submit button(
through HTML) to display the message by greeting Welcome back your name ! , you have visited this page
n times ( n = number of your visit ) along with the list of cookies and demonstrate the expiry of cookie also.
 */
package com.cookieservlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("userName");

        int visitCount = 1;
        String name = null;

        Cookie[] cookies = request.getCookies();

        // Read existing cookies
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user")) {
                    name = c.getValue();
                }
                if (c.getName().equals("count")) {
                    visitCount = Integer.parseInt(c.getValue());
                    visitCount++;
                }
            }
        }

        // If new user
        if (userName != null) {
            name = userName;

            Cookie userCookie = new Cookie("user", name);
            Cookie countCookie = new Cookie("count", String.valueOf(visitCount));

            // Expiry demonstration (30 seconds)
            userCookie.setMaxAge(30);
            countCookie.setMaxAge(30);

            response.addCookie(userCookie);
            response.addCookie(countCookie);
        }

        out.println("<html><body>");

        if (name != null) {
            out.println("<h2 style='color:blue;'>Welcome back, " + name + "!</h2>");
            out.println("<h3 style='color:magenta;'>You have visited this page "
                    + visitCount + " times!</h3>");
        } else {
            out.println("<h3 style='color:red;'>Welcome Guest! Please enter your name.</h3>");
        }

        // Display all cookies
        out.println("<h3>List of Cookies:</h3>");
        if (cookies != null) {
            for (Cookie c : cookies) {
                out.println("Name: " + c.getName() + " | Value: " + c.getValue() + "<br>");
            }
        }

        out.println("<br><a href='index.html'>Go Back</a>");

        out.println("</body></html>");
    }
}