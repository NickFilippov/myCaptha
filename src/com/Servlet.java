package com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by fil-n on 28.12.17.
 */
@WebServlet(urlPatterns = {"/captha"})
public class Servlet extends HttpServlet {
    public static Connection connection;
    public static  List<Pic> names;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("servlet 1");
        names = new ArrayList<>();
        try{
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/fil-n/IdeaProjects/myCaptha/web/resourse/pics.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PICS where answers_tag2 > 25 ORDER BY RANDOM() LIMIT 6");//*
            while(resultSet.next()){
                names.add(new Pic(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(7), resultSet.getInt(8), resultSet.getInt(9),
                        resultSet.getDouble(10)
                ));

            }
            resultSet = statement.executeQuery("SELECT * FROM PICS where answers_tag2 <= 25 ORDER BY RANDOM() LIMIT 3");//*
            while(resultSet.next()){
                names.add(new Pic(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(7), resultSet.getInt(8), resultSet.getInt(9),
                        resultSet.getDouble(10)
                ));
            }
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(names);
        Collections.shuffle(names);
        System.out.println(names);
        request.setAttribute("tag", "cat");
        request.setAttribute("names", names);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }


}
