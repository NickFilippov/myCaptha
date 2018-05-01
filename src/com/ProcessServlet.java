package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fil-n on 17.09.17.
 */
@WebServlet("/process")
public class ProcessServlet extends HttpServlet {
    private List<Integer> arr;
    private List<Pic> news_choken;
    private List<Pic> news_unchoken;
    private List<Pic> known__choken;
    private List<Pic> known__unchoken;
    private Double k_ext = 5.7;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("servlet process");
        response.getWriter().write("testTest");
//        return;
    }
    protected void doGet2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("servlet process");
        response.getWriter().write("testTest");
//        return;
        String arrString = request.getParameter("arr");
        String status ="not OK";
        double k = 0;
        arr = new ArrayList<>();
        news_choken = new ArrayList<>();
        news_unchoken = new ArrayList<>();
        known__choken = new ArrayList<>();
        known__unchoken = new ArrayList<>();
        for (String s : arrString.substring(1, arrString.length() - 1).split(",")) {
            if (!s.equals("null")) {
                Integer n = Integer.valueOf(s);
                arr.add(n);
            }
        }
        for (int i = 0; i < Servlet.names.size(); i++) {
            Pic pic = Servlet.names.get(i);
            if (arr.contains(i)) {
                if (pic.getAnswers() > 99) {// уверенная картинка
                    k += pic.getP();
                    known__choken.add(pic);
                } else { // нвоая

                    news_choken.add(pic);
                }
            } else {
                if (pic.getAnswers() > 99) {// уверенная картинка
                    k += 1 - pic.getP();
                    known__unchoken.add(pic);
                } else { // нвоая

                    news_unchoken.add(pic);
                }
            }

        }
        System.out.println("k" + k);
        if (k > k_ext) {
            status ="OK";
            for (int i = 0; i < news_choken.size(); i++) {
                Pic p = news_choken.get(i);
                p.setAnswers(p.getAnswers() + 1);
                p.setRightAnswers(p.getRightAnswers() + 1);
            }
            for (int i = 0; i < news_unchoken.size(); i++) {
                Pic p = news_unchoken.get(i);
                p.setAnswers(p.getAnswers() + 1);
            }
            for (int i = 0; i < known__choken.size(); i++) {
                Pic p = known__choken.get(i);
                p.setAnswers(p.getAnswers() + 1);
                p.setRightAnswers(p.getRightAnswers() + 1);
            }
            for (int i = 0; i < known__unchoken.size(); i++) {
                Pic p = known__unchoken.get(i);
                p.setAnswers(p.getAnswers() + 1);
            }
        }
        System.out.println(arr);
        System.out.println(news_choken);
        System.out.println(news_unchoken);

        try {
            ArrayList<Pic> picsUpdated = new ArrayList<Pic>() {{
                addAll(news_choken);
                addAll(news_unchoken);
                addAll(known__choken);
                addAll(known__unchoken);
            }};
//            Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/fil-n/IdeaProjects/myCaptha/web/resourse/pics.db");
            for (Pic pic : picsUpdated) {
                PreparedStatement preparedStatement = Servlet.connection.prepareStatement(
                        "UPDATE pics SET answers_tag2 = ?, right_answers_tag2 = ?, p2= ? WHERE id = ?"); //*
                preparedStatement.setInt(1, pic.getAnswers());
                preparedStatement.setInt(2, pic.getRightAnswers());
                preparedStatement.setDouble(3, Double.valueOf(pic.getRightAnswers()) / Double.valueOf(pic.getAnswers()));
                preparedStatement.setInt(4, pic.getId());
                /*int qqq = */preparedStatement.executeUpdate();
                //System.out.println(qqq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(status);
    }
}
