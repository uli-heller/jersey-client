import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class JerseyClient {
	static public void main(String[] args) {
		if (args.length != 1) {
			System.err.println("AUFRUF: java -jar ... URL");
		} else {
			ClientConfig clientConfig = new ClientConfig();
			// clientConfig.register(MyClientResponseFilter.class);
			// clientConfig.register(new AnotherClientFilter());

			Client client = ClientBuilder.newClient(clientConfig);
			// client.register(ThirdClientFilter.class);

			WebTarget webTarget = client.target(args[0]);
			// webTarget.register(FilterForExampleCom.class);
			// WebTarget resourceWebTarget = webTarget.path("resource");
			// WebTarget helloworldWebTarget = resourceWebTarget.path("helloworld");
			// WebTarget helloworldWebTargetWithQueryParam =
			// helloworldWebTarget.queryParam("greeting", "Hi World!");

			// Invocation.Builder invocationBuilder =
			// helloworldWebTargetWithQueryParam.request(MediaType.TEXT_PLAIN_TYPE);
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN_TYPE);
			// invocationBuilder.header("some-header", "true");

			Response response = invocationBuilder.get();
			System.out.println(response.getStatus());
			System.out.println(response.readEntity(String.class));
		}
	}
}
