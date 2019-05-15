package stripe;
import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;

public class PaymentsTest {
	
	public PaymentsTest(String key) {
		Stripe.apiKey = key;
	}
}