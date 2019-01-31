package com.transfermoney.service;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.transfermoney.bo.Account;
import com.transfermoney.dao.ConnectionFactory;
import com.transfermoney.util.PropertiesUtil;

public class AccountServiceTest {

	private static final String ACCOUNT_SERVICE_URL = PropertiesUtil.getProperty("account_service_url");
	private static final Client client = ClientBuilder.newClient();

	@Before
	public void loadTestData() {
		ConnectionFactory.populateTestData();

	}

	@Test
	public void getAllAccountsTest() {

        URI uri = builder.setPath("/account/all").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertTrue(statusCode == 200);
        //check the content
        String jsonString = EntityUtils.toString(response.getEntity());
        Account[] accounts = mapper.readValue(jsonString, Account[].class);
        Assert.assertTrue(accounts.length > 0);
	}

}
