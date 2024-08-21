package com.bizTrack.BizTrack.Service;

import com.bizTrack.BizTrack.Exception.CourseNotFoundException;
import com.bizTrack.BizTrack.Exception.UserNotFoundException;
import com.bizTrack.BizTrack.Model.Course;
import com.bizTrack.BizTrack.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);
    List<Customer> getCustomers();
    Customer updateCustomer(Customer customer, Long customerId) throws UserNotFoundException;
    Course saveCourse(Course course);
    List<Course> getCourses();
    Course updateCourse(Course course, Long courseId) throws CourseNotFoundException;

}
