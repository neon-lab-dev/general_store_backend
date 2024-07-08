package com.neonlab.product.service;
import com.neonlab.product.dtos.BoughtProductDetailsDto;
import com.neonlab.product.dtos.OrderDto;
import com.neonlab.product.models.requests.EmailData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailServices {

    @Autowired
    private JavaMailSender mailSender;

    public EmailData setEmailData(OrderDto orderDto) {
        var emailData = new EmailData();
        emailData.setOrderNumber(getOrderId(orderDto));
        emailData.setName(orderDto.getUserDetailsDto().getName());
        emailData.setPhone(orderDto.getUserDetailsDto().getPrimaryPhoneNo());
        emailData.setEmail(orderDto.getUserDetailsDto().getEmail());
        emailData.setOrderDate(String.valueOf(orderDto.getCreatedAt()));
        emailData.setUpdatedStatus(String.valueOf(orderDto.getOrderStatus()));
        emailData.setItems(getItems(orderDto).toString());
        emailData.setTotalAmount(orderDto.getTotalCost());
        return emailData;
    }

    private String getOrderId(OrderDto orderDto) {
        String orderId = orderDto.getId();
        if(orderId != null && !orderId.isEmpty()){
            String[] parts = orderId.split("-");
            return parts[parts.length-1];
        }
        return null;
    }

    private StringBuilder getItems(OrderDto orderDto) {
        StringBuilder items = new StringBuilder();
        for(BoughtProductDetailsDto item : orderDto.getBoughtProductDetailsList()){
            items.append(item.getName())
                    .append(" (")
                    .append(item.getBoughtQuantity())
                    .append(" ")
                    .append(item.getUnit())
                    .append(")\n");
        }
        return items;
    }

    public void sendOrderConfirmationEmail(EmailData emailData){
        String subject = "Order Confirmation - KaseraG";
        String text = "Dear " + emailData.getName() + ",\n\n" +
                "Thank you for placing your order with KaseraG! We are thrilled to have the opportunity to serve you. Here are the details of your order:\n\n" +
                "Order Number: " + emailData.getOrderNumber() + "\n" +
                "Order Date: " + emailData.getOrderDate() + "\n" +
                "Items Ordered:\n" + emailData.getItems() + "\n" +
                "Total Amount: " + emailData.getTotalAmount() + "\n\n" +
                "You will receive another email once your order is shipped. If you have any questions or need further assistance, please do not hesitate to contact our customer service team at " + emailData.getEmail() + " or " + emailData.getPhone()
                + ".\n\n" +
                "Thank you for choosing KaseraG!\n\n" +
                "Best regards,\n" +
                "The KaseraG Team";

        sendEmail(emailData, subject, text);
    }

    public void sendAdminNotificationEmail(EmailData emailData) {
        String subject = "New Order Placed - " + emailData.getOrderNumber();
        String text = "Dear Admin,\n\n" +
                "A new order has been placed on KaseraG. Below are the details of the order:\n\n" +
                "Order Number: " + emailData.getOrderNumber() + "\n" +
                "Customer Name: " + emailData.getName() + "\n" +
                "Order Date: " + emailData.getOrderDate() + "\n" +
                "Items Ordered:\n" + emailData.getItems() + "\n" +
                "Total Amount: " + emailData.getTotalAmount() + "\n\n" +
                "Please review the order and proceed with the necessary actions to ensure timely processing and delivery.\n\n" +
                "Best regards,\n" +
                "KaseraG Order Notification System";

        sendEmail(emailData, subject, text);
    }

    public void sendOrderStatusUpdateEmail(EmailData emailData) {
        String subject = "Order Status Update - KaseraG";
        String text = "Dear " + emailData.getName() + ",\n\n" +
                "We wanted to inform you that the status of your order " + emailData.getOrderNumber() + " has been updated. Here are the latest details:\n\n" +
                "Order Number: " + emailData.getOrderNumber() + "\n" +
                "Order Date: " + emailData.getOrderDate() + "\n" +
                "Previous Status: " + emailData.getPreviousStatus() + "\n" +
                "Updated Status: " + emailData.getUpdatedStatus() + "\n" +
                "Estimated Delivery Date (if applicable): " + emailData.getEstimatedDeliveryDate() + "\n\n" +
                "Status Details:\n" + "statusDetails" + "\n\n" +
                "If you have any questions or need further assistance, please feel free to contact our customer service team at " + emailData.getEmail() + " or " + emailData.getPhone() + ".\n\n" +
                "Thank you for choosing KaseraG!\n\n" +
                "Best regards,\n" +
                "The KaseraG Team";

        sendEmail(emailData, subject, text);
    }

    public void sendAdminOrderCancellationEmail(EmailData emailData) {
        String subject = "Order Cancellation - " + emailData.getOrderNumber();
        String text = "Dear Admin,\n\n" +
                "An order has been canceled on KaseraG. Below are the details of the canceled order:\n\n" +
                "Order Number: " + emailData.getOrderNumber() + "\n" +
                "Customer Name: " + emailData.getName() + "\n" +
                "Order Date: " + emailData.getOrderDate() + "\n" +
                "Items Ordered:\n" + emailData.getItems() + "\n" +
                "Total Amount: " + emailData.getTotalAmount() + "\n\n" +
                "Please review the order and proceed with the necessary actions.\n\n" +
                "Best regards,\n" +
                "KaseraG Order Notification System";

        sendEmail(emailData, subject, text);
    }

    public void sendOrderCancellationEmailToUser(EmailData emailData) {
        String subject = "Order Cancellation Notification - KaseraG";
        String text = "Dear " + emailData.getName() + ",\n\n" +
                "We regret to inform you that your order " + emailData.getOrderNumber() + " placed on " + emailData.getOrderNumber() + " has been canceled. Here are the details of the canceled order:\n\n" +
                "Order Number: " + emailData.getOrderNumber() + "\n" +
                "Order Date: " + emailData.getOrderDate() + "\n" +
                "Items Ordered:\n" + emailData.getItems() + "\n" +
                "Total Amount: " + emailData.getTotalAmount() + "\n\n" +
                "If you have any questions or need further assistance, please do not hesitate to contact our customer service team at " + emailData.getEmail() + " or " + emailData.getPhone() + ".\n\n" +
                "We apologize for any inconvenience this may have caused and appreciate your understanding.\n\n" +
                "Thank you for choosing KaseraG.\n\n" +
                "Best regards,\n" +
                "The KaseraG Team";

        sendEmail(emailData, subject, text);
    }

    private void sendEmail(EmailData emailData, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailData.getEmail());
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
