package com.amazonaws.lambda;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.opencsv.bean.CsvToBeanBuilder;

public class UserBulkUpload implements RequestHandler<Object, String> {

	private String AccessKey = "AKIAYUCMZEDM5Q3UW7ZH";
	private String SecretKey = "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";

	private String AppClientId = "6t097jurf8m5rhj6gh2cklmik8";
	private String password = "Btech@99";

	@Override
	public String handleRequest(Object input, Context context) {
		context.getLogger().log("Input: " + input);
		try {
			
//			getting details from event
			LinkedHashMap map = (LinkedHashMap) input;
			LinkedHashMap details = (LinkedHashMap) map.get("detail");
			String base64String = (String)details.get("Base64String");
			context.getLogger().log(base64String);
			

//			decoding base64 encoded file and adding to input stream

			byte[] decodedBody = Base64.getDecoder().decode(base64String);
			context.getLogger().log("Body decoded");
			InputStream stream = new ByteArrayInputStream(decodedBody);
			context.getLogger().log("InputStream set");

//				converting .csv base 64 to list of java objects 

			List<user> ll = new CsvToBeanBuilder<user>(new InputStreamReader(stream)).withType(user.class).build()
					.parse();

//				 creating aws cognito client

			AWSCredentials cred = new BasicAWSCredentials(AccessKey, SecretKey);
			AWSCredentialsProvider credProvider = new AWSStaticCredentialsProvider(cred);
			AWSCognitoIdentityProvider client = AWSCognitoIdentityProviderClientBuilder.standard()
					.withCredentials(credProvider).withRegion(Regions.AP_SOUTH_1).build();
			context.getLogger().log("Cognito Client created !");

			for (user u : ll) {
				String SupervisorName = u.getFirstName() + " " + u.getLastName();
				String userName = u.getFirstName() + u.getLastName();

				AttributeType[] attribute = new AttributeType[5];

				AttributeType email = new AttributeType();
				email.setName("email");
				email.setValue(u.getEmailId());

				AttributeType OrgIdAttribute = new AttributeType();
				OrgIdAttribute.setName("custom:OrgId");
				OrgIdAttribute.setValue(u.getOrgId());

				AttributeType SupervisorIdAttribute = new AttributeType();
				SupervisorIdAttribute.setName("custom:SupervisorId");
				SupervisorIdAttribute.setValue(u.getEmployeeId());

				AttributeType SupervisorLevelAttribute = new AttributeType();
				SupervisorLevelAttribute.setName("custom:SupervisorLevel");
				SupervisorLevelAttribute.setValue(u.getLevel());

				AttributeType SupervisorNameAttribute = new AttributeType();
				SupervisorNameAttribute.setName("custom:SupervisorName");
				SupervisorNameAttribute.setValue(SupervisorName);

				attribute[0] = email;
				attribute[1] = OrgIdAttribute;
				attribute[2] = SupervisorIdAttribute;
				attribute[3] = SupervisorLevelAttribute;
				attribute[4] = SupervisorNameAttribute;

//				cognito Sign Up request set up

				SignUpRequest request = new SignUpRequest().withClientId(AppClientId).withUsername(userName)
						.withPassword(password).withUserAttributes(attribute);
				client.signUp(request);

			}
			
			context.getLogger().log("user bulk uploaded to cognito successfully !");
			return "user bulk uploaded to cognito successfully !";

		} catch (Exception e) {
			context.getLogger().log("Exception occured :-  " + e.getMessage());
			
		
			return "Exception occured :-  " + e.getMessage();

		}

 
	}

}
