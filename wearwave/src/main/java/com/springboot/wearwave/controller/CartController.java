package com.springboot.wearwave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.wearwave.model.Cart;
import com.springboot.wearwave.model.Item;
import com.springboot.wearwave.model.LoginUser;
import com.springboot.wearwave.service.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	@Autowired
	private CartService cartService;
	@PostMapping(value = "/item/cart.html")
	public ModelAndView cart(HttpSession session, String item_code, String selectedQuantity, String selectedSize,
			Cart cart) {
	    ModelAndView mav = new ModelAndView("index");
	    LoginUser user = (LoginUser) session.getAttribute("loginUser");
	    if (user == null) {
	        return new ModelAndView("redirect:/login.html"); // 로그인되지 않은 경우 로그인 페이지로 이동
	    }
	    String userId = user.getId();
	    mav.addObject("BODY", "cart/cart.jsp");
	    // Cart 객체 설정
	    cart.setUser_id(userId);
	    cart.setItem_code(item_code);
	    cart.setQuantity(Integer.parseInt(selectedQuantity));
	    // 장바구니에 추가 (중복 체크 포함)
	    this.cartService.putCart(cart);
	    // 사용자 장바구니 목록 불러오기
	    List<Cart> cartList = this.cartService.getUserCart(userId);
	    mav.addObject("cartList", cartList);
	    return mav;
	}
}