package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://www.localhost:8888/tasks_frontend");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("admin_config", adminConfig);
        context.setVariable("preview_message", "Tasks: New Trello card");
        context.setVariable("goodbye_message", "Have a nice day");
        context.setVariable("company_details", adminConfig.getCompanyName() + "\n" +
                            adminConfig.getCompanyEmail() + "\n" + adminConfig.getCompanyPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildScheduledTasksInfoEmail(String message) {

        List<String> tasks_check = new ArrayList<>();
        tasks_check.add("You can check and manage your tasks on Trello");
        tasks_check.add("You can either manage your tasks directly from app");

        Context context = new Context();
        context.setVariable("preview_message", "Tasks: Daily info");
        context.setVariable("message", message);
        context.setVariable("trello_button", "Check out your Trello!");
        context.setVariable("trello_url",
                            "https://trello.com/login?truid=tr264fb8-4e59-ece3-2882-9adb87ee0aaa&returnUrl=%2F");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("seeYou_message", "See you soon!");
        context.setVariable("company_details", adminConfig.getCompanyName() + "\n" +
                adminConfig.getCompanyEmail() + "\n" + adminConfig.getCompanyPhone());
        context.setVariable("show_button", true);
        context.setVariable("show_button", true);
        context.setVariable("check_options", tasks_check);

        return templateEngine.process("mail/daily-tasks-info-mail", context);
    }
}
