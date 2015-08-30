package no.java.beerduke.servlet;

import no.java.beerduke.control.AutomatController;
import no.java.beerduke.control.CountersReader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServlet;

public abstract class DukeServlet extends HttpServlet {
    protected AutomatController controller;
    protected CountersReader counters;

    protected void initController() {
        controller = (AutomatController) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("automatController");
        counters = (CountersReader) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("countersReader");
    }
}
