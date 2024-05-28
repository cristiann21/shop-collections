package org.ies.tierno.shop.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class Shop {
    private String name;
    private Map<Integer, Product> productsById;
    private TreeSet<Customer> customers;


    public Customer findCustomer(String nif) {
        for (Customer customer : customers) {
            if (customer.getNif().equals(nif)) {
                return customer;
            }
        }
        return null;
    }

    public Product findcustomerByid(String nif, int id) {
        var customer = findCustomer(nif);
        if (customer != null) {
            for (Product product : productsById.values()) {
                if (product.getId() == id) {
                    return product;
                }
            }
        }
        return null;
    }

    public Product finProductById(int id) {
        for (Product product : productsById.values()) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public List<Product> products(String nif, int id) {
        List<Product> products = new ArrayList<>();
        var customer = findcustomerByid(nif, id);
        if (customer != null) {
            products.add(customer);
            return products;
        }
        return null;
    }

    public List<Product> etiqueta(String name) {
        List<Product> products = new ArrayList<>();
        for (Product product : productsById.values()) {
            if (product.getName().equals(name)) {
                products.add(product);
                return products;
            }
        }
        return null;
    }

    public Double nifBymoney (String nif) {
        var customerByNif = findCustomer(nif);
        if (customerByNif != null) {
            double total = 0;
            for (Order order : customerByNif.getOrders()) {
                total += order.getPrice();
            }
            return total;
        } else {
            return null;
        }
    }

    //    public Optional<Customer> findCustomer(String nif) {
//        return customers.stream()
//                .filter(customer -> customer.getNif().equals(nif))
//                .findFirst();
//    }
//
//    public Optional<Order> findCustomerOrder(String nif, int orderId) {
//        return findCustomer(nif)
//                .flatMap(customer ->
//                        customer.getOrders()
//                                .stream()
//                                .filter(order -> order.getId() == orderId)
//                                .findFirst()
//                );
//    }
//
//    public Product findById(int productId) {
//        if (productsById.containsKey(productId)) {
//            return productsById.get(productId);
//        } else {
//            return null;
//        }
//    }
//
//    public Optional<List<Product>> getOrderProducts(int orderId, String nif) {
//        Optional<Order> orderOpt = findCustomerOrder(nif, orderId);
//        return orderOpt.map(order ->
//                order
//                        .getItems()
//                        .stream()
//                        .map(item -> productsById.get(item.getProductId()))
//                        .collect(Collectors.toList())
//        );
//    }
//
//    public List<Product> findProductsByTag(String tag) {
//        return productsById
//                .values()
//                .stream()
//                .filter(product -> product.getTags().contains(tag))
//                .collect(Collectors.toList());
//    }
//
//    public Optional<Double> calculateCustomerExpenditures(String nif) {
//        Optional<Customer> customerOpt = findCustomer(nif);
//
//        return customerOpt
//                .map(customer ->
//                        customer
//                                .getOrders()
//                                .stream()
//                                .mapToDouble(order -> order.getPrice())
//                                .sum()
//                );
//    }

}
