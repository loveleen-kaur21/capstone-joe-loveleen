package com.example.capstone;

import com.example.capstone.pages.RequestPage;
import com.example.capstone.pages.ShiftPage;
import jdk.swing.interop.SwingInterOpUtils;
import net.bytebuddy.utility.RandomString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.*;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;

    private Utility utility;

    @Autowired
    private CustomUserDetailsService customUserService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private ShiftRepository shiftRepo;

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestRepository requestRepo;

    @Autowired
    private ShiftPage shiftPage;

    @GetMapping("/")
    public String viewPage() {
        Date date = new GregorianCalendar(2021, Calendar.OCTOBER, 3).getTime();
//        Date date = java.util.Calendar.getInstance().getTime();
        shiftService.generateShifts(date);
        return "redirect:/user/home/";
    }

    @GetMapping("/user/home/")
    public String viewHomePage(@RequestParam(value = "date", required = false) Date gdate, Model model, @RequestParam(value = "query", required = false) String query) {
        if (gdate == null) {
            long mil = System.currentTimeMillis();
            gdate = new Date(mil);
            model.addAttribute("searchDate", "");
            ZoneId defaultZoneId = ZoneId.systemDefault();
            Instant instant = gdate.toInstant();
            LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
            gdate = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        } else {
            System.out.println(gdate.getClass());
            model.addAttribute("searchDate", gdate.toString());
            System.out.println(gdate.toString());
        }

        model.addAttribute("date", gdate.toString());
        System.out.println("here is " + gdate);
        Date currentDate = java.util.Calendar.getInstance().getTime();
        customUserService.renderUser(model);
        List<Shift> shifts = shiftRepo.findAllByDateBetween(
                ShiftPage.getStartDate(gdate),
                ShiftPage.getEndDate(ShiftPage.getStartDate(gdate))
        );
        //find all by date between
        List<User> users;
        List<Date> datesList = shiftPage.dates(gdate);
        model.addAttribute("datesList", datesList);
        if (query != null && !query.equals("")) {
            users = userRepo.findAllByFullNameIgnoreCaseContaining(query);

            if (users.isEmpty()) {
                return "no_results_found";
            }
        } else {
            var userIds = shifts.stream().map(Shift::getUserID).collect(Collectors.toSet());
            users = userRepo.findAllByIdIn(userIds);
            if (users.isEmpty()) {
                return "no_results_found";
            }
        }
        shiftPage.setShiftMap(shiftPage.buildShiftMap(shifts));
        model.addAttribute("shiftPage", new ShiftPage(shifts, users));
        model.addAttribute("java8Instant", Instant.now());
        // logic for searchfield holding the date and next/prev look at it and decide what to present on page
        return "home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, HttpServletRequest request) {
        model.addAttribute("user", new User());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "signup_form.html";
        }
//        HttpSession session = request.getSession();
//        session.invalidate();
//        SecurityContextHolder.clearContext();
        return "redirect:/user/home/";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        customUserService.setGroup(user);

        userRepo.save(user);
        return "login";
    }

    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
//         Log out user on delete
//        HttpSession session = request.getSession();
//        session.invalidate();
//        SecurityContextHolder.clearContext();
        return "redirect:/user/home/";
    }

    @Configuration
    public static class MvcConfig implements WebMvcConfigurer {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/login").setViewName("login");
        }
    }

    @GetMapping("/user/admin_view_pending")
    public String showAdminPending(Model model) {
        model.addAttribute("user", new User());
        List<Request> requests = requestRepo.findAll();
//        requestPage.getFullName(requests);
        model.addAttribute("requestPage", new RequestPage(requests, shiftRepo, userRepo));
        model.addAttribute("requests", requests);
        return "admin_view_pending.html";
    }

    @GetMapping("/user/user_view_pending")
    public String showUserPending(Model model) {
        model.addAttribute("user", new User());
        return "user_view_pending";
    }

    @GetMapping("/user/schedule")
    public String showSchedule(Model model) {
        model.addAttribute("user", new User());
        return "schedule";
    }

    @GetMapping("/user/request_change")
    public String showRequestForm(Model model) {
        model.addAttribute("user", new User());
        RequestFormCreation newR = new RequestFormCreation();
        model.addAttribute("request_form", newR);
        System.out.println("showing ");
        System.out.println(newR.getDate());

        return "request_change";
    }

    @GetMapping("/accept/{id}")
    public String acceptRequest(@PathVariable Long id) {
        Optional<Request> request = requestRepo.findById(id);
        if (request.isPresent()) {
            Optional<Shift> shift = shiftRepo.findById(request.get().getRequesteeShiftID());
            if (shift.isPresent()){
                shift.get().setUserID(request.get().getRequesterID());
            }
            request.get().setIs_accepted(true);
            requestRepo.delete(request.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/user/home/";
    }

    @GetMapping("/reject/{id}")
    public String rejectRequest(@PathVariable Long id) {
        Optional<Request> request = requestRepo.findById(id);
        if (request.isPresent()) {
            requestRepo.delete(request.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/user/home/";
    }

    @RequestMapping(value = "/save_request", method = RequestMethod.POST)
    public String processApplication(Request request, RequestFormCreation requestFormCreation, @AuthenticationPrincipal CustomUserDetails userDetails, Model model) throws ParseException {
        model.addAttribute("user", new User());
        model.addAttribute("request", new Request());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = ((CustomUserDetails) principal).getFullName();
//
        Request requestNow = requestService.createRequest(requestFormCreation.getFullName(), requestFormCreation.getDate(), requestFormCreation.getShift(), username);
        requestRepo.save(requestNow);
        return "redirect:/user/home/";
    }


    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "forgot_password_form.html";
        }
        return "redirect:/user/home/";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = customUserService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = customUserService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            customUserService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "login";
    }

    @PostMapping("/forgot_password")
    public String processForgotPasswordForm(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            customUserService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
            return "login";


        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "forgot_password_form";
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("nursesystem85@gmail.com", "Nurse System Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

}


//}
