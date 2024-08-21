package com.bizTrack.BizTrack.Controller;

import com.bizTrack.BizTrack.Model.Course;
import com.bizTrack.BizTrack.Model.Customer;
import com.bizTrack.BizTrack.Service.CustomerService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/biz/Track")
public class BizTrack {
    @Autowired
    private CustomerService customerService;
        @PostMapping("/addCustomers")
        public ResponseEntity<Customer> saveCustomer(@Validated @RequestBody Customer customer) {
            Customer savedCustomer = customerService.saveCustomer(customer);
            return ResponseEntity.ok(savedCustomer);
        }
        @GetMapping("/getCustomers")
        public ResponseEntity<List<Customer>> getCustomers() {
            List<Customer> customers = customerService.getCustomers();
            return ResponseEntity.ok(customers);
        }

        @PutMapping("/updateCustomers/{id}")
        public ResponseEntity<Customer> updateCustomer(@Validated @RequestBody Customer customer, @PathVariable("id") Long customerId) {
            Customer updatedCustomer = customerService.updateCustomer(customer, customerId);
            return ResponseEntity.ok(updatedCustomer);
        }

        @PostMapping("/addCourse")
        public ResponseEntity<Course> saveCourse(@Validated @RequestBody Course course) {
            Course savedCourse = customerService.saveCourse(course);
            return ResponseEntity.ok(savedCourse);
// post api for adding customers
        }
        @GetMapping("/getCourses")
        public ResponseEntity<List<Course>> getCourses() {
            List<Course> courses = customerService.getCourses();
            return ResponseEntity.ok(courses);
        }

        @PutMapping("/updateCourse/{id}")
        public ResponseEntity<Course> updateCourse(@Validated @RequestBody Course course, @PathVariable("id") Long courseId) {
            Course updatedCourse = customerService.updateCourse(course, courseId);
            return ResponseEntity.ok(updatedCourse);
        }
// RUNNING CODE TO FETCH THE LATEST TRENDS ON THE INTERNET
/*
    @GetMapping("/latest-trends")
    public String getLatestTrends() {
        try {
            // Path to your Python script
            ClassPathResource resource = new ClassPathResource("latest_trends.py");
            Path tempFile = Files.createTempFile(UUID.randomUUID().toString(), ".py");

            // Copy the Python script to a temporary file
            try (InputStream is = resource.getInputStream();
                 FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            }

            // Path to Python interpreter
            String pythonPath = "/usr/local/bin/python3"; // Adjust according to your system

            // Execute the Python script
            ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, tempFile.toString());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Read the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }

            // Check exit code
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return "Python script failed with exit code " + exitCode + ". Output:\n" + result.toString();
            }

            // Cleanup
            Files.delete(tempFile);

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while fetching trends: " + e.getMessage();
        }
    }
    */
    /*private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private static final String OPENAI_API_KEY = "sk-proj-I6CKwIUVVnWWWoBQfxgzT3BlbkFJFV7C3tcZgmYgf8B5AYVK";

    @GetMapping("/latest-trends")
    public ResponseEntity<List<String>> getLatestTrends() {
        try {
            // Fetch latest trends from your Python script or any other source
            List<String> trends = fetchTrends();

            // Generate articles using OpenAI API
            List<String> articles = generateArticles(trends);

            // Return the articles
            return ResponseEntity.ok(articles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private List<String> fetchTrends() {
        // Mock implementation: Replace this with actual code to fetch trends
        List<String> trends = new ArrayList<>();
        trends.add("Climate change");
//        trends.add("Artificial Intelligence");
        return trends;
    }
    private static final int MAX_RETRIES = 5;
    private static final long INITIAL_BACKOFF_MS = 1000; // 1 second
    private static final double BACKOFF_MULTIPLIER = 2.0;
    private static final long GLOBAL_THROTTLE_MS = 2000; // 2 seconds

    public List<String> generateArticles(List<String> trends) {
        List<String> articles = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        boolean quotaExceeded = false;

        for (String trend : trends) {
            if (quotaExceeded) {
                articles.add("Quota exceeded. Please check your OpenAI API plan and billing details.");
                continue;
            }

            boolean success = false;
            int retries = 0;
            long backoff = INITIAL_BACKOFF_MS;

            while (!success && retries < MAX_RETRIES) {
                try {
                    // Throttle requests to prevent hitting rate limits
                    Thread.sleep(GLOBAL_THROTTLE_MS);

                    // Prepare the request payload
                    JSONObject requestBody = new JSONObject();
                    requestBody.put("model", "gpt-3.5-turbo");
                    JSONArray messages = new JSONArray();
                    JSONObject message = new JSONObject();
                    message.put("role", "user");
                    message.put("content", "Write a comprehensive article on the topic: " + trend);
                    messages.put(message);
                    requestBody.put("messages", messages);
                    requestBody.put("max_tokens", 1500);
                    requestBody.put("temperature", 0.7);

                    // Set headers
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.set("Authorization", "Bearer " + OPENAI_API_KEY);

                    HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);

                    // Make the request
                    ResponseEntity<String> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, request, String.class);

                    // Process the response
                    if (response.getStatusCode().is2xxSuccessful()) {
                        JSONObject responseBody = new JSONObject(response.getBody());
                        JSONArray choices = responseBody.getJSONArray("choices");
                        if (choices.length() > 0) {
                            String article = choices.getJSONObject(0).getJSONObject("message").getString("content");
                            articles.add(article.trim());
                        } else {
                            articles.add("No article generated for trend: " + trend);
                        }
                        success = true;
                    } else if (response.getStatusCode().value() == 429) { // Handle rate limit error
                        retries++;
                        Thread.sleep(backoff); // Wait before retrying
                        backoff *= BACKOFF_MULTIPLIER; // Exponential backoff

                        // Check for insufficient quota
                        if (response.getBody().contains("insufficient_quota")) {
                            quotaExceeded = true;
                            articles.add("Quota exceeded. Please check your OpenAI API plan and billing details.");
                            success = true;
                        }
                    } else {
                        articles.add("Failed to generate article for trend: " + trend + " with status code: " + response.getStatusCode());
                        success = true; // Avoid infinite loop
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    articles.add("Error occurred while generating article for trend: " + trend + " - " + e.getMessage());
                    success = true; // Avoid infinite loop
                }
            }
            if (!success && !quotaExceeded) {
                articles.add("Failed to generate article for trend: " + trend + " after " + MAX_RETRIES + " retries.");
            }
        }
        return articles;
    }*/

}
