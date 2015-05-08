<%
/**************************************************************************
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
****************************************************************************/
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javax.portlet.*"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<portlet:defineObjects/>

<script type="text/javascript">
    $(document).ready(function() {
              
    $('.slideshow').cycle({
	fx: 'fade' // choose your transition type, ex: fade, scrollUp, shuffle, etc...
    });
    
    // Roller
    $('#astra_footer').rollchildren({
        delay_time         : 3000,
        loop               : true,
        pause_on_mouseover : true,
        roll_up_old_item   : true,
        speed              : 'slow'   
    });
    
    //var $tumblelog = $('#tumblelog');  
    $('#tumblelog').imagesLoaded( function() {
      $tumblelog.masonry({
        columnWidth: 440
      });
    });
});
</script>
                    
<br/>

<fieldset>
<legend>About the project</legend>

<section id="content">

<div id="tumblelog" class="clearfix">
    
    <table style="width:490px;" border="0">
    <tr>
    <td><div align="center">
        <a href="http://www.astraproject.org/">
        <img width="250"
             src="<%= renderRequest.getContextPath()%>/images/ASTRA_logo_new.png" 
             border="0" title="The ASTRA Project"/>
        </a>
    </td>
    <td>
        <div align="center">
        <a href="http://www.lostsoundsorchestra.org/">
        <img width="250"
             src="<%= renderRequest.getContextPath()%>/images/LostSoundOrchestra.jpg" 
             border="0" title="The Lost Sound Orchestra"/>
        </a>
    </td>
    </tr>    
    </table>
    
  <div class="story col3">    
       <p style="text-align:justify; position: relative;">
       The <a href="http://www.astraproject.org/">ASTRA</a> project aims to reconstruct the sound or timbre of ancient instruments (not existing anymore) using 
       archaeological data as fragments from excavations, written descriptions, pictures, etc.</br>
       The technique used is the <a href="http://www.cim.mcgill.ca/~clark/nordmodularbook/nm_physical.html">Physical Modeling Synthesis</a>, 
       a complex digital audio rendering technique which allows 
       modeling the time-domain physics of the instrument.
       In other words the basic idea is to recreate a model of the musical instrument and produce the sound by simulating 
       its behavior as a mechanical system. </br>
       The application would produce one or more sounds corresponding to different configurations of the instrument 
       (i.e. the different notes).
       The project runs since 2006 thanks to the GEANT backbone and to computing resources located in Europe and in other regions of 
       the world, allowing researchers, musicians and historians to collaborate, communicate and share experiencies on lost 
       instruments and sounds ASTRA brings again to life.    
      </p>    
    <p>&mdash; <i>Domenico VICINANZA, Dante (Delivery of Advanced Network Technology to Europe)</i></p>
  </div>

  <div class="story col3">      

    <table border="0">
    <tr>
    <td>
    
    <a href="http://www.youtube.com/embed/KmzAQPz7Rj4">
    <img src="<%= renderRequest.getContextPath()%>/images/Help1.png"
         title="The Barbiton - Images credit of VisArc Studio, Parma, Italy"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         height="200" 
         width="270"/>
    </a>
    </td>
    <td> &nbsp;&nbsp; </td>
    <td>        
    <p style="text-align:justify;">
        <font style="position: relative;">
        In December 2008, a unique concert was staged using the digitally reconstructed sounds of the epigonion, 
        a harp-like instrument from Ancient Greece, alongside the Sonora Network Ensemble's performance of the 
        Czech composer <a href="http://en.wikipedia.org/wiki/Jan_Dismas_Zelenka">Jan Dismas Zelenka’s</a> Psalm “Laetatus sum”. 
        <br/>This world premiere showcased the sounds of an instrument of the past, reconstructed via computer-intensive 
        modeling, being performed alongside real instruments such as violins and flutes as well as voices.
        </font>        
    </p>    
    </td>
    </tr>
    </table>
    
  </div>                 
             
  <div class="story col1" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
      <h3>What's the PMS?</h3>      
        <p style="text-align:justify; font-size: 14px;">
        Physical Modeling Synthesis is the synthesis of 
        sound by using a set of equations and algorithms 
        to simulate a physical source of sound. 
        Sound is then generated using parameters that 
        describe the physical materials used in the 
        instrument and the user's interaction with it, 
        for example, by plucking a string, or covering 
        tone holes, and so on. [..]
        </p>
 </div>
             
  <div class="story col1" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
      <h3>Why the Grid ?</h3>      
          <p style="text-align:justify; font-size: 14px;">
          Physical modeling is a really computing intensive 
          technique since the complex models of the musical 
          instruments are solved by integrating numerical 
          coupled differential equations. 
          To have an idea of the needed time for simulation, 
          on a Pentium IV 1.6 Ghz, 512MB RAM Personal Computer 
          to correctly reproduce a sound lasting for 30 seconds 
          it could be required more than 4h.
          </p>
  </div>
             
  <div class="story col1" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
      <h3>ASTRA & GEANT</h3>  
      <p style="text-align:justify; font-size: 14px;">
      ASTRA computations are quite demanding in terms of network 
      and computing requirements. The extremely high reliability 
      of the <a href="http://www.geant.net">GEANT2</a> network, its performances in terms of number 
      of institutions connected and throughput, made the ASTRA project possible.            
      </p>
  </div>
             
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 18px;">
    <h3 style="text-align:justify; font-size: 17px;">Success Stories</h3>
    <div id="slideshow">    
    <!--img src="<%= renderRequest.getContextPath()%>/images/epigonion.jpg"
         title="The Epigonion (440 B.C.)"         
         style="padding: 15px; border: 1px solid #ccc; background-color: #eee;"
         heigth="210"
         width="130"/-->
    
    <a href="<%= renderRequest.getContextPath()%>/images/astra.jpg">
    <img src="<%= renderRequest.getContextPath()%>/images/astra.jpg"
         title="The Epigonion - Images credit of VisArc Studio, Parma, Italy"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigth="175"
         width="175"/>
    </a>    
    
    <a href="<%= renderRequest.getContextPath()%>/images/phoca_thumb_l_020.jpeg">
    <img src="<%= renderRequest.getContextPath()%>/images/phoca_thumb_l_020.jpeg"
         title="The Barbiton - Images credit of VisArc Studio, Parma, Italy"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="180" 
         width="160"/>
    </a>
         
    <a href="http://www.youtube.com/embed/YZ6JNzD2TM4">
    <img src="<%= renderRequest.getContextPath()%>/images/Help2.png"         
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="300" 
         width="180"/>
    </a>
         
    
    <br/>
         
    <table border="0">
    <tr>
    <td>
    
    <a href="http://www.youtube.com/watch?v=S-AL3Z0GmlM">
    <img src="<%= renderRequest.getContextPath()%>/images/EGI_StoriesFromTheGrid.jpg"
         title="Reviving the lost sounds of the Epigonion"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         height="180" 
         width="250"/>
    </a>
    </td>
    <td> &nbsp;&nbsp; </td>
    <td>            
        <font style="position: relative;">
        <h3 style="text-align:justify; font-size: 17px;">Stories from the Grid - Episode II</h3>        
        <p style="text-align:justify; font-size: 14px;">
        As part of the new outreach strategy, EGI asked the film company 'Een van de jongens' 
        to produce a series of videos about grid computing applications for science.  
        Each five-minute episode focuses on a specific application of grid computing in a
        different research field. 
        The videos were filmed as documentaries and feature the people actively involved in the
        research explaining the goals and outcomes of the work.
        </p> 
        </font>    
    </td>
    </tr>
    </table>    
    </div>
  </div>
         
  <!--div class="story col2" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 18px;">
      <h3 style="text-align:justify; font-size: 17px;">Stories from the Grid - the premiere</h3>
      <p style="text-align:justify; font-size: 14px;">
      As part of the new outreach strategy, EGI asked the film company 'Een van de jongens' 
      to produce a series of videos about grid computing applications for science.     
      </p>
      
      <a href="http://www.youtube.com/watch?v=S-AL3Z0GmlM">
      <img src="<%= renderRequest.getContextPath()%>/images/Help2.png"
         title="Episode 2 - The Epigonion"
         style="padding: 5px; border: 1px solid #ccc; background-color: #eee;"
         heigh="300" 
         width="180"/>
    </a>
  </div-->

  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
      <h2><a href="mailto:info@lostsoundorchestra.org">
      <img width="100" style="font-size: 13px;" src="<%= renderRequest.getContextPath()%>/images/contact6.jpg" border="0" title="Get in touch with the ASTRA Project"/></a>Contacts</h2>
      <p style="text-align:justify;">Salvatore AVANZO <i> &mdash; (Responsible for Development Activities)</i></p>     
      <p style="text-align:justify;">Francesco DE MATTIA <i> &mdash; (Artistic Coordinator)</i></p>
      <p style="text-align:justify;">Giuseppe LA ROCCA<i> &mdash; (Responsible for GRID deployment)</i></p>
      <p style="text-align:justify;">Mariapaola SORRENTINO<i> &mdash; (Modelling/Testing)</i></p>
      <p style="text-align:justify;">Domenico VICINANZA <i> &mdash; (Technical Coordinator)</i></p>        
  </div>               
    
  <div class="story col3" style="font-family: Tahoma,Verdana,sans-serif,Arial; font-size: 13px;">
        <h2>Sponsors</h2>
        <table border="0">
            <tr><td>
            <a href="http://www.abluesky.com/">
            <img align="center" width="100" 
                 src="<%= renderRequest.getContextPath()%>/images/BlueSkyLogo.jpg" 
                 border="0" title="BlueSky"/>
            </a>
            </td>
            
            <td>
            <a href="http://www.grisbymusic.it/">
            <img align="center" width="60" 
                 src="<%= renderRequest.getContextPath()%>/images/grisby.jpg"
                 border="0" title="Grisby Music"/>
            </a>
            </td>
            
            <td>
            <a href="http://www.meyersound.com/">
            <img align="center" width="90"
                 src="<%= renderRequest.getContextPath()%>/images/meyersound.jpg"
                 border="0" title="Meyer Sound"/>            
            </a>
            </td>
            
            <td>
            <a href="http://www.bricasti.com/">
                <img align="center" width="120"
                     src="<%= renderRequest.getContextPath()%>/images/bricasti.jpg" 
                     border="0" title="Bricasti home page" />
            </a>
            </td>
            
            <td>
            <a href="http://www.visarcstudio.com/">
                <img align="center" width="60"
                     src="<%= renderRequest.getContextPath()%>/images/VisArc.png" 
                     border="0" title="The VisArch Studio" />
            </a>
            </td>                        
            </tr>
            
            <tr>                
            <td>
            <a href="http://www.geant.net">
                <img align="center" width="100" 
                     src="<%= renderRequest.getContextPath()%>/images/geant_logo_rgb_300dpi.png" 
                     border="0" title="The GEANT Project" />
            </a>
            </td>                            
                    
            <td>
            <a href="http://www.eumedconnect.net/">
                <img align="center" width="100"                      
                     src="<%= renderRequest.getContextPath()%>/images/EUMEDconnect2_300dpi_RGB.png" 
                     border="0" title="The EUMEDCONNECT Project" />
            </a>
            </td>                        
            
            <td>
            <a href="http://www.redclara.net/">
                <img align="center" width="100"                      
                     src="<%= renderRequest.getContextPath()%>/images/redclara.png" 
                     border="0" title="RedCLARA - Cooperación Latino Americana de Redes Avanzadas" />
            </a>
            </td>
            
            <td>
            <a href="http://www.tein2.net">
                <img align="center" width="100"                      
                     src="<%= renderRequest.getContextPath()%>/images/TEIN2-web.gif" 
                     border="0" title="The TEIN2 Project" />
            </a>
            </td>
            
            <td>
            <p align="right">
            <a href="http://www.dante.net/">
                <img align="left" width="80"
                     src="<%= renderRequest.getContextPath()%>/images/dante_2colour_logo.png" 
                     border="0" title="DANTE" />
            </a>
            </p>
            </td>
            </tr>
            
            <tr>
            <td>
            <a href="http://www.egi.eu/projects/egi-inspire/">
                <img align="center" width="80" 
                     src="<%= renderRequest.getContextPath()%>/images/EGI_Logo_RGB_315x250px.png" 
                 title="EGI - The European Grid Infrastructure" />
            </a>   
            </td>
            
            <td>
            <a href="http://www.infn.it/indexen.php">
                <img align="center" width="100" 
                     src="<%= renderRequest.getContextPath()%>/images/garland_logo.png"  
                     border="0" title="IGI - The Italian Grid Initiatives" />
            </a>   
            </td>
            
            <td>
            <a href="http://achalai.redclara.net/">
                <img align="center" width="130"                      
                     src="<%= renderRequest.getContextPath()%>/images/achalai.jpeg" 
                     border="0" title="The Achalai Project" />
            </a>
            </td>                        
            
            <td>
            <a href="http://www.eumedgrid.eu/">
                <img align="center" width="130"
                     src="http://www.eumedgrid.eu/images/stories/eumedgrid_logo.png" 
                     border="0" title="The EUMEDGRID-Support Project" />
            </a>
            </td>
            
            <td>
            <a href="http://www.gisela-grid.eu/">
                <img align="center" width="120"                      
                     src="<%= renderRequest.getContextPath()%>/images/GISELA_Logo_B.gif" 
                     border="0" title="The giSela Project" />
            </a>
            </td>            
            </tr>
            
            <tr>
            <td>
            <p align="justify">
            <a href="http://www.indicate-project.eu/">
                <img align="center" width="120"                      
                     src="<%= renderRequest.getContextPath()%>/images/Indicate.png" 
                     border="0" title="The Indicate Project" />
            </a>
            </p>
            </td>
            
            <td>
            <p align="justify">
            <a href="http://www.euindiagrid.eu/">
                <img align="center" width="120"                      
                     src="<%= renderRequest.getContextPath()%>/images/euindia_logo.png" 
                     border="0" title="The EU-India Project" />
            </a>
            </p>
            </td>
            </tr>
        </table>   
  </div>
</div>
</section>
</fieldset>
           
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