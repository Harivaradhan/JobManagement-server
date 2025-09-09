package com.tap.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.tap.database.Db;

public class JobDao {

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM Drives";  // adjust table name

        try (Connection conn = Db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Job job = new Job();
                job.setId(rs.getInt("id")); // set id if you want
                job.setTitle(rs.getString("title"));
                job.setCompany(rs.getString("company"));
                job.setLocation(rs.getString("location"));
                job.setJobType(rs.getString("jobType"));
                job.setSalaryMin(rs.getInt("salaryMin"));
                job.setSalaryMax(rs.getInt("salaryMax"));
                job.setDeadline(rs.getDate("deadline").toString());
                job.setDescription(rs.getString("description"));
                jobs.add(job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public void saveJob(Job job) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = Db.getConnection();

            // 1️⃣ Get next ID
            int nextId = 1; // default if table is empty
            String maxIdQuery = "SELECT MAX(id) AS max_id FROM Drives";
            ps = con.prepareStatement(maxIdQuery);
            rs = ps.executeQuery(); 
            if (rs.next()) {
                nextId = rs.getInt("max_id") + 1;
            }
            rs.close();
            ps.close();

            // 2️⃣ Insert job with nextId
            String insertSQL = "INSERT INTO Drives (id, title, company, location, jobType, salaryMin, salaryMax, deadline, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(insertSQL);
            ps.setInt(1, nextId);
            ps.setString(2, job.getTitle());
            ps.setString(3, job.getCompany());
            ps.setString(4, job.getLocation());
            ps.setString(5, job.getJobType());
            ps.setInt(6, job.getSalaryMin());
            ps.setInt(7, job.getSalaryMax());
            ps.setString(8, job.getDeadline());
            ps.setString(9, job.getDescription());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
