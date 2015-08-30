package no.java.beerduke.servlet;

import no.java.beerduke.rfid.AutomatController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class GiveBeer extends DukeServlet {

    private String message;

    public void init() throws ServletException
    {
        // Do required initialization
        initController();
        message = "Hello World";
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        String slot = request.getParameter("slot");
        int slotNumber = slot == null ? 0: Integer.parseInt(slot);
        controller.giveBeer(slotNumber);
        // Set response content type
        response.setContentType("text/plain");


        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println(message);
    }

    public void destroy()
    {
        // do nothing.
    }
}