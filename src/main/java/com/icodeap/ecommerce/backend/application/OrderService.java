package com.icodeap.ecommerce.backend.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.icodeap.ecommerce.backend.domain.model.Constants;
import com.icodeap.ecommerce.backend.domain.model.MailMessage;
import com.icodeap.ecommerce.backend.domain.model.Order;
import com.icodeap.ecommerce.backend.domain.model.OrderDTO;
import com.icodeap.ecommerce.backend.domain.model.OrderProduct;
import com.icodeap.ecommerce.backend.domain.model.OrderProductDTO;
import com.icodeap.ecommerce.backend.domain.model.OrderState;
import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.model.User;
import com.icodeap.ecommerce.backend.domain.port.IMailSenderService;
import com.icodeap.ecommerce.backend.domain.port.IOrderRepository;
import com.icodeap.ecommerce.backend.domain.port.IProductRepository;
import com.icodeap.ecommerce.backend.domain.port.IUserRepository;
import com.icodeap.ecommerce.backend.domain.utils.ConvertersOrder;
import com.icodeap.ecommerce.backend.infrastructure.service.ParameterService;

public class OrderService {

	private final IOrderRepository iOrderRepository;
	private final IProductRepository iProductRepository;
	private final IUserRepository iUserRepository;
	private final IMailSenderService mailSender;
	private final MailOrder mailOrder;
	private ConvertersOrder converters;

	@Autowired
	private ParameterService parameterService;

	public OrderService(IOrderRepository iOrderRepository, IMailSenderService mailSender, MailOrder mailorder,
			IProductRepository iProductRepository, IUserRepository iUserRepository) {
		this.iOrderRepository = iOrderRepository;
		this.mailSender = mailSender;
		this.mailOrder = mailorder;
		this.iProductRepository = iProductRepository;
		this.iUserRepository = iUserRepository;
		converters = new ConvertersOrder();
	}

	public OrderDTO save(OrderDTO order) {
		order.setOrderState(OrderState.CONFIRMED);
		Order orderSave = converters.getOrderFromOrderDTO(order);
		orderSave = this.iOrderRepository.save(orderSave);
		order = converters.convertOrderDTOFromOrder(orderSave);
		mailOrder.setDataOrder(orderSave);

		sendMail();
		return order;
	}

	private void sendMail() {
		Thread thread = new Thread(() -> {
			MailMessage message = new MailMessage();
			message.setTo(mailOrder.getUserMail());
			message.setTitle("Ventas SAGAFITMI");
			message.setMessage(mailOrder.getDetailOrder());
			mailSender.sendSimpleMessage(message);
			message.setTo(parameterService.getParameter("mail.market").get().getValor());
			mailSender.sendSimpleMessage(message);
		});

		// Iniciar el hilo
		thread.start();

	}

	public OrderDTO update(OrderDTO orderDTO) {
		Order orderBD = iOrderRepository.findById(orderDTO.getId());
		orderBD.setOrderState(converters.getStatusIntFromEnum(orderDTO.getOrderState()));
		iOrderRepository.save(orderBD);
		return orderDTO;
	}

	public Iterable<Order> findAll() {
		return this.iOrderRepository.findAll();
	}

	public Iterable<Order> findByUserId(Integer userId) {
		return this.iOrderRepository.findByUserId(userId);
	}

	public void updateStateById(Integer id, String state) {
		this.iOrderRepository.updateStateById(id, state);
	}

	public OrderDTO findByIdDTO(Integer id) {
		Order order = this.iOrderRepository.findById(id);
		OrderDTO orderDTO = assignValuesfromOrderToDTO(order);
		List<OrderProductDTO> orderProductsDTO = converters.convertProductsOrderToProductsDTO(order);
		List<Integer> idsProducts = orderProductsDTO.stream().map(p -> p.getProductId()).toList();
		Iterable<Product> products = iProductRepository.findByProductsId(idsProducts);
		AssignNameDescriptionsProductsToDTOs(products, orderProductsDTO);
		orderDTO.setOrderProducts(orderProductsDTO);
		return orderDTO;
	}

	public Order findById(Integer id) {
		Order order = this.iOrderRepository.findById(id);
		return order;
	}

	private void AssignNameDescriptionsProductsToDTOs(Iterable<Product> products,
			List<OrderProductDTO> orderProductsDTO) {
		products.forEach(p -> {
			List<OrderProductDTO> productsDTO = orderProductsDTO.stream().filter(dto -> dto.getProductId() == p.getId())
					.toList();
			productsDTO.forEach(dto -> {
				dto.setName(p.getName());
				dto.setDescription(p.getDescription());
			});

		});
	}

	private OrderDTO assignValuesfromOrderToDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setUserId(order.getUserId());
		orderDTO.setDateCreated(order.getDateCreated());
		orderDTO.setOrderState(converters.getStatusEnumFromInt(order.getOrderState()));
		return orderDTO;
	}

	public Iterable<OrderDTO> findByOrderState(OrderState orderState) {

		Iterable<Order> orders = this.iOrderRepository.findByOrderState(converters.getStatusIntFromEnum(orderState));
		List<OrderDTO> ordersDTO = buildOrdersDTOFromOrders(orders);
		return ordersDTO;
	}

	public Page<OrderDTO> findByFullNameAndEmail(OrderState orderState, String fullName, String email,
			Pageable pageable) {

		Page<Order> orderPage = iOrderRepository.findByFullNameAndEmail(converters.getStatusIntFromEnum(orderState),
				fullName, email, pageable);
		List<Order> orders = orderPage.stream().collect(Collectors.toList());
		List<OrderDTO> ordersDTO = buildOrdersDTOFromOrders(orders);

		return new PageImpl(ordersDTO, orderPage.getPageable(), orderPage.getTotalElements());
	}

	private List<OrderDTO> buildOrdersDTOFromOrders(Iterable<Order> orders) {
		ConvertersOrder converters = new ConvertersOrder();
		List<Integer> usersIds = StreamSupport.stream(orders.spliterator(), false).map(o -> o.getUserId())
				.collect(Collectors.toList());
		List<User> users = iUserRepository.findByIds(usersIds);
		List<OrderDTO> ordersDTO = new ArrayList<>();
		orders.forEach(o -> {
			List<OrderProductDTO> orderProductsDTO = converters.convertProductsOrderToProductsDTO(o);
			User user = users.stream().filter(u -> u.getId() == o.getUserId()).findFirst().get();
			ordersDTO.add(new OrderDTO(o.getId(), o.getDateCreated(), orderProductsDTO,
					converters.getStatusEnumFromInt(o.getOrderState()), o.getUserId(),
					user.getFirstName() + " " + user.getLastName(), user.getEmail()));
		});
		return ordersDTO;
	}

}
