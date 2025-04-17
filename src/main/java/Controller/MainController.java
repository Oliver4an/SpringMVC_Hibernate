package Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Model.User;
import Service.Service;
import Service.ServiceImpl;

@Controller
public class MainController {
	Service uService = new ServiceImpl();

	@GetMapping("/index")
	public String main() {
		return "LogInSignUp";
	}

	@PostMapping("/login")
	@ResponseBody
	public String LogIn(@RequestParam String uname, @RequestParam String pwd, HttpSession session) {
		System.out.printf("Name: %s Password: %s\n", uname, pwd);

		if (uService.selectUser(uname, pwd)) {
			session.setAttribute("user", uname);
			return "/SpringMVC_Hibernate/dashboard"; // 修正 return 路徑以符合 context path
		}
		return "false";
	}

	@PostMapping("/signup")
	@ResponseBody
	public String SignUp(@RequestBody User user) {
		System.out.println("帳號: " + user.getName());
		System.out.println("密碼: " + user.getPassword());
		System.out.println("Email: " + user.getEmail());
		System.out.println("Phone: " + user.getNumber());

		uService.insertUser(user);
		return "Sign up successfully";
	}

	@PostMapping("/checkUname")
	@ResponseBody
	public String checkUname(@RequestParam String uname) {
		System.out.println(uname);
		boolean result = uService.checkUserExist(uname);
		return Boolean.toString(result);
	}

	@PostMapping("/sendVerifyCode")
	@ResponseBody
	public String sendVerificationCode(@RequestBody Map<String, String> payload, HttpSession session) {
		String email = payload.get("email");
		String code = uService.register(email);
		System.out.println("Email: " + email);
		session.setAttribute("verifyCode", code);
		System.out.println("寄送驗證碼到 " + email + ": " + code);
		return "驗證碼已發送";
	}

	@PostMapping("/verify")
	@ResponseBody
	public String verifyCode(@RequestBody Map<String, String> payload, HttpSession session) {
		String inputCode = payload.get("code");
		String correctCode = (String) session.getAttribute("verifyCode");

		if (correctCode != null && correctCode.equals(inputCode)) {
			session.removeAttribute("verifyCode");
			return "success";
		} else {
			return "fail";
		}
	}

	@GetMapping("/dashboard")
	public String dashboardPage(HttpSession session, Model model) {
		String username = (String) session.getAttribute("user");
		User user = uService.getUserByName(username);
		model.addAttribute("user", user);
		System.out.printf("Name: %s Email: %s Phone: %s%n", user.getName(), user.getEmail(), user.getNumber());
		return "InfoDashBoard";
	}

	@PostMapping("/sendResetEmail")
	@ResponseBody
	public Map<String, Object> sendResetEmail(@RequestParam String email) {
		System.out.println("收到要重設密碼的 email：" + email);
		uService.reSetPwd(email);
		Map<String, Object> result = new HashMap<>();
		result.put("message", "驗證信已發送至 " + email);
		return result;
	}
}