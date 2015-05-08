/*
 *************************************************************************
Copyright (c) 2011-2013:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy

See http://www.infn.it and and http://www.consorzio-cometa.it for details on
the copyright holders.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author <a href="mailto:giuseppe.larocca@ct.infn.it">Giuseppe La Rocca</a>
 ***************************************************************************
 */
package it.infn.ct.astra;

// import liferay libraries
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

// import DataEngine libraries
import com.liferay.portal.util.PortalUtil;
import it.infn.ct.GridEngine.InformationSystem.BDII;
import it.infn.ct.GridEngine.Job.*;

// import generic Java libraries
import it.infn.ct.GridEngine.UsersTracking.UsersTrackingDBInterface;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URI;

// import portlet libraries
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

// Importing Apache libraries
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author larocca
 */
public class Astra extends GenericPortlet {

    private static Log log = LogFactory.getLog(Astra.class);

    @Override
    protected void doEdit(RenderRequest request,
            RenderResponse response)
            throws PortletException, IOException
    {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");
        
        // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the COMETA VO
        String cometa_astra_INFRASTRUCTURE = portletPreferences.getValue("cometa_astra_INFRASTRUCTURE", "N/A");
        // Get the ASTRA VONAME from the portlet preferences for the COMETA VO
        String cometa_astra_VONAME = portletPreferences.getValue("cometa_astra_VONAME", "N/A");
        // Get the ASTRA TOPPBDII from the portlet preferences for the COMETA VO
        String cometa_astra_TOPBDII = portletPreferences.getValue("cometa_astra_TOPBDII", "N/A");
        // Get the ASTRA WMS from the portlet preferences for the COMETA VO
        String[] cometa_astra_WMS = portletPreferences.getValues("cometa_astra_WMS", new String[5]);
        // Get the ASTRA ETOKENSERVER from the portlet preferences for the COMETA VO
        String cometa_astra_ETOKENSERVER = portletPreferences.getValue("cometa_astra_ETOKENSERVER", "N/A");
        // Get the ASTRA MYPROXYSERVER from the portlet preferences for the COMETA VO
        String cometa_astra_MYPROXYSERVER = portletPreferences.getValue("cometa_astra_MYPROXYSERVER", "N/A");
        // Get the ASTRA PORT from the portlet preferences for the COMETA VO
        String cometa_astra_PORT = portletPreferences.getValue("cometa_astra_PORT", "N/A");
        // Get the ASTRA ROBOTID from the portlet preferences for the COMETA VO
        String cometa_astra_ROBOTID = portletPreferences.getValue("cometa_astra_ROBOTID", "N/A");
        // Get the ASTRA ROLE from the portlet preferences for the COMETA VO
        String cometa_astra_ROLE = portletPreferences.getValue("cometa_astra_ROLE", "N/A");
        // Get the ASTRA RENEWAL from the portlet preferences for the COMETA VO
        String cometa_astra_RENEWAL = portletPreferences.getValue("cometa_astra_RENEWAL", "checked");
        // Get the ASTRA DISABLEVOMS from the portlet preferences for the COMETA VO
        String cometa_astra_DISABLEVOMS = portletPreferences.getValue("cometa_astra_DISABLEVOMS", "unchecked");
        
        // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the GARUDA VO
        String garuda_astra_INFRASTRUCTURE = portletPreferences.getValue("garuda_astra_INFRASTRUCTURE", "N/A");
        // Get the ASTRA VONAME from the portlet preferences for the GARUDA VO
        String garuda_astra_VONAME = portletPreferences.getValue("garuda_astra_VONAME", "N/A");
        // Get the ASTRA TOPPBDII from the portlet preferences for the GARUDA VO
        String garuda_astra_TOPBDII = portletPreferences.getValue("garuda_astra_TOPBDII", "N/A");
        // Get the ASTRA WMS from the portlet preferences for the GARUDA VO
        String[] garuda_astra_WMS = portletPreferences.getValues("garuda_astra_WMS", new String[5]);
        // Get the ASTRA ETOKENSERVER from the portlet preferences for the GARUDA VO
        String garuda_astra_ETOKENSERVER = portletPreferences.getValue("garuda_astra_ETOKENSERVER", "N/A");
        // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GARUDA VO
        String garuda_astra_MYPROXYSERVER = portletPreferences.getValue("garuda_astra_MYPROXYSERVER", "N/A");
        // Get the ASTRA PORT from the portlet preferences for the GARUDA VO
        String garuda_astra_PORT = portletPreferences.getValue("garuda_astra_PORT", "N/A");
        // Get the ASTRA ROBOTID from the portlet preferences for the GARUDA VO
        String garuda_astra_ROBOTID = portletPreferences.getValue("garuda_astra_ROBOTID", "N/A");
        // Get the ASTRA ROLE from the portlet preferences for the GARUDA VO
        String garuda_astra_ROLE = portletPreferences.getValue("garuda_astra_ROLE", "N/A");
        // Get the ASTRA RENEWAL from the portlet preferences for the GARUDA VO
        String garuda_astra_RENEWAL = portletPreferences.getValue("garuda_astra_RENEWAL", "checked");
        // Get the ASTRA DISABLEVOMS from the portlet preferences for the GARUDA VO
        String garuda_astra_DISABLEVOMS = portletPreferences.getValue("garuda_astra_DISABLEVOMS", "unchecked");

        // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the GRIDIT VO
        String gridit_astra_INFRASTRUCTURE = portletPreferences.getValue("gridit_astra_INFRASTRUCTURE", "N/A");
        // Get the ASTRA VONAME from the portlet preferences for the GRIDIT VO
        String gridit_astra_VONAME = portletPreferences.getValue("gridit_astra_VONAME", "N/A");
        // Get the ASTRA TOPPBDII from the portlet preferences for the GRIDIT VO
        String gridit_astra_TOPBDII = portletPreferences.getValue("gridit_astra_TOPBDII", "N/A");
        // Get the ASTRA WMS from the portlet preferences for the GRIDIT VO
        String[] gridit_astra_WMS = portletPreferences.getValues("gridit_astra_WMS", new String[5]);
        // Get the ASTRA ETOKENSERVER from the portlet preferences for the GRIDIT VO
        String gridit_astra_ETOKENSERVER = portletPreferences.getValue("gridit_astra_ETOKENSERVER", "N/A");
        // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GRIDIT VO
        String gridit_astra_MYPROXYSERVER = portletPreferences.getValue("gridit_astra_MYPROXYSERVER", "N/A");
        // Get the ASTRA PORT from the portlet preferences for the GRIDIT VO
        String gridit_astra_PORT = portletPreferences.getValue("gridit_astra_PORT", "N/A");
        // Get the ASTRA ROBOTID from the portlet preferences for the GRIDIT VO
        String gridit_astra_ROBOTID = portletPreferences.getValue("gridit_astra_ROBOTID", "N/A");
        // Get the ASTRA ROLE from the portlet preferences for the GRIDIT VO
        String gridit_astra_ROLE = portletPreferences.getValue("gridit_astra_ROLE", "N/A");
        // Get the ASTRA RENEWAL from the portlet preferences for the GRIDIT VO
        String gridit_astra_RENEWAL = portletPreferences.getValue("gridit_astra_RENEWAL", "checked");
        // Get the ASTRA DISABLEVOMS from the portlet preferences for the GRIDIT VO
        String gridit_astra_DISABLEVOMS = portletPreferences.getValue("gridit_astra_DISABLEVOMS", "unchecked");
        
        // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the GILDA VO
        String gilda_astra_INFRASTRUCTURE = portletPreferences.getValue("gilda_astra_INFRASTRUCTURE", "N/A");
        // Get the ASTRA VONAME from the portlet preferences for the GILDA VO
        String gilda_astra_VONAME = portletPreferences.getValue("gilda_astra_VONAME", "N/A");
        // Get the ASTRA TOPPBDII from the portlet preferences for the GILDA VO
        String gilda_astra_TOPBDII = portletPreferences.getValue("gilda_astra_TOPBDII", "N/A");
        // Get the ASTRA WMS from the portlet preferences for the GILDA VO
        String[] gilda_astra_WMS = portletPreferences.getValues("gilda_astra_WMS", new String[5]);
        // Get the ASTRA ETOKENSERVER from the portlet preferences for the GILDA VO
        String gilda_astra_ETOKENSERVER = portletPreferences.getValue("gilda_astra_ETOKENSERVER", "N/A");
        // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GILDA VO
        String gilda_astra_MYPROXYSERVER = portletPreferences.getValue("gilda_astra_MYPROXYSERVER", "N/A");
        // Get the ASTRA PORT from the portlet preferences for the GILDA VO
        String gilda_astra_PORT = portletPreferences.getValue("gilda_astra_PORT", "N/A");
        // Get the ASTRA ROBOTID from the portlet preferences for the GILDA VO
        String gilda_astra_ROBOTID = portletPreferences.getValue("gilda_astra_ROBOTID", "N/A");
        // Get the ASTRA ROLE from the portlet preferences for the GILDA VO
        String gilda_astra_ROLE = portletPreferences.getValue("gilda_astra_ROLE", "N/A");
        // Get the ASTRA RENEWAL from the portlet preferences for the GILDA VO
        String gilda_astra_RENEWAL = portletPreferences.getValue("gilda_astra_RENEWAL", "checked");
        // Get the ASTRA DISABLEVOMS from the portlet preferences for the GILDA VO
        String gilda_astra_DISABLEVOMS = portletPreferences.getValue("gilda_astra_DISABLEVOMS", "unchecked");

        // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the EUMED VO
        String eumed_astra_INFRASTRUCTURE = portletPreferences.getValue("eumed_astra_INFRASTRUCTURE", "N/A");
        // Get the ASTRA VONAME from the portlet preferences for the EUMED VO
        String eumed_astra_VONAME = portletPreferences.getValue("eumed_astra_VONAME", "N/A");
        // Get the ASTRA TOPPBDII from the portlet preferences for the EUMED VO
        String eumed_astra_TOPBDII = portletPreferences.getValue("eumed_astra_TOPBDII", "N/A");
        // Get the ASTRA WMS from the portlet preferences for the EUMED VO
        String[] eumed_astra_WMS = portletPreferences.getValues("eumed_astra_WMS", new String[5]);
        // Get the ASTRA ETOKENSERVER from the portlet preferences for the EUMED VO
        String eumed_astra_ETOKENSERVER = portletPreferences.getValue("eumed_astra_ETOKENSERVER", "N/A");
        // Get the ASTRA MYPROXYSERVER from the portlet preferences for the EUMED VO
        String eumed_astra_MYPROXYSERVER = portletPreferences.getValue("eumed_astra_MYPROXYSERVER", "N/A");
        // Get the ASTRA PORT from the portlet preferences for the EUMED VO
        String eumed_astra_PORT = portletPreferences.getValue("eumed_astra_PORT", "N/A");
        // Get the ASTRA ROBOTID from the portlet preferences for the EUMED VO
        String eumed_astra_ROBOTID = portletPreferences.getValue("eumed_astra_ROBOTID", "N/A");
        // Get the ASTRA ROLE from the portlet preferences for the EUMED VO
        String eumed_astra_ROLE = portletPreferences.getValue("eumed_astra_ROLE", "N/A");
        // Get the ASTRA RENEWAL from the portlet preferences for the EUMED VO
        String eumed_astra_RENEWAL = portletPreferences.getValue("eumed_astra_RENEWAL", "checked");
        // Get the ASTRA DISABLEVOMS from the portlet preferences for the EUMED VO
        String eumed_astra_DISABLEVOMS = portletPreferences.getValue("eumed_astra_DISABLEVOMS", "unchecked");

        // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the GISELA VO
        String gisela_astra_INFRASTRUCTURE = portletPreferences.getValue("gisela_astra_INFRASTRUCTURE", "N/A");
        // Get the ASTRA VONAME from the portlet preferences for the GISELA VO
        String gisela_astra_VONAME = portletPreferences.getValue("gisela_astra_VONAME", "N/A");
        // Get the ASTRA TOPPBDII from the portlet preferences for the GISELA VO
        String gisela_astra_TOPBDII = portletPreferences.getValue("gisela_astra_TOPBDII", "N/A");
        // Get the ASTRA WMS from the portlet preferences for the GISELA VO
        String[] gisela_astra_WMS = portletPreferences.getValues("gisela_astra_WMS", new String[5]);
        // Get the ASTRA ETOKENSERVER from the portlet preferences for the GISELA VO
        String gisela_astra_ETOKENSERVER = portletPreferences.getValue("gisela_astra_ETOKENSERVER", "N/A");
        // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GISELA VO
        String gisela_astra_MYPROXYSERVER = portletPreferences.getValue("gisela_astra_MYPROXYSERVER", "N/A");
        // Get the ASTRA PORT from the portlet preferences for the GISELA VO
        String gisela_astra_PORT = portletPreferences.getValue("gisela_astra_PORT", "N/A");
        // Get the ASTRA ROBOTID from the portlet preferences for the GISELA VO
        String gisela_astra_ROBOTID = portletPreferences.getValue("gisela_astra_ROBOTID", "N/A");
        // Get the ASTRA ROLE from the portlet preferences for the GISELA VO
        String gisela_astra_ROLE = portletPreferences.getValue("gisela_astra_ROLE", "N/A");
        // Get the ASTRA RENEWAL from the portlet preferences for the GISELA VO
        String gisela_astra_RENEWAL = portletPreferences.getValue("gisela_astra_RENEWAL", "checked");
        // Get the ASTRA DISABLEVOMS from the portlet preferences for the GISELA VO
        String gisela_astra_DISABLEVOMS = portletPreferences.getValue("gisela_astra_DISABLEVOMS", "unchecked");
        
        // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the SAGRID VO
        String sagrid_astra_INFRASTRUCTURE = portletPreferences.getValue("sagrid_astra_INFRASTRUCTURE", "N/A");
        // Get the ASTRA VONAME from the portlet preferences for the SAGRID VO
        String sagrid_astra_VONAME = portletPreferences.getValue("sagrid_astra_VONAME", "N/A");
        // Get the ASTRA TOPPBDII from the portlet preferences for the SAGRID VO
        String sagrid_astra_TOPBDII = portletPreferences.getValue("sagrid_astra_TOPBDII", "N/A");
        // Get the ASTRA WMS from the portlet preferences for the SAGRID VO
        String[] sagrid_astra_WMS = portletPreferences.getValues("sagrid_astra_WMS", new String[5]);
        // Get the ASTRA ETOKENSERVER from the portlet preferences for the SAGRID VO
        String sagrid_astra_ETOKENSERVER = portletPreferences.getValue("sagrid_astra_ETOKENSERVER", "N/A");
        // Get the ASTRA MYPROXYSERVER from the portlet preferences for the SAGRID VO
        String sagrid_astra_MYPROXYSERVER = portletPreferences.getValue("sagrid_astra_MYPROXYSERVER", "N/A");
        // Get the ASTRA PORT from the portlet preferences for the SAGRID VO
        String sagrid_astra_PORT = portletPreferences.getValue("sagrid_astra_PORT", "N/A");
        // Get the ASTRA ROBOTID from the portlet preferences for the SAGRID VO
        String sagrid_astra_ROBOTID = portletPreferences.getValue("sagrid_astra_ROBOTID", "N/A");
        // Get the ASTRA ROLE from the portlet preferences for the SAGRID VO
        String sagrid_astra_ROLE = portletPreferences.getValue("sagrid_astra_ROLE", "N/A");
        // Get the ASTRA RENEWAL from the portlet preferences for the SAGRID VO
        String sagrid_astra_RENEWAL = portletPreferences.getValue("sagrid_astra_RENEWAL", "checked");
        // Get the ASTRA DISABLEVOMS from the portlet preferences for the SAGRID VO
        String sagrid_astra_DISABLEVOMS = portletPreferences.getValue("sagrid_astra_DISABLEVOMS", "unchecked");

        // Get the ASTRA APPID from the portlet preferences
        String astra_APPID = portletPreferences.getValue("astra_APPID", "N/A");
        // Get the ASTRA OUTPUT from the portlet preferences
        String astra_OUTPUT_PATH = portletPreferences.getValue("astra_OUTPUT_PATH", "/tmp");
        // Get the ASTRA SFOTWARE from the portlet preferences
        String astra_SOFTWARE = portletPreferences.getValue("astra_SOFTWARE", "N/A");
        // Get the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Get the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Get the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Get the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Get the SENDER MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        // Get the list of enabled Infrastructures
        String[] infras = portletPreferences.getValues("astra_ENABLEINFRASTRUCTURE", new String[6]);

        // Set the default portlet preferences
        request.setAttribute("cometa_astra_INFRASTRUCTURE", cometa_astra_INFRASTRUCTURE.trim());
        request.setAttribute("cometa_astra_VONAME", cometa_astra_VONAME.trim());
        request.setAttribute("cometa_astra_TOPBDII", cometa_astra_TOPBDII.trim());
        request.setAttribute("cometa_astra_WMS",cometa_astra_WMS);
        request.setAttribute("cometa_astra_ETOKENSERVER", cometa_astra_ETOKENSERVER.trim());
        request.setAttribute("cometa_astra_MYPROXYSERVER", cometa_astra_MYPROXYSERVER.trim());
        request.setAttribute("cometa_astra_PORT", cometa_astra_PORT.trim());
        request.setAttribute("cometa_astra_ROBOTID", cometa_astra_ROBOTID.trim());
        request.setAttribute("cometa_astra_ROLE", cometa_astra_ROLE.trim());
        request.setAttribute("cometa_astra_RENEWAL", cometa_astra_RENEWAL);
        request.setAttribute("cometa_astra_DISABLEVOMS", cometa_astra_DISABLEVOMS);
        
        request.setAttribute("garuda_astra_INFRASTRUCTURE", garuda_astra_INFRASTRUCTURE.trim());
        request.setAttribute("garuda_astra_VONAME", garuda_astra_VONAME.trim());
        request.setAttribute("garuda_astra_TOPBDII", garuda_astra_TOPBDII.trim());
        request.setAttribute("garuda_astra_WMS",garuda_astra_WMS);
        request.setAttribute("garuda_astra_ETOKENSERVER", garuda_astra_ETOKENSERVER.trim());
        request.setAttribute("garuda_astra_MYPROXYSERVER", garuda_astra_MYPROXYSERVER.trim());
        request.setAttribute("garuda_astra_PORT", garuda_astra_PORT.trim());
        request.setAttribute("garuda_astra_ROBOTID", garuda_astra_ROBOTID.trim());
        request.setAttribute("garuda_astra_ROLE", garuda_astra_ROLE.trim());
        request.setAttribute("garuda_astra_RENEWAL", garuda_astra_RENEWAL);
        request.setAttribute("garuda_astra_DISABLEVOMS", garuda_astra_DISABLEVOMS);
        
        request.setAttribute("gridit_astra_INFRASTRUCTURE", gridit_astra_INFRASTRUCTURE.trim());
        request.setAttribute("gridit_astra_VONAME", gridit_astra_VONAME.trim());
        request.setAttribute("gridit_astra_TOPBDII", gridit_astra_TOPBDII.trim());
        request.setAttribute("gridit_astra_WMS",gridit_astra_WMS);
        request.setAttribute("gridit_astra_ETOKENSERVER", gridit_astra_ETOKENSERVER.trim());
        request.setAttribute("gridit_astra_MYPROXYSERVER", gridit_astra_MYPROXYSERVER.trim());
        request.setAttribute("gridit_astra_PORT", gridit_astra_PORT.trim());
        request.setAttribute("gridit_astra_ROBOTID", gridit_astra_ROBOTID.trim());
        request.setAttribute("gridit_astra_ROLE", gridit_astra_ROLE.trim());
        request.setAttribute("gridit_astra_RENEWAL", gridit_astra_RENEWAL);
        request.setAttribute("gridit_astra_DISABLEVOMS", gridit_astra_DISABLEVOMS);
        
        request.setAttribute("gilda_astra_INFRASTRUCTURE", gilda_astra_INFRASTRUCTURE.trim());
        request.setAttribute("gilda_astra_VONAME", gilda_astra_VONAME.trim());
        request.setAttribute("gilda_astra_TOPBDII", gilda_astra_TOPBDII.trim());
        request.setAttribute("gilda_astra_WMS", gilda_astra_WMS);
        request.setAttribute("gilda_astra_ETOKENSERVER", gilda_astra_ETOKENSERVER.trim());
        request.setAttribute("gilda_astra_MYPROXYSERVER", gilda_astra_MYPROXYSERVER.trim());
        request.setAttribute("gilda_astra_PORT", gilda_astra_PORT.trim());
        request.setAttribute("gilda_astra_ROBOTID", gilda_astra_ROBOTID.trim());
        request.setAttribute("gilda_astra_ROLE", gilda_astra_ROLE.trim());
        request.setAttribute("gilda_astra_RENEWAL", gilda_astra_RENEWAL);
        request.setAttribute("gilda_astra_DISABLEVOMS", gilda_astra_DISABLEVOMS);

        request.setAttribute("eumed_astra_INFRASTRUCTURE", eumed_astra_INFRASTRUCTURE.trim());
        request.setAttribute("eumed_astra_VONAME", eumed_astra_VONAME.trim());
        request.setAttribute("eumed_astra_TOPBDII", eumed_astra_TOPBDII.trim());
        request.setAttribute("eumed_astra_WMS", eumed_astra_WMS);
        request.setAttribute("eumed_astra_ETOKENSERVER", eumed_astra_ETOKENSERVER.trim());
        request.setAttribute("eumed_astra_MYPROXYSERVER", eumed_astra_MYPROXYSERVER.trim());
        request.setAttribute("eumed_astra_PORT", eumed_astra_PORT.trim());
        request.setAttribute("eumed_astra_ROBOTID", eumed_astra_ROBOTID.trim());
        request.setAttribute("eumed_astra_ROLE", eumed_astra_ROLE.trim());
        request.setAttribute("eumed_astra_RENEWAL", eumed_astra_RENEWAL);
        request.setAttribute("eumed_astra_DISABLEVOMS", eumed_astra_DISABLEVOMS);

        request.setAttribute("gisela_astra_INFRASTRUCTURE", gisela_astra_INFRASTRUCTURE.trim());
        request.setAttribute("gisela_astra_VONAME", gisela_astra_VONAME.trim());
        request.setAttribute("gisela_astra_TOPBDII", gisela_astra_TOPBDII.trim());
        request.setAttribute("gisela_astra_WMS", gisela_astra_WMS);
        request.setAttribute("gisela_astra_ETOKENSERVER", gisela_astra_ETOKENSERVER.trim());
        request.setAttribute("gisela_astra_MYPROXYSERVER", gisela_astra_MYPROXYSERVER.trim());
        request.setAttribute("gisela_astra_PORT", gisela_astra_PORT.trim());
        request.setAttribute("gisela_astra_ROBOTID", gisela_astra_ROBOTID.trim());
        request.setAttribute("gisela_astra_ROLE", gisela_astra_ROLE.trim());
        request.setAttribute("gisela_astra_RENEWAL", gisela_astra_RENEWAL);
        request.setAttribute("gisela_astra_DISABLEVOMS", gisela_astra_DISABLEVOMS);
        
        request.setAttribute("sagrid_astra_INFRASTRUCTURE", sagrid_astra_INFRASTRUCTURE.trim());
        request.setAttribute("sagrid_astra_VONAME", sagrid_astra_VONAME.trim());
        request.setAttribute("sagrid_astra_TOPBDII", sagrid_astra_TOPBDII.trim());
        request.setAttribute("sagrid_astra_WMS", sagrid_astra_WMS);
        request.setAttribute("sagrid_astra_ETOKENSERVER", sagrid_astra_ETOKENSERVER.trim());
        request.setAttribute("sagrid_astra_MYPROXYSERVER", sagrid_astra_MYPROXYSERVER.trim());
        request.setAttribute("sagrid_astra_PORT", sagrid_astra_PORT.trim());
        request.setAttribute("sagrid_astra_ROBOTID", sagrid_astra_ROBOTID.trim());
        request.setAttribute("sagrid_astra_ROLE", sagrid_astra_ROLE.trim());
        request.setAttribute("sagrid_astra_RENEWAL", sagrid_astra_RENEWAL);
        request.setAttribute("sagrid_astra_DISABLEVOMS", sagrid_astra_DISABLEVOMS);

        request.setAttribute("astra_ENABLEINFRASTRUCTURE", infras);
        request.setAttribute("astra_APPID", astra_APPID.trim());
        request.setAttribute("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
        request.setAttribute("astra_SOFTWARE", astra_SOFTWARE.trim());
        request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
        request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
        request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
        request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
        request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());        

        log.info("\nStarting the EDIT mode...with this settings"
        + "\n\ncometa_astra_INFRASTRUCTURE: " + cometa_astra_INFRASTRUCTURE
        + "\ncometa_astra_VONAME: " + cometa_astra_VONAME
        + "\ncometa_astra_TOPBDII: " + cometa_astra_TOPBDII                    
        + "\ncometa_astra_ETOKENSERVER: " + cometa_astra_ETOKENSERVER
        + "\ncometa_astra_MYPROXYSERVER: " + cometa_astra_MYPROXYSERVER
        + "\ncometa_astra_PORT: " + cometa_astra_PORT
        + "\ncometa_astra_ROBOTID: " + cometa_astra_ROBOTID
        + "\ncometa_astra_ROLE: " + cometa_astra_ROLE
        + "\ncometa_astra_RENEWAL: " + cometa_astra_RENEWAL
        + "\ncometa_astra_DISABLEVOMS: " + cometa_astra_DISABLEVOMS
                
        + "\n\ngaruda_astra_INFRASTRUCTURE: " + garuda_astra_INFRASTRUCTURE
        + "\ngaruda_astra_VONAME: " + garuda_astra_VONAME
        + "\ngaruda_astra_TOPBDII: " + garuda_astra_TOPBDII                    
        + "\ngaruda_astra_ETOKENSERVER: " + garuda_astra_ETOKENSERVER
        + "\ngaruda_astra_MYPROXYSERVER: " + garuda_astra_MYPROXYSERVER
        + "\ngaruda_astra_PORT: " + garuda_astra_PORT
        + "\ngaruda_astra_ROBOTID: " + garuda_astra_ROBOTID
        + "\ngaruda_astra_ROLE: " + garuda_astra_ROLE
        + "\ngaruda_astra_RENEWAL: " + garuda_astra_RENEWAL
        + "\ngaruda_astra_DISABLEVOMS: " + garuda_astra_DISABLEVOMS                
                
        + "\n\ngridit_astra_INFRASTRUCTURE: " + gridit_astra_INFRASTRUCTURE
        + "\ngridit_astra_VONAME: " + gridit_astra_VONAME
        + "\ngridit_astra_TOPBDII: " + gridit_astra_TOPBDII                    
        + "\ngridit_astra_ETOKENSERVER: " + gridit_astra_ETOKENSERVER
        + "\ngridit_astra_MYPROXYSERVER: " + gridit_astra_MYPROXYSERVER
        + "\ngridit_astra_PORT: " + gridit_astra_PORT
        + "\ngridit_astra_ROBOTID: " + gridit_astra_ROBOTID
        + "\ngridit_astra_ROLE: " + gridit_astra_ROLE
        + "\ngridit_astra_RENEWAL: " + gridit_astra_RENEWAL
        + "\ngridit_astra_DISABLEVOMS: " + gridit_astra_DISABLEVOMS
                
        + "\n\ngilda_astra_INFRASTRUCTURE: " + gilda_astra_INFRASTRUCTURE
        + "\ngilda_astra_VONAME: " + gilda_astra_VONAME
        + "\ngilda_astra_TOPBDII: " + gilda_astra_TOPBDII                    
        + "\ngilda_astra_ETOKENSERVER: " + gilda_astra_ETOKENSERVER
        + "\ngilda_astra_MYPROXYSERVER: " + gilda_astra_MYPROXYSERVER
        + "\ngilda_astra_PORT: " + gilda_astra_PORT
        + "\ngilda_astra_ROBOTID: " + gilda_astra_ROBOTID
        + "\ngilda_astra_ROLE: " + gilda_astra_ROLE
        + "\ngilda_astra_RENEWAL: " + gilda_astra_RENEWAL
        + "\ngilda_astra_DISABLEVOMS: " + gilda_astra_DISABLEVOMS

        + "\n\neumed_astra_INFRASTRUCTURE: " + eumed_astra_INFRASTRUCTURE
        + "\neumed_astra_VONAME: " + eumed_astra_VONAME
        + "\neumed_astra_TOPBDII: " + eumed_astra_TOPBDII                    
        + "\neumed_astra_ETOKENSERVER: " + eumed_astra_ETOKENSERVER
        + "\neumed_astra_MYPROXYSERVER: " + eumed_astra_MYPROXYSERVER
        + "\neumed_astra_PORT: " + eumed_astra_PORT
        + "\neumed_astra_ROBOTID: " + eumed_astra_ROBOTID
        + "\neumed_astra_ROLE: " + eumed_astra_ROLE
        + "\neumed_astra_RENEWAL: " + eumed_astra_RENEWAL
        + "\neumed_astra_DISABLEVOMS: " + eumed_astra_DISABLEVOMS

        + "\n\ngisela_astra_INFRASTRUCTURE: " + gisela_astra_INFRASTRUCTURE
        + "\ngisela_astra_VONAME: " + gisela_astra_VONAME
        + "\ngisela_astra_TOPBDII: " + gisela_astra_TOPBDII                    
        + "\ngisela_astra_ETOKENSERVER: " + gisela_astra_ETOKENSERVER
        + "\ngisela_astra_MYPROXYSERVER: " + gisela_astra_MYPROXYSERVER
        + "\ngisela_astra_PORT: " + gisela_astra_PORT
        + "\ngisela_astra_ROBOTID: " + gisela_astra_ROBOTID
        + "\ngisela_astra_ROLE: " + gisela_astra_ROLE
        + "\ngisela_astra_RENEWAL: " + gisela_astra_RENEWAL
        + "\ngisela_astra_DISABLEVOMS: " + gisela_astra_DISABLEVOMS
                
        + "\n\nsagrid_astra_INFRASTRUCTURE: " + sagrid_astra_INFRASTRUCTURE
        + "\nsagrid_astra_VONAME: " + sagrid_astra_VONAME
        + "\nsagrid_astra_TOPBDII: " + sagrid_astra_TOPBDII                    
        + "\nsagrid_astra_ETOKENSERVER: " + sagrid_astra_ETOKENSERVER
        + "\nsagrid_astra_MYPROXYSERVER: " + sagrid_astra_MYPROXYSERVER
        + "\nsagrid_astra_PORT: " + sagrid_astra_PORT
        + "\nsagrid_astra_ROBOTID: " + sagrid_astra_ROBOTID
        + "\nsagrid_astra_ROLE: " + sagrid_astra_ROLE
        + "\nsagrid_astra_RENEWAL: " + sagrid_astra_RENEWAL
        + "\nsagrid_astra_DISABLEVOMS: " + sagrid_astra_DISABLEVOMS

        + "\nastra_APPID: " + astra_APPID
        + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
        + "\nastra_SOFTWARE: " + astra_SOFTWARE
        + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
        + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
        + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
        + "\nSMTP Server: " + SMTP_HOST
        + "\nSender: " + SENDER_MAIL);

        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/edit.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        //super.doHelp(request, response);

        response.setContentType("text/html");

        log.info("\nStarting the HELP mode...");
        PortletRequestDispatcher dispatcher =
                getPortletContext().getRequestDispatcher("/help.jsp");

        dispatcher.include(request, response);
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        response.setContentType("text/html");

        //java.util.Enumeration listPreferences = portletPreferences.getNames();
        PortletRequestDispatcher dispatcher = null;

        String cometa_astra_TOPBDII = "";
        String cometa_astra_VONAME = "";
        String garuda_astra_TOPBDII = "";
        String garuda_astra_VONAME = "";
        String gridit_astra_TOPBDII = "";
        String gridit_astra_VONAME = "";
        String gilda_astra_TOPBDII = "";
        String gilda_astra_VONAME = "";
        String eumed_astra_TOPBDII = "";
        String eumed_astra_VONAME = "";
        String gisela_astra_TOPBDII = "";
        String gisela_astra_VONAME = "";
        String sagrid_astra_TOPBDII = "";
        String sagrid_astra_VONAME = "";
        
        String cometa_astra_ENABLEINFRASTRUCTURE="";
        String garuda_astra_ENABLEINFRASTRUCTURE="";
        String gridit_astra_ENABLEINFRASTRUCTURE="";
        String gilda_astra_ENABLEINFRASTRUCTURE="";
        String eumed_astra_ENABLEINFRASTRUCTURE="";
        String gisela_astra_ENABLEINFRASTRUCTURE="";
        String sagrid_astra_ENABLEINFRASTRUCTURE="";
        String[] infras = new String[7];
                
        String[] astra_INFRASTRUCTURES = 
                portletPreferences.getValues("astra_ENABLEINFRASTRUCTURE", new String[7]);
        
        for (int i=0; i<astra_INFRASTRUCTURES.length; i++) {            
            if (astra_INFRASTRUCTURES[i]!=null && astra_INFRASTRUCTURES[i].equals("cometa")) 
                { cometa_astra_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n COMETA!"); }
            if (astra_INFRASTRUCTURES[i]!=null && astra_INFRASTRUCTURES[i].equals("garuda")) 
                { garuda_astra_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GARUDA!"); }
            if (astra_INFRASTRUCTURES[i]!=null && astra_INFRASTRUCTURES[i].equals("gridit")) 
                { gridit_astra_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GRIDIT!"); }
            if (astra_INFRASTRUCTURES[i]!=null && astra_INFRASTRUCTURES[i].equals("gilda")) 
                { gilda_astra_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GILDA!"); }
            if (astra_INFRASTRUCTURES[i]!=null && astra_INFRASTRUCTURES[i].equals("eumed")) 
                { eumed_astra_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n EUMED!"); }
            if (astra_INFRASTRUCTURES[i]!=null && astra_INFRASTRUCTURES[i].equals("gisela")) 
                { gisela_astra_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n GISELA!"); }
            if (astra_INFRASTRUCTURES[i]!=null && astra_INFRASTRUCTURES[i].equals("sagrid")) 
                { sagrid_astra_ENABLEINFRASTRUCTURE = "checked"; log.info ("\n SAGRID!"); }
        }
        
        // Get the ASTRA APPID from the portlet preferences
        String astra_APPID = portletPreferences.getValue("astra_APPID", "N/A");
        // Get the ASTRA OUTPUT_PATH from the portlet preferences
        String astra_OUTPUT_PATH = portletPreferences.getValue("astra_OUTPUT_PATH", "/tmp");
        // Get the ASTRA SOFTWARE from the portlet preferences
        String astra_SOFTWARE = portletPreferences.getValue("astra_SOFTWARE", "N/A");
        // Get the TRACKING_DB_HOSTNAME from the portlet preferences
        String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
        // Get the TRACKING_DB_USERNAME from the portlet preferences
        String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
        // Get the TRACKING_DB_PASSWORD from the portlet preferences
        String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD", "N/A");
        // Get the SMTP_HOST from the portlet preferences
        String SMTP_HOST = portletPreferences.getValue("SMTP_HOST", "N/A");
        // Get the SENDER_MAIL from the portlet preferences
        String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL", "N/A");
        
        if (cometa_astra_ENABLEINFRASTRUCTURE.equals("checked"))
        {        
            infras[0]="cometa";            
            // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the COMETA VO
            String cometa_astra_INFRASTRUCTURE = portletPreferences.getValue("cometa_astra_INFRASTRUCTURE", "N/A");
            // Get the ASTRA VONAME from the portlet preferences for the COMETA VO
            cometa_astra_VONAME = portletPreferences.getValue("cometa_astra_VONAME", "N/A");
            // Get the ASTRA TOPPBDII from the portlet preferences for the COMETA VO
            cometa_astra_TOPBDII = portletPreferences.getValue("cometa_astra_TOPBDII", "N/A");
            // Get the ASTRA WMS from the portlet preferences for the COMETA VO
            String[] cometa_astra_WMS = portletPreferences.getValues("cometa_astra_WMS", new String[5]);
            // Get the ASTRA ETOKENSERVER from the portlet preferences for the COMETA VO
            String cometa_astra_ETOKENSERVER = portletPreferences.getValue("cometa_astra_ETOKENSERVER", "N/A");
            // Get the ASTRA MYPROXYSERVER from the portlet preferences for the COMETA VO
            String cometa_astra_MYPROXYSERVER = portletPreferences.getValue("cometa_astra_MYPROXYSERVER", "N/A");
            // Get the ASTRA PORT from the portlet preferences for the COMETA VO
            String cometa_astra_PORT = portletPreferences.getValue("cometa_astra_PORT", "N/A");
            // Get the ASTRA ROBOTID from the portlet preferences for the COMETA VO
            String cometa_astra_ROBOTID = portletPreferences.getValue("cometa_astra_ROBOTID", "N/A");
            // Get the ASTRA ROLE from the portlet preferences for the COMETA VO
            String cometa_astra_ROLE = portletPreferences.getValue("cometa_astra_ROLE", "N/A");
            // Get the ASTRA RENEWAL from the portlet preferences for the COMETA VO
            String cometa_astra_RENEWAL = portletPreferences.getValue("cometa_astra_RENEWAL", "checked");
            // Get the ASTRA DISABLEVOMS from the portlet preferences for the COMETA VO
            String cometa_astra_DISABLEVOMS = portletPreferences.getValue("cometa_astra_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the COMETA VO
            String cometa_WMS = "";
            if (cometa_astra_ENABLEINFRASTRUCTURE.equals("checked")) {
                if (cometa_astra_WMS!=null) {
                    //log.info("length="+cometa_astra_WMS.length);
                    for (int i = 0; i < cometa_astra_WMS.length; i++)
                        if (!(cometa_astra_WMS[i].trim().equals("N/A")) ) 
                            cometa_WMS += cometa_astra_WMS[i] + " ";                        
                } else { log.info("WMS not set for COMETA!"); cometa_astra_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("cometa_astra_INFRASTRUCTURE", cometa_astra_INFRASTRUCTURE.trim());
            request.setAttribute("cometa_astra_VONAME", cometa_astra_VONAME.trim());
            request.setAttribute("cometa_astra_TOPBDII", cometa_astra_TOPBDII.trim());
            request.setAttribute("cometa_astra_WMS", cometa_WMS);
            request.setAttribute("cometa_astra_ETOKENSERVER", cometa_astra_ETOKENSERVER.trim());
            request.setAttribute("cometa_astra_MYPROXYSERVER", cometa_astra_MYPROXYSERVER.trim());
            request.setAttribute("cometa_astra_PORT", cometa_astra_PORT.trim());
            request.setAttribute("cometa_astra_ROBOTID", cometa_astra_ROBOTID.trim());
            request.setAttribute("cometa_astra_ROLE", cometa_astra_ROLE.trim());
            request.setAttribute("cometa_astra_RENEWAL", cometa_astra_RENEWAL);
            request.setAttribute("cometa_astra_DISABLEVOMS", cometa_astra_DISABLEVOMS); 
            
            //request.setAttribute("astra_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("astra_APPID", astra_APPID.trim());
            request.setAttribute("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
            request.setAttribute("astra_SOFTWARE", astra_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (garuda_astra_ENABLEINFRASTRUCTURE.equals("checked"))
        {        
            infras[1]="garuda";
            // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the GARUDA VO
            String garuda_astra_INFRASTRUCTURE = portletPreferences.getValue("garuda_astra_INFRASTRUCTURE", "N/A");
            // Get the ASTRA VONAME from the portlet preferences for the GARUDA VO
            garuda_astra_VONAME = portletPreferences.getValue("garuda_astra_VONAME", "N/A");
            // Get the ASTRA TOPPBDII from the portlet preferences for the GARUDA VO
            garuda_astra_TOPBDII = portletPreferences.getValue("garuda_astra_TOPBDII", "N/A");
            // Get the ASTRA WMS from the portlet preferences for the GARUDA VO
            String[] garuda_astra_WMS = portletPreferences.getValues("garuda_astra_WMS", new String[5]);
            // Get the ASTRA ETOKENSERVER from the portlet preferences for the GARUDA VO
            String garuda_astra_ETOKENSERVER = portletPreferences.getValue("garuda_astra_ETOKENSERVER", "N/A");
            // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GARUDA VO
            String garuda_astra_MYPROXYSERVER = portletPreferences.getValue("garuda_astra_MYPROXYSERVER", "N/A");
            // Get the ASTRA PORT from the portlet preferences for the GARUDA VO
            String garuda_astra_PORT = portletPreferences.getValue("garuda_astra_PORT", "N/A");
            // Get the ASTRA ROBOTID from the portlet preferences for the GARUDA VO
            String garuda_astra_ROBOTID = portletPreferences.getValue("garuda_astra_ROBOTID", "N/A");
            // Get the ASTRA ROLE from the portlet preferences for the GARUDA VO
            String garuda_astra_ROLE = portletPreferences.getValue("garuda_astra_ROLE", "N/A");
            // Get the ASTRA RENEWAL from the portlet preferences for the GARUDA VO
            String garuda_astra_RENEWAL = portletPreferences.getValue("garuda_astra_RENEWAL", "checked");
            // Get the ASTRA DISABLEVOMS from the portlet preferences for the GARUDA VO
            String garuda_astra_DISABLEVOMS = portletPreferences.getValue("garuda_astra_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the GARUDA VO            
            String garuda_WMS = "";
            if (garuda_astra_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (garuda_astra_WMS!=null) {
                    //log.info("length="+garuda_astra_WMS.length);
                    for (int i = 0; i < garuda_astra_WMS.length; i++)
                        if (!(garuda_astra_WMS[i].trim().equals("N/A")) ) 
                            garuda_WMS += garuda_astra_WMS[i] + " ";                        
                } else { log.info("WSGRAM not set for GARUDA!"); garuda_astra_ENABLEINFRASTRUCTURE="unchecked"; }
            }   
            
            // Save the portlet preferences
            request.setAttribute("garuda_astra_INFRASTRUCTURE", garuda_astra_INFRASTRUCTURE.trim());
            request.setAttribute("garuda_astra_VONAME", garuda_astra_VONAME.trim());
            request.setAttribute("garuda_astra_TOPBDII", garuda_astra_TOPBDII.trim());
            request.setAttribute("garuda_astra_WMS", garuda_WMS);
            request.setAttribute("garuda_astra_ETOKENSERVER", garuda_astra_ETOKENSERVER.trim());
            request.setAttribute("garuda_astra_MYPROXYSERVER", garuda_astra_MYPROXYSERVER.trim());
            request.setAttribute("garuda_astra_PORT", garuda_astra_PORT.trim());
            request.setAttribute("garuda_astra_ROBOTID", garuda_astra_ROBOTID.trim());
            request.setAttribute("garuda_astra_ROLE", garuda_astra_ROLE.trim());
            request.setAttribute("garuda_astra_RENEWAL", garuda_astra_RENEWAL);
            request.setAttribute("garuda_astra_DISABLEVOMS", garuda_astra_DISABLEVOMS); 
            
            //request.setAttribute("astra_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("astra_APPID", astra_APPID.trim());
            request.setAttribute("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
            request.setAttribute("astra_SOFTWARE", astra_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (gridit_astra_ENABLEINFRASTRUCTURE.equals("checked"))
        {        
            infras[2]="gridit";
            // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the GRIDIT VO
            String gridit_astra_INFRASTRUCTURE = portletPreferences.getValue("gridit_astra_INFRASTRUCTURE", "N/A");
            // Get the ASTRA VONAME from the portlet preferences for the GRIDIT VO
            gridit_astra_VONAME = portletPreferences.getValue("gridit_astra_VONAME", "N/A");
            // Get the ASTRA TOPPBDII from the portlet preferences for the GRIDIT VO
            gridit_astra_TOPBDII = portletPreferences.getValue("gridit_astra_TOPBDII", "N/A");
            // Get the ASTRA WMS from the portlet preferences for the GRIDIT VO
            String[] gridit_astra_WMS = portletPreferences.getValues("gridit_astra_WMS", new String[5]);
            // Get the ASTRA ETOKENSERVER from the portlet preferences for the GRIDIT VO
            String gridit_astra_ETOKENSERVER = portletPreferences.getValue("gridit_astra_ETOKENSERVER", "N/A");
            // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GRIDIT VO
            String gridit_astra_MYPROXYSERVER = portletPreferences.getValue("gridit_astra_MYPROXYSERVER", "N/A");
            // Get the ASTRA PORT from the portlet preferences for the GRIDIT VO
            String gridit_astra_PORT = portletPreferences.getValue("gridit_astra_PORT", "N/A");
            // Get the ASTRA ROBOTID from the portlet preferences for the GRIDIT VO
            String gridit_astra_ROBOTID = portletPreferences.getValue("gridit_astra_ROBOTID", "N/A");
            // Get the ASTRA ROLE from the portlet preferences for the GRIDIT VO
            String gridit_astra_ROLE = portletPreferences.getValue("gridit_astra_ROLE", "N/A");
            // Get the ASTRA RENEWAL from the portlet preferences for the GRIDIT VO
            String gridit_astra_RENEWAL = portletPreferences.getValue("gridit_astra_RENEWAL", "checked");
            // Get the ASTRA DISABLEVOMS from the portlet preferences for the GRIDIT VO
            String gridit_astra_DISABLEVOMS = portletPreferences.getValue("gridit_astra_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the GRIDIT VO            
            String gridit_WMS = "";
            if (gridit_astra_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gridit_astra_WMS!=null) {
                    //log.info("length="+gridit_astra_WMS.length);
                    for (int i = 0; i < gridit_astra_WMS.length; i++)
                        if (!(gridit_astra_WMS[i].trim().equals("N/A")) ) 
                            gridit_WMS += gridit_astra_WMS[i].trim() + " ";
                } else { log.info("WMS not set for GRIDIT!"); gridit_astra_ENABLEINFRASTRUCTURE="unchecked"; }
            }   
            
            // Save the portlet preferences
            request.setAttribute("gridit_astra_INFRASTRUCTURE", gridit_astra_INFRASTRUCTURE.trim());
            request.setAttribute("gridit_astra_VONAME", gridit_astra_VONAME.trim());
            request.setAttribute("gridit_astra_TOPBDII", gridit_astra_TOPBDII.trim());
            request.setAttribute("gridit_astra_WMS", gridit_WMS);
            request.setAttribute("gridit_astra_ETOKENSERVER", gridit_astra_ETOKENSERVER.trim());
            request.setAttribute("gridit_astra_MYPROXYSERVER", gridit_astra_MYPROXYSERVER.trim());
            request.setAttribute("gridit_astra_PORT", gridit_astra_PORT.trim());
            request.setAttribute("gridit_astra_ROBOTID", gridit_astra_ROBOTID.trim());
            request.setAttribute("gridit_astra_ROLE", gridit_astra_ROLE.trim());
            request.setAttribute("gridit_astra_RENEWAL", gridit_astra_RENEWAL);
            request.setAttribute("gridit_astra_DISABLEVOMS", gridit_astra_DISABLEVOMS); 
            
            //request.setAttribute("astra_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("astra_APPID", astra_APPID.trim());
            request.setAttribute("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
            request.setAttribute("astra_SOFTWARE", astra_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (gilda_astra_ENABLEINFRASTRUCTURE.equals("checked"))
        {       
            infras[3]="gilda";
            // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the GILDA VO
            String gilda_astra_INFRASTRUCTURE = portletPreferences.getValue("gilda_astra_INFRASTRUCTURE", "N/A");
            // Get the ASTRA VONAME from the portlet preferences for the GILDA VO
            gilda_astra_VONAME = portletPreferences.getValue("gilda_astra_VONAME", "N/A");
            // Get the ASTRA TOPPBDII from the portlet preferences for the GILDA VO
            gilda_astra_TOPBDII = portletPreferences.getValue("gilda_astra_TOPBDII", "N/A");
            // Get the ASTRA WMS from the portlet preferences for the GILDA VO
            String[] gilda_astra_WMS = portletPreferences.getValues("gilda_astra_WMS", new String[5]);
            // Get the ASTRA ETOKENSERVER from the portlet preferences for the GILDA VO
            String gilda_astra_ETOKENSERVER = portletPreferences.getValue("gilda_astra_ETOKENSERVER", "N/A");
            // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GILDA VO
            String gilda_astra_MYPROXYSERVER = portletPreferences.getValue("gilda_astra_MYPROXYSERVER", "N/A");
            // Get the ASTRA PORT from the portlet preferences for the GILDA VO
            String gilda_astra_PORT = portletPreferences.getValue("gilda_astra_PORT", "N/A");
            // Get the ASTRA ROBOTID from the portlet preferences for the GILDA VO
            String gilda_astra_ROBOTID = portletPreferences.getValue("gilda_astra_ROBOTID", "N/A");
            // Get the ASTRA ROLE from the portlet preferences for the GILDA VO
            String gilda_astra_ROLE = portletPreferences.getValue("gilda_astra_ROLE", "N/A");
            // Get the ASTRA RENEWAL from the portlet preferences for the GILDA VO
            String gilda_astra_RENEWAL = portletPreferences.getValue("gilda_astra_RENEWAL", "checked");
            // Get the ASTRA DISABLEVOMS from the portlet preferences for the GILDA VO
            String gilda_astra_DISABLEVOMS = portletPreferences.getValue("gilda_astra_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the GILDA VO 
            String gilda_WMS = "";
            if (gilda_astra_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gilda_astra_WMS!=null) {
                    //log.info("length="+gilda_astra_WMS.length);
                    for (int i = 0; i < gilda_astra_WMS.length; i++)
                        if (!(gilda_astra_WMS[i].trim().equals("N/A")) ) 
                            gilda_WMS += gilda_astra_WMS[i] + " ";                        
                } else { log.info("WMS not set for GILDA!"); gilda_astra_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("gilda_astra_INFRASTRUCTURE", gilda_astra_INFRASTRUCTURE.trim());
            request.setAttribute("gilda_astra_VONAME", gilda_astra_VONAME.trim());
            request.setAttribute("gilda_astra_TOPBDII", gilda_astra_TOPBDII.trim());
            request.setAttribute("gilda_astra_WMS", gilda_WMS);
            request.setAttribute("gilda_astra_ETOKENSERVER", gilda_astra_ETOKENSERVER.trim());
            request.setAttribute("gilda_astra_MYPROXYSERVER", gilda_astra_MYPROXYSERVER.trim());
            request.setAttribute("gilda_astra_PORT", gilda_astra_PORT.trim());
            request.setAttribute("gilda_astra_ROBOTID", gilda_astra_ROBOTID.trim());
            request.setAttribute("gilda_astra_ROLE", gilda_astra_ROLE.trim());
            request.setAttribute("gilda_astra_RENEWAL", gilda_astra_RENEWAL);
            request.setAttribute("gilda_astra_DISABLEVOMS", gilda_astra_DISABLEVOMS);
       
            //request.setAttribute("astra_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("astra_APPID", astra_APPID.trim());
            request.setAttribute("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
            request.setAttribute("astra_SOFTWARE", astra_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());        
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
        
        if (eumed_astra_ENABLEINFRASTRUCTURE.equals("checked"))
        {       
            infras[4]="eumed";
            // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the EUMED VO
            String eumed_astra_INFRASTRUCTURE = portletPreferences.getValue("eumed_astra_INFRASTRUCTURE", "N/A");
            // Get the ASTRA VONAME from the portlet preferences for the EUMED VO
            eumed_astra_VONAME = portletPreferences.getValue("eumed_astra_VONAME", "N/A");
            // Get the ASTRA TOPPBDII from the portlet preferences for the EUMED VO
            eumed_astra_TOPBDII = portletPreferences.getValue("eumed_astra_TOPBDII", "N/A");
            // Get the ASTRA WMS from the portlet preferences for the EUMED VO
            String[] eumed_astra_WMS = portletPreferences.getValues("eumed_astra_WMS", new String[5]);
            // Get the ASTRA ETOKENSERVER from the portlet preferences for the EUMED VO
            String eumed_astra_ETOKENSERVER = portletPreferences.getValue("eumed_astra_ETOKENSERVER", "N/A");
            // Get the ASTRA MYPROXYSERVER from the portlet preferences for the EUMED VO
            String eumed_astra_MYPROXYSERVER = portletPreferences.getValue("eumed_astra_MYPROXYSERVER", "N/A");
            // Get the ASTRA PORT from the portlet preferences for the EUMED VO
            String eumed_astra_PORT = portletPreferences.getValue("eumed_astra_PORT", "N/A");
            // Get the ASTRA ROBOTID from the portlet preferences for the EUMED VO
            String eumed_astra_ROBOTID = portletPreferences.getValue("eumed_astra_ROBOTID", "N/A");
            // Get the ASTRA ROLE from the portlet preferences for the EUMED VO
            String eumed_astra_ROLE = portletPreferences.getValue("eumed_astra_ROLE", "N/A");
            // Get the ASTRA RENEWAL from the portlet preferences for the EUMED VO
            String eumed_astra_RENEWAL = portletPreferences.getValue("eumed_astra_RENEWAL", "checked");
            // Get the ASTRA DISABLEVOMS from the portlet preferences for the EUMED VO
            String eumed_astra_DISABLEVOMS = portletPreferences.getValue("eumed_astra_DISABLEVOMS", "unchecked");
            
            // Fetching all the WMS Endpoints for the EUMED VO        
            String eumed_WMS = "";
            if (eumed_astra_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (eumed_astra_WMS!=null) {
                    //log.info("length="+eumed_astra_WMS.length);
                    for (int i = 0; i < eumed_astra_WMS.length; i++)
                        if (!(eumed_astra_WMS[i].trim().equals("N/A")) ) 
                            eumed_WMS += eumed_astra_WMS[i] + " ";                        
                } else { log.info("WMS not set for EUMED!"); eumed_astra_ENABLEINFRASTRUCTURE="unchecked"; }
            }
            
            // Save the portlet preferences
            request.setAttribute("eumed_astra_INFRASTRUCTURE", eumed_astra_INFRASTRUCTURE.trim());
            request.setAttribute("eumed_astra_VONAME", eumed_astra_VONAME.trim());
            request.setAttribute("eumed_astra_TOPBDII", eumed_astra_TOPBDII.trim());
            request.setAttribute("eumed_astra_WMS", eumed_WMS);
            request.setAttribute("eumed_astra_ETOKENSERVER", eumed_astra_ETOKENSERVER.trim());
            request.setAttribute("eumed_astra_MYPROXYSERVER", eumed_astra_MYPROXYSERVER.trim());
            request.setAttribute("eumed_astra_PORT", eumed_astra_PORT.trim());
            request.setAttribute("eumed_astra_ROBOTID", eumed_astra_ROBOTID.trim());
            request.setAttribute("eumed_astra_ROLE", eumed_astra_ROLE.trim());
            request.setAttribute("eumed_astra_RENEWAL", eumed_astra_RENEWAL);
            request.setAttribute("eumed_astra_DISABLEVOMS", eumed_astra_DISABLEVOMS);
       
            //request.setAttribute("astra_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("astra_APPID", astra_APPID.trim());
            request.setAttribute("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
            request.setAttribute("astra_SOFTWARE", astra_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());        
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }
            
        if (gisela_astra_ENABLEINFRASTRUCTURE.equals("checked"))
        {         
            infras[5]="gisela";
            // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the GISELA VO
            String gisela_astra_INFRASTRUCTURE = portletPreferences.getValue("gisela_astra_INFRASTRUCTURE", "N/A");
            // Get the ASTRA VONAME from the portlet preferences for the GISELA VO
            gisela_astra_VONAME = portletPreferences.getValue("gisela_astra_VONAME", "N/A");
            // Get the ASTRA TOPPBDII from the portlet preferences for the GISELA VO
            gisela_astra_TOPBDII = portletPreferences.getValue("gisela_astra_TOPBDII", "N/A");
            // Get the ASTRA WMS from the portlet preferences for the GISELA VO
            String[] gisela_astra_WMS = portletPreferences.getValues("gisela_astra_WMS", new String[5]);
            // Get the ASTRA ETOKENSERVER from the portlet preferences for the GISELA VO
            String gisela_astra_ETOKENSERVER = portletPreferences.getValue("gisela_astra_ETOKENSERVER", "N/A");
            // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GISELA VO
            String gisela_astra_MYPROXYSERVER = portletPreferences.getValue("gisela_astra_MYPROXYSERVER", "N/A");
            // Get the ASTRA PORT from the portlet preferences for the GISELA VO
            String gisela_astra_PORT = portletPreferences.getValue("gisela_astra_PORT", "N/A");
            // Get the ASTRA ROBOTID from the portlet preferences for the GISELA VO
            String gisela_astra_ROBOTID = portletPreferences.getValue("gisela_astra_ROBOTID", "N/A");
            // Get the ASTRA ROLE from the portlet preferences for the GISELA VO
            String gisela_astra_ROLE = portletPreferences.getValue("gisela_astra_ROLE", "N/A");
            // Get the ASTRA RENEWAL from the portlet preferences for the GISELA VO
            String gisela_astra_RENEWAL = portletPreferences.getValue("gisela_astra_RENEWAL", "checked");
            // Get the ASTRA DISABLEVOMS from the portlet preferences for the GISELA VO
            String gisela_astra_DISABLEVOMS = portletPreferences.getValue("gisela_astra_DISABLEVOMS", "unchecked");

            // Fetching all the WMS Endpoints for the GISELA VO
            String gisela_WMS = "";
            if (gisela_astra_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (gisela_astra_WMS!=null) {
                    //log.info("length="+gisela_astra_WMS.length);
                    for (int i = 0; i < gisela_astra_WMS.length; i++)
                        if (!(gisela_astra_WMS[i].trim().equals("N/A")) ) 
                            gisela_WMS += gisela_astra_WMS[i] + " ";                        
                } else { log.info("WMS not set for GISELA!"); gisela_astra_ENABLEINFRASTRUCTURE="unchecked"; }
            }

            // Save the portlet preferences
            request.setAttribute("gisela_astra_INFRASTRUCTURE", gisela_astra_INFRASTRUCTURE.trim());
            request.setAttribute("gisela_astra_VONAME", gisela_astra_VONAME.trim());
            request.setAttribute("gisela_astra_TOPBDII", gisela_astra_TOPBDII.trim());
            request.setAttribute("gisela_astra_WMS", gisela_WMS);
            request.setAttribute("gisela_astra_ETOKENSERVER", gisela_astra_ETOKENSERVER.trim());
            request.setAttribute("gisela_astra_MYPROXYSERVER", gisela_astra_MYPROXYSERVER.trim());
            request.setAttribute("gisela_astra_PORT", gisela_astra_PORT.trim());
            request.setAttribute("gisela_astra_ROBOTID", gisela_astra_ROBOTID.trim());
            request.setAttribute("gisela_astra_ROLE", gisela_astra_ROLE.trim());
            request.setAttribute("gisela_astra_RENEWAL", gisela_astra_RENEWAL);
            request.setAttribute("gisela_astra_DISABLEVOMS", gisela_astra_DISABLEVOMS);

            //request.setAttribute("astra_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("astra_APPID", astra_APPID.trim());
            request.setAttribute("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
            request.setAttribute("astra_SOFTWARE", astra_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }        
        
        if (sagrid_astra_ENABLEINFRASTRUCTURE.equals("checked"))
        {         
            infras[6]="sagrid";
            // Get the ASTRA INFRASTRUCTURE from the portlet preferences for the SAGRID VO
            String sagrid_astra_INFRASTRUCTURE = portletPreferences.getValue("sagrid_astra_INFRASTRUCTURE", "N/A");
            // Get the ASTRA VONAME from the portlet preferences for the SAGRID VO
            sagrid_astra_VONAME = portletPreferences.getValue("sagrid_astra_VONAME", "N/A");
            // Get the ASTRA TOPPBDII from the portlet preferences for the SAGRID VO
            sagrid_astra_TOPBDII = portletPreferences.getValue("sagrid_astra_TOPBDII", "N/A");
            // Get the ASTRA WMS from the portlet preferences for the SAGRID VO
            String[] sagrid_astra_WMS = portletPreferences.getValues("sagrid_astra_WMS", new String[5]);
            // Get the ASTRA ETOKENSERVER from the portlet preferences for the SAGRID VO
            String sagrid_astra_ETOKENSERVER = portletPreferences.getValue("sagrid_astra_ETOKENSERVER", "N/A");
            // Get the ASTRA MYPROXYSERVER from the portlet preferences for the SAGRID VO
            String sagrid_astra_MYPROXYSERVER = portletPreferences.getValue("sagrid_astra_MYPROXYSERVER", "N/A");
            // Get the ASTRA PORT from the portlet preferences for the SAGRID VO
            String sagrid_astra_PORT = portletPreferences.getValue("sagrid_astra_PORT", "N/A");
            // Get the ASTRA ROBOTID from the portlet preferences for the SAGRID VO
            String sagrid_astra_ROBOTID = portletPreferences.getValue("sagrid_astra_ROBOTID", "N/A");
            // Get the ASTRA ROLE from the portlet preferences for the SAGRID VO
            String sagrid_astra_ROLE = portletPreferences.getValue("sagrid_astra_ROLE", "N/A");
            // Get the ASTRA RENEWAL from the portlet preferences for the SAGRID VO
            String sagrid_astra_RENEWAL = portletPreferences.getValue("sagrid_astra_RENEWAL", "checked");
            // Get the ASTRA DISABLEVOMS from the portlet preferences for the SAGRID VO
            String sagrid_astra_DISABLEVOMS = portletPreferences.getValue("sagrid_astra_DISABLEVOMS", "unchecked");

            // Fetching all the WMS Endpoints for the SAGRID VO
            String sagrid_WMS = "";
            if (sagrid_astra_ENABLEINFRASTRUCTURE.equals("checked")) {            
                if (sagrid_astra_WMS!=null) {
                    //log.info("length="+sagrid_astra_WMS.length);
                    for (int i=0; i<sagrid_astra_WMS.length; i++)                        
                        if (!(sagrid_astra_WMS[i].trim().equals("N/A")) ) 
                            sagrid_WMS += sagrid_astra_WMS[i] + " ";                    
                } else { log.info("WMS not set for SAGRID!"); sagrid_astra_ENABLEINFRASTRUCTURE="unchecked"; }
            }

            // Save the portlet preferences
            request.setAttribute("sagrid_astra_INFRASTRUCTURE", sagrid_astra_INFRASTRUCTURE.trim());
            request.setAttribute("sagrid_astra_VONAME", sagrid_astra_VONAME.trim());
            request.setAttribute("sagrid_astra_TOPBDII", sagrid_astra_TOPBDII.trim());
            request.setAttribute("sagrid_astra_WMS", sagrid_WMS);
            request.setAttribute("sagrid_astra_ETOKENSERVER", sagrid_astra_ETOKENSERVER.trim());
            request.setAttribute("sagrid_astra_MYPROXYSERVER", sagrid_astra_MYPROXYSERVER.trim());
            request.setAttribute("sagrid_astra_PORT", sagrid_astra_PORT.trim());
            request.setAttribute("sagrid_astra_ROBOTID", sagrid_astra_ROBOTID.trim());
            request.setAttribute("sagrid_astra_ROLE", sagrid_astra_ROLE.trim());
            request.setAttribute("sagrid_astra_RENEWAL", sagrid_astra_RENEWAL);
            request.setAttribute("sagrid_astra_DISABLEVOMS", sagrid_astra_DISABLEVOMS);

            //request.setAttribute("astra_ENABLEINFRASTRUCTURE", infras);
            request.setAttribute("astra_APPID", astra_APPID.trim());
            request.setAttribute("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
            request.setAttribute("astra_SOFTWARE", astra_SOFTWARE.trim());
            request.setAttribute("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
            request.setAttribute("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
            request.setAttribute("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
            request.setAttribute("SMTP_HOST", SMTP_HOST.trim());
            request.setAttribute("SENDER_MAIL", SENDER_MAIL.trim());
        }

        // Save in the preferences the list of supported infrastructures 
        request.setAttribute("astra_ENABLEINFRASTRUCTURE", infras);        
        
        HashMap<String,Properties> GPS_table = new HashMap<String, Properties>();
        HashMap<String,Properties> GPS_queue = new HashMap<String, Properties>();

        // ********************************************************
        List<String> CEqueues_cometa = null;  
        List<String> CEqueues_garuda = null;
        List<String> CEqueues_gridit = null;
        List<String> CEqueues_gilda = null;
        List<String> CEqueues_eumed = null;
        List<String> CEqueues_gisela = null;
        List<String> CEqueues_sagrid = null;
         
        List<String> CEs_list_cometa = null;        
        List<String> CEs_list_garuda = null;
        List<String> CEs_list_gridit = null;
        List<String> CEs_list_gilda = null;
        List<String> CEs_list_eumed = null;
        List<String> CEs_list_gisela = null;
        List<String> CEs_list_sagrid = null;
         
        BDII bdii = null;

        try {
                if (cometa_astra_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!cometa_astra_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<COMETA>*RESOURCES*-----");
                    bdii = new BDII(new URI(cometa_astra_TOPBDII));
                    CEs_list_cometa = 
                            getListofCEForSoftwareTag(cometa_astra_VONAME,
                                                      cometa_astra_TOPBDII,
                                                      astra_SOFTWARE);
                    
                    CEqueues_cometa = 
                            bdii.queryCEQueues(cometa_astra_VONAME);
                }
                
                //=========================================================
                // IMPORTANT: THIS FIX IS ONLY FOR INSTANCIATE THE 
                //            CHAIN INTEROPERABILITY DEMO                
                //=========================================================
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
                if (garuda_astra_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!garuda_astra_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GARUDA>*RESOURCES*-----");
                    CEs_list_garuda = new ArrayList();
                    CEs_list_garuda.add("xn03.ctsf.cdacb.in");
                    
                    CEqueues_garuda = new ArrayList();
                    CEqueues_garuda.add("wsgram://xn03.ctsf.cdacb.in:8443/GW");
                }
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
                
                if (gridit_astra_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!gridit_astra_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GRIDIT>*RESOURCES*-----");
                    bdii = new BDII(new URI(gridit_astra_TOPBDII));
                    CEs_list_gridit = 
                                getListofCEForSoftwareTag(gridit_astra_VONAME,
                                                          gridit_astra_TOPBDII,
                                                          astra_SOFTWARE);
                    
                    CEqueues_gridit = 
                                bdii.queryCEQueues(gridit_astra_VONAME);
                }
                
                if (gilda_astra_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!gilda_astra_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GILDA>*RESOURCES*-----");
                    bdii = new BDII(new URI(gilda_astra_TOPBDII));
                    CEs_list_gilda = 
                                getListofCEForSoftwareTag(gilda_astra_VONAME,
                                                          gilda_astra_TOPBDII,
                                                          astra_SOFTWARE);
                    
                    CEqueues_gilda = 
                            bdii.queryCEQueues(gilda_astra_VONAME);
                }
                
                if (eumed_astra_ENABLEINFRASTRUCTURE.equals("checked") && 
                   (!eumed_astra_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<EUMED>*RESOURCES*-----");
                    bdii = new BDII(new URI(eumed_astra_TOPBDII));
                    CEs_list_eumed = 
                                getListofCEForSoftwareTag(eumed_astra_VONAME,
                                                          eumed_astra_TOPBDII,
                                                          astra_SOFTWARE);
                    
                    CEqueues_eumed = 
                            bdii.queryCEQueues(eumed_astra_VONAME);
                }
                
                if (gisela_astra_ENABLEINFRASTRUCTURE.equals("checked") &&
                   (!gisela_astra_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<GISELA>*RESOURCES*-----");
                    bdii = new BDII(new URI(gisela_astra_TOPBDII));
                    CEs_list_gisela = 
                                getListofCEForSoftwareTag(gisela_astra_VONAME,
                                                          gisela_astra_TOPBDII,
                                                          astra_SOFTWARE);
                    
                    CEqueues_gisela = 
                            bdii.queryCEQueues(gisela_astra_VONAME);
                }
                
                if (sagrid_astra_ENABLEINFRASTRUCTURE.equals("checked") &&
                   (!sagrid_astra_TOPBDII.equals("N/A")) ) 
                {
                    log.info("-----*FETCHING*THE*<SAGRID>*RESOURCES*-----");
                    bdii = new BDII(new URI(sagrid_astra_TOPBDII));
                    CEs_list_sagrid = 
                                getListofCEForSoftwareTag(sagrid_astra_VONAME,
                                                          sagrid_astra_TOPBDII,
                                                          astra_SOFTWARE);
                    
                    CEqueues_sagrid = 
                            bdii.queryCEQueues(sagrid_astra_VONAME);
                }
                
                // Merging the list of CEs and queues
                List<String> CEs_list_TOT = new ArrayList<String>();
                if (cometa_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_cometa);
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
                if (garuda_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_garuda);
                // ===== ONLY FOR THE CHAIN INTEROPERABILITY DEMO =====
                if (gridit_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gridit);
                if (gilda_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gilda);
                if (eumed_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_eumed);
                if (gisela_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_gisela);
                if (sagrid_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                        CEs_list_TOT.addAll(CEs_list_sagrid);
                                
                List<String> CEs_queue_TOT = new ArrayList<String>();
                if (cometa_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_cometa);
                if (gridit_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gridit);
                if (gilda_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gilda);                
                if (eumed_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_eumed);
                if (gisela_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_gisela);
                if (sagrid_astra_ENABLEINFRASTRUCTURE.equals("checked"))
                    CEs_queue_TOT.addAll(CEqueues_sagrid);
                
                //=========================================================
                // IMPORTANT: INSTANCIATE THE UsersTrackingDBInterface
                //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
                //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
                //=========================================================
                /*UsersTrackingDBInterface DBInterface =
                        new UsersTrackingDBInterface(
                                TRACKING_DB_HOSTNAME.trim(),
                                TRACKING_DB_USERNAME.trim(),
                                TRACKING_DB_PASSWORD.trim());*/
                
                UsersTrackingDBInterface DBInterface =
                        new UsersTrackingDBInterface();
                
		if ( (CEs_list_TOT != null) && (!CEs_queue_TOT.isEmpty()) )
                {
                    log.info("NOT EMPTY LIST!");                    
                    // Fetching the list of CEs publushing the SW
                    for (String CE:CEs_list_TOT)  
                    {
                        log.info("Fetching the CE="+CE);
                        Properties coordinates = new Properties();
                        Properties queue = new Properties();

                        float coords[] = DBInterface.getCECoordinate(CE);                        

                        String GPS_LAT = Float.toString(coords[0]);
                        String GPS_LNG = Float.toString(coords[1]);

                        coordinates.setProperty("LAT", GPS_LAT);
                        coordinates.setProperty("LNG", GPS_LNG);

                        // Fetching the Queues
                        for (String CEqueue:CEs_queue_TOT) {
                                if (CEqueue.contains(CE))
                                    queue.setProperty("QUEUE", CEqueue);
                        }

                        // Saving the GPS location in a Java HashMap
                        GPS_table.put(CE, coordinates);

                        // Saving the queue in a Java HashMap
                        GPS_queue.put(CE, queue);
                    }
                } else log.info ("EMPTY LIST!");
             } catch (URISyntaxException ex) {
               Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException e) {}

            // Checking the HashMap
            Set set = GPS_table.entrySet();
            Iterator iter = set.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry entry = (Map.Entry)iter.next();
                log.info(" - GPS location of the CE " +
                           entry.getKey() + " => " + entry.getValue());
            }

            // Checking the HashMap
            set = GPS_queue.entrySet();
            iter = set.iterator();
            while ( iter.hasNext() )
            {
                Map.Entry entry = (Map.Entry)iter.next();
                log.info(" - Queue " +
                           entry.getKey() + " => " + entry.getValue());
            }

            Gson gson = new GsonBuilder().create();
            request.setAttribute ("GPS_table", gson.toJson(GPS_table));
            request.setAttribute ("GPS_queue", gson.toJson(GPS_queue));

            // ********************************************************

            dispatcher = getPortletContext().getRequestDispatcher("/view.jsp");       
            dispatcher.include(request, response);
    }

    // The init method will be called when installing for the first time the portlet
    // This is the right time to setup the default values into the preferences
    @Override
    public void init() throws PortletException {
        super.init();
    }

    @Override
    public void processAction(ActionRequest request,
                              ActionResponse response)
                throws PortletException, IOException {

        String action = "";
        
        // Get the action to be processed from the request
        action = request.getParameter("ActionEvent");

        // Determine the username and the email address
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User user = themeDisplay.getUser();
        String username = user.getScreenName();
	String emailAddress = user.getDisplayEmailAddress();

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        if (action.equals("CONFIG_ASTRA_PORTLET")) {
            log.info("\nPROCESS ACTION => " + action);
            
            // Get the ASTRA APPID from the portlet request
            String astra_APPID = request.getParameter("astra_APPID");
            // Get the ASTRA_OUTPUT_PATH from the portlet request
            String astra_OUTPUT_PATH = request.getParameter("astra_OUTPUT_PATH");
            // Get the ASTRA SOFTWARE from the portlet request
            String astra_SOFTWARE = request.getParameter("astra_SOFTWARE");
            // Get the TRACKING_DB_HOSTNAME from the portlet request for the GRIDIT VO
            String TRACKING_DB_HOSTNAME = request.getParameter("TRACKING_DB_HOSTNAME");
            // Get the TRACKING_DB_USERNAME from the portlet request for the GRIDIT VO
            String TRACKING_DB_USERNAME = request.getParameter("TRACKING_DB_USERNAME");
            // Get the TRACKING_DB_PASSWORD from the portlet request for the GRIDIT VO
            String TRACKING_DB_PASSWORD = request.getParameter("TRACKING_DB_PASSWORD");
            // Get the SMTP_HOST from the portlet request
            String SMTP_HOST = request.getParameter("SMTP_HOST");
            // Get the SENDER_MAIL from the portlet request
            String SENDER_MAIL = request.getParameter("SENDER_MAIL");
            String[] infras = new String[7];
            
            String cometa_astra_ENABLEINFRASTRUCTURE = "unchecked";
            String garuda_astra_ENABLEINFRASTRUCTURE = "unchecked";
            String gridit_astra_ENABLEINFRASTRUCTURE = "unchecked";
            String gilda_astra_ENABLEINFRASTRUCTURE = "unchecked";
            String eumed_astra_ENABLEINFRASTRUCTURE = "unchecked";
            String gisela_astra_ENABLEINFRASTRUCTURE = "unchecked";
            String sagrid_astra_ENABLEINFRASTRUCTURE = "unchecked";
        
            String[] astra_INFRASTRUCTURES = request.getParameterValues("astra_ENABLEINFRASTRUCTURES");
        
            if (astra_INFRASTRUCTURES != null) {
                Arrays.sort(astra_INFRASTRUCTURES);                    
                cometa_astra_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(astra_INFRASTRUCTURES, "cometa") >= 0 ? "checked" : "unchecked";
                garuda_astra_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(astra_INFRASTRUCTURES, "garuda") >= 0 ? "checked" : "unchecked";
                gridit_astra_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(astra_INFRASTRUCTURES, "gridit") >= 0 ? "checked" : "unchecked";
                gilda_astra_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(astra_INFRASTRUCTURES, "gilda") >= 0 ? "checked" : "unchecked";
                eumed_astra_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(astra_INFRASTRUCTURES, "eumed") >= 0 ? "checked" : "unchecked";
                gisela_astra_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(astra_INFRASTRUCTURES, "gisela") >= 0 ? "checked" : "unchecked";
                sagrid_astra_ENABLEINFRASTRUCTURE = 
                    Arrays.binarySearch(astra_INFRASTRUCTURES, "sagrid") >= 0 ? "checked" : "unchecked";
            }
                    
            if (cometa_astra_ENABLEINFRASTRUCTURE.equals("checked"))
            {                
                infras[0]="cometa";
                // Get the ASTRA INFRASTRUCTURE from the portlet request for the COMETA VO
                String cometa_astra_INFRASTRUCTURE = request.getParameter("cometa_astra_INFRASTRUCTURE");
                // Get the ASTRA VONAME from the portlet request for the COMETA VO
                String cometa_astra_VONAME = request.getParameter("cometa_astra_VONAME");
                // Get the ASTRA TOPBDII from the portlet request for the COMETA VO
                String cometa_astra_TOPBDII = request.getParameter("cometa_astra_TOPBDII");
                // Get the ASTRA WMS from the portlet request for the COMETA VO
                String[] cometa_astra_WMS = request.getParameterValues("cometa_astra_WMS");
                // Get the ASTRA ETOKENSERVER from the portlet request for the COMETA VO
                String cometa_astra_ETOKENSERVER = request.getParameter("cometa_astra_ETOKENSERVER");
                // Get the ASTRA MYPROXYSERVER from the portlet request for the COMETA VO
                String cometa_astra_MYPROXYSERVER = request.getParameter("cometa_astra_MYPROXYSERVER");
                // Get the ASTRA PORT from the portlet request for the COMETA VO
                String cometa_astra_PORT = request.getParameter("cometa_astra_PORT");
                // Get the ASTRA ROBOTID from the portlet request for the COMETA VO
                String cometa_astra_ROBOTID = request.getParameter("cometa_astra_ROBOTID");
                // Get the ASTRA ROLE from the portlet request for the COMETA VO
                String cometa_astra_ROLE = request.getParameter("cometa_astra_ROLE");
                // Get the ASTRA OPTIONS from the portlet request for the COMETA VO
                String[] cometa_astra_OPTIONS = request.getParameterValues("cometa_astra_OPTIONS");
            
                String cometa_astra_RENEWAL = "";
                String cometa_astra_DISABLEVOMS = "";

                if (cometa_astra_OPTIONS == null){
                    cometa_astra_RENEWAL = "checked";
                    cometa_astra_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(cometa_astra_OPTIONS);
                    // Get the ASTRA RENEWAL from the portlet preferences for the COMETA VO
                    cometa_astra_RENEWAL = Arrays.binarySearch(cometa_astra_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the ASTRA DISABLEVOMS from the portlet preferences for the COMETA VO
                    cometa_astra_DISABLEVOMS = Arrays.binarySearch(cometa_astra_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < cometa_astra_WMS.length; i++)
                    if ( cometa_astra_WMS[i]!=null && (!cometa_astra_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] cometa_astra_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    cometa_astra_WMS_trimmed[i]=cometa_astra_WMS[i].trim();
                    log.info ("\n\nCOMETA [" + i + "] WMS=[" + cometa_astra_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("cometa_astra_INFRASTRUCTURE", cometa_astra_INFRASTRUCTURE.trim());
                portletPreferences.setValue("cometa_astra_VONAME", cometa_astra_VONAME.trim());
                portletPreferences.setValue("cometa_astra_TOPBDII", cometa_astra_TOPBDII.trim());
                portletPreferences.setValues("cometa_astra_WMS", cometa_astra_WMS_trimmed);
                portletPreferences.setValue("cometa_astra_ETOKENSERVER", cometa_astra_ETOKENSERVER.trim());
                portletPreferences.setValue("cometa_astra_MYPROXYSERVER", cometa_astra_MYPROXYSERVER.trim());
                portletPreferences.setValue("cometa_astra_PORT", cometa_astra_PORT.trim());
                portletPreferences.setValue("cometa_astra_ROBOTID", cometa_astra_ROBOTID.trim());
                portletPreferences.setValue("cometa_astra_ROLE", cometa_astra_ROLE.trim());
                portletPreferences.setValue("cometa_astra_RENEWAL", cometa_astra_RENEWAL);
                portletPreferences.setValue("cometa_astra_DISABLEVOMS", cometa_astra_DISABLEVOMS);
                
                portletPreferences.setValue("astra_APPID", astra_APPID.trim());
                portletPreferences.setValue("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
                portletPreferences.setValue("astra_SOFTWARE", astra_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the ASTRA portlet preferences ..."
                + "\ncometa_astra_INFRASTRUCTURE: " + cometa_astra_INFRASTRUCTURE
                + "\ncometa_astra_VONAME: " + cometa_astra_VONAME
                + "\ncometa_astra_TOPBDII: " + cometa_astra_TOPBDII                    
                + "\ncometa_astra_ETOKENSERVER: " + cometa_astra_ETOKENSERVER
                + "\ncometa_astra_MYPROXYSERVER: " + cometa_astra_MYPROXYSERVER
                + "\ncometa_astra_PORT: " + cometa_astra_PORT
                + "\ncometa_astra_ROBOTID: " + cometa_astra_ROBOTID
                + "\ncometa_astra_ROLE: " + cometa_astra_ROLE
                + "\ncometa_astra_RENEWAL: " + cometa_astra_RENEWAL
                + "\ncometa_astra_DISABLEVOMS: " + cometa_astra_DISABLEVOMS
                       
                + "\n\nastra_ENABLEINFRASTRUCTURE: " + "cometa"
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
            
            if (garuda_astra_ENABLEINFRASTRUCTURE.equals("checked"))
            {       
                infras[1]="garuda";
                // Get the ASTRA INFRASTRUCTURE from the portlet request for the GARUDA VO
                String garuda_astra_INFRASTRUCTURE = request.getParameter("garuda_astra_INFRASTRUCTURE");
                // Get the ASTRA VONAME from the portlet request for the GARUDA VO
                String garuda_astra_VONAME = request.getParameter("garuda_astra_VONAME");
                // Get the ASTRA TOPBDII from the portlet request for the GARUDA VO
                String garuda_astra_TOPBDII = request.getParameter("garuda_astra_TOPBDII");
                // Get the ASTRA WMS from the portlet request for the GARUDA VO
                String[] garuda_astra_WMS = request.getParameterValues("garuda_astra_WMS");
                // Get the ASTRA ETOKENSERVER from the portlet request for the GARUDA VO
                String garuda_astra_ETOKENSERVER = request.getParameter("garuda_astra_ETOKENSERVER");
                // Get the ASTRA MYPROXYSERVER from the portlet request for the GARUDA VO
                String garuda_astra_MYPROXYSERVER = request.getParameter("garuda_astra_MYPROXYSERVER");
                // Get the ASTRA PORT from the portlet request for the GARUDA VO
                String garuda_astra_PORT = request.getParameter("garuda_astra_PORT");
                // Get the ASTRA ROBOTID from the portlet request for the GARUDA VO
                String garuda_astra_ROBOTID = request.getParameter("garuda_astra_ROBOTID");
                // Get the ASTRA ROLE from the portlet request for the GARUDA VO
                String garuda_astra_ROLE = request.getParameter("garuda_astra_ROLE");
                // Get the ASTRA OPTIONS from the portlet request for the GARUDA VO
                String[] garuda_astra_OPTIONS = request.getParameterValues("garuda_astra_OPTIONS");
            
                String garuda_astra_RENEWAL = "";
                String garuda_astra_DISABLEVOMS = "";

                if (garuda_astra_OPTIONS == null){
                    garuda_astra_RENEWAL = "checked";
                    garuda_astra_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(garuda_astra_OPTIONS);
                    // Get the ASTRA RENEWAL from the portlet preferences for the GARUDA VO
                    garuda_astra_RENEWAL = Arrays.binarySearch(garuda_astra_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the ASTRA DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    garuda_astra_DISABLEVOMS = Arrays.binarySearch(garuda_astra_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < garuda_astra_WMS.length; i++)
                    if ( garuda_astra_WMS[i]!=null && (!garuda_astra_WMS[i].trim().equals("N/A")) )                        
                        nmax++;                    
                
                log.info("\n\nLength="+nmax);
                String[] garuda_astra_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    garuda_astra_WMS_trimmed[i]=garuda_astra_WMS[i].trim();
                    log.info ("\n\nGARUDA [" + i + "] WSGRAM=[" + garuda_astra_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("garuda_astra_INFRASTRUCTURE", garuda_astra_INFRASTRUCTURE.trim());
                portletPreferences.setValue("garuda_astra_VONAME", garuda_astra_VONAME.trim());
                portletPreferences.setValue("garuda_astra_TOPBDII", garuda_astra_TOPBDII.trim());
                portletPreferences.setValues("garuda_astra_WMS", garuda_astra_WMS_trimmed);
                portletPreferences.setValue("garuda_astra_ETOKENSERVER", garuda_astra_ETOKENSERVER.trim());
                portletPreferences.setValue("garuda_astra_MYPROXYSERVER", garuda_astra_MYPROXYSERVER.trim());
                portletPreferences.setValue("garuda_astra_PORT", garuda_astra_PORT.trim());
                portletPreferences.setValue("garuda_astra_ROBOTID", garuda_astra_ROBOTID.trim());
                portletPreferences.setValue("garuda_astra_ROLE", garuda_astra_ROLE.trim());
                portletPreferences.setValue("garuda_astra_RENEWAL", garuda_astra_RENEWAL);
                portletPreferences.setValue("garuda_astra_DISABLEVOMS", garuda_astra_DISABLEVOMS);
                
                portletPreferences.setValue("astra_APPID", astra_APPID.trim());
                portletPreferences.setValue("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
                portletPreferences.setValue("astra_SOFTWARE", astra_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the ASTRA portlet preferences ..."
                + "\ngaruda_astra_INFRASTRUCTURE: " + garuda_astra_INFRASTRUCTURE
                + "\ngaruda_astra_VONAME: " + garuda_astra_VONAME
                + "\ngaruda_astra_TOPBDII: " + garuda_astra_TOPBDII                    
                + "\ngaruda_astra_ETOKENSERVER: " + garuda_astra_ETOKENSERVER
                + "\ngaruda_astra_MYPROXYSERVER: " + garuda_astra_MYPROXYSERVER
                + "\ngaruda_astra_PORT: " + garuda_astra_PORT
                + "\ngaruda_astra_ROBOTID: " + garuda_astra_ROBOTID
                + "\ngaruda_astra_ROLE: " + garuda_astra_ROLE
                + "\ngaruda_astra_RENEWAL: " + garuda_astra_RENEWAL
                + "\ngaruda_astra_DISABLEVOMS: " + garuda_astra_DISABLEVOMS
                       
                + "\n\nastra_ENABLEINFRASTRUCTURE: " + "garuda"
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
            
            if (gridit_astra_ENABLEINFRASTRUCTURE.equals("checked"))
            {       
                infras[2]="gridit";
                // Get the ASTRA INFRASTRUCTURE from the portlet request for the GRIDIT VO
                String gridit_astra_INFRASTRUCTURE = request.getParameter("gridit_astra_INFRASTRUCTURE");
                // Get the ASTRA VONAME from the portlet request for the GRIDIT VO
                String gridit_astra_VONAME = request.getParameter("gridit_astra_VONAME");
                // Get the ASTRA TOPBDII from the portlet request for the GRIDIT VO
                String gridit_astra_TOPBDII = request.getParameter("gridit_astra_TOPBDII");
                // Get the ASTRA WMS from the portlet request for the GRIDIT VO
                String[] gridit_astra_WMS = request.getParameterValues("gridit_astra_WMS");
                // Get the ASTRA ETOKENSERVER from the portlet request for the GRIDIT VO
                String gridit_astra_ETOKENSERVER = request.getParameter("gridit_astra_ETOKENSERVER");
                // Get the ASTRA MYPROXYSERVER from the portlet request for the GRIDIT VO
                String gridit_astra_MYPROXYSERVER = request.getParameter("gridit_astra_MYPROXYSERVER");
                // Get the ASTRA PORT from the portlet request for the GRIDIT VO
                String gridit_astra_PORT = request.getParameter("gridit_astra_PORT");
                // Get the ASTRA ROBOTID from the portlet request for the GRIDIT VO
                String gridit_astra_ROBOTID = request.getParameter("gridit_astra_ROBOTID");
                // Get the ASTRA ROLE from the portlet request for the GRIDIT VO
                String gridit_astra_ROLE = request.getParameter("gridit_astra_ROLE");
                // Get the ASTRA OPTIONS from the portlet request for the GRIDIT VO
                String[] gridit_astra_OPTIONS = request.getParameterValues("gridit_astra_OPTIONS");
            
                String gridit_astra_RENEWAL = "";
                String gridit_astra_DISABLEVOMS = "";

                if (gridit_astra_OPTIONS == null){
                    gridit_astra_RENEWAL = "checked";
                    gridit_astra_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(gridit_astra_OPTIONS);
                    // Get the ASTRA RENEWAL from the portlet preferences for the GRIDIT VO
                    gridit_astra_RENEWAL = Arrays.binarySearch(gridit_astra_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the ASTRA DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    gridit_astra_DISABLEVOMS = Arrays.binarySearch(gridit_astra_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < gridit_astra_WMS.length; i++)
                    if ( gridit_astra_WMS[i]!=null && (!gridit_astra_WMS[i].trim().equals("N/A")) )                        
                        nmax++;                    
                
                log.info("\n\nLength="+nmax);
                String[] gridit_astra_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gridit_astra_WMS_trimmed[i]=gridit_astra_WMS[i].trim();
                    log.info ("\n\nGRIDIT [" + i + "] WMS=[" + gridit_astra_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("gridit_astra_INFRASTRUCTURE", gridit_astra_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gridit_astra_VONAME", gridit_astra_VONAME.trim());
                portletPreferences.setValue("gridit_astra_TOPBDII", gridit_astra_TOPBDII.trim());
                portletPreferences.setValues("gridit_astra_WMS", gridit_astra_WMS_trimmed);
                portletPreferences.setValue("gridit_astra_ETOKENSERVER", gridit_astra_ETOKENSERVER.trim());
                portletPreferences.setValue("gridit_astra_MYPROXYSERVER", gridit_astra_MYPROXYSERVER.trim());
                portletPreferences.setValue("gridit_astra_PORT", gridit_astra_PORT.trim());
                portletPreferences.setValue("gridit_astra_ROBOTID", gridit_astra_ROBOTID.trim());
                portletPreferences.setValue("gridit_astra_ROLE", gridit_astra_ROLE.trim());
                portletPreferences.setValue("gridit_astra_RENEWAL", gridit_astra_RENEWAL);
                portletPreferences.setValue("gridit_astra_DISABLEVOMS", gridit_astra_DISABLEVOMS);
                
                portletPreferences.setValue("astra_APPID", astra_APPID.trim());
                portletPreferences.setValue("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
                portletPreferences.setValue("astra_SOFTWARE", astra_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the ASTRA portlet preferences ..."
                + "\ngridit_astra_INFRASTRUCTURE: " + gridit_astra_INFRASTRUCTURE
                + "\ngridit_astra_VONAME: " + gridit_astra_VONAME
                + "\ngridit_astra_TOPBDII: " + gridit_astra_TOPBDII                    
                + "\ngridit_astra_ETOKENSERVER: " + gridit_astra_ETOKENSERVER
                + "\ngridit_astra_MYPROXYSERVER: " + gridit_astra_MYPROXYSERVER
                + "\ngridit_astra_PORT: " + gridit_astra_PORT
                + "\ngridit_astra_ROBOTID: " + gridit_astra_ROBOTID
                + "\ngridit_astra_ROLE: " + gridit_astra_ROLE
                + "\ngridit_astra_RENEWAL: " + gridit_astra_RENEWAL
                + "\ngridit_astra_DISABLEVOMS: " + gridit_astra_DISABLEVOMS
                       
                + "\n\nastra_ENABLEINFRASTRUCTURE: " + "gridit"
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
            
            if (gilda_astra_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[3]="gilda";
                // Get the ASTRA INFRASTRUCTURE from the portlet request for the GILDA VO
                String gilda_astra_INFRASTRUCTURE = request.getParameter("gilda_astra_INFRASTRUCTURE");
                // Get the ASTRA VONAME from the portlet request for the GILDA VO
                String gilda_astra_VONAME = request.getParameter("gilda_astra_VONAME");
                // Get the ASTRA TOPBDII from the portlet request for the GILDA VO
                String gilda_astra_TOPBDII = request.getParameter("gilda_astra_TOPBDII");
                // Get the ASTRA WMS from the portlet request for the GILDA VO
                String[] gilda_astra_WMS = request.getParameterValues("gilda_astra_WMS");
                // Get the ASTRA ETOKENSERVER from the portlet request for the GILDA VO
                String gilda_astra_ETOKENSERVER = request.getParameter("gilda_astra_ETOKENSERVER");
                // Get the ASTRA MYPROXYSERVER from the portlet request for the GILDA VO
                String gilda_astra_MYPROXYSERVER = request.getParameter("gilda_astra_MYPROXYSERVER");
                // Get the ASTRA PORT from the portlet request for the GILDA VO
                String gilda_astra_PORT = request.getParameter("gilda_astra_PORT");
                // Get the ASTRA ROBOTID from the portlet request for the GILDA VO
                String gilda_astra_ROBOTID = request.getParameter("gilda_astra_ROBOTID");
                // Get the ASTRA ROLE from the portlet request for the GILDA VO
                String gilda_astra_ROLE = request.getParameter("gilda_astra_ROLE");
                // Get the ASTRA OPTIONS from the portlet request for the GILDA VO
                String[] gilda_astra_OPTIONS = request.getParameterValues("gilda_astra_OPTIONS");                

                String gilda_astra_RENEWAL = "";
                String gilda_astra_DISABLEVOMS = "";

                if (gilda_astra_OPTIONS == null){
                    gilda_astra_RENEWAL = "checked";
                    gilda_astra_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(gilda_astra_OPTIONS);
                    // Get the ASTRA RENEWAL from the portlet preferences for the GILDA VO
                    gilda_astra_RENEWAL = Arrays.binarySearch(gilda_astra_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the ASTRA DISABLEVOMS from the portlet preferences for the GILDA VO
                    gilda_astra_DISABLEVOMS = Arrays.binarySearch(gilda_astra_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < gilda_astra_WMS.length; i++)
                    if ( gilda_astra_WMS[i]!=null && (!gilda_astra_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] gilda_astra_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gilda_astra_WMS_trimmed[i]=gilda_astra_WMS[i].trim();
                    log.info ("\n\nGILDA [" + i + "] WMS=[" + gilda_astra_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("gilda_astra_INFRASTRUCTURE", gilda_astra_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gilda_astra_VONAME", gilda_astra_VONAME.trim());
                portletPreferences.setValue("gilda_astra_TOPBDII", gilda_astra_TOPBDII.trim());
                portletPreferences.setValues("gilda_astra_WMS", gilda_astra_WMS_trimmed);
                portletPreferences.setValue("gilda_astra_ETOKENSERVER", gilda_astra_ETOKENSERVER.trim());
                portletPreferences.setValue("gilda_astra_MYPROXYSERVER", gilda_astra_MYPROXYSERVER.trim());
                portletPreferences.setValue("gilda_astra_PORT", gilda_astra_PORT.trim());
                portletPreferences.setValue("gilda_astra_ROBOTID", gilda_astra_ROBOTID.trim());
                portletPreferences.setValue("gilda_astra_ROLE", gilda_astra_ROLE.trim());
                portletPreferences.setValue("gilda_astra_RENEWAL", gilda_astra_RENEWAL);
                portletPreferences.setValue("gilda_astra_DISABLEVOMS", gilda_astra_DISABLEVOMS); 
                
                portletPreferences.setValue("astra_APPID", astra_APPID.trim());
                portletPreferences.setValue("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
                portletPreferences.setValue("astra_SOFTWARE", astra_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                    + "\n- Storing the ASTRA portlet preferences ..."                    
                    + "\n\ngilda_astra_INFRASTRUCTURE: " + gilda_astra_INFRASTRUCTURE
                    + "\ngilda_astra_VONAME: " + gilda_astra_VONAME
                    + "\ngilda_astra_TOPBDII: " + gilda_astra_TOPBDII                    
                    + "\ngilda_astra_ETOKENSERVER: " + gilda_astra_ETOKENSERVER
                    + "\ngilda_astra_MYPROXYSERVER: " + gilda_astra_MYPROXYSERVER
                    + "\ngilda_astra_PORT: " + gilda_astra_PORT
                    + "\ngilda_astra_ROBOTID: " + gilda_astra_ROBOTID
                    + "\ngilda_astra_ROLE: " + gilda_astra_ROLE
                    + "\ngilda_astra_RENEWAL: " + gilda_astra_RENEWAL
                    + "\ngilda_astra_DISABLEVOMS: " + gilda_astra_DISABLEVOMS

                    + "\n\nastra_ENABLEINFRASTRUCTURE: " + "gilda"
                    + "\nastra_APPID: " + astra_APPID
                    + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                    + "\nastra_SOFTWARE: " + astra_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
                
            if (eumed_astra_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[4]="eumed";
                // Get the ASTRA INFRASTRUCTURE from the portlet request for the EUMED VO
                String eumed_astra_INFRASTRUCTURE = request.getParameter("eumed_astra_INFRASTRUCTURE");
                // Get the ASTRA VONAME from the portlet request for the EUMED VO
                String eumed_astra_VONAME = request.getParameter("eumed_astra_VONAME");
                // Get the ASTRA TOPBDII from the portlet request for the EUMED VO
                String eumed_astra_TOPBDII = request.getParameter("eumed_astra_TOPBDII");
                // Get the ASTRA WMS from the portlet request for the EUMED VO
                String[] eumed_astra_WMS = request.getParameterValues("eumed_astra_WMS");
                // Get the ASTRA ETOKENSERVER from the portlet request for the EUMED VO
                String eumed_astra_ETOKENSERVER = request.getParameter("eumed_astra_ETOKENSERVER");
                // Get the ASTRA MYPROXYSERVER from the portlet request for the EUMED VO
                String eumed_astra_MYPROXYSERVER = request.getParameter("eumed_astra_MYPROXYSERVER");
                // Get the ASTRA PORT from the portlet request for the EUMED VO
                String eumed_astra_PORT = request.getParameter("eumed_astra_PORT");
                // Get the ASTRA ROBOTID from the portlet request for the EUMED VO
                String eumed_astra_ROBOTID = request.getParameter("eumed_astra_ROBOTID");
                // Get the ASTRA ROLE from the portlet request for the EUMED VO
                String eumed_astra_ROLE = request.getParameter("eumed_astra_ROLE");
                // Get the ASTRA OPTIONS from the portlet request for the EUMED VO
                String[] eumed_astra_OPTIONS = request.getParameterValues("eumed_astra_OPTIONS");

                String eumed_astra_RENEWAL = "";
                String eumed_astra_DISABLEVOMS = "";

                if (eumed_astra_OPTIONS == null){
                    eumed_astra_RENEWAL = "checked";
                    eumed_astra_DISABLEVOMS = "unchecked";
                } else {
                    Arrays.sort(eumed_astra_OPTIONS);
                    // Get the ASTRA RENEWAL from the portlet preferences for the EUMED VO
                    eumed_astra_RENEWAL = Arrays.binarySearch(eumed_astra_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                    // Get the ASTRA DISABLEVOMS from the portlet preferences for the GRIDIT VO
                    eumed_astra_DISABLEVOMS = Arrays.binarySearch(eumed_astra_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                
                int nmax=0;                
                for (int i = 0; i < eumed_astra_WMS.length; i++)
                    if ( eumed_astra_WMS[i]!=null && (!eumed_astra_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] eumed_astra_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    eumed_astra_WMS_trimmed[i]=eumed_astra_WMS[i].trim();
                    log.info ("\n\nEUMED [" + i + "] WMS=[" + eumed_astra_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("eumed_astra_INFRASTRUCTURE", eumed_astra_INFRASTRUCTURE.trim());
                portletPreferences.setValue("eumed_astra_VONAME", eumed_astra_VONAME.trim());
                portletPreferences.setValue("eumed_astra_TOPBDII", eumed_astra_TOPBDII.trim());
                portletPreferences.setValues("eumed_astra_WMS", eumed_astra_WMS_trimmed);
                portletPreferences.setValue("eumed_astra_ETOKENSERVER", eumed_astra_ETOKENSERVER.trim());
                portletPreferences.setValue("eumed_astra_MYPROXYSERVER", eumed_astra_MYPROXYSERVER.trim());
                portletPreferences.setValue("eumed_astra_PORT", eumed_astra_PORT.trim());
                portletPreferences.setValue("eumed_astra_ROBOTID", eumed_astra_ROBOTID.trim());
                portletPreferences.setValue("eumed_astra_ROLE", eumed_astra_ROLE.trim());
                portletPreferences.setValue("eumed_astra_RENEWAL", eumed_astra_RENEWAL);
                portletPreferences.setValue("eumed_astra_DISABLEVOMS", eumed_astra_DISABLEVOMS);
                
                portletPreferences.setValue("astra_APPID", astra_APPID.trim());
                portletPreferences.setValue("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
                portletPreferences.setValue("astra_SOFTWARE", astra_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
                portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the ASTRA portlet preferences ..."
                + "\n\neumed_astra_INFRASTRUCTURE: " + eumed_astra_INFRASTRUCTURE
                + "\neumed_astra_VONAME: " + eumed_astra_VONAME
                + "\neumed_astra_TOPBDII: " + eumed_astra_TOPBDII                    
                + "\neumed_astra_ETOKENSERVER: " + eumed_astra_ETOKENSERVER
                + "\neumed_astra_MYPROXYSERVER: " + eumed_astra_MYPROXYSERVER
                + "\neumed_astra_PORT: " + eumed_astra_PORT
                + "\neumed_astra_ROBOTID: " + eumed_astra_ROBOTID
                + "\neumed_astra_ROLE: " + eumed_astra_ROLE
                + "\neumed_astra_RENEWAL: " + eumed_astra_RENEWAL
                + "\neumed_astra_DISABLEVOMS: " + eumed_astra_DISABLEVOMS
                        
                + "\n\nastra_ENABLEINFRASTRUCTURE: " + "eumed"
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
                
            if (gisela_astra_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[5]="gisela";
                // Get the ASTRA INFRASTRUCTURE from the portlet request for the GISELA VO
                String gisela_astra_INFRASTRUCTURE = request.getParameter("gisela_astra_INFRASTRUCTURE");
                // Get the ASTRA VONAME from the portlet request for the GISELA VO
                String gisela_astra_VONAME = request.getParameter("gisela_astra_VONAME");
                // Get the ASTRA TOPBDII from the portlet request for the GISELA VO
                String gisela_astra_TOPBDII = request.getParameter("gisela_astra_TOPBDII");
                // Get the ASTRA WMS from the portlet request for the GISELA VO
                String[] gisela_astra_WMS = request.getParameterValues("gisela_astra_WMS");
                // Get the ASTRA ETOKENSERVER from the portlet request for the GISELA VO
                String gisela_astra_ETOKENSERVER = request.getParameter("gisela_astra_ETOKENSERVER");
                // Get the ASTRA MYPROXYSERVER from the portlet request for the GISELA VO
                String gisela_astra_MYPROXYSERVER = request.getParameter("gisela_astra_MYPROXYSERVER");
                // Get the ASTRA PORT from the portlet request for the GISELA VO
                String gisela_astra_PORT = request.getParameter("gisela_astra_PORT");
                // Get the ASTRA ROBOTID from the portlet request for the GISELA VO
                String gisela_astra_ROBOTID = request.getParameter("gisela_astra_ROBOTID");
                // Get the ASTRA ROLE from the portlet request for the GISELA VO
                String gisela_astra_ROLE = request.getParameter("gisela_astra_ROLE");
                // Get the ASTRA OPTIONS from the portlet request for the GISELA VO
                String[] gisela_astra_OPTIONS = request.getParameterValues("gisela_astra_OPTIONS");

                String gisela_astra_RENEWAL = "";
                String gisela_astra_DISABLEVOMS = "";

                if (gisela_astra_OPTIONS == null){
                        gisela_astra_RENEWAL = "checked";
                        gisela_astra_DISABLEVOMS = "unchecked";
                } else {
                        Arrays.sort(gisela_astra_OPTIONS);
                        // Get the ASTRA RENEWAL from the portlet preferences for the GISELA VO
                        gisela_astra_RENEWAL = Arrays.binarySearch(gisela_astra_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                        // Get the ASTRA DISABLEVOMS from the portlet preferences for the GISELA VO
                        gisela_astra_DISABLEVOMS = Arrays.binarySearch(gisela_astra_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                        
                int nmax=0;                
                for (int i = 0; i < gisela_astra_WMS.length; i++)
                    if ( gisela_astra_WMS[i]!=null && (!gisela_astra_WMS[i].trim().equals("N/A")) )                        
                        nmax++;                    
                
                log.info("\n\nLength="+nmax);
                String[] gisela_astra_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    gisela_astra_WMS_trimmed[i]=gisela_astra_WMS[i].trim();
                    log.info ("\n\nGISELA [" + i + "] WMS=[" + gisela_astra_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("gisela_astra_INFRASTRUCTURE", gisela_astra_INFRASTRUCTURE.trim());
                portletPreferences.setValue("gisela_astra_VONAME", gisela_astra_VONAME.trim());
                portletPreferences.setValue("gisela_astra_TOPBDII", gisela_astra_TOPBDII.trim());
                portletPreferences.setValues("gisela_astra_WMS", gisela_astra_WMS_trimmed);
                portletPreferences.setValue("gisela_astra_ETOKENSERVER", gisela_astra_ETOKENSERVER.trim());
                portletPreferences.setValue("gisela_astra_MYPROXYSERVER", gisela_astra_MYPROXYSERVER.trim());
                portletPreferences.setValue("gisela_astra_PORT", gisela_astra_PORT.trim());
                portletPreferences.setValue("gisela_astra_ROBOTID", gisela_astra_ROBOTID.trim());
                portletPreferences.setValue("gisela_astra_ROLE", gisela_astra_ROLE.trim());
                portletPreferences.setValue("gisela_astra_RENEWAL", gisela_astra_RENEWAL);
                portletPreferences.setValue("gisela_astra_DISABLEVOMS", gisela_astra_DISABLEVOMS);

                portletPreferences.setValue("astra_APPID", astra_APPID.trim());
                portletPreferences.setValue("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
                portletPreferences.setValue("astra_SOFTWARE", astra_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
		portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the ASTRA portlet preferences ..."
                + "\n\ngisela_astra_INFRASTRUCTURE: " + gisela_astra_INFRASTRUCTURE
                + "\ngisela_astra_VONAME: " + gisela_astra_VONAME
                + "\ngisela_astra_TOPBDII: " + gisela_astra_TOPBDII                    
                + "\ngisela_astra_ETOKENSERVER: " + gisela_astra_ETOKENSERVER
                + "\ngisela_astra_MYPROXYSERVER: " + gisela_astra_MYPROXYSERVER
                + "\ngisela_astra_PORT: " + gisela_astra_PORT
                + "\ngisela_astra_ROBOTID: " + gisela_astra_ROBOTID
                + "\ngisela_astra_ROLE: " + gisela_astra_ROLE
                + "\ngisela_astra_RENEWAL: " + gisela_astra_RENEWAL
                + "\ngisela_astra_DISABLEVOMS: " + gisela_astra_DISABLEVOMS

                + "\n\nastra_ENABLEINFRASTRUCTURE: " + "gisela"
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
            
            if (sagrid_astra_ENABLEINFRASTRUCTURE.equals("checked"))
            {
                infras[6]="sagrid";
                // Get the ASTRA INFRASTRUCTURE from the portlet request for the SAGRID VO
                String sagrid_astra_INFRASTRUCTURE = request.getParameter("sagrid_astra_INFRASTRUCTURE");
                // Get the ASTRA VONAME from the portlet request for the SAGRID VO
                String sagrid_astra_VONAME = request.getParameter("sagrid_astra_VONAME");
                // Get the ASTRA TOPBDII from the portlet request for the SAGRID VO
                String sagrid_astra_TOPBDII = request.getParameter("sagrid_astra_TOPBDII");
                // Get the ASTRA WMS from the portlet request for the SAGRID VO
                String[] sagrid_astra_WMS = request.getParameterValues("sagrid_astra_WMS");
                // Get the ASTRA ETOKENSERVER from the portlet request for the SAGRID VO
                String sagrid_astra_ETOKENSERVER = request.getParameter("sagrid_astra_ETOKENSERVER");
                // Get the ASTRA MYPROXYSERVER from the portlet request for the SAGRID VO
                String sagrid_astra_MYPROXYSERVER = request.getParameter("sagrid_astra_MYPROXYSERVER");
                // Get the ASTRA PORT from the portlet request for the SAGRID VO
                String sagrid_astra_PORT = request.getParameter("sagrid_astra_PORT");
                // Get the ASTRA ROBOTID from the portlet request for the SAGRID VO
                String sagrid_astra_ROBOTID = request.getParameter("sagrid_astra_ROBOTID");
                // Get the ASTRA ROLE from the portlet request for the SAGRID VO
                String sagrid_astra_ROLE = request.getParameter("sagrid_astra_ROLE");
                // Get the ASTRA OPTIONS from the portlet request for the SAGRID VO
                String[] sagrid_astra_OPTIONS = request.getParameterValues("sagrid_astra_OPTIONS");

                String sagrid_astra_RENEWAL = "";
                String sagrid_astra_DISABLEVOMS = "";

                if (sagrid_astra_OPTIONS == null){
                        sagrid_astra_RENEWAL = "checked";
                        sagrid_astra_DISABLEVOMS = "unchecked";
                } else {
                        Arrays.sort(sagrid_astra_OPTIONS);
                        // Get the ASTRA RENEWAL from the portlet preferences for the SAGRID VO
                        sagrid_astra_RENEWAL = Arrays.binarySearch(sagrid_astra_OPTIONS, "enableRENEWAL") >= 0 ? "checked" : "unchecked";
                        // Get the ASTRA DISABLEVOMS from the portlet preferences for the SAGRID VO
                        sagrid_astra_DISABLEVOMS = Arrays.binarySearch(sagrid_astra_OPTIONS, "disableVOMS") >= 0 ? "checked" : "unchecked";
                }
                        
                int nmax=0;                
                for (int i = 0; i < sagrid_astra_WMS.length; i++)
                    if ( sagrid_astra_WMS[i]!=null && (!sagrid_astra_WMS[i].trim().equals("N/A")) )                        
                        nmax++;
                
                log.info("\n\nLength="+nmax);
                String[] sagrid_astra_WMS_trimmed = new String[nmax];
                for (int i = 0; i < nmax; i++)
                {
                    sagrid_astra_WMS_trimmed[i]=sagrid_astra_WMS[i].trim();
                    log.info ("\n\nSAGRID [" + i + "] WMS=[" + sagrid_astra_WMS_trimmed[i] + "]");
                }
                
                // Set the portlet preferences
                portletPreferences.setValue("sagrid_astra_INFRASTRUCTURE", sagrid_astra_INFRASTRUCTURE.trim());
                portletPreferences.setValue("sagrid_astra_VONAME", sagrid_astra_VONAME.trim());
                portletPreferences.setValue("sagrid_astra_TOPBDII", sagrid_astra_TOPBDII.trim());
                portletPreferences.setValues("sagrid_astra_WMS", sagrid_astra_WMS_trimmed);
                portletPreferences.setValue("sagrid_astra_ETOKENSERVER", sagrid_astra_ETOKENSERVER.trim());
                portletPreferences.setValue("sagrid_astra_MYPROXYSERVER", sagrid_astra_MYPROXYSERVER.trim());
                portletPreferences.setValue("sagrid_astra_PORT", sagrid_astra_PORT.trim());
                portletPreferences.setValue("sagrid_astra_ROBOTID", sagrid_astra_ROBOTID.trim());
                portletPreferences.setValue("sagrid_astra_ROLE", sagrid_astra_ROLE.trim());
                portletPreferences.setValue("sagrid_astra_RENEWAL", sagrid_astra_RENEWAL);
                portletPreferences.setValue("sagrid_astra_DISABLEVOMS", sagrid_astra_DISABLEVOMS);

                portletPreferences.setValue("astra_APPID", astra_APPID.trim());
                portletPreferences.setValue("astra_OUTPUT_PATH", astra_OUTPUT_PATH.trim());
                portletPreferences.setValue("astra_SOFTWARE", astra_SOFTWARE.trim());
                portletPreferences.setValue("TRACKING_DB_HOSTNAME", TRACKING_DB_HOSTNAME.trim());
                portletPreferences.setValue("TRACKING_DB_USERNAME", TRACKING_DB_USERNAME.trim());
                portletPreferences.setValue("TRACKING_DB_PASSWORD", TRACKING_DB_PASSWORD.trim());
		portletPreferences.setValue("SMTP_HOST", SMTP_HOST.trim());
                portletPreferences.setValue("SENDER_MAIL", SENDER_MAIL.trim());
                
                log.info("\n\nPROCESS ACTION => " + action
                + "\n- Storing the ASTRA portlet preferences ..."
                + "\n\nsagrid_astra_INFRASTRUCTURE: " + sagrid_astra_INFRASTRUCTURE
                + "\nsagrid_astra_VONAME: " + sagrid_astra_VONAME
                + "\nsagrid_astra_TOPBDII: " + sagrid_astra_TOPBDII                    
                + "\nsagrid_astra_ETOKENSERVER: " + sagrid_astra_ETOKENSERVER
                + "\nsagrid_astra_MYPROXYSERVER: " + sagrid_astra_MYPROXYSERVER
                + "\nsagrid_astra_PORT: " + sagrid_astra_PORT
                + "\nsagrid_astra_ROBOTID: " + sagrid_astra_ROBOTID
                + "\nsagrid_astra_ROLE: " + sagrid_astra_ROLE
                + "\nsagrid_astra_RENEWAL: " + sagrid_astra_RENEWAL
                + "\nsagrid_astra_DISABLEVOMS: " + sagrid_astra_DISABLEVOMS

                + "\n\nastra_ENABLEINFRASTRUCTURE: " + "sagrid"
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
            }
           
            for (int i=0; i<infras.length; i++)
            log.info("\n - Infrastructure Enabled = " + infras[i]);
            portletPreferences.setValues("astra_ENABLEINFRASTRUCTURE", infras);
            portletPreferences.setValue("cometa_astra_ENABLEINFRASTRUCTURE",infras[0]);
            portletPreferences.setValue("garuda_astra_ENABLEINFRASTRUCTURE",infras[1]);
            portletPreferences.setValue("gridit_astra_ENABLEINFRASTRUCTURE",infras[2]);
            portletPreferences.setValue("gilda_astra_ENABLEINFRASTRUCTURE",infras[3]);
            portletPreferences.setValue("eumed_astra_ENABLEINFRASTRUCTURE",infras[4]);
            portletPreferences.setValue("gisela_astra_ENABLEINFRASTRUCTURE",infras[5]);
            portletPreferences.setValue("sagrid_astra_ENABLEINFRASTRUCTURE",infras[6]);

            portletPreferences.store();
            response.setPortletMode(PortletMode.VIEW);
        } // end PROCESS ACTION [ CONFIG_ASTRA_PORTLET ]
        

        if (action.equals("SUBMIT_ASTRA_PORTLET")) {
            log.info("\nPROCESS ACTION => " + action);            
            InfrastructureInfo infrastructures[] = new InfrastructureInfo[7];
            int NMAX=0;                        
                       
            // Get the ASTRA APPID from the portlet preferences
            String astra_APPID = portletPreferences.getValue("astra_APPID", "N/A");
            // Get the ASTRA_OUTPUT_PATH from the portlet preferences
            String astra_OUTPUT_PATH = portletPreferences.getValue("astra_OUTPUT_PATH", "/tmp");
            // Get the ASTRA SOFTWARE from the portlet preferences
            String astra_SOFTWARE = portletPreferences.getValue("astra_SOFTWARE", "N/A");
            // Get the TRACKING_DB_HOSTNAME from the portlet request
            String TRACKING_DB_HOSTNAME = portletPreferences.getValue("TRACKING_DB_HOSTNAME", "N/A");
            // Get the TRACKING_DB_USERNAME from the portlet request
            String TRACKING_DB_USERNAME = portletPreferences.getValue("TRACKING_DB_USERNAME", "N/A");
            // Get the TRACKING_DB_PASSWORD from the portlet request
            String TRACKING_DB_PASSWORD = portletPreferences.getValue("TRACKING_DB_PASSWORD","N/A");
	    // Get the SMTP_HOST from the portlet request
	    String SMTP_HOST = portletPreferences.getValue("SMTP_HOST","N/A");
	    // Get the SENDER_MAIL from the portlet request
	    String SENDER_MAIL = portletPreferences.getValue("SENDER_MAIL","N/A");       
            
            String cometa_astra_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("cometa_astra_ENABLEINFRASTRUCTURE","null");
            String garuda_astra_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("garuda_astra_ENABLEINFRASTRUCTURE","null");
            String gridit_astra_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gridit_astra_ENABLEINFRASTRUCTURE","null");
            String gilda_astra_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gilda_astra_ENABLEINFRASTRUCTURE","null");
            String eumed_astra_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("eumed_astra_ENABLEINFRASTRUCTURE","null");
            String gisela_astra_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("gisela_astra_ENABLEINFRASTRUCTURE","null");
            String sagrid_astra_ENABLEINFRASTRUCTURE =
                    portletPreferences.getValue("sagrid_astra_ENABLEINFRASTRUCTURE","null");
        
            if (cometa_astra_ENABLEINFRASTRUCTURE != null &&
                cometa_astra_ENABLEINFRASTRUCTURE.equals("cometa"))
            {            
                NMAX++;
                // Get the ASTRA VONAME from the portlet preferences for the COMETA VO
                String cometa_astra_INFRASTRUCTURE = portletPreferences.getValue("cometa_astra_INFRASTRUCTURE", "N/A");
                // Get the ASTRA VONAME from the portlet preferences for the COMETA VO
                String cometa_astra_VONAME = portletPreferences.getValue("cometa_astra_VONAME", "N/A");
                // Get the ASTRA TOPPBDII from the portlet preferences for the GRIDIT VO
                String cometa_astra_TOPBDII = portletPreferences.getValue("cometa_astra_TOPBDII", "N/A");
                // Get the ASTRA WMS from the portlet preferences for the COMETA VO            
                String[] cometa_astra_WMS = portletPreferences.getValues("cometa_astra_WMS", new String[5]);
                // Get the ASTRA ETOKENSERVER from the portlet preferences for the COMETA VO
                String cometa_astra_ETOKENSERVER = portletPreferences.getValue("cometa_astra_ETOKENSERVER", "N/A");
                // Get the ASTRA MYPROXYSERVER from the portlet preferences for the COMETA VO
                String cometa_astra_MYPROXYSERVER = portletPreferences.getValue("cometa_astra_MYPROXYSERVER", "N/A");
                // Get the ASTRA PORT from the portlet preferences for the COMETA VO
                String cometa_astra_PORT = portletPreferences.getValue("cometa_astra_PORT", "N/A");
                // Get the ASTRA ROBOTID from the portlet preferences for the COMETA VO
                String cometa_astra_ROBOTID = portletPreferences.getValue("cometa_astra_ROBOTID", "N/A");
                // Get the ASTRA ROLE from the portlet preferences for the COMETA VO
                String cometa_astra_ROLE = portletPreferences.getValue("cometa_astra_ROLE", "N/A");
                // Get the ASTRA RENEWAL from the portlet preferences for the COMETA VO
                String cometa_astra_RENEWAL = portletPreferences.getValue("cometa_astra_RENEWAL", "checked");
                // Get the ASTRA DISABLEVOMS from the portlet preferences for the COMETA VO
                String cometa_astra_DISABLEVOMS = portletPreferences.getValue("cometa_astra_DISABLEVOMS", "unchecked");
                // Get the random CE for the ASTRA portlet               
                //RANDOM_CE = getRandomCE(cometa_astra_VONAME, cometa_astra_TOPBDII, astra_SOFTWARE);
                
                log.info("\n- Getting the ASTRA portlet preferences ..."
                + "\ncometa_astra_INFRASTRUCTURE: " + cometa_astra_INFRASTRUCTURE
                + "\ncometa_astra_VONAME: " + cometa_astra_VONAME
                + "\ncometa_astra_TOPBDII: " + cometa_astra_TOPBDII
                + "\ncometa_astra_ETOKENSERVER: " + cometa_astra_ETOKENSERVER
                + "\ncometa_astra_MYPROXYSERVER: " + cometa_astra_MYPROXYSERVER
                + "\ncometa_astra_PORT: " + cometa_astra_PORT
                + "\ncometa_astra_ROBOTID: " + cometa_astra_ROBOTID
                + "\ncometa_astra_ROLE: " + cometa_astra_ROLE
                + "\ncometa_astra_RENEWAL: " + cometa_astra_RENEWAL
                + "\ncometa_astra_DISABLEVOMS: " + cometa_astra_DISABLEVOMS
                
                + "\n\nastra_ENABLEINFRASTRUCTURE: " + cometa_astra_ENABLEINFRASTRUCTURE
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "COMETA" Infrastructure                
                int nmax=0;
                for (int i = 0; i < cometa_astra_WMS.length; i++)
                    if ((cometa_astra_WMS[i]!=null) && (!cometa_astra_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (cometa_astra_WMS[i]!=null) {
                    wmsList[i]=cometa_astra_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting for COMETA ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                    + "]");
                    }
                }

                infrastructures[0] = new InfrastructureInfo(
                    cometa_astra_VONAME,
                    cometa_astra_TOPBDII,
                    wmsList,
                    cometa_astra_ETOKENSERVER,
                    cometa_astra_PORT,
                    cometa_astra_ROBOTID,
                    cometa_astra_VONAME,
                    cometa_astra_ROLE,
                    "VO-" + cometa_astra_VONAME + "-" + astra_SOFTWARE);
            }
            
            if (garuda_astra_ENABLEINFRASTRUCTURE != null &&
                garuda_astra_ENABLEINFRASTRUCTURE.equals("garuda"))
            {
                NMAX++;
                // Get the ASTRA VONAME from the portlet preferences for the GARUDA VO
                String garuda_astra_INFRASTRUCTURE = portletPreferences.getValue("garuda_astra_INFRASTRUCTURE", "N/A");
                // Get the ASTRA VONAME from the portlet preferences for the GARUDA VO
                String garuda_astra_VONAME = portletPreferences.getValue("garuda_astra_VONAME", "N/A");
                // Get the ASTRA TOPPBDII from the portlet preferences for the GARUDA VO
                String garuda_astra_TOPBDII = portletPreferences.getValue("garuda_astra_TOPBDII", "N/A");
                // Get the ASTRA WMS from the portlet preferences for the GARUDA VO            
                String[] garuda_astra_WMS = portletPreferences.getValues("garuda_astra_WMS", new String[5]);
                // Get the ASTRA ETOKENSERVER from the portlet preferences for the GARUDA VO
                String garuda_astra_ETOKENSERVER = portletPreferences.getValue("garuda_astra_ETOKENSERVER", "N/A");
                // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GARUDA VO
                String garuda_astra_MYPROXYSERVER = portletPreferences.getValue("garuda_astra_MYPROXYSERVER", "N/A");
                // Get the ASTRA PORT from the portlet preferences for the GARUDA VO
                String garuda_astra_PORT = portletPreferences.getValue("garuda_astra_PORT", "N/A");
                // Get the ASTRA ROBOTID from the portlet preferences for the GARUDA VO
                String garuda_astra_ROBOTID = portletPreferences.getValue("garuda_astra_ROBOTID", "N/A");
                // Get the ASTRA ROLE from the portlet preferences for the GARUDA VO
                String garuda_astra_ROLE = portletPreferences.getValue("garuda_astra_ROLE", "N/A");
                // Get the ASTRA RENEWAL from the portlet preferences for the GARUDA VO
                String garuda_astra_RENEWAL = portletPreferences.getValue("garuda_astra_RENEWAL", "checked");
                // Get the ASTRA DISABLEVOMS from the portlet preferences for the GARUDA VO
                String garuda_astra_DISABLEVOMS = portletPreferences.getValue("garuda_astra_DISABLEVOMS", "unchecked");
                // Get the random CE for the ASTRA portlet               
                //RANDOM_CE = getRandomCE(garuda_astra_VONAME, garuda_astra_TOPBDII, astra_SOFTWARE);
                
                log.info("\n- Getting the ASTRA portlet preferences ..."
                + "\ngaruda_astra_INFRASTRUCTURE: " + garuda_astra_INFRASTRUCTURE
                + "\ngaruda_astra_VONAME: " + garuda_astra_VONAME
                + "\ngaruda_astra_TOPBDII: " + garuda_astra_TOPBDII                    
                + "\ngaruda_astra_ETOKENSERVER: " + garuda_astra_ETOKENSERVER
                + "\ngaruda_astra_MYPROXYSERVER: " + garuda_astra_MYPROXYSERVER
                + "\ngaruda_astra_PORT: " + garuda_astra_PORT
                + "\ngaruda_astra_ROBOTID: " + garuda_astra_ROBOTID
                + "\ngaruda_astra_ROLE: " + garuda_astra_ROLE
                + "\ngaruda_astra_RENEWAL: " + garuda_astra_RENEWAL
                + "\ngaruda_astra_DISABLEVOMS: " + garuda_astra_DISABLEVOMS
                
                + "\n\nastra_ENABLEINFRASTRUCTURE: " + garuda_astra_ENABLEINFRASTRUCTURE
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "GARUDA" Infrastructure
                int nmax=0;
                for (int i = 0; i < garuda_astra_WMS.length; i++)
                    if ((garuda_astra_WMS[i]!=null) && (!garuda_astra_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (garuda_astra_WMS[i]!=null) {
                    wmsList[i]=garuda_astra_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting for GARUDA ["
                                      + i
                                      + "] using WMSGRAM=["
                                      + wmsList[i]
                                    + "]");
                    }
                }
                
                infrastructures[1] = new InfrastructureInfo(
                    "GARUDA",
                    "wsgram",
                    "",
                    wmsList,
                    garuda_astra_ETOKENSERVER,
                    garuda_astra_PORT,
                    garuda_astra_ROBOTID,
                    garuda_astra_VONAME,
                    garuda_astra_ROLE);
            }
            
            if (gridit_astra_ENABLEINFRASTRUCTURE != null &&
                gridit_astra_ENABLEINFRASTRUCTURE.equals("gridit"))
            {
                NMAX++;
                // Get the ASTRA VONAME from the portlet preferences for the GRIDIT VO
                String gridit_astra_INFRASTRUCTURE = portletPreferences.getValue("gridit_astra_INFRASTRUCTURE", "N/A");
                // Get the ASTRA VONAME from the portlet preferences for the GRIDIT VO
                String gridit_astra_VONAME = portletPreferences.getValue("gridit_astra_VONAME", "N/A");
                // Get the ASTRA TOPPBDII from the portlet preferences for the GRIDIT VO
                String gridit_astra_TOPBDII = portletPreferences.getValue("gridit_astra_TOPBDII", "N/A");
                // Get the ASTRA WMS from the portlet preferences for the GRIDIT VO            
                String[] gridit_astra_WMS = portletPreferences.getValues("gridit_astra_WMS", new String[5]);
                // Get the ASTRA ETOKENSERVER from the portlet preferences for the GRIDIT VO
                String gridit_astra_ETOKENSERVER = portletPreferences.getValue("gridit_astra_ETOKENSERVER", "N/A");
                // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GRIDIT VO
                String gridit_astra_MYPROXYSERVER = portletPreferences.getValue("gridit_astra_MYPROXYSERVER", "N/A");
                // Get the ASTRA PORT from the portlet preferences for the GRIDIT VO
                String gridit_astra_PORT = portletPreferences.getValue("gridit_astra_PORT", "N/A");
                // Get the ASTRA ROBOTID from the portlet preferences for the GRIDIT VO
                String gridit_astra_ROBOTID = portletPreferences.getValue("gridit_astra_ROBOTID", "N/A");
                // Get the ASTRA ROLE from the portlet preferences for the GRIDIT VO
                String gridit_astra_ROLE = portletPreferences.getValue("gridit_astra_ROLE", "N/A");
                // Get the ASTRA RENEWAL from the portlet preferences for the GRIDIT VO
                String gridit_astra_RENEWAL = portletPreferences.getValue("gridit_astra_RENEWAL", "checked");
                // Get the ASTRA DISABLEVOMS from the portlet preferences for the GRIDIT VO
                String gridit_astra_DISABLEVOMS = portletPreferences.getValue("gridit_astra_DISABLEVOMS", "unchecked");
                // Get the random CE for the ASTRA portlet               
                //RANDOM_CE = getRandomCE(gridit_astra_VONAME, gridit_astra_TOPBDII, astra_SOFTWARE);
                
                log.info("\n- Getting the ASTRA portlet preferences ..."
                + "\ngridit_astra_INFRASTRUCTURE: " + gridit_astra_INFRASTRUCTURE
                + "\ngridit_astra_VONAME: " + gridit_astra_VONAME
                + "\ngridit_astra_TOPBDII: " + gridit_astra_TOPBDII                    
                + "\ngridit_astra_ETOKENSERVER: " + gridit_astra_ETOKENSERVER
                + "\ngridit_astra_MYPROXYSERVER: " + gridit_astra_MYPROXYSERVER
                + "\ngridit_astra_PORT: " + gridit_astra_PORT
                + "\ngridit_astra_ROBOTID: " + gridit_astra_ROBOTID
                + "\ngridit_astra_ROLE: " + gridit_astra_ROLE
                + "\ngridit_astra_RENEWAL: " + gridit_astra_RENEWAL
                + "\ngridit_astra_DISABLEVOMS: " + gridit_astra_DISABLEVOMS
                
                + "\n\nastra_ENABLEINFRASTRUCTURE: " + gridit_astra_ENABLEINFRASTRUCTURE
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "GRIDIT" Infrastructure
                int nmax=0;
                for (int i = 0; i < gridit_astra_WMS.length; i++)
                    if ((gridit_astra_WMS[i]!=null) && (!gridit_astra_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (gridit_astra_WMS[i]!=null) {                    
                    wmsList[i]=gridit_astra_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting for GRIDIT ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                    + "]");
                    }
                }

                infrastructures[2] = new InfrastructureInfo(
                    gridit_astra_VONAME,
                    gridit_astra_TOPBDII,
                    wmsList,
                    gridit_astra_ETOKENSERVER,
                    gridit_astra_PORT,
                    gridit_astra_ROBOTID,
                    gridit_astra_VONAME,
                    gridit_astra_ROLE,
                    "VO-" + gridit_astra_VONAME + "-" + astra_SOFTWARE);
            }
            
            if (gilda_astra_ENABLEINFRASTRUCTURE != null &&
                gilda_astra_ENABLEINFRASTRUCTURE.equals("gilda"))
            {
                NMAX++;                
                // Get the ASTRA VONAME from the portlet preferences for the GILDA VO
                String gilda_astra_INFRASTRUCTURE = portletPreferences.getValue("gilda_astra_INFRASTRUCTURE", "N/A");
                // Get the ASTRA VONAME from the portlet preferences for the GILDA VO
                String gilda_astra_VONAME = portletPreferences.getValue("gilda_astra_VONAME", "N/A");
                // Get the ASTRA TOPPBDII from the portlet preferences for the GILDA VO
                String gilda_astra_TOPBDII = portletPreferences.getValue("gilda_astra_TOPBDII", "N/A");
                // Get the ASTRA WMS from the portlet preferences for the GILDA VO
                String[] gilda_astra_WMS = portletPreferences.getValues("gilda_astra_WMS", new String[5]);
                // Get the ASTRA ETOKENSERVER from the portlet preferences for the GILDA VO
                String gilda_astra_ETOKENSERVER = portletPreferences.getValue("gilda_astra_ETOKENSERVER", "N/A");
                // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GILDA VO
                String gilda_astra_MYPROXYSERVER = portletPreferences.getValue("gilda_astra_MYPROXYSERVER", "N/A");
                // Get the ASTRA PORT from the portlet preferences for the GILDA VO
                String gilda_astra_PORT = portletPreferences.getValue("gilda_astra_PORT", "N/A");
                // Get the ASTRA ROBOTID from the portlet preferences for the GILDA VO
                String gilda_astra_ROBOTID = portletPreferences.getValue("gilda_astra_ROBOTID", "N/A");
                // Get the ASTRA ROLE from the portlet preferences for the GILDA VO
                String gilda_astra_ROLE = portletPreferences.getValue("gilda_astra_ROLE", "N/A");
                // Get the ASTRA RENEWAL from the portlet preferences for the GILDA VO
                String gilda_astra_RENEWAL = portletPreferences.getValue("gilda_astra_RENEWAL", "checked");
                // Get the ASTRA DISABLEVOMS from the portlet preferences for the GILDA VO
                String gilda_astra_DISABLEVOMS = portletPreferences.getValue("gilda_astra_DISABLEVOMS", "unchecked");
                // Get the random CE for the ASTRA portlet               
                //RANDOM_CE = getRandomCE(gilda_astra_VONAME, gilda_astra_TOPBDII, astra_SOFTWARE);
                
                log.info("\n- Getting the ASTRA portlet preferences ..."
                    + "\n\ngilda_astra_INFRASTRUCTURE: " + gilda_astra_INFRASTRUCTURE
                    + "\ngilda_astra_VONAME: " + gilda_astra_VONAME
                    + "\ngilda_astra_TOPBDII: " + gilda_astra_TOPBDII                    
                    + "\ngilda_astra_ETOKENSERVER: " + gilda_astra_ETOKENSERVER
                    + "\ngilda_astra_MYPROXYSERVER: " + gilda_astra_MYPROXYSERVER
                    + "\ngilda_astra_PORT: " + gilda_astra_PORT
                    + "\ngilda_astra_ROBOTID: " + gilda_astra_ROBOTID
                    + "\ngilda_astra_ROLE: " + gilda_astra_ROLE
                    + "\ngilda_astra_RENEWAL: " + gilda_astra_RENEWAL
                    + "\ngilda_astra_DISABLEVOMS: " + gilda_astra_DISABLEVOMS

                    + "\n\nastra_ENABLEINFRASTRUCTURE: " + eumed_astra_ENABLEINFRASTRUCTURE
                    + "\nastra_APPID: " + astra_APPID
                    + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                    + "\nastra_SOFTWARE: " + astra_SOFTWARE
                    + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                    + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                    + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
                    + "\nSMTP_HOST: " + SMTP_HOST
                    + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "GILDA" Infrastructure
                int nmax=0;
                for (int i = 0; i < gilda_astra_WMS.length; i++)
                    if ((gilda_astra_WMS[i]!=null) && (!gilda_astra_WMS[i].equals("N/A"))) nmax++;
                
                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (gilda_astra_WMS[i]!=null) {
                    wmsList[i]=gilda_astra_WMS[i].trim();
                    log.info ("\n\n[" + nmax
                                      + "] Submitting to GILDA ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[3] = new InfrastructureInfo(
                    gilda_astra_VONAME,
                    gilda_astra_TOPBDII,
                    wmsList,
                    gilda_astra_ETOKENSERVER,
                    gilda_astra_PORT,
                    gilda_astra_ROBOTID,
                    gilda_astra_VONAME,
                    gilda_astra_ROLE,
                    "VO-" + gilda_astra_VONAME + "-" + astra_SOFTWARE);
            }
                
            if (eumed_astra_ENABLEINFRASTRUCTURE != null &&
                eumed_astra_ENABLEINFRASTRUCTURE.equals("eumed"))
            {
                NMAX++;
                // Get the ASTRA VONAME from the portlet preferences for the EUMED VO
                String eumed_astra_INFRASTRUCTURE = portletPreferences.getValue("eumed_astra_INFRASTRUCTURE", "N/A");
                // Get the ASTRA VONAME from the portlet preferences for the EUMED VO
                String eumed_astra_VONAME = portletPreferences.getValue("eumed_astra_VONAME", "N/A");
                // Get the ASTRA TOPPBDII from the portlet preferences for the EUMED VO
                String eumed_astra_TOPBDII = portletPreferences.getValue("eumed_astra_TOPBDII", "N/A");
                // Get the ASTRA WMS from the portlet preferences for the EUMED VO
                String[] eumed_astra_WMS = portletPreferences.getValues("eumed_astra_WMS", new String[5]);
                // Get the ASTRA ETOKENSERVER from the portlet preferences for the EUMED VO
                String eumed_astra_ETOKENSERVER = portletPreferences.getValue("eumed_astra_ETOKENSERVER", "N/A");
                // Get the ASTRA MYPROXYSERVER from the portlet preferences for the EUMED VO
                String eumed_astra_MYPROXYSERVER = portletPreferences.getValue("eumed_astra_MYPROXYSERVER", "N/A");
                // Get the ASTRA PORT from the portlet preferences for the EUMED VO
                String eumed_astra_PORT = portletPreferences.getValue("eumed_astra_PORT", "N/A");
                // Get the ASTRA ROBOTID from the portlet preferences for the EUMED VO
                String eumed_astra_ROBOTID = portletPreferences.getValue("eumed_astra_ROBOTID", "N/A");
                // Get the ASTRA ROLE from the portlet preferences for the EUMED VO
                String eumed_astra_ROLE = portletPreferences.getValue("eumed_astra_ROLE", "N/A");
                // Get the ASTRA RENEWAL from the portlet preferences for the EUMED VO
                String eumed_astra_RENEWAL = portletPreferences.getValue("eumed_astra_RENEWAL", "checked");
                // Get the ASTRA DISABLEVOMS from the portlet preferences for the EUMED VO
                String eumed_astra_DISABLEVOMS = portletPreferences.getValue("eumed_astra_DISABLEVOMS", "unchecked");
                // Get the random CE for the ASTRA portlet               
                //RANDOM_CE = getRandomCE(eumed_astra_VONAME, eumed_astra_TOPBDII, astra_SOFTWARE);
                
                log.info("\n- Getting the ASTRA portlet preferences ..."
                + "\n\neumed_astra_INFRASTRUCTURE: " + eumed_astra_INFRASTRUCTURE
                + "\neumed_astra_VONAME: " + eumed_astra_VONAME
                + "\neumed_astra_TOPBDII: " + eumed_astra_TOPBDII                    
                + "\neumed_astra_ETOKENSERVER: " + eumed_astra_ETOKENSERVER
                + "\neumed_astra_MYPROXYSERVER: " + eumed_astra_MYPROXYSERVER
                + "\neumed_astra_PORT: " + eumed_astra_PORT
                + "\neumed_astra_ROBOTID: " + eumed_astra_ROBOTID
                + "\neumed_astra_ROLE: " + eumed_astra_ROLE
                + "\neumed_astra_RENEWAL: " + eumed_astra_RENEWAL
                + "\neumed_astra_DISABLEVOMS: " + eumed_astra_DISABLEVOMS
                       
                + "\n\nastra_ENABLEINFRASTRUCTURE: " + eumed_astra_ENABLEINFRASTRUCTURE
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "EUMED" Infrastructure
                int nmax=0;
                for (int i = 0; i < eumed_astra_WMS.length; i++)
                    if ((eumed_astra_WMS[i]!=null) && (!eumed_astra_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (eumed_astra_WMS[i]!=null) {
                    wmsList[i]=eumed_astra_WMS[i].trim();
		    log.info ("\n\n[" + nmax
                                      + "] Submitting to EUMED ["
                                      + i
                                      + "] using WMS=["
                                      + wmsList[i]
                                      + "]");
                    }
                }

                infrastructures[4] = new InfrastructureInfo(
                    eumed_astra_VONAME,
                    eumed_astra_TOPBDII,
                    wmsList,
                    eumed_astra_ETOKENSERVER,
                    eumed_astra_PORT,
                    eumed_astra_ROBOTID,
                    eumed_astra_VONAME,
                    eumed_astra_ROLE,
                    "VO-" + eumed_astra_VONAME + "-" + astra_SOFTWARE);
            }
            
            if (gisela_astra_ENABLEINFRASTRUCTURE != null &&
                gisela_astra_ENABLEINFRASTRUCTURE.equals("gisela")) 
            {            
                NMAX++;
                // Get the ASTRA VONAME from the portlet preferences for the GISELA VO
                String gisela_astra_INFRASTRUCTURE = portletPreferences.getValue("gisela_astra_INFRASTRUCTURE", "N/A");
                // Get the ASTRA VONAME from the portlet preferences for the GISELA VO
                String gisela_astra_VONAME = portletPreferences.getValue("gisela_astra_VONAME", "N/A");
                // Get the ASTRA TOPPBDII from the portlet preferences for the GISELA VO
                String gisela_astra_TOPBDII = portletPreferences.getValue("gisela_astra_TOPBDII", "N/A");
                // Get the ASTRA WMS from the portlet preferences for the GISELA VO
                String[] gisela_astra_WMS = portletPreferences.getValues("gisela_astra_WMS", new String[5]);
                // Get the ASTRA ETOKENSERVER from the portlet preferences for the GISELA VO
                String gisela_astra_ETOKENSERVER = portletPreferences.getValue("gisela_astra_ETOKENSERVER", "N/A");
                // Get the ASTRA MYPROXYSERVER from the portlet preferences for the GISELA VO
                String gisela_astra_MYPROXYSERVER = portletPreferences.getValue("gisela_astra_MYPROXYSERVER", "N/A");
                // Get the ASTRA PORT from the portlet preferences for the GISELA VO
                String gisela_astra_PORT = portletPreferences.getValue("gisela_astra_PORT", "N/A");
                // Get the ASTRA ROBOTID from the portlet preferences for the GISELA VO
                String gisela_astra_ROBOTID = portletPreferences.getValue("gisela_astra_ROBOTID", "N/A");
                // Get the ASTRA ROLE from the portlet preferences for the GISELA VO
                String gisela_astra_ROLE = portletPreferences.getValue("gisela_astra_ROLE", "N/A");
                // Get the ASTRA RENEWAL from the portlet preferences for the GISELA VO
                String gisela_astra_RENEWAL = portletPreferences.getValue("gisela_astra_RENEWAL", "checked");
                // Get the ASTRA DISABLEVOMS from the portlet preferences for the GISELA VO
                String gisela_astra_DISABLEVOMS = portletPreferences.getValue("gisela_astra_DISABLEVOMS", "unchecked");
                
		// Get the random CE for the ASTRA portlet               
                //RANDOM_CE = getRandomCE(gisela_astra_VONAME, gisela_astra_TOPBDII, astra_SOFTWARE);
                                        
                log.info("\n- Getting the ASTRA portlet preferences ..."
                + "\n\ngisela_astra_INFRASTRUCTURE: " + gisela_astra_INFRASTRUCTURE
                + "\ngisela_astra_VONAME: " + gisela_astra_VONAME
                + "\ngisela_astra_TOPBDII: " + gisela_astra_TOPBDII                    
                + "\ngisela_astra_ETOKENSERVER: " + gisela_astra_ETOKENSERVER
                + "\ngisela_astra_MYPROXYSERVER: " + gisela_astra_MYPROXYSERVER
                + "\ngisela_astra_PORT: " + gisela_astra_PORT
                + "\ngisela_astra_ROBOTID: " + gisela_astra_ROBOTID
                + "\ngisela_astra_ROLE: " + gisela_astra_ROLE
                + "\ngisela_astra_RENEWAL: " + gisela_astra_RENEWAL
                + "\ngisela_astra_DISABLEVOMS: " + gisela_astra_DISABLEVOMS

                + "\n\nastra_ENABLEINFRASTRUCTURE: " + gisela_astra_ENABLEINFRASTRUCTURE
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH                        
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "GISELA" Infrastructure
                int nmax=0;
                for (int i = 0; i < gisela_astra_WMS.length; i++)
                    if ((gisela_astra_WMS[i]!=null) && (!gisela_astra_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (gisela_astra_WMS[i]!=null) {
                    wmsList[i]=gisela_astra_WMS[i].trim();
                    log.info ("\n\nSubmitting for GISELA [" + i + "] using WMS=[" + wmsList[i] + "]");
                    }
                }

                infrastructures[5] = new InfrastructureInfo(
                    gisela_astra_VONAME,
                    gisela_astra_TOPBDII,
                    wmsList,
                    gisela_astra_ETOKENSERVER,
                    gisela_astra_PORT,
                    gisela_astra_ROBOTID,
                    gisela_astra_VONAME,
                    gisela_astra_ROLE,
                    "VO-" + gisela_astra_VONAME + "-" + astra_SOFTWARE);
            }
            
            if (sagrid_astra_ENABLEINFRASTRUCTURE != null &&
                sagrid_astra_ENABLEINFRASTRUCTURE.equals("sagrid")) 
            {            
                NMAX++;
                // Get the ASTRA VONAME from the portlet preferences for the SAGRID VO
                String sagrid_astra_INFRASTRUCTURE = portletPreferences.getValue("sagrid_astra_INFRASTRUCTURE", "N/A");
                // Get the ASTRA VONAME from the portlet preferences for the SAGRID VO
                String sagrid_astra_VONAME = portletPreferences.getValue("sagrid_astra_VONAME", "N/A");
                // Get the ASTRA TOPPBDII from the portlet preferences for the SAGRID VO
                String sagrid_astra_TOPBDII = portletPreferences.getValue("sagrid_astra_TOPBDII", "N/A");
                // Get the ASTRA WMS from the portlet preferences for the SAGRID VO
                String[] sagrid_astra_WMS = portletPreferences.getValues("sagrid_astra_WMS", new String[5]);
                // Get the ASTRA ETOKENSERVER from the portlet preferences for the SAGRID VO
                String sagrid_astra_ETOKENSERVER = portletPreferences.getValue("sagrid_astra_ETOKENSERVER", "N/A");
                // Get the ASTRA MYPROXYSERVER from the portlet preferences for the SAGRID VO
                String sagrid_astra_MYPROXYSERVER = portletPreferences.getValue("sagrid_astra_MYPROXYSERVER", "N/A");
                // Get the ASTRA PORT from the portlet preferences for the SAGRID VO
                String sagrid_astra_PORT = portletPreferences.getValue("sagrid_astra_PORT", "N/A");
                // Get the ASTRA ROBOTID from the portlet preferences for the SAGRID VO
                String sagrid_astra_ROBOTID = portletPreferences.getValue("sagrid_astra_ROBOTID", "N/A");
                // Get the ASTRA ROLE from the portlet preferences for the SAGRID VO
                String sagrid_astra_ROLE = portletPreferences.getValue("sagrid_astra_ROLE", "N/A");
                // Get the ASTRA RENEWAL from the portlet preferences for the SAGRID VO
                String sagrid_astra_RENEWAL = portletPreferences.getValue("sagrid_astra_RENEWAL", "checked");
                // Get the ASTRA DISABLEVOMS from the portlet preferences for the SAGRID VO
                String sagrid_astra_DISABLEVOMS = portletPreferences.getValue("sagrid_astra_DISABLEVOMS", "unchecked");
                
		// Get the random CE for the ASTRA portlet               
                //RANDOM_CE = getRandomCE(sagrid_astra_VONAME, sagrid_astra_TOPBDII, astra_SOFTWARE);
                                        
                log.info("\n- Getting the ASTRA portlet preferences ..."
                + "\n\nsagrid_astra_INFRASTRUCTURE: " + sagrid_astra_INFRASTRUCTURE
                + "\nsagrid_astra_VONAME: " + sagrid_astra_VONAME
                + "\nsagrid_astra_TOPBDII: " + sagrid_astra_TOPBDII                    
                + "\nsagrid_astra_ETOKENSERVER: " + sagrid_astra_ETOKENSERVER
                + "\nsagrid_astra_MYPROXYSERVER: " + sagrid_astra_MYPROXYSERVER
                + "\nsagrid_astra_PORT: " + sagrid_astra_PORT
                + "\nsagrid_astra_ROBOTID: " + sagrid_astra_ROBOTID
                + "\nsagrid_astra_ROLE: " + sagrid_astra_ROLE
                + "\nsagrid_astra_RENEWAL: " + sagrid_astra_RENEWAL
                + "\nsagrid_astra_DISABLEVOMS: " + sagrid_astra_DISABLEVOMS

                + "\n\nastra_ENABLEINFRASTRUCTURE: " + sagrid_astra_ENABLEINFRASTRUCTURE
                + "\nastra_APPID: " + astra_APPID
                + "\nastra_OUTPUT_PATH: " + astra_OUTPUT_PATH
                + "\nastra_SOFTWARE: " + astra_SOFTWARE
                + "\nTracking_DB_Hostname: " + TRACKING_DB_HOSTNAME
                + "\nTracking_DB_Username: " + TRACKING_DB_USERNAME
                + "\nTracking_DB_Password: " + TRACKING_DB_PASSWORD
		+ "\nSMTP_HOST: " + SMTP_HOST
                + "\nSENDER_MAIL: " + SENDER_MAIL);
                
                // Defining the WMS list for the "SAGRID" Infrastructure
                int nmax=0;
                for (int i = 0; i < sagrid_astra_WMS.length; i++)
                    if ((sagrid_astra_WMS[i]!=null) && (!sagrid_astra_WMS[i].equals("N/A"))) nmax++;

                String wmsList[] = new String [nmax];
                for (int i = 0; i < nmax; i++)
                {
                    if (sagrid_astra_WMS[i]!=null) {
                    wmsList[i]=sagrid_astra_WMS[i].trim();
                    log.info ("\n\nSubmitting for SAGRID [" + i + "] using WMS=[" + wmsList[i] + "]");
                    }
                }

                infrastructures[6] = new InfrastructureInfo(
                    sagrid_astra_VONAME,
                    sagrid_astra_TOPBDII,
                    wmsList,
                    sagrid_astra_ETOKENSERVER,
                    sagrid_astra_PORT,
                    sagrid_astra_ROBOTID,
                    sagrid_astra_VONAME,
                    sagrid_astra_ROLE,
                    "VO-" + sagrid_astra_VONAME + "-" + astra_SOFTWARE);
            }
                        
            String[] ASTRA_Parameters = new String [8];

            // Upload the input settings for the ASTRA application
            ASTRA_Parameters = uploadAstraSettings( request, response, username );

            log.info("\n- ASTRA Input Parameters: ");
            log.info("\n- ASTRA_MIN = " + ASTRA_Parameters[0]);
            log.info("\n- ASTRA_MAX = " + ASTRA_Parameters[1]);
            log.info("\n- ASTRA_MINVELOCITY = " + ASTRA_Parameters[2]);
            log.info("\n- ASTRA_MAXVELOCITY = " + ASTRA_Parameters[3]);
            log.info("\n- ASTRA_STEPVELOCITY = " + ASTRA_Parameters[4]);
            log.info("\n- ASTRA_DEMO = " + ASTRA_Parameters[6]);
            log.info("\n- ASTRA_CE = " + ASTRA_Parameters[5]);
            log.info("\n- Enable Notification = " + ASTRA_Parameters[7]);
            log.info("\n- Description = " + ASTRA_Parameters[8]);
            
            //=============================================================
            // IMPORTANT: INSTANCIATE THE MultiInfrastructureJobSubmission
            //            CLASS USING THE EMPTY CONSTRUCTOR WHEN
            //            WHEN THE PORTLET IS DEPLOYED IN PRODUCTION!!!
            //=============================================================
            // Preparing to submit ASTRA jobs in different grid infrastructure..
            /*MultiInfrastructureJobSubmission AstraMultiJobSubmission =
            new MultiInfrastructureJobSubmission(TRACKING_DB_HOSTNAME,
                                                 TRACKING_DB_USERNAME,
                                                 TRACKING_DB_PASSWORD);*/
            
            MultiInfrastructureJobSubmission AstraMultiJobSubmission =
                new MultiInfrastructureJobSubmission();

            // Set the list of infrastructure(s) activated for the ASTRA portlet            
            if (infrastructures[0]!=null) {
                 log.info("\n- Adding the COMETA Infrastructure.");
                 AstraMultiJobSubmission.addInfrastructure(infrastructures[0]); 
            }
            if (infrastructures[1]!=null) {
                log.info("\n- Adding the GARUDA Infrastructure.");
                 AstraMultiJobSubmission.addInfrastructure(infrastructures[1]); 
            }
            if (infrastructures[2]!=null) {
                log.info("\n- Adding the GRIDIT Infrastructure.");
                 AstraMultiJobSubmission.addInfrastructure(infrastructures[2]); 
            }
            if (infrastructures[3]!=null) {
                 log.info("\n- Adding the GILDA Infrastructure.");
                 AstraMultiJobSubmission.addInfrastructure(infrastructures[3]);
            }
            if (infrastructures[4]!=null) {
                log.info("\n- Adding the EUMED Infrastructure.");
                 AstraMultiJobSubmission.addInfrastructure(infrastructures[4]);
            }
            if (infrastructures[5]!=null) {
                log.info("\n- Adding the GISELA Infrastructure.");
                 AstraMultiJobSubmission.addInfrastructure(infrastructures[5]);
            }
            if (infrastructures[6]!=null) {
                log.info("\n- Adding the SAGRID Infrastructure.");
                 AstraMultiJobSubmission.addInfrastructure(infrastructures[6]);
            }

            String AstraFilesPath = getPortletContext().getRealPath("/") +
                                    "config";

            // Set the Executable for ASTRA
            AstraMultiJobSubmission.setExecutable("AstraStk-2.2.sh");            
            
            InfrastructureInfo infrastructure = 
                    AstraMultiJobSubmission.getInfrastructure();            
            
            String Middleware = "";
            if (infrastructure.getMiddleware().equals("glite"))
                    Middleware = "glite";
            if (infrastructure.getMiddleware().equals("wsgram"))
                    Middleware = "wsgram";
                        
            log.info("\n- Enabled Middleware = " + Middleware);
                        
            String Arguments = ASTRA_Parameters[0] + "," +
                               ASTRA_Parameters[1] + "," +
                               ASTRA_Parameters[2] + "," +
                               ASTRA_Parameters[3] + "," +
                               ASTRA_Parameters[4] + "," +
                               ASTRA_Parameters[6] + "," +
                               Middleware;

            // Set the list of Arguments for ASTRA
            AstraMultiJobSubmission.setArguments(Arguments);

            // Set the Output path for ASTRA results
            //AstraMultiJobSubmission.setOutputPath("/tmp");
            AstraMultiJobSubmission.setOutputPath(astra_OUTPUT_PATH);
            
            // Set the StandardOutput for ASTRA
            AstraMultiJobSubmission.setJobOutput("AstraStk.out");

            // Set the StandardError for ASTRA
            AstraMultiJobSubmission.setJobError("AstraStk.err");

            String InputSandbox = AstraFilesPath + "/AstraStk-2.2.sh" +
                                  "," +
                                  AstraFilesPath + "/AstraStk.tar.gz" +
                                  "," +
                                  AstraFilesPath + "/generatetone.sh" +
                                  "," +
                                  AstraFilesPath + "/makeaudiosamples.sh" +
                                  "," +
                                  AstraFilesPath + "/" + ASTRA_Parameters[6];

            // Set InputSandbox files (string with comma separated list of file names)
            AstraMultiJobSubmission.setInputFiles(InputSandbox);

            // OutputSandbox (string with comma separated list of file names)
            String AstraFiles="samples.tar.gz";

            // Retrieve the WAV file name to retrieve
            String AstraWAVFile=ASTRA_Parameters[6].replace(".ski",".wav");
            
            // Retreive a README file
            String README = "output.README";

            // Set the OutputSandbox files (string with comma separated list of file names)
            AstraMultiJobSubmission.setOutputFiles(AstraFiles + 
                                                   "," + 
                                                   AstraWAVFile + 
                                                   "," + 
                                                   README);
            // Set the queue if it's defined
            // This option is not supported in multi-infrastructures mode
            if (NMAX==1) {
                if (!ASTRA_Parameters[5].isEmpty())
                    AstraMultiJobSubmission.setJobQueue(ASTRA_Parameters[5]);
                //else //AstraMultiJobSubmission.setRandomCE(true);
                    //AstraMultiJobSubmission.setJobQueue(RANDOM_CE);
            }
            
            // Adding the proper requirements to run more than 24h
            /*String jdlRequirements[] = new String[1];
            jdlRequirements[0] = "JDLRequirements=(other.GlueCEPolicyMaxCPUTime>1440)";
            AstraMultiJobSubmission.setJDLRequirements(jdlRequirements);*/

            InetAddress addr = InetAddress.getLocalHost();                        
            
            Company company;
            try {
                company = PortalUtil.getCompany(request);
                String gateway = company.getName();
                
                // Send a notification email to the user if enabled.
                if (ASTRA_Parameters[7]!=null)
                    if ( (SMTP_HOST==null) || 
                         (SMTP_HOST.trim().equals("")) ||
                         (SMTP_HOST.trim().equals("N/A")) ||
                         (SENDER_MAIL==null) || 
                         (SENDER_MAIL.trim().equals("")) ||
                         (SENDER_MAIL.trim().equals("N/A"))
                       )
                    log.info ("\nThe Notification Service is not properly configured!!");
                else {
                        
                        // Enabling Job's notification via email
                        AstraMultiJobSubmission.setUserEmail(emailAddress);
                        
                        sendHTMLEmail(username, 
                                       emailAddress, 
                                       SENDER_MAIL, 
                                       SMTP_HOST, 
                                       "ASTRA", 
                                       gateway);
                }
                
                // Submitting...
                log.info("\n- ASTRA job submittion in progress using JSAGA JobEngine");
                AstraMultiJobSubmission.submitJobAsync(
                            infrastructure,
                            username,
                            addr.getHostAddress()+":8162",
                            Integer.valueOf(astra_APPID),
                            ASTRA_Parameters[8]
                );
                
            } catch (PortalException ex) {
                Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
            }            
        } // end PROCESS ACTION [ SUBMIT_ASTRA_PORTLET ]
    }
    
    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response)
                throws PortletException, IOException
    {
        //super.serveResource(request, response);

        PortletPreferences portletPreferences = (PortletPreferences) request.getPreferences();

        final String action = (String) request.getParameter("action");

        if (action.equals("get-ratings")) {
            //Get CE Ratings from the portlet preferences
            String astra_CE = (String) request.getParameter("astra_CE");

            String json = "{ \"avg\":\"" + 
			  portletPreferences.getValue(astra_CE+"_avg", "0.0") +
	                  "\", \"cnt\":\"" + 
			  portletPreferences.getValue(astra_CE+"_cnt", "0")+ "\"}";

            response.setContentType("application/json");
            response.getPortletOutputStream().write( json.getBytes() );

        } else if (action.equals("set-ratings")) {

            String astra_CE = (String) request.getParameter("astra_CE");
            int vote = Integer.parseInt(request.getParameter("vote"));

             double avg = Double.parseDouble(portletPreferences.getValue(astra_CE+"_avg", "0.0"));
             long cnt = Long.parseLong(portletPreferences.getValue(astra_CE+"_cnt", "0"));

             portletPreferences.setValue(astra_CE+"_avg", Double.toString(((avg*cnt)+vote) / (cnt +1)));
             portletPreferences.setValue(astra_CE+"_cnt", Long.toString(cnt+1));

             portletPreferences.store();
        }
    }


    // Upload ASTRA input files
    public String[] uploadAstraSettings(ActionRequest actionRequest,
                                        ActionResponse actionResponse, String username)
    {
        String[] ASTRA_Parameters = new String [9];
        boolean status;

        // Check that we have a file upload request
        boolean isMultipart = PortletFileUpload.isMultipartContent(actionRequest);

        if (isMultipart) {
            // Create a factory for disk-based file items.
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Set factory constrains
            File ASTRA_Repository = new File ("/tmp");
            if (!ASTRA_Repository.exists()) status = ASTRA_Repository.mkdirs();
            factory.setRepository(ASTRA_Repository);

            // Create a new file upload handler.
            PortletFileUpload upload = new PortletFileUpload(factory);

            try {
                    // Parse the request
                    List items = upload.parseRequest(actionRequest);

                    // Processing items
                    Iterator iter = items.iterator();

                    while (iter.hasNext())
                    {
                        FileItem item = (FileItem) iter.next();

                        String fieldName = item.getFieldName();

                        // Processing a regular form field
                        if ( item.isFormField() )
                        {
                            if (fieldName.equals("astra_min"))
                                ASTRA_Parameters[0]=item.getString();

                            if (fieldName.equals("astra_max"))
                                ASTRA_Parameters[1]=item.getString();

                            if (fieldName.equals("astra_minvelocity"))
                                ASTRA_Parameters[2]=item.getString();

                            if (fieldName.equals("astra_maxvelocity"))
                                ASTRA_Parameters[3]=item.getString();

                            if (fieldName.equals("astra_stepvelocity"))
                                ASTRA_Parameters[4]=item.getString();

                            if (fieldName.equals("astra_CE"))
                                ASTRA_Parameters[5]=item.getString();

                            if (fieldName.equals("defaultdemo"))
                                ASTRA_Parameters[6]=item.getString();

                        } else {
                            // Processing a file upload
                            if (fieldName.equals("astra_file"))
                            {
                                ASTRA_Parameters[6]=item.getName();
                                log.info("\n- Uploading the following user's file: "
                                       + "\n[ " + item.getName() + " ]"
                                       + "\n[ " + item.getContentType() + " ]"
                                       + "\n[ " + item.getSize() + "KBytes ]"
                                       );

                                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                                String timeStamp = dateFormat.format(Calendar.getInstance().getTime());

                                // Writing the file to disk
                                String uploadAstraFile = ASTRA_Repository +
                                                       "/" + timeStamp +
                                                       "_" + username +
                                                       "_" + item.getName();

                                log.info("\n- Writing the user's file: [ "
                                        + uploadAstraFile.toString()
                                        + " ] to disk");

                                item.write(new File(uploadAstraFile));
                            }
                        }
                        
                        if (fieldName.equals("EnableNotification"))
                                ASTRA_Parameters[7]=item.getString();
                        
                        if (fieldName.equals("astra_desc"))                                
                                if (item.getString().equals("Please, insert here a description for your job"))
                                    ASTRA_Parameters[8]="ASTRA Sound/Timbre Reconstruction Simulation Started";
                                else
                                    ASTRA_Parameters[8]=item.getString();
                        
                    } // end while
            } catch (FileUploadException ex) {
              Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
              Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ASTRA_Parameters;
    }

    // Retrieve a random Computing Element
    // matching the Software Tag for the ASTRA application    
    public String getRandomCE(String astra_VONAME,
                              String astra_TOPBDII,
                              String astra_SOFTWARE)
                              throws PortletException, IOException
    {
        String randomCE = null;
        BDII bdii = null;    
        List<String> CEqueues = null;
                        
        log.info("\n- Querying the Information System [ " + 
                  astra_TOPBDII + 
                  " ] and retrieving a random CE matching the SW tag [ VO-" + 
                  astra_VONAME +
                  "-" +
                  astra_SOFTWARE + " ]");  

       try {               

                bdii = new BDII( new URI(astra_TOPBDII) );
                
                // Get the list of the available queues
                CEqueues = bdii.queryCEQueues(astra_VONAME);
                
                // Get the list of the Computing Elements for the given SW tag
                randomCE = bdii.getRandomCEForSWTag("VO-" + 
                                              astra_VONAME + 
                                              "-" +
                                              astra_SOFTWARE);
                
                // Fetching the Queues
                for (String CEqueue:CEqueues) {
                    if (CEqueue.contains(randomCE))
                        randomCE=CEqueue;
                }

        } catch (URISyntaxException ex) {
                Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
        }                   

        return randomCE;
    }

    // Retrieve the list of Computing Elements
    // matching the ASTRA Software Tag    
    public List<String> getListofCEForSoftwareTag(String astra_VONAME,
                                                  String astra_TOPBDII,
                                                  String astra_SOFTWARE)
                                throws PortletException, IOException
    {
        List<String> CEs_list = null;
        BDII bdii = null;
        
        log.info("\n- Querying the Information System [ " + 
                  astra_TOPBDII + 
                  " ] and looking for CEs matching SW tag [ VO-" + 
                  astra_VONAME +
                  "-" +
                  astra_SOFTWARE + " ]");

        try {
        
                bdii = new BDII( new URI(astra_TOPBDII) );
                    
                CEs_list = bdii.queryCEForSWTag(
                           "VO-" +
                           astra_VONAME +
                           "-" +
                           astra_SOFTWARE);

		// Fetching the CE hostnames
		/*for (String CE:CEs_list) 
	          log.info("\n- CE host found = " + CE);*/

        } catch (URISyntaxException ex) {
                Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
                Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
        }

        return CEs_list;
    }

    // Get the GPS location of the given grid resource
    public String[] getCECoordinate(RenderRequest request,
                                    String CE)
                                    throws PortletException, IOException
    {
        String[] GPS_locations = null;
        BDII bdii = null;

        PortletPreferences portletPreferences =
                (PortletPreferences) request.getPreferences();

        // Get the ASTRA TOPPBDII from the portlet preferences
        String cometa_astra_TOPBDII = 
		portletPreferences.getValue("cometa_astra_TOPBDII", "N/A");
        String gridit_astra_TOPBDII = 
		portletPreferences.getValue("gridit_astra_TOPBDII", "N/A");
	String gilda_astra_TOPBDII = 
                portletPreferences.getValue("gilda_astra_TOPBDII", "N/A");
        String eumed_astra_TOPBDII = 
		portletPreferences.getValue("eumed_astra_TOPBDII", "N/A");
        String gisela_astra_TOPBDII = 
		portletPreferences.getValue("gisela_astra_TOPBDII", "N/A");
        String sagrid_astra_TOPBDII = 
		portletPreferences.getValue("sagrid_astra_TOPBDII", "N/A");

        // Get the ASTRA ENABLEINFRASTRUCTURE from the portlet preferences
        String astra_ENABLEINFRASTRUCTURE = 
		portletPreferences.getValue("astra_ENABLEINFRASTRUCTURE", "N/A");

            try {                
                if ( astra_ENABLEINFRASTRUCTURE.equals("cometa") )
                     bdii = new BDII( new URI(cometa_astra_TOPBDII) );
                
                if ( astra_ENABLEINFRASTRUCTURE.equals("gridit") )
                     bdii = new BDII( new URI(gridit_astra_TOPBDII) );
                
                if ( astra_ENABLEINFRASTRUCTURE.equals("gilda") )
                     bdii = new BDII( new URI(gilda_astra_TOPBDII) );

                if ( astra_ENABLEINFRASTRUCTURE.equals("eumed") )
                     bdii = new BDII( new URI(eumed_astra_TOPBDII) );

                if ( astra_ENABLEINFRASTRUCTURE.equals("gisela") )
                    bdii = new BDII( new URI(gisela_astra_TOPBDII) );
                
                if ( astra_ENABLEINFRASTRUCTURE.equals("sagrid") )
                    bdii = new BDII( new URI(sagrid_astra_TOPBDII) );

                GPS_locations = bdii.queryCECoordinate("ldap://" + CE + ":2170");

            } catch (URISyntaxException ex) {
                Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Astra.class.getName()).log(Level.SEVERE, null, ex);
            }

            return GPS_locations;
    }


    private void sendHTMLEmail (String USERNAME,
                                String TO,
                                String FROM,
                                String SMTP_HOST,
                                String ApplicationAcronym,
                                String GATEWAY)
    {

        log.info("\n- Sending email notification to the user " + USERNAME + " [ " + TO + " ]");

        log.info("\n- SMTP Server = " + SMTP_HOST);
        log.info("\n- Sender = " + FROM);
        log.info("\n- Receiver = " + TO);
        log.info("\n- Application = " + ApplicationAcronym);
        log.info("\n- Gateway = " + GATEWAY);        

        // Assuming you are sending email from localhost
        String HOST = "localhost";
        
        // Get system properties
        Properties properties = System.getProperties();
        properties.setProperty(SMTP_HOST, HOST);
        
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);                
        
	try {
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);
        
        // Set From: header field of the header.
        message.setFrom(new InternetAddress(FROM));
        
        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        //message.addRecipient(Message.RecipientType.CC, new InternetAddress(FROM));
        
        // Set Subject: header field
        message.setSubject(" [liferay-sg-gateway] - [ " + GATEWAY + " ] ");
        
        Date currentDate = new Date();
        currentDate.setTime (currentDate.getTime());                

	// Send the actual HTML message, as big as you like
	message.setContent(
	"<br/><H4>" +
        "<img src=\"http://fbcdn-profile-a.akamaihd.net/hprofile-ak-snc6/195775_220075701389624_155250493_n.jpg\" width=\"100\">Science Gateway Notification" +	
	"</H4><hr><br/>" +
	"<b>Description:</b> Notification for the application <b>[ " + ApplicationAcronym + " ]</b><br/><br/>" +
	"<i>The application has been successfully submitted from the [ " + GATEWAY + " ]</i><br/><br/>" +
	"<b>TimeStamp:</b> " + currentDate + "<br/><br/>" +
	"<b>Disclaimer:</b><br/>" +
	"<i>This is an automatic message sent by the Science Gateway based on Liferay technology.<br/>" +
	"If you did not submit any jobs through the Science Gateway, please " +
        "<a href=\"mailto:" + FROM + "\">contact us</a></i>",
	"text/html");
		
	// Send message
	Transport.send(message);
	} catch (MessagingException mex) { mex.printStackTrace(); }
    }
}
