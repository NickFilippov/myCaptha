package com;

import java.sql.*;
import java.util.*;

public class CaptchaDBUtils {

    private static Connection c = null;

    public static Connection getConn() throws Exception {
        if (c == null) {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:C:/Users/fil-n/IdeaProjects/myCaptha/web/resourse/pics.db");
        }
        return c;
    }

    public static String getResponseParams(Map<String, String[]> reqParams) {
        String tag = null;
        int numberImages = 9;
        int limitWrongChecks = -1;
        ResponseParameters resParams = new ResponseParameters();
        if (reqParams != null) {
            // tag
            if (reqParams.containsKey("tags[]")) {
                // check tag into db
                String[] tags = reqParams.get("tags[]");
                tag = tags[(new Random()).nextInt(tags.length)];
                while (!hasTag(tag)){
                    tag = tags[(new Random()).nextInt(tags.length)];
                }
                resParams.setTag(tag);
            } else {
                // set random tag
                tag = getRandomTag();
                resParams.setTag(tag);
            }

            // numberImages
            if (reqParams.containsKey("numberImages")) {
                numberImages = Integer.valueOf(reqParams.get("numberImages")[0]);
                if (numberImages <= 0) {
                    numberImages = 9;
                }
            }
            resParams.setNumberImages(numberImages);

            // limitWrongChecks
            if (reqParams.containsKey("limitWrongChecks")) {
                limitWrongChecks = Integer.valueOf(reqParams.get("limitWrongChecks")[0]);
                if (limitWrongChecks <= 0) {
                    limitWrongChecks = -1;
                }
            }

            // images
            List<String> images = getImages(tag, numberImages);
            resParams.setPics(images);
            return resParams.toString();

        }
        return "";
    }

    public static boolean hasTag(String tag) {
        if (tag != null) {
            try {
                final Connection connection = getConn();
                PreparedStatement ps = connection.prepareStatement("select COUNT(*) from tags where tag_name = ?");
                ps.setString(1, tag);

                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    int countRows = resultSet.getInt(1);
                    if (countRows == 1) {
                        return true;
                    }
                    return false;
                }
                ps.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    //todo проверить
    public static String getRandomTag() {
        try {
            final Connection connection = getConn();
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("select id_tag, tag_name from tags WHERE (select COUNT(*) from statistics WHERE statistics.id_tag = tags.id_tag) >= 6 ORDER BY RANDOM() LIMIT 1");
            while (resultSet.next()) {
                String tag = resultSet.getString(2);
                return tag;
            }
            st.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "кошки";
    }

    public static List<String> getImages(String tag, int numberImages) {
        List<String> pics = new ArrayList<>(numberImages);
        int numberKnownImages = Math.round (numberImages*Float.valueOf(String.valueOf(0.6)));
        int numberNonKnownImages = numberImages-numberKnownImages;

        try {
            final Connection connection = getConn();
            Statement st = connection.createStatement();
            int id_tag = getIdTag(tag);

            //known images
            ResultSet resultSet = st.executeQuery("select * FROM images where images.id_image IN (SELECT id_image FROM statistics where answers > 10 AND (probability>0.8 OR probability<0.2) and id_tag = " + id_tag + ") ORDER BY RANDOM() LIMIT "+ numberKnownImages);
            while (resultSet.next()) {
                String image_src = resultSet.getString("image_src");
                pics.add(image_src);
            }

            //non-known images
            st = connection.createStatement();
            resultSet = st.executeQuery("SELECT * FROM images WHERE (SELECT COUNT(*) FROM statistics WHERE statistics.id_image = images.id_image AND statistics.id_tag = "+id_tag+") = 0 OR images.id_image IN (SELECT statistics.id_image FROM statistics WHERE statistics.id_tag = "+id_tag+" AND statistics.answers < 10) ORDER BY RANDOM() LIMIT " + numberNonKnownImages);
            while (resultSet.next()) {
                String image_src = resultSet.getString("image_src");
                pics.add(image_src);
            }
            st.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pics;
    }

    public static int getIdTag(String tag) {
        try {
            final Connection connection = getConn();
            PreparedStatement ps = connection.prepareStatement("SELECT id_tag FROM tags WHERE tag_name = ?");
            ps.setString(1, tag);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("id_tag");

            }
            ps.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean check(Map<String, String[]> parameterMap) {
        String[] pics = parameterMap.get("pics[]");
        String[] selectedImages = parameterMap.get("selectedImages[]");
        String tag = parameterMap.get("tag")[0];
        int id_tag = getIdTag(tag);
        double k = 0;
        for (Integer i = 0; i < pics.length; i++) {
            String pic = pics[i];
            Double probability = getProbability(pic, id_tag);
            if (probability != null) {
                if (Arrays.asList(selectedImages).contains(i.toString())) {
                    k += getProbability(pic, id_tag);
                } else {
                    k += 1 - getProbability(pic, id_tag);
                }
            }
        }
        //не все а только известных картинок!!
        boolean result = k >= Math.round (Float.valueOf(String.valueOf(pics.length*0.6)))*0.9;//5.7;
        if (result) {
            updateStatistics(id_tag, pics, selectedImages);
        }
        return result;
    }

    public static void updateStatistics(int id_tag, String[] pics, String[] selectedImages) {
        try {
            Connection connection = getConn();
            for (Integer i = 0; i < pics.length; i++) {
                String pic = pics[i];
                PreparedStatement ps = connection.prepareStatement("select COUNT(*) from statistics where id_tag = ? and id_image = (select id_image from images where image_src = ?)");
                ps.setInt(1, id_tag);
                ps.setString(2, pic);
                ResultSet resultSet = ps.executeQuery();

                update(pic, id_tag, resultSet.next() && resultSet.getInt("COUNT(*)") == 1, selectedImages, i);
                ps.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(String pic, int id_tag, boolean hasRow, String[] selectedImages, Integer i) {
        try {
            Connection connection = getConn();
            PreparedStatement ps;
            if (hasRow) {
                //update
                if (Arrays.asList(selectedImages).contains(i.toString())) { //selected
                    ps = connection.prepareStatement("UPDATE statistics set answers = answers +1, selections = selections + 1, probability = 1.0* (selections+1)/(answers+1) where id_tag = ? and id_image = (select id_image from images where image_src = ?)");
                    ps.setInt(1, id_tag);
                    ps.setString(2, pic);
                    //todo delete
                    boolean execute = ps.execute();
                } else { //non-selected
                    ps = connection.prepareStatement("UPDATE statistics set answers = answers +1, probability = 1.0* (selections)/(answers+1) where id_tag = ? and id_image = (select id_image from images where image_src = ?)");
                    ps.setInt(1, id_tag);
                    ps.setString(2, pic);
                    //todo delete
                    boolean execute = ps.execute();
                }

            } else {
                //insert
                if (Arrays.asList(selectedImages).contains(i.toString())) { //selected
                    ps = connection.prepareStatement("INSERT INTO statistics (probability, id_image, id_tag, answers, selections) VALUES (1.0, (select id_image from images where image_src = ?), ?, 1, 1)");
                    ps.setInt(2, id_tag);
                    ps.setString(1, pic);
                    //todo delete
                    boolean execute = ps.execute();
                } else { // non-selected
                    ps = connection.prepareStatement("INSERT INTO statistics (probability, id_image, id_tag, answers, selections) VALUES (0.0, (select id_image from images where image_src = ?), ?, 1, 0)");
                    ps.setInt(2, id_tag);
                    ps.setString(1, pic);
                    //todo delete
                    boolean execute = ps.execute();
                }
            }
            ps.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Double getProbability(String pic, int id_tag) {
        try {
            final Connection connection = getConn();
            PreparedStatement ps = connection.prepareStatement("select * from statistics where id_tag = ? and id_image = (SELECT id_image FROM images where image_src = ?)");
            ps.setInt(1, id_tag);
            ps.setString(2, pic);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("answers") > 10) {
                    return resultSet.getDouble("probability");
                }
                return null;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
