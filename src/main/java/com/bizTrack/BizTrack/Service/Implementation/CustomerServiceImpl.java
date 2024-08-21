package com.bizTrack.BizTrack.Service.Implementation;
import com.bizTrack.BizTrack.Exception.CourseNotFoundException;
import com.bizTrack.BizTrack.Exception.UserNotFoundException;
import com.bizTrack.BizTrack.Model.Course;
import com.bizTrack.BizTrack.Model.Customer;
import com.bizTrack.BizTrack.Repository.CourseRepository;
import com.bizTrack.BizTrack.Repository.CustomerRepository;
import com.bizTrack.BizTrack.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer, Long customerId) throws UserNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()) {
            Customer updatedCustomer = optionalCustomer.get();
            if(Objects.nonNull(customer.getFirstName()) && !"".equalsIgnoreCase(customer.getFirstName())){
                updatedCustomer.setFirstName(customer.getFirstName());
            }
            if(Objects.nonNull(customer.getLastName()) && !"".equalsIgnoreCase(customer.getLastName())){
                updatedCustomer.setLastName(customer.getLastName());
            }
           // if(Objects.nonNull(customer.getAddress()) && !"".equalsIgnoreCase(customer.getAddress())){
             //   updatedCustomer.setAddress(customer.getAddress());
            //}
            if(Objects.nonNull(customer.getEmailId()) && !"".equalsIgnoreCase(customer.getEmailId())){
                updatedCustomer.setEmailId(customer.getEmailId());
            }
            if(Objects.nonNull(customer.getPhoneNumber()) && !"".equalsIgnoreCase(customer.getPhoneNumber())){
                updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
            }
            if(Objects.nonNull(customer.getPassword()) && !"".equalsIgnoreCase(customer.getPassword())){
                updatedCustomer.setPassword(customer.getPassword());
            }
            return customerRepository.save(updatedCustomer);
        } else {
            throw new UserNotFoundException("User not found with ID: " + customerId);
        }
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course updateCourse(Course course, Long courseId) throws CourseNotFoundException {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isPresent()) {
            Course updatedCourse = optionalCourse.get();
            if(Objects.nonNull(course.getName()) && !"".equalsIgnoreCase(course.getName())){
                updatedCourse.setName(course.getName());
            }
            if (course.getCharges() != null) {
                updatedCourse.setCharges(course.getCharges());
            }

            if (course.getDuration() != null || course.getDuration() != 0) {
                updatedCourse.setDuration(course.getDuration());
            }
            return courseRepository.save(updatedCourse);
        } else {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
    }

    public void getAdd(List<Customer> customerList){
        List<Customer> list = customerList.stream()
                .filter(customer -> customer.getAddresses().stream().anyMatch(address -> address.getName().equals("Mumbai")))
                .collect(Collectors.toUnmodifiableList());
        list.forEach(cust -> {
            System.out.println("name" +cust.getCustomerId());
        });

        List<Long> list1 = customerList.stream()
                .map(customer -> customer.getCustomerId()).collect(Collectors.toUnmodifiableList());
    }
}
