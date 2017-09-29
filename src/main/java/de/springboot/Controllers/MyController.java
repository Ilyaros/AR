package de.springboot.Controllers;

import de.springboot.DataBase.BaseControler;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;

@RestController
public class MyController {


    private static final String FILE_PATH = "/tmp/";
    private static final String FILE_EXTENSION = ".prefab";
    private static final String APPLICATION_PDF = "application/prefab";

    @RequestMapping("/hello")

    public String showHello() {

        System.out.println("ЗАПРОС ПОЛУЧЕН***********************************");

        return "hello-world";
    }

    @RequestMapping(value = "/download/{fileID}", method = RequestMethod.GET, produces = APPLICATION_PDF)
    public @ResponseBody
    void downloadfile(HttpServletResponse response, @PathVariable("fileID") int fileID) throws IOException {
        System.out.println("bla=bla");
        File file = getFile(fileID);
        InputStream in = new FileInputStream(file);

        response.setContentType(APPLICATION_PDF);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(in, response.getOutputStream());
        System.out.println("Загрузка файла : " + fileID + "-------------------------- " + new Date().toString());
    }

    private File getFile(int fileID) throws FileNotFoundException {
        String fullName = FILE_PATH + fileID + FILE_EXTENSION;
        File file = new File(fullName);
        if (!file.exists()) {
            throw new FileNotFoundException("file with path: " + fullName + " was not found.");
        }
        return file;

    }


    @RequestMapping(value = "/webrequest", method = RequestMethod.POST)
    @ResponseBody
    public String webRequest(WebRequest webRequest,Model model) {

        Iterator<String> iterator2 = webRequest.getParameterNames();
        System.out.println("\n<Iterator2>");
        while (iterator2.hasNext()) {
            String temp = iterator2.next();
            System.out.println(temp + " = " + webRequest.getParameter(temp));
        }
        System.out.println("</Iterator2>");

        System.out.println("//////////////////////////////////////////////");

        return "server_time = "+System.currentTimeMillis();
    }




    @RequestMapping(value = "/autorize/{login}/{psw}", method = RequestMethod.GET)
    @ResponseBody
    public String autorization(@PathVariable("login") String login, @PathVariable("psw") String psw) {

        System.out.println("Запрос на авторизацию");

        boolean isAutorized = BaseControler.autorize(login,psw);

        return Boolean.toString(isAutorized);

    }


    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ResponseBody
    public String registration(WebRequest webRequest) {
        System.out.println("");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("Запрос на регистрацию нового пользователя");

        String login = webRequest.getParameter("login");
        String psw = webRequest.getParameter("psw");
        String tel = webRequest.getParameter("tel");

        boolean isSuccses = BaseControler.register(login, psw, tel);

        return Boolean.toString(isSuccses);
    }
}
