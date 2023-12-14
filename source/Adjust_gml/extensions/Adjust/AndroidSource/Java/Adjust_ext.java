package ${YYAndroidPackageName};
import ${YYAndroidPackageName}.R;
import com.yoyogames.runner.RunnerJNILib;

import com.adjust.sdk.AdjustEvent;
import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;

import android.app.Activity;
import android.util.Log;

import org.json.JSONObject;
import java.io.IOException;
import org.json.JSONArray;
import java.lang.Exception;

//https://github.com/adjust/android_sdk#qs-integrate-sdk


public class Adjust_ext extends RunnerSocial
{
	public static Activity activity = RunnerActivity.CurrentActivity;
	
	public void onPause() 
	{
		Adjust.onPause();
	}
	
	
	public void onResume()
	{
		Adjust.onResume();
	}
	
	
	private Object JSONObjectGet(JSONObject jsonObj,String key)
	{
		try 
		{
			if(jsonObj.isNull(key)) 
				return null;
			return jsonObj.get(key);
		}
		catch(Exception e)
		{return null;}
	}
	
	public void adjust_init()
	{
		String appToken = RunnerJNILib.extOptGetString("Adjust", "Android_AppToken");
		String environment = RunnerJNILib.extOptGetReal("Adjust", "Debug")>0.5 ? AdjustConfig.ENVIRONMENT_SANDBOX : AdjustConfig.ENVIRONMENT_PRODUCTION;
        AdjustConfig config = new AdjustConfig(activity, appToken, environment);
        Adjust.onCreate(config);
		
		//Adjust logging
		// config.setLogLevel(LogLevel.VERBOSE); // enable all logs
		// config.setLogLevel(LogLevel.DEBUG); // disable verbose logs
		// config.setLogLevel(LogLevel.INFO); // disable debug logs (default)
		// config.setLogLevel(LogLevel.WARN); // disable info logs
		// config.setLogLevel(LogLevel.ERROR); // disable warning logs
		// config.setLogLevel(LogLevel.ASSERT); // disable error logs
		// config.setLogLevel(LogLevel.SUPRESS); // disable all logs
	}
	
	// Event tracking
	public void adjust_track_event(String event,String str_json)
	{
		AdjustEvent adjustEvent = new AdjustEvent(event);
		
		JSONObject fluent_obj;
		
		try 
		{fluent_obj = new JSONObject(str_json);}
		catch (Exception e) 
		{return;}
		
		if(fluent_obj.has("Revenue"))
		{
			try
			{
				JSONObject object = fluent_obj.getJSONObject("Revenue");
				adjustEvent.setRevenue((double)JSONObjectGet(object,"Amount"),(String)JSONObjectGet(object,"Currency"));
			}
			catch (Exception e) 
			{return;}
			
		}
		
		if(fluent_obj.has("OrderId"))
			adjustEvent.setOrderId((String)JSONObjectGet(fluent_obj,"OrderId"));
		
		if(fluent_obj.has("CallbackId"))
			adjustEvent.setCallbackId((String)JSONObjectGet(fluent_obj,"CallbackId"));
		
		if(fluent_obj.has("CallbackParameter"))
		{
			try
			{
				JSONObject object = fluent_obj.getJSONObject("CallbackParameter");
				JSONArray keys = object.names ();

				for (int i = 0; i < keys.length (); ++i) 
				{
					String key = keys.getString (i); // Here's your key
					String value = object.getString(key); // Here's your value
				   
				   adjustEvent.addCallbackParameter(key,value);
				}
			} 
			catch (Exception e) 
			{
			}
		}
		
		if(fluent_obj.has("PartnerParameter"))
		{
			try
			{
				JSONObject object = fluent_obj.getJSONObject("PartnerParameter");
				JSONArray keys = object.names ();

				for (int i = 0; i < keys.length (); ++i) 
				{
					String key = keys.getString (i); // Here's your key
					String value = object.getString(key); // Here's your value
				   
				   adjustEvent.addPartnerParameter(key,value);
				}
			} 
			catch (Exception e) 
			{
			}
		}
		
		Adjust.trackEvent(adjustEvent);
	}
	
	
	public void adjust_add_session_callback_parameter(String key,String value)
	{
		Adjust.addSessionCallbackParameter(key,value);
	}


	public void adjust_remove_session_callback_parameter(String key)
	{
		Adjust.removeSessionCallbackParameter(key);
	}
	
	public void adjust_reset_session_callback_parameters()
	{
		Adjust.resetSessionCallbackParameters();
	}


	public void adjust_add_session_partner_parameter(String key,String value)
	{
		Adjust.addSessionPartnerParameter(key,value);
	}


	public void adjust_remove_session_partner_parameter(String key)
	{
		Adjust.removeSessionPartnerParameter(key);
	}

	public void adjust_reset_session_partner_parameters()
	{
		Adjust.resetSessionPartnerParameters();
	}
}

