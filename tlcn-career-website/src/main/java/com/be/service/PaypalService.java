package com.be.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.be.controller.exception.CommonRuntimeException;
import com.be.model.Post;
import com.be.model.PostOrder;
import com.be.model.Service;
import com.be.payload.BaseResponse;
import com.be.repository.PostOrderRepository;
import com.be.repository.PostRepository;
import com.be.utility.datatype.EROrderStatus;
import com.be.utility.datatype.EStatus;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@org.springframework.stereotype.Service
public class PaypalService {

    @Autowired
    private APIContext apiContext;

    @Autowired
    private PostOrderRepository orderRepo;

    @Autowired
    private PostRepository postRepo;

    public Payment createPayment(Long orderId, String cancelUrl, String successUrl) throws PayPalRESTException {

        PostOrder order = orderRepo.findById(orderId).get();
        if (order.getStatus() == EROrderStatus.PAID)
            throw new CommonRuntimeException("You already paid for this post .!");
        Post post = order.getPost();
        Service service = post.getService();

        Double total = order.getTotal();
        String currency = order.getCurrency().toString();
        String method = "paypal";
        String intent = "sale";
        String description = String.format("Rent service for post id (%d) with title (%s) in %d months (%d).",
                post.getId(), post.getTitle(), order.getDuration(), order.getId());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setName(String.format("Rent service %s in %d months.", service.getName(), order.getDuration()));
        item.setCurrency(currency);
        item.setPrice(service.getPrice().toString());
        item.setSku("001");
        item.setQuantity(order.getDuration().toString());
        
        items.add(item);
        itemList.setItems(items);

        Amount amount = new Amount();
        amount.setCurrency(currency);
        total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
    
        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    public BaseResponse handleSuccess(Payment payment) throws PayPalRESTException {

        try {
            String description = payment.getTransactions().get(0).getDescription();

            Matcher m = Pattern.compile("\\d+").matcher(description);
            List<Integer> lstNum = new ArrayList<>();
            while (m.find()) {
                lstNum.add(Integer.parseInt(m.group()));
            }

            Long postId = (long) lstNum.get(0);
            Long orderId = (long) lstNum.get(lstNum.size() - 1);

            Post post = postRepo.findById(postId).get();
            PostOrder order = orderRepo.findById(orderId).get();

            post.setStatus(EStatus.WAIT_FOR_ACCEPT);

            order.setPaidDate(new Date());
            order.setStatus(EROrderStatus.PAID);
            order.setPost(post);

            orderRepo.save(order);

        } catch (Exception e) {
            return new BaseResponse(false, e.getMessage());

        }
        return new BaseResponse(true, "Payment successfully, wait for admin to accept the post !");
    }
}
