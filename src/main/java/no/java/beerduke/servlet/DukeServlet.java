package no.java.beerduke.servlet;

import no.java.beerduke.control.AutomatController;
import no.java.beerduke.control.CountersReader;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class DukeServlet extends HttpServlet {
    protected AutomatController controller;
    protected CountersReader counters;

    protected void initController() {
        controller = (AutomatController) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("automatController");
        counters = (CountersReader) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("countersReader");
    }

    protected String countersString(int[] counters){
        final String c = ArrayUtils.toString(counters, "");
        return "".equals(c) ? "": c.replaceAll("\\{", "[").replaceAll("\\}", "]");
    }

    protected void prepareResponse(HttpServletResponse response){
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
    }
}
