package ir.zibal.mpos.sample.zibalmposconnection;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText id;
    TextView receivedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = findViewById(R.id.zibalId);
        receivedData = findViewById(R.id.returned_data);
    }

    private boolean isPackageInstalled(String packagename) {
        try {
            getPackageManager().getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void onPay(View view){
        if (isPackageInstalled("mpos.zibal.com.zibal")){
            Intent payByZibal = new Intent("com.zibal.mpos.zibalpay");
            payByZibal.putExtra("zibalId", id.getText().toString());
            startActivityForResult(payByZibal, 951);
        }
        else
            Toast.makeText(this, "لطفا زیبال را نصب و فعال کنید.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            String info = "zibalId: " + data.getStringExtra("zibalId") + "\n" +
                    "refNumber:"  + data.getStringExtra("refNumber") + "\n" +
                    "amount:"  + data.getStringExtra("amount") + "\n" +
                    "success:"  + data.getBooleanExtra("success", false);
            receivedData.setText(info);
        }
        else if (resultCode == RESULT_CANCELED){
            receivedData.setText(data.getStringExtra("message"));
            //خطای دریافت شناسه...
            //عدم دسترسی به اینترنت...
            //عدم دسترسی به سرور زیبال
            //شناسه زیبال نامعتبر است.
            //شناسه قبلا پرداخت شده.
        }

    }

}
