package com.BookingApp.Security.Email;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;

@Service
public class EmailService {


    public EmailService(){

    }

    public void sendForgotPasswordEmail(String email, String token) {

    }




   }
