<!DOCTYPE html>
<html xmlns="http://www.w3c.org/1999/xhtml"
xmlns:h="http://java.sun.com/jsf/html"
xmlns:p="http://primefaces.org/ui"
xmlns:ui="http://java.sun.com/jsf/facelets"
xmlns:pt="http://xmlns.jcp.org/jsf/passthrough"
xmlns:f="http://java.sun.com/jsf/core">
<h:head>
    <h:outputStylesheet library="css" name="style.css"  />
    <script type="text/javascript" src="resources/js/fr.js"></script>
</h:head>
    <h:body styleClass="background_login">
        <h:form>
        <div class="sub_body_login"></div>
       
        <table class="tlogin tajouter">
            <tr class="ajoutertitre"><td>Ajouter nouveau dossier :</td><td></td></tr>
            <tr>
                <td><h:outputText value="Type de dossier" styleClass="labeladd"/> <br/>
                <p:selectOneMenu id="typedossier" styleClass="choix"
                                 value="#{ajouterdossierbean.typedossier}" onchange="onChangeDossier(this)">
                        <f:selectItem itemLabel="Liaison routière" itemValue="liaison" />
                        <f:selectItem itemLabel="Carrefour" itemValue="crf" />
                        <f:selectItem itemLabel="OA" itemValue="oa" />
                        <f:selectItem itemLabel="CPS" itemValue="cps" />
                    </p:selectOneMenu>
                </td>
                <td id="typecpsblock" style="display: none">
                <h:outputText value="Type de CPS" /><br/>
                <p:selectOneMenu id="typecps" styleClass="choix" value="#{ajouterdossierbean.typecps}">
                        <f:selectItem itemLabel="CPS Etude" itemValue="cpse" />
                        <f:selectItem itemLabel="CPS Travaux" itemValue="cpst" />
                        <f:selectItem itemLabel="CPS Controle" itemValue="cpsc" />
                    </p:selectOneMenu>
                </td>
            </tr>
            <tr class="input_area">
                <td colspan="2">
            <h:outputText value="Dossier" styleClass="input_login"/><br/>
            <p:inputTextarea value="#{ajouterdossierbean.dossierdescription}" />
                </td>
            </tr>
            <tr>
                <td>
                     <h:outputText value="DPETL" /><br/>
                     <p:selectOneMenu id="dpetl" styleClass="choix" value="#{ajouterdossierbean.dpetl}">
                         <ui:repeat var="d" value="#{ajouterdossierbean.dpetls}" varStatus="status">
                             <f:selectItem itemLabel="#{d}" itemValue="#{d}"/>
                         </ui:repeat>
                    </p:selectOneMenu>
                </td>
                <td>
                <h:outputText value="Date d'entrée"/><br/>
                <p:calendar styleClass="input_date" value="#{ajouterdossierbean.dateentree}" locale="fr" /><!--<p:inputText styleClass="input_login"/>-->
                </td>
            </tr>
            <tr class="forlaison">
                <td>
            <h:outputText value="Route"/><br/>
            <p:inputText styleClass="input_login" value="#{ajouterdossierbean.route}"/>
                </td>
                <td>
                <h:outputText value="Longeur"/><br/>
                <p:inputText styleClass="input_login" value="#{ajouterdossierbean.longeur}"/>
                </td>
            </tr>
            <tr class="forlaison">
                <td>
                    <h:outputText value="Point kilométrique debut"/><br/>
                    <p:inputText styleClass="input_login" value="#{ajouterdossierbean.pkd}"/>
                </td>
                <td>
                    <h:outputText value="Point kilométrique fin"/><br/>
                    <p:inputText styleClass="input_login" value="#{ajouterdossierbean.pkf}"/>
                </td>
            </tr>
             <tr class="forlaison">
                <td>
                    <h:outputText value="BET"/><br/>
                    <p:inputText styleClass="input_login"/>
                </td>
                <td>
                <h:outputText value="Phase"/><br/>
                <p:selectOneMenu value="#{ajouterdossierbean.phase}" styleClass="optionsajoutd choix">
                    <ui:repeat var="p" value="#{ajouterdossierbean.l}" varStatus="status">
                             <f:selectItem itemLabel="#{p}" itemValue="#{p}"/>
                    </ui:repeat>
                </p:selectOneMenu>
                </td>
            </tr>
             <tr >
                <td><h:outputText value="Nature"/><br/>
                  <p:selectOneMenu styleClass="choix" value="#{ajouterdossierbean.nature}">             
                      <ui:repeat var="n" value="#{ajouterdossierbean.natures}" varStatus="status">
                             <f:selectItem itemLabel="#{n}" itemValue="#{n}"/>
                      </ui:repeat>
                    </p:selectOneMenu>
                </td>
              <td/>
            </tr>
            <tr>
                <td><p:commandButton value="Ajouter" action="#{ajouterdossierbean.ajouterdossier}"/></td>
                <td></td>
            </tr>
        </table> 
        </h:form>
        <h:outputScript library="js" name="script.js" />
    </h:body>
</html>