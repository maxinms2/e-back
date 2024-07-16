package com.icodeap.ecommerce.backend.application;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.icodeap.ecommerce.backend.domain.model.Order;
import com.icodeap.ecommerce.backend.domain.model.OrderProduct;
import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.model.User;
import com.icodeap.ecommerce.backend.domain.port.IProductRepository;
import com.icodeap.ecommerce.backend.domain.port.IUserRepository;

import lombok.Data;
@Data
public class MailOrder {
	
	private String userName;
	private String userMail;
	private String detailOrder;
	

    private final IUserRepository userRepository;
    private final IProductRepository productRepository;
    public MailOrder(IUserRepository userRepository,
    		IProductRepository productRepository) {
    	this.userRepository=userRepository;
    	this.productRepository=productRepository;
    }
	
	public void setDataOrder(Order order) {
		User user = userRepository.findById(order.getUserId());
		if(user==null) {
			return;
		}
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
		this.userName=user.getFirstName()+" "+user.getLastName();
		this.userMail=user.getEmail();
		StringBuilder sb=new StringBuilder();
		sb.append("<head>");
		sb.append("<meta charset=\"UTF-8\">");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		sb.append("<title>Resumen de tu compra</title>");
		sb.append("    <style>"
				+ "        table {"
				+ "            width: 50%;"
				+ "            border-collapse: collapse;"
				+ "            margin: 20px 0;"
				+ "        }"
				+ "        th, td {"
				+ "            border: 1px solid #dddddd;"
				+ "            text-align: left;"
				+ "            padding: 8px;"
				+ "        }"
				+ "        th {"
				+ "            background-color: #f2f2f2;"
				+ "        }"
				+ "    </style>");
		sb.append("</head>");
		sb.append("<h1>"+this.userName+" gracias por tu compra" +"</h1>");
		sb.append("<h3>En breve nos comunicaremos contigo para dar seguimiento a tu orden</h3>");
		sb.append("<h3>Contacto al correo edgar.mh.0307@gmail.com o al teléfono 722 443 4791</h3>" );
		sb.append("<h3>Número de orden: "+order.getId()+"</h3>" );
		sb.append("<table>");
		sb.append("        <thead>\r\n"
				+ "            <tr>\r\n"
				+ "                <th>Producto</th>\r\n"
				+ "                <th>Talla</th>\r\n"
				+ "                <th>Cantidad</th>\r\n"
				+ "                <th>Precio</th>\r\n"
				+ "                <th>Subtotal</th>\r\n"
				+ "            </tr>"
				+ "        </thead>");
		sb.append("<tbody>");
		BigDecimal total=new BigDecimal(0);
		for(OrderProduct orderProduct:order.getOrderProducts()) {
			Product product = productRepository.findById(orderProduct.getProductId());
			sb.append("<tr>");
			sb.append("<td>"+product.getName()+" "+product.getDescription());
			sb.append("<td>"+orderProduct.getModel()+"</td>");
			sb.append("<td>"+orderProduct.getQuantity()+"</td>");
			sb.append("<td>$ "+decimalFormat.format(orderProduct.getPrice())+"</td>");
			BigDecimal subtotal=orderProduct.getPrice().multiply(orderProduct.getQuantity());
			sb.append("<td>$ "+decimalFormat.format(subtotal)+"</td>");
			total=total.add(subtotal);
			sb.append("</tr>");			
		}
		sb.append("<tr>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("<td></td>");
		sb.append("<td>TOTAL:</td>");
		sb.append("<td>$ "+decimalFormat.format(total)+"</td>");
		sb.append("</tr>");
		
		sb.append("</tbody>");
		sb.append("</table>");
		this.detailOrder=sb.toString();
	}

}
