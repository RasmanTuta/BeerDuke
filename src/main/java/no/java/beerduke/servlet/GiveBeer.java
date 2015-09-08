package no.java.beerduke.servlet;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Extend HttpServlet class
public class GiveBeer extends DukeServlet {
    private static Logger logger = LoggerFactory.getLogger(DukeServlet.class);

    public void init() throws ServletException
    {
        // Do required initialization
        initController();
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
        final int[] counters = controller.giveBeer(slotNumber);
        // Set response content type
        prepareResponse(response);


        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println(countersString(counters));
    }

    public void destroy()
    {
        // do nothing.
    }

}