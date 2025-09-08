package com.tap.servlets;

import com.google.gson.Gson;
import com.tap.classes.Job;
import com.tap.classes.JobDao;

import com.tap.database.Db;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/jobs")
public class JobServlet extends HttpServlet {
    private JobDao jobDao = new JobDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); 
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        try {
            List<Job> jobs = jobDao.getAllJobs();  // implement in JobDao
            Gson gson = new Gson();
            String json = gson.toJson(jobs);
            resp.getWriter().write(json);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Failed to fetch jobs\"}");
        }
    }

    
    
    
    
    
@Override
protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Origin", "https://job-management-hf3nkffat-hari-varadhans-projects.vercel.app/");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type");

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        response.setStatus(HttpServletResponse.SC_OK);
        return;
    }

}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	

		
		
			BufferedReader reader = request.getReader();
			StringBuilder sb = new StringBuilder();
			String line;
			while((line= reader.readLine())!=null)
			{
				System.out.print("ayyo poche");
				sb.append(line);
			}
			
			System.out.println("RAW JSON: [" + sb.toString() + "]");
			Gson gson = new Gson();
				String json=sb.toString();
				if ( json== null || json.trim().isEmpty() || json.trim().equals("{}")) {
				    System.out.println("No data received yet.");
				    return;
				}
				
				String requestBody = sb.toString();
		        System.out.println("Received JSON: " + requestBody);

		        // Parse JSON → Job
		        Job job = new Gson().fromJson(requestBody, Job.class);

		        if (job == null) {
		            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		            response.getWriter().write("{\"error\":\"Invalid JSON payload\"}");
		            return;
		        }
				 jobDao.saveJob(job);

			        // Respond
			        response.getWriter().write("{\"status\":\"success\",\"message\":\"Job saved successfully!\"}");
		
	
	}

}