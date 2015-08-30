package no.java.beerduke.servlet;

import no.java.beerduke.rfid.AutomatController;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServlet;

public abstract class DukeServlet extends HttpServlet {
    protected AutomatController controller;

    protected void initController() {
        controller = (AutomatController) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("automatController");
    }
}
