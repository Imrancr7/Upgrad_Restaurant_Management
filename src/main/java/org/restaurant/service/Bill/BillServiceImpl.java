package org.restaurant.service.Bill;


import org.restaurant.model.Bill;
import org.restaurant.model.OrderItem;
import org.restaurant.repo.DAOInterface.BillDAO;
import org.restaurant.repo.DAOInterface.MenuItemDAO;
import org.restaurant.repo.DAOInterface.OrderItemDAO;
import java.time.LocalDateTime;
import java.util.List;

public class BillServiceImpl implements BillService {

    private final BillDAO billDAO;
    private final OrderItemDAO orderItemDAO;
    private final MenuItemDAO menuItemDAO;

    public BillServiceImpl(BillDAO billDAO, OrderItemDAO orderItemDAO,
                           MenuItemDAO menuItemDAO) {
        this.billDAO = billDAO;
        this.orderItemDAO = orderItemDAO;
        this.menuItemDAO = menuItemDAO;
    }

    @Override
    public void generateBill(int orderId) {
        List<OrderItem> items = orderItemDAO.getOrderItemsByOrderId(orderId);
        double total = items.stream()
                .mapToDouble(i -> i.getQuantity() * menuItemDAO.getMenuItemById(i.getItemId()).getPrice())
                .sum();

        Bill bill = new Bill.Builder()
                .orderId(orderId)
                .totalAmount(total)
                .generatedTime(LocalDateTime.now())
                .status("UNPAID")
                .build();

        Bill saved = billDAO.addBill(bill);
        System.out.println("Bill generated for Order ID " + orderId + " | Total: ₹" + saved.getTotalAmount());
    }
}

