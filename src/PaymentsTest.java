import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;

public class PaymentsTest {

    public static void main(String[] args) {
        Stripe.apiKey = "sk_test_dZGN1z9nd2Bx0WHAMfRmomsJ00wCLPBWmC";

        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 50);
        chargeMap.put("currency", "eur");
        chargeMap.put("source", "tok_amex"); // obtained via Stripe.js

        try {
            Charge charge = Charge.create(chargeMap);
            System.out.println(charge);
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}