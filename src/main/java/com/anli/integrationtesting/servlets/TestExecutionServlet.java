package com.anli.integrationtesting.servlets;

import com.anli.integrationtesting.execution.TestExecutor;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestExecutionServlet extends HttpServlet {

    protected static final int HTTP_OK = 200;
    protected static final int HTTP_CONFLICT = 409;

    protected static final String CLASS_PARAM = "class";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String executorClassName = request.getParameter(CLASS_PARAM);
        try {
            TestExecutor executor = getExecutor(executorClassName);
            executor.test();
            response.setStatus(HTTP_OK);
            out.print("Test success!");
        } catch (Exception | Error thr) {
            response.setStatus(HTTP_CONFLICT);
            thr.printStackTrace(out);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected TestExecutor getExecutor(String className) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        return (TestExecutor) Class.forName(className).newInstance();
    }
}
