package com.aait.oms.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.aait.oms.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class Trams_Condition_Activity extends AppCompatActivity {

    HtmlTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trams_condition);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("  Terms & Conditions");

        textView = findViewById(R.id.termsconditions);
        textView.setHtml("<p><big><b><u> Terms & Conditions </u></b></big><br><br>\n" +
                "\n" +
                "By downloading of using the app, these terms will automatically apply to you- you should make sure therefore that you read them carefully before using the app. You are not allowed to copy, or modify the app, any part of the app, and you also should not try to translate the app into other languages, or make derivative versions. The app itself, and all the trade marks, copyright, database rights and other intellectual property rights related to it, still belong to AAIT.<br><br>\n" +
                "AAIT is committed to ensuring that the app is as useful and efficient as possible. For that reason, we reserve the right to make changes to the app or to charge for its services at any time and for any reason. We will never charge you for the or its services without making it very clear to you exactly what you are paying for.<br><br>\n" +
                "The OMS app stores and processes personal data that you have provided to us, in order to provide my service. It is your responsibility to keep your phone and access to the app secure. We therefor recommend that you do not jailbreak or root your phone, which is the process of removing software<br><br>\n" +
                "Restrictions and limitations imposed by the official operating system of your device. It could make your phone vulnerable to malware/viruses/malicious programs, compromise your phone security features and it could mean that the OMS app would not work properly or at all. <br>\n" +
                "The app does use third party services that declare their own terms and conditions.<br><br>\n" +
                "Link to terms and conditions of third-party service providers used by the app.<br><br>\n" +
                "<ul><li>Google play services</li>\n" +
                "<li> Ad mob</li>\n" +
                "<li> Google analytics for firebase</li></ul><br><br>\n" +
                "You should be aware that there are certain things that AAIT will not take responsibility for. Certain functions of the app will require the app to have an active internet connection.  The connection can be wi-fi, or provided by your mobile network provider, but AAIT cannot take responsibility for the app not working at full functionality if you do not have any of your data allowance left.<br><br>\n" +
                "If you are using the app outside of an area with Wi-Fi, you should remember that your terms of the agreement with your mobile network provider will still apply. <br><br>As a result, you may be charged by your mobile provider for the cost of data for the duration of the connection while accessing the app, or other third-party charges, in using the app, you are accepting responsibility for any such charges, including roaming data charges if you use the outside of your home territory (i.e., region or country) without turning off data roaming. <br><br>If you are not the bill payer for the device on which you are using the app, please be aware that we assume that you have received permission from the bill payer for using the app.<br><br>\n" +
                "Along the same lines, AAIT cannot always take responsibility for the way you use the app i.e., you need to make sure that your device stays charge- if it runs out of battery and you can not turn it on to avail the service, AAIT cannot accept responsibility.<br><br>\n" +
                "With respect to AAIT responsibility for your use of the app, when you are using the app, it is important to bear in mind that although we endeavor to ensure that it is updated and correct at all times, we do rely on third parties to provide information to us so that we can make it available to you. AAIT accepts no liability for any loss, direct or indirect, you experience as a result of relying wholly on this functionality of the app.<br><br>\n" +
                "At some point, we may wish to update the app. The app is currently available on android – the requirements for system (and for any additional systems we decide to extend the availability of the app to) may change, and you will need to download the updates if you want to keep using the app. <br><br>AAIT does not promise that it will always update the app so that it is relevant to you and/or works with the android version that you have installed on your device.<br><br>However, you promise to always accept updates to the application when offered to you, we may also wish to stop providing the app and may terminate use of it at any time without giving notice of termination to you. Unless we tell you otherwise, upon any termination, <br><br>(a) the rights and licenses granted to you in these terms will end; <br>(b) you must stop using the app and (if needed) delete if from your device. <br><br>\n" +
                "<big><b><u> Changes to this terms and conditions. </u></b></big><br><br>\n" +
                "\n" +
                "I may update our terms and conditions from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new terms and conditions on this page.<br>\n" +
                "These terms and conditions are effective as of 2021-07-01.<br><br>\n" +
                "<big><b><u> Contact us </u></b></big><br><br>\n" +
                "\n" +
                "If you have any questions or suggestions about my terms and conditions, do not hesitate to contact me at giyasuddin636@gamil.com.</p>\n");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}