package de.teamgamma.cansat.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import de.teamgamma.cansat.app.R;
import de.teamgamma.cansat.app.webView.TeamGammaWebVieClient;

/**
 * 
 * 
 * @author Alexander Brennecke
 *
 *shows the home screen when an object is initialized
 *initialized when app opened
 *
 */

public class HomeFragment extends Fragment {
    public static final String ARG_SLIDEMENU_VALUES = "slidemenu_values";

    public HomeFragment() {
        // Empty constructor
    }

    /**
     * 
     * called when an object of this class was created
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
     	final LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_home,
                 container, false);
     	WebView webview = (WebView) mLinearLayout.findViewById(R.id.webView1);     	
     	webview.setWebViewClient(new TeamGammaWebVieClient());
        webview.getSettings().setBuiltInZoomControls(false); 
        webview.getSettings().setSupportZoom(false);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);   
        webview.getSettings().setAllowFileAccess(true); 
        webview.getSettings().setDomStorageEnabled(true);
        webview.loadUrl("http://www.team-gamma.de");  
    return mLinearLayout;
}


}
