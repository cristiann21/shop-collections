package org.ies.tierno.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private String name;
    private Map<Integer, Product> productsById;
    private TreeSet<Customer> customers;


    public Customer findCustomer(String nif) {
        for (var customer : customers) {
            if (customer.getNif().equals(nif)) {
                return customer;
            }
        }
        return null;
    }

    public Order findCustomerOrder(String nif, int orderId) {
        Customer customer = findCustomer(nif);
        if (customer != null) {
            for (var order : customer.getOrders()) {
                if (order.getId() == orderId) {
                    return order;
                }
            }
        }
        return null;
    }

    public Product findById(int productId) {
        if(productsById.containsKey(productId)) {
            return productsById.get(productId);
        } else {
            return null;
        }
    }
}
