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
        String sql = "INSERT INTO Drives (title, company, location, jobType, salaryMin, salaryMax, deadline, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
        	
                Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, job.getTitle());
            ps.setString(2, job.getCompany());
            ps.setString(3, job.getLocation());
            ps.setString(4, job.getJobType());
            ps.setInt(5, job.getSalaryMin());
            ps.setInt(6, job.getSalaryMax());
            ps.setString(7, job.getDeadline());
            ps.setString(8, job.getDescription());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

