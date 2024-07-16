package com.icodeap.ecommerce.backend.application;

import java.util.ArrayList;
import java.util.List;

import com.icodeap.ecommerce.backend.domain.model.Constants;
import com.icodeap.ecommerce.backend.domain.model.MailMessage;
import com.icodeap.ecommerce.backend.domain.model.Order;
import com.icodeap.ecommerce.backend.domain.model.OrderDTO;
import com.icodeap.ecommerce.backend.domain.model.OrderProduct;
import com.icodeap.ecommerce.backend.domain.model.OrderProductDTO;
import com.icodeap.ecommerce.backend.domain.model.OrderState;
import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.port.IMailSenderService;
import com.icodeap.ecommerce.backend.domain.port.IOrderRepository;
import com.icodeap.ecommerce.backend.domain.port.IProductRepository;
import com.icodeap.ecommerce.backend.domain.utils.ConvertersOrder;

public class OrderService {

    private  final IOrderRepository iOrderRepository;
    private final IProductRepository iProductRepository;
    private final IMailSenderService mailSender;
    private final MailOrder mailOrder;
    private ConvertersOrder converters;

    public OrderService(IOrderRepository iOrderRepository,IMailSenderService mailSender
    		,MailOrder mailorder,IProductRepository iProductRepository) {
        this.iOrderRepository = iOrderRepository;
        this.mailSender=mailSender;
        this.mailOrder=mailorder;
        this.iProductRepository=iProductRepository;
        converters=new ConvertersOrder();
    }

    public OrderDTO save (OrderDTO  order){  	
    	order.setOrderState(OrderState.CONFIRMED);
    	Order orderSave=converters.getOrderFromOrderDTO(order);	
    	orderSave=this.iOrderRepository.save(orderSave);
    	mailOrder.setDataOrder(orderSave);
    	MailMessage message=new MailMessage();
    	message.setTo(mailOrder.getUserMail());
    	message.setTitle("Ventas Market Fit");
    	message.setMessage(mailOrder.getDetailOrder());
    	mailSender.sendSimpleMessage(message);
    	order=converters.convertOrderDTOFromOrder(orderSave);
        return order;
    }
    
    public OrderDTO update(OrderDTO orderDTO) {
    	Order orderBD=iOrderRepository.findById(orderDTO.getId());
    	orderBD.setOrderState(converters.getStatusIntFromEnum(orderDTO.getOrderState()));
    	iOrderRepository.save(orderBD);
    	return orderDTO;
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

    public OrderDTO findByIdDTO(Integer id){
    	Order order=this.iOrderRepository.findById(id);
    	OrderDTO orderDTO = assignValuesfromOrderToDTO(order);
    	List<OrderProductDTO> orderProductsDTO = converters.convertProductsOrderToProductsDTO(order);
    	List idsProducts=orderProductsDTO.stream().map(p->p.getProductId()).toList();   	
    	Iterable<Product> products=iProductRepository.findByProductsId(idsProducts);
    	AssignNameDescriptionsProductsToDTOs(products,orderProductsDTO);   	
    	orderDTO.setOrderProducts(orderProductsDTO);   	
        return orderDTO;
    }
    
    public Order findById(Integer id){
    	Order order=this.iOrderRepository.findById(id);  	
        return order;
    }

	private void AssignNameDescriptionsProductsToDTOs(Iterable<Product> products,List<OrderProductDTO> orderProductsDTO) {
		products.forEach(p->{
    		OrderProductDTO productDTO=orderProductsDTO.stream().filter(dto->dto.getProductId()==p.getId())
    				.findFirst().get();
    		productDTO.setName(p.getName());
    		productDTO.setDescription(p.getDescription());
    	});
	}

	private OrderDTO assignValuesfromOrderToDTO(Order order) {
		OrderDTO orderDTO=new OrderDTO();
    	orderDTO.setId(order.getId());
    	orderDTO.setUserId(order.getUserId());
    	orderDTO.setDateCreated(order.getDateCreated());
    	orderDTO.setOrderState(converters.getStatusEnumFromInt(order.getOrderState()));
		return orderDTO;
	}


    public Iterable<OrderDTO> findByOrderState(OrderState orderState){
    	List<OrderDTO> ordersDTO=new ArrayList<>();
    	ConvertersOrder converters=new ConvertersOrder();
    	Iterable<Order> orders=this.iOrderRepository.findByOrderState(converters.getStatusIntFromEnum(orderState));
    	orders.forEach(o->{
    		List<OrderProductDTO> orderProductsDTO = converters.convertProductsOrderToProductsDTO(o);
    		ordersDTO.add(new OrderDTO(o.getId(), o.getDateCreated(),orderProductsDTO,
    				converters.getStatusEnumFromInt(o.getOrderState()),o.getUserId()));
    	});
        return ordersDTO;
    }
    

}
