package com.tap.servlets;

import com.google.gson.Gson;
import com.tap.classes.Job;
import com.tap.classes.JobDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


public class JobServlet extends HttpServlet {
    private JobDao jobDao = new JobDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        try {
            List<Job> jobs = jobDao.getAllJobs();
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
        // CORS handled by filter, just return 200 OK
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        // Read JSON body
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        String requestBody = sb.toString();
        System.out.println("Received JSON: " + requestBody);

        if (requestBody == null || requestBody.trim().isEmpty() || requestBody.trim().equals("{}")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Empty request body\"}");
            return;
        }

        // Parse JSON → Job
        Job job = new Gson().fromJson(requestBody, Job.class);

        if (job == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid JSON payload\"}");
            return;
        }

        // Save job
        jobDao.saveJob(job);

        // Respond
        response.getWriter().write("{\"status\":\"success\",\"message\":\"Job saved successfully!\"}");
    }
}
