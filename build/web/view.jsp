<%
/**************************************************************************
Copyright (c) 2011-2013:
Istituto Nazionale di Fisica Nucleare (INFN), Italy
Consorzio COMETA (COMETA), Italy
    
See http://www.infn.it and and http://www.consorzio-cometa.it for details 
on the copyright holders.
    
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
 **************************************************************************/
%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.liferay.portal.model.Company" %>
<%@ page import="javax.portlet.*" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<portlet:defineObjects/>

<%
  Company company = PortalUtil.getCompany(request);
  String gateway = company.getName();
%>

<jsp:useBean id="GPS_table" class="java.lang.String" scope="request"/>
<jsp:useBean id="GPS_queue" class="java.lang.String" scope="request"/>

<jsp:useBean id="cometa_astra_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_astra_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_astra_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_astra_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_astra_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_astra_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_astra_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_astra_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_astra_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_astra_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="cometa_astra_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="garuda_astra_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_astra_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_astra_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_astra_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_astra_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_astra_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_astra_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_astra_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_astra_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_astra_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="garuda_astra_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gridit_astra_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_astra_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_astra_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_astra_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_astra_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_astra_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_astra_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_astra_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_astra_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_astra_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gridit_astra_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gilda_astra_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_astra_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_astra_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_astra_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_astra_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_astra_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_astra_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_astra_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_astra_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_astra_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gilda_astra_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="eumed_astra_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_astra_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_astra_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_astra_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_astra_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_astra_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_astra_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_astra_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_astra_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_astra_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="eumed_astra_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="gisela_astra_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_astra_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_astra_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_astra_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_astra_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_astra_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_astra_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_astra_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_astra_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_astra_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="gisela_astra_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="sagrid_astra_INFRASTRUCTURE" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_astra_TOPBDII" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_astra_WMS" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_astra_VONAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_astra_ETOKENSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_astra_MYPROXYSERVER" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_astra_PORT" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_astra_ROBOTID" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_astra_ROLE" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_astra_RENEWAL" class="java.lang.String" scope="request"/>
<jsp:useBean id="sagrid_astra_DISABLEVOMS" class="java.lang.String" scope="request"/>

<jsp:useBean id="astra_ENABLEINFRASTRUCTURE" class="java.lang.String[]" scope="request"/>
<jsp:useBean id="astra_APPID" class="java.lang.String" scope="request"/>
<jsp:useBean id="astra_OUTPUT_PATH" class="java.lang.String" scope="request"/>
<jsp:useBean id="astra_SOFTWARE" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_HOSTNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_USERNAME" class="java.lang.String" scope="request"/>
<jsp:useBean id="TRACKING_DB_PASSWORD" class="java.lang.String" scope="request"/>
<jsp:useBean id="SMTP_HOST" class="java.lang.String" scope="request"/>
<jsp:useBean id="SENDER_MAIL" class="java.lang.String" scope="request"/>

<script type="text/javascript">
    
    var latlng2markers = [];
    var icons = [];
    
    icons["plus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/plus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["minus"] = new google.maps.MarkerImage(
          '<%= renderRequest.getContextPath()%>/images/minus_new.png',
          new google.maps.Size(16,16),
          new google.maps.Point(0,0),
          new google.maps.Point(8,8));
    
    icons["ce"] = new google.maps.MarkerImage(
            '<%= renderRequest.getContextPath()%>/images/ce-run.png',
            new google.maps.Size(16,16),
            new google.maps.Point(0,0),
            new google.maps.Point(8,8));
    
    function hideMarkers(markersMap,map) 
    {
            for( var k in markersMap) 
            {
                if (markersMap[k].markers.length >1) {
                    var n = markersMap[k].markers.length;
                    var centerMark = new google.maps.Marker({
                        title: "Coordinates:" + markersMap[k].markers[0].getPosition().toString(),
                        position: markersMap[k].markers[0].getPosition(),
                        icon: icons["plus"]
                    });
                    for ( var i=0 ; i<n ; i++ ) 
                        markersMap[k].markers[i].setVisible(false);
                    
                    centerMark.setMap(map);
                    google.maps.event.addListener(centerMark, 'click', function() {
                        var markersMap = latlng2markers;
                        var k = this.getPosition().toString();
                        var visibility = markersMap[k].markers[0].getVisible();
                        if (visibility == false ) {
                            splitMarkersOnCircle(markersMap[k].markers,
                            markersMap[k].connectors,
                            this.getPosition(),
                            map
                        );
                            this.setIcon(icons["minus"]);
                        }
                        else {
                            var n = markersMap[k].markers.length;
                            for ( var i=0 ; i<n ; i++ ) {
                                markersMap[k].markers[i].setVisible(false);
                                markersMap[k].connectors[i].setMap(null);
                            }
                            markersMap[k].connectors = [];
                            this.setIcon(icons["plus"]);
                        }
                    });
                }
            }
    }
    
    function splitMarkersOnCircle(markers, connectors, center, map) 
    {
            var z = map.getZoom();
            var r = 64.0 / (z*Math.exp(z/2));
            var n = markers.length;
            var dtheta = 2.0 * Math.PI / n
            var theta = 0;
            
            for ( var i=0 ; i<n ; i++ ) 
            {
                var X = center.lat() + r*Math.cos(theta);
                var Y = center.lng() + r*Math.sin(theta);
                markers[i].setPosition(new google.maps.LatLng(X,Y));
                markers[i].setVisible(true);
                theta += dtheta;
                
                var line = new google.maps.Polyline({
                    path: [center,new google.maps.LatLng(X,Y)],
                    clickable: false,
                    strokeColor: "#0000ff",
                    strokeOpacity: 1,
                    strokeWeight: 2
                });
                
                line.setMap(map);
                connectors.push(line);
            }
    }
    
    function updateAverage(name) {
        
        $.getJSON('<portlet:resourceURL><portlet:param name="action" value="get-ratings"/></portlet:resourceURL>&astra_CE='+name,                  function(data) {                                               
            console.log(data.avg);
            $("#fake-stars-on").width(Math.round(parseFloat(data.avg)*20)); // 20 = 100(px)/5(stars)
            $("#fake-stars-cap").text(new Number(data.avg).toFixed(2) + " (" + data.cnt + ")");
        });                
    }
    
    // Create the Google Map JavaScript APIs V3
    function initialize(lat, lng, zoom) {
        console.log(lat);
        console.log(lng);
        console.log(zoom);
        
        var myOptions = {
            zoom: zoom,
            center: new google.maps.LatLng(lat, lng),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        
        var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);  
        var image = new google.maps.MarkerImage('<%= renderRequest.getContextPath() %>/images/ce-run.png');
        
        var strVar="";
        strVar += "<table>";
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Vote the resource availability";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<tr><td>\&nbsp;\&nbsp;";
        strVar += "<\/td><\/tr>";
        
        strVar += "<tr>";
        strVar += "<td>";
        strVar += "Rating: <span id=\"stars-cap\"><\/span>";
        strVar += "<div id=\"stars-wrapper2\">";
        strVar += "<select name=\"selrate\">";
        strVar += "<option value=\"1\">Very poor<\/option>";
        strVar += "<option value=\"2\">Not that bad<\/option>";
        strVar += "<option value=\"3\" selected=\"selected\">Average<\/option>";
        strVar += "<option value=\"4\">Good<\/option>";
        strVar += "<option value=\"5\">Perfect<\/option>";
        strVar += "<\/select>";
        strVar += "<\/div>";
        strVar += "<\/td>";       
        strVar += "<\/tr>";
        
        strVar += "<tr>";        
        strVar += "<td>";
        strVar += "Average: <span id=\"fake-stars-cap\"><\/span>";
        strVar += "";
        strVar += "<div id=\"fake-stars-off\" class=\"stars-off\" style=\"width:100px\">";
        strVar += "<div id=\"fake-stars-on\" class=\"stars-on\"><\/div>";
        strVar += "";
        strVar += "<\/div>";
        strVar += "<\/td>";
        strVar += "<\/tr>";
        strVar += "<\/table>";
    
        var data = <%= GPS_table %>;
        var queues = <%= GPS_queue %>;
        
        var infowindow = new google.maps.InfoWindow();
        google.maps.event.addListener(infowindow, 'closeclick', function() {
            this.setContent('');
        });
        
        var infowindowOpts = { 
            maxWidth: 200
        };
               
       infowindow.setOptions(infowindowOpts);
       
       var markers = [];
       for( var key in data ){
                        
            var LatLong = new google.maps.LatLng(parseFloat(data[key]["LAT"]), 
                                                 parseFloat(data[key]["LNG"]));                    
            
            // Identify locations on the map
            var marker = new google.maps.Marker ({
                animation: google.maps.Animation.DROP,
                position: LatLong,
                icon: image,
                title : key
            });    
  
            // Add the market to the map
            marker.setMap(map);
            
            var latlngKey=marker.position.toString();
            if ( latlng2markers[latlngKey] == null )
                latlng2markers[latlngKey] = {markers:[], connectors:[]};
            
            latlng2markers[latlngKey].markers.push(marker);
            markers.push(marker);
        
            google.maps.event.addListener(marker, 'click', function() {
             
            var ce_hostname = this.title;
            
            google.maps.event.addListenerOnce(infowindow, 'domready', function() {
                                        
                    $("#stars-wrapper2").stars({
                        cancelShow: false, 
                        oneVoteOnly: true,
                        inputType: "select",
                        callback: function(ui, type, value)
                        { 
                            $.getJSON('<portlet:resourceURL><portlet:param name="action" value="set-ratings"/></portlet:resourceURL>' +
                                '&astra_CE=' + ce_hostname + 
                                '&vote=' + value);
                            
                            updateAverage(ce_hostname);                      
                        }
                    });
                    
                    updateAverage(ce_hostname);               
                });              
                                                
                infowindow.setContent('<h3>' + ce_hostname + '</h3></br>' + strVar);
                infowindow.open(map, this);
                                           
                var CE_queue = (queues[ce_hostname]["QUEUE"]);
                $('#astra_CE').val(CE_queue);
                
                marker.setMap(map);
            }); // function                             
        } // for
        
        hideMarkers(latlng2markers,map);
        var markerCluster = new MarkerClusterer(map, markers, {maxZoom: 3, gridSize: 20});
    }    
</script>

<script type="text/javascript">    
    var EnabledInfrastructure = null;           
    var infrastructures = new Array();  
    var i = 0;    
    
    <c:forEach items="<%= astra_ENABLEINFRASTRUCTURE %>" var="current">
    <c:choose>
    <c:when test="${current!=null}">
        infrastructures[i] = '<c:out value='${current}'/>';       
        i++;  
    </c:when>
    </c:choose>
    </c:forEach>
        
    for (var i = 0; i < infrastructures.length; i++) {
       console.log("Reading array = " + infrastructures[i]);
       if (infrastructures[i]=="cometa") EnabledInfrastructure='cometa';
       if (infrastructures[i]=="garuda") EnabledInfrastructure='garuda';
       if (infrastructures[i]=="gridit") EnabledInfrastructure='gridit';
       if (infrastructures[i]=="gilda")  EnabledInfrastructure='gilda';
       if (infrastructures[i]=="eumed")  EnabledInfrastructure='eumed';
       if (infrastructures[i]=="gisela") EnabledInfrastructure='gisela';
       if (infrastructures[i]=="sagrid") EnabledInfrastructure='sagrid';
    }
    
    var NMAX = infrastructures.length;
    console.log (NMAX);

    $(document).ready(function() 
    {           
        var lat; var lng; var zoom;
        var found=0;
        
        if (parseInt(NMAX)>1) { 
            console.log ("More than one infrastructure has been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure').append("<img width='70' src='<%= renderRequest.getContextPath()%>/images/world.png' border='0'> More than one infrastructure has been configured!");
            lat=19;lng=14;zoom=2; found=1;
        } else if (EnabledInfrastructure=='cometa') {
            console.log ("Start up: enabled the cometa VO!");
            $('#cometa_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#cometa_enabled').show();            
            $('#gridit_enabled').hide();
            $('#gilda_enabled').hide();
            $('#eumed_enabled').hide();            
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();
            lat=37;lng=14;zoom=7;
            found=1;
        } else if (EnabledInfrastructure=='garuda') {
            console.log ("Start up: enabling garuda!");
            $('#garuda_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            lat=29.15;lng=77.41;zoom=4;
            found=1;
         } else if (EnabledInfrastructure=='gridit') {
            console.log ("Start up: enabled the gridit VO!");
            $('#gridit_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gridit_enabled').show();            
            $('#cometa_enabled').hide();
            $('#gilda_enabled').hide();
            $('#eumed_enabled').hide();
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();
            lat=42;lng=12;zoom=5;
            found=1;
        } else if (EnabledInfrastructure=='eumed') {
            console.log ("Start up: enabled the eumed VO!");
            $('#eumed_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#eumed_enabled').show();            
            $('#gridit_enabled').hide();
            $('#cometa_enabled').hide();
            $('#gilda_enabled').hide();
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();
            lat=34;lng=20;zoom=4;
            found=1;
        } else if (EnabledInfrastructure=='gilda') {
            console.log ("Start up: enabled the Indian Grid Infrastructure!");
            $('#gilda_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gilda_enabled').show();
            $('#eumed_enabled').hide();            
            $('#gridit_enabled').hide();
            $('#cometa_enabled').hide();            
            $('#gisela_enabled').hide();*/
            //$('#error_infrastructure').hide();            
            lat=42;lng=12;zoom=5;
            found=1;    
        } else if (EnabledInfrastructure=='gisela') {
            console.log ("Start up: enabled the gisela VO!");
            $('#gisela_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gisela_enabled').show();            
            $('#cometa_enabled').hide();
            $('#gridit_enabled').hide();
            $('#eumed_enabled').hide();
            $('#gilda_enabled').hide();*/
            //$('#error_infrastructure').hide();
            lat=2;lng=-36;zoom=2;
            found=1;
        } else if (EnabledInfrastructure=='sagrid') {
            console.log ("Start up: enabled the sagrid VO!");
            $('#sagrid_astra_ENABLEINFRASTRUCTURE').attr('checked','checked');
            /*$('#gisela_enabled').show();            
            $('#cometa_enabled').hide();
            $('#gridit_enabled').hide();
            $('#eumed_enabled').hide();
            $('#gilda_enabled').hide();*/
            //$('#error_infrastructure').hide();
            lat=-23;lng=24;zoom=4;
            found=1;
        }

        if (found==0) { 
	    console.log ("None of the grid infrastructures have been configured!");
            $("#error_infrastructure img:last-child").remove();
            $('#error_infrastructure').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> None of the available grid infrastructures have been configured!");
        }
                        
        var accOpts = {
            change: function(e, ui) {                       
                $("<div style='width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;'>").addClass("notify ui-corner-all").text(ui.newHeader.find("a").text() +
                    " was activated... ").appendTo("#error_message").fadeOut(2500, function(){ $(this).remove(); });
                // Get the active option
                var active = $("#accordion").accordion("option", "active");                
                if (active==2) initialize(lat, lng, zoom);                                                                           
            },
            autoHeight: false
        };
        
        // Create the accordions
        //$("#accordion").accordion({ autoHeight: false });
        $("#accordion").accordion(accOpts);
          
        // Create the sliders
        $( "#slider-astra-min" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 0,
            min: 0,
            max: 127,
            slide: function( event, ui ) {
                $( "#astra_min" ).val( ui.value );
                $( "input[type=hidden][name='astra_min']").val( ui.value);
            }
        });
        $( "#astra_min" ).val( $( "#slider-astra-min" ).slider( "value" ) );       
          
        $( "#slider-astra-max" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 0,
            min: 0,
            max: 127,
            slide: function( event, ui ) {                
                $( "#astra_max" ).val( ui.value ) ;
                $( "input[type=hidden][name='astra_max']").val( ui.value);
            }
        });
        $( "#astra_max" ).val( $( "#slider-astra-max" ).slider( "value" ) );
           
        $( "#slider-astra-minvelocity" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 0,
            min: 0,
            max: 127,
            slide: function( event, ui ) {
                $( "#astra_minvelocity" ).val( ui.value );
                $( "input[type=hidden][name='astra_minvelocity']").val( ui.value);
            }
        });
        $( "#astra_minvelocity" ).val( $( "#slider-astra-minvelocity" ).slider( "value" ) );
           
        $( "#slider-astra-maxvelocity" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 0,
            min: 0,
            max: 127,
            slide: function( event, ui ) {
                $( "#astra_maxvelocity" ).val( ui.value );
                $( "input[type=hidden][name='astra_maxvelocity']").val( ui.value);
            }
        });
        $( "#astra_maxvelocity" ).val( $( "#slider-astra-maxvelocity" ).slider( "value" ) );
           
        $( "#slider-astra-stepvelocity" ).slider({
            orientation: "horizontal",
            range: "min",
            value: 0,
            min: 1,
            max: 64,
            slide: function( event, ui ) {
                $( "#astra_stepvelocity" ).val( ui.value );
                $( "input[type=hidden][name='astra_stepvelocity']").val( ui.value);
            }
        });
        $( "#astra_stepvelocity" ).val( $( "#slider-astra-stepvelocity" ).slider( "value" ) );
            
        // Validate input form
        $('#commentForm').validate({
            rules: {
                astra_min: {
                    required: true,
                    min: 0,
                    max: 127                    
                },
                astra_max: {                    
                    required: true,
                    range: [ 0, 127 ]
                },
                astra_minvelocity: {
                    required: true,
                    range: [ 0, 127 ]
                },
                astra_maxvelocity: {
                    required: true,
                    range: [ 0, 127 ]
                },
                astra_stepvelocity: {
                    required: true,
                    range: [ 1, 64 ]
                }
            }                                  
        });
        
        // Check file input size with jQuery (Max. 2.5MB)
        $('input[type=file][name=\'astra_file\']').bind('change', function() {
            if (this.files[0].size/1000 > 25600) {     
                // Remove the img and text (if any)
                $("#error_message img:last-child").remove();
                $("#error_message").empty();
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> The user demo file must be less than 2.5MB");
                $("#error_message").css({"color":"red","font-size":"14px"});
                // Removing the input file
                $('input[type=\'file\'][name=\'astra_file\']').val('');
                return false;
            }           
        });                
        
        $("#commentForm").bind('submit', function() {            

            var flag=true;
            // Remove the img and text error (if any)
            $("#error_message img:last-child").remove();
            $("#error_message").empty();
            
            // Check if the range of (astra_min and astra_max) params are ok.
            if ( parseInt($("#astra_max").val()) < parseInt($("#astra_min").val()) ) {                
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> Wrong parameters range");
                $("#error_message").css({"color":"red","font-size":"14px"});                   
                return false;
                flag=false;
            }
            
            // Check if the range of (astra_minvelocity and astra_maxvelocity) params are ok.
            if ( parseInt($("#astra_maxvelocity").val()) < parseInt($("#astra_minvelocity").val()) ) {                
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> Wrong parameters range");
                $("#error_message").css({"color":"red","font-size":"14px"});                   
                return false;
                flag=false;
            }
                
            
            // Check if the uploaded file is a .ski file.
            if ($('input:checked[type=\'radio\'][name=\'astra_demo\']').val() == "astra_user_demo")
            {
                var ext = ($('input[type=file][name=\'astra_file\']').val().split('.').pop().toLowerCase());                
                if ($.inArray(ext, ['ski','mid']) == -1) {
                    $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> Invalid file extension");
                    $("#error_message").css({"color":"red","font-size":"14px"});                   
                    return false;
                    flag=false;
                }                
            } else
                      
            // Check if the input settings are valid before to
            // display the warning message.
                if ( (($('input:checked[type=\'radio\'][name=\'astra_demo\']').val() == "astra_user_demo") &&
                ($('input[type=file][name=astra_file]').val() == "")) ||
                (($('input:checked[type=\'radio\'][name=\'astra_demo\']').val() != "astra_user_demo") &&
                ($('input:checked[type=\'radio\'][name=\'astra_demo\']').val() != "astra_default_demo")) ) 
                {            
                // Display the warning message  
                $('#error_message').append("<img width='35' src='<%= renderRequest.getContextPath()%>/images/Warning.png' border='0'> You missed many settings! They have been highlighted below.");
                $("#error_message").css({"color":"red","font-size":"14px"});
                flag=false;
            }
            
            if (flag) {
                $("#error_message").css({"color":"red","font-size":"14px", "font-family": "Tahoma,Verdana,sans-serif,Arial"});
                $('#error_message').append("<img width='30' src='<%= renderRequest.getContextPath()%>/images/button_ok.png' border='0'> Submission in progress...")(30000, function(){ $(this).remove(); });                 
            }
            
        });
                   
        // Roller
        $('#astra_footer').rollchildren({
            delay_time         : 3000,
            loop               : true,
            pause_on_mouseover : true,
            roll_up_old_item   : true,
            speed              : 'slow'   
        });
        
        $("#stars-wrapper1").stars({
            cancelShow: false,
            captionEl: $("#stars-cap"),
            callback: function(ui, type, value)
            {
                $.getJSON("ratings.php", {rate: value}, function(json)
                {                                        
                    $("#fake-stars-on").width(Math.round( $("#fake-stars-off").width()/ui.options.items*parseFloat(json.avg) ));
                    $("#fake-stars-cap").text(json.avg + " (" + json.votes + ")");
                });
            }
        });                
    });

    function enable_AstraDemo(f) {
        if ($('input:checked[type=\'radio\'][name=\'astra_demo\']',f).val() == "astra_user_demo") {
            // Enabling the uploading of the user file
            $('input[type=\'file\'][name=\'astra_file\']').removeAttr ('disabled');
            // Disabling the choosing of the astra demo file
            $('select[name="defaultdemo"]').attr('disabled','disabled');
        } else {        
            // Disabling the uploading of the user file
            $('input[type=\'file\'][name=\'astra_file\']').attr('disabled','disabled');
            // Enabling the choosing of the astra demo file
            $('select[name="defaultdemo"]').removeAttr ('disabled');
        }     
    }

</script>

<table style="width:700px;" border="0">
<tr>
    <td><div align="absmiddle">
        <a align="left" href="http://www.astraproject.org/">
        <img width="250"
             src="<%= renderRequest.getContextPath()%>/images/ASTRA_logo_new.png" 
             border="0" title="The ASTRA Project"/>
        </a>
    </td>
    <td>
        <div align="absmiddle">
        <a align="left" href="http://www.lostsoundsorchestra.org/">
        <img width="250"
             src="<%= renderRequest.getContextPath()%>/images/LostSoundOrchestra.png" 
             border="0" title="The Lost Sound Orchestra"/>
        </a>
    </td>
</tr>    
</table>

<br/>
<form enctype="multipart/form-data" 
      id="commentForm" 
      action="<portlet:actionURL><portlet:param name="ActionEvent" 
      value="SUBMIT_ASTRA_PORTLET"/></portlet:actionURL>"      
      method="POST">

<fieldset>
<legend>ASTRA Input Form</legend>
<div style="margin-left:15px" id="error_message"></div> 

<!-- Accordions -->
<div id="accordion" style="width:650px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">       
<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_1.png" />
    <b>Display Settings</b>
    </a>
</h3>
<div> <!-- Inizio primo accordion -->
<p>The current ASTRA portlet has been configured for:</p>
<table id="results" border="0" width="600">
          
<!-- COMETA -->
<tr></tr>
<tr>
    <td>        
        <c:forEach var="enabled" items="<%= astra_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='cometa'}">
                <c:set var="results_cometa" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_cometa=='true'}">        
            <input type="checkbox" 
                   id="cometa_astra_ENABLEINFRASTRUCTURE"
                   name="cometa_astra_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The COMETA Grid Infrastructure
            
            <!--img width="20"
                 id="cometa_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- GARUDA -->
<tr></tr>
<tr>
    <td>        
        <c:forEach var="enabled" items="<%= astra_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='garuda'}">
                <c:set var="results_garuda" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_garuda=='true'}">        
            <input type="checkbox" 
                   id="garuda_astra_ENABLEINFRASTRUCTURE"
                   name="garuda_astra_ENABLEINFRASTRUCTURE"
                   size="55px;" 
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Garuda Grid Infrastructure
        
            <!--img width="20"
                 id="gridit_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- GRIDIT -->
<tr></tr>
<tr>
    <td>        
        <c:forEach var="enabled" items="<%= astra_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gridit'}">
                <c:set var="results_gridit" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_gridit=='true'}">        
            <input type="checkbox" 
                   id="gridit_astra_ENABLEINFRASTRUCTURE"
                   name="gridit_astra_ENABLEINFRASTRUCTURE"
                   size="55px;" 
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Italian Grid Infrastructure
        
            <!--img width="20"
                 id="gridit_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- GILDA -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= astra_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gilda'}">
                <c:set var="results_gilda" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_gilda=='true'}">        
            <input type="checkbox" 
                   id="gilda_astra_ENABLEINFRASTRUCTURE"
                   name="gilda_astra_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The GILDA t-Infrastructure
        
            <!--img width="20"
                 id="gilda_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- EUMED -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= astra_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='eumed'}">
                <c:set var="results_eumed" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_eumed=='true'}">        
            <input type="checkbox" 
                   id="eumed_astra_ENABLEINFRASTRUCTURE"
                   name="eumed_astra_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Mediterranean Grid Infrastructure
        
            <!--img width="20"
                 id="eumed_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- GISELA -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= astra_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='gisela'}">
                <c:set var="results_gisela" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_gisela=='true'}">        
            <input type="checkbox" 
                   id="gisela_astra_ENABLEINFRASTRUCTURE"
                   name="gisela_astra_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The Latin America Grid Infrastructure
        
            <!--img width="20"
                 id="gisela_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>
    </td>
</tr>

<!-- SAGRID -->
<tr></tr>
<tr>
    <td>
        <c:forEach var="enabled" items="<%= astra_ENABLEINFRASTRUCTURE %>">
            <c:if test="${enabled=='sagrid'}">
                <c:set var="results_sagrid" value="true"></c:set>
            </c:if>
        </c:forEach>

        <c:choose>
            <c:when test="${results_sagrid=='true'}">        
            <input type="checkbox" 
                   id="sagrid_astra_ENABLEINFRASTRUCTURE"
                   name="sagrid_astra_ENABLEINFRASTRUCTURE"
                   size="55px;"
                   checked="checked"
                   class="textfield ui-widget ui-widget-content required"
                   disabled="disabled"/> The South African Grid Infrastructure
        
            <!--img width="20"
                 id="sagrid_enabled"
                 style="display:none"
                 src="<%= renderRequest.getContextPath()%>/images/button_ok.png" 
                 border="0"/-->
            </c:when>
        </c:choose>
    </td>
</tr>

</table>
<br/>
<div style="margin-left:15px" 
     id="error_infrastructure" 
     style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px; display:none;">    
</div>
<br/>

<p align="center">
<img width="120" src="<%=renderRequest.getContextPath()%>/images/separatore.gif"/>
</p>

<p align="justify">
Instructions for users:<br/>
~ This portlet implements the sound/timbre reconstruction of ancient instruments 
  using archaeological data as fragments from excavations, written descriptions, pictures...<br/><br/>
  The technique used is the <a href="http://www.cim.mcgill.ca/~clark/nordmodularbook/nm_physical.html">Physical Modeling Synthesis</a>, a complex digital audio rendering 
  technique which allows modeling the time-domain physics of the instrument.<br/><br/>
  
  <img width="20" src="<%=renderRequest.getContextPath()%>/images/help.png" title="Read the Help"/>
   For further details, please click
   <a href="<portlet:renderURL portletMode='HELP'><portlet:param name='action' value='./help.jsp' />
            </portlet:renderURL>" >here</a>
  <br/><br/>
</p>
  
Inputs:<br/>
~ The portlet takes as input some parameters;<br/>
~ .ski or .midi file.<br/><br/>
Each run will produce:<br/>
~ std.out: the standard output file;<br/>
~ std.err: the standard error file;<br/>
~ .wav: a MIDI file about an opera played using the <a href="http://www.egi.eu/news-and-media/newsfeed/news_0142_reviving_sounds_epigonion.html">Epigonion</a>;<br/>
~ .tar.gz: containing the sound of each singular string of the Epigonion.<br/><br/>

For further information, please refer to the output.README file produced during the run.<br/><br/>

<p>If you need to change some preferences, please contact the
<a href="mailto:credentials-admin@ct.infn.it?Subject=Request for Technical Support [<%=gateway%> Science Gateway]&Body=Describe Your Problems&CC=sg-licence@ct.infn.it"> administrator</a>
</p>

<liferay-ui:ratings
    className="<%= it.infn.ct.astra.Astra.class.getName()%>"
    classPK="<%= request.getAttribute(WebKeys.RENDER_PORTLET).hashCode()%>" />

<!--div id="pageNavPosition"></div-->
</div> <!-- Fine accordion -->

<h3><a href="#">
    <img width="32" src="<%= renderRequest.getContextPath()%>/images/glass_numbers_2.png" />
    <b>Specify the Input Settings</b>               
    </a>
</h3>           
<div> <!-- Inizio secondo accordion -->
<p>Please, select the ASTRA input settings for your run</p>
<table border="0" width="540">
<tr>
    <td width="150">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Range value [ 0, 127 ] " />

        <label for="astra_min">Min Param.<em>*</em></label>                        
    </td>

    <td width="350"><div align="absmiddle" id="slider-astra-min"></div></td>
    <td width="50" align="right">
        <input type="hidden" 
               name="astra_min"
               value="0"
               class="required"/>
            
        <input type="text" 
               id="astra_min"
               value="0"
               disabled="disabled"                  
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
   </td>
</tr>

<tr>
    <td width="150">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Range value [ 0, 127 ] " />

        <label for="astra_max">Max Param.<em>*</em></label>
    </td>

    <td width="350"><div align="absmiddle" id="slider-astra-max"></div></td>
    <td width="50" align="right">
        <input type="hidden" 
               name="astra_max" 
               value="0"
               class="required"/>
        
        <input type="text" 
               id="astra_max"
               value="0"
               disabled="disabled"
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus "/>
     </td>                    
</tr>

<tr>
    <td width="150">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Range value [ 0, 127 ] " />

        <label for="astra_minvelocity">Min Velocity<em>*</em></label>
    </td>

    <td width="350"><div align="absmiddle" id="slider-astra-minvelocity"></div></td>
    <td width="50" align="right">
        <input type="hidden" 
               name="astra_minvelocity" 
               value="0"
               class="required"/>
        
        <input type="text" 
               id="astra_minvelocity"
               value="0"
               disabled="disabled"
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
    </td>                                    
</tr>

<tr>
    <td width="150">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png"  
             border="0" title="Range value [ 0, 127 ] " />

        <label for="astra_maxvelocity">Max Velocity<em>*</em></label>
    </td>

    <td width="350"><div align="absmiddle" id="slider-astra-maxvelocity"></div></td>
    <td width="50" align="right">
        <input type="hidden" 
               name="astra_maxvelocity" 
               value="0"
               class="required"/>
        
        <input type="text" 
               id="astra_maxvelocity" 
               value="0"
               disabled="disabled"
               style="width:30px; border:0; background:#C9C9C9; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
    </td>
</tr>

<tr></tr>

<tr>
    <td width="150">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Range value [ 1, 64 ] " />

          <label for="astra_stepvelocity">Step Velocity<em>*</em></label>
     </td>

     <td width="350"><div align="absmiddle" id="slider-astra-stepvelocity"></div></td>
     <td width="50" align="right">
        <input type="hidden" 
               name="astra_stepvelocity" 
               value="1"
               class="required"/>
         
        <input type="text" 
               id="astra_stepvelocity" 
               value="1"
               disabled="disable"
               style="width:30px; border:0; background:#D8D8D8; color:black; font-weight:bold;"
               class="textfield ui-widget ui-widget-content ui-state-focus"/>
     </td>
</tr>                
</table>
</div> <!-- Fine secondo accordion -->        

<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_3.png" />
    <b>Worldwide Software Distribution</b>
    </a>
</h3>           
<div> <!-- Inizio Terzo accordion -->            
    <p>See with the Google Map API where the ASTRA software has been successfully installed.</p>
    <p>Select the GPS location of the grid resource where you want run your ASTRA demo
    <u>OR, BETTER,</u> let the Science Gateway to choose the best one for you!</p>
    <p>This option is <u>NOT SUPPORTED</u> in multi-infrastructure mode!</p>

    <table border="0">
        <tr>
            <td><legend>Legend</legend></td>
            <td>&nbsp;<img src="<%=renderRequest.getContextPath()%>/images/plus_new.png"/></td>
            <td>&nbsp;Split close sites&nbsp;</td>
        
            <td><img src="<%=renderRequest.getContextPath()%>/images/minus_new.png"/></td>
            <td>&nbsp;Unsplit close sites&nbsp;</td>
            
            <td><img src="<%=renderRequest.getContextPath()%>/images/ce-run.png"/></td>
            <td>&nbsp;Computing resource&nbsp;</td>
        </tr>    
        <tr><td>&nbsp;</td></tr>
    </table>
            
    <legend>
        <div id="map_canvas" style="width:570px; height:600px;"></div>
    </legend>

    <input type="hidden" 
           name="astra_CE" 
           id="astra_CE"
           size="25px;" 
           class="textfield ui-widget ui-widget-content"
           value=""/>                  
</div> <!-- Fine terzo accordion -->        

<h3><a href="#">
    <img width="32" src="<%=renderRequest.getContextPath()%>/images/glass_numbers_4.png" />
    <b>Choose the Demo</b>
    </a>
</h3>           
<div> <!-- Inizio Quarto accordion -->
<p>Please, upload your *.ski or *.mid file <u>OR</u> select a default demo from the list below</p>
<table border="0" width="600">
    <tr>
        <td width="160">
            <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Upload your demo to be processed as ASCII file"/>
            
            <input type="radio" 
                   name="astra_demo"
                   id="astra_demo"
                   value="astra_user_demo"
                   class="required"
                   onchange="enable_AstraDemo(this.form);"/>Upload your demo (Max 2,5MB) <em>*</em>
            
             <input type="file" 
                    name="astra_file" 
                    style="padding-left: 1px; border-style: solid; border-color: gray; border-width: 1px; padding-left: 1px;"
                    width="500" class="required" disabled="disabled"/>
        </td>
    </tr>
    
    <tr>
        <td width="160">
            <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Select one of the available demos from the drop-down list"/>
        
            <input type="radio" 
                   name="astra_demo" 
                   id="astra_demo"                   
                   value="astra_default_demo" 
                   class="required"
                   onchange="enable_AstraDemo(this.form);"/>Available demos <em>*</em>
            
             <select name="defaultdemo" 
                     style="height:25px; padding-left: 1px; border-style: solid; border-color: gray; border-width: 1px; padding-left: 1px;" 
                     disabled="disabled">
             <option value="bachfugue.ski">Bach fugue played on a reconstructed Epigonion</option>
             <option value="dufay.ski">Middle age piece (G. Dufay) played on 4 reconstructued Epigonions</option>
             <option value="banchieri.ski">Excerpt from a XVII Century piece (A. Bianchieri) played on 4 reconstructed Epigonions</option>
             <option value="buxtehude.ski">Excerpt from a XVII Century piece (D. Buxtehude) played on 3 reconstructed Epigonions</option>
             <option value="schubert.ski">Excerpt from a XIX Century piece (F. Schubert) played on 3 reconstructed Epigonions</option>
             </select>
        </td>
    </tr>
    
    <tr><td><br/></td></tr>
    
    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Choose a description for your run "/>
        
        <label for="astra_desc">Description</label>
                      
        <input type="text"                
               id="astra_desc"
               name="astra_desc"
               style="padding-left: 1px; border-style: solid; border-color: grey; border-width: 1px; padding-left: 1px;"
               value="Please, insert here a description for your job"
               size="57" />
        </td>           
    </tr>

    <tr><td><br/></td></tr>

    <tr>
        <td width="160">
        <img width="30" 
             align="absmiddle"
             src="<%= renderRequest.getContextPath()%>/images/question.png" 
             border="0" title="Enable email notification to the user"/>
        
        <c:set var="enabled_SMTP" value="<%= SMTP_HOST %>" />
        <c:set var="enabled_SENDER" value="<%= SENDER_MAIL %>" />
        <c:choose>
        <c:when test="${empty enabled_SMTP || empty enabled_SENDER}">
        <input type="checkbox" 
               name="EnableNotification"
               disabled="disable"
               value="yes" /> Enable Notification
        </c:when>
        <c:otherwise>
        <input type="checkbox" 
               name="EnableNotification"               
               value="yes" /> Enable Notification
        </c:otherwise>
        </c:choose>

        <img width="70"
             id="EnableNotificationid"             
             src="<%= renderRequest.getContextPath()%>/images/mailing2.png" 
             border="0"/>
        </td>
    </tr>

    <tr><td><br/></td></tr>

    <tr>                    
        <td align="left">
            <input type="image" 
                   src="<%= renderRequest.getContextPath()%>/images/start-icon.png"
                   width="60"
                   name="submit"
                   id ="submit" 
                   title="Run your ASTRA Sound/Timbre Reconstruction demo!" />                    
        </td>
     </tr>                                            
</table>    
</div>	<!-- Fine Quarto accordion -->
</div> <!-- Fine accordions -->
</fieldset>    
</form>                                                                         

<div id="astra_footer" style="width:690px; font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 14px;">
    <div>ASTRA portlet ver. 1.8.4</div>
    <div>Istituto Nazionale di Fisica Nucleare (INFN), Italy, Copyright © 2006-2013</div>
    <div>All rights reserved</div>
    <div>This work has been partially supported by
        <a href="http://www.egi.eu/projects/egi-inspire/">
            <img width="35" 
                 border="0"
                 src="<%= renderRequest.getContextPath()%>/images/EGI_Logo_RGB_315x250px.png" 
                 title="EGI - The European Grid Infrastructure" />
        </a>
    </div>
</div>
                 
<!--script type="text/javascript">
    var pager = new Pager('results', 13); 
    pager.init(); 
    pager.showPageNav('pager', 'pageNavPosition'); 
    pager.showPage(1);
</script-->
