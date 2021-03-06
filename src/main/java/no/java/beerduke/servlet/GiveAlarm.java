package no.java.beerduke.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// Extend HttpServlet class
public class GiveAlarm extends DukeServlet {
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
        controller.giveAlarm();
        // Set response content type
    }

    public void destroy()
    {
        // do nothing.
    }
}