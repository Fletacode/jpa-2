package jpabook.jpashop.controller;

import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jpabook.jpashop.domain.Member;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;


    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findAll();
        List<Item> items = itemService.findAll();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "/order/orderForm";

    }

    @PostMapping(value = "/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId, @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);

        return "redirect:/";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
         List<Order> orders = orderService.findOrders(orderSearch);


         model.addAttribute("orders", orders);

         return "/order/orderList";
    }

    @PostMapping("/orders/{orderid}/cancel")
    public String orderCancel(@PathVariable("orderid") Long orderid){
        orderService.cancelOrder(orderid);
        return "redirect:/orders";
    }



}
