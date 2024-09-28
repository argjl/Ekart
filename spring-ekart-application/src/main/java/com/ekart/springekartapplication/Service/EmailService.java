package com.ekart.springekartapplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ekart.springekartapplication.Entity.Customer;
import com.ekart.springekartapplication.Entity.Order;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendOrderConfirmation(Customer customer, Order order) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(customer.getEmailCustomer());
		message.setSubject("Order Confirmation");
		message.setText("Your Order with ID " + order.getId() + "has been placed Successfully!");
		mailSender.send(message);
	}

}
