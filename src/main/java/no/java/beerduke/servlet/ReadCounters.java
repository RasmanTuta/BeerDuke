package no.java.beerduke.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by kristian on 08.09.2015.
 */
public class ReadCounters extends DukeServlet {
    public void init() throws ServletException
    {
        // Do required initialization
        initController();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final int[] counters = this.counters.readCounters();
        response.setContentType("application/json");


        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println(countersString(counters));

    }
}
