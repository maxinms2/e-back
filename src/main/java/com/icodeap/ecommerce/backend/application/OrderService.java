package com.icodeap.ecommerce.backend.application;

import com.icodeap.ecommerce.backend.domain.model.MailMessage;
import com.icodeap.ecommerce.backend.domain.model.Order;
import com.icodeap.ecommerce.backend.domain.port.IMailSenderService;
import com.icodeap.ecommerce.backend.domain.port.IOrderRepository;

public class OrderService {

    private  final IOrderRepository iOrderRepository;
    private final IMailSenderService mailSender;
    private final MailOrder mailOrder;

    public OrderService(IOrderRepository iOrderRepository,IMailSenderService mailSender
    		,MailOrder mailorder) {
        this.iOrderRepository = iOrderRepository;
        this.mailSender=mailSender;
        this.mailOrder=mailorder;
    }

    public Order save (Order  order){
    	Order orderSave=this.iOrderRepository.save(order);
    	mailOrder.setDataOrder(orderSave);
    	MailMessage message=new MailMessage();
    	message.setTo(mailOrder.getUserMail());
    	message.setTitle("Ventas Market Fit");
    	message.setMessage(mailOrder.getDetailOrder());
    	mailSender.sendSimpleMessage(message);
        return orderSave;
    }
    public Iterable<Order> findAll(){
        return this.iOrderRepository.findAll();
    }

    public Iterable<Order> findByUserId(Integer userId){
        return this.iOrderRepository.findByUserId(userId);
    }
    public void updateStateById(Integer id, String state){
        this.iOrderRepository.updateStateById(id, state);
    }

    public Order findById(Integer id){
        return  this.iOrderRepository.findById(id);
    }


}
