package com.aait.oms.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.aait.oms.R;
import org.sufficientlysecure.htmltextview.HtmlTextView;


public class Privacy_PolicyActivity extends AppCompatActivity {

    HtmlTextView taxtview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar .setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setIcon(R.drawable.logopng40);
        actionBar.setTitle("  Privacy & Policy");


        taxtview = findViewById(R.id.privacyid);

        taxtview.setHtml("<p><big><b><u>Privacy Policy</u></b></big><br><br>\n" +
                "AAIT-built OMS (Order Management System) app as a premium app. This service is provided by AAIT at no cost and is intended for use as is .<br>\n" +
                "This page used to inform visitors regarding my policies with the collection use, and disclosure of personal information if anyone decided to use my service. <br>\n" +
                "If you choose to use my service then you agree to this policy. The personal information with anyone accepts as described in this privacy policy. <br>\n" +
                "The terms used in this privacy have the same meanings as in our terms and conditions, which is accessible at OMS unless otherwise defined in this privacy policy. <br><br>\n" +
                "<big><b><u> Information collection and use </u></b></big><br><br>\n" +
                "For a better experience, while using our service, I may require you to provide us with certain personally identifiable information. The information that I request will be retained on your device and is not collected by me in any way. The app does use third party services that may collect information used to identify you. <br>\n" +
                "Link to privacy policy of Third-party service providers used by the app.<br><br>\n" +
                "<ul><li>Google play services</li>\n" +
                "<li> Ad mob</li>\n" +
                "<li> Google analytics for firebase</li></ul><br>\n" +
                "<big><b><u> Log data </u></b></big><br><br>\n" +
                "\n" +
                "I want to inform you that whenever you use my service, if a case of an error in the app I collect data and information (through third party products) on your phone called log data. This log data may include information such as your device internet protocol(IP) address, device name operating system version, the configuration of the app when utilizing my service, the time and data of your use of the service and other statistic.<br><br>\n" +
                "<big><b><u> Cookies </u></b></big><br><br>\n" +
                "Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device’s internal memory. <br>\n" +
                "This service does not use these cookies explicitly. However, the app may use third party code and libraries that use cookies to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this service.<br><br>\n" +
                "<big><b><u> Service Providers </u></b></big><br><br>\n" +
                "I may apply third-party companies and individuals due to the following reasons:<br><br>\n" +
                "<ul><li>To facilitate our service;</li>\n" +
                "<li>To provide the service on our behalf;</li>\n" +
                "<li>To perform service-related services; or</li> \n" +
                "<li>To assist us in analyzing how our service is used;</li></ul><br>\n" +
                "I want to inform users of this service that these third parties have access to your personal information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose. <br><br>\n" +
                "<big><b><u> Security</u></b></big><br><br>\n" +
                "I value your trust in providing us your personal information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and I cannot guarantee its absolute security.<br><br>\n" +
                "<big><b><u> Links to other Sites</u></b></big><br><br>\n" +
                "This service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated my me. Therefore, I strongly advise you to review the privacy policy of these websites. I have no control over and assume no responsibility for the content, privacy policies, of practices of any third-party sites or services.<br><br>\n" +
                "<big><b><u> Childrens Privacy </u></b></big><br><br>\n" +
                "\n" +
                "These services do not address anyone under the age of 13. I do not knowingly collect personally identifiable information from children under 13 years of age. In the case I discover that a child under 13 has provided me with personal information, I immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact me so that I will be able to do necessary actions.<br><br>\n" +
                "<big><b><u> Changes to this Privacy Policy </u></b></big><br><br>\n" +
                "\n" +
                "I may update our privacy policy from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new privacy policy on this page.<br>\n" +
                "This policy is effective as of 21-07-01.<br><br>\n" +

                "<big><b><u> Contact Us </u></b></big><br><br>\n" +

                "If you have any questions of suggestions about my privacy policy, do not hesitate to contact me at giyasuddin636@gmail.com. </p>\n");
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