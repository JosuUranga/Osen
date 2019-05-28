package stripe;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.stripe.model.Token;

public class Suscripciones {
	
	public Suscripciones(String key) {
		Stripe.apiKey = key;
	}
	public boolean createCustomer(String name,String mail) throws StripeException{
		Customer customer=findCustomer(mail);
		if (customer!=null) {
			return true;
		}
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("description", "Cliente para osen");
		customerParams.put("email", mail);
		customerParams.put("name", name);
		Customer.create(customerParams);
		return false;
	}
	public boolean createCard(String mail,String num,String cadAno,String cadMes,String cvc) throws StripeException{
		Customer customer=findCustomer(mail);
	    Map<String, Object> cardParams = new HashMap<String, Object>();
	    cardParams.put("number", num);
	    cardParams.put("exp_month", cadMes);
	    cardParams.put("exp_year", cadAno);
	    cardParams.put("cvc", cvc);

	    Map<String, Object> tokenParams = new HashMap<String, Object>();
	    tokenParams.put("card", cardParams);
	    Token cardToken = Token.create(tokenParams);

	    Map<String, Object> sourceParams = new HashMap<String, Object>();
	    sourceParams.put("source", cardToken.getId());
		customer.getSources().create(sourceParams);
		return false;
	}
	private Customer findCustomer(String mail) throws StripeException{
		Map<String, Object> options = new HashMap<>();
		options.put("email", mail);
		List<Customer> customers;
		customers = Customer.list(options).getData();
		Customer customer=null;
		if (customers.size() > 0) {
		    customer = customers.get(0);
		}
		return customer;
	}
	public boolean activateSub(String mail) throws StripeException{
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("plan","plan_F4UxD4XuHMw9sR");

		Map<String, Object> items = new HashMap<String, Object>();
		items.put("0", item);
		Customer customer=this.findCustomer(mail);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customer", customer.getId());
		params.put("items", items);
		Subscription.create(params);
		return false;
	}
	private Subscription getSub(String mail) throws StripeException{
		Customer customer=this.findCustomer(mail);
		if(customer==null)return null;
		Map<String, Object> options = new HashMap<>();
		options.put("customer", customer.getId());
		List<Subscription> subscriptions;
		subscriptions = Subscription.list(options).getData();
		Subscription subscription=null;
		if (subscriptions.size() > 0) {
			subscription = subscriptions.get(0);
		}
		return subscription;
	}
	public boolean checkSubActive(String mail) throws StripeException{
		Subscription sub=getSub(mail);
		if(sub==null)return false;
		if (sub.getStatus().equals("active"))return true;
		return false;
	}
	public void cancelSub(String mail) throws StripeException{
		Subscription sub=getSub(mail);
		sub.cancel();
	}
}