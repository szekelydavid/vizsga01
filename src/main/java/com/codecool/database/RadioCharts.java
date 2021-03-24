package com.codecool.database;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadioCharts {
    String url;
    String user;
    String password;

    public RadioCharts(String url,String user,String password){
        this.url=url;
        this.user=user;
        this.password=password;
    }

    public String getMostPlayedSong(){
        String SQL = "SELECT song,times_aired FROM music_broadcast ORDER BY times_aired DESC;";
        try {

            Connection conn = DriverManager.getConnection(this.url, this.user, this.password);
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);


            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            String topSongString;
            topSongString=rs.getString("song");

            return topSongString;

        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }

    public String getMostActiveArtist(){
        List<Artist> artists= new ArrayList<Artist>();

        HashMap<String, Integer> eredmenyekMap = new HashMap<String, Integer>();

        String SQL = "SELECT artist,count(DISTINCT song) as cou FROM music_broadcast GROUP BY artist;";
        try {
            Connection conn = DriverManager.getConnection(this.url, this.user, this.password);
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String inputArtist = rs.getString("artist");
                int putsongnum = rs.getInt("cou");

                System.out.println(inputArtist+putsongnum);
                eredmenyekMap.put(inputArtist,putsongnum);
            }

            Map.Entry<String, Integer> maxEntry = null;
            for (Map.Entry<String, Integer> entry : eredmenyekMap.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                    System.out.println(entry.toString());
                }
            }
            if (maxEntry == null){
                return "";
            }
            String topName=maxEntry.getKey();
            return topName;

        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "";
        }

    }


}
