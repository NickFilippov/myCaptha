package com;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name="check", urlPatterns = {"/check"})
public class CheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        Map<String, String[]> parameterMap = request.getParameterMap();
        boolean check = CaptchaDBUtils.check(parameterMap);
        response.setContentType("text/html; charset=windows-1251");
        PrintWriter out = response.getWriter();
        out.print(check);
        out.close();
    }
}
