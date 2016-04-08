package com.wjwl.mobile.taocz.boc;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.wjwl.mobile.taocz.R;
import com.chinamworld.electronicpayment.IRemoteService;
import com.chinamworld.electronicpayment.json.BOCPAYUtil;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bocactivity_main); 
		// 绑定服务
		bindService(new Intent(IRemoteService.class.getName()),
				serviceConnection, Context.BIND_AUTO_CREATE);
		findViewById(R.id.bt_4).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startAPK();
			}
		});
		
	}

	/**
	 * 
	 * @param packageName
	 *            订单信息
	 */
	public void startAPK() {
		try {
			String custTranId = ((EditText) findViewById(R.id.zxf_custTranId))
					.getText().toString();
			String orderNo = ((EditText) findViewById(R.id.zxf_orderNo))
					.getText().toString();
			String merchantNum = ((EditText) findViewById(R.id.et_1)).getText()
					.toString();
			String curCode = ((EditText) findViewById(R.id.et_2)).getText()
					.toString();
			String orderAmount = ((EditText) findViewById(R.id.et_3)).getText()
					.toString();
			String orderqishu = ((EditText) findViewById(R.id.et_4)).getText()
					.toString();
			String orderNote = ((EditText) findViewById(R.id.order_what))
					.getText().toString(); // 订单说明

			String planCode = ((EditText) findViewById(R.id.et_5)).getText()
					.toString();
			String planNumber = ((EditText) findViewById(R.id.et_6)).getText()
					.toString();
			String signCspData = ((EditText) findViewById(R.id.et_7)).getText()
					.toString();
			//封装订单信息
			/*final String businessResult = "{\"result\":{\"custTranId\":\""
						+ custTranId
						+ "\", \"tranType\":\""
						+ "01"
						+ "\", \"merchantNo\":\""
						+ merchantNum
						+ "\", \"orderNo\":\""
						+ orderNo
						+ "\", \"curCode\":\""
						+ curCode
						+ "\",\"orderAmount\":\""
						+ orderAmount
						+ "\", \"orderTime\":\"20130321132413\", \"orderNote\":\""
						+ orderNote
						+ "\", \"orderUrl\":\"http://22.11.101.182:8087/SimMerchant/showPara.do\",  \"mNormalData\":\"{c=123, d=456}\", \"signature\":\"xx\"},\"payType\"=\"0\"}";*/
			/*{"result":{"custTranId":"10000779855655a50066508119", "tranType":"01", "merchantNo":
			 * "104110082201632", "orderNo":"201311281714159672", "curCode":"001",
			 * "orderAmount":"200.00", "orderTime":"20131125142425","orderNote":"短信标题", 
			 * "orderUrl":"http://test.lashoupay.com/lashoupay/wapPayCallBackAction.action?bankid=109$$$206$$$10062",
			 * "signature":"MIIEFQYJKoZIhvcNAQcCoIIEBjCCBAICAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCAw8wggMLMIIB86ADAgECAgUQEQOUMDANBgkqhkiG9w0BAQUFADAhMQswCQYDVQQGEwJDTjESMBAGA1UEChMJQ0ZDQSBPQ0ExMB4XDTEzMDkxMjAxMTY1MVoXDTE1MDkxMjAxMTY1MVowZDELMAkGA1UEBhMCY24xEjAQBgNVBAoTCUNGQ0EgT0NBMTEMMAoGA1UECxMDQk9DMRgwFgYDVQQLEw9Pcmdhbml6YXRpb25hbDIxGTAXBgNVBAMTEDk1NTY2U1owMDAwMDA5NjMwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAKmTIxhgKzH8XeBUKcnKp6gdBVbyo3O9nebJGYBENWBXyIw4d68iK+RPAY89zbipmftHq1eiThmtOwrllAktJD9uxDeyFFk6cv0B/TVAY6cWrs+HLiA+Iz9sqGP82sBRnosf6oyvzgng7RuDcF3cKkRVG+caUVds1rbI3N1xVl2dAgMBAAGjgYowgYcwHwYDVR0jBBgwFoAU0dvpiILl3RqPTKoAjL588qsb9tkwOAYDVR0fBDEwLzAtoCugKYYnaHR0cDovL2NybC5jZmNhLmNvbS5jbi9SU0EvY3JsMTE2OTAuY3JsMAsGA1UdDwQEAwIGwDAdBgNVHQ4EFgQU5vYlbttojl/qEk5nWjlN7Ehd/9UwDQYJKoZIhvcNAQEFBQADggEBAGVAqnO3RW2Oo+HUZPCt8NZnYbv5/+MCx3RXvyBeAn0vr8bgtjCCWhpiB2gucpo+Gw6O93VVvDy0Hw+F0CZ70KLE5Z6SPD6bFW6j3Ia+nQGUF8S+L0MqS4/tNPhLTpz1pGpBqxn1yxx4DIQam8i4kMmsq2geVQixtWB1Y4qDVzj2+PK80rxWWI+H3aCGVBFJAkoHfJXZKqa3kxYHeyHSNgykZMWiYgM1kUVl9FYG8Qm5hMBYnPN0mj0x0l0LlSSJr58IBAaqOukFq6mDa8yJBb+hVKqNWZBKYF8qJt4KY66aFeXFAZqDKn+ibUHABnPt+TJJ0BpGNwOAaoIPlkOnaAYxgc8wgcwCAQEwKjAhMQswCQYDVQQGEwJDTjESMBAGA1UEChMJQ0ZDQSBPQ0ExAgUQEQOUMDAJBgUrDgMCGgUAMA0GCSqGSIb3DQEBAQUABIGAGZSpevR+DDgMCh/VVx3XwNnAvcXRyJC+18saiE9avAbgy+73guYph99568R1M02lLhZkteGND9Msc+XbKMfLCf901d0twQ0IQxTSmZ7QLDTvKMn7JS7sb3sXUK6YnQxOijSzJ5wQxMosicNO4Kw2D6Wml2tnqnj3ouhs7F0mdso="},"payType":"0"}
			 */
			/*businessResult===={"result":{"custTranId":"2013112900111726", "tranType":"01", 
			 * "merchantNo":"104320148141015",
			 *  "orderNo":"2013112900111726",
			 *   "curCode":"001",
			 *   "orderAmount":"10", 
			 *   "orderTime":"20130321132413", 
			 *   "orderNote":"广电项目缴费", 
			 *   "orderUrl":"http://122.96.58.36:7002/merchant/person/pc/app/toBocMessage?bankDepositId=8662",  
			 *   "mNormalData":"{c=123, d=456}",
			 *    "signature":"MIIE7gYJKoZIhvcNAQcCoIIE3zCCBNsCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA58w
				ggObMIICg6ADAgECAhAtnGBp8aVxrpzhRo5pddgSMA0GCSqGSIb3DQEBBQUAMF0xCzAJBgNVBAYT
				AmNuMRYwFAYDVQQKEw1iYW5rIG9mIGNoaW5hMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdi
				ZWlqaW5nMRIwEAYDVQQDEwlib2NuZXQgY2EwHhcNMTMwNjA2MDkxODQ2WhcNMTgwNjA1MDkxODQ2
				WjCBuTELMAkGA1UEBhMCQ04xFjAUBgNVBAoTDUJBTksgT0YgQ0hJTkExgZEwgY4GA1UEAx6Bhmxf
				gs93AV5/dTVnCX6/T+Fgb39RftyAoU79ZwmWUFFsU/gAXwAzADMAMQBhADkAYwA5ADAAOAAxAGMA
				ZgA0ADUAMwAzAGMANQA3ADgANgAzAGEANwA0ADEAOABlADkANQA3AGMAMABiADEANAAyAGEANwBj
				AF+WSGimWh8AXwAzADIAMgA4MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpzF6lljY9t6PS
				FoHGRuYui7rZhXtqISh2afmIaHgBg2SRgmndPJ3dj8+yUoAvi44RvbZJ7fACJz3U8DzPUL6kIDt9
				ew5LLoWv55JBp217LFnNwPJ0EHJQduPsCFbtFj2nmNUyJRwYM/L9Ea0At3vjaunQ6R0+Txf5ITuX
				zhTXNwIDAQABo34wfDAfBgNVHSMEGDAWgBQmar81qyOZaxvwUDeNenNg2gFSUDAtBgNVHR8EJjAk
				MCKgIKAehhxodHRwOi8vMjYuMTIwLjM2LjMyL2NybDEuY3JsMAsGA1UdDwQEAwIE8DAdBgNVHQ4E
				FgQUXRi254Jv/unha5aBKfEWdvPBGdkwDQYJKoZIhvcNAQEFBQADggEBAAHhqm9JH4Fftijf6M+2
				gopHrEN2JytnrZKi2Ka25kdVRreJw3YIwTbPd84W0r9HoaTYx6VEFkBIAIogdpD3nAYSYHV3a/yD
				ljsd+3rxMSC71C5/A/hFDiteN9w1Qs5jkV1jlLGCdzKo0apGQqIqfismOWc6ZHuokLGYgNxmbUyO
				OBU6gXUvmDz+HDOs2GXX8r2+RYWttfSeP9tTdZBrFyi1cfBpKmc3cDx+z/bDVw6DL6GTs2gXJ4Rg
				IL9jJPk1GW1ZRIa65qxeQB6sIwQz3I3RifWcm3Cxgti4GqszqCddIJajSs9bU0Am07fFaF2HeFkZ
				4QTmISmLARfWIieaVugxggEXMIIBEwIBATBxMF0xCzAJBgNVBAYTAmNuMRYwFAYDVQQKEw1iYW5r
				IG9mIGNoaW5hMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdiZWlqaW5nMRIwEAYDVQQDEwli
				b2NuZXQgY2ECEC2cYGnxpXGunOFGjml12BIwCQYFKw4DAhoFADANBgkqhkiG9w0BAQEFAASBgA9q
				PdVpUnuYTSouuUeKTVoRDbED0NRjfLouU3up5T/XoIQxpPcL0U0rENO8xPdGRNnTaFSYdVPiplzF
				dbQ4/1tz8MPq7FE4UCPsxRdDgo1e/hFmPP5GAqw/x5NWo0MonC27j/DTAp8F2j1GTXxRjEvMavrw
				5IJ9QwZCNUip8ffJ"},"payType"="0"}
*/			
			/*{"result":{"custTranId":"2013112900111726", 
				"tranType":"01", 
				"merchantNo":"104320148141015", 
				"orderNo":"2013112900111726", 
				"curCode":"001","orderAmount":"10", 
				"orderTime":"20130321132413",
				"orderNote":"广电项目缴费", 
				"orderUrl":"http://122.96.58.36:7002/merchant/person/pc/app/toBocMessage?bankDepositId=8662",
				"signature":"MIIE7gYJKoZIhvcNAQcCoIIE3zCCBNsCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA58w
				ggObMIICg6ADAgECAhAtnGBp8aVxrpzhRo5pddgSMA0GCSqGSIb3DQEBBQUAMF0xCzAJBgNVBAYT
				AmNuMRYwFAYDVQQKEw1iYW5rIG9mIGNoaW5hMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdi
				ZWlqaW5nMRIwEAYDVQQDEwlib2NuZXQgY2EwHhcNMTMwNjA2MDkxODQ2WhcNMTgwNjA1MDkxODQ2
				WjCBuTELMAkGA1UEBhMCQ04xFjAUBgNVBAoTDUJBTksgT0YgQ0hJTkExgZEwgY4GA1UEAx6Bhmxf
				gs93AV5/dTVnCX6/T+Fgb39RftyAoU79ZwmWUFFsU/gAXwAzADMAMQBhADkAYwA5ADAAOAAxAGMA
				ZgA0ADUAMwAzAGMANQA3ADgANgAzAGEANwA0ADEAOABlADkANQA3AGMAMABiADEANAAyAGEANwBj
				AF+WSGimWh8AXwAzADIAMgA4MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpzF6lljY9t6PS
				FoHGRuYui7rZhXtqISh2afmIaHgBg2SRgmndPJ3dj8+yUoAvi44RvbZJ7fACJz3U8DzPUL6kIDt9
				ew5LLoWv55JBp217LFnNwPJ0EHJQduPsCFbtFj2nmNUyJRwYM/L9Ea0At3vjaunQ6R0+Txf5ITuX
				zhTXNwIDAQABo34wfDAfBgNVHSMEGDAWgBQmar81qyOZaxvwUDeNenNg2gFSUDAtBgNVHR8EJjAk
				MCKgIKAehhxodHRwOi8vMjYuMTIwLjM2LjMyL2NybDEuY3JsMAsGA1UdDwQEAwIE8DAdBgNVHQ4E
				FgQUXRi254Jv/unha5aBKfEWdvPBGdkwDQYJKoZIhvcNAQEFBQADggEBAAHhqm9JH4Fftijf6M+2
				gopHrEN2JytnrZKi2Ka25kdVRreJw3YIwTbPd84W0r9HoaTYx6VEFkBIAIogdpD3nAYSYHV3a/yD
				ljsd+3rxMSC71C5/A/hFDiteN9w1Qs5jkV1jlLGCdzKo0apGQqIqfismOWc6ZHuokLGYgNxmbUyO
				OBU6gXUvmDz+HDOs2GXX8r2+RYWttfSeP9tTdZBrFyi1cfBpKmc3cDx+z/bDVw6DL6GTs2gXJ4Rg
				IL9jJPk1GW1ZRIa65qxeQB6sIwQz3I3RifWcm3Cxgti4GqszqCddIJajSs9bU0Am07fFaF2HeFkZ
				4QTmISmLARfWIieaVugxggEXMIIBEwIBATBxMF0xCzAJBgNVBAYTAmNuMRYwFAYDVQQKEw1iYW5r
				IG9mIGNoaW5hMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdiZWlqaW5nMRIwEAYDVQQDEwli
				b2NuZXQgY2ECEC2cYGnxpXGunOFGjml12BIwCQYFKw4DAhoFADANBgkqhkiG9w0BAQEFAASBgA9q
				PdVpUnuYTSouuUeKTVoRDbED0NRjfLouU3up5T/XoIQxpPcL0U0rENO8xPdGRNnTaFSYdVPiplzF
				dbQ4/1tz8MPq7FE4UCPsxRdDgo1e/hFmPP5GAqw/x5NWo0MonC27j/DTAp8F2j1GTXxRjEvMavrw
				5IJ9QwZCNUip8ffJ"}
			,"payType":"0"}*/
			final String businessResult = "{\"result\":{\"custTranId\":\""
			+ "2013112900111726"
			+ "\", \"tranType\":\""
			+ "01"
			+ "\", \"merchantNo\":\""
			+ "104320148141015"
			+ "\", \"orderNo\":\""
			+ "2013112900111726"
			+ "\", \"curCode\":\""
			+ "001"
			+ "\",\"orderAmount\":\""
			+ "10"
			+ "\", \"orderTime\":\"20130321132413\", \"orderNote\":\""
			+ "广电项目缴费"
			+ "\", \"orderUrl\":\"http://122.96.58.36:7002/merchant/person/pc/app/toBocMessage?bankDepositId=8662\", " +
			" \"mNormalData\":\"{c=123, d=456}\", " +
			"\"signature\":\"MIIE7gYJKoZIhvcNAQcCoIIE3zCCBNsCAQExCzAJBgUrDgMCGgUAMAsGCSqGSIb3DQEHAaCCA58wggObMIICg6ADAgECAhAtnGBp8aVxrpzhRo5pddgSMA0GCSqGSIb3DQEBBQUAMF" +
			"0xCzAJBgNVBAYTAmNuMRYwFAYDVQQKEw1iYW5rIG9mIGNoaW5hMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdiZWlqaW5nMRIwEAYDVQQDEwlib2NuZXQgY2EwHhcNMTMwNjA2MDkxODQ2WhcNMTgwNj" +
			"A1MDkxODQ2WjCBuTELMAkGA1UEBhMCQ04xFjAUBgNVBAoTDUJBTksgT0YgQ0hJTkExgZEwgY4GA1UEAx6Bhmxfgs93AV5/dTVnCX6/T+Fgb39RftyAoU79ZwmWUFFsU/gAXwAzADMAMQBhADkAYwA5ADAAOAAx" +
			"AGMAZgA0ADUAMwAzAGMANQA3ADgANgAzAGEANwA0ADEAOABlADkANQA3AGMAMABiADEANAAyAGEANwBjAF+WSGimWh8AXwAzADIAMgA4MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpzF6lljY9t6PSFoH" +
			"GRuYui7rZhXtqISh2afmIaHgBg2SRgmndPJ3dj8+yUoAvi44RvbZJ7fACJz3U8DzPUL6kIDt9ew5LLoWv55JBp217LFnNwPJ0EHJQduPsCFbtFj2nmNUyJRwYM/L9Ea0At3vjaunQ6R0+Txf5ITuXzhTXNwIDAQ" +
			"ABo34wfDAfBgNVHSMEGDAWgBQmar81qyOZaxvwUDeNenNg2gFSUDAtBgNVHR8EJjAkMCKgIKAehhxodHRwOi8vMjYuMTIwLjM2LjMyL2NybDEuY3JsMAsGA1UdDwQEAwIE8DAdBgNVHQ4EFgQUXRi254Jv/unha5a" +
			"BKfEWdvPBGdkwDQYJKoZIhvcNAQEFBQADggEBAAHhqm9JH4Fftijf6M+2gopHrEN2JytnrZKi2Ka25kdVRreJw3YIwTbPd84W0r9HoaTYx6VEFkBIAIogdpD3nAYSYHV3a/yDljsd+3rxMSC71C5/A/hFDiteN9w1Q" +
			"s5jkV1jlLGCdzKo0apGQqIqfismOWc6ZHuokLGYgNxmbUyOOBU6gXUvmDz+HDOs2GXX8r2+RYWttfSeP9tTdZBrFyi1cfBpKmc3cDx+z/bDVw6DL6GTs2gXJ4RgIL9jJPk1GW1ZRIa65qxeQB6sIwQz3I3RifWcm3Cxgt" +
			"i4GqszqCddIJajSs9bU0Am07fFaF2HeFkZ4QTmISmLARfWIieaVugxggEXMIIBEwIBATBxMF0xCzAJBgNVBAYTAmNuMRYwFAYDVQQKEw1iYW5rIG9mIGNoaW5hMRAwDgYDVQQIEwdiZWlqaW5nMRAwDgYDVQQHEwdiZWlq" +
			"aW5nMRIwEAYDVQQDEwlib2NuZXQgY2ECEC2cYGnxpXGunOFGjml12BIwCQYFKw4DAhoFADANBgkqhkiG9w0BAQEFAASBgA9qPdVpUnuYTSouuUeKTVoRDbED0NRjfLouU3up5T/XoIQxpPcL0U0rENO8xPdGRNnTaFSYdVPi" +
			"plzFdbQ4/1tz8MPq7FE4UCPsxRdDgo1e/hFmPP5GAqw/x5NWo0MonC27j/DTAp8F2j1GTXxRjEvMavrw5IJ9QwZCNUip8ffJ\"}," +
			"\"payType\"=\"0\"}";
			//调起移动支付
			BOCPAYUtil.bocPay(mIRemoteService, payHandle, businessResult);

		} catch (Exception e) {
			Toast.makeText(this, "未安装应用程序", Toast.LENGTH_LONG).show();
		}
	}
	
	//服务，服务名固定不变
	IRemoteService mIRemoteService;
	//处理支付结果Handler
	private Handler payHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 处理支付结果，刷新页面
			Toast.makeText(MainActivity.this, "支付结果：  " + msg.obj,
					Toast.LENGTH_LONG).show();
		}
	};
	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.d("info", "---- onServiceDisconnected-- ");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mIRemoteService = IRemoteService.Stub.asInterface(service);
		}
	};

	/* (non-Javadoc)
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		unbindService(serviceConnection);
	}
	
	

}
