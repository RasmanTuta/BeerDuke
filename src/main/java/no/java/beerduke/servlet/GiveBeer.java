package no.java.beerduke.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class GiveBeer extends DukeServlet {
    private static Logger logger = LoggerFactory.getLogger(DukeServlet.class);

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
        int slotNumber = 0;
        try {
            slotNumber = slot == null ? 0: Integer.parseInt(slot);
        } catch (NumberFormatException e) {
            logger.debug("Failed to parse slot, using round-robin");
        }
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